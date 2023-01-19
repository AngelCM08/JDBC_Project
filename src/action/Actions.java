package action;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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
        /*switch (tabla){
            case "monstruo":

                break;

            case "objeto":

                break;

            case "personaje":

                break;
        }*/
        try {
            CSVReader reader = new CSVReader(new FileReader("src/data/"+tabla+".csv"));
            Stream<String[]> rows = reader.readAll().stream();
            rows.forEach(s -> System.out.println(Arrays.toString(s)));
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
