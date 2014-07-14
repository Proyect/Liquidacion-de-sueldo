
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
    
    
    // crea un nuevo concepto para aplicarlo 
    class Detalle extends libSentenciasSQL 
    {
        int idConcepto = 0;
        String nombreCons = "";
        String detalleCons = "";
        int idFormula = 0;
        int idLicencia = 0;
        int tipo = 0;
        // constructor
        public Detalle()
        {
            this.tabla = "conceptosdetalle";
            this.campos = "nombreCons,detalleCons,idFormula,idLicencia,tipo";
        }
        
        public int nuevo()
        {
            this.valores = "'"+nombreCons+"','"+detalleCons+"',"+idFormula+","+
                            idLicencia+","+tipo;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idConcepto="+idConcepto;
            this.valores =  "'"+nombreCons+"','"+detalleCons+"',"+idFormula+","+
                            idLicencia+","+tipo;
            return this.modificaSQL();
        }
        
                
        public ResultSet consulta()
        {
            this.condicion = "idConcepto="+idConcepto;
            return this.consultaSQL();
        }
    }   
    
    // aplica los conceptos a cada recibo de sueldo
    class Aplica extends Concepto
    {
        int idRecibo = 0;
        int idConcepto = 0;
        float valor = 0;
        float unidad = 0;
        int tipo =0;
        Concepto.Detalle det = new Detalle();
        
        //constructor
        public Aplica()
        {
            this.tabla = "conceptos";
            this.campos = "idRecibo,idConcepto,valor,unidad,tipo";
        }
        
        public int nuevo() 
        {
            det.idConcepto = this.idConcepto;    
            ResultSet form=det.consulta();       
            Concepto valform = new Concepto();
            try 
            {
                valform.idFormula = form.getInt("idFormula");
                ResultSet resultado = valform.consulta(); //aqui me quede
                if (resultado.isFirst())
                {
                    resultado.getString("fin");
                }
                else
                {
                    Imprime("No se produjeron resultados viables");
                }
            } 
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
            }
            this.valor= valform.formulas();
            this.tipo = det.tipo;
            this.valores = idRecibo+","+idConcepto+","+valor+","+unidad+","+tipo;
            return this.insertaSQL();
        }
        
        @Override
        public int modifica()
        {
            this.condicion = "idRecibo="+idRecibo+" AND idConcepto="+idConcepto;
            this.valores = idRecibo+","+idConcepto+","+valor+","+unidad+","+tipo;
            return this.modificaSQL();
        }
        
        public int baja()
        {
            this.condicion = "idRecibo="+idRecibo+" AND idConcepto="+idConcepto;
            return this.borraSQL();
        }
        
       
        @Override
        public ResultSet consulta()
        {
            this.condicion = "idRecibo="+idRecibo+" AND idConcepto="+idConcepto;
            return this.consultaSQL();
        }
        
        //muestra todas las consultas del recibo
        public ResultSet consultaRecibo()
        {
            this.condicion = "idRecibo="+idRecibo;
            return this.consultaSQL();
        }
    }
    
    //aplica los conceptos predeterminados de cada uno de los legajos
    class Control extends libSentenciasSQL    
    {
        int idLegajo=0;
        int idConcepto=0;
        float unidades = 0;
        int estadoConcepto = 1;
        public Control()
        {
            this.tabla = "legajoconcepto";
            this.campos = "idLegajo,idConcepto,unidades,estado";
        }
        
        public int nuevo()
        {
            this.valores = idLegajo+","+idConcepto+","+unidades+","+estadoConcepto;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idLegajo="+idLegajo+" AND idConcepto="+idConcepto;
            this.valores = idLegajo+","+idConcepto+","+unidades+","+estadoConcepto;
            return this.modificaSQL();
        }
        
        public int baja()
        {
            this.condicion = "idLegajo="+idLegajo+" AND idConcepto="+idConcepto;
            return this.borraSQL();
        }
        
        public ResultSet consulta()
        {
            this.condicion = "idLegajo="+idLegajo;
            return this.consultaSQL();
        }
    }
}
