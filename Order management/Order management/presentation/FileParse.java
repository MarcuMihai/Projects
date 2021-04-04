package presentation;

import com.itextpdf.text.DocumentException;
import dataAccessLayer.ClientDao;
import dataAccessLayer.OrderDao;
import dataAccessLayer.OrderItemDao;
import dataAccessLayer.ProductDao;
import model.Client;
import model.Order;
import model.OrderItem;
import model.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * In aceasta clasa sunt citite comenzile din fisier ce sunt utilizate pentru manipularea bazei de date.
 */
public class FileParse {
    String s;
    int idc=1,idp=1,idoi=1,ido=1;
    /**
     * @param s1
     * @throws FileNotFoundException
     * @throws DocumentException
     * Constructorul clasei, in acesta se initializeaza un sir de caractere, acesta este utilizat pentru citirea din fisier, s1 fiind numele fisierului care este dat ca parametru al metodei main din clasa MainClass.
     */
    public FileParse(String s1) throws FileNotFoundException, DocumentException {
        s=s1;
        readFromFile();
    }

    /**
     * In aceasta functie am deschis fisierul si cu ajutorul expresiilor regex am impartit continutul acestuia. Tot aici se apeleaza si functiile din alte clase care realizeaza comanda respectiva. Citirea din fisier am realizat-o prin intermediul unui scanner.
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void readFromFile() throws FileNotFoundException, DocumentException {
        File f = new File(s);
        Scanner scan = new Scanner(f);
        String table,command,s1;
        String[] s2,s3,s4;
        while (scan.hasNextLine()) {
            s1 = scan.nextLine();
            s2 = s1.split(":");
            s3 = s2[0].split(" ");
            command = s3[0];
            if (!command.equals("Order")) {
                table = s3[1];
                if (!command.equals("Report")) {
                    s4 = s2[1].split(",");
                    for (int i = 0; i < s4.length; i++)
                        s4[i] = s4[i].replaceFirst(" ", "");
                    if (table.equals("client") || table.equals("product")) {
                        if (table.equals("client")) {
                            createClient(command,idc,s4[0],s4[1]);
                        } else if (table.equals("product")) {
                            verifyProduct(s4,command);
                        }
                    }
                }
                else {
                    if(table.equals("client")){
                        reportClient();
                    }
                    else if(table.equals("product")){
                        reportProduct();
                    }
                    if(table.equals("order")){
                        Presentation pres=new Presentation();
                        pres.docPdfOrder();
                    }
                }
            }
            else if (command.equals("Order")) {
                createOrder(s2[1]);
            }
        }
    }

    public void verifyProduct(String[] s4,String command){
        if(command.equals("Delete")){
            Product p = ProductDao.findByName(s4[0]);
            ProductDao.delete(p);
        }
        else createProduct(command,idp, s4[0], Integer.parseInt(s4[1]), Double.parseDouble(s4[2]));
    }

    public void createClient(String command,int id,String name,String city){
        Client c = new Client(idc, name, city);
        idc++;
        checkc(c, command);
    }

    public void createProduct(String command,int id,String name,int cant,double price){
        Product p = new Product(id, name, cant,price);
        idp++;
        checkp(p, command);
    }

    public void checkc(Client c, String st) {
        if (st.equals("Insert"))
            ClientDao.insert(c);
        else if (st.equals("Delete")) {
            Client c1=ClientDao.findByName(c.getName());
            ClientDao.delete(c1);
        }
    }

    public void reportClient(){
        Presentation pres=new Presentation();
        try {
            pres.docPdfClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void checkp(Product p,String st){
        if (st.equals("Insert")) {
            if(ProductDao.findByName(p.getName())!=null){
                Product p1=ProductDao.findByName(p.getName());
                p.setIdproduct(p1.getIdproduct());
                p.setQuantity(p.getQuantity()+p1.getQuantity());
                ProductDao.delete(p1);
            }
            ProductDao.insert(p);
        }
        else if (st.equals("Report")) {

        }
    }

    public void reportProduct(){
        Presentation pres=new Presentation();
        try {
            pres.docPdfProduct();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void createOrder(String st) throws FileNotFoundException, DocumentException {
        String[] s1;
        s1 = st.split(",");
        for (int i = 0; i < s1.length; i++)
            s1[i] = s1[i].replaceFirst(" ", "");
        OrderItem i = new OrderItem(idoi, searchByNameClient(s1[0]), searchByNameProduct(s1[1]), Integer.parseInt(s1[2]));
        Product p=ProductDao.findById(i.getIdproduct());
        Presentation pres=new Presentation();
        if(p.getQuantity()<i.getQuantity())
            pres.docPdfBill(0,i);
        else{
            idoi++;
            OrderItemDao.insert(i);
            pres.docPdfBill(1,i);
            if(OrderDao.findByIdClient(i.getIdc())==null){
                Order ord=new Order(ido,i.getIdc(),p.getPrice()*i.getQuantity());
                ido++;
                OrderDao.insert(ord);
            }
            else {
                Order ord = OrderDao.findByIdClient(i.getIdc());
                Order ord1=ord;
                ord1.setFinalprice(ord.getFinalprice()+p.getPrice()*i.getQuantity());
                OrderDao.delete(ord);
                OrderDao.insert(ord1);
            }
            Product p1=new Product(p.getIdproduct(),p.getName(),p.getQuantity()-i.getQuantity(),p.getPrice());
            ProductDao.delete(p);
            ProductDao.insert(p1);
        }
    }

    public int searchByNameClient(String name){
        Client c=ClientDao.findByName(name);
        return c.getIdc();
    }
    public int searchByNameProduct(String name){
        Product p=ProductDao.findByName(name);
        return p.getIdproduct();
    }
}
