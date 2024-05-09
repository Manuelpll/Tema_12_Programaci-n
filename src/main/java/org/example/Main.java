package org.example;
import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

/**
 * Este programa trata de un menu  con varias opciones entorno a una base de datos llamada empresa
 * @author Mparr
 * @version 1.0
 */
public class Main {
    private static final String url = "jdbc:mysql://192.168.80.153:3306/empresa";
    private static final String user = "manuel";
    private static final String password = "Opassword78%";
    static int eleccion;
    private  static Connection con = conectar();
     static Statement instrucion= crearStatement(con);

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
            if (resultado == null){
                System.out.println("No se encontro el resultado");
            }else {
                while (resultado.next()) {
                    String nombre = resultado.getString("Nombre");
                    System.out.println("Nombre: " + nombre);
                    String apellido = resultado.getString("apellidos");
                    System.out.println("Apellido: " + apellido);
                    LocalDate fecha_de_nacimiento = resultado.getDate("fecha_de_nacimiento").toLocalDate();
                    System.out.println("Fecha de nacimiento: " + fecha_de_nacimiento);
                    LocalDate fecha_de_ingreso = resultado.getDate("fecha_de_ingreso").toLocalDate();
                    System.out.println("Fecha de ingreso: " + fecha_de_ingreso);
                    String puesto = resultado.getString("puesto");
                    System.out.println("Puesto: " + puesto);
                    double salario = resultado.getDouble("salario");
                    System.out.println("Salario: " + salario);
                    System.out.println("------------------------------");
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
                while (resultado.next()) {
                  double salario = resultado.getInt("Total");
                    System.out.println("Suma total del salario: " + salario);
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
         * Metodo que genera un menu de las diferentes opciones que tienes para utilizar
         * @throws InputMismatchException Si pones una cosa que no sea un numero entero
         */
        public static void menu() {
            //Zona de declaracion de variables
            boolean salir =false;
            //Zona de inicializacion , añadir más cosas si es necesario
            Scanner scanner = new Scanner(System.in);
            //Zona de salida
            do {
                try {


                    System.out.println("""
                        Elige una de las siguientes opciones:
                        1-> Añadir empleado
                        2-> Eliminar empleado
                        3-> Buscar empleado
                        4-> Imprimir empleados ordenados de diferentes maneras
                        5-> Calcular el gasto total de los empleados
                        6-> Salir""");
                    eleccion = scanner.nextInt();
                    scanner.nextLine();//Si es necesario se pone
                    switch (eleccion) {
                        case 1:
                            añadirEmpleado(scanner);
                            break;
                        case 2:
                            eliminarEmpleado(scanner);
                            break;
                        case 3:
                            buscarEmpleado(scanner);
                            break;
                        case 4:
                            menuDeOrdenar(scanner);
                            break;
                        case 5:
                            gastoTotal();
                            break;
                        case 6:
                            System.out.println("Saliendo...");
                            salir = true;
                            cerrar(con,instrucion);
                            break;
                        default:
                            System.out.println("Error: Opcion no valida");
                            break;
                    }//Fin switch
                }catch (InputMismatchException e){
                    System.out.println("Inserta una de las opciones validas");
                    scanner.nextLine();
                }//Fin try-catch
            } while (!salir);
        }//Fin de menu

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
         * @param scanner -El scanner para introducir la opcion
         */
        public static void menuDeOrdenar(Scanner scanner) {
            try {
                int enumeracion= 1;
                System.out.println("""
                    1-> Por antiguedad
                    2-> Por salario
                    3-> Por Apellido""");
                eleccion = scanner.nextInt();
                scanner.nextLine();
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
         * @param scanner -El scanner que se necesita para insertar los datos
         * @throws InputMismatchException Si se pone un input inadecuado
         * @throws DateTimeException Si se pone una fecha imposible
         */
        public static void añadirEmpleado(Scanner scanner) {
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
                    nombre = scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Error no puedes poner otra cosa que no sea un nombre ");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce los apellidos del empleado");
                    apellidos = scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Error no puedes poner otra cosa que no sea un nombre ");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce el año de nacimiento");
                    año = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("No puedes poner otra cosa que no sea un  numero entero");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce el mes de nacimiento");
                    mes = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("No puedes poner otra cosa que no sea un  numero entero");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce el día de nacimiento");
                    dia = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("No puede poner otra cosa que no sea un numero entero");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    fechaDeNacimiento = LocalDate.of(año, mes, dia);
                } catch (DateTimeException e) {
                    System.out.println("No existe esta fecha");
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce el año de ingreso a la empresa");
                    añoI = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("No puedes poner otra cosa que no sea un  numero entero");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce el mes de ingreso a la empresa");
                    mesI = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("No puedes poner otra cosa que no sea un  numero entero");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce el día de ingreso a la empresa");
                    diaI = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("No puede poner otra cosa que no sea un numero entero");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    fechaDeIngreso = LocalDate.of(añoI, mesI, diaI);
                } catch (DateTimeException e) {
                    System.out.println("No existe esta fecha");
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Indica tu puesto de trabajo");
                    puesto = scanner.nextLine();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Error no puedes poner otra cosa que no sea un nombre ");
                    scanner.nextLine();
                    return;
                }//Fin try-catch
                try {
                    System.out.println("Introduce tu salario");
                    salario = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("No puedes poner otra cosa que no sea un numero");
                    scanner.nextLine();
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



        /**
         * Metodo que ejecuta el codigo
         * @param args Los argumentos de la linea de comandos
         */
        public static void main(String[] args) {
            menu();
        }//Fin del main
    }//Fin de Main
