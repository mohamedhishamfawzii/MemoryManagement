package sample;

public class Byte {

    private String processName  ;

    public Byte( String b) {

        this.processName = b;

    }
    public Byte() {
    this.processName = "empty";

    }



    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
