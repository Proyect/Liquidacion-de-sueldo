/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/** * Ariel Marcelo Diaz*/
public class Empresaslib extends libSentenciasSQL
{
    int idEmpresas=0;
    String tipo = "";
    String clase = "";
    String cuit = "";
    String razonSocial = "";    
    String  cp = "";
    String  barrio = "";
    String  direccion = "";
    String  telefono = "";
    String celular = "";
    String otrosDatos = "";
       
    //constructor
    public Empresaslib()
    {
        this.tabla = "empresas";
        this.campos = "tipo,clase,cuit,razonSocial,cp,barrio,direccion,telefono,"
                     + "celular,otros";
    }
    
     // inserta una nueva persona en la base de datos
    public int nueva()
    {
       this.valores= "'" + this.tipo + "','" + this.clase + "','" + this.cuit+"','"+ this.razonSocial+"','"+
                     this.cp + "','" + this.barrio + "','" + this.direccion + "','" + this.telefono +
                     "','" + this.celular + "','" + this.otrosDatos + "'" ;
       return insertaSQL();
    }
    
    // modifica los datos de una empresa
    public int modificar()
    {
        this.valores= "'" + this.tipo + "','" + this.clase + "','" + this.cuit+"','"+ this.razonSocial+"','"+
                     this.cp + "','" + this.barrio + "','" + this.direccion + "','" + this.telefono +
                     "','" + this.celular + "','" + this.otrosDatos + "'" ;
        this.condicion = "idEmpresa="+idEmpresas;
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
    
   
}
