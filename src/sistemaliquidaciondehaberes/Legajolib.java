/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.Date;

/** * Ariel Marcelo Diaz*/
public class Legajolib extends libSentenciasSQL{
    //instancias
    int idLegajo=0;
    String fecha=FechaActual();
    String hora=HoraActual();
            
    //constructor
    public Legajolib()
    {
        this.tabla="novedad";
    }
    
    class Novedad extends Legajolib{
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
}
