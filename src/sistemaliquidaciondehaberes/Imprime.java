/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

//librerias
import com.itextpdf.text.*; 
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Imprime 
{    
    private final Legajolib leg = new Legajolib();
    private final Concepto con = new Concepto();
    private final Concepto.Aplica apli = con.new Aplica();
    private final Concepto.Detalle det = con.new Detalle();
    private final Empresaslib emp = new Empresaslib();
    private final Personaslib pers = new Personaslib();
   
    public String destino;
    private Document documento = null;    
    private final PdfWriter writer = null;
    //fuentes
    private final Font fontbold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
    private final Font fontsimple = new Font(FontFamily.TIMES_ROMAN, 11);
    private Paragraph parrafo= new Paragraph();
    //constructor
    public Imprime()
    {        
        this.destino = "D:/recibo.pdf";
        documento = new Document();
        documento.addAuthor("Ariel Marcelo Diaz"); 
        documento.setPageSize(PageSize.A4);
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
            parrafo.setFont(fontbold);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            parrafo.add("Recibo de sueldo");
            documento.add(parrafo);
            documento.add(new Paragraph("Empresa:"+resultadoEmp.getString("razonSocial")
                    +"                  Periodo:"+liq.periodoIni+" - "+liq.periodoFin+
                    "\n \r"+
                    "CUIT:"+resultadoEmp.getString("cuit")+"   -    Antiguedad:"+liq.anti
                    +"\n \r"+
                    "Apellido y Nombre:"+resultadoPers.getString("apellido")+", "
                    + resultadoPers.getString("nombre")+"       CUIL:"
                    +resultadoPers.getString("cuil")+" \n \r Fecha Nac:"+
                    resultadoPers.getString("fechaNac")+"\n \r",fontsimple));
            
            //generando la tabla
            PdfPTable table = new PdfPTable(5); 
            
            table.setWidthPercentage(90);
            float[] headerWidths={80,30,60,60,60};
            table.setWidths(headerWidths);
            
            parrafo.clear();
            parrafo.setAlignment( Element.ALIGN_CENTER);
            parrafo.add("Concepto");
            table.addCell(parrafo);            
            table.addCell(new Paragraph("Unidad",fontbold));
            table.addCell(new Paragraph("Remunerativo",fontbold));
            table.addCell(new Paragraph("No Remunerativo",fontbold));
            table.addCell(new Paragraph("Descuento",fontbold));            
             
            
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
            
            table.addCell("");
            table.addCell("");
            table.addCell("-");
            table.addCell("-");
            table.addCell("-");
            
            table.addCell("Sub Totales");
            table.addCell("");
            table.addCell(""+liq.totalRemunerativo);
            table.addCell(""+liq.totalNoRemunerativo);
            table.addCell(""+liq.totalDescuentos);
            
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("-");
            
            table.addCell(" Total");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell(""+liq.total);
            
            documento.add(table);
            parrafo.clear();
            parrafo.setFont(fontsimple);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            
            parrafo.add("\n \r Firma Responzable                                               Firma Empleado \n \r"
                       + "\n \r ........................                                                    ............................... ");
            documento.add(parrafo);
           
            documento.close();
            abre();
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
    
    //abre el archivo generado
    private void abre()
    {
        File path = new File (destino);
        try 
        {
            Desktop.getDesktop().open(path);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Imprime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
