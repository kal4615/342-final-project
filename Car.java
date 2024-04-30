public class Car {
    private int ID;
    private int max_speed;
    private int current_speed;
    private float speed_decay;
    private int pit_time;
    private int position;

    public Car(int ID, int speed, float decay, int pit){
        this.ID = ID;
        this.max_speed = speed;
        this.current_speed = speed;
        this.speed_decay = decay;
        this.pit_time = pit;
        this.position = 0;
    }
    
    public static void Drive (){

    }

    public static void Next_Pit_Stop(){

    }

}
