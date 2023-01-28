package action;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Actions {
    public static void restart(Connection c){
        try {
            Statement st = null;
            st = c.createStatement();
            BufferedReader br = new BufferedReader(new FileReader("src/data/schema.sql"));
            st.execute(br.lines().collect(Collectors.joining(" \n")));
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void FillTable(Connection c, String tabla){
        String values = "";
        List<String> header = new ArrayList<>();
        List<String> data_types = new ArrayList<>();
        List<String[]> data = GetDataFromCSV(tabla);

        try {
            String query = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?;";
            PreparedStatement ps_header = c.prepareStatement(query);
            ps_header.setString(1,tabla);
            ResultSet resultSet = ps_header.executeQuery();

            while (resultSet.next()) {
                header.add(resultSet.getString(1));
                data_types.add(resultSet.getString(2));
            }
            data_types.forEach(System.out::println);

            for (int i = 0; i < header.size(); i++) {
                if(i == 0) values = values.concat("(?, ");
                else if(i+1 == header.size()) values = values.concat("?)");
                else values = values.concat("?, ");
            }

            String insert = "INSERT INTO "+tabla+"("+header.toString().substring(1,header.toString().length()-1)+") VALUES "+values+";";
            PreparedStatement ps_insert = c.prepareStatement(insert);

            for (int i = 0; i < data.size(); i++) {
                String[] fields = data.get(i);
                try {
                    for (int j = 0; j < fields.length; j++) {
                        if(data_types.get(j).equals("integer")){
                            ps_insert.setInt(i, Integer.parseInt(fields[j]));
                        }else{
                            ps_insert.setString(i,fields[j]);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println(ps_insert);
            System.out.println(ps_insert.execute());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String[]> GetDataFromCSV(String tabla){
        try {
            CSVReader reader = new CSVReader(new FileReader("src/data/"+tabla+".csv"));
            List<String[]> entities = reader.readAll();
            entities.remove(0);
            return entities;
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
