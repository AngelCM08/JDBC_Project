package action;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Actions {
    public static void restart(Connection c){
        try {
            Statement st = null;
            st = c.createStatement();
            BufferedReader br = new BufferedReader(new FileReader("src/data/CreateTablesTboiaPostgreSql.sql"));
            st.execute(br.lines().collect(Collectors.joining(" \n")));
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void FillTable(Connection c, String tabla){
        //List<String[]> data = GetDataFromCSV(tabla);
        //TODO menuda fumada estoxD
        try {
            String query = "SELECT column_name FROM information_schema.columns WHERE table_a = '"+tabla+"';";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("column1"));
            }
            /*Statement st = c.createStatement();
            st.*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //String sentence = "INSERT INTO "+tabla+"() VALUES()";
        //PreparedStatement pst = c.prepareStatement(sentence);
    }

    public static List<String[]> GetDataFromCSV(String tabla){
        try {
            CSVReader reader = new CSVReader(new FileReader("src/data/"+tabla+".csv"));
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
