package actions;

import java.sql.Connection;
import java.sql.SQLException;

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
    //TODO Posibilidad de eliminar un conjunto de registros de información que cumplan una condición.

    /*public static void deleteTableRegisterByCondition(Connection c, String tabla, int condition) {
        String id = DB_Actions.GetHeader(c, tabla).get(0).get(0);
        try {
            c.createStatement().executeUpdate("DELETE FROM "+tabla+" WHERE "+id+" = ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
}
