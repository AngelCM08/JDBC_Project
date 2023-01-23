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
import java.util.ArrayList;
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
        String values = "";
        List<String> header = new ArrayList<>();
        List<String[]> data = GetDataFromCSV(tabla);
        data.forEach(d -> System.out.println(Arrays.toString(d)));
        try {
            String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ?;";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1,tabla);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                header.add(resultSet.getString(1));
            }

            for (int i = 0; i < header.size(); i++) {
                if(i == 0) values = values.concat("(?, ");
                else if(i+1 == header.size()) values = values.concat("?)");
                else values = values.concat("?, ");
            }

            String insert = "INSERT INTO ? "+values+" VALUES "+values;
            ps = c.prepareStatement(insert);

            for (int i = 1; i <= header.size(); i++) {
                //TODO Xd
                ps.setString(i, );
            }

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
