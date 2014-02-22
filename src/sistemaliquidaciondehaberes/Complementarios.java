/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/** * Ariel Marcelo Diaz****/
public class Complementarios 
{   
    // Realiza la creacion y modificacion de los puestos de la empresa
    class Cargos  extends libSentenciasSQL
    {
        int idPuesto=0;
        String nombrePuesto = "";
        String funcionPuesto = "";
        float basico = 0;
        String horario = "";
        int hsSemanales = 0;
        float costoHs=0;
        float costoHs50=0;
        float costoHs100=0;
        //constructor
        public Cargos()
        {
            this.tabla = "puesto";
            this.campos = "nombrePuesto,funcionPuesto,basico,horario,hsSemanales,costoHs,costoHs50,costoHs100";
        }
        
        //crea un nuevo puesto en la empresa
        public int nuevo()
        {
            this.valores = "'"+nombrePuesto+"','"+funcionPuesto+"',"+basico+",'"+
                           horario+"',"+hsSemanales+","+costoHs+","+costoHs50
                            +","+costoHs100+",";
            return this.insertaSQL();
        }
        
        // realiza la consulta de un cargo
        public ResultSet consulta()
        {
            this.condicion = "idPuesto="+this.idPuesto;
            return this.consultaSQL();
        }
        
        //realiza la modificacion de un cargo de una empresa
        public  int modifica()
        {
            this.valores = "'"+nombrePuesto+"','"+funcionPuesto+"',"+basico+",'"+
                           horario+"',"+hsSemanales+","+costoHs+","+costoHs50
                            +","+costoHs100+",";
            return this.modificaSQL();
        }
    }
    
    // Esta clase crea, modifica y elimina las capacitaciones para el presonal
    class Capacitaciones extends libSentenciasSQL
    {
        //contructor
        public Capacitaciones()
        {
            this.tabla = "";
            this.campos = "";
        }
    }    
}
