/**************************************
Autor: Ariel Marcelo Diaz
 *Sitio Web: http://www.infrasoft.com.ar 
Desarrollo de sistemas a medidas
 ****************************************/
//clase para el manejo de usuarios
package sistemaliquidaciondehaberes;

import java.sql.ResultSet;

/**  Ariel Marcelo Diaz
 */
class Usuarios extends libSentenciasSQL
    {
        String usuario = "";
        String pass = "";
        String tipo = "";
        int idPersona = 0;
        
        public Usuarios()
        {
            this.tabla = "user";
            this.campos = "usuario,pass,tipo,idPersona";
        }
        
        public int nuevo()
        {
            this.valores = "'"+usuario+"','"+pass+"','"+tipo+"',"+idPersona;
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "usuario='"+usuario+"'";
            this.valores = "'"+usuario+"','"+pass+"','"+tipo+"',"+idPersona;
            return this.modificaSQL();
        }
        
        public int baja()
        {
            this.condicion = "usuario='"+usuario+"'";
            return this.borraSQL();
        }
        
        public ResultSet consulta()
        {
            this.condicion = "usuario='"+usuario+"'";
            return this.consultaSQL();
        }
    }
