package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseTest {
    private static final String url = "jdbc:mysql://192.168.80.153:3306/prueba";
    private static final String user = "manuel";
    private static final String password = "Opassword78%";

    private static Connection conectar() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            return null;
        }
    }

    private static Statement crearStatement(Connection con) {
        try {
            return con.createStatement();
        } catch (SQLException e) {
            System.out.println("Error al crear el statement: " + e.getMessage());
            return null;
        }
    }

    private static void hacerConsulta(Statement instruccion) {
        try {
            String consulta = "SELECT * FROM personas";
            ResultSet resultado = instruccion.executeQuery(consulta);
            while (resultado.next()) {
                String nombre = resultado.getString("Nombre");
                System.out.println(nombre);
                String apellido = resultado.getString("apellidos");
                System.out.println(apellido);
                int telefono = resultado.getInt("teléfono");
                System.out.println(telefono);
            }
            resultado.close();
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }

    private static void cerrar(Connection con, Statement instruccion) {
        try {
            instruccion.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        /*String url = "jdbc:mysql://192.168.80.153:3306/prueba";
        String user = "manuel";
        String password = "Opassword78%";
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            Statement instrution = con.createStatement();
            System.out.println("Conexión exitosa!");//Creo un Statemet instruction para hacer consultas, inserciones ,etc

        String consulta = "SELECT * FROM personas";
        ResultSet resultado = instrution.executeQuery(consulta);
         while(resultado.next()){
               String nombre = resultado.getString("Nombre");
               System.out.println(nombre);
                String apellido = resultado.getString("apellidos");
                   System.out.println(apellido);
                   int telefono= resultado.getInt("teléfono");
                   System.out.println(telefono);
            }//Fin while
        resultado.close();
        instrution.close();
        con.close();

        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }//Fin try-cath*/
        Connection con = conectar();
        Statement instrucion = crearStatement(con);
        hacerConsulta(instrucion);
        cerrar(con, instrucion);
    }//Fin main
}//Fin DatabaseTest