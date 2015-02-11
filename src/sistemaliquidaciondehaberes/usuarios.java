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
        public String usuario = "";
        public String pass = "";
        public int tipo = 1;
        public String nombre = "";
        public String direccion = "";
        public String telefono = "";
        public String celular = "";
        public String email = "";
        
        public usuarios()
        {
            this.tabla = "user";
            this.campos = "usuario,pass,tipo,nombre,direccion,"
                        + "telefono,celular,email";
        }
        
        public int nuevo()
        {
            this.valores = "'"+usuario+"','"+pass+"',"+tipo+",'"+nombre+"','"
                            +direccion+"','"+telefono+"','"+celular+"','"+
                            email+"'";
            return this.insertaSQL();
        }
        
        public int modifica()
        {
            this.condicion = "usuario='"+usuario+"'";
            this.valores = "'"+usuario+"','"+pass+"',"+tipo+",'"+nombre+"','"
                            +direccion+"','"+telefono+"','"+celular+"','"+
                            email+"'";
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
