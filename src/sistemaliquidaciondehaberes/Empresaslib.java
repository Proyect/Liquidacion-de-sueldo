/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/** * Ariel Marcelo Diaz*/
public class Empresaslib extends libSentenciasSQL
{
    String matricula = "";
    String razonSocial = "";
    String cuit = "";
    String  cp = "";
       int  barrio = 0;
    String  direccion = "";
    String  telefono = "";
    String celular = "";
    String otrosDatos = "";
       int estadoEmp = 0;
    //constructor
    public Empresaslib()
    {
        this.campos = "matricula,razonSocial,cuit,cp,barrio,direccion,telefono,celular,otrosDatos,estado";
    }
    
     // inserta una nueva persona en la base de datos
    public int nueva()
    {
       this.valores= "'" + this.matricula + "','" + this.razonSocial+"','" + this.cuit+"','"+
                     this.cp + "'," + this.barrio + ",'" + this.direccion + "','" + this.telefono +
                     "','" + this.celular + "','" + this.otrosDatos + "'," + this.estadoEmp;
       return insertaSQL();
    }
    
    // modifica los datos de una persona
    public int modificar(String condiciones)
    {
        this.valores= "'" + this.matricula + "','" + this.razonSocial+"','" + this.cuit+"','"+
                     this.cp + "'," + this.barrio + ",'" + this.direccion + "','" + this.telefono +
                     "','" + this.celular + "','" + this.otrosDatos + "'," + this.estadoEmp;
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
    
    // elimina una empresa
    public int borra(String condicion)
    {
        this.condicion = condicion;
        return this.borraSQL();
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
