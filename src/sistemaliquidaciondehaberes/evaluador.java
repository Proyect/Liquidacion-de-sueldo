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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**  Ariel Marcelo Diaz */
//clase para implementar funciones matematicas para el sistema
public class evaluador 
{
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("js");
    Liquidacion liq = new Liquidacion();
    String exp = "";
    float sb = 0;
    
    int tipo=0; //0: Test, 1: Busca y reemplazo de caracteres
    String concep="";
    //ejecuta las funciones ya probadas
    public float ejecutar()
    {
        float aux=0;
        ResultSet resultado = liq.consultarecibo();
        tipo=1;
        exp=exp.replaceAll("SB", "");
        try 
        {
            exp=exp.replaceAll("SP", ""+resultado.getFloat("basico"));
            exp=exp.replaceAll("TR", ""+resultado.getFloat("totalRemunerativo"));
            exp=exp.replaceAll("TNR", ""+resultado.getFloat("totalNoRemunerativo"));
            exp=exp.replaceAll("TD", ""+resultado.getFloat("totalDescuento"));
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(evaluador.class.getName()).log(Level.SEVERE, null, ex);
        }     
        concepto();
        try
        {
            Imprime(exp);
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            Logger.getLogger(evaluador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Imprime(""+aux);
        return aux;
    }
    // es para probar las funciones
    public float evaluar()
    {
        float aux=0;        
        tipo = 0;
        //reemplazo de expresion
        exp=exp.replaceAll("SB", "1");
        exp=exp.replaceAll("SP", "1");
        exp=exp.replaceAll("TR", "1");
        exp=exp.replaceAll("TNR", "1");
        exp=exp.replaceAll("TD", "1");        
        concepto();
        try
        {
            Imprime(exp);
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            Logger.getLogger(evaluador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Imprime(""+aux);
        return aux;
    }
    
    //busca un concepto 
    public void concepto()
    {
        Pattern pat = Pattern.compile("concep\\d*");
        Matcher mat = pat.matcher(exp);
        if(mat.find())
        { 
            concep = mat.group();
           /* System.out.println("inicio "+mat.start()+" fin "+mat.end()
                    +" grupo "+concep);*/
            if(tipo == 0)
            {//test
                exp=exp.replaceAll(concep, "1");
            }            
            else
            {// se ejecuta conceptos 
                Concepto con = new Concepto();
                Concepto.Detalle det = con.new Detalle();
                Concepto.Aplica apli = con.new Aplica();
                apli.idRecibo = liq.idRecibo;
                
                int aux = Integer.parseInt(concep.substring(6));
                Imprime(""+aux);
                det.idConcepto= aux;
                apli.idConcepto= aux;
                ResultSet resultado = det.consulta();
                try
                {//aqui me quede
                    if (resultado.getInt("claseForm")==0)
                    {//constante
                        apli.nuevo();
                    }
                    else 
                    {//formula
                        if (resultado.getInt("tipoForm")==4)
                        {
                            String form=resultado.getString("formula");
                            mat = pat.matcher(form);
                            if (mat.find())
                            {
                                form = mat.group();
                                aux = Integer.parseInt(form.substring(6));
                                apli.idConcepto = aux;
                                ResultSet resul = apli.consulta();
                                if(resul != null)
                                {
                                    apli.nuevo();
                                }
                                else
                                {
                                    Imprime("Conepto no hayado");
                                }
                            }
                            else
                            {
                                apli.nuevo();
                            }
                        }
                        else
                        {
                           apli.nuevo(); 
                        }
                    }
                    ResultSet resultado2=apli.consultaRecibo();
                    float concepto=resultado2.getFloat("remunerativo")+
                                   resultado2.getFloat("noremunerativo")+
                                   resultado2.getFloat("descuento");
                    exp=exp.replaceAll(concep, ""+concepto);
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(evaluador.class.getName()).log(Level.SEVERE, null, ex);
                }
                exp.replaceAll(concep, "1");
            }
        }    
    }  
    
    //imprime
    public static void Imprime(String x)
        {
            x=x+"\n";
            System.out.print(x);
        }
}
