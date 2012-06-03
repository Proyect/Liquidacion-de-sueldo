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
        ResultSet resultado = null;
        Imprimir("Conectando");
        Conexion con = new Conexion();
        Imprimir("\n" + con.estado + "\n");        
        Integer idPers = 2;
        String fecha = con.FechaActual();
        String hora = con.HoraActual();
        resultado = con.consultaSQL("barrio", "nombre", "");
        
              // con.estadoObraSocial(idPers, idPers); 
                
               // con.altaSindicato(hora, hora, fecha, hora, hora, hora, idPers);
                
                //con.estadoObraSocial(4, 3);
              //  con.asignaObraScial(idPers, 4);
                
              //  con.buscaPersona("nombre", "ari");
               // con.asignaObraScial(idPers, 3);
               // con.asignaObraScial(idPers, 3);
       
        
    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
