/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

//librerias
import com.itextpdf.text.*; 
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Imprime 
{    
    Legajolib leg = new Legajolib();
    Concepto con = new Concepto();
    Concepto.Aplica apli = con.new Aplica();
    Concepto.Detalle det = con.new Detalle();
    Empresaslib emp = new Empresaslib();
    Personaslib pers = new Personaslib();
    String destino ="D:/recibo.pdf";
    Document documento = null;
    
    PdfWriter writer = null;
    //constructor
    public Imprime()
    {        
        documento = new Document();
        documento.addAuthor("Ariel Marcelo Diaz");         
    }
    
    
    //imprime recibo de sueldo
    public void recibo(Liquidacion liq) throws SQLException  
    {           
        leg.idLegajo = liq.idLegajo;
        ResultSet resultadoLeg = leg.consulta(); //datos del recibo
        
        apli.idRecibo = liq.idRecibo;
        ResultSet resultadoConcep = apli.consultaRecibo();
        try 
        {
             pers.idPersona = resultadoLeg.getInt("idPersona");
             ResultSet resultadoPers = pers.consulta(); //datos de persona             
             ResultSet resultadoEmp = emp.consulta("idEmpresa="+1); //datos empresa
        
            writer.getInstance(documento, new FileOutputStream(destino));            
            documento.addTitle("Recibo de sueldo");    
            documento.open();             
            documento.add(new Paragraph("Empresa:"+resultadoEmp.getString("razonSocial")
                    +"   Original    Periodo:"+liq.periodoIni+" - "+liq.periodoFin+
                    "\n \r"+
                    "CUIT:"+resultadoEmp.getString("cuit")+" - Antiguedad:"+liq.anti
                    +"\n \r"+
                    "Apellido y Nombre:"+resultadoPers.getString("apellido")+", "
                    + resultadoPers.getString("nombre")+" CUIL:"
                    +resultadoPers.getString("cuil")+" Fecha Nac:"+
                    resultadoPers.getString("fechaNac")+"\n \r"));
            //genrando la tabla
            PdfPTable table = new PdfPTable(5);            
            table.addCell("Concepto");
            table.addCell("Unidad");
            table.addCell("Remunerativo");
            table.addCell("No Remunerativo");
            table.addCell("Descuento");
            
            table.addCell("Basico");
            table.addCell("");
            table.addCell(""+liq.basico);
            table.addCell("");
            table.addCell("");
            
            table.addCell("Antiguedad");
            table.addCell("");
            table.addCell(""+liq.antiguedad);
            table.addCell("");
            table.addCell("");
            
            if(liq.presentismo != 0)
            {
                table.addCell("Presentismo");
                table.addCell("");
                table.addCell(""+liq.presentismo);
                table.addCell("");
                table.addCell("");
            }
            // hacer lo de hs 
            
            boolean salir=true;
            resultadoConcep.first();
            ResultSet resultadoDet = null;
            while(!resultadoConcep.wasNull() && salir)
            {
                det.idConcepto = resultadoConcep.getInt("idConcepto");
                resultadoDet = det.consulta();
                if (resultadoDet.getInt("tipo")==1)
                {
                    table.addCell(resultadoDet.getString("nombreCons"));
                    table.addCell(""+resultadoConcep.getFloat("unidad"));
                    table.addCell(""+resultadoConcep.getFloat("remunerativo"));
                    table.addCell("");
                    table.addCell("");
                }
                if(resultadoConcep.isLast())
                {                   
                    salir=false;                    
                }    
                else
                {
                    resultadoConcep.next();
                }                
            }    
            
            resultadoConcep.first();
            salir=true;
            resultadoDet = null;
            while(!resultadoConcep.wasNull() && salir)
            {
                if(resultadoConcep.getInt("idConcepto") !=0)
                {    
                    det.idConcepto = resultadoConcep.getInt("idConcepto");
                    resultadoDet = det.consulta();
                    if (resultadoDet.getInt("tipo")==2)
                    {
                        table.addCell(resultadoDet.getString("nombreCons"));
                        table.addCell(""+resultadoConcep.getFloat("unidad"));
                        table.addCell("");
                        table.addCell(""+resultadoConcep.getFloat("noremunerativo"));
                        table.addCell("");
                    }
                }
                if(resultadoConcep.isLast())
                {
                    salir=false;                   
                }    
                else
                {
                    resultadoConcep.next();
                }
            }
            
            table.addCell("Obra Social");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell(""+liq.obraSocial);
            
            table.addCell("ART");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell(""+liq.art);
            
            if (liq.sindicato != 0)
            {
                table.addCell("Sindicato");
                table.addCell("");
                table.addCell("");
                table.addCell("");
                table.addCell(""+liq.sindicato);
            }
            
            resultadoConcep.first();
            resultadoDet = null;
            salir=true;
            while(!resultadoConcep.wasNull() && salir)
            {
                det.idConcepto = resultadoConcep.getInt("idConcepto");
                resultadoDet = det.consulta();
                if (resultadoDet.getInt("tipo")==3)
                {
                    table.addCell(resultadoDet.getString("nombreCons"));
                    table.addCell(""+resultadoConcep.getFloat("unidad"));
                    table.addCell("");
                    table.addCell("");
                    table.addCell(""+resultadoConcep.getFloat("descuento"));
                }
                if(resultadoConcep.isLast())
                {
                    salir=false;                    
                }    
                else
                {
                    resultadoConcep.next();
                }
            }
            
            documento.add(table);
            documento.add(new Paragraph("Firma Responzable                     Firma Empleado \n \r"
                                + "\n \r ........................                                    ...................."));
            documento.close();
        } 
        catch (DocumentException ex)
        {
            liq.Imprime("Entro en DocumentException");
        } 
        catch (java.io.IOException ex)
        {
            liq.Imprime("Entro en ioException");
        }
    }
    
}
