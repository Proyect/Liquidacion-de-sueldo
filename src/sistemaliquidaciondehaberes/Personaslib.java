/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/** * Ariel Marcelo Diaz*/
public class Personaslib extends libSentenciasSQL
{
    int idPersona =0;
    String  apellido = "";
    String nombre = "";
    String telefono = "";
    String telefono2 = "";
    String celular = "";
    String barrio = "";
    String direccion = "";
    String tipoDoc = "";
    String nroDoc = "";
    String cuil = "";
    String estadoCivil = "";
    String fechaNac = "";
    String mail = "";
    String mail2 = "";
    String otrosDatos = "";
    String nacionalidad = "";
    
    //constructor
    public Personaslib()
    {
        this.tabla="persona";
        this.campos="apellido,nombre,telefono,telefono2,celular,barrio,direccion,"+
                    "tipoDoc,nroDoc,cuil,estadoCivil,fechaNac,mail,mail2,otrosDatos,nacionalidad";
    }       
    
    // inserta una nueva persona en la base de datos
    public int nueva()
    {       
       this.valores= "'"+apellido+"','"+nombre+"','"+telefono+"','"+telefono2+"','"+
                        celular+"','"+barrio+ "','"+direccion+"','"+tipoDoc+"','"+nroDoc+
                        "','"+cuil+"','"+estadoCivil+"','"+fechaNac+"','"+mail+
                        "','"+mail2+"','"+otrosDatos+"','"+nacionalidad+"'";
       return insertaSQL();
    }
    
    // modifica los datos de una persona
    public int modificar()
    {
        this.condicion = "idpersona="+idPersona;
        this.valores= "'"+apellido+"','"+nombre+"','"+telefono+"','"+telefono2+"','"+
                        celular+"','"+barrio+ "','"+direccion+"','"+tipoDoc+"','"+nroDoc+
                        "','"+cuil+"','"+estadoCivil+"','"+fechaNac+"','"+mail+
                        "','"+mail2+"','"+otrosDatos+"','"+nacionalidad+"'";
         return modificaSQL();
    }
    
    //consulta los datos de una persona
    public ResultSet consulta()
    { 
        this.condicion = "idpersona="+idPersona;
        return consultaSQL();
    }
}
