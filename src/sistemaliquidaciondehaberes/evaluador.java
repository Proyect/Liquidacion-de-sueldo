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
    
    String exp = "";
    float sb = 0;    
    int tipo=0; //0: Test, 1: Busca y reemplazo de caracteres
    String concep="";
    float pi=(float) 3.1415926535897932384626433832;
    
    //ejecuta las funciones ya probadas
    public float ejecutar(Liquidacion liq)
    {
        float aux=0;        
        tipo=1;
        exp=exp.replaceAll("SB", "0");
        
        exp=exp.replaceAll("SP", ""+liq.basico);
        exp=exp.replaceAll("TR", ""+liq.totalRemunerativo);
        exp=exp.replaceAll("TNR", ""+liq.totalNoRemunerativo);
        exp=exp.replaceAll("TD", ""+liq.totalDescuentos);
             
        concepto(liq);
        try
        {
            Imprime(exp);
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            Imprime("Fallo en la evaluacion de formula");
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
        exp=exp.replaceAll("SB", ""+pi);
        exp=exp.replaceAll("SP", ""+pi);
        exp=exp.replaceAll("TR", ""+pi);
        exp=exp.replaceAll("TNR", ""+pi);
        exp=exp.replaceAll("TD", ""+pi);        
        concepto(null);
        try
        {
            Imprime(exp);
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            Imprime("Error en la funcion artimetica");
        }
        Imprime(""+aux);
        return aux;
    }
    
    //busca un concepto 
    public void concepto(Liquidacion liq)
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
                exp=exp.replaceAll(concep, ""+pi);
            }            
            else
            {// se ejecuta conceptos 
                Concepto con = new Concepto();
                Concepto.Detalle det = con.new Detalle();
                Concepto.Aplica apli = con.new Aplica();                
                
                int aux = Integer.parseInt(concep.substring(6));
                Imprime(""+aux);
                det.idConcepto= aux;
                apli.idConcepto= aux;
                ResultSet resultado = det.consulta();
                
                apli.idRecibo = liq.idRecibo;
                ResultSet resultado2 = apli.consultaRecibo();
                float mostrar =0;
                try
                {
                    if (resultado.getInt("claseForm")==0)
                    {//constante
                        mostrar=resultado.getFloat("formula");
                        Imprime("Datos a mostrar: "+mostrar);
                    }
                    else 
                    {//formula
                        if(resultado2.isFirst())
                        {
                            mostrar=resultado2.getFloat("remunerativo")+
                                   resultado2.getFloat("noremunerativo")+
                                   resultado2.getFloat("descuento");
                            Imprime("valor del concepto: "+mostrar);
                        }
                        else
                        {
                            apli.nuevo(liq);
                        }
                    }                    
                }
                catch (SQLException ex)
                {
                    Imprime("Fallo en el evaluador de conceptos");
                }
                exp=exp.replaceAll(concep, ""+mostrar);                
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
