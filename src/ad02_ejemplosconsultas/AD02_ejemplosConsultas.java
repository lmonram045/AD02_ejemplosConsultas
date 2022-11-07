/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad02_ejemplosconsultas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author jm
 */
public class AD02_ejemplosConsultas {

    /**
     * @param args the command line arguments
     */
    static Connection conBd;
    static final String dirUrl = "jdbc:mysql://localhost:3306/Instituto?user=root&password=root";
    private static CallableStatement proc = null;
    private static int resultado = 0;
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Ir comentando o descomentando para probar cada uno de los ejemplos
        // deberian de ir sus correspondientes bloques try catch y cerrar los recursos en los finally (o con recursos)

        // uno_SelectConStatement();
        // dos_InsertConStatement();
        // tres_InsertConStatementVariable();
        cuatro_SelectConStatementVariable();
        // cinco_SelectConSentenciaPreparada();
        // seis_ResultSetConMetaDAta();
         // siete_PAConCallableStatement(21, 29);
        
    }
    
    public static void uno_SelectConStatement() throws ClassNotFoundException, SQLException {

        conBd = DriverManager.getConnection(dirUrl);
        String SQL = "Select * from Persona";

        Statement stm = conBd.createStatement();

        ResultSet rst = stm.executeQuery(SQL);

        // Mientras tenga resultados (next comprueba si hay siguiente fila (devuelve true) y avanza)
        while (rst.next()) {
            //System.out.println(rst.getString(2) + " " + rst.getString(3)+ ": " + rst.getInt(4));
            // Se puede pasar el nombre de la columna o su posicion en la tabla (empezando por 1, no por 0)

            System.out.println(rst.getString("nombre") + " " + rst.getString("apellidos") + ": " + rst.getInt("edad"));
        }
        conBd.close();
    }
    
    public static void dos_InsertConStatement() throws ClassNotFoundException, SQLException {

        conBd = DriverManager.getConnection(dirUrl);
        String SQL = "Insert into Persona(nombre,apellidos,edad) values ('Elena','García Sánchez',33);";

        // Se puede usar executeUpdate para insert, update y delete
        Statement stm = conBd.createStatement();

        // Devuelve el numero de filas afectadas
        int filas = stm.executeUpdate(SQL);

        // si filas vale 1 tiene resultados si vale 0 no tiene resultados
        if (filas == 1) {
            System.out.println("Inserción realizada con éxito");
        } else {
            System.out.println("Error en la inserción");
        }
    }

    /**
     * Ejemplo de inserción con Statement y variables
     * en este caso vamos pidiendo los datos por teclado.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void tres_InsertConStatementVariable() throws ClassNotFoundException, SQLException {

        conBd = DriverManager.getConnection(dirUrl);

        String nombre = Leer.leerTexto("Dime el nombre: ");
        String apellidos = Leer.leerTexto("Dime los apellidos: ");
        int edad = Leer.leerEntero("Dime la edad: ");
        String SQL = "Insert into Persona(nombre,apellidos,edad)"
                + " values ('" + nombre + "','" + apellidos + "'," + edad + ")";

        Statement stm = conBd.createStatement();

        int filas = stm.executeUpdate(SQL);

        if (filas == 1) {
            System.out.println("Inserción realizada con éxito");
        } else {
            System.out.println("Error en la inserción");
        }
    }
    
    public static void cuatro_SelectConStatementVariable() throws ClassNotFoundException, SQLException {

        conBd = DriverManager.getConnection(dirUrl);

        String idPersona = Leer.leerTexto("Dime el id a consultar: ");

        String SQL = "Select * from Persona where idPersona=" + idPersona;

        System.out.println(SQL);
        Statement stm = conBd.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        while (rst.next()) {
            System.out.println(rst.getInt("idPersona") + " " + rst.getString("nombre")
                    + " " + rst.getString("apellidos") + ": " + rst.getInt("edad"));
        }
    }
    
    public static void cinco_SelectConSentenciaPreparada() throws ClassNotFoundException, SQLException {

        conBd = DriverManager.getConnection(dirUrl);

        String nombre = Leer.leerTexto("Dime el nombre: ");
        String apellidos = Leer.leerTexto("Dime los apellidos: ");
        int edad = Leer.leerEntero("Dime la edad: ");
        String SQL = "Insert into Persona(nombre,apellidos,edad) values (?,?,?)";

        PreparedStatement pstm = conBd.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        pstm.setString(1, nombre);
        pstm.setString(2, apellidos);
        pstm.setInt(3, edad);

        int filas = pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();

        if (filas > 0) {
            System.out.print("Inserción realizada con éxito del registro.");
            if (rs.next()) {
                System.out.println("La clave de la fila insertada es " + rs.getLong(1));
            }
        } else {
            System.out.println("Error en la inserción");
        }
    }
    
     public static void seis_ResultSetConMetaDAta() throws ClassNotFoundException, SQLException {

        conBd = DriverManager.getConnection(dirUrl);
        String SQL = "Select * from Persona";
        Statement stm = conBd.createStatement();
        ResultSet rst = stm.executeQuery(SQL);          // los datos
        ResultSetMetaData rstmd = rst.getMetaData();    // los metadatos
        int cols = rstmd.getColumnCount();              // cuantas columnas hay

        //imprimimos cabecera
        for (int i = 1; i <= cols; i++) {
            System.out.print(String.format("%20s", rstmd.getColumnName(i)));
        }
        System.out.println("");

        //imprimimos datos
        while (rst.next()) {
            for (int i = 1; i <= cols; i++) {
                switch (rstmd.getColumnType(i)) {   // segun el tipo formateamos
                    case java.sql.Types.VARCHAR:
                        System.out.print(String.format("%20s", rst.getString(i)));
                        break;
                    case java.sql.Types.INTEGER:
                        System.out.print(String.format("%20d", rst.getInt(i)));
                        break;
                    case java.sql.Types.DOUBLE,java.sql.Types.FLOAT:
                        System.out.print(String.format("%20lf", rst.getInt(i)));
                        break;
                    default:    // cualquier otro tipo. El polimorfismo efectua su magia
                        System.out.print(String.format("%20s", rst.getObject(i)));
                }
            }
            System.out.println("");
        }
    }
    
    public static int siete_PAConCallableStatement (int min, int max) {                   
        
        try {
            conBd = DriverManager.getConnection(dirUrl);
            
            String procedimiento = ("{call personas_edad (?, ?, ?)}");
            proc = conBd.prepareCall(procedimiento);
            
            //Establecemeos los valores de los parámetros de entrada
            proc.setInt(1, min);
            proc.setInt(2, max);
            //Registramos el parámetro de salida
            proc.registerOutParameter(3, Types.INTEGER);
            proc.execute();
            resultado = proc.getInt(3);
                        
            System.out.println("El resultado de la ejecución del procedimiento almacenado con los parámetros"
                               + " introducidos es: " + resultado);        
           
        } catch (SQLException e) {
            System.err.println("SQL ERROR mensaje: " + e.getMessage());  
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (proc != null){
                try {
                if (proc != null)
                    proc.close(); //Cerramos el PA
                } catch (SQLException e) {
                    System.err.println("SQL ERROR mensaje: " + e.getMessage()); 
                }
            }
            if (conBd != null){
                try {
                    conBd.close(); //Cerramos la conexión a la BD
                } catch (SQLException ex) {
                    System.err.println("No se puede cerrar la conexion ; " + ex);
                    }
            }
        }
        return resultado;
    }
    
    
}