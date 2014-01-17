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
        int resultado ;
        Imprimir("Conectando");
        libSentenciasSQL con = new libSentenciasSQL();
        Imprimir("\n" + con.estado + "\n");        
        Integer idPers = 2;
        String fecha = con.FechaActual();
        String hora = con.HoraActual();
        
        //resultado = con.insertaSQL("sindicato", "razonSocial", "'{jola}'", "");
          con.tabla="persona";
       // con.campos="apellido,nombre";
       // con.valores ="'hola','mi amor'";
          con.condicion="idpersona=7";
        
        resultado = con.borraSQL();  
        
                
    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
