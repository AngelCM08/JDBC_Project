package actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DB_Deletes {
    public static void deleteTable(Connection c, String tabla){
        try {
            c.createStatement().executeUpdate("DROP TABLE "+tabla+" CASCADE");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteTableRegister(Connection c, String tabla, int register) {
        String id = DB_Actions.GetHeader(c, tabla).get(0).get(0);
        try {
            c.createStatement().executeUpdate("DELETE FROM "+tabla+" WHERE "+id+" = "+register);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTableRegisterByCondition(Connection c, String tabla, String[] columna) {
        Scanner sc = new Scanner(System.in);
        String text;
        int value;
        try {
            System.out.print("Cuál es el texto que contienen los registros a eliminar en la columna "+columna[0]+": ");
            PreparedStatement insert_pst;
            PreparedStatement select_pst;
            if(columna[1].equals("integer")){
                value = sc.nextInt();
                sc.nextLine();

                insert_pst = c.prepareStatement("DELETE FROM "+tabla+" WHERE "+columna[0]+" = ?");
                insert_pst.setInt(1, value);

                select_pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+columna[0]+" = ?");
                select_pst.setInt(1, value);
            }else{
                text = sc.nextLine();

                select_pst = c.prepareStatement("SELECT * FROM "+tabla+" WHERE "+columna[0]+" ILIKE ?");
                select_pst.setString(1, text);

                insert_pst = c.prepareStatement("DELETE FROM "+tabla+" WHERE "+columna[0]+" ILIKE ?");
                insert_pst.setString(1, text);
            }

            ResultSet result = select_pst.executeQuery();
            insert_pst.executeUpdate();

            System.out.println("**** SE HAN ELIMINADO LOS SIGUIENTES REGISTROS ****");
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
}
