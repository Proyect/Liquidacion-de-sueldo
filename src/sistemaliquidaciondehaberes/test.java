/*
Solo es para probar algunas funciones y clases .
 */
package sistemaliquidaciondehaberes;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**  Ariel Marcelo Diaz
 */
public class test {

    public static void main(String[] args) 
    {
        Legajolib.Inasistencia resultado ;
        Imprimir("Conectando \n");
        Legajolib con = new Legajolib();               
      
       // con.idLegajo=1;
        resultado = con.new Inasistencia();        
        resultado.idLegajo=1;
        
        resultado.nueva();            
              
    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
