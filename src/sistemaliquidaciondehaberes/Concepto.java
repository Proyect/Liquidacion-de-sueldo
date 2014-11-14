
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.sql.Date;
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
        int idLicencia = 0;
        int tipo = 0;
        String inicio = "";
        String fin = "";
        int idRecibo=0; //para la ultima funcion
        float formula =0;
        String formula2 = null;
        int tipoform = 0; 
        int claseForm=0;
        
        // constructor
        public Detalle()
        {
            this.tabla = "conceptosdetalle";
            this.campos = "nombreCons,detalleCons,idLicencia,tipo,"
                            + "inicio,fin,formula,formula2,tipoForm,claseForm";                            
        }
        
        public int nuevo()
        {
            this.valores = "'"+nombreCons+"','"+detalleCons+"',"+
                            idLicencia+","+tipo+",'"+inicio+"','"+fin+"',"+
                            formula+",'"+formula2+"',"+tipoform+","+claseForm;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idConcepto="+idConcepto;
            this.valores =  "'"+nombreCons+"','"+detalleCons+"',"+
                            idLicencia+","+tipo+",'"+inicio+"','"+fin+"',"+
                            formula+",'"+formula2+"',"+tipoform+","+claseForm;
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
            
            try 
            { 
                if(form.getInt("aplicacion")==2)//es un concepto temporal
                {
                    liq.idRecibo = idRecibo;
                    resultado2 = liq.consultarecibo();
                    if((resultado2.getDate("periodoIni").before(form.getDate("fin")))
                       ||( form.getDate("inicio").before(resultado2.getDate("periodoFin"))))
                    {
                    
                    }
                    else
                    {
                        Imprime("Concepto Fuera de fecha");
                    }    
                }
                
                if(form.getInt("claseform")==1 )
                {//formula
                    liq.idRecibo=idRecibo;
                    resultado2 = liq.consultarecibo();
                    switch(form.getInt("tipo"))
                    {                       
                        case 1://remunerativo
                            
                            switch(form.getInt("tipoform"))
                            {
                                case 1: //pre-establecida
                                    this.remunerativo = 0;
                                    this.formula = ""+form.getFloat("formula");
                                break;
                                   
                                case 2: //aplicar formula
                                    this.remunerativo = form.getFloat("formula")*
                                        this.remunerativo;
                                    this.formula = ""+form.getFloat("formula");
                                break;                      
                            }
                            
                        break;
                        
                        //no remunerativo
                        case 2:
                            switch(form.getInt("tipoForm")) 
                            {   //pre-establecida
                                case 1:
                                    liq.totalNoRemunerativo = 0;
                                    this.formula = ""+form.getFloat("formula");
                                break;
                                    
                                case 2://aplicar la formula                                    
                                    this.noremunerativo = 0;
                                    this.formula = ""+form.getFloat("formula");
                                break;
                            }         
                            
                        break;                                       
                        
                        case 3:// descuento
                            switch(form.getInt("tipoForm"))
                            {   //pre-establecida
                                case 1:
                                    this.descuentos = 0;
                                    this.formula = ""+form.getFloat("formula");
                                break;
                                
                                // aplicada a la formula    
                                case 2:                                    
                                    this.descuentos = 0;
                                    this.formula = ""+form.getFloat("formula");
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
                        break;
                            
                        case 2://no remunerativo
                            this.noremunerativo = form.getFloat("formula");
                        break;
                           
                        case 3://descuento
                            this.descuentos = form.getFloat("formula");
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
                float total=0;
                
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
