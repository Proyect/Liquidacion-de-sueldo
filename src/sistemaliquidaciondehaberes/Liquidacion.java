/*
 *Esta clase es realizar la liquidacion de haberes
 */
package sistemaliquidaciondehaberes;

import java.lang.String;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/** * Ariel Marcelo Diaz*/

public class Liquidacion extends libSentenciasSQL
{
    int idRecibo=0;
    int idLegajo = 0;
    float costoHs = 0;
    float costoHs50 = 0;
    float costoHs100 = 0;
    int idPuesto = 0;
    String periodoIni = "";
    String periodoFin = "";            
    String emision = FechaActual();
    int dias=0; // dias a trabajar
    int diasTrabajados = 0;
    float obraSocial = 0;
    int idObraSocial = 0;
    float sindicato = 0; 
    int idSindicato = 0;
    float presentismo = 0;
    float basico = 0;
    float antiguedad=0;    
    int cantHs = 0;
    int cantHs50 = 0;
    int cantHs100 = 0;
    float jubilacion = 0;
    float art = 0;
    int idART = 0;
    float totalRemunerativo =0;
    float totalNoRemunerativo = 0;
    float totalDescuentos = 0;
    float total = 0;
    String fechaInicio = "";
    Legajolib fsLegajo = new Legajolib();
    Concepto fsConceptos = new Concepto(); 
    //constructor
    public Liquidacion()
    {
        this.tabla = "recibos";
        this.campos = "idLegajo,costoHs50,costoHs100,idPuesto,periodoIni,periodoFin,emision,"+
                        "obraSocial,sindicato,presentismo,basico,CantHs,costoHs,cantHs50,"+
                        "CantHs100,jubilacion,art,idObraSocial,idSindicato,idART,diasTrabajados"
                        +",antiguedad,totalRemunerativo,totalNoRemunerativo,total"; 
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
    public float obtieneBasico(float basico)
    {
        //Imprime("basico:"+basico+" trab: "+diasTrabajados+" dias:"+dias);
        float resultado = (basico * this.diasTrabajados)/this.dias;        
        return resultado;
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
                this.basico = obtieneBasico(datos.getFloat("basico"));
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
        resultados = obrasocial.consulta("idLegajo="+idLegajo+" AND Vinculacion='Obra Social'");
        try
        {
            if ( resultados.next())
            {       
                this.idObraSocial = resultados.getInt(2);
                fsConceptos.idFormula = 2;            
                this.obraSocial = this.basico*fsConceptos.formulas();
                Imprime("Obra Social: "+this.obraSocial);            
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
        ResultSet resultado=sindicatofs.consulta("idLegajo="+idLegajo+" AND Vinculacion='Sindicato'");
        try 
        {
            if(resultado.isFirst())  
            {
                try 
                {
                    this.idSindicato = resultado.getInt(1);
                    fsConceptos.idFormula = 2;
                    this.sindicato= this.basico*fsConceptos.formulas();
                    Imprime("sindicato: "+this.sindicato);
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
        fsConceptos.idFormula=6;
        this.antiguedad = ant*this.basico*fsConceptos.formulas();
        Imprime("Antiguedad valor:"+this.antiguedad);
        return ant;
    }
    
    // realiza el calculo de jubilacion del empleado
    public float devuelveJubilacion()
    {        
        fsConceptos.idFormula=5;
        this.jubilacion=this.basico*fsConceptos.formulas();
        Imprime("Aportes jubilatorios: "+this.jubilacion);
        return this.jubilacion;
    }
    
    //realiza el calculo de art
    public float devuelveART()
    {
        ResultSet resultado = null;
        Legajolib.CargasSociales art= fsLegajo.new CargasSociales();
        art.idLegajo= this.idLegajo;
        resultado = art.consulta("idLegajo="+idLegajo+" AND Vinculacion='Art'");
        try 
        {
            if(resultado.next())
            {
                this.idART= resultado.getInt("idEmpresa");
                Imprime("ART:"+idART);
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
        fsConceptos.idFormula=4;
        this.art=this.basico*fsConceptos.formulas();
        Imprime("ART: "+this.art);
        return this.art;
    }
    
    //realiza el presentismo del mes
    public float presentismo() 
    {
        Legajolib.Inasistencia Faltas = fsLegajo.new Inasistencia();
        Faltas.condicion="(idLegajo ="+idLegajo+")"
                    + "  AND (fecha >= '"+periodoIni+"' AND fecha <= '"+periodoFin+"') AND"
                    + "(justificada =0);";
        ResultSet resultado= Faltas.consulta(Faltas.condicion);
        try
        {
            if (!resultado.first())
            {           
                Imprime("El empleado tiene presentismo");
                fsConceptos.idFormula = 3;
                this.presentismo = this.basico*fsConceptos.formulas();
                Imprime("Presentismo: "+this.presentismo);
                while(resultado.isLast() == true) // controlar aqui, 
                {
                    this.diasTrabajados = this.diasTrabajados - 1;
                    resultado.next();
                    Imprime("entro aqui");
                }            
                return this.presentismo;
            }
            else
            {
                Imprime("El empleado no tiene presentismo");
                return 0;
            }
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage();
            return 0;
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
                        this.cantHs50 = this.cantHs50 + resultado.getInt("cantidadHs");
                    }
                    else
                    {
                        this.cantHs100 = this.cantHs100 + resultado.getInt("cantidadHs");
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
    
    //realiza el recibo de sueldo
    public void  recibo() 
    {
        obtienePuesto();
        obtieneDatos();
        horasExtras();
        obtieneObraSocial();
        obtieneSindicato();
        devuelveAntiguedad();
        devuelveJubilacion();
        devuelveART();
        presentismo();
        
        Imprime("guardando recibo de sueldo");
        this.valores = idLegajo+","+costoHs50+","+costoHs100+","+idPuesto+",'"+
                       periodoIni+"','"+periodoFin+"','"+emision+"',"+obraSocial+
                       ","+sindicato+","+presentismo+","+basico+","+cantHs
                        +","+costoHs+","+cantHs50+","+cantHs100+","
                        +jubilacion+","+art+","+idObraSocial+","+idSindicato+","+
                        idART+","+diasTrabajados+","+antiguedad+","+
                        totalRemunerativo+","+totalNoRemunerativo+","+total; 
        
        this.insertaSQL();
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
        asignaciones();
    }
    
    //realiza la consulta de un recibo de sueldo
    public ResultSet consultarecibo()
    {
        this.condicion= "idRecibo="+this.idRecibo;
        return this.consultaSQL();
    }
    
    //realiza la consulta de conceptos de un recibo
    public ResultSet consultaConceptos()
    {
        Concepto.Aplica concept= fsConceptos.new Aplica();
        concept.condicion= "idRecibo="+this.idRecibo;
        return concept.consultaSQL();
    }
    
    //realiza el vector del recibo de sueldo - aun no terminada
    public ArrayList vectorRecibo()
    {   //variables
        ArrayList<String[]> list = new ArrayList<String[]>();
        String[] fila = new String[5];
        ResultSet resultado = consultarecibo();
        Empresaslib empresa = new Empresaslib();
        ResultSet auxiliar = null;
        int sind = 0;
        Concepto.Detalle detalle = fsConceptos.new Detalle();
        
        try
        {      
            fila[0] = "Basico";
             fila[1] = resultado.getString("diasTrabajados");
             fila[2] = resultado.getString("basico");             
             list.add(fila);             
              fila[1] = null;
              Imprime( list.get(0)[0]);

            fila[0] = "Presentismo";
             fila[2] = String.valueOf(resultado.getFloat("presentismo"));
             list.add(fila);
             Imprime( list.get(1)[0]);
         
             
             fila[0] = "Antiguedad";
             fila[2] = resultado.getString("antiguedad");
             list.add(fila);
             Imprime( list.get(2)[0]);
             
            auxiliar = empresa.consulta("idEmpresa="+resultado.getInt("idObraSocial"));
            fila[0] = "Obra Social:"+auxiliar.getString("razonSocial"); 
            fila[4] = String.valueOf(resultado.getFloat("obrasocial"));            
            list.add(fila);
            Imprime( list.get(3)[0]);
            
            auxiliar = empresa.consulta("idEmpresa="+resultado.getInt("idART"));
            fila[0] = "ART:"+auxiliar.getString("razonSocial");  
            fila[4] = String.valueOf(resultado.getFloat("art"));
            list.add(fila);
            Imprime( list.get(4)[0]);
            
            sind = resultado.getInt("idSindicato");
            if(sind !=0)
            {    
                auxiliar = empresa.consulta("idEmpresa="+sind);
                fila[0] = "Sindicato:"+auxiliar.getString("razonSocial");  
                fila[4] = String.valueOf(resultado.getFloat("sindicato"));
                list.add(fila);
                Imprime( list.get(5)[0]);
            }
                        
            resultado = consultaConceptos();
            fila[0]= "";fila[1]= "";fila[2]= "";fila[3]= "";fila[4]= "";
            resultado.first();
            while (resultado.isLast()) //verificar
            {               
                detalle.idConcepto=resultado.getInt("idConcepto");
                auxiliar = detalle.consulta();
                fila[0] = auxiliar.getString("nombreCons");
                Imprime(fila[0]);
                fila[1] = String.valueOf(resultado.getFloat("unidad"));
                switch(auxiliar.getInt("tipo"))
                {
                    case 1:
                        fila[2] = String.valueOf(resultado.getFloat("valor"));
                    break;
                    
                    case 2:
                        fila[3] = String.valueOf(resultado.getFloat("valor"));
                    break;
                        
                    case 3:
                        fila[4] = String.valueOf(resultado.getFloat("valor"));
                    break;
                }                
                resultado.next();
            }
            
        }
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
        }
        return list;
    } 
    
      
    
    //modifica los valores del recibo de sueldo
    public int modificaRecibo()
    {
        this.valores = idLegajo+","+costoHs50+","+costoHs100+","+idPuesto+",'"+
                       periodoIni+"','"+periodoFin+"','"+emision+"',"+obraSocial+
                       ","+sindicato+","+presentismo+","+basico+","+cantHs
                        +","+costoHs+","+cantHs50+","+cantHs100+","
                        +jubilacion+","+art+","+idObraSocial+","+idSindicato+","+
                        idART+","+diasTrabajados+","+antiguedad+","+
                        totalRemunerativo+","+totalNoRemunerativo+","+total; 
        return this.modificaSQL();
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
        Legajolib control = new Legajolib();
        Legajolib.Asignaciones familiares = control.new Asignaciones();
        Imprime("Buscando asignaciones familiares:");
        familiares.idLegajo = this.idLegajo;
        ResultSet  vector = null;
        vector = familiares.consulta();
        while((vector != null) )
        {   
            Concepto concep = new Concepto();
            Concepto.Aplica asignacion = concep.new Aplica();
            asignacion.idRecibo = this.idRecibo;
            asignacion.unidad = 1;
            asignacion.tipo = 2;
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
                asignacion.nuevo();
                Imprime("Asignacion cagada correctamente");
            }
            else
            {
                Imprime("Asignacion no cargada");//optimizar
            }
            
            try 
            {
                if(!vector.isLast())
                {
                    vector.next();
                }
                else
                {
                    vector =null;
                }
            } catch (SQLException ex) {
                estado = ex.getMessage();
            }
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
    public void preajustados()
    {
        Concepto.Control concep = fsConceptos.new Control();
        Concepto.Aplica aplicarConcep = fsConceptos.new Aplica();
        concep.idLegajo = this.idLegajo;  
        aplicarConcep.idRecibo = this.idRecibo;    
        ResultSet resultado = concep.consulta();
        try
        {
            resultado.first();
            while(resultado.isLast())
            {
                 aplicarConcep.idConcepto = resultado.getInt("idConcepto");
                 aplicarConcep.unidad = resultado.getFloat("unidades");  
                 aplicarConcep.nuevo();
                 resultado.next();
            }
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage();
        }
    }
    
    //realiza el concepto del sac
    //utilizando el promedio de los sueldos
    public void SAC()
    {
        Imprime("Generando SAC");
        obtienePuesto();
        obtieneDatos();        
        this.condicion = "periodoIni>='"+periodoIni+"' AND periodoFin<='" + periodoFin+"' AND"
                        +" idLegajo="+idLegajo;
        ResultSet resultado = this.consultaSQL();
        float acum = 0; //acumular el basico
        int di = 0; // dias trabajados  
        int i=0;
        try {
            resultado.first();
            while (!resultado.isLast())
            {              
                acum += resultado.getFloat("basico");
                di += resultado.getInt("diasTrabajados");                  
                i++;
                resultado.next();
            }
            this.diasTrabajados = di;
            this.basico = obtieneBasico(acum/(i*2));     
            Imprime("basico:"+basico);
            obtieneObraSocial();
            obtieneSindicato();
            devuelveAntiguedad();
            devuelveJubilacion();
            devuelveART();
            this.valores = idLegajo+","+costoHs50+","+costoHs100+","+idPuesto+",'"+
                       periodoIni+"','"+periodoFin+"','"+emision+"',"+obraSocial+
                       ","+sindicato+","+presentismo+","+basico+","+cantHs
                        +","+costoHs+","+cantHs50+","+cantHs100+","
                        +jubilacion+","+art+","+idObraSocial+","+idSindicato+","+
                        idART+","+diasTrabajados+","+antiguedad+","+
                        totalRemunerativo+","+totalNoRemunerativo+","+total; 
            Imprime("Guardando el SAC");
            this.insertaSQL();
        }
        catch (SQLException ex) 
        {
            estado = ex.getMessage();
        }
    }
}
