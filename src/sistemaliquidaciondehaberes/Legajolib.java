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
    int idLegajo;
    int idPersona;
    int estadoL;
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
        this.idPersona=persona;
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
    
    class Asignaciones extends Legajolib 
    {   
        int idVinculo = 0;
        Novedad novedad = this.new Novedad();
        // constructores
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
            return this.insertaSQL();
        }
        
        //realiza la baja del vinculo
        public int baja()
        {
            this.condicion = "idPersona=" + this.idPersona +" AND legajo=" + this.idLegajo;
            return this.borraSQL();
        }
    }
}
