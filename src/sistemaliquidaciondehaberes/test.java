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
        /* Pruebas de legajo - Inasistencia
        Legajolib con = new Legajolib();
        Legajolib.Inasistencia inasistencia = con.new Inasistencia();
        inasistencia.idLegajo=2;
        inasistencia.justificada=0;
        inasistencia.nueva();*/
        
       /* Empresaslib con = new Empresaslib();
        con.tipo = "S.R.L.";
        con.nueva();*/
        
        /* prueba clase personas
        Personaslib con = new Personaslib();        
        con.apellido = "prueba";
        con.tipoDoc = "D.N.I.";
        con.estadoCivil ="Soltero/a";
        con.fechaNac = Personaslib.FechaActual();
        con.nueva(); */
        
       /* Pueba de liquidacion de sueldo*/
         Liquidacion con = new Liquidacion();
        con.idRecibo=2;
        con.idLegajo=1;
        con.periodoIni = "2014-03-01";
        con.periodoFin = "2014-03-30";
        con.dias=30;
        con.diasTrabajados=30;
        ResultSet obtienePuesto = con.obtienePuesto();
        con.obtieneDatos();
        con.horasExtras();
        /*con.obtieneSindicato();
        con.devuelveAntiguedad();
        con.devuelveJubilacion();
        con.devuelveART();
        con.presentismo();
        con.recibo();*/
        con.asignaciones();
        
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
