
/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;
import java.sql.SQLException;

/** * Ariel Marcelo Diaz*/
public class libSentenciasSQL extends Conexion{
    //instancias
    String tabla ="";
    String campos = "";
    String valores = "";
    String condicion = "";
    
    //Realiza la insercion de datos
        public int insertaSQL()
        {
            int devuelve = 0;
            String sentencia = null;
            sentencia = "INSERT INTO "+tabla+" ("+campos+") VALUES ("+valores+");";
            Imprime(sentencia);
            try
                {
                    devuelve = st.executeUpdate(sentencia);
                    if(devuelve==1)
                    {
                        Imprime("Datos Insertados Correctamente");
                    }
                    else
                    {
                        Imprime("Datos no insertados");
                    }
                }
                catch (SQLException ex)
                {
                    estado = ex.getMessage();
                    Imprime(estado);
                }            
            return devuelve;
        }
        
         //modifica datos de una libreria en particular
        public int modificaSQL()
        { 
            int devuelve=0;
            String  sentencia = null; 
            sentencia = "UPDATE "+tabla+" SET ";            
            
            String[] camp = campos.split(",");
            String[] val = valores.split(",");
            int n = camp.length;
            for (int i=0; i<n; i++)
            {
               sentencia = sentencia + camp[i] + "=" + val[i] + ",";
               //Imprime(sentencia);
            }
            sentencia= sentencia.substring(0,sentencia.length()-1);            
           
            if(condicion != null)
            {
                sentencia = sentencia+" WHERE "+condicion+";";
            }
            Imprime(sentencia);
            try
            {
                devuelve = st.executeUpdate(sentencia);
                if(devuelve == 1)
                {
                    Imprime("Modificacion realizada \n");
                }
                else
                {
                    Imprime("Sentencia fallida \n");
                }
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
                Imprime(estado);
            }
            return devuelve;
        }
        
        //Realiza una consulta en la base de datos
        public ResultSet consultaSQL()
        {
            String sentencia = "SELECT "+ campos + " FROM "+tabla;
            ResultSet resultado=null;
            if(!condicion.equals("") )
            {
                sentencia = sentencia + " WHERE "+ condicion;
            }
            sentencia = sentencia + ";";
            Imprime(sentencia);            
            try
            {
                resultado = st.executeQuery(sentencia);
                if(resultado.first())
                {
                    Imprime("Se encontraron resultados");
                }
                else
                {
                   Imprime("No se encontraron resultados");
                }
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
                Imprime(estado);
            }
            return resultado;
        }
        
        //realiza una eliminacion de la base de datos
        public int borraSQL()
        {
            int devuelve =0;
            String sentencia ="DELETE FROM "+ tabla +" WHERE " + condicion+";";
            //Imprime(sentencia);
            try 
            {
                devuelve = st.executeUpdate(sentencia);
                if(devuelve==1)
                {
                   // Imprime("Borrado Exitoso");
                }
                else
                {
                   // Imprime("Borrado no realizado");
                }
            }
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
                Imprime(estado);
            } 
            return devuelve;
        }
        
        //realiza una consulta en mysql sin acceso a las tablas.
        public ResultSet sentencias()
        {
            ResultSet resultado = null;
            String sentencia = "SELECT "+ campos +";";
            //Imprime(sentencia);
            try
            {
                resultado = st.executeQuery(sentencia);
            }
            catch (SQLException ex)
            {
                 estado = ex.getMessage();
                 Imprime(estado);
            }
            return resultado;
        }
}
