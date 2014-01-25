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
        Legajolib.Sindicato resultado ;
        Imprimir("Conectando \n");
        Legajolib con = new Legajolib();
               
      /*  Integer idPers = 2;
        String fecha = con.FechaActual();
        String hora = con.HoraActual();*/
        
        
        //Imprimir("\n" + con.valores + "\n"); 
        resultado = con.new Sindicato();
        resultado.idSindicato=1;
        resultado.idLegajo=12;
       resultado.alta_sindicato();
              
    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
