
package sistemaliquidaciondehaberes;

/*** *  Ariel Marcelo Diaz */
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*; 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 
import java.util.logging.Level;
import java.util.logging.Logger;


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
      /***************************************  
         * **********Optimizar*************
         * *********************************/
        
        // Optimiza la consulta de seleccion
        public ResultSet consultaSQL(String tabla, String campos, String condicion)
        {
            ResultSet resultado=null;           
            if(condicion == "")
            {  
                try 
                {   
                    resultado = st.executeQuery("SELECT "+campos+" FROM "+tabla+";");                    
                    resultado.first();
                }
                catch (SQLException ex) 
                {                    
                    estado = ex.getMessage();
                }
            }
            else
            {
                try 
                {
                    resultado = st.executeQuery("SELECT "+campos+" FROM "+tabla+" WHERE "+condicion+";"); 
                    resultado.first();
                }
                catch (SQLException ex) 
                {
                    estado = ex.getMessage();
                }
            }
            return resultado;
        }
        
        //Realiza la insercion de datos
        public int insertaSQL(String tabla, String campos,String valores, String condicion)
        {
            int devuelve = 0;
            String sentencia = null;
            sentencia = "INSERT INTO "+tabla+"("+campos+") VALUES ("+valores+")";
            if(condicion == "")
            {
                try
                {
                    devuelve = st.executeUpdate(sentencia);
                }
                catch (SQLException ex)
                {
                    estado = ex.getMessage();
                }
            }
            else
            {
                sentencia = sentencia + "WHERE "+ condicion+";";
                try
                {
                    devuelve = st.executeUpdate(sentencia);
                }
                catch (SQLException ex) 
                {
                    estado = ex.getMessage();
                }
            }
            return devuelve;
        }// AUN FALTA PROBAR
        
        //modifica datos
        public int modificaSQL(String tabla, String campos, String valores, String condicion)
        {
            int devuelve=0;
            String  sentencia = null; // aqui me quede, todavia falta
            sentencia = "UPDATE "+tabla+"SET " + "WHERE "+condicion+";";
                        
            try
            {
                devuelve = st.executeUpdate(sentencia);
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
            return devuelve;
        }
        
        
    /*******************************************    
     **********entidad persona****************
      ****************************************/
              
             // Realiza el alta en el atributo persona
        public void altaPersona(String apellido, String nombre, String telefono,
                String telefono2, String celular, Integer idProvincia,Integer cp,
                Integer idBarrio, String direccion, Integer tipoDoc, String nroDoc,
                Integer estadoCivil,Date fechaNac, String mail, String mail2,
                String otrosDatos)
        {
            try 
            {        
                  st.executeUpdate("INSERT INTO persona(apellido, nombre, telefono,"
                          + "telefono2, celular, cp, idBarrio,direccion, tipoDoc,"
                          + " nroDoc,estadoCivil,fechaNac, mail, mail2, )"
                          + " VALUE ('"+apellido+"','"+nombre+"',"
                          + "'"+telefono+"','"+telefono2+"','"+celular+"','"+cp+"',"
                          + ""+idBarrio+",'"+direccion+"',"+tipoDoc+",'"+nroDoc+"',"
                          + "'"+estadoCivil+"','"+fechaNac+"','"+mail+"','"+mail2+"',"
                           + " '"+otrosDatos+"'); ");
            } 
            catch (SQLException ex)
            {
                 estado = ex.getMessage();
            }
        }
        
        //Realiza la baja de personas en el atributo persona
        public boolean bajaPersona(Integer idPers)
        {
            ResultSet resultado=null;
            boolean echo = false;
            try 
            {
                resultado = st.executeQuery("DELET * FROM persona WHERE idpersona="+idPers+";");
                echo= true;
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
            return echo;
        }
        
        // Modifica los datos del atributo persona
        public void modificaPersona (Integer idpersona,String apellido, String nombre, String telefono,
                String telefono2, String celular,Integer idProvincia, Integer cp, Integer idBarrio,
                String direccion, Integer tipoDoc, String nroDoc,
                Integer estadoCivil,String fechaNac, String mail, String mail2, String otrosDatos)
        {
             try 
             {
                st.executeUpdate("UPDATE legajo SET(apellido='"+apellido+"',"
                        + "nombre='"+nombre+"',telefono='"+telefono+"',"
                        + "telefono2='"+telefono2+"',celular='"+celular+"',"
                        + "idProvincia="+idProvincia+",cp='"+cp+"',"
                        + "idBarrio="+idBarrio+",direccion='"+direccion+"',"
                        + "tipoDoc="+tipoDoc+",nroDoc='"+nroDoc+"',"
                        + "estadoCivil="+estadoCivil+",fechaNac='"+fechaNac+"'"
                        + ",mail='"+mail+"',mail2='"+mail2+"',"
                        + "otrosDatos='"+otrosDatos+"')"
                        + " WHERE (idpersona="+idpersona+");");
             }
             catch (SQLException ex)
             {
                estado = ex.getMessage();
             }
        }
        
        //Consulta los datos del atributo persona
        public ResultSet consultaPersona (Integer idPers)
        {
            ResultSet resultado=null;
            try 
            {
                resultado = st.executeQuery("SELECT * FROM persona WHERE idpersona="+idPers+";");
                if(resultado.first())
                {
                    Imprime("Persona encontrada");
                }
                else
                {
                    Imprime("Persona no encontrada");
                }
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }             
            return resultado;
        }
    
    
    /***********************************
     ********operaciones de legajo******
      *********************************/
  
        //realiza el alta de legajo
        public void altaLegajo(Integer idPersona)
        {
            Integer est=1;
            try
            {
                st.executeUpdate("INSERT INTO legajo (idPersona,idEstado) VALUES ("+idPersona+","+est+");");
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
        }
        
        //Cambia el estado del atributo legajo
        public void estadoLegajo(Integer idLegajo, Integer idEstado)
        {           
            try
            {
                st.executeUpdate("UPDATE legajo SET idEstado="+idEstado+" WHERE idLegajo="+idLegajo+";");
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
        }
        
        /*************************************
         * ******Area Novedades***************
         * ***********************************/
        
        // muestra la ultima novedad cargada
        public Integer ultimaNovedad( ResultSet resultado)
        {
            Integer ultiNov =0;
            try 
            {
                resultado = st.executeQuery("SELECT max(idNovedad) AS ultima FROM novedad;");
                
                if( resultado.first())
                {                    
                    System.out.print("resultado:"+resultado.getInt(1)+"\n");
                }
                else
                {
                    System.out.print("No existen resultados");
                }                   
                        
            }
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
            } 
            
            try 
            {
                ultiNov = resultado.getInt(1);
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
            return ultiNov;
        }
        
        
        // Agrega una novedad al legajo, devuelve el idNovedad en el cual se registro
        public Integer agregaNovedad(Integer idLegajo, String asunto, String detalle, Integer idTipo)
        {
            ResultSet resultado=null;
            String fecha = FechaActual();   
            Integer nov =0;
            try 
            {
                 st.executeUpdate("INSERT INTO novedad(idLegajo,asuntoNovedad,detalleNovedad,idTipoNovedad,fecha)"
                        + " VALUE ("+idLegajo+",'"+asunto+"','"+detalle+"',"+idTipo+",'"+fecha+"');");       
                                   
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
            
             nov = ultimaNovedad(resultado);              
            return nov;
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
        
        //Registra las fechas en las cuales el personal llega tarde
       public void InasistenciasPersonal(String fecha, Integer idLegajo)
        {
           Integer idNovedad = agregaNovedad(idLegajo, "Inasistencia:"+idLegajo, "El Empleado  "+idLegajo+" No asistio a su trabajo", 1);
        
            try
            {
                st.executeUpdate("INSERT INTO inasistencia(fecha, idLegajo, idNovedad) "
                        + "VALUES ('"+fecha+"',"+idLegajo+","+idNovedad+");");
            }
            catch (SQLException ex) 
            {
                estado = ex.getMessage();
            }
        }
       
       // Registra las llegadas tardes del personal
       public void llegadasTardes(Integer idLegajo, String fecha, String hora)
       {
           Integer idNovedad = agregaNovedad(idLegajo, "Llegada tarde:"+idLegajo, "El empleado "+idLegajo+"Llego a "
                   + "hr"+hora, 1);
            try
            {
                st.executeUpdate("INSERT INTO llegadastardes(fecha, hora,idLegajo,idNovedad)"
                        + " VALUES('"+fecha+"','"+hora+"',"+idLegajo+","+idNovedad+");");
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
       }
       
       //Carga una nueva licencia al legajo del trabajador
       public void cargaLicencia(Integer idLegajo, String motivo, Integer cantDias,
               String fechaIni, Integer tipoLicencia)
       {
           Integer idNovedad = agregaNovedad(idLegajo, "Licencia medica", idLegajo+"Dispone de Licencia Medica por "
                    +cantDias+"dias por motivo de:"+motivo, 1);
            // falta poner algo que me devuelva la el idnovedad que se cargo
            try
            {
                st.executeUpdate("INSERT INTO licencia(Motivo,cantDias, fechaInicio, tipoLicencia,idNovedad,idLegajo)"
                        + " VALUES('"+motivo+"',"+cantDias+",'"+fechaIni+"',"+tipoLicencia+","+idNovedad+","+idLegajo+");");
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }
       }
       
       // asigna los puestos a cada empleado    
       public void asignaPuesto(Integer idLegajo, Integer idPuesto,String fechaInicio)
       {
           
           Integer idNovedad = agregaNovedad(idLegajo,"Asignacion de puesto", "El "
                   + "empleado "+idLegajo+" ha sido asignado al puesto"+idPuesto,1);
           String sentencia ="";
        try 
        {
            st.executeUpdate("INSERT INTO puestoLegajo(idPuesto,idLegajo,idNovedad,fechaInicio) "
                    + "VALUES("+idPuesto+","+idLegajo+","+idNovedad+","+fechaInicio+");");
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage();
        }
       }
       
       //***********************************************
       // script para liquidacion de basica de sueldo
       //**********************************************
       
       // verifica sin el trabajador tiene o no presentismo
       public int verificaPresentismo(Integer idLegajo ,String fechaInicio,String fechaFin)
       {
        int result=1; // por defecto es 1 para que sea computado
        ResultSet realizar = null;
        String operacion ="SELECT fecha FROM inasistencia WHERE (idLegajo ="+idLegajo+")"
                    + "  AND (fecha >= '"+fechaInicio+"' AND fecha <= '"+fechaFin+"') AND"
                    + "(justificada =0);";
       
        try 
        {
            realizar = st.executeQuery(operacion);
            if(realizar.first())
            {
                result=0;
                Imprime(" El Empleado tuvo inasistencias injustificadas");                
            }
            else
            {
                Imprime("El empleado posee presentismo");                
            }                        
        }
        catch (SQLException ex)
        {
            estado = ex.getMessage();
        }
        return result;// si el resultado es 1, entonces el empleado tiene presentismo
       }      
       
       // carga Descuento obraSocial, la cantidad de obras sociales que existen
       public Integer obraSocialCarga(Integer idLegajo)
       {
            Integer result=0;
            ResultSet verif=null;
            String operacion ="SELECT COUNT(idLegajo)  AS cantidad"
                        + " FROM  obrasociallegajo "
                    + "WHERE idLegajo ="+idLegajo+" GROUP By idLegajo;";
           
            try  // verificar la funcion, por algo me da siempre cero
            {                
                verif = st.executeQuery(operacion);
                if(verif.first())
                {
                    result = verif.getInt(1);
                    Imprime("Obra Social/es: "+ result);
                }
                else
                {
                    Imprime("No se gravaron obras sociales");
                }
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }           
             
             return result;
       }
       
       // verifica la cantidad de Sindicatos a cargar
       public Integer SindicatosCarga(Integer idLegajo) 
       {
           ResultSet verif = null;
           Integer devolver = 0; 
           String sentencia = "SELECT COUNT( idSindicato ) "
                                + "FROM sindicatolegajo "
                                + "WHERE idLegajo ="+idLegajo
                                + " GROUP BY idSindicato"; 
           
            try 
            {
                verif =st.executeQuery(sentencia);
                if (verif.first())
                {
                    devolver = verif.getInt(1);
                    Imprime("Sindicato/s: "+devolver);                  
                }
                else
                {
                    Imprime("No se registraron cargas al sindicato");
                }
                
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage();
            }            
            
            return devolver;
       }
       
       // obtiene el sueldo basico de un determinado puesto
       public float   obtieneBasico(Integer idPuesto)
       {
           float devolver=0;
           ResultSet operacion=null;
           String sentencia = "SELECT basico, nombrePuesto,funcionPuesto FROM puesto WHERE idPuesto="+idPuesto+";";           
           try
           {
                operacion = st.executeQuery(sentencia);
                
                if(operacion.first())
                {
                    Imprime("Cargo: "+operacion.getString(2));
                    Imprime("Funcion: "+operacion.getString(3));
                    devolver = operacion.getInt(1); 
                    Imprime("Basico: "+ devolver);
                }
                else
                {
                    Imprime("no pudo obterner el basico, por favor verifique");
                }
           }
           catch (SQLException ex)
           {
             estado = ex.getMessage();
           }          
          
           
           return devolver;
       }
      
       // obtiene el puesto del trabajador
       public int obtienePuesto( int  idLegajo)
       {
           int devolver =0;
           ResultSet operacion = null;
           String sentencia = "SELECT idPuesto  FROM puestolegajo "
                   + "WHERE idLegajo="+idLegajo+" ORDER BY idNovedad LIMIT 1";
           Imprime(sentencia);
           
           try
           {
                operacion = st.executeQuery(sentencia);
                if(operacion.first())
                {
                    devolver=operacion.getInt(1);
                    Imprime("Puesto: "+devolver);                            
                }
                else
                {
                    Imprime("El trabajador no tiene asignado un puesto");
                }
           } catch (SQLException ex) 
           {
                estado = ex.getMessage();
           }
           return devolver;
       }
     
       // devuelve la formulas cargadas en la entidad formulas
       public float formulasDesc(int idFormula)
       {
           float devolver=0;
           ResultSet realizar = null; 
           String operacion = "SELECT formula FROM formulas WHERE idFormula="+idFormula+";";
           try
           {
             realizar = st.executeQuery(operacion);
             if(realizar.first())
             {
                  devolver = Float.parseFloat(realizar.getString(1));
             }
             else
             {
                 Imprime("no se logro optener la formula");
             }
           }
           catch (SQLException ex)
           {
             estado = ex.getMessage();
            }
           return devolver;
       }        
         
       // calcula la jubilacion del empleado
       public int calculoAnt(Integer idLegajo)
       {
           int devolver=1;           
           String Fecha = FechaActual();
           ResultSet hacer=null;
           String sentencia="SELECT fechaIngreso FROM legajo WHERE idLegajo ="+idLegajo+";";
                      
           try
           {
                hacer = st.executeQuery(sentencia);
                if(hacer.first())
                {
                   // devolver= hacer.getInt(1);
                      String  fechaIngreso = hacer.getString(1);
                      sentencia ="select datediff( curdate(),'"+fechaIngreso+"')/365;";
                      ResultSet verFecha = st.executeQuery(sentencia);
                      if(verFecha.first())
                      {
                          devolver = verFecha.getInt(1);
                          Imprime("Antiguedad: "+devolver);
                      }
                      else
                      {
                          Imprime("No se pudo calcular la antiguedad");
                      }
                  
                }
                else
                {
                    Imprime("No se pudo generar la antiguedad");
                }
           }
           catch (SQLException ex)
           {
                estado = ex.getMessage();
            }
           return devolver;
       }
       
       //Esta funcion carga las hs Extras de un legajo
       public void cargaHs(int idLegajo, String fecha, int cantidad, int tipo)// aqui me quede
       {
           int idNovedad = agregaNovedad(idLegajo," Carga de Hs Extras","El "
                   + "legajo: "+idLegajo+" realizo "+ cantidad+" de horas Extras"
                   + "de tipo "+tipo, 1);
           
           String sentencia ="INSERT INTO hsextra(idLegajo, idNovedad, fecha, cantidadHs, tipoHs) "
                            + "VALUES("+idLegajo+", "+idNovedad+", '"+fecha+"', "+cantidad+","+tipo+");";
           
           
          try 
          {
             st.executeUpdate(sentencia);
          }
          catch (SQLException ex)
          {
            estado = ex.getMessage();
          }         
       }
       
       // busca la cantidad de hs extras de un legajo
       public int cantidadHs50(int idLegajo,String fechaIni, String fechaHasta)
       {
           int devolver = 0;
           String sentencia = "SELECT SUM(cantidadHs) FROM hsextra WHERE (idLegajo ="+idLegajo+")"
                                 + "AND (fecha BETWEEN '"+fechaIni+"'"
                                       + " AND '"+fechaHasta+"')"
                                 + "AND tipoHs = 1;"; 
           
           try
           {
                ResultSet hacer = st.executeQuery(sentencia);
                if(hacer.first())
                {
                   devolver = hacer.getInt(1);
                   Imprime("Cantidad de Hs extra al 50%: "+devolver);
                }
                else
                {
                   Imprime("No se registraron hs extras al 50%");
                }
           }
           catch (SQLException ex) 
           {
                estado = ex.getMessage();
           }           
           return devolver;
       }
       // obtiene la cantidad de horas que el trabajador realiza en un determinado periodo
       public int cantidadHs100(int idLegajo,String fechaIni, String fechaHasta)
       {
           int devolver = 0;
           String sentencia = "SELECT SUM(cantidadHs) FROM hsextra WHERE (idLegajo ="+idLegajo+")"
                                 + "AND (fecha BETWEEN '"+fechaIni+"'"
                                       + " AND '"+fechaHasta+"')"
                                 + "AND tipoHs = 2;"; 
           
           try
           {
                ResultSet hacer = st.executeQuery(sentencia);
                if(hacer.first())
                {
                   devolver = hacer.getInt(1);
                   Imprime("Cantidad de Hs extra al 100%: "+devolver);
                }
                else
                {
                   Imprime("No se registraron hs extras al 50%");
                }
           }
           catch (SQLException ex) 
           {
                estado = ex.getMessage();
           }           
           return devolver;
       }
       // Obtiene el valor hora segun el puesto que disponga
       // 1 = horas al 50% , 2 = hs al 100%
       public float costoHs(int idPuesto, int tipoHs)
       {
           float devolver = 0;
           String sentencia1 = "SELECT costoHs50 FROM puesto WHERE idPuesto="+idPuesto+";";
           String sentencia2 = "SELECT costoHs100 FROM puesto WHERE idPuesto="+idPuesto+";";
           ResultSet hacer = null;
           if(tipoHs==1)
           {
                try
                {
                    hacer = st.executeQuery(sentencia1);
                    if(hacer.first())
                    {
                        devolver = hacer.getInt(1);
                    }
                    else
                    {
                        Imprime("No se pudo obtener las hs extras");
                    }
                }
                catch (SQLException ex)
                {
                    estado = ex.getMessage();
                }
           }
           else
           {
                try 
                {
                    hacer = st.executeQuery(sentencia2);
                    if(hacer.first())
                    {
                        devolver = hacer.getInt(1);
                    }
                    else
                    {
                        Imprime("No se pudo obtener las hs extras");
                    }
                }
                catch (SQLException ex)
                {
                     estado = ex.getMessage();
                }
           }              
           
           return devolver;
       }
       // esta funcion realiza la liquidacion basica de sueldo      
       public void  liquidacionSueldo(Integer idLegajo)
       {
          // Integer devolver = 0;
           Integer cantSindicatos = 0;
           float sindicato = 0;
           Integer cantObraSocial =0;
           float obrasocial = 0;
           float presentismo = 0;
           float jubilacion = 0;
           float art=0;
           float juv=0;
           float totalDesc = 0;
           float antiguedad = 0;
           float leyNose = 0;
           float hsExtras50 = 0;
           float hsExtras100 = 0;
           float totalAdic = 0;
         
           Imprime("Sistema Liquidacion de haberes ");
           Imprime("Imprimiendo recibo de sueldo ");
           int puesto = obtienePuesto(idLegajo);
           if( puesto == 0)
           {
               Imprime("El nro no es un legajo valido");
           }
           else
           {
                float basico = obtieneBasico(puesto);
                Imprime("::::Descuentos:");
                cantSindicatos = SindicatosCarga(idLegajo);
                sindicato = cantSindicatos * formulasDesc(1)* basico;
                Imprime("Sindicato: "+sindicato);
                cantObraSocial= obraSocialCarga(idLegajo);
                obrasocial = cantObraSocial * formulasDesc(2)* basico;
                Imprime("Obra Social:"+obrasocial);           
                jubilacion =  formulasDesc(5)* basico;
                Imprime("Juvilacion: "+jubilacion);
                 art = formulasDesc(4)* basico;
                Imprime("ART:"+art);           
                totalDesc = sindicato + obrasocial + jubilacion + art;
                Imprime("Total descuentos: "+totalDesc);
                Imprime("::::Adicionales");
                presentismo = verificaPresentismo(idLegajo, "2011-11-01", "2011-11-30") * formulasDesc(3)* basico;
                Imprime("Presentismo: "+presentismo);
                antiguedad = calculoAnt(idLegajo) * formulasDesc(6)* basico;
                Imprime("Antiguedad:"+antiguedad);
                leyNose = formulasDesc(7)* basico;
                Imprime("Ley Nro 2.030:  "+leyNose);
                hsExtras50 = cantidadHs50(idLegajo, "2011-11-01", "2011-11-30")*costoHs(puesto, 1);
                Imprime("Hs Extras al 50%: "+hsExtras50);
                hsExtras100 = cantidadHs100(idLegajo, "2011-11-01", "2011-11-30")*costoHs(puesto, 2);
                Imprime("Hs Extras al 100%: "+hsExtras100);
                totalAdic = presentismo + antiguedad + leyNose + hsExtras50 + hsExtras100;
                Imprime("Total adicionales: "+totalAdic);
           }
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
       
       /****************************************
        * *******Obras Sociales******************
        * **************************************/
       
       // genera el alta de una obra social
       public void altaObraSocial(String matricula,String Cuit,String razonSocial,String direccion,String telefono,String otros)
       {
           String sentencia ="INSERT INTO obrasocial"
                   + "(matricula, Cuit, razonSocial, direccion, telefono, otros)"
                   + " VALUE('"+matricula+"', '"+Cuit+"', '"+razonSocial+"',"
                   + " '"+direccion+"', '"+telefono+"', '"+otros+"');";
           
           try 
           {
               if(st.executeUpdate(sentencia) ==1)
               {
                   Imprime("se realizo la operacion");
               }
               else
               {
                   Imprime("No se realizo la operacion");
               }
           }
              catch (SQLException ex) 
           {
             estado = ex.getMessage();           
           }
       }
       
       //Cambia el estado de una obra social
       public void estadoObraSocial(int idObraSocial, int idEstadoOS) // todavia no esta provada
       {
           String sentencia = "UPDATE"
                                     + " obrasocial "
                            + "SET "
                                        + "estadoOS = " + idEstadoOS
                            + " WHERE "
                                        + "(idObraSocial = "+idObraSocial+");";           
           try
           {
             if(st.executeUpdate(sentencia)==1)
             {
                 Imprime("La operacion se concreto con exito");
             }
             else
             {
                 Imprime("La operacion no pudo realizarse");
             }
           }
           catch (SQLException ex) 
           {
             estado = ex.getMessage();
           }
       }
       
       // asigna un empleado una obra social
       public void asignaObraScial(int idLegajo, int idObraSocial)
       {
           String sentencia = "INSERT INTO obrasociallegajo(idLegajo, idObraSocial)"
                   + " VALUES("+idLegajo+","+idObraSocial+");";
           try
           {
              if (st.executeUpdate(sentencia)==1)
              {
                  Imprime("Se realizo la operacion");
                  agregaNovedad(idLegajo, estado, estado, idLegajo); // corregir esta parte
              }
              else
              {
                  Imprime("No se realizo la operacion");
              }
           }
           catch (SQLException ex) 
           {
              estado = ex.getMessage(); 
           }
       }  
       
       /*************************************************
        *************Sindicatos*************************
        * ***********************************************/
       
       //crea un nuevo Sindicato
       public void altaSindicato(String razonSocial, String Cuit, String direccion, String telefono,
               String otrosDatos, String matricula, int idEstado)
       {
           String sentencia = "INSERT INTO "
                                + "sindicato(razonSocial, cuit, direccion, telefono, otrosDatos, matricula, idEstado)"
                                + " VALUES('"+razonSocial+"','"+Cuit+"','"+direccion+"','"+telefono
                                +"','"+otrosDatos+"','"+matricula+"',"+idEstado+");";
                       
            try 
            {                
                if(st.executeUpdate(sentencia)==1)
                {
                    Imprime("Sindicato correctamente ingresado");
                }
                else
                {
                    Imprime("Fallo en la inserción de datos");
                }
            }
            catch (SQLException ex) 
            {
                estado = ex.getMessage(); 
            }
       }
       
       //Cambia el estado del sindicato
       public void cambiaEstadoSindicato(int idSindicato, int idEstado)
       {
           String sentencia = "UPDATE sindicato SET idEstado=idEstado  WHERE idSindicato=idSindicato;";
           try
           {
                if(st.executeUpdate(sentencia)==1)
                {
                    Imprime("Cambio realizado correctamente");
                }
                else
                {
                    Imprime("Error en la operacion, intente nuevamente");
                }
           }
           catch (SQLException ex) 
           {
               estado = ex.getMessage(); 
           }
       }
       
       //asigna un sindicato a un legajo, pudiendo este tener mas de un sindicato
       public void asignaSindicato(int idLegajo, int idSindicatos) 
       {
           String sentencia = "INSERT INTO sindicatolegajo(idSindicato, idLegajo)"
                            + "VALUES ("+idSindicatos+","+idLegajo+");";
            try 
            {
                if(st.executeUpdate(sentencia) == 1)
                {
                    Imprime("Sindicato agregado correctamente legajo");
                }
                else
                {
                    Imprime("Operacion fallida");
                }
            }
            catch (SQLException ex)
            {
                estado = ex.getMessage(); 
            }
       }
       
       
       /*****************************************************
        * ****************Formulas**************************
        * **************************************************/
        
       // crea una nueva formula
       
       // modifica una formula
       
       /*Necesito planificar mejor la parte de conseptos no remunerativos
        La parte de interfaz grafica necesita una mejor planificacion*/
}  