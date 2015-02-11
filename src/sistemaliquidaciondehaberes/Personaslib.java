

package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Personaslib extends libSentenciasSQL
{
    public int idPersona =0;
    public String  apellido = "";
    public String nombre = "";
    public String telefono = "";
    public String telefono2 = "";
    public String celular = "";
    public int idProvincia = 0;
    public String cp= "";
    public String barrio = "";
    public String calle = "";
    public String nro= "";
    public String dto = "";
    public String piso = "";
    public String tipoDoc = "";
    public String nroDoc = "";
    public String cuil = "";
    public String estadoCivil = "";
    public String fechaNac = "";
    public String nacionalidad = "";
    public int sexo=1;
    public String mail = "";
    public String mail2 = "";
    public String otrosDatos = "";
    public int estudios =2;
    
    //constructor
    public Personaslib()
    {
        this.tabla="persona";
        this.campos="apellido,nombre,telefono,telefono2,celular,idProvincia,cp,"
                    + "barrio,calle,nro,dto,piso,tipoDoc,nroDoc,cuil,estadoCivil"
                    + ",fechaNac,nacionalidad,sexo,mail,mail2,otrosDatos,estudios";
    }       
    
    // inserta una nueva persona en la base de datos
    public void nueva()
    {       
       this.valores= "'"+apellido+"','"+nombre+"','"+telefono+"','"+telefono2+
                     "','"+celular+"',"+idProvincia+",'"+cp+"','"+barrio+ "','"+
                     calle+"','"+nro+"','"+dto+"','"+piso+"','"+tipoDoc+"','"+
                     nroDoc+"','"+cuil+"','"+estadoCivil+"','"+fechaNac+"','"+
                     nacionalidad+"',"+sexo+",'"+mail+"','"+mail2+"','"+
                     otrosDatos+"',"+estudios;
       this.condicion="cuil='"+cuil+"'";
       ResultSet consulta = this.consultaSQL();
        try 
        {
            if (consulta.first())
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
        this.valores= "'"+apellido+"','"+nombre+"','"+telefono+"','"+telefono2+
                     "','"+celular+"',"+idProvincia+",'"+cp+"','"+barrio+ "','"+
                     calle+"','"+nro+"','"+dto+"','"+piso+"','"+tipoDoc+"','"+
                     nroDoc+"','"+cuil+"','"+estadoCivil+"','"+fechaNac+"','"+
                     nacionalidad+"',"+sexo+",'"+mail+"','"+mail2+"','"+
                     otrosDatos+"',"+estudios;
         return modificaSQL();
    }
    
    //consulta los datos de una persona
    public ResultSet consulta()
    { 
        this.condicion = "idpersona="+idPersona;
        return consultaSQL();
    }
}
