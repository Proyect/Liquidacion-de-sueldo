package sistemaliquidaciondehaberes;
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Concepto extends libSentenciasSQL 
{   
    // crea un nuevo concepto para aplicarlo 
    public class Detalle extends libSentenciasSQL 
    {
        public int idConcepto = 0;
        public String nombreCons = "";
        public String detalleCons = "";       
        public int tipo = 0;        
        public int idRecibo=0; //para la ultima funcion
        public float formula =0;
        public String formula2 = null;
        public int tipoform = 2; 
        public int claseForm=0;
        public int aplicacion=0;
        // constructor
        public Detalle()
        {
            this.tabla = "conceptosdetalle";
            this.campos = "nombreCons,detalleCons,tipo,formula,formula2,"
                          + "tipoForm,claseForm,aplicacion";                            
        }
        
        public int nuevo()
        {
            this.valores = "'"+nombreCons+"','"+detalleCons+"',"+tipo+","+
                            formula+",'"+formula2+"',"+tipoform+","+claseForm
                            +","+aplicacion;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idConcepto="+idConcepto;
            this.valores =  "'"+nombreCons+"','"+detalleCons+"',"+tipo+","+
                            formula+",'"+formula2+"',"+tipoform+","+claseForm
                            +","+aplicacion;
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
    }   
    
    // aplica los conceptos a cada recibo de sueldo
    public class Aplica extends Concepto 
    {
        public int idRecibo = 0;
        public int idConcepto = 0;        
        public float unidad = 1;
        public String formula = "";
        public float remunerativo = 0;
        public float noremunerativo = 0;
        public float descuentos = 0;
        private Concepto.Detalle det = new Detalle();       
         
        //constructor
        public Aplica()
        {
            this.tabla = "conceptos";
            this.campos = "idRecibo,idConcepto,unidad,formula,remunerativo,"
                            + "noremunerativo,descuento";
        }
        
        //crea un nuevo concepto
        public int nuevo(Liquidacion liq) //sin terminar
        {
            det.idConcepto = this.idConcepto;    
            ResultSet form=det.consulta();       
            ResultSet resultado2 = null;
            Date inicio=null;
            Date fin = null;
            //Imprime("comienza a ejecutar el concepto");
            try 
            {                 
                if(form.getInt("claseform")==1 )
                {//formula
                    
                    switch(form.getInt("tipo"))
                    {                       
                        case 1://remunerativo                            
                            switch(form.getInt("tipoform"))
                            {
                                case 1: //pre-establecida
                                    this.remunerativo = form.getFloat("formula");
                                    this.formula = form.getString("formula2");
                                break;
                                   
                                case 2: //aplicar formula
                                    this.formula = form.getString("formula2");
                                    evaluador evalu = new evaluador();                           
                                    evalu.exp = formula; 
                                    this.remunerativo = evalu.ejecutar(liq);
                                break;                      
                            }                            
                        break;
                        
                        //no remunerativo
                        case 2:
                            switch(form.getInt("tipoForm")) 
                            {   //pre-establecida
                                case 1:
                                    this.noremunerativo = form.getFloat("formula");
                                    this.formula = form.getString("formula2");
                                break;
                                    
                                case 2://aplicar la formula                             
                                    this.formula = form.getString("formula2");
                                    evaluador evalu = new evaluador();                           
                                    evalu.exp = formula;
                                    this.noremunerativo = evalu.ejecutar(liq);
                                break;
                            }                         
                        break;                                       
                        
                        case 3:// descuento
                            switch(form.getInt("tipoForm"))
                            {   //pre-establecida
                                case 1:
                                    this.descuentos = +form.getFloat("formula");
                                    this.formula = form.getString("formula2");
                                break;
                                
                                // aplicada a la formula    
                                case 2:              
                                    
                                    this.formula = form.getString("formula2");
                                    evaluador evalu = new evaluador();                           
                                    evalu.exp = formula;
                                    this.descuentos = evalu.ejecutar(liq);
                                break;
                            }    
                        break;
                    }
                }
                else
                {//importe
                    switch(form.getInt("tipo")) 
                    {
                        case 1:  //remunerativos
                            this.remunerativo = form.getFloat("formula");
                            this.formula= "Imp";
                        break;
                            
                        case 2://no remunerativo
                            this.noremunerativo = form.getFloat("formula");
                            this.formula= "Imp";
                        break;
                           
                        case 3://descuento
                            this.descuentos = form.getFloat("formula");
                            this.formula= "Imp";
                        break;   
                    }                    
                }
                
                liq.campos = "totalRemunerativo,totalNoRemunerativo,"+
                                "totalDescuento,total";
                liq.idRecibo= this.idRecibo;
                resultado2 =liq.consultarecibo();
                float rem = resultado2.getFloat("totalRemunerativo");
                float noRem = resultado2.getFloat("totalNoRemunerativo");
                float desc = resultado2.getFloat("totalDescuento");
                float total=resultado2.getFloat("total");
                
                switch(form.getInt("tipo")) 
                {
                    case 1:
                        rem += this.remunerativo;
                        total += remunerativo;
                    break;
                        
                    case 2:
                        noRem += this.noremunerativo;
                        total +=this.noremunerativo;
                    break;
                        
                    case 3:
                        desc += this.descuentos; 
                        total -= descuentos;
                    break;
                }
                liq.valores = rem+","+noRem+","+desc+","+total;
            } 
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
                Imprime(estado);
                Imprime(this.campos);
                Imprime(this.valores);
            }            
            
            valores = idRecibo+","+idConcepto+","+unidad+",'"+formula+"',"
                            +remunerativo+","+noremunerativo+","+descuentos;
            if(this.insertaSQL()==1)
            {
                liq.modificaSQL();
                return 1;
            }
            else
            {
                return 0; 
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
                float remu = resultado.getFloat("remunerativo");
                float noRemu = resultado.getFloat("noremunerativo");
                float desc = resultado.getFloat("descuento");
                this.valores = idRecibo+","+idConcepto+","+unidad+",'"+formula+"',"
                            +remunerativo+","+noremunerativo+","+descuentos;
                this.modificaSQL();     //aqui tengo que modificar
                if (remu != remunerativo)
                {
                    total.totalRecibo(1);
                }
                else
                {
                    if (noRemu != noremunerativo)
                    {
                        total.totalRecibo(2);
                    } 
                    else
                    {
                        if (desc != descuentos)
                        {
                            total.totalRecibo(3);
                        }
                    }
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
    public class Control extends libSentenciasSQL    
    {
        public int idLegajo=0;
        public int idConcepto=0;
        public float unidades = 0;
        public int tipo = 0;
        public String ini = "";
        public String fin = "";
        public int estadoConcepto = 1;
        
        
        public Control()
        {
            this.tabla = "legajoconcepto";
            this.campos = "idLegajo,idConcepto,unidades,tipo,inicio,fin,estado";
        }
        
        public int nuevo()
        {
            this.valores = idLegajo+","+idConcepto+","+unidades+","+tipo+",'"+
                            ini+"','"+fin+"',"+estadoConcepto;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idLegajo="+idLegajo+" AND idConcepto="+idConcepto;
            this.valores = idLegajo+","+idConcepto+","+unidades+","+tipo+",'"+
                            ini+"','"+fin+"',"+estadoConcepto;
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
