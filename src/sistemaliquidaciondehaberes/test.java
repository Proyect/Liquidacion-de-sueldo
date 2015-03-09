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
        /*************************************
         * Pruebas del evaluador de funciones
         * ************************************/
         
       /* Probando evaluador de funciones
        evaluador evalu = new evaluador();
        Liquidacion liq = new Liquidacion();
        liq.idRecibo=2;     
        liq.cargaRecibo();
        evalu.tipo=1;
        evalu.exp = "TR";
        evalu.ejecutar(liq);
        //evalu.concepto(liq);*/
        
        /**************************************
                 Pruebas de Impresion 
         **************************************/
        
        /*Imprime el recibo de sueldo
         Imprime imp = new Imprime();
         Liquidacion con = new Liquidacion();
         con.idRecibo=10;
         con.cargaRecibo();
         
         imp.recibo(con);*/
         
        
        /**************************************
         *    Liquidacion de Sueldo
         *************************************/
        
        /* Pruebas de inasistencias de un empleado
        Liquidacion con = new Liquidacion();
        con.idLegajo=1;
        con.periodoIni = "2014-08-01";
        con.periodoFin = "2014-08-30";
        con.inasistencias();*/
        
        /* Prueba de totales del recibo
        Liquidacion con = new Liquidacion();
        con.idRecibo=56;
        Imprimir("Total Descuento: "+con.totalRecibo(3));*/
        
        /* Prueba de asignaciones familiares
        Liquidacion con = new Liquidacion();
        con.idRecibo=45;
        con.idLegajo=1;
        con.asignaciones();*/
        
         /*prueba de consulta de recibo, fs vector recibo
        Liquidacion con = new Liquidacion();        
        con.idRecibo = 12;
        con.vectorRecibo();*/
        
        /*Prueba liquidacion de sac
        Liquidacion con = new Liquidacion();
        con.idLegajo = 1;
        con.periodoIni = "2014-06-01";
        con.periodoFin = "2014-12-30";
        con.dias = 180;
        con.SAC(); */
        
        /* Prueba de vacaciones - no termina 
        Liquidacion con = new Liquidacion();
        con.idLegajo = 3;
        con.vacaciones();*/
        
        /* Pueba de liquidacion de sueldo
         Liquidacion con = new Liquidacion();        
        con.idLegajo=1;
        con.periodoIni = "2014-10-01";
        con.periodoFin = "2014-10-30";
        con.dias=30;
        con.diasTrabajados=30;       
        con.recibo();*/
       
        /* Pruebas individuales de liquidacion de sueldo  
        Liquidacion con = new Liquidacion(); 
        con.idLegajo=30;
        con.obtienePuesto();
        con.obtieneDatos(); */
        
        /* Realiza las consultas de un recibo de sueldo 
        Liquidacion con = new Liquidacion();
        con.idLegajo=23;
        con.consultarecibo();*/
        
        /* Realiza las actualiaciones del recibo de sueldo - probar 
        Liquidacion con = new Liquidacion();
        con.idRecibo=51;
        con.diasTrabajados=30;
        con.dias = 30; //aqui tengo que ver 
        con.periodoIni = "2014-08-01";
        con.periodoFin = "2014-08-30";
        con.reciboUpdate();*/
        
        /* Calcula la antiguedad del recibo 
        Liquidacion con = new Liquidacion();
        con.idRecibo=5;
        con.fechaInicio="2011-02-07";
        con.devuelveAntiguedad();*/
        
        /*Prueba asignaciones 
        Legajolib con = new Legajolib();
        Legajolib.Asignaciones asignar= con.new Asignaciones();
        asignar.idLegajo=2;
        asignar.idPersona = 5;
        asignar.idVinculo=5;
        asignar.alta();*/
        
        /* Realiza las asignaciones familiares del recibo de sueldo
        Liquidacion con = new Liquidacion();
        con.idRecibo=5;
        con.idLegajo=1;
        con.asignaciones();*/
        
        /* realiza la aplicacion de conceptos pre-establecidos 
        Liquidacion con = new Liquidacion();
        con.idRecibo=4;
        con.idLegajo=1;
        con.preajustados();*/
        
        /*Prueba aplicacion de cargas sociales
        Liquidacion con = new Liquidacion();
        con.idRecibo=6;
        con.idLegajo=1;
        con.totalRemunerativo = (float) 7349.33;
        con.obtieneObraSocial();*/
        
        /***************************
         *       Conceptos
         ***************************/
        
        /*prueba aplicacion de conceptos 
        Liquidacion liq = new Liquidacion();
        liq.idRecibo=12;
        liq.cargaRecibo();
        Concepto cons= new Concepto();
        Concepto.Aplica con= cons.new Aplica();
        con.idRecibo = 12;
        con.idConcepto = 15;
        con.nuevo(liq);*/
        
        /* Prueba modificacion de conceptos        
        Concepto cons= new Concepto();
        Concepto.Aplica con= cons.new Aplica();
        con.idRecibo = 48;
        con.idConcepto = 2;
        con.unidad=1;               
        con.modifica(); */
        
        /*Prueba de consulta de conceptos no incluidos en el recibo
        Concepto cons= new Concepto();
        Concepto.Detalle con= cons.new Detalle();
        con.idRecibo = 3;
        con.noEnRecibos();        */
        
        /* Prueba creacion de nuevos conceptos    
        Concepto cons= new Concepto();
        Concepto.Detalle con= cons.new Detalle();
        con.nombreCons = "Presentismo";
        con.detalleCons = "";
        con.formula2 = "SB/12";
        con.claseForm = 1;
        con.tipo = 1;
        con.tipoform =2;
        con.aplicacion = 0;
        con.nuevo(); */
        
        /* prueba para conceptos pre establecidos
        Liquidacion con = new Liquidacion();
        con.idLegajo = 1;
        con.idRecibo = 13;
        con.preajustados();*/
        
        /* Modifica los codigos pre-ajustados
        Liquidacion con = new Liquidacion();
        Concepto.Control concep = con.fsConceptos.new Control();
       
        concep.idLegajo = 1;
        concep.idConcepto = 6;
        concep.unidades = 1;
        concep.tipo = 0;
        concep.ini = "2011-10-10";
        concep.fin = "2017-12-12";
        concep.estadoConcepto = 0;
        concep.modifica();*/
          
        
        /* Consulta los conceptos incluidos en el recibo
        Concepto cons= new Concepto();
        Concepto.Aplica con= cons.new Aplica();
        con.idRecibo=5;
        con.consultaRecibo();*/
        
        /**************************************
         *      Legajos
         **************************************/
        
         /*prueba licencia   
        Legajolib con = new Legajolib();               
        Legajolib.Licencias resultado;
        resultado = con.new Licencias();        
        resultado.idLegajo=20;
        resultado.motivo="Duelo del madre";
        resultado.cantidad=3;
        resultado.inicio="2014-05-01";
        resultado.fin = "2014-05-02";
        resultado.tipoLic = 3;
        resultado.alta();    */ 
        
        /* Prueba de control de licencias vencidas
        Legajolib con = new Legajolib();               
        Legajolib.Licencias resultado = con.new Licencias(); 
        resultado.control();*/
        
        /* Prueba estado legajo
        Legajolib con = new Legajolib();
        con.idLegajo=2;
        con.modificaLegajo(2); */
        
         /* Cargas sociales 
        Legajolib con = new Legajolib();
        Legajolib.CargasSociales cargaSocial = con.new CargasSociales(); 
        cargaSocial.idLegajo=22;
        cargaSocial.idEmpresa = 5;
        cargaSocial.vinculacion ="Obra Social";
        //cargaSocial.nuevo();
        //cargaSocial.modifica("idLegajo=22 AND idEmpresa=1 AND Vinculacion='Obra Social'");
        */
        
        /*Modificacion de las cargas sociales
        Legajolib con = new Legajolib();
        Legajolib.CargasSociales cargaSocial = con.new CargasSociales();
        cargaSocial.idLegajo=10;
        cargaSocial.idEmpresa = 3;
        cargaSocial.vinculacion="Art";
        cargaSocial.modifica("idLegajo=10 AND idEmpresa=3 AND Vinculacion='Sindicato'");*/
        
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
                
        
        
        /* Prueba inasistencia
        Legajolib con = new Legajolib();
        Legajolib.Inasistencia inasistencia = con.new Inasistencia();
        inasistencia.idLegajo=1;
        inasistencia.fecha= "2014-08-23";
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
        inasistencia.fecha="2014-08-29";
        inasistencia.nueva();*/
        
        /* Prueba modificacion de puestos
        Legajolib con = new Legajolib();
        Legajolib.Puestos puesto = con.new Puestos();
        puesto.idLegajo=34;
        puesto.idPuesto=5;
        puesto.idNovedad = 9;
        puesto.fechaInicio="2014-09-30";
        puesto.fechaFin="2770-01-01";
        puesto.estadoP=1;
        puesto.condicion="idPuesto="+4+
                            " AND idLegajo="+puesto.idLegajo;
        puesto.modifica();*/
        
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
        
        /*Alta en clase persona
        Personaslib con = new Personaslib();        
        con.apellido = "prueba";
        con.cuil="1234";
        con.tipoDoc = "D.N.I.";
        con.nroDoc = "123456789";
        con.estadoCivil ="Soltero/a";
        con.mail = "hola.hola";
        con.celular = "1555554555";
        con.calle ="Las Heras";
        con.nro="23";
        con.fechaNac = Personaslib.FechaActual();
        con.idProvincia=2;
        con.nueva();*/
        
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
        
        /* realiza la carga de un nuevo titulo 
        Complementarios con = new Complementarios();
        Complementarios.Titulos titulosCreacion = con.new Titulos();
        titulosCreacion.idInstitucion= 2;
        titulosCreacion.carrera = "Lic en Analisis de Sistemas";
        titulosCreacion.titulo = "Licienciatura en Analisis de Sistema";
        titulosCreacion.objetivo = "";
        titulosCreacion.materias = "";
        titulosCreacion.analitico = "";
        titulosCreacion.nueva();*/
    }
    // Imprime en pantalla el mensaje que se envia

    public static void Imprimir(String x) {
        System.out.print(x);
    }
}
