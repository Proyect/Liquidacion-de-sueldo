
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/** * Ariel Marcelo Diaz****/
public class Concepto extends libSentenciasSQL 
{ 
    
    // crea un nuevo concepto para aplicarlo 
    class Detalle extends libSentenciasSQL 
    {
        int idConcepto = 0;
        String nombreCons = "";
        String detalleCons = "";
        int idFormula = 0;
        int idLicencia = 0;
        int tipo = 0;
        String inicio = "";
        String fin = "";
        int idRecibo=0; //para la ultima funcion
        float formula =0;
        String formula2 = null;
        int tipoform = 0;
        int aplicacion = 0;
        int clase = 0;
        
        // constructor
        public Detalle()
        {
            this.tabla = "conceptosdetalle";
            this.campos = "nombreCons,detalleCons,idFormula,idLicencia,tipo,"
                            + "inicio,fin,formula,formula2,tipoForm,claseForm,"
                            + "aplicacion";
        }
        
        public int nuevo()
        {
            this.valores = "'"+nombreCons+"','"+detalleCons+"',"+idFormula+","+
                            idLicencia+","+tipo+",'"+inicio+"','"+fin+"',"+
                            formula+",'"+formula2+"',"+tipoform+","+aplicacion+
                            ","+clase;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idConcepto="+idConcepto;
            this.valores =  "'"+nombreCons+"','"+detalleCons+"',"+idFormula+","+
                            idLicencia+","+tipo+",'"+inicio+"','"+fin+"',"+
                            formula+",'"+formula2+"',"+tipoform+","+aplicacion+
                            ","+clase;
            return this.modificaSQL();
        }
        
                
        public ResultSet consulta()
        {
            this.condicion = "idConcepto="+idConcepto;
            return this.consultaSQL();
        }
        
        //devuelve los conceptos no relacionados con el recibo
        public ResultSet noEnRecibos()
        {          
            this.condicion = "idConcepto NOT IN ("
                                    + "SELECT "
                                     + "idConcepto "
                                    + "FROM conceptos "
                                    + "WHERE idRecibo="+idRecibo+")";
            return this.consultaSQL();
        }
        
        //devuelve el valor de una formula
        public float formulas() // sin terminar
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
    }   
    
    // aplica los conceptos a cada recibo de sueldo
    class Aplica extends Concepto 
    {
        int idRecibo = 0;
        int idConcepto = 0;        
        float unidad = 1;
        String formula = "";
        float remunerativo = 0;
        float noremunerativo = 0;
        float descuentos = 0;
        Concepto.Detalle det = new Detalle();
        Liquidacion liq = new Liquidacion();
         
        //constructor
        public Aplica()
        {
            this.tabla = "conceptos";
            this.campos = "idRecibo,idConcepto,unidad,formula,remunerativo,"
                            + "noremunerativo,descuento";
        }
        
        //crea un nuevo concepto
        public int nuevo() //sin terminar
        {
            det.idConcepto = this.idConcepto;    
            ResultSet form=det.consulta();       
            Concepto valform = new Concepto();
            try 
            {
                valform.idFormula = form.getInt("idFormula");
                ResultSet resultado = valform.consulta(); 
                if(resultado.getInt("claseform")==1 )
                {
                    ResultSet resultado2 = null;
                    switch(resultado.getInt("tipoform"))
                    {
                        //aplicada al basico
                        case 1:
                            liq.idRecibo=idRecibo;
                            resultado2 = liq.consultarecibo();
                            this.valor = resultado.getFloat("formula")*
                                        resultado2.getFloat("basico");//aqui me quede
                           // this.formula = "BasicoProporcional*"+resultado.getFloat("formula");
                        break;
                        
                        //aplicada a conceptos remunerativos
                        case 2:
                            liq.idRecibo=idRecibo;
                            this.valor = resultado.getFloat("formula")*
                                            liq.totalRecibo(1);
                        break;
                            
                        //aplicada a conceptos no remunerativos
                        case 3:
                            liq.idRecibo=idRecibo;
                            this.valor = resultado.getFloat("formula")*
                                            liq.totalRecibo(2);
                        break;                        
                        
                        case 4:// esta parte es para el evaluador de wilson
                        break;
                    }
                }
                else
                {
                    this.valor = resultado.getFloat("formula");
                }
                this.tipo = form.getInt("tipo");
                liq.campos = "totalRemunerativo,totalNoRemunerativo,"+
                                "totalDescuento,total";
                liq.idRecibo= this.idRecibo;
                resultado =liq.consultarecibo();
                float rem = resultado.getFloat("totalRemunerativo");
                float noRem = resultado.getFloat("totalNoRemunerativo");
                float desc = resultado.getFloat("totalDescuento");
                float total=0;
                switch(this.tipo) 
                {
                    case 1:
                        rem += this.valor;
                        total += rem;
                    break;
                        
                    case 2:
                        noRem += this.valor;
                        total +=this.valor;
                    break;
                        
                    case 3:
                        desc += this.valor;
                        total -= desc;
                    break;
                }
                liq.valores = rem+","+noRem+","+desc+","+total;
            } 
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
                Imprime(estado);
            }            
            
            this.valores = idRecibo+","+idConcepto+","+unidad+",'"+formula+"',"
                            +remunerativo+","+noremunerativo+","+descuentos;
            if(this.insertaSQL()==1)
            {
                liq.modificaSQL();
                return 1;
            }
            else
            {
                return 0; //no se pudo insertar los datos;
            }
        }
        
      
        public int modifica()
        {
            Liquidacion total = new Liquidacion();
            total.idRecibo = idRecibo;
            
            this.condicion = "idRecibo="+idRecibo+" AND idConcepto="+idConcepto;
            ResultSet resultado = this.consulta();
            try 
            {
                float val = resultado.getFloat("valor");
                int tip = resultado.getInt("tipo");
                this.valores = idRecibo+","+idConcepto+","+unidad+",'"+formula+"',"
                            +remunerativo+","+noremunerativo+","+descuentos;
                this.modificaSQL();    
                if (val != valor)
                {
                    total.totalRecibo(tipo);
                }
                return 1;
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
                return 0;
            }          
             
        }
        
        public int baja()
        {
            this.condicion = "idRecibo="+idRecibo+" AND idConcepto="+idConcepto;
            return this.borraSQL();
        }
        
       
        
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
        int tipo = 0;
        String inicio = "";
        String fin = "";
        int estadoConcepto = 1;
        public Control()
        {
            this.tabla = "legajoconcepto";
            this.campos = "idLegajo,idConcepto,unidades,tipo,inicio,fin,estado";
        }
        
        public int nuevo()
        {
            this.valores = idLegajo+","+idConcepto+","+unidades+","+tipo+","+
                            ",'"+inicio+"','"+fin+"',"+estadoConcepto;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idLegajo="+idLegajo+" AND idConcepto="+idConcepto;
            this.valores = idLegajo+","+idConcepto+","+unidades+","+tipo+","+
                            ",'"+inicio+"','"+fin+"',"+estadoConcepto;
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
