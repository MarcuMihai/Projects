import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tasks {
    private int nrDistinctDays;
    private ArrayList<MonitoredData> data=new ArrayList<MonitoredData>();
    private List<Date> distinctDays=new ArrayList<Date>();
    private Map<String,Integer> mapTask3=new HashMap<String, Integer>();
    private Map<Integer, Map<String, Integer>> mapTask4=new HashMap<Integer, Map<String, Integer>>();
    private Map<String, Integer> mapTask5=new HashMap<String, Integer>();
    private List<String> listTask6=new ArrayList<String>();
    public void task1(){
        try(Stream<String> stream= Files.lines(Paths.get("Activities.txt"))){
            Date startTime,endTime;
            String activity;
            List<String> list=stream.collect(Collectors.toList());
            for (String s:list) {
                String s1[]=s.split("\t+");
                startTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s1[0]);
                endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s1[1]);
                activity=s1[2];
                MonitoredData d=new MonitoredData(startTime,endTime,activity);
                data.add(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        FileWriter.writeTxt(1,this);
    }
    public void task2(){
        ArrayList<Date> days=new ArrayList<Date>();
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        data.forEach(d-> {
            try {
                days.add(format.parse(format.format(d.getStartTime())));
                days.add(format.parse(format.format(d.getEndTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        distinctDays=days.stream().distinct().collect(Collectors.toList());
        nrDistinctDays=(int)distinctDays.stream().count();
        FileWriter.writeTxt(2,this);
    }
    public void task3(){
        List<String> activities=data.stream().map(MonitoredData::getActivity).distinct().collect(Collectors.toList());
        activities.forEach(act->{
                mapTask3.put(act,0);
        });
        data.forEach(d->{
            activities.stream().filter(act->act.equals(d.getActivity())).forEach(act->{
                int aux=mapTask3.get(act);
                mapTask3.replace(act,aux+1);
            });
        });
        FileWriter.writeTxt(3,this);
    }
    public void task4() {
        List<String> activities=data.stream().map(MonitoredData::getActivity).distinct().collect(Collectors.toList());
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        distinctDays.forEach(day->{
            Map<String, Integer> map = new HashMap<String, Integer>();
            activities.forEach(act->{
                map.put(act,0);
            });
            mapTask4.put(distinctDays.indexOf(day)+1,map);
            data.stream().filter(d->format.format(d.getStartTime()).equals(format.format(day)) || format.format(d.getEndTime()).equals(format.format(day))).forEach(d->{
                activities.stream().filter(act->act.equals(d.getActivity())).forEach(act -> {
                    int aux = map.get(act);
                    map.replace(act, aux + 1);
                    mapTask4.replace(distinctDays.indexOf(day)+1, map);
                });
            });
        });
        FileWriter.writeTxt(4,this);
    }
    public void task5(){
        List<String> activities=data.stream().map(MonitoredData::getActivity).distinct().collect(Collectors.toList());
        activities.forEach(act->{
            mapTask5.put(act,0);
        });
        data.forEach(d->{
            LocalDateTime st = new Timestamp(d.getStartTime().getTime()).toLocalDateTime();
            LocalDateTime et = new Timestamp(d.getEndTime().getTime()).toLocalDateTime();
            Duration duration=Duration.between(st,et);
            activities.stream().filter(act->act.equals(d.getActivity())).forEach(act->{
                int aux=mapTask5.get(act);
                mapTask5.replace(act,aux+(int)(duration.toSeconds()));
            });
        });
        FileWriter.writeTxt(5,this);
    }
    public void task6(){
        List<String> activities=data.stream().map(MonitoredData::getActivity).distinct().collect(Collectors.toList());
        Map<String, Integer> map = new HashMap<String, Integer>();
        activities.forEach(act->{
            map.put(act,0);
        });
        data.forEach(d->{
            LocalDateTime st = new Timestamp(d.getStartTime().getTime()).toLocalDateTime();
            LocalDateTime et = new Timestamp(d.getEndTime().getTime()).toLocalDateTime();
            Duration duration=Duration.between(st,et);
            activities.stream().filter(act->act.equals(d.getActivity()) && (duration.toSeconds() <= 300)).forEach(act->{
                int aux = map.get(act);
                map.put(act, aux + 1);
            });
        });
        activities.forEach(act->{
            double rez=map.get(act)/mapTask3.get(act);
            if(rez*100>=90){
                listTask6.add(act);
            }
        });
        FileWriter.writeTxt(6,this);
    }
    public int getNrDistinctDays() {
        return nrDistinctDays;
    }
    public ArrayList<MonitoredData> getData() {
        return data;
    }
    public Map<String, Integer> getMapTask3() {
        return mapTask3;
    }
    public Map<Integer, Map<String, Integer>> getMapTask4() {
        return mapTask4;
    }
    public Map<String, Integer> getMapTask5() {
        return mapTask5;
    }
    public List<String> getListTask6() {
        return listTask6;
    }
}
