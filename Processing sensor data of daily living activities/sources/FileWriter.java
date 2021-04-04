import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

public class FileWriter {
    public static void writeTxt(int nrTask,Tasks tasks){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Task_"+nrTask+".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(nrTask==1){
            for (MonitoredData d:tasks.getData()) {
                writer.println(d.toString()+"\n");
            }
        }
        else if(nrTask==2)
            writer.println("Numarul de zile distincte este: "+tasks.getNrDistinctDays());
        else if(nrTask==3)
            for (Map.Entry<String, Integer> y : tasks.getMapTask3().entrySet()){
                writer.println(y.getKey()+" "+y.getValue());
            }
        else if(nrTask==4)
            for (Map.Entry<Integer, Map<String, Integer>> y : tasks.getMapTask4().entrySet()){
                for (Map.Entry<String, Integer> z : y.getValue().entrySet()) {
                    writer.println("Ziua cu nr: "+y.getKey()+" "+z.getKey()+" "+z.getValue());
                }
            }
        else if(nrTask==5)
            for (Map.Entry<String,Integer> x:tasks.getMapTask5().entrySet()) {
                writer.println(x.getKey()+" "+x.getValue()/60);
            }
        else if(nrTask==6)
            for (String s:tasks.getListTask6()) {
                writer.println(s);
            }
        writer.close();
    }
}
