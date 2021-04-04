package start;

import com.itextpdf.text.DocumentException;
import presentation.FileParse;
import java.io.FileNotFoundException;
/**
 * Creeaza un obiect nou de tipul fileParser si ii da ca parametru un string care reprezinta numele fisierului din care trebuie citite comenzile, string care este parametru al metodei main.
 */
public class MainClass {
    public static void main(String[] args) {
       try {
           try {
               FileParse f=new FileParse(args[0]);
           } catch (DocumentException e) {
               e.printStackTrace();
           }
       } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
