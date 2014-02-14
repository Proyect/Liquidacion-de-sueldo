/*
 *Esta clase es realizar la liquidacion de haberes
 */
package sistemaliquidaciondehaberes;

/** * Ariel Marcelo Diaz*/

public class Liquidacion extends libSentenciasSQL
{
    int idLegajo = 0;
    float costoHs50 = 0;
    float costoHs100 = 0;
    int idPuesto = 0;
    String periodo = "";
    String emision = "";
    float obraSocial = 0;
    float sindicato = 0;
    float presentismo = 0;
    float basico = 0;
    int cantHs50 = 0;
    int cantHs100 = 0;
    float jubilacion = 0;
    float art = 0;
    //constructor
    public Liquidacion()
    {
        this.tabla = "recibos";
        this.campos = "idLegajo,costoHs50,costoHs100,idPuesto,periodo,emision,obraSocial,sindicato,presentismo,basico,cantHs50,CantHs100,jubilacion,art"; 
    }
}
