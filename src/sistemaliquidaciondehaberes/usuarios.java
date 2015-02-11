/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemaliquidaciondehaberes;

/**
 *
 * @author Gustavo
 */
import java.sql.ResultSet;


public class usuarios extends libSentenciasSQL
    {
        String usuario = "";
        String pass = "";
        String tipo = "";
        int idPersona = 0;
        
        public usuarios()
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
