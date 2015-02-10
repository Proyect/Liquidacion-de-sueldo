package sistemaliquidaciondehaberes;

import java.lang.String;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Liquidacion extends libSentenciasSQL
{
    public int idRecibo=0;
    public int idLegajo = 0;
    public int idPuesto = 0;
    public int estadoR = 0;
    public String periodoIni = "";
    public String periodoFin = ""; 
    public String emision = FechaActual();
    public float basico = 0;  //sueldo basico no proporcional
    public int dias=30; // dias a trabajar
    public int diasTrabajados = 30; 
    public float costoHs = 0;
    public float costoHs50 = 0;
    public float costoHs100 = 0;   
    public int cantHs = 0;
    public int cantHs50 = 0;
    public int cantHs100 = 0;      
    public int idObraSocial = 0;    
    public int idSindicato = 0;       
    public int anti = 0;  
    public int idART = 0;
    public float totalRemunerativo =0;
    public float totalNoRemunerativo = 0;
    public float totalDescuentos = 0;
    public float total = 0;
    public String fechaInicio = "";
    private Legajolib fsLegajo = new Legajolib();
    private Concepto fsConceptos = new Concepto(); 
    private Concepto.Detalle detall= fsConceptos.new Detalle();
    private Concepto.Aplica aplic = fsConceptos.new Aplica();
            
    //constructor
    public Liquidacion()
    {
        this.tabla = "recibos";
        this.campos = "idLegajo,estadoR,costoHs50,costoHs100,idPuesto,periodoIni,periodoFin,emision,"+
                        "basico,CantHs,costoHs,cantHs50,"+
                        "CantHs100,idObraSocial,idSindicato,idART,diasTrabajados"
                        +",anti,totalRemunerativo,totalNoRemunerativo,totalDescuento,total";
        fsLegajo.idLegajo = idLegajo;
    }

    //obtiene el numero de recibo antes de gravarlo
    private void nrorecibo()
    {

    }

    //obtiene el puesto del empleado en cuestion
    public void obtienePuesto()
    {   
        ResultSet reg = null;     
        Legajolib.Puestos puesto = fsLegajo.new Puestos();       
        puesto.condicion = "idLegajo="+this.idLegajo+ " AND NOT estado=7";
        reg = puesto.consulta();
        if (reg != null)
        {
            try
            {
                Imprime("Puesto obtenido exitosamente");
                this.idPuesto = reg.getInt(1);
                Imprime("idPuesto: "+this.idPuesto);
                this.fechaInicio = reg.getString("fechaInicio");
                Imprime("inicio: "+this.fechaInicio);                
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
                Imprime(estado);
            }            
        }
        else
        {
            Imprime("El legajo no tiene ningun puesto activo");
            // aqui hay que liquidar cuando se despide al empleado
        }          
    }
    
    
    //obtiene el basico del empleado
    public void obtieneBasico()
    {        
        aplic.idConcepto=1;
        aplic.nuevo(this);        
    }    
    
    // obtiene los datos basicos de la liquidacion
    public void obtieneDatos()
    {
        Complementarios complemento= new Complementarios();
        Complementarios.Cargos cargo = complemento.new Cargos();
        cargo.idPuesto = this.idPuesto;
        ResultSet datos = cargo.consulta();
        if (datos != null)
        {
            try 
            {   
                this.inasistencias();
                this.basico = datos.getFloat("basico");
                Imprime("Basico: "+this.basico);
                this.costoHs = datos.getFloat("costoHs");
                Imprime("Costo Hs:"+this.costoHs);
                this.costoHs50 = datos.getFloat("costoHs50");
                Imprime("Costo Hs al 50%:"+this.costoHs50);
                this.costoHs100 = datos.getFloat("costoHs100");
                Imprime("Costo Hs al 100$:"+this.costoHs100);                
                Imprime("cantidad de hs mensuales: "+datos.getInt("hsSemanales")*4);
            }
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
                Imprime(estado);
            }            
        }
        else
        {
            Imprime("Datos no obtenidos");           
        }
    }
    
    // obtiene los datos de obra social
    public int obtieneObraSocial() 
    {
        Legajolib.CargasSociales obrasocial=  fsLegajo.new CargasSociales();
        obrasocial.idLegajo = this.idLegajo;
        ResultSet resultados = null;
        resultados = obrasocial.consulta("idLegajo="+idLegajo
                        +" AND Vinculacion='Obra Social'");        
       
        try
        {
            if ( resultados.first())
            {       
                this.idObraSocial = resultados.getInt(2);
                aplic.idConcepto=4;
                aplic.nuevo(this);
                Imprime("Obra Social aplicada ");
                return 1;  
            }        
            else
            {
                Imprime("no se registro obra social");
                return 0;           
            }            
        }
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
            return 0;
        }
    }
    
    // obtiene los datos del sindicato
    public int obtieneSindicato() 
    {
        int valor=0;
        Legajolib.CargasSociales sindicatofs= fsLegajo.new CargasSociales();
        sindicatofs.idLegajo = this.idLegajo;
        ResultSet resultado=sindicatofs.consulta("idLegajo="+idLegajo
                                            +" AND Vinculacion='Sindicato'");
        try 
        {
            if(resultado.isFirst())  
            {
                try 
                {
                    this.idSindicato = resultado.getInt(1);
                    aplic.idConcepto = 5;
                    aplic.nuevo(this);
                    Imprime("sindicato aplicado ");
                    valor=1;
                } 
                catch (SQLException ex) 
                {
                    estado = ex.getMessage();
                }
            }
        } catch (SQLException ex) 
        {
            estado = ex.getMessage();
        }
         return valor;
        
    }
    
    //devuelve la antiguedad del empleado
    public int antiguedad(String fecha) throws SQLException
    {
        int devolver=0;
        String sentencia = "select datediff( curdate(),'"+fecha+"')/365";
        ResultSet verFecha = st.executeQuery(sentencia);
        if(verFecha.first())
        {
             devolver = verFecha.getInt(1);
             Imprime("Antiguedad: "+devolver);
             anti=devolver;
        }
        else
        {
             Imprime("No se pudo calcular la antiguedad");
        }
        return devolver;
    }
    
    //realiza el calculo de antiguedad del empleado
    public int devuelveAntiguedad() 
    {
        int ant=0;        
        try 
        {
            ant = this.antiguedad(this.fechaInicio);
        } 
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
        }        
        
        aplic.idConcepto=2;
        aplic.idRecibo=this.idRecibo;
        aplic.nuevo(this);
        Imprime("Antiguedad obtenida:");
        return ant;
    }
    
    // realiza el calculo de jubilacion del empleado
    public void devuelveJubilacion()
    {        
        aplic.idConcepto=6;
        aplic.nuevo(this);
        Imprime("Aportes jubilatorios aplicados ");
    }
    
    //realiza el calculo de art
    public void devuelveART()
    {
        ResultSet resultado = null;
        Legajolib.CargasSociales artL= fsLegajo.new CargasSociales();
        artL.idLegajo= this.idLegajo;
        resultado = artL.consulta("idLegajo="+idLegajo+" AND Vinculacion='Art'");
        try 
        {
            if(resultado.first())
            {
                this.idART= resultado.getInt("idEmpresa");
                this.totalDescuentos += this.idART; 
                Imprime("ART:"+idART);
                aplic.idConcepto=7;
                aplic.nuevo(this);
                Imprime("Art cargada");
            }
            else
            {
                Imprime("ART no asignada");
            }
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage();
        }        
    }
    
    //controla las inasistencias del mes
    public void inasistencias()
    {
        Legajolib.Inasistencia Faltas = fsLegajo.new Inasistencia();
        Faltas.condicion="(idLegajo ="+idLegajo+")"
                    + "  AND (fecha >= '"+periodoIni
                    +"' AND fecha <= '"+periodoFin+"') AND"
                    + "(justificada =0)";
        ResultSet resultado= Faltas.consulta(Faltas.condicion);
        try
        {
            if (!resultado.first())
            {           
                Imprime("El empleado no tiene inasistencias");           
            }
            else
            {  
                Imprime("El empleado registra inasistencias");
                while(resultado.isLast() == true) 
                {
                    this.diasTrabajados = this.diasTrabajados - 1;
                    resultado.next();                    
                }             
            }
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage();            
        }
    }
    
    //realiza el presentismo del mes
    public void presentismo() 
    {
        if(this.diasTrabajados ==30)
        {           
            aplic.idConcepto=3;
            aplic.idRecibo = this.idRecibo;
            aplic.nuevo(this);
            Imprime("Aplicado presentismo ");            
        }
        else
        {
            Imprime("El empleado no tiene presentismo");            
        }        
    } 
    
    //calcula  las horas extras del  empleado
    public void horasExtras() 
    {
        Legajolib.HorasExtras horasExtras = fsLegajo.new HorasExtras();
        horasExtras.condicion = "(idLegajo ="+idLegajo+")"
                                 + "AND (fecha BETWEEN '"+periodoIni+"'"
                                       + " AND '"+periodoFin+"')";
        ResultSet resultado = horasExtras.consulta();
        try 
        {
            if(resultado.first())
            {
                while(resultado != null)
                {
                    if(resultado.getInt("tipoHs")==1)
                    {
                        this.cantHs50 = this.cantHs50 +
                                        resultado.getInt("cantidadHs");
                    }
                    else
                    {
                        this.cantHs100 = this.cantHs100 +
                                        resultado.getInt("cantidadHs");
                    }
                    resultado.next();
                }
                Imprime("Horas al 50%: "+cantHs50);
                Imprime("Horas al 100%: "+cantHs100);            
            }
            else
            {
                Imprime("No existen hs extras registradas");            
            }
        } 
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
        }
    }
    
    //genera los totales de los recibos: remunerativo, 
    //no remunerativo y descuentos - verificar
    public float totalRecibo(int opcion)//actualizar
    {    
        Concepto.Aplica otrosConcep = fsConceptos.new Aplica();        
        ResultSet resultado = this.consultarecibo();
        
        otrosConcep.idRecibo = idRecibo; //conceptos
        ResultSet resulConcept = otrosConcep.consultaRecibo();
        ResultSet resulDetalle = null;
        float acu = 0;
        Imprime("Imprimiendo totales");        
        switch(opcion)
        {            //conceptos remunerativos
            case 1:     
                Imprime("Calculando total remunerativo");
                try
                {                    
                    while(!resulConcept.wasNull())
                    { 
                        detall.idConcepto = resulConcept.getInt("idConcepto");
                        resulDetalle=detall.consulta();
                        if(resulDetalle.getInt("tipo")==1)
                        {
                            acu = acu+resulConcept.getFloat("remunerativo");
                        }
                        if(resulConcept.isLast())
                        {
                            resulConcept.close();
                        }
                        else
                        {
                            resulConcept.next();
                        }                        
                    }
                    Imprime("Total remunerativos:"+acu);
                }
                catch (SQLException ex)
                {
                    estado = ex.getMessage();
                    Imprime(estado);
                }
            break;
            
            //conceptos no remunerativos
            case 2:
                 try 
                 {                    
                    while(!resulConcept.wasNull())
                    { 
                        detall.idConcepto = resulConcept.getInt("idConcepto");
                        resulDetalle=detall.consulta();
                        if(resulDetalle.getInt("tipo")==2)
                        {
                            acu = acu+resulConcept.getFloat("noremunerativo");
                        }    
                        if(resulConcept.isLast())
                        {
                            resulConcept.close();
                        }
                        else
                        {
                            resulConcept.next();
                        }
                    }
                    Imprime("Total no remunerativos:"+acu);
                 }
                 catch (SQLException ex) 
                 {
                     estado = ex.getMessage();
                     Imprime(estado);
                 }
            break;
            
            //Descuentos
            case 3:
              try 
              {                
                resulConcept.first();                
                while(!resulConcept.wasNull())
                { 
                   detall.idConcepto = resulConcept.getInt("idConcepto");
                   resulDetalle=detall.consulta();
                   if(resulDetalle.getInt("tipo")==3)
                   {
                      acu = acu+resulConcept.getFloat("descuento");
                   }    
                   if(resulConcept.isLast())
                   {
                       resulConcept.close();
                   }
                   else
                   {
                       resulConcept.next();
                   }
                }
                Imprime("Total descuentos:"+acu);
              }
              catch (SQLException ex)
              {
                  estado = ex.getMessage();
                  Imprime(estado);
              }
                
            break;
        }
        //Imprime("total:"+acu);
        return acu;
    }
    
    //realiza el recibo de sueldo
    public void  recibo() 
    {
        obtienePuesto();
        obtieneDatos();        
        horasExtras();        
        
        Imprime("generando recibo de sueldo");
        this.valores = idLegajo+","+estadoR+","+costoHs50+","+costoHs100
                            +","+idPuesto+",'"+periodoIni+"','"+periodoFin+"','"
                            +emision+"',"+cantHs+","+costoHs+","
                            +cantHs50+","+cantHs100+","+idObraSocial+
                            ","+idSindicato+","+idART+","
                            +diasTrabajados+","+anti+","
                            +totalRemunerativo+","+totalNoRemunerativo+","+
                            totalDescuentos+","+total; 
        
        if(this.insertaSQL()==1)
        {
            this.campos="MAX( idRecibo )";
            ResultSet ultima = null;
            ultima = this.consultaSQL();
            try 
            {
                idRecibo = ultima.getInt(1);
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
            Imprime("Recibo de sueldo nro: "+idRecibo);
        }
        else
        {
            Imprime("No se pudo imprimir el recibode sueldo");
        }
        aplic.idRecibo= this.idRecibo;
        presentismo();
        devuelveAntiguedad();
        asignaciones();//verificar
        try
        {
            preajustados();//modificar aqui
        } 
        catch (ParseException ex)
        {
            Imprime("Error en preajustados");
        }
        this.campos = "idLegajo,estadoR,costoHs50,costoHs100,idPuesto,periodoIni,periodoFin,emision,"+
                        "basico,CantHs,costoHs,cantHs50,"+
                        "CantHs100,idObraSocial,idSindicato,idART,diasTrabajados"
                        +",anti,totalRemunerativo,totalNoRemunerativo,totalDescuento,total";
        totalRemunerativo = totalRecibo(1);
        totalNoRemunerativo = totalRecibo(2);
        
        //descuentos
        obtieneObraSocial();
        obtieneSindicato();
        devuelveJubilacion();
        devuelveART();        
              
        total = totalRemunerativo + totalNoRemunerativo - totalDescuentos;
        modificaRecibo();        
    }
    
    //realiza la consulta de un recibo de sueldo
    public ResultSet consultarecibo()
    {
        this.condicion= "idRecibo="+this.idRecibo;
        return this.consultaSQL();
    }
    
    //realiza la carga de los datos en el recibo
    public void cargaRecibo()
    {
        ResultSet carga = consultarecibo();
        try 
        {// sn terminar
            anti = carga.getInt("anti");            
            basico = carga.getFloat("basico");
            cantHs = carga.getInt("CantHs");
            cantHs100 = carga.getInt("CantHs100");
            cantHs50  = carga.getInt("cantHs50");
            costoHs = carga.getFloat("costoHs");
            costoHs100 = carga.getFloat("costoHs100");
            costoHs50 = carga.getFloat("costoHs50");
            diasTrabajados = carga.getInt("diasTrabajados");
            
        }
        catch (SQLException ex)
        {
            Imprime("Carga no realizada");
        }
    }
    
    //realiza la consulta de conceptos de un recibo
    public ResultSet consultaConceptos()
    {
        Concepto.Aplica concept= fsConceptos.new Aplica();
        concept.condicion= "idRecibo="+this.idRecibo;
        return concept.consultaSQL();
    } 
    
    //modifica los valores del recibo de sueldo
    public int modificaRecibo()
    {
        this.campos = "idLegajo,estadoR,costoHs50,costoHs100,idPuesto,periodoIni,periodoFin,emision,"+
                        "basico,CantHs,costoHs,cantHs50,"+
                        "CantHs100,idObraSocial,idSindicato,idART,diasTrabajados"
                        +",anti,totalRemunerativo,totalNoRemunerativo,totalDescuento,total";
        this.valores = idLegajo+","+estadoR+","+costoHs50+","+costoHs100
                            +","+idPuesto+",'"+periodoIni+"','"+periodoFin+"','"
                            +emision+"',"+cantHs+","+costoHs+","
                            +cantHs50+","+cantHs100+","+idObraSocial+
                            ","+idSindicato+","+idART+","
                            +diasTrabajados+","+anti+","
                            +totalRemunerativo+","+totalNoRemunerativo+","+
                            totalDescuentos+","+total; 
        return this.modificaSQL();
    }
    
    //realiza la actualizacion del recibo de sueldo
    public int reciboUpdate() throws ParseException
    {
        ResultSet resultado = consultarecibo();
        try
        {
            idLegajo = resultado.getInt("idLegajo");
        }
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
                    Imprime(estado);        }
        obtienePuesto();
        obtieneDatos();        
        horasExtras();
        presentismo();
        devuelveAntiguedad();
        asignaciones();
        preajustados();
        this.campos = "idLegajo,estadoR,costoHs50,costoHs100,idPuesto,periodoIni,periodoFin,emision,"+
                        "basico,CantHs,costoHs,cantHs50,"+
                        "CantHs100,idObraSocial,idSindicato,idART,diasTrabajados"
                        +",anti,totalRemunerativo,totalNoRemunerativo,totalDescuento,total";
        totalRemunerativo = totalRecibo(1);
        totalNoRemunerativo = totalRecibo(2);
        
        //descuentos
        obtieneObraSocial();
        obtieneSindicato();
        devuelveJubilacion();
        devuelveART();        
              
        total = totalRemunerativo + totalNoRemunerativo - totalDescuentos;
        return modificaRecibo();
    }
    
    //realiza la baja de un recibo de sueldo
    public int bajaRecibo()
    {   
        //elimina los conceptos del recibo
        Concepto.Aplica conceptosfs = fsConceptos.new Aplica();
        conceptosfs.idRecibo = this.idRecibo;
        conceptosfs.condicion="idRecibo="+this.idRecibo;
        if(conceptosfs.borraSQL()==1)
        {    
            this.condicion = "idRecibo="+this.idRecibo;
            Imprime("Se ha eliminado el recibo de sueldo");
            return this.borraSQL();            
        }
        else
        {
            Imprime("No se ha eliminado el recibo de sueldo");
            return 0;
        }
    }
    
    // controla y agrega las asignaciones correspondientes, falta terminar
    public void asignaciones() 
    {       
        try 
        {
            Legajolib control = new Legajolib();
            Legajolib.Asignaciones familiares = control.new Asignaciones();
            
            familiares.idLegajo = this.idLegajo;            
            ResultSet vector = familiares.consulta();
            vector.first(); 
            Imprime("Buscando asignaciones familiares:");          
            while(!vector.wasNull()) 
            {   
                Concepto concep = new Concepto();
                Concepto.Aplica asignacion = concep.new Aplica();
                asignacion.idRecibo = this.idRecibo;
                asignacion.unidad = 1;
               
                if(vector != null)
                {                
                    try
                    {
                        switch(vector.getInt("idvinculo"))
                        {   
                            //Marido
                            case 1:
                                Imprime("Asignacion por conyugue: Marido");
                                asignacion.idConcepto=1;
                            break;
                            
                            //Mujer
                            case 2:
                                Imprime("Asignacion por conyugue: Mujer");
                                asignacion.idConcepto=1;
                            break;
                               
                            // Hijo o hija
                            case 3:
                                Imprime("Asignacion por hijo");
                                asignacion.idConcepto=2;
                            break;
                                
                            //hijo o hija discapacitada
                            case 4:
                                Imprime("Asignacion por hijo Discapacitado");
                                asignacion.idConcepto=3;
                            break;        
                        }
                    }
                    catch (SQLException ex) 
                    {
                        estado = ex.getMessage();
                    }
                }     
                
                
                if(asignacion.idConcepto != 0)
                {
                    asignacion.nuevo(this);
                    Imprime("Asignacion cagada correctamente");
                }
                else
                {
                    //Imprime("Asignacion no cargada");
                    vector.close();
                }
                vector.next();          
            }
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage(); 
        }
    }
    
    //genera el concepto de vacaciones
    public void vacaciones()
    {
        Legajolib.Licencias lic = fsLegajo.new Licencias();
        lic.idLegajo = this.idLegajo;
        try 
        {
            int ant = antiguedad(FechaActual());
            if (ant>=20)
            {
                lic.tipoLic=9;
                lic.alta();
            }
            else
            {
                if (ant>=10)
                {
                    lic.tipoLic=10;
                    lic.alta();
                }
                else
                {
                    if (ant>=5)
                    {
                        lic.tipoLic=11;
                        lic.alta();
                    }  
                    else
                    {
                        lic.tipoLic=12;
                        lic.alta();
                    }
                }
            }
        }
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
        }
    }    
    

    //aplica los conceptos pre ajustados
    public void preajustados() throws ParseException 
    {   
        Concepto.Aplica aplicarConcep = fsConceptos.new Aplica();
        aplicarConcep.idRecibo = this.idRecibo; 
        
        Concepto.Control concep = fsConceptos.new Control();
        concep.idLegajo = this.idLegajo;     
        ResultSet resultado = concep.consulta();
        
        SimpleDateFormat inicioF = new SimpleDateFormat("yyyy-MMM-dd");
        SimpleDateFormat finF = new SimpleDateFormat("yyyy-MMM-dd");
        Imprime("Buscando conceptos predefinidos");
        try
        {
            resultado.first();
            while(resultado.isLast())
            {
                if(resultado.getInt("estado")!=0)
                {//estado activo
                    if(resultado.getInt("tipo")!=2)
                    {    
                        aplicarConcep.idConcepto = resultado.getInt("idConcepto");
                        aplicarConcep.unidad = resultado.getFloat("unidades");  
                        aplicarConcep.nuevo(this);
                    
                        if (resultado.getInt("tipo")==0)
                        {//concepto por cantidad de veces
                            concep.idConcepto = resultado.getInt("idConcepto");
                            concep.unidades = resultado.getFloat("unidades");
                            concep.tipo = resultado.getInt("tipo");
                            concep.inicio = resultado.getString("inicio");
                            concep.fin = resultado.getString("fin");
                            concep.estadoConcepto = resultado.getInt("estado")-1;
                            
                            concep.modifica();
                        }  
                    }
                    else
                    {//concepto temporal
                        if(resultado.getDate("inicio").before(finF.parse(this.periodoFin)) 
                                ||
                           inicioF.parse(this.periodoIni).before(resultado.getDate("fin")))
                        {
                             aplicarConcep.idConcepto = resultado.getInt("idConcepto");
                             aplicarConcep.unidad = resultado.getFloat("unidades");  
                             aplicarConcep.nuevo(this);
                        }
                        else
                        {                     
                            Imprime("Periodo fuera de fecha");
                            concep.idConcepto= resultado.getInt("idConcepto");
                            concep.unidades = resultado.getFloat("unidades");
                            concep.tipo = resultado.getInt("tipo");
                            concep.inicio = resultado.getString("inicio");
                            concep.fin = resultado.getString("fin");
                            concep.estadoConcepto =0;
                            concep.modifica();
                        }
                    }
                }
                 resultado.next();  
            }
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage();
            Imprime(estado);
        }
    }
    
    //realiza el concepto del sac
    //utilizando el promedio de los sueldos
    public void SAC() //sin terminar
    {
        Imprime("Generando SAC");
        obtienePuesto();
        obtieneDatos();        
        this.condicion = "periodoIni>='"+periodoIni+"' AND periodoFin<='" +
                        periodoFin+"' AND idLegajo="+idLegajo; 
        Imprime("Consultando datos del periodo");       
        ResultSet resultado = this.consultaSQL();
        
        float acum = 0; //acumular el basico
        int di = 0; // dias trabajados  
        int i=0;
        float remu = 0;
        float noremu = 0;
        try {
            resultado.first();
            while (!resultado.isLast())
            {              
                acum += resultado.getFloat("basico");
                di += resultado.getInt("diasTrabajados");                
                remu += resultado.getInt("totalRemunerativo");
                noremu += resultado.getInt("totalNoRemunerativo");
                i++;
                resultado.next();
            }
            this.diasTrabajados = di;
            //this.basico = obtieneBasico(acum/(i*2));
            Imprime("SAC:"+basico);
            this.totalRemunerativo = remu/6;
            Imprime("Total remunerativo:"+totalRemunerativo);
            this.totalNoRemunerativo=noremu/6;
            Imprime("Total no remunerativo: "+totalNoRemunerativo);
            obtieneObraSocial();
            obtieneSindicato();
            devuelveAntiguedad();
            devuelveJubilacion();
            devuelveART();
            this.total=totalRemunerativo+totalNoRemunerativo-totalDescuentos;
            this.valores = idLegajo+","+estadoR+","+costoHs50+","+costoHs100
                            +","+idPuesto+",'"+periodoIni+"','"+periodoFin+"','"
                            +emision+"',"+cantHs+","+costoHs+","
                            +cantHs50+","+cantHs100+","+idObraSocial+
                            ","+idSindicato+","+idART+","
                            +diasTrabajados+","+anti+","
                            +totalRemunerativo+","+totalNoRemunerativo+","+
                            totalDescuentos+","+total; 
            Imprime("Guardando el SAC");
            this.insertaSQL();
        }
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
        }
    }
}
