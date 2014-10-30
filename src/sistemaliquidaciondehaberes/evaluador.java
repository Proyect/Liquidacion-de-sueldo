/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**  Ariel Marcelo Diaz */
//clase para implementar funciones matematicas para el sistema
public class evaluador 
{
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("js");
    String exp="";
    float sb=0;
    float sp=0;
    float tr=0;
    float tnr=0;
    float td=0;
    int tipo=0; //0: Test, 1: Busca y reemplazo de caracteres
    //ejecuta las funciones ya probadas
    public float ejecutar()
    {
        float aux=0;
        exp.replaceAll("SB", ""+sb);
        exp.replaceAll("SP", ""+sp);
        exp.replaceAll("TR", ""+tr);
        exp.replaceAll("TNR", ""+tnr);
        exp.replaceAll("TD", ""+td);
        try
        {
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            Logger.getLogger(evaluador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    // es para probar las funciones
    public float evaluar()
    {
        float aux=0;
        
        //reemplazo de expresion
        exp.replaceAll("SB", "1");
        exp.replaceAll("SP", "1");
        exp.replaceAll("TR", "1");
        exp.replaceAll("TNR", "1");
        exp.replaceAll("TD", "1");
        //falta lo de conceptos
        try
        {
            aux=Float.valueOf(""+engine.eval(exp));
        } 
        catch (ScriptException ex) 
        {
            Logger.getLogger(evaluador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    
    //busca un concepto y devuelve un valor
    public String concepto()
    {
        int con= exp.indexOf("concep");
        while(con != -1)
        {
         exp.indexOf(" ", con); //mejor busqueda por expresiones regulares
        }
    }        
}
