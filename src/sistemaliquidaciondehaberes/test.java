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
         
        Imprimir("Conectando \n");   
        
        /*  */
        
        /* Prueba estado legajo
        Legajolib con = new Legajolib();
        con.idLegajo=2;
        con.modificaLegajo(2); */
        
         /* Cargas sociales 
        Legajolib con = new Legajolib();
        Legajolib.CargasSociales cargaSocial = con.new CargasSociales(); 
        cargaSocial.idLegajo=2;
        cargaSocial.idEmpresa = 2;
        cargaSocial.vinculacion ="Obra Social";
        //cargaSocial.nuevo();
        //cargaSocial.modifica("idLegajo="+cargaSocial.idLegajo);*/
        
        /* Pueba clase novedad
        Legajolib con = new Legajolib();
        Legajolib.Novedad nov= con.new Novedad();
       // nov.modifica_novedad("2,'provando la novedad','no se que hacer',1,'2014-04-23'", 
        //                      "idNovedad=31");*/
        
        /* Pueba clase horas extras 
        
        Legajolib con = new Legajolib();
        Legajolib.HorasExtras horaextras=con.new HorasExtras();
        horaextras.idLegajo=2;        
        horaextras.fecha="2014-02-01";
        horaextras.cantidadHs=10;
        horaextras.tipoHs=2;//100
        horaextras.nueva();*/
                
        /*Prueba asignaciones
        Legajolib con = new Legajolib();
        Legajolib.Asignaciones asignar= con.new Asignaciones();
        asignar.idLegajo=2;
        asignar.idPersona = 5;
        asignar.idVinculo=5;
        asignar.alta();*/
        
        /* Prueba inasistencia
        Legajolib con = new Legajolib();
        Legajolib.Inasistencia inasistencia = con.new Inasistencia();
        inasistencia.idLegajo=3;
        inasistencia.fecha= "2014-04-23";
        inasistencia.justificada=0;
        inasistencia.nueva();*/
        
        /*Pueba llegadas tardes
        Legajolib con = new Legajolib();
        Legajolib.LlegadasTardes llegadastarde = con.new LlegadasTardes();
        llegadastarde.idLegajo=2;
      // llegadastarde.fecha="2014-04-22";
       // llegadastarde.hora = llegadastarde.
        llegadastarde.alta();*/
        
        
        
        
        /* Pruebas de legajo - Inasistencia
        Legajolib con = new Legajolib();
        Legajolib.Inasistencia inasistencia = con.new Inasistencia();
        inasistencia.idLegajo=3;
        inasistencia.justificada=0;
        inasistencia.fecha="2014-04-30";
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
        
       /* Pueba de liquidacion de sueldo
         Liquidacion con = new Liquidacion();
        //con.idRecibo=1;
        con.idLegajo=1;
        con.periodoIni = "2014-03-01";
        con.periodoFin = "2014-03-30";
        con.dias=30;
        con.diasTrabajados=30;
        ResultSet obtienePuesto = con.obtienePuesto();
        con.obtieneDatos();
        con.horasExtras();
        con.obtieneObraSocial();
        con.obtieneSindicato();
        con.devuelveAntiguedad();
        con.devuelveJubilacion();
        con.devuelveART();
        con.presentismo();
        con.recibo();
        con.asignaciones();*/
        
        /* prueba licencia medica - verificar la inasistencia
        Legajolib con = new Legajolib();               
        Legajolib.Licencias resultado;
        resultado = con.new Licencias();        
        resultado.idLegajo=1;
        resultado.motivo="Licencia medica";
        resultado.cantidad=5;
        resultado.inicio="2014-04-01";
        resultado.fin = "2014-04-30";
        resultado.tipoLic = 1;
        resultado.alta();       */
        
        /* Crear los complementarios  
        Complementarios con = new Complementarios();
        Complementarios.Capacitaciones capacitacion = con.new Capacitaciones();
        capacitacion.idInstitucion=1;
        capacitacion.nombre = "Curso de kamasutra";
        capacitacion.fechaIni = "2014-04-10";
        capacitacion.fechaFin = "2014-04-20";
        capacitacion.nueva();*/

    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
