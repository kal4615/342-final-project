public class Car extends Thread{
    private int ID;
    private int max_speed;
    private int current_speed;
    private double speed_decay;
    private int pit_time;
    private int position;
    private RaceManager manager;

    public Car (int ID, int speed, double decay, int pit, RaceManager racemanager){
        this.ID = ID;
        this.max_speed = speed;
        this.current_speed = speed;
        this.speed_decay = decay;
        this.pit_time = pit;
        this.position = 0;
        this.manager = racemanager;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // tell race manager to move car distance of current speed
        // decay speed current_speed = (int)(current_speed * speed_decay);
    }
    
    /**
     * default drive function that will decay the speed of the car as it drives
     * 
     * @return if the car finishes the race
     */
    public boolean drive(){
        position += current_speed;
        if (manager.check_finish(position)){
            return true;
        };
        current_speed = (int)(current_speed * speed_decay);
        return false;
    }

    /**
     * runs a formula to decide if the car makes a pitstop or not
     * 
     * @return returns the PitStop if the formula decides to make a pit stop
     */
    public PitStop next_pit_stop(){
        PitStop next_pit = manager.next_pit(this.position);
        if (next_pit == null){
            return null; //return null if there is no next pit stop
        }

        int distance_to_pit = next_pit.get_location() - this.position;
        if (distance_to_pit > this.current_speed){
            return null; //return null if the car cannot make it to the next pit stop this iteration
        }

        return next_pit;
    }

}
