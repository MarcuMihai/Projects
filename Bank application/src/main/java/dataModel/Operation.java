package dataModel;

public class Operation {
    private int id;
    private String name,inputData,type, username;

    public Operation(int id, String type, String name, String username, String input){
        this.id=id;
        this.type=type;
        this.name=name;
        this.username=username;
        this.inputData=input;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
