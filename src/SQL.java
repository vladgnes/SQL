/**
 * Created by vlad on 28.03.2016.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;


public class SQL {
    public static final String DATABASENAME = "text";
    public static final String USERNAME = "root";
    public static final String TABLENAME = "some";
    public static final String PASSWORD = "021714g";

    public static void main(String [] args) throws IOException, SQLException, ClassNotFoundException {
        String text = "";//string for reading from file
        String [] words;//after reading split string to words
        text = ReadingFromFile(text);
        words = text.split(" |\\.|'|-|,|!|\\.\\.\\.");
        HashMap<String,Integer> map = new HashMap<String,Integer>();//split text to words
        //create HashMap for our words:
        // string - word,integer - number of the repetitions of this word in the current text
        Add(map,words);
    }
    private static String ReadingFromFile(String text){
        try(BufferedReader br = new BufferedReader(new FileReader("D:\\Hello\\Call.txt"))){
            String sCurrentLine;
            int i = 0;
            while ((sCurrentLine = br.readLine()) != null){
                text+=sCurrentLine;
                i++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return text;
    }

    private static void Add(HashMap<String, Integer> map, String[] words) throws SQLException,ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASENAME, USERNAME, PASSWORD);
        Statement statement = conn.createStatement();
        for (String element : words) {//put words on HashMap
            if (map.containsKey(element))
                map.put(element, map.get(element) + 1);//fixing of repetitions
            else
                map.put(element, 1);
        }
        for(Entry<String,Integer> element : map.entrySet()){//put key and values to SQL request
            String command = "INSERT INTO "+ TABLENAME + " Value(" + 0 + ", '" + element.getKey() + "' ,"+ element.getValue() + ");";
            System.out.println(command);
            statement.execute(command);
        }
        conn.close();
    }
}



