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
    private final Font fontbold = new Font(FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.WHITE);
    private final Font fontsimple = new Font(FontFamily.TIMES_ROMAN, 12);
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
            documento.add(new Paragraph("Empresa:"+resultadoEmp.getString("razonSocial")
                    +"   Original    Periodo:"+liq.periodoIni+" - "+liq.periodoFin+
                    "\n \r"+
                    "CUIT:"+resultadoEmp.getString("cuit")+" - Antiguedad:"+liq.anti
                    +"\n \r"+
                    "Apellido y Nombre:"+resultadoPers.getString("apellido")+", "
                    + resultadoPers.getString("nombre")+" CUIL:"
                    +resultadoPers.getString("cuil")+" Fecha Nac:"+
                    resultadoPers.getString("fechaNac")+"\n \r"));
            
            //generando la tabla
            PdfPTable table = new PdfPTable(5);            
            table.addCell("Concepto");
            table.addCell("Unidad");
            table.addCell("Remunerativo");
            table.addCell("No Remunerativo");
            table.addCell("Descuento");            
             
            
            boolean salir=true;
            resultadoConcep.first();
            ResultSet resultadoDet = null;
            while(resultadoConcep.wasNull() && salir)
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
            while(resultadoConcep.wasNull() && salir)
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
            while(resultadoConcep.wasNull() && salir)
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
