package org.example;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

/**
 * Este programa trata de un menu de los empleados donde puedes añadir,eliminar , mostrar los que hay y ordenar la lista de diferentes maneras
 * @author Mparr
 * @version 1.0
 */
public class Main {
    private static final String url = "jdbc:mysql://192.168.80.153:3306/empresa";
    private static final String user = "manuel";
    private static final String password = "Opassword78%";
    static int eleccion;
    private static Connection conectar() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            return null;
        }//Fin try-catch
    }//Fin de conectar


    /**
     * Metodo que genera un menu de las diferentes opciones que tienes para utilizar
     * @throws InputMismatchException Si pones una cosa que no sea un numero entero
     */
    public static void menu() {
        boolean salir = false;
        do {
            try {
                String[] options = {"Añadir empleado", "Eliminar empleado", "Buscar empleado", "Imprimir empleados ordenados", "Calcular gasto total", "Salir"};
                eleccion = JOptionPane.showOptionDialog(null, "Elige una de las siguientes opciones:", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]) + 1;
                switch (eleccion) {
                    case 1:
                        añadirEmpleado();
                        break;
                    case 2:
                        eliminarEmpleado();
                        break;
                    case 3:
                        buscarEmpleado();
                        break;
                    case 4:
                        menuDeOrdenar();
                        break;
                    case 5:
                        gastoTotal();
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(null, "Saliendo...");
                        salir = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error: Opción no válida");
                        break;
                }//Fin switch
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Inserta una de las opciones válidas");
            }//Fin try-catch
        } while (!salir);
    } // Fin de menu

    /**
     * Este metodo busca a un empleado por su nombre
     * @throws InputMismatchException Si se pone otra cosa que no sea una cadena de carácteres
     */
    public static void buscarEmpleado() {





    } // Fin de buscarEmpleado


    /**
     * Este metodo elimina un empleado que se desee
     * @throws InputMismatchException Si se pone un input incorrecto
     */
    public static void eliminarEmpleado() {

    } // Fin de eliminarEmpleado

    /**
     * Menu que genera varias opciones para ordenar el arraylist
     */
    public static void menuDeOrdenar() {
        try {
            int enumeracion = 1;
            String informacionFinal = " ";
            String[] options = {"Por antigüedad", "Por salario", "Por apellido"};
            eleccion = JOptionPane.showOptionDialog(null, "Elige una opción para ordenar la lista:", "Ordenar Lista", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]) + 1;
            switch (eleccion) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error: Opción no válida");
                    break;
            }//Fin switch
        } catch (InputMismatchException e2) {
            JOptionPane.showMessageDialog(null, "Inserta una de las opciones válidas");
        }//Fin try-catch
    } // Fin de menuDeOrdenar

    /**
     * Este metodo añade un empleado nuevo a el arraylist
     * @throws InputMismatchException Si se pone un input inadecuado
     * @throws DateTimeException Si se pone una fecha imposible
     */
    public static void añadirEmpleado() {
        String nombre;
        String apellidos;
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
        try {
            nombre = JOptionPane.showInputDialog("Introduce el nombre del empleado");
        } catch (InputMismatchException e) {
            JOptionPane.showMessageDialog(null, "Error: no puedes poner otra cosa que no sea un nombre");
            return;
        }//Fin try-catch
        try {
            apellidos = JOptionPane.showInputDialog("Introduce los apellidos del empleado");
        } catch (InputMismatchException e) {
            JOptionPane.showMessageDialog(null, "Error: no puedes poner otra cosa que no sea un nombre");
            return;
        }//Fin try-catch
        try {
            año = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de nacimiento"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un número entero");
            return;
        }//Fin try-catch
        try {
            mes = Integer.parseInt(JOptionPane.showInputDialog("Introduce el mes de nacimiento"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un número entero");
            return;
        }//Fin try-catch
        try {
            dia = Integer.parseInt(JOptionPane.showInputDialog("Introduce el día de nacimiento"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un número entero");
            return;
        }//Fin try-catch
        try {
            fechaDeNacimiento = LocalDate.of(año, mes, dia);
        } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(null, "No existe esta fecha");
            return;
        }//Fin try-catch
        try {
            añoI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de ingreso a la empresa"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un número entero");
            return;
        }//Fin try-catch
        try {
            mesI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el mes de ingreso a la empresa"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un número entero");
            return;
        }//Fin try-catch
        try {
            diaI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el día de ingreso a la empresa"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un número entero");
            return;
        }//Fin try-catch
        try {
            fechaDeIngreso = LocalDate.of(añoI, mesI, diaI);
        } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(null, "No existe esta fecha");
            return;
        }//Fin try-catch
        try {
            puesto = JOptionPane.showInputDialog("Indica tu puesto de trabajo");
        } catch (InputMismatchException e) {
            JOptionPane.showMessageDialog(null, "Error: no puedes poner otra cosa que no sea un nombre");
            return;
        }//Fin try-catch
        try {
            salario = Integer.parseInt(JOptionPane.showInputDialog("Introduce tu salario"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un número");
            return;
        }//Fin try-catch
    } // Fin de añadirEmpleado

    /**
     * Metodo que calcula el gasto total de todos los empleados
     */
    public static void gastoTotal() {
        int total = 0;

    } // Fin de gastoTotal

    /**
     * Metodo que ejecuta el codigo
     * @param args Los argumentos de la linea de comandos
     */
    public static void main(String[] args) {
    conectar();
    } // Fin de main
}//Fin del Main
