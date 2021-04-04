package main;
import PresentationLayer.MainGraphicalUserInterface;
public class MainClass {
    private static String fileName;
    public static void main(String[] args){
        fileName=args[0];
        MainGraphicalUserInterface view=new MainGraphicalUserInterface();
    }
    public static String getFileName(){
        return fileName;
    }
}
