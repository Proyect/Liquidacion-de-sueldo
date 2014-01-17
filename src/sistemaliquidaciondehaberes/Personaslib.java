/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/** * Ariel Marcelo Diaz*/
public class Personaslib extends libSentenciasSQL{

    public Personaslib()
    {
        this.tabla="persona";
        this.campos="apellido,nombre,telefono,telefono2,celular,idBarrio,direccion,tipoDoc,nroDoc,estadoCivil,fechaNac,mail,mail2,otrosDatos,nacionalidad";
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
        return consultaSQL();
    }
}
