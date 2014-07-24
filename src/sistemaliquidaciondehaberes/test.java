/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
//sirve para probar algunas cosas
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;


/**  Ariel Marcelo Diaz
 */
public class test {

    public static void main(String[] args) throws SQLException 
    {  
        /**************************************
         *    Liquidacion de Sueldo
         *************************************/
        
        /* Prueba de asignaciones familiares
        Liquidacion con = new Liquidacion();
        con.idRecibo=41;
        con.idLegajo=1;
        con.asignaciones();*/
        
         /*prueba de consulta de recibo 
        Liquidacion con = new Liquidacion();        
        con.idRecibo = 12;
        con.vectorRecibo();*/
        
        
        /*prueba aplicacion de conceptos - seguir provando
        Concepto cons= new Concepto();
        Concepto.Aplica con= cons.new Aplica();
        con.idRecibo = 12;
        con.idConcepto = 3;
        con.nuevo();*/
        
        /* prueba para conceptos pre establecidos
        Liquidacion con = new Liquidacion();
        con.idLegajo = 1;
        con.idRecibo = 13;
        con.preajustados();*/
        
        /*Prueba liquidacion de sac
        Liquidacion con = new Liquidacion();
        con.idLegajo = 1;
        con.periodoIni = "2014-01-01";
        con.periodoFin = "2014-06-06";
        con.dias = 360;
        con.SAC();*/
        
        /* Prueba de vacaciones - no termina 
        Liquidacion con = new Liquidacion();
        con.idLegajo = 3;
        con.vacaciones();*/
        
        /* Pueba de liquidacion de sueldo
         Liquidacion con = new Liquidacion();
        //con.idRecibo=1;
        con.idLegajo=3;
        con.periodoIni = "2014-07-01";
        con.periodoFin = "2014-07-30";
        con.dias=30;
        con.diasTrabajados=30;       
        con.recibo();*/
        
        /**************************************
         *      Legajos
         **************************************/
        
         /*prueba licencia  
        Legajolib con = new Legajolib();               
        Legajolib.Licencias resultado;
        resultado = con.new Licencias();        
        resultado.idLegajo=2;
        resultado.motivo="Duelo del madre";
        resultado.cantidad=2;
        resultado.inicio="2014-05-01";
        resultado.fin = "2014-05-02";
        resultado.tipoLic = 2;
        resultado.alta();   */   
        
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
        
        /************************************
         *      Empresas
         **********************************/
        
       /* Empresaslib con = new Empresaslib();
        con.tipo = "S.R.L.";
        con.nueva();*/
        
        /*********************************
         *      Personas
         ********************************/
        
        /* prueba clase personas
        Personaslib con = new Personaslib();
        con.idPersona=2;
        con.apellido = "prueba";
        con.tipoDoc = "D.N.I.";
        con.nroDoc = "123456789";
        con.estadoCivil ="Soltero/a";
        con.mail = "hola.hola";
        con.celular = "1555554555";
        con.fechaNac = Personaslib.FechaActual();
        con.idProvincia=2;
        con.modificar(); */
        
       /*******************************************
         *         Complementarios
         *****************************************/  
        
        
        /* Crear los complementarios  
        Complementarios con = new Complementarios();
        Complementarios.Capacitaciones capacitacion = con.new Capacitaciones();
        capacitacion.idInstitucion=1;
        capacitacion.nombre = "Curso de kamasutra";
        capacitacion.fechaIni = "2014-04-10";
        capacitacion.fechaFin = "2014-04-20";
        capacitacion.nueva();*/
        
        /*  Carga un nuevo tipo de licencia
        Complementarios con = new Complementarios();
        Complementarios.TipoLicencia tipoLic = con.new TipoLicencia();
        tipoLic.tipoLicencia = "Maternidad";
        tipoLic.detalle = "La empleada entro en perido de maternidad";  
        tipoLic.dias = 60;
        tipoLic.concepto=0;
        tipoLic.valides = 3;
        tipoLic.nuevo();*/
        
        /* realiza la carga de un nuevo titulo */
        Complementarios con = new Complementarios();
        Complementarios.Titulos titulosCreacion = con.new Titulos();
        titulosCreacion.idInstitucion= 2;
        titulosCreacion.carrera = "Lic en Analisis de Sistemas";
        titulosCreacion.titulo = "Licienciatura en Analisis de Sistema";
        titulosCreacion.objetivo = "";
        titulosCreacion.materias = "";
        titulosCreacion.analitico = "";
        titulosCreacion.nueva();
    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
