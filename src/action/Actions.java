package action;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            List<List<String>> columnMetaData = GetHeader(c,tabla);
            header = columnMetaData.get(0);
            data_types = columnMetaData.get(1);
            //System.out.println(Arrays.toString(header.toArray()));
            //System.out.println(Arrays.toString(data_types.toArray()));

            for (int i = 0; i < header.size(); i++) {
                if(i == 0) values = values.concat("(?, ");
                else if(i+1 == header.size()) values = values.concat("?)");
                else values = values.concat("?, ");
            }

            String insert = "INSERT INTO "+tabla+"("+header.toString().substring(1,header.toString().length()-1)+") VALUES "+values+";";
            PreparedStatement ps_insert = c.prepareStatement(insert);

            for (int i = 0; i < data.size(); i++) {
                String[] fields = data.get(i);
                //System.out.println(Arrays.toString(fields));
                try {
                    for (int j = 0; j < fields.length; j++) {
                        //System.out.println(fields[j]);
                        if(data_types.get(j).equals("integer")){
                            ps_insert.setInt(j+1, Integer.parseInt(fields[j]));
                        }else{
                            ps_insert.setString(j+1,fields[j]);
                        }
                    }
                    System.out.println(ps_insert);
                    ps_insert.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> GetHeader(Connection c, String tabla) {
        List<String> header = new ArrayList<>();
        List<String> data_types = new ArrayList<>();
        try {
            String query = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ? ORDER BY ordinal_position;";
            PreparedStatement ps_header = c.prepareStatement(query);
            ps_header.setString(1,tabla);
            ResultSet resultSet = ps_header.executeQuery();

            while (resultSet.next()) {
                header.add(resultSet.getString(1));
                data_types.add(resultSet.getString(2));
            }
            //header.forEach(System.out::println);
            //data_types.forEach(System.out::println);
            return List.of(header, data_types);
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

    public static void selectAllTable(Connection c, String tabla){
        try {
            ResultSet s = c.createStatement().executeQuery("SELECT * FROM "+tabla);
            while(s.next()){
                for (int i = 1; i <= s.getMetaData().getColumnCount(); i++) {
                    if(i != s.getMetaData().getColumnCount()) System.out.print(s.getMetaData().getColumnName(i) +": "+ s.getString(i) + "\t|\t");
                    else System.out.print(s.getMetaData().getColumnName(i) +": "+ s.getString(i));
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
            System.out.println("Que texto quieres buscar en la columna "+columna[0]);
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO Seleccionar todos los elementos que cumplan una condición.

    //TO DO Seleccionar una columna específica.
    public static void selectColumn(Connection c, String tabla, String[] columna) {
        try {
            PreparedStatement pst = c.prepareStatement("SELECT "+columna[0]+" FROM "+tabla);
            //TODO*********************************************************************************************
            System.out.println(pst.toString());
            ResultSet result = pst.executeQuery();
            System.out.println("Mostrando columna "+columna[0]+" de la tabla "+tabla);
            while(result.next()){
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    if(i != result.getMetaData().getColumnCount()) System.out.print(result.getMetaData().getColumnName(i) +": "+ result.getString(i) + " | ");
                    else System.out.print(result.getMetaData().getColumnName(i) +": "+ result.getString(i));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sortir(Connection c) {
        System.out.println("ADÉU!");
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println("Error al tancar la BBDD");
        }
        System.exit(0);
    }

}