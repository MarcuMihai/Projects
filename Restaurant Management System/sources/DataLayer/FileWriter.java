package DataLayer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileWriter {
    public void writeInTxt(String s,int id){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Bill for order with id "+id+".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.println(s);
        writer.close();
    }
}
