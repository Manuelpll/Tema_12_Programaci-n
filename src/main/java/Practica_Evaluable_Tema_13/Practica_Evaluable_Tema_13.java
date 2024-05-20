package Practica_Evaluable_Tema_13;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;

public class Practica_Evaluable_Tema_13 extends JFrame {
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
    private Connection con;
    private Statement instrucion;

    public Practica_Evaluable_Tema_13() {
        con = conectar();
        instrucion = crearStatement(con);

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
        mostrarEmpleadosAntiguosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostraEmpleadosAntiguos();
            }
        });
        eliminarEmpleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
            }
        });
        buscarEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEmpleado();
            }
        });
    }

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

    private void hacerConsulta(Statement instruccion, String consulta) {
        try {
            ResultSet resultado = instruccion.executeQuery(consulta);
            Consulta.setText("");
            if (resultado == null) {
                System.out.println("No se encontro el resultado");
            } else {
                while (resultado.next()) {
                    String nombre = resultado.getString("Nombre");
                    String apellido = resultado.getString("apellidos");
                    LocalDate fecha_de_nacimiento = resultado.getDate("fecha_de_nacimiento").toLocalDate();
                    LocalDate fecha_de_ingreso = resultado.getDate("fecha_de_ingreso").toLocalDate();
                    String puesto = resultado.getString("puesto");
                    double salario = resultado.getDouble("salario");
                    String informacionTotal = "Nombre: " + nombre + "\n Apellido: " + apellido +
                            "\nFecha de nacimiento: " + fecha_de_nacimiento + "\nFecha de ingreso: " + fecha_de_ingreso + "\nPuesto: " + puesto + "\nSalario: " + salario;
                    Consulta.append(informacionTotal);
                }
                resultado.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }

    private void hacerConsultaSuma(Statement instruccion, String consulta) {
        try {
            ResultSet resultado = instruccion.executeQuery(consulta);
            Consulta.setText("");
            while (resultado.next()) {
                double salario = resultado.getInt("Total");
                String informacion = "Salario: " + salario;
                Consulta.append(informacion);
            }
            resultado.close();
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }

    private void Insertarempleado(Statement instruccion, String insercion) {
        try {
            int filasAfectadas = instruccion.executeUpdate(insercion);
            System.out.println("Se ha insertado corectamente " + filasAfectadas + " fila");
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }

    private void eliminarEmpleado(Statement instruccion, String consulta) {
        try {
            int filasAfectadas = instruccion.executeUpdate(consulta);
            System.out.println("Se ha eliminado corectamente " + filasAfectadas + " fila");
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta: " + e.getMessage());
        }
    }

    private void cerrar(Connection con, Statement instruccion) {
        try {
            instrucion.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public void buscarEmpleado() {
        try {
            System.out.println("Cual empleado quieres imprimir?");
            String empleadoQuerido = JOptionPane.showInputDialog("Ingrese el nombre del empleado que quiere buscar");
            String consulta = "Select * from empleados where nombre = '" + empleadoQuerido + "'";
            hacerConsulta(instrucion, consulta);
        } catch (InputMismatchException e) {
            System.out.println("ERROR: No se puede poner una cosa que no sea una cadena de carácteres");
        }
    }

    public void eliminarEmpleado() {
        try {
            System.out.println("¿Qué empleado quieres eliminar?");
            String empleadoEliminado = JOptionPane.showInputDialog("¿Que empleado desea eliminar?");
            LocalDate fecha_de_finalizacion = LocalDate.now();
            String consulta1 = "INSERT INTO empleadosAntiguos (nombre, apellidos, fecha_de_nacimiento, fecha_de_ingreso, fecha_de_finalizacion) " +
                    "SELECT nombre, apellidos, fecha_de_nacimiento, fecha_de_ingreso, '" + fecha_de_finalizacion + "' " +
                    "FROM empleados WHERE nombre = '" + empleadoEliminado + "'";
            Insertarempleado(instrucion, consulta1);
            String consulta2 = "DELETE FROM empleados WHERE nombre = '" + empleadoEliminado + "'";
            eliminarEmpleado(instrucion, consulta2);
        } catch (InputMismatchException e) {
            System.out.println("Error: No se puede ingresar algo que no sea una cadena de caracteres");
        }
    }

    public void menuDeOrdenar() {
        try {
            eleccion = Integer.parseInt(JOptionPane.showInputDialog("""
                    1-> Por antiguedad
                    2-> Por salario
                    3-> Por Apellido"""));
            switch (eleccion) {
                case 1:
                    String consultaAntiguedad = "Select * from empleados order by fecha_de_nacimiento";
                    hacerConsulta(instrucion, consultaAntiguedad);
                    break;
                case 2:
                    String consultasalario = "Select * from empleados order by salario";
                    hacerConsulta(instrucion, consultasalario);
                    break;
                case 3:
                    String consultaApellido = "Select * from empleados order by apellidos";
                    hacerConsulta(instrucion, consultaApellido);
                    break;
                default:
                    System.out.println("Error: Opcion no valida");
            }
        } catch (InputMismatchException e2) {
            System.out.println("Inserte una de la opciones validas");
        }
    }

    public void añadirEmpleado() {
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
        do {
            try {
                nombre = JOptionPane.showInputDialog("Introduce el nombre del empleado");
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Error no puedes poner otra cosa que no sea un nombre ");
                return;
            }
            try {
                apellidos = JOptionPane.showInputDialog("Introduce los apellidos del empleado");
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Error no puedes poner otra cosa que no sea un nombre ");
                return;
            }
            try {
                año = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de nacimiento"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un  numero entero");
                return;
            }
            try {
                mes = Integer.parseInt(JOptionPane.showInputDialog("Introduce el mes de nacimiento"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un  numero entero");
                return;
            }
            try {
                dia = Integer.parseInt(JOptionPane.showInputDialog("Introduce el dia de nacimiento"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "No puede poner otra cosa que no sea un numero entero");
                return;
            }
            try {
                fechaDeNacimiento = LocalDate.of(año, mes, dia);
            } catch (DateTimeException e) {
                JOptionPane.showMessageDialog(null, "No existe esta fecha");
                return;
            }
            try {
                añoI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de ingreso a la empresa"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un  numero entero");

                return;
            }
            try {
                mesI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el mes de ingreso a la empresa"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un  numero entero");
                return;
            }
            try {
                diaI = Integer.parseInt(JOptionPane.showInputDialog("Introduce el dia de ingreso a la empresa"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "No puede poner otra cosa que no sea un numero entero");
                return;
            }
            try {
                fechaDeIngreso = LocalDate.of(añoI, mesI, diaI);
            } catch (DateTimeException e) {
                JOptionPane.showMessageDialog(null, "No existe esta fecha");
                return;
            }
            try {
                puesto = JOptionPane.showInputDialog("Introduce el puesto de trabajo");
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Error no puedes poner otra cosa que no sea un nombre ");
                return;
            }
            try {
                salario = Integer.parseInt(JOptionPane.showInputDialog("Introduce tu salario"));
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "No puedes poner otra cosa que no sea un numero");
                return;
            }
        } while (salario == 0);
        String consulta = "Insert into empleados values(" + "'" + nombre + "'" + "," + "'" + apellidos + "'" + ","
                + "'" + fechaDeIngreso + "'" + "," + "'" + fechaDeNacimiento + "'" + "," + "'" + puesto + "'" + "," + salario + ")";
        Insertarempleado(instrucion, consulta);
    }

    public void gastoTotal() {
        String consulta = " Select sum(salario) as Total from empleados";
        hacerConsultaSuma(instrucion, consulta);
    }

    public void mostraEmpleadosAntiguos() {
        String consultaAntiguedad = "Select * from empleadosAntiguos";
        hacerConsulta(instrucion, consultaAntiguedad);
    }
}
    public static void main(String[] args) {
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //JFrame frame = new JFrame("Practica_Evaluable_Tema_13");
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.add(new Practica_Evaluable_Tema_13());
                //frame.pack();
                //frame.setLocationRelativeTo(null);
                new Practica_Evaluable_Tema_13().setVisible(true);
            }
        });*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new Practica_Evaluable_Tema_13();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    //}
}
