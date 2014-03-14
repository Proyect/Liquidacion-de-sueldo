/*
 *Esta clase es realizar la liquidacion de haberes
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/** * Ariel Marcelo Diaz*/

public class Liquidacion extends libSentenciasSQL
{
    int idLegajo = 0;
    float costoHs = 0;
    float costoHs50 = 0;
    float costoHs100 = 0;
    int idPuesto = 0;
    String periodoIni = "";
    String periodoFin = "";            
    String emision = "";
    float obraSocial = 0;
    int idObraSocial = 0;
    float sindicato = 0; 
    int idSindicato = 0;
    float presentismo = 0;
    float basico = 0;
    float antiguedad=0;
    int diasTrabajados=0;// verificar
    int cantHs = 0;
    int cantHs50 = 0;
    int cantHs100 = 0;
    float jubilacion = 0;
    float art = 0;
    String fechaInicio = "";
    Legajolib fsLegajo = new Legajolib();
    Concepto fsConceptos = new Concepto(); 
    //constructor
    public Liquidacion()
    {
        this.tabla = "recibos";
        this.campos = "idLegajo,costoHs50,costoHs100,idPuesto,periodo,emision,"+
                        "obraSocial,sindicato,presentismo,basico,CantHs,costoHs,cantHs50,costoHs50,"+
                        "costoHs100,CantHs100,jubilacion,art,periodoIni,periodoFin,idObraSocial,"+
                        "idSindicato,art,idART"; 
    }
    
    //obtiene el puesto del empleado en cuestion
    public ResultSet obtienePuesto()
    {   
        ResultSet reg = null;     
        Legajolib.Puestos puesto = fsLegajo.new Puestos();       
        puesto.condicion = "idLegajo="+this.idLegajo+ " AND estado=1";
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
        return reg;
    }
    
    // obtiene los datos basicos de la liquidacion
    public int obtieneDatos()
    {
        Complementarios complemento= new Complementarios();
        Complementarios.Cargos cargo = complemento.new Cargos();
        cargo.idPuesto = this.idPuesto;
        ResultSet datos = cargo.consulta();
        if (datos != null)
        {
            try 
            {
                this.basico = datos.getFloat("basico");
                Imprime("Basico: "+this.basico);
                this.costoHs = datos.getFloat("costoHs");
                Imprime("Costo Hs:"+this.costoHs);
                this.costoHs50 = datos.getFloat("costoHs50");
                Imprime("Costo Hs al 50%:"+this.costoHs50);
                this.costoHs100 = datos.getFloat("costoHs100");
                Imprime("Costo Hs al 100$:"+this.costoHs100);
                this.cantHs=datos.getInt("hsSemanales")*4;
                Imprime("cantidad de hs: "+this.cantHs);
            }
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
                Imprime(estado);
            }
            return 1;
        }
        else
        {
            Imprime("Datos no obtenidos");
            return 0;
        }
    }
    
    // obtiene los datos de obra social
    public int obtieneObraSocial() throws SQLException
    {
        Legajolib.ObraSocial obrasocial=  fsLegajo.new ObraSocial();
        obrasocial.idLegajo = this.idLegajo;
        ResultSet resultados = obrasocial.consulta();
        if (resultados != null)
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
    
    // obtiene los datos del sindicato
    public int obtieneSindicato() throws SQLException 
    {
        int valor=0;
        Legajolib.Sindicato sindicatofs= fsLegajo.new Sindicato();
        sindicatofs.idLegajo = this.idLegajo;
        ResultSet resultado=sindicatofs.consulta();
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
    public int devuelveAntiguedad() throws SQLException
    {
        int ant=this.antiguedad(this.fechaInicio);        
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
        fsConceptos.idFormula=4;
        this.art=this.basico*fsConceptos.formulas();
        Imprime("ART: "+this.art);
        return this.art;
    }
    
    //realiza el presentismo del mes
    public float presentismo() throws SQLException
    {
        Legajolib.Inasistencia Faltas = fsLegajo.new Inasistencia();
        Faltas.condicion="(idLegajo ="+idLegajo+")"
                    + "  AND (fecha >= '"+periodoIni+"' AND fecha <= '"+periodoFin+"') AND"
                    + "(justificada =0);";
        ResultSet resultado= Faltas.consulta(Faltas.condicion);
        if (!resultado.first())
        {
            Imprime("El empleado tiene presentismo");
            fsConceptos.idFormula = 3;
            this.presentismo = this.basico*fsConceptos.formulas();
            Imprime("Presentismo: "+this.presentismo);
            return this.presentismo;
        }
        else
        {
            Imprime("El empleado no tiene presentismo");
            return 0;
        }
    } 
    
    //calcula  las horas extras del  empleado
    public int horasExtras() throws SQLException
    {
        Legajolib.HorasExtras horasExtras = fsLegajo.new HorasExtras();
        horasExtras.condicion = "(idLegajo ="+idLegajo+")"
                                 + "AND (fecha BETWEEN '"+periodoIni+"'"
                                       + " AND '"+periodoFin+"')";
        ResultSet resultado = horasExtras.consulta();
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
            return 1;
        }
        else
        {
            Imprime("No existen hs extras registradas");
            return 0;
        }
        
    }        
    // faltan conceptos adjuntos
}
