/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/** * Ariel Marcelo Diaz*/
public class Empresaslib extends libSentenciasSQL{
    
    //constructor
    public Empresaslib()
    {
        this.campos="";
    }
    
     // inserta una nueva persona en la base de datos
    public int nueva(String datos)
    {
       this.valores= datos;
       return insertaSQL();
    }
    
    // modifica los datos de una persona
    public int modificar(String condiciones)
    {
        this.condicion=condiciones;
         return modificaSQL();
    }
    
    //consulta los datos de una persona
    public ResultSet consulta(String condiciones)
    {
        this.condicion=condiciones;
        ResultSet resultado=consultaSQL();
        return resultado;
    }
    
    // clase de obra social
    class Obra_social extends Empresaslib
    {
        //construcctor
        public Obra_social()
        {
            this.tabla = "obrasocial";
        }
    }
    
    //clase de sindicato
    class Sindicato extends Empresaslib
    {
        //construcctor
        public Sindicato()
        {
            this.tabla = "sindicato";
        }
    }
    
    //clase de instituciones
    class Instituciones extends Empresaslib
    {
        //construcctor
        public Instituciones()
        {
            this.tabla = "institucion";
        }
    }
}
