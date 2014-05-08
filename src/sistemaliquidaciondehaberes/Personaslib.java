/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** * Ariel Marcelo Diaz*/
public class Personaslib extends libSentenciasSQL
{
    int idPersona =0;
    String  apellido = "";
    String nombre = "";
    String telefono = "";
    String telefono2 = "";
    String celular = "";
    int idProvincia = 0;
    String cp= "";
    String barrio = "";
    String direccion = "";
    String tipoDoc = "";
    String nroDoc = "";
    String cuil = "";
    String estadoCivil = "";
    String fechaNac = "";
    String nacionalidad = "";
    int sexo=1;
    String mail = "";
    String mail2 = "";
    String otrosDatos = "";
    
    
    //constructor
    public Personaslib()
    {
        this.tabla="persona";
        this.campos="apellido,nombre,telefono,telefono2,celular,idProvincia,cp,barrio,direccion,"+
                    "tipoDoc,nroDoc,cuil,estadoCivil,fechaNac,nacionalidad,sexo,mail,mail2,otrosDatos";
    }       
    
    // inserta una nueva persona en la base de datos
    public void nueva()
    {       
       this.valores= "'"+apellido+"','"+nombre+"','"+telefono+"','"+telefono2+"','"+
                        celular+"',"+idProvincia+","+cp+",'"+barrio+ "','"+direccion+"','"+tipoDoc+"','"+nroDoc+
                        "','"+cuil+"','"+estadoCivil+"','"+fechaNac+"','"+nacionalidad+"',"+sexo+",'"+mail+
                        "','"+mail2+"','"+otrosDatos+"'";
       this.condicion="cuil='"+cuil+"'";
       ResultSet consulta = this.consultaSQL();
        try 
        {
            if (consulta.next())
            {
                this.modificaSQL();
            }
            else
            {
                this.insertaSQL();
            }
        }
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
           // Imprime(estado);
        }
    }
    
    // modifica los datos de una persona
    public int modificar()
    {
        this.condicion = "idpersona="+idPersona;
        this.valores= "'"+apellido+"','"+nombre+"','"+telefono+"','"+telefono2+"','"+
                        celular+"',"+idProvincia+","+cp+",'"+barrio+ "','"+direccion+"','"+tipoDoc+"','"+nroDoc+
                        "','"+cuil+"','"+estadoCivil+"','"+fechaNac+"','"+nacionalidad+"',"+sexo+",'"+mail+
                        "','"+mail2+"','"+otrosDatos+"'";
         return modificaSQL();
    }
    
    //consulta los datos de una persona
    public ResultSet consulta()
    { 
        this.condicion = "idpersona="+idPersona;
        return consultaSQL();
    }
}
