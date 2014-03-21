
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;


/** * Ariel Marcelo Diaz****/
public class Concepto extends libSentenciasSQL 
{
    int idFormula = 0;
    String nombre = null;
    float formula =0;
    String inicio = "";
    String fin = "";
    int tipoform = 0;
    int aplicacion = 0;
    int clase = 0;
    // constructor
    public Concepto()
    {
        this.tabla = "formulas";
        this.campos = "nombreForm,formula,inicio,fin,tipoform,claseform,aplicacion";
    }
    
    //realiza la consulta sobre conseptos
    public ResultSet consulta()
    {   
        this.condicion = "idFormula="+this.idFormula;     
        return this.consultaSQL();
    }
    
    //devuelve el valor de una formula
    public float formulas() 
    {        
        ResultSet consul = this.consulta();
        float aux=0;
        try 
        {
            aux = consul.getFloat("formula");
        } 
        catch (SQLException ex)
        {
            estado = ex.getMessage();
            Imprime(estado);            
        }
        return aux;
    }    
    
    //modifica las formulas 
    public int modifica()
    {
        this.valores = "'"+nombre+"',"+formula+",'"+inicio+"','"+fin+"','"+
                        tipoform+","+clase+","+aplicacion;
        return this.modifica();
    }    
    
    // alta en nueva formula
    public int nueva()
    {
        this.valores = "'"+nombre+"',"+formula+",'"+inicio+"','"+fin+"','"+
                        tipoform+","+clase+","+aplicacion;
        return this.insertaSQL();
    }
    
    class Detalle extends libSentenciasSQL 
    {
        int idConcepo = 0;
        String nombreCons = "";
        String detalleCons = "";
        int idFormula = 0;
        int idLicencia = 0;
        // constructor
        public Detalle()
        {
            this.tabla = "conceptosdetalle";
            this.campos = "";
        }
        
        public int nuevo()
        {
            this.valores = "";
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "";
            this.valores = "";
            return this.modificaSQL();
        }
        
        public int baja()
        {
            this.condicion = "";
            return this.borraSQL();
        }
        
        public ResultSet consulta()
        {
            this.condicion = "";
            return this.consultaSQL();
        }
    }   
    
    class Control extends libSentenciasSQL 
    {
        
    }
}
