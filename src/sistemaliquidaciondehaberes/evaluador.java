/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.swing.JOptionPane;

/**  Ariel Marcelo Diaz */
//clase para implementar funciones matematicas para el sistema
public class evaluador 
{
    private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine = manager.getEngineByName("js");
    
    public String exp = "";
    public float sb = 0;    
    public int tipo=0; //0: Test, 1: Busca y reemplazo de caracteres
    public String concep="";
    public String mensaje = "";
    private float pi=(float) 3.1415926535897932384626433832;
    
    //ejecuta las funciones ya probadas
    public float ejecutar(Liquidacion liq)
    {
        float aux=0;
        Concepto fsConceptos = new Concepto();
        Concepto.Aplica aplic = fsConceptos.new Aplica();
        aplic.idRecibo = liq.idRecibo;
        tipo=1;
        exp=exp.replaceAll("SB", ""+liq.basico); 
        exp=exp.replaceAll("TR", ""+liq.totalRemunerativo);
        exp=exp.replaceAll("TNR", ""+liq.totalNoRemunerativo);
        exp=exp.replaceAll("TD", ""+liq.totalDescuentos); 
        exp=exp.replaceAll("DT", ""+liq.diasTrabajados);
        aplic.idConcepto=2;
        //optimizar aqui
        ResultSet resultado = aplic.consulta();
        exp=exp.replaceAll("Ant", ""+resultado.getFloat("remunerativo"));
        exp=exp.replaceAll("AñosT", ""+liq.anti);
        // y aqui tambien
        aplic.idConcepto=2;
        resultado = aplic.consulta();
        exp=exp.replaceAll("Prec", ""+resultado.getFloat("remunerativo"));
        concepto(liq);
        if(exp.indexOf("SP") != -1)
        {            
            Concepto con = new Concepto();
            Concepto.Aplica apli = con.new Aplica();
            apli.idRecibo = liq.idRecibo;
            apli.idConcepto = 1;
            ResultSet datos = apli.consulta();
            try 
            {
                exp=exp.replaceAll("SB", ""+datos.getFloat("remunerativo"));
            }
            catch (SQLException ex) 
            {
                Imprime("Error al obtener el sueldo basico");
            }
        }  
        
        try
        {
            Imprime(exp);
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            mensaje = "Fallo en la evaluacion de formula";           
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
        exp=exp.replaceAll("DT", ""+pi); 
        exp=exp.replaceAll("Ant", ""+pi); 
        exp=exp.replaceAll("AñosT", ""+pi);
        exp=exp.replaceAll("Prec", ""+pi); 
        concepto(null);
        try
        {
            Imprime(exp);
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            mensaje = "Fallo en la evaluacion de formula";  
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
