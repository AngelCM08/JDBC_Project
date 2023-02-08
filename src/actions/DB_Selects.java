package actions;
import ui.ACBMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DB_Selects {
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

    //TO DO Seleccionar todos los elementos que cumplan una condición.
    //TO DO elementos superiores o inferiores al valor indicado
    //TO DO elementos de tamaño inferior o superior al indicado en cantidad de caracteres
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

    //TO DO Seleccionar una columna específica.
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
