
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/** * Ariel Marcelo Diaz****/
public class Complementarios 
{   
    // Realiza la creacion y modificacion de los puestos de la empresa
    class Cargos  extends libSentenciasSQL
    {
        int idPuesto=0;
        String nombrePuesto = "";
        String funcionPuesto = "";
        float basico = 0;
        String horario = "";
        int hsSemanales = 0;
        float costoHs=0;
        float costoHs50=0;
        float costoHs100=0;
        //constructor
        public Cargos()
        {
            this.tabla = "puesto";
            this.campos = "nombrePuesto,funcionPuesto,basico,horario,hsSemanales,costoHs,costoHs50,costoHs100";
        }
        
        //crea un nuevo puesto en la empresa
        public int nuevo()
        {
            this.valores = "'"+nombrePuesto+"','"+funcionPuesto+"',"+basico+",'"+
                           horario+"',"+hsSemanales+","+costoHs+","+costoHs50
                            +","+costoHs100+",";
            return this.insertaSQL();
        }
        
        // realiza la consulta de un cargo
        public ResultSet consulta()
        {
            this.condicion = "idPuesto="+this.idPuesto;
            return this.consultaSQL();
        }
        
        //realiza la modificacion de un cargo de una empresa
        public  int modifica()
        {
            this.valores = "'"+nombrePuesto+"','"+funcionPuesto+"',"+basico+",'"+
                           horario+"',"+hsSemanales+","+costoHs+","+costoHs50
                            +","+costoHs100+",";
            return this.modificaSQL();
        }
    }
    
    // Esta clase crea, modifica y elimina las capacitaciones para el presonal
    class Capacitaciones extends libSentenciasSQL
    {
        int idCapacitacion = 0;
        int idInstitucion = 0;
        String nombre = "";
        String fechaIni = "";
        String fechaFin = "";
        int horasCatedra = 0;
        String diasAsistencia = "";
        String programa = "";
        String objetivos = "";               
        //contructor
        public Capacitaciones()
        {
            this.tabla = "capacitacion";
            this.campos = "idInstitucion,nombre,fechaIni,fechaFin,horasCatedra,diasAsistencia,programa,objetivos";
        }
        
        public int nueva()
        {
            this.valores = idInstitucion+",'"+nombre+"','"+fechaIni+"','"+fechaFin+"',"+
                            horasCatedra+",'"+diasAsistencia+"','"+programa+"','"+
                            objetivos+"'";
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idCapacitacion="+idCapacitacion;
            this.valores = idInstitucion+",'"+nombre+"','"+fechaIni+"','"+fechaFin+"',"+
                            horasCatedra+",'"+diasAsistencia+"','"+programa+"','"+
                            objetivos+"'";
            return this.modificaSQL();
        }
        
        public ResultSet consulta()
        {
            this.condicion = "idCapacitacion="+idCapacitacion;
            return this.consultaSQL();
        }
        
        public int borrar()
        {
            this.condicion = "idCapacitacion="+idCapacitacion;
            return this.borraSQL();
        }
    }  
    
    //esta clase registra los titulos de los empleados
    class Titulos extends libSentenciasSQL
    {
        int idTitulo=0;
        int idInstitucion = 0;
        String carrera = "";
        String titulo = "";
        String objetivo = "";
        String materias = "";
        String analitico = "";
        //contructor
        public Titulos()
        {
            this.tabla = "titulo";
            this.campos = "idInstitucion,Carrera,titulo,objetivos,materias,analitico";
        }
        public int nueva()
        {
            this.valores = idInstitucion+",'"+carrera+"','"+titulo+"','"+objetivo+
                            "','"+materias+"','"+analitico+"'";
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "idtitulo="+idTitulo;
            this.valores = idInstitucion+",'"+carrera+"','"+titulo+"','"+objetivo+
                            "','"+materias+"','"+analitico+"'";
            return this.modificaSQL();
        }
        
        public ResultSet consulta()
        {
            this.condicion = "idtitulo="+idTitulo;
            return this.consultaSQL();
        }
        
        public int borrar()
        {
            this.condicion = "idtitulo="+idTitulo;
            return this.borraSQL();
        }
    }
    
    //esta clase edita los tipos de licencia
    class TipoLicencia extends libSentenciasSQL
    {
        int id = 0;
        String tipoLicencia = "";
        String detalle = "";
        int dias = 0;        
        int concepto = 0;
        int valides = 0;
        // constructor
        public TipoLicencia()
        {
            this.tabla = "tipolicencia";
            this.campos = "tipoLicencia,detalle,dias,concepto,valides";
        }
        
        public int nuevo()
        {
            this.valores = "'"+tipoLicencia+"','"+detalle+"',"+dias+","+
                            concepto+","+valides;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "id="+id;
            this.valores = "'"+tipoLicencia+"','"+detalle+"',"+dias+","+
                            concepto+","+valides;
            return this.modificaSQL();
        }
        
        public int baja()
        {
            this.condicion = "id="+id;
            return this.borraSQL();
        }
        
        public ResultSet consulta()
        {
            this.condicion = "id="+id;
            return this.consultaSQL();
        }
    }
}
