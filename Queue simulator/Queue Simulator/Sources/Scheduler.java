import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNrServers;
    private int maxTaskPerServer;
    private String empty="not empty";
    private Strategy strategy=new ConcreteStrategyTime();
    private int averageWaitingPeriod;
    public Scheduler(int maxNrS, int maxTaskPerS){
        maxNrServers=maxNrS;
        maxTaskPerServer=maxTaskPerS;
        servers=new ArrayList<Server>(maxNrServers);
        for(int i=0;i<maxNrServers;i++) {
            Server sv = new Server(getMaxTaskPerServer());
            servers.add(sv);
            Thread th = new Thread(sv);
            th.start();
        }
    }

    public int getMaxTaskPerServer() {
        return maxTaskPerServer;
    }

    public void dispatchTask(Task t){
        strategy.addTask(servers,t);
    }

    public int getAverageWaitingPeriod() {
        for (Server sv:servers) {
            averageWaitingPeriod += sv.getAverageWaitingPeriod();
        }
        return averageWaitingPeriod;
    }

    public String getEmpty() {
        return empty;
    }

    public List<Server> getServers(){
        return servers;
    }
    public String toString(){
        String st=new String();
        int i=0,ok=0;
        for (Server sv:servers) {
            if(sv.toString()!="closed"){
                ok=1;
            }
            i++;
            st=st+"\n"+"Queue "+i+":"+" "+sv.toString();
        }
        if(ok==0)
        {
            empty="empty";
        }
        else if(ok==1){
            empty="not empty";
        }
        st=st.replaceFirst("\n","");
        return st;
    }
}
