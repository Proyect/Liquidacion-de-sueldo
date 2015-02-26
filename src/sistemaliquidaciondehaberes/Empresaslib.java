package sistemaliquidaciondehaberes;
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
import java.sql.ResultSet;

public class Empresaslib extends libSentenciasSQL
{
    public int idEmpresas=0;
    public String tipo = "";
    public String clase = "";
    public String cuit = "";
    public String razonSocial = "";    
    public String  cp = "";
    public String  barrio = "";
    public String  direccion = "";
    public String  telefono = "";
    public String celular = "";
    public String otrosDatos = "";
       
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
