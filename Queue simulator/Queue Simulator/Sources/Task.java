import java.lang.*;
public class Task implements Comparable<Task>{
    private int id;
    private int arrivalTime;
    private int processingPeriod;
    private int waitingInLineTime;
    private int finishTime;
    public Task(int i,int aT,int pT){
            id=i;
            arrivalTime=aT;
            processingPeriod=pT;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setProcessingPeriod(int processingPeriod) {
        this.processingPeriod = processingPeriod;
    }

    public int getProcessingPeriod() {
        return processingPeriod;
    }

    public int getWaitingInLineTime() {
        return waitingInLineTime;
    }

    public void setWaitingInLineTime(int waitingTime) {
        this.waitingInLineTime = waitingTime;
    }

    public void setFinishTime() {
        this.finishTime = arrivalTime+waitingInLineTime;
    }

    public int compareTo(Task t) {
        return arrivalTime - t.arrivalTime;
    }
    public String toString(){
        return "("+id+","+arrivalTime+","+processingPeriod+")";
    }
}
