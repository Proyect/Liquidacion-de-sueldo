/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** * Ariel Marcelo Diaz*/
public class Legajolib extends libSentenciasSQL
{
    //instancias
    int idLegajo=0;
    int idPersona=0;
    int estadoL=0;
    String fecha=FechaActual();
    String hora=HoraActual();
    //Legajolib.Novedad novedad = this.new Novedad();
            
    //constructor
    public Legajolib()
    {
        this.tabla = "legajo";
        this.campos = "idPersona,idEstado,fechaIngreso";        
    }
    
    //agrega un nuevo legajo
    public int nuevoLegajo(int persona)
    {
        this.idPersona = persona;
        this.estadoL=1;
        this.valores = this.idPersona+","+this.estado+",'"+this.fecha+"'";
        return this.insertaSQL();
    }        
            
     //cambia el estado del legajo
    public int modificaLegajo(int estado)
    {
        this.estadoL = estado;
        this.campos = "idEstado";
        this.valores = ""+this.estado;
        this.condicion="idLegajo="+idLegajo;
        return this.modificaSQL();
    }
    
    class Novedad extends Legajolib
    {
        //constructor
        public Novedad()
        {
           this.tabla="novedad"; 
           this.campos ="idLegajo,asuntoNovedad,detalleNovedad,idTipoNovedad,fecha";
        }
        
        // agrega una nueva novedad
        public int nueva_novedad(String asunto, String detalle, int tipo)
        { 
            this.valores = this.idLegajo+",'"+asunto+"','"+detalle+"',"+tipo+",'"+this.fecha+"'";
            int resultado=0;
            if(this.insertaSQL()==1)
            {
                this.campos="MAX( idNovedad )";
                ResultSet ultima = null;
                ultima = this.consultaSQL();
                try 
                {                   
                    resultado = ultima.getInt(1);
                }
                catch (SQLException ex)
                {
                    estado = ex.getMessage();
                }                
            }
            else
            {
                Imprime("Novedad no agregada");
            }            
            return resultado;
        }
         
        //modifica una novedad
        public int modifica_novedad(String valores, String condicion)
        {
            this.valores = valores;
            this.condicion = condicion;
            return this.modificaSQL();
        }
        
        // Elimina una novedad del legajo
        public int elimina_novedad(int idNovedad)
        {
            this.condicion = "idNovedad="+idNovedad;            
            return this.borraSQL();
        }
    }
    
    //Sindicato
    class Sindicato extends Legajolib    
    {   //instancias
        int idSindicato=0;
        
        //constructor
        public Sindicato()
        {
            this.tabla = "sindicatolegajo";
            this.campos = "idSindicato,idLegajo";
        }
        
        //asigna un sindicato
        public int alta_sindicato()
        {    
            Novedad novedad = this.new Novedad();
            this.valores = this.idSindicato+","+this.idLegajo;
            if(this.insertaSQL() == 1)
            {
                novedad.idLegajo=this.idLegajo;
                novedad.nueva_novedad("nuevo sindicato",
                                    "El "+this.fecha+" se asigno un nuevo sindicato al legajo Sindicato Nro: "+
                                    this.idSindicato, 1);
                return 1;
            }
            else
            {
                return 0;
            }
        }
        
        //Elimina un sindicato del legajo
        public int baja_sindicato()
        {
            Novedad novedad = this.new Novedad();
            this.condicion = "idSindicato=" + this.idSindicato + " AND idLegajo=" + this.idLegajo;
            if (this.borraSQL() ==1)
            {
                novedad.nueva_novedad("baja de sindicato", 
                            "Se dio de baja al sindicato Nro:" + this.idSindicato, 1);
                return 1;
            }
            else
            {
                return 0;
            }
            
        }
    }
    
    //Clase de obra social
    class ObraSocial extends Legajolib 
    {  
        int idObraSocial;
        Novedad novedad = this.new Novedad();
        //constructor
        public ObraSocial()
        {
            this.tabla="obrasociallegajo";
            this.campos="idLegajo,idObraSocial";
            novedad.idLegajo = this.idLegajo;
        }
        
        public int asigna_obraSocial(int obraSocial)
        {
            this.idObraSocial=obraSocial;
            this.valores = this.idLegajo+","+this.idObraSocial;
            if( this.insertaSQL() ==1)
            {                
                novedad.nueva_novedad("Nueva asignacion de obra social",
                        "Se asigno la obra social Nro:"+this.idObraSocial, 1);
                return 1;
            }
            else
            {
                return 0;
            }
        }
        
        public int baja_obrasocial(int obraSocial)
        {
            this.condicion = "idLegajo="+this.idLegajo+" AND idObraSocial="+obraSocial;            
            if (this.borraSQL()==1) 
            {
                novedad.nueva_novedad("baja de obra social", 
                        "se dio de baja de la obra social Nro:"+this.idLegajo, estadoL);
                return 1;
            } 
            else
            {
                return 0;
            }
        }
    }
    
    class Asignaciones extends Legajolib // corregir y probar
    {   
        int idVinculo = 0;
        Novedad novedad = this.new Novedad();
        // constructor
        public Asignaciones()
        {
            this.tabla = "vinculopersona";
            this.campos = "idPersona,idvinculo,legajo";
            novedad.idLegajo = this.idLegajo;
        } 
        
        // realiza una asignacion 
        public int alta()
        {
            this.valores = this.idPersona+","+this.idVinculo+","+this.idLegajo;
            if( this.insertaSQL() == 1)
            {
                novedad.nueva_novedad("Nueva Asignacion Familiar", 
                                "Vinculo "+this.idVinculo+" Persona " + this.idPersona, 1);
                return 1;
            }
            else
            {
                return 0;
            }
        }
        
        //realiza la baja del vinculo
        public int baja()
        {
            this.condicion = "idPersona=" + this.idPersona +" AND legajo=" + this.idLegajo;
            return this.borraSQL();
        }
    }
    
    // Clases de inasistencia
    class Inasistencia extends Legajolib 
    {
        int justificada=0; 
        Novedad novedad = this.new Novedad();    
        int idNovedad = 0;
        //constructor
        public Inasistencia()
        {            
            this.tabla = "inasistencia";
            this.campos = "fecha,idLegajo,idNovedad,justificada";      
        }
        
        //crea una nueva inasistencia
        public int nueva()
        {   
            novedad.idLegajo = this.idLegajo; 
            Licencias licencia = new Licencias();
            licencia.condicion = "idLegajo " + this.idLegajo + " AND estado = 1";
            if (this.consultaSQL()==null) 
            {
                idNovedad = novedad.nueva_novedad("Nueva inasistencia", "Injustificada", 1);
            }
            else
            {
                idNovedad = novedad.nueva_novedad("Nueva inasistencia", "Justificada", 1);
                this.justificada=1;
            }
            
            if(idNovedad != 0)
            {
                this.valores = "'"+this.fecha+"',"+this.idLegajo+","+idNovedad+","+justificada;
                this.insertaSQL();
                return 1;
            }
            else
            {
                return 0;
            }            
        }
        
        //modifica una inasistencia
        public int modifica(int idInasistencia)
        {
            novedad.idLegajo = this.idLegajo;            
            this.condicion = "idInasistencia="+idInasistencia;
            this.valores =  "'"+this.fecha+"',"+this.idLegajo+","+idNovedad+","+justificada;
            
            if(this.modificaSQL() == 1)
            {
                novedad.nueva_novedad("Retificacion de inasistencia",this.valores , 1);
                return 1;
            }
            else
            {
                return 0;
            }
        }
        
        // elimina una inasistencia
        public int baja(int idInasistencia)
        {
            novedad.idLegajo = this.idLegajo; 
            this.condicion = "idInasistencia="+idInasistencia;
            if(this.borraSQL()==1)
            {
                novedad.nueva_novedad("Borrado de Inasistencia Nro:"+idInasistencia,"" , 1);
                return 1;
            }
            else
            {
                return 0;
            }              
        }
    }
    
    //clase llegadas tardes
    class LlegadasTardes extends Legajolib
    {
        int idLlegada=0;
        Novedad novedad = this.new Novedad();    
        int idNovedad = 0;
        public LlegadasTardes()
        {
            this.tabla = "llegadastardes";
            this.campos = "fecha,hora,idLegajo,idNovedad";            
        }
        
        // crea una nueva llegada tarde
        public int alta()
        {
            novedad.idLegajo = this.idLegajo;
            idNovedad = novedad.nueva_novedad("Nueva llegada Tarde "+this.fecha,
                                            "Fecha: " + this.fecha + " a las horas: " + this.hora, 1);
            if (idNovedad != 0)
            {
                this.valores = "'" + this.fecha + "','" + this.hora + "',"+this.idLegajo+","+idNovedad;
                return this.insertaSQL();                
            }
            else
            {
                return 0;
            }            
        }
        
        // se modifica la llegada tarde
        public int modifica()
        {
            novedad.idLegajo = this.idLegajo;
            idNovedad = novedad.nueva_novedad("Retificacion de llegada tarde",
                                        "Fecha: "+ this.fecha+ " Horas: "+this.hora, 1);
            this.valores = "'"+this.fecha+"','"+this.hora+"',"+this.idLegajo+","+this.idNovedad;
            this.condicion = "idLlegada="+this.idLlegada;
            return this.modificaSQL();
        }
        
        // Realiza la baja de una llegada tarde
        public int baja()
        {
            novedad.idLegajo = this.idLegajo;            
            idNovedad = novedad.nueva_novedad("Se elimino Llegada tarde", "Registro nro: "+this.idLlegada, 1);
            this.condicion = "idLlegada="+this.idLlegada;
            return this.borraSQL();
        }
    }
    
    // clase licencias
    class Licencias extends Legajolib
    {
        int idLicencia = 0;
        Novedad novedad = this.new Novedad();    
        int idNovedad = 0;
        String motivo = "";
        int cantidad = 0;
        String inicio = "";
        String fin = "";
        int tipoLic=0;
        int estado=0;
        // constructor
        public Licencias()
        {
            this.tabla = "licencia";
            this.campos = "idNovedad,idLegajo,Motivo,cantDias,fechaInicio,fechaFin,tipoLicencia,estado";
        }
        
        //Crea una nueva licencia
        public int alta() 
        {
            idNovedad = novedad.nueva_novedad("Nueva Licencia",
                                this.motivo+" se extiende por "+ this.cantidad+
                                " dias, empesando el "+this.inicio+" y finalizando el"+
                                this.fin+" tipo de licencia: "+this.tipoLic, 1);
            this.valores = idNovedad+","+this.idLegajo+",'"+this.motivo+"',"+
                            this.cantidad+",'"+this.inicio+"','"+this.fin+"',"+
                            this.tipoLic+","+this.estado;
            return this.insertaSQL();
        }
        
        // realiza una consulta sobre licencias
        public ResultSet consulta()
        {
            return this.consultaSQL();
        }
        
        //realiza la modificacion de la licencia
    }
    
    // clases de capacitaciones y cursos
    class Capacitaciones extends Legajolib
    {
        public Capacitaciones()
        {
            this.tabla = "";
            this.campos = "";
        }
    }
    
    // Clase para notificaciones al personal
    class Notificaciones extends Legajolib
    {
        public Notificaciones()
        {
            this.tabla = "";
            this.campos = "";
        }
    }
    
    //Clases que generan contratos laborales
    class Contratos extends Legajolib
    {
        public Contratos()
        {
            this.tabla = "";
            this.campos = "";
        }
    }
    
    //clase para el manejo de horas extras
    class HorasExtras extends Legajolib
    {
        public HorasExtras()
        {
            this.tabla = "";
            this.campos = "";
        }
    }
    
    // clase para el manejo de adelantos
    class Adelantos extends Legajolib
    {
        public Adelantos()
        {
            this.tabla = "";
            this.campos = "";
        }
    }
}
