package Practica_Evaluable_Tema_13;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Practica_Evaluable_Tema_13  extends JFrame {
    private JTextArea Consulta;
    private JButton añadirEmpleadosButton;
    private JButton eliminarEmpleadosButton;
    private JButton buscarEmpleadoButton;
    private JButton mostrarEmpleadosDeDiferentesButton;
    private JButton mostrarEmpleadosAntiguosButton;
    private JButton gastoTotalButton;
    private static final String url = "jdbc:mysql://192.168.80.153:3306/empresa";
    private static final String user = "manuel";
    private static final String password = "Opassword78%";
    static int eleccion;
    private  static Connection con = conectar();
    static Statement instrucion= crearStatement(con);

    public Practica_Evaluable_Tema_13() {
        añadirEmpleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  añadirEmpleado();
            }
        });
        mostrarEmpleadosDeDiferentesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDeOrdenar();
            }
        });
        gastoTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gastoTotal();
            }
        });
    }

    /**
     * Metodo que hace la conexion a la base datos
     * @return Si da una excepcion no retorna nada
     */
    private static Connection conectar() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            return null;
        }//Fin try-catch
    }//Fin de conectar

    /**
     * Metodo que crea el Statement que se usara para hacer consultas,inserciones y delete
     * @param con La conexion a la base de datos
     * @return Si da una  excepcion no retorna nada
     */
    private static Statement crearStatement(Connection con) {
        try {
            return con.createStatement();
        } catch (SQLException e) {
            System.out.println("Error al crear el statement: " + e.getMessage());
            return null;
        }//Fin try-catch
    }//Fin crearStament

    /**
     * Metodo que te realiza una consulta
     * @param instruccion Statement que realiza la consulta
     * @param consulta La consulta para obtener la información
     */
    private static void hacerConsulta(Statement instruccion, String consulta) {
        try {
            ResultSet resultado = instruccion.executeQuery(consulta);
            Consulta.setText("");
            if (resultado == null){
                System.out.println("No se encontro el resultado");
            }else {
                while (resultado.next()) {
                    String nombre = resultado.getString("Nombre");
                    String apellido = resultado.getString("apellidos");
                    LocalDate fecha_de_nacimiento = resultado.getDate("fecha_de_nacimiento").toLocalDate();
                    LocalDate fecha_de_ingreso = resultado.getDate("fecha_de_ingreso").toLocalDate();
                    String puesto = resultado.getString("puesto");
                    double salario = resultado.getDouble("salario");
                    String informacionTotal="Nombre: "+nombre+"\n Apellido: "+apellido+
                            "\nFecha de nacimiento"+fecha_de_nacimiento+"\nFecha de ingreso: "+fecha_de_ingreso+"\nPuesto: "+puesto+"\nSalario: "+salario;
                    Consulta.append(informacionTotal);
                }//Fin while
                resultado.close();
            }//Fin if-else
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }//Fin de hacerConsulta

    /**
     * Metodo que hace la consulta de la suma de salarios
     * @param instruccion Statement que realizara la consulata
     * @param consulta Consulta con la funcion sum()
     * @throws SQLException si la insercion esta mal hecha
     */
    private static void hacerConsultaSuma(Statement instruccion, String consulta) {
        try {
            ResultSet resultado = instruccion.executeQuery(consulta);
            Consulta.setText("");
            while (resultado.next()) {
                double salario = resultado.getInt("Total");
                String informacion = "Salario: "+salario;
              Consulta.append(informacion);
            }//Fin while
            resultado.close();
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }//Fin de hacerConsulta

    /**
     * Metodo que hace una insercion
     * @param instruccion Statement que realizara la insercion
     * @param insercion Insercion del nuevo empleado
     * @throws SQLException si la insercion esta mal hecha
     */
    private static void Insertarempleado(Statement instruccion, String insercion) {
        try {
            int filasAfectadas = instruccion.executeUpdate(insercion);
            System.out.println("Se ha insertado corectamente " + filasAfectadas+" fila");
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }//Fin InsertarEmpleado

    /**
     * Metodo que realiza la consulta de elimnar
     * @param instruccion Statement que realiza el delete
     * @param consulta Delete del registro
     * @throws SQLException si el delete esta mal hecho
     */
    private static void eliminarEmpleado(Statement instruccion, String consulta) {
        try {
            int filasAfectadas = instruccion.executeUpdate(consulta);
            System.out.println("Se ha eliminado corectamente " + filasAfectadas+" fila");
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }//Fin EliminarEmpleado

    /**
     * Metodo cierra la conexion
     * @param con Conexion a la base de datos
     * @param instruccion Staement que realiza las acciones a la base de datos
     * @throws SQLException si la insercion esta mal hecha
     */
    private static void cerrar(Connection con, Statement instruccion) {
        try {
            instruccion.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }//Fin try-catch
    }//Fin cerrar
    /**
     * Este metodo busca a un empleado por su nombre
     * @param scanner -El scanner que se necesita para recibir los imputs
     * @throws InputMismatchException Si se pone otra cosa que no sea una cadena de carácteres
     */
    public static void buscarEmpleado(Scanner scanner) {
        try {
            System.out.println("Cual empleado quieres imprimir?");
            String empleadoQuerido = scanner.nextLine();
            String consulta = "Select * from empleados where nombre = '" + empleadoQuerido + "'";
            hacerConsulta(instrucion,consulta);
        }catch (InputMismatchException e){
            System.out.println("ERROR: No se puede poner una cosa que no sea una cadena de carateres");
        }//Fin try-catch
    }//Fin de buscarEmpleado

    /**
     * Este metodo elimina un empleado que se desee
     * @param scanner -El scanner que se necesita para los inputs
     */
    public static void eliminarEmpleado(Scanner scanner) {
        try {
            System.out.println("¿Qué empleado quieres eliminar?");
            String empleadoEliminado = scanner.nextLine();
            LocalDate fecha_de_finalizacion = LocalDate.now();
            String consulta1 = "INSERT INTO empleadosAntiguos (nombre, apellidos, fecha_de_nacimiento, fecha_de_ingreso, fecha_de_finalizacion) " +
                    "SELECT nombre, apellidos, fecha_de_nacimiento, fecha_de_ingreso, '" + fecha_de_finalizacion + "' " +
                    "FROM empleados WHERE nombre = '" + empleadoEliminado + "'";
            Insertarempleado(instrucion, consulta1);
            String consulta2 = "DELETE FROM empleados WHERE nombre = '" + empleadoEliminado + "'";
            eliminarEmpleado(instrucion, consulta2);
        } catch (InputMismatchException e) {
            System.out.println("Error: No se puede ingresar algo que no sea una cadena de caracteres");
            scanner.nextLine();
        }//Fin try-catch
    }
    /**
     * Menu que genera varias opciones para ordenar el arraylist
     */
    public static void menuDeOrdenar() {
        try {
            int enumeracion= 1;
            System.out.println("""
                    1-> Por antiguedad
                    2-> Por salario
                    3-> Por Apellido""");
            eleccion = Integer.parseInt(JOptionPane.showInputDialog("""
                    1-> Por antiguedad
                    2-> Por salario
                    3-> Por Apellido"""));
            switch (eleccion) {
                case 1:
                    String consultaAntiguedad = "Select * from empleados order by fecha_de_nacimiento";
                    hacerConsulta(instrucion,consultaAntiguedad);
                    break;
                case 2:
                    String consultasalario = "Select * from empleados order by salario";
                    hacerConsulta(instrucion,consultasalario);
                    break;
                case 3:
                    String consultaApellido = "Select * from empleados order by apellidos";
                    hacerConsulta(instrucion,consultaApellido);
                    break;
                default:
                    System.out.println("Error: Opcion no valida");
            }//Fin switch
        }catch (InputMismatchException e2){
            System.out.println("Inserte una de la opciones validas");
        }//Fin del try-catch
    }//Fin de menuDeOrdenar

    /**
     * Este metodo añade un empleado nuevo a el arraylist
     * @throws InputMismatchException Si se pone un input inadecuado
     * @throws DateTimeException Si se pone una fecha imposible
     */
    public static void añadirEmpleado() {
        String nombre ;
        String apellidos ;
        int año = 0;
        int mes = 0;
        int dia = 0;
        int añoI = 0;
        int mesI = 0;
        int diaI = 0;
        String puesto = "";
        int salario = 0;
        LocalDate fechaDeIngreso;
        LocalDate fechaDeNacimiento;
        do {
            try {
                System.out.println("Introduce el nombre del empleado");
                nombre = JOptionPane.showInputDialog("Introduce el nombre del empleado");
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null,"Error no puedes poner otra cosa que no sea un nombre ");
                return;
            }//Fin try-catch
            try {
                apellidos =JOptionPane.showInputDialog("Introduce los apellidos del empleado");
            } catch (InputMismatchException e) {
             JOptionPane.showMessageDialog(null,"Error no puedes poner otra cosa que no sea un nombre ");
                return;
            }//Fin try-catch
            try {
                System.out.println("Introduce el año de nacimiento");
                año = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de nacimiento"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null,"No puedes poner otra cosa que no sea un  numero entero");
                return;
            }//Fin try-catch
            try {
                System.out.println("Introduce el mes de nacimiento");
                mes = Integer.parseInt(JOptionPane.showInputDialog("Introduce el mes de nacimiento"));
            } catch (InputMismatchException e) {
            JOptionPane.showMessageDialog(null,"No puedes poner otra cosa que no sea un  numero entero");
                return;
            }//Fin try-catch
            try {
                System.out.println("Introduce el día de nacimiento");
                dia = Integer.parseInt(JOptionPane.showInputDialog("Introduce el dia de nacimiento"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null,"No puede poner otra cosa que no sea un numero entero");
                return;
            }//Fin try-catch
            try {
                fechaDeNacimiento = LocalDate.of(año, mes, dia);
            } catch (DateTimeException e) {
                JOptionPane.showMessageDialog(null,"No existe esta fecha");
                return;
            }//Fin try-catch
            try {
                System.out.println("Introduce el año de ingreso a la empresa");
                añoI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de ingreso a la empresa"));
            } catch (InputMismatchException e) {
              JOptionPane.showMessageDialog(null,"No puedes poner otra cosa que no sea un  numero entero");

                return;
            }//Fin try-catch
            try {
                System.out.println("Introduce el mes de ingreso a la empresa");
                mesI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el mes de ingreso a la empresa"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null,"No puedes poner otra cosa que no sea un  numero entero");
                return;
            }//Fin try-catch
            try {
                diaI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el dia de ingreso a la empresa"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null,"No puede poner otra cosa que no sea un numero entero");
                return;
            }//Fin try-catch
            try {
                fechaDeIngreso = LocalDate.of(añoI, mesI, diaI);
            } catch (DateTimeException e) {
                JOptionPane.showMessageDialog(null,"No existe esta fecha");
                return;
            }//Fin try-catch
            try {
                puesto = JOptionPane.showInputDialog("Introduce el puesto de trabajo");
            } catch (InputMismatchException e) {
              JOptionPane.showMessageDialog(null,"Error no puedes poner otra cosa que no sea un nombre ");
                return;
            }//Fin try-catch
            try {
                System.out.println("Introduce tu salario");
                salario = Integer.parseInt(JOptionPane.showInputDialog("Introduce tu salario"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null,"No puedes poner otra cosa que no sea un numero");
                return;
            }//Fin try-catch
        }while (salario ==0);
        String consulta= "Insert into empleados values("+"'"+nombre+"'"+","+"'"+apellidos+"'"+","
                +"'"+fechaDeIngreso+"'"+","+"'"+fechaDeNacimiento+"'"+","+"'"+puesto+"'"+","+salario+")";
        Insertarempleado(instrucion,consulta);
    }//Fin de añadirEmpleado

    /**
     * Metodo que calcula el gasto total
     */
    public static void gastoTotal(){
        String consulta =" Select sum(salario) as Total from empleados";
        hacerConsultaSuma(instrucion,consulta);
    }//Fin de gastoTotal
    public void  mostraEmpleadosAntiguos(){
        String consultaAntiguedad = "Select * from empleadosAntiguos";
        hacerConsulta(instrucion,consultaAntiguedad);
    }//Fin de  mostraEmpleadosAntiguos
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run(){
                JFrame frame = new JFrame("Practica_Evaluable_Tema_13");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add( new Practica_Evaluable_Tema_13());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }//Fin de main
}//Fin de Practica_Evaluable_Tema_13
