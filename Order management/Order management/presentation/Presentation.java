package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfWriter;
import dataAccessLayer.ClientDao;
import dataAccessLayer.OrderDao;
import dataAccessLayer.ProductDao;
import model.OrderItem;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Stream;

/**
 * In aceasta clasa am creeat PDF-urile specifice fiecarui client, produs si fiecarei comenzi si facturi.
 */
public class Presentation {
    private static int nrReportClients=1;
    private static int nrReportProducts=1;
    private static int nrReportBills=1;
    private static int nrReportOrders=1;

    /**
     * Creeaza un document ce contine un tabel PDF in care salveaza datele despre fiecare client inserat.
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public void docPdfClient() throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String s="Report clients "+nrReportClients+".pdf";
        nrReportClients++;
        PdfWriter.getInstance(document, new FileOutputStream(s));
        document.open();
        PdfPTable table = new PdfPTable(3);
        addTableHeaderClient(table);
        ClientDao.findReport(table);
        document.add(table);
        document.close();
    }

    /**
     * Creeaza header-ul pentru tabelul cu clienti.
     * @param table
     */
    private void addTableHeaderClient(PdfPTable table) {
        Stream.of("idclient", "name", "city")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Realizeaza inserarea in tabel a fiecarui client.
     * @param table
     * @param id
     * @param s2
     * @param s3
     */
    public static void addRowsClient(PdfPTable table, int id,String s2,String s3) {
        String s1=id+"";
        table.addCell(s1);
        table.addCell(s2);
        table.addCell(s3);
    }

    /**
     * Creeaza un document ce contine un tabel PDF in care salveaza datele despre fiecare produs inserat.
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public void docPdfProduct() throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String s="Report products "+nrReportProducts+".pdf";
        nrReportProducts++;
        PdfWriter.getInstance(document, new FileOutputStream(s));
        document.open();
        PdfPTable table = new PdfPTable(4);
        addTableHeaderProduct(table);
        ProductDao.findReport(table);
        document.add(table);
        document.close();
    }

    /**
     * Creeaza header-ul pentru tabelul cu produse.
     * @param table
     */
    private void addTableHeaderProduct(PdfPTable table) {
        Stream.of("idproduct", "name", "quantity","price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Realizeaza inserarea in tabel a fiecarui produs.
     * @param table
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     */
    public static void addRowsProduct(PdfPTable table, String s1,String s2,String s3,String s4) {
        table.addCell(s1);
        table.addCell(s2);
        table.addCell(s3);
        table.addCell(s4);
    }

    /**
     * Creeaza un document ce contine un tabel PDF in care salveaza datele despre o factura creeata. Fiecare factura are tabelul ei.
     * @param ok
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public void docPdfBill(int ok, OrderItem oi) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String s="Report bill number "+nrReportBills+".pdf";
        nrReportBills++;
        PdfWriter.getInstance(document, new FileOutputStream(s));
        document.open();
        if(ok==1){
        PdfPTable table = new PdfPTable(4);
        addTableHeaderBill(table);
        addRowsBill(table,oi.getIdorderitem()+"",""+oi.getIdc(),""+oi.getIdproduct(),""+oi.getQuantity());
        document.add(table);
        }
        else if(ok==0){
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Stoc insuficient!", font);
            document.add(chunk);
        }
        document.close();
    }

    /**
     * Creeaza header-ul pentru tabelul cu facturi.
     * @param table
     */
    private void addTableHeaderBill(PdfPTable table) {
        Stream.of("idbill", "idclient", "idproduct","quantity")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Realizeaza inserarea in tabel a facturii.
     * @param table
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     */
    public static void addRowsBill(PdfPTable table, String s1,String s2,String s3,String s4) {
        table.addCell(s1);
        table.addCell(s2);
        table.addCell(s3);
        table.addCell(s4);
    }

    /**
     * Creeaza un document ce contine un tabel PDF in care salveaza datele despre fiecare comanda finala confirmata pentru fiecare client.
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public void docPdfOrder() throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String s="Report order number "+nrReportOrders+".pdf";
        nrReportOrders++;
        PdfWriter.getInstance(document, new FileOutputStream(s));
        document.open();
        PdfPTable table = new PdfPTable(3);
        addTableHeaderOrder(table);
        OrderDao.findReport(table);
        document.add(table);
        document.close();
    }

    /**
     * Creeaza header-ul pentru tabelul cu comenzi.
     * @param table
     */
    private void addTableHeaderOrder(PdfPTable table) {
        Stream.of("idorder", "idclient", "final price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Realizeaza inserarea in tabel a fiecarei comenzi.
     * @param table
     * @param s1
     * @param s2
     * @param s3
     */
    public static void addRowsOrder(PdfPTable table, String s1,String s2,String s3) {
        table.addCell(s1);
        table.addCell(s2);
        table.addCell(s3);
    }
}
