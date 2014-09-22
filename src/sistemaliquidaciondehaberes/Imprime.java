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
            // hacer lo de hs extras
            
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
            
            
            
            documento.add(table);
            
            documento.close();
        } 
        catch (DocumentException ex)
        {
            
        } 
        catch (java.io.IOException ex)
        {
            liq.Imprime("Entro en ioException");
        }
    }

    
    
}
