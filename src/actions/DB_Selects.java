package actions;

import ui.ACBMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *  Clase en la que hay funciones estáticas para acciones relacionadas
 *  con la selección de tablas y registros de la BBDD.
 */
public class DB_Selects {
    /**
     * Función que permite mostrar el contenido de los registros de la tabla seleccionada por el usuario.
     * Cabe mencionar que no se muestra el icono en éstos casos por temas de formateo de la consola.
     *
     * @param c Objeto de la conexión con la BBDD.
     * @param tabla Nombre de la tabla que se quiere mostrar.
     */
    public static void selectAllTable(Connection c, String tabla){
        try {
            ResultSet s = c.createStatement().executeQuery("SELECT * FROM "+tabla);
            while(s.next()){
                for (int i = 1; i <= s.getMetaData().getColumnCount(); i++) {
                    if(!s.getMetaData().getColumnName(i).equals("icono")){
                        if(i != s.getMetaData().getColumnCount()) System.out.print(s.getMetaData().getColumnName(i) +": "+ s.getString(i) + "\t|\t");
                        else System.out.print(s.getMetaData().getColumnName(i) +": "+ s.getString(i));
                    }
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Función que muestra los registros de la tabla pasada por parámetro cuya información
     * en la columna indicada coincida con el valor introducido por el usuario.
     *
     * @param c Objeto de la conexión con la BBDD.
     * @param tabla Nombre de la tabla de la que se quieren mostrar registros.
     * @param columna Array que indica el nombre y tipo de dato de la columna seleccionada para comparar.
     */
    //Seleccionar todos los elementos que contengan un texto concreto.
    public static void selectSpecificText(Connection c, String tabla, String[] columna){
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Que texto quieres buscar en la columna "+columna[0]+": ");
            PreparedStatement pst;
            if(columna[1].equals("integer")){
                pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+columna[0]+" = ?");
                pst.setInt(1, sc.nextInt());
                sc.nextLine();
            }else{
                pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+columna[0]+" ILIKE ?");
                pst.setString(1, sc.nextLine());
            }

            ResultSet result = pst.executeQuery();
            while(result.next()){
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    if(i != result.getMetaData().getColumnCount()) System.out.print(result.getMetaData().getColumnName(i) +": "+ result.getString(i) + " | ");
                    else System.out.print(result.getMetaData().getColumnName(i) +": "+ result.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Función que muestra los registros de la tabla pasada por parámetro cuya información
     * en la columna indicada coincida con el valor introducido por el usuario.
     * Muy similar a la función "selectSpecificText" pero contemplando otro tipo de condición.
     *
     * @param c Objeto de la conexión con la BBDD.
     * @param tabla Nombre de la tabla de la que se quieren mostrar registros.
     * @param columna Array que indica el nombre y tipo de dato de la columna seleccionada para comparar.
     */
    public static void selectByCondition(Connection c, String tabla, String[] columna) {
        Scanner sc = new Scanner(System.in);
        ACBMenu menu = new ACBMenu();
        int condition = menu.ConsultasPorCondicionesMenu();
        System.out.print("Cuál es la cantidad que quieres filtrar: ");
        int cantidad = sc.nextInt();
        PreparedStatement pst = null;
        try{
            if(condition == 1){
                if(columna[1].equals("integer")){
                    pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+columna[0]+" > ?");
                }else{
                    pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE length("+columna[0]+") > ?");
                }
            }else if(condition == 2) {
                if(columna[1].equals("integer")){
                    pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+columna[0]+" < ?");
                }else{
                    pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE length("+columna[0]+") < ?");
                }
            }
            assert pst != null;
            pst.setInt(1, cantidad);
            System.out.println(pst.toString());
            ResultSet result = pst.executeQuery();
            while(result.next()){
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    if(i != result.getMetaData().getColumnCount()) System.out.print(result.getMetaData().getColumnName(i) +": "+ result.getString(i) + " | ");
                    else System.out.print(result.getMetaData().getColumnName(i) +": "+ result.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Función que muestra todos los campos de una tabla y columna específicamente introducida por el usuario.
     *
     * @param c Objeto de la conexión con la BBDD.
     * @param tabla Nombre de la tabla de la que se quieren mostrar campos.
     * @param columna Array que indica el nombre y tipo de dato de la columna seleccionada.
     */
    public static void selectColumn(Connection c, String tabla, String[] columna) {
        try {
            PreparedStatement pst = c.prepareStatement("SELECT "+columna[0]+" FROM "+tabla);
            //System.out.println(pst.toString());
            ResultSet result = pst.executeQuery();
            System.out.println("Mostrando columna "+columna[0]+" de la tabla "+tabla);
            int i = 1;
            while(result.next()){
                System.out.println(i+": "+ result.getString(1));
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
