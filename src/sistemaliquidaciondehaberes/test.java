/*
Solo es para probar algunas funciones y clases .
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;


/**  Ariel Marcelo Diaz
 */
public class test {

    public static void main(String[] args) throws SQLException 
    {
       // Legajolib.Inasistencia resultado ;
         
        Imprimir("Conectando \n");
        
        Liquidacion con = new Liquidacion();
        con.idLegajo=1;
        ResultSet obtienePuesto = con.obtienePuesto();
        con.obtieneDatos();
        con.obtieneSindicato();
        con.devuelveAntiguedad();
        con.devuelveJubilacion();
        con.devuelveART();
        /*
        Legajolib con = new Legajolib();               
        Legajolib.Licencias resultado;
        resultado = con.new Licencias();
        
        resultado.idLegajo=1;
        resultado.motivo="Licencia medica";
        resultado.cantidad=10;
        resultado.inicio="2014-02-01";
        resultado.fin = "2014-02-20";
        resultado.tipoLic = 1;
        resultado.alta();    */    
        
    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
