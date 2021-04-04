import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int averageWaitingPeriod;
    private boolean th=true;
    public Server(int maximT){
        tasks = new ArrayBlockingQueue<Task>(maximT);
        waitingPeriod =new AtomicInteger(0);
    }
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void addTask(Task t){
        tasks.add(t);
        waitingPeriod.addAndGet(t.getProcessingPeriod());
        t.setWaitingInLineTime(waitingPeriod.intValue());
        t.setFinishTime();
        if(!th) {
            th=true;
            synchronized (this) {
                this.notify();
            }
        }
    }
    public void run() {
        while (th) {
            if (!tasks.isEmpty()) {
                try {
                    Task t = tasks.peek();
                    while (t.getProcessingPeriod() > 0) {
                        Thread.sleep(1000);
                        t.setProcessingPeriod(t.getProcessingPeriod() - 1);
                        waitingPeriod.addAndGet(-1);
                    }
                    setAverageWaitingPeriod(t.getWaitingInLineTime());
                    tasks.remove();
                    if(tasks.isEmpty()) {
                        suspendTh();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        else if(tasks.isEmpty()){
            suspendTh();
            }
        }
    }

    public void suspendTh(){
            th=false;
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public void setTh(boolean th) {
        this.th = th;
    }

    public int getAverageWaitingPeriod() {
        return averageWaitingPeriod;
    }

    public void setAverageWaitingPeriod(int averageWaitingPeriod) {
        this.averageWaitingPeriod += averageWaitingPeriod;
    }

    public String toString() {
        String st=new String();
        if(tasks.isEmpty()){
            st="closed";
        }
        else {
            for (Task t : tasks) {
                st = st + ";" + t.toString();
            }
            st = st.replaceFirst(";", "");
        }
        return st;
    }
}