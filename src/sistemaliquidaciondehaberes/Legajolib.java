/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.Date;

/** * Ariel Marcelo Diaz*/
public class Legajolib extends libSentenciasSQL
{
    //instancias
    int idLegajo;
    int idPersona;
    int estadoL;
    String fecha=FechaActual();
    String hora=HoraActual();
            
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
            int resultado=this.insertaSQL();
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
            this.campos = this.idSindicato+","+this.idLegajo;
            return this.insertaSQL();
        }
        
        //Elimina un sindicato del legajo
        public int baja_sindicato()
        {        
            return this.borraSQL();
        }
    }
    
    //Clase de obra social
    class ObraSocial extends Legajolib 
    {  
        int idObraSocial;
        //constructor
        public ObraSocial()
        {
            this.tabla="obrasociallegajo";
            this.campos="idLegajo,idObraSocial";
        }
        
        public int asigna_obraSocial(int obraSocial)
        {
            this.idObraSocial=obraSocial;
            this.valores = this.idLegajo+","+this.idObraSocial;
            return this.insertaSQL();
        }
        
        public int baja_obrasocial(int obraSocial)
        {
            this.condicion = "idLegajo="+this.idLegajo+" AND idObraSocial="+obraSocial;
            return this.borraSQL();
        }
    }
    
    
}
