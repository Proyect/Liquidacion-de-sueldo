/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

/*** *  Ariel Marcelo Diaz */
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*; 
import java.text.SimpleDateFormat;
import java.util.Date; 


public class Conexion
{
    MysqlDataSource DataSource;
    Connection conexion;
    Statement st;
    String estado = "Ok";
    
    // Constructor y coneccion a la base de datos
    public Conexion()
    {
        DataSource = new MysqlDataSource();
        DataSource.setUser("root");
        DataSource.setPassword("");
        DataSource.setDatabaseName("liquidaciondb");
        DataSource.setServerName("Localhost");
        try 
        {
            conexion = DataSource.getConnection();
            st = conexion.createStatement();
        } 
        catch (SQLException ex)
        {
            estado = ex.getMessage();
        }        
    }
    
          // Imprime en pantalla el mensaje que se envia
        public static void Imprime(String x)
        {
            x=x+"\n";
            System.out.print(x);
        }       
        
               
        // Funciones utiles para fechas
        
        // Obtiene la fecha actual
        public static String FechaActual()
        {
            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            return formateador.format(ahora);
        }
        
        // Obtiene la hora actual
        public static String HoraActual()
        {
            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
            return formateador.format(ahora);
        }
        
        
        // fin de funciones de tiempo
        
        //Obtiene el listado de novedades de un legajo
        public ResultSet historialLegajo(Integer idLegajo)
        {
            ResultSet resultado=null;
            try 
            {
                resultado = st.executeQuery("SELECT * FROM novedad WHERE idLegajo="+idLegajo+";");
                while(resultado.next())
                {    
                    Imprime("Id Novedad: "+resultado.getString(1));
                    Imprime("Asunto: "+resultado.getString(3));
                    Imprime("Detalle: "+resultado.getString(4));
                    Imprime("-------------------------------");
                }
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
            
            return resultado;
        }          
       
          // busca los datos en una tabla predeterminada
       public ResultSet buscaLegajo(String cabecera, String buscar)
       {
          ResultSet devulve = null;
          String sencencia = "select * from listaLegajo where "+cabecera+" like '%"+buscar+"%';";
          try
          {
            devulve = st.executeQuery(sencencia);
            if(devulve.first())
            {
                Imprime("Se obuvieron resultados");
            }
            else
            {
                Imprime("No se obtuvieron resultados");
            }
          }
          catch (SQLException ex) 
          {
            estado = ex.getMessage();
          }
          return devulve;
       }
       
       // busca los elementos de la tabla listapersona
       public ResultSet buscaPersona(String atributo, String buscar)
       {
           ResultSet devuelve = null;
           String sentencia = "select * from listapersona where "+atributo+" like '%"+buscar+"%';";
           Imprime(sentencia);
           try
           {
             devuelve = st.executeQuery(sentencia);
             if(devuelve.first())
             {
                 Imprime("se obtuvieron resultado");
             }
             else
             {
                 Imprime("No se obtuvieron resultado");
             }
           }
           catch (SQLException ex)
           {
             estado = ex.getMessage();
           }
           return devuelve;
       }
       
       
       
}  