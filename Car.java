public class Car extends Thread{
    private int ID;
    private int max_speed;
    private int current_speed;
    private double speed_decay;
    private int pit_time;
    private int position;

    public Car (int ID, int speed, double decay, int pit){
        this.ID = ID;
        this.max_speed = speed;
        this.current_speed = speed;
        this.speed_decay = decay;
        this.pit_time = pit;
        this.position = 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // tell race manager to move car distance of current speed
        // decay speed current_speed = (int)(current_speed * speed_decay);
    }
    
    // default drive function that will decay the speed of the car as it drives
    public void Drive (){
        current_speed = (int)(current_speed * speed_decay);

    }

    // formula to decide if car uses next stop or not
    public static void Next_Pit_Stop(){

    }

}
