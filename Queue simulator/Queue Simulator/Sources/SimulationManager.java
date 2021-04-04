import java.io.*;
import java.util.*;

public class SimulationManager implements Runnable{
    public int maxProcessingTime;
    public int minProcessingTime;
    public int numberOfServers;
    public int numberOfClients;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int simulationTime;
    public float averageWaitingTime;
    public String a0;
    public String a1;

    private Scheduler scheduler;
    private List<Task> generatedTask;
    public SimulationManager(String a0, String a1) {
        this.a0=a0;
        this.a1=a1;
        generatedTask=new ArrayList<Task>();
        readFromFile(a0);
        scheduler=new Scheduler(numberOfServers,numberOfClients);
        generateNRandomTasks();
    }
    public void readFromFile(String fis){
        try {
            File f1=new File(fis);
            Scanner scan = new Scanner(f1);
            while(scan.hasNextLine()) {
                numberOfClients=Integer.parseInt(scan.nextLine());
                numberOfServers=Integer.parseInt(scan.nextLine());
                simulationTime=Integer.parseInt(scan.nextLine());
                String s1=scan.nextLine();
                String[] s2=s1.split(",");
                minArrivalTime=Integer.parseInt(s2[0]);
                maxArrivalTime=Integer.parseInt(s2[1]);
                s1=scan.nextLine();
                s2=s1.split(",");
                minProcessingTime=Integer.parseInt(s2[0]);
                maxProcessingTime=Integer.parseInt(s2[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void generateNRandomTasks(){
        Random random=new Random();
        for(int i=1;i<=numberOfClients;i++){
            int pT=random.nextInt(maxProcessingTime-minProcessingTime)+minProcessingTime;
            int aT=random.nextInt(maxArrivalTime-minArrivalTime)+minArrivalTime;
            Task c=new Task(i,aT,pT);
            generatedTask.add(c);
        }
        Collections.sort(generatedTask);
    }
    public void run(){
        int currentTime=0;
        createFile(a1);
        while(currentTime<simulationTime && (!generatedTask.isEmpty() || scheduler.getEmpty()=="not empty")){
            String s="Time ";
            s=s+currentTime+"\n";
            s=s+"Waiting clients:";
            Iterator<Task> i=generatedTask.iterator();
           while(i.hasNext()) {
               Task t=i.next();
               if(t.getArrivalTime()>currentTime){
                    s=s+" "+t.toString();
               }
               if(t.getArrivalTime()==currentTime) {
                    scheduler.dispatchTask(t);
                    i.remove();
                }
            }
           s=s+"\n"+scheduler.toString();
           outputInFile(a1,s);
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(currentTime==simulationTime || (generatedTask.isEmpty() && scheduler.getEmpty()=="empty")){
                averageWaitingTime=(float)scheduler.getAverageWaitingPeriod()/numberOfClients;
                String st1="Average waiting time is: "+averageWaitingTime;
                outputInFile(a1,st1);
            }
        }
        closeTh();
    }
    public void closeTh(){
        for (Server sv:scheduler.getServers()) {
            sv.setTh(false);
            synchronized (sv){
                sv.notify();
            }
        }
    }
    public void createFile(String s) {
        File f=new File(s);
        try {
            if(f.createNewFile()){}
            else {
                PrintWriter w= new PrintWriter(f);
                w.print("");
                w.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void outputInFile(String s,String st) {
        File f=new File(s);
        PrintWriter w=null;
        try {
            w = new PrintWriter(new FileOutputStream(s,true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        w.write(st+'\n');
        w.close();
    }
    public static void main(String[] args){
        SimulationManager s=new SimulationManager(args[0],args[1]);
        Thread t=new Thread(s);
        t.start();
    }
}
