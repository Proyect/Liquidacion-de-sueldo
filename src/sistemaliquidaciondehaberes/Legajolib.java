/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;


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
    
    //clase novedad
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
    
    //clase para las cargas sociales: obrasocial, sindicato, art    
    class CargasSociales extends Legajolib
    {   //instancias
        int idEmpresa = 0;
        String vinculacion = "";
        public CargasSociales()
        {
            this.tabla = "vinculacioneslegajo";
            this.campos = "idLegajo,idEmpresa,Vinculacion";
        }
        
        public int nuevo()
        {
            this.valores = idLegajo+","+idEmpresa+",'"+vinculacion+"'";
            return this.insertaSQL();
        }
        
        public int modifica(String condiciones)
        {
            this.condicion = condiciones;
            this.valores = idLegajo+","+idEmpresa+",'"+vinculacion+"'";
            return this.modificaSQL();
        }
        
        public int baja(String condiciones)
        {
            this.condicion = condiciones;
            return this.borraSQL();
        }
        
        public ResultSet consulta(String condiciones)
        {
            this.condicion = condiciones;
            return this.consultaSQL();
        }
    }
    
    // operaciones con las asignaciones   
    class Asignaciones extends Legajolib 
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
        
        // realiza la consulta de las asignaciones por legajo
        public ResultSet consulta()
        {
            this.condicion = "legajo="+this.idLegajo;
            return this.consultaSQL();
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
        public int modifica()
        {
            novedad.idLegajo = this.idLegajo;         
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
        
        // realiza una consulta de las inasistencias
        public ResultSet consulta(String condicion)
        {
            this.condicion = condicion;
            return this.consultaSQL();
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
        int estadoLic=1;
        float pago = 0;
        // constructor
        public Licencias()
        {
            this.tabla = "licencia";
            this.campos = "idNovedad,idLegajo,Motivo,cantDias,fechaInicio,fechaFin,tipoLicencia,estado,pago";
        }
        
        //Crea una nueva licencia
        public int alta() //mejorar
        {
            novedad.idLegajo = this.idLegajo;
            idNovedad = novedad.nueva_novedad("Nueva Licencia",
                                this.motivo+" se extiende por "+ this.cantidad+
                                " dias, empesando el "+this.inicio+" y finalizando el"+
                                this.fin+" tipo de licencia: "+this.tipoLic, 1);
            
            this.valores = idNovedad+","+this.idLegajo+",'"+this.motivo+"',"+
                            this.cantidad+",'"+this.inicio+"','"+this.fin+"',"+
                            this.tipoLic+","+this.estadoLic+","+this.pago;
            
            if (this.insertaSQL() ==1)
            {
                Inasistencia inasist= new Inasistencia();
                inasist.condicion = "fecha>='"+this.inicio+"' AND fecha<='"+this.fin+"'";
                if (inasist.consulta(inasist.condicion) != null)
                {
                    inasist.campos = "justificada";
                    inasist.valores="1";
                    inasist.modificaSQL();
                } 
                return 1;
            }
            else
            {
                Imprime("Licencia no cargada");
                return 0;
            }
        }
        
        // realiza una consulta sobre licencias
        public ResultSet consulta()
        {
            return this.consultaSQL();
        }
        
        //realiza la modificacion de la licencia
        public int modifica()
        {
            idNovedad = novedad.nueva_novedad("Modificacion en la licencia",
                                this.motivo+" se extiende por "+ this.cantidad+
                                " dias, empesando el "+this.inicio+" y finalizando el"+
                                this.fin+" tipo de licencia: "+this.tipoLic+
                                "estado de la licencia: "+this.estadoLic, 1);
            
           this.valores = idNovedad+","+this.idLegajo+",'"+this.motivo+"',"+
                            this.cantidad+",'"+this.inicio+"','"+this.fin+"',"+
                            this.tipoLic+","+this.estadoLic+","+this.pago; 
           
           this.condicion = "idLicencia="+this.idLicencia;
           
           return this.modificaSQL();
        }
        
        //realiza la baja de una licencia
        public int baja()
        {
            idNovedad = novedad.nueva_novedad("Baja de licencia", "Licencia nro:"+this.idLicencia, 1);
            this.condicion = "idLicencia="+this.idLicencia;
            return this.borraSQL();
        }
    }  
        

    // clases de capacitaciones y cursos
    class Capacitaciones extends Legajolib
    {
        int idCapacitacion = 0;
        int idNovedad = 0;
        Novedad novedad = this.new Novedad(); 
        //constructor
        public Capacitaciones()
        {
            this.tabla = "empl_capacitaciones";
            this.campos = "idCapacitacion,idLegajo";
        }
        
        //realiza el alta de una capacitacion
        public int alta() //falta agregar a novedades
        {
            this.valores =  this.idCapacitacion+ ","+this.idLegajo;
            if(this.insertaSQL()==1)
            {
                novedad.idLegajo = this.idLegajo;
                idNovedad = novedad.nueva_novedad("Nueva capacitacion", 
                            "capacitacion nro:"+this.idCapacitacion, 1); 
                return 1;
            }
            else
            {
                return 0;
            }
        }
        
        //realiza la baja de una capacitacion
        public int baja()
        {
            this.condicion = "idCapacitacion="+this.idCapacitacion+" AND idLegajo="+this.idLegajo;
            if(this.borraSQL()== 1)
            {
                novedad.idLegajo = this.idLegajo;
                idNovedad=novedad.nueva_novedad("Retificacion de capaciaion", 
                            "capacitacion de baja nro:"+this.idCapacitacion, 1);
                  return 1;
            }
            else
            {
                return 0;
            }
        }
        
        public ResultSet consulta()
        {
            return this.consultaSQL();
        }
        
    }
    
    // clase de titulos adquiridos
    class Titulos extends Legajolib
    {        
        int idTitulo= 0;
        int estadoTi= 0;
        public Titulos()
        {
            this.tabla = "legajoTitulo";
            this.campos = "idLegajo,idTitulo,estado";
        }
        
        public int nuevo()
        {
            this.valores = idLegajo+","+idTitulo+","+estadoTi;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idLegajo="+this.idLegajo+" AND idTitulo="+this.idTitulo;
            this.valores = idLegajo+","+idTitulo+","+estadoTi;
            return this.modificaSQL();
        }
        
        public int baja()
        {
            this.condicion = "idLegajo="+this.idLegajo+" AND idTitulo="+this.idTitulo;
            return this.borraSQL();
        }
        
        public ResultSet consulta()
        {
            this.condicion = "idLegajo="+this.idLegajo+" AND idTitulo="+this.idTitulo;
            return this.consultaSQL();
        }
    }
    
    // Clase para notificaciones al personal
    class Notificaciones extends Legajolib
    {
        int idComunicado=0;
        int idTipoNot=0;
        String detalle="";
        Novedad novedad = this.new Novedad();
        int idNovedad=0;
        //constructor
        public Notificaciones()
        {            
            this.tabla = "comunicados";
            this.campos = "idNovedades,idTipo,detalle";
        }
        
        //genera un nuevo comunicado
        public int alta()        
        {   novedad.idLegajo = this.idLegajo;         
            idNovedad = novedad.nueva_novedad("Nueva Notificacion", 
                                "Novedad nro: "+idNovedad+" Tipo: "+idTipoNot+
                                " detalles: " +detalle, 1);
            
            if(idNovedad != 0)
            {
                this.valores = this.idNovedad+","+this.idTipoNot+",'"+this.detalle+"'";
                return this.insertaSQL();
            }
            else
            {
                return 0;
            }
        }
        
        //Modifica un comunicado
        public int modifica()
        {
            this.valores = this.idNovedad+","+this.idTipoNot+",'"+this.detalle+"'";
            this.condicion = "idComunicado="+this.idComunicado;
            return this.modificaSQL();
        }
        
        //realiza una consulta de un comunicado
        public ResultSet consulta(String condicion)
        {
            this.condicion = condicion;
            return this.consultaSQL();
        }
        
        //realiza una baja del comunicado
        public int baja()
        {            
            this.condicion = "idComunicado="+this.idComunicado;
            return this.borraSQL();
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
        int cantidadHs = 0;
        int tipoHs = 0;
        Novedad novedad = this.new Novedad();        
        int idNovedad=0;
        //constructor
        public HorasExtras()
        {
            this.tabla = "hsextra";
            this.campos = "idLegajo,idNovedad,fecha,cantidadHs,tipoHs";
        }
        
        //ingresa una nueva hora extra al legajo
        public int nueva()
        {  
            novedad.idLegajo = this.idLegajo;         
            idNovedad = novedad.nueva_novedad("", tabla, tipoHs);
            if (idNovedad != 0)
            {
                this.valores = this.idLegajo+","+this.idNovedad+",'"+this.fecha+"',"+
                           this.cantidadHs+","+this.tipoHs;
                return this.insertaSQL();
            }
            else
            {
                return 0;
            }
            
        }
        
        //modifica las horas extras
        public int modifica() // optimizar
        {
            this.condicion = "idLegajo="+this.idLegajo+" AND idNovedad="+this.idNovedad;
            idNovedad=novedad.nueva_novedad("Modificacion de horas extras", this.valores, 1);
            if(idNovedad !=0)
            {
                return this.modificaSQL();
            }
            else
            {
                return 0;
            }
        }
        
        // realiza una consulta de las horas extras
        public ResultSet consulta()
        {
            return this.consultaSQL();
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
    
    //clase para la asignacion y modificacion de los puestos de la empresa
    class Puestos extends Legajolib
    {
        int idPuesto=0;
        Novedad novedad = this.new Novedad();        
        int idNovedad=0;
        String fechaInicio = "";
        String fechaFin = "";
        int estadoP=1;
        // constructor
        public Puestos()
        {
            this.tabla = "puestolegajo";
            this.campos = "idLegajo,idNovedad,fechaInicio,fechaFin,estado";
        }       
        
        // genera un nuevo puesto laboral
        public int nuevo()
        {
            idNovedad = novedad.nueva_novedad("Nuevo puesto Laboral",
                        "Puesto nro:"+this.idPuesto, 1);
            if (idNovedad !=0)
            {
                this.valores = this.idLegajo+","+this.idNovedad + ",'"+ 
                            this.fechaInicio+"','"+this.fechaFin+"',"+this.estadoP;
                return this.insertaSQL();
            }
            else
            {
                return 0;
            }           
        }
        
        // realiza una consulta
        public ResultSet consulta()
        {
            return this.consultaSQL();
        }
        
        // modifica la relacion del puesto laboral
        public int modifica()
        {
            this.valores=this.valores = this.idLegajo+","+this.idNovedad + ",'"+ 
                            this.fechaInicio+"','"+this.fechaFin+"',"+this.estadoP;
            return this.modificaSQL();
        }
    }     
    
}
