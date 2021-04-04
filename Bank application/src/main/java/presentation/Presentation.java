package presentation;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Stream;

public class Presentation {
    private static int nrReport=1;
    private PdfPTable table;
    public Presentation(){
        table= new PdfPTable(5);
        addTableHeaderClient(table);
    }
    public void docPdf() throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String s="Report"+nrReport+".pdf";
        nrReport++;
        PdfWriter.getInstance(document, new FileOutputStream(s));
        document.open();
        document.add(table);
        document.close();
    }

    private void addTableHeaderClient(PdfPTable table) {
        Stream.of("ID", "Type", "Name", "Username", "Input data(Client/User/Account ID)")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public void addRowsClient(int id,String s2,String s3,String s4, String s5) {
        String s1=id+"";
        table.addCell(s1);
        table.addCell(s2);
        table.addCell(s3);
        table.addCell(s4);
        table.addCell(s5);
    }
}
