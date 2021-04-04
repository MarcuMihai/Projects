import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoredData {
    private Date startTime,endTime;
    String activity;
    public MonitoredData(Date start,Date end,String activity){
        startTime=start;
        endTime=end;
        this.activity=activity;
    }
    @Override
    public String toString() {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String st=format.format(startTime);
        String et=format.format(endTime);
        return "MonitoredData{" +
                "startTime='" + st + '\'' +
                ", endTime='" + et + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
