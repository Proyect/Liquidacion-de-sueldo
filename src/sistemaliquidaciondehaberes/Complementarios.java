package sistemaliquidaciondehaberes;

import java.sql.ResultSet;


public class Complementarios 
{   
    // Realiza la creacion y modificacion de los puestos de la empresa
    public class Cargos  extends libSentenciasSQL
    {
        public int idPuesto=0;
        public String nombrePuesto = "";
        public String funcionPuesto = "";
        public float basico = 0;
        public String horario = "";
        public int hsSemanales = 0;
        public float costoHs=0;
        public float costoHs50=0;
        public float costoHs100=0;
        public String seccion = "";
        public String departamento = "";
        public int categoria = 0;
        
        //constructor
        public Cargos()
        {
            this.tabla = "puesto";
            this.campos = "nombrePuesto,funcionPuesto,basico,horario,"
                        + "hsSemanales,costoHs,costoHs50,costoHs100,"
                        + "seccion,departamento,categoria";
        }
        
        //crea un nuevo puesto en la empresa
        public int nuevo()
        {
            this.valores = "'"+nombrePuesto+"','"+funcionPuesto+"',"+basico+",'"+
                           horario+"',"+hsSemanales+","+costoHs+","+costoHs50
                            +","+costoHs100+",'"+seccion+"','"+departamento+"',"
                            +categoria;
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
                            +","+costoHs100+",'"+seccion+"','"+departamento+"',"
                            +categoria;
            return this.modificaSQL();
        }
    }
    
    // Esta clase crea, modifica y elimina las capacitaciones para el presonal
    public class Capacitaciones extends libSentenciasSQL
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
    public class Titulos extends libSentenciasSQL
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
    public class TipoLicencia extends libSentenciasSQL
    {
        public int id = 0;
        public String tipoLicencia = "";
        public String detalle = "";
        public int dias = 0;        
        public int concepto = 0;
        public int valides = 0;
        public int clasificacion = 0;
        // constructor
        public TipoLicencia()
        {
            this.tabla = "tipolicencia";
            this.campos = "tipoLicencia,detalle,dias,concepto,valides,clasificacion";
        }
        
        public int nuevo()
        {
            this.valores = "'"+tipoLicencia+"','"+detalle+"',"+dias+","+
                            concepto+","+valides+","+clasificacion;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "id="+id;
            this.valores = "'"+tipoLicencia+"','"+detalle+"',"+dias+","+
                            concepto+","+valides+","+clasificacion;
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
