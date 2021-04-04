import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    public void addTask(List<Server> servers, Task t) {
        int nrcmin=0;
        boolean ok=true;
        for (Server sv : servers) {
            if (ok == true) {
                nrcmin = sv.getWaitingPeriod().intValue();
                ok=false;
            }
            if (sv.getWaitingPeriod().intValue() < nrcmin) {
                nrcmin = sv.getWaitingPeriod().intValue();
            }
        }
        for (Server sv:servers) {
            if(sv.toString()=="closed") {
                sv.addTask(t);
                break;
            }
            if(sv.getWaitingPeriod().intValue()==nrcmin){
                sv.addTask(t);
                break;
            }
        }
    }
}
