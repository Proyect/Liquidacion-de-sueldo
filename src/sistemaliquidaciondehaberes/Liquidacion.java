/*
 *Esta clase es realizar la liquidacion de haberes
 */
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;


/** * Ariel Marcelo Diaz*/

public class Liquidacion extends libSentenciasSQL
{
    int idLegajo = 0;
    float costoHs = 0;
    float costoHs50 = 0;
    float costoHs100 = 0;
    int idPuesto = 0;
    String periodo = "";
    String emision = "";
    float obraSocial = 0;
    int idObraSocial = 0;
    float sindicato = 0;  
    float presentismo = 0;
    float basico = 0;
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
        this.campos = "idLegajo,costoHs50,costoHs100,idPuesto,periodo,emision,obraSocial,sindicato,presentismo,basico,cantHs50,CantHs100,jubilacion,art"; 
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
    public int obtieneSindicato()
    {
    }
}
