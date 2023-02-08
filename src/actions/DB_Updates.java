package actions;

import ui.ACBMenu;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class DB_Updates {
    //TODO Seleccionar un elemento concreto y permitir su modificación.
    public static void UpdateRegister(Connection c, String tabla, int row) {
        Scanner sc = new Scanner(System.in);
        List<List<String>> header = DB_Actions.GetHeader(c, tabla);
        ACBMenu menu = new ACBMenu();
        PreparedStatement pst;
        String column;
        String change;
        boolean out = false;
        int resp;

        try {
            Statement st = c.createStatement();
            do{
                ResultSet result = st.executeQuery("SELECT * FROM "+tabla+" WHERE "+header.get(0).get(0)+" = "+row);
                if((resp = menu.SelectFieldMenu(result)) != 0){
                    System.out.print("Introduce el valor actualizado: ");
                    change = sc.nextLine();
                    column = header.get(0).get(resp-1);

                    if(header.get(1).get(resp-1).equals("integer")){
                        pst = c.prepareStatement("UPDATE "+tabla+" SET "+column+" = ? WHERE "+header.get(0).get(0)+" = "+row);
                        pst.setInt(1, Integer.parseInt(change));
                    }else{
                        pst = c.prepareStatement("UPDATE "+tabla+" SET "+column+" = ? WHERE "+header.get(0).get(0)+" = "+row);
                        pst.setString(1, change);
                    }
                    System.out.println(pst.toString());
                    pst.executeUpdate();
                    System.out.println("**** REGISTRO ACTUALIZADO CORRECTAMENTE ****");
                }else break;
                System.out.print("1. Sí.\n" +
                                "2. No.\n"+
                                "Quieres seguir actualizando datos? ");
                if(sc.nextInt() == 2) out = true;
                sc.nextLine();
            }while(!out);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO Posibilidad de modificar diferentes registros de información.
    public static void updateRegistersByCondition(Connection c, String tabla, String[] columna) {
        Scanner sc = new Scanner(System.in);
        PreparedStatement insert_pst;
        String text;
        String change;
        try {
            System.out.print("Cuál es el valor a reemplazar en la columna "+columna[0]+": ");
            text = sc.nextLine();
            System.out.print("Cuál será el valor actualizado: ");
            change = sc.nextLine();
            if(columna[1].equals("integer")){
                insert_pst = c.prepareStatement("UPDATE "+tabla+" SET "+columna[0]+" = ? WHERE "+columna[0]+" = ?");
                insert_pst.setInt(1, Integer.parseInt(change));
                insert_pst.setInt(2, Integer.parseInt(text));
            }else{
                insert_pst = c.prepareStatement("UPDATE "+tabla+" SET "+columna[0]+" = ? WHERE "+columna[0]+" ILIKE ?");
                insert_pst.setString(1, change);
                insert_pst.setString(2, text);
            }
            System.out.println("**** SE HAN ACTUALIZADO "+insert_pst.executeUpdate()+" REGISTROS ****");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
