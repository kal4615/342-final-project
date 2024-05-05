import java.util.Random;

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
        boolean finished = false;
        while(true){
                PitStop next_pit = next_pit_stop();
                if (next_pit == null){
                    synchronized(manager){
                        finished = drive();
                    }
                }
                else{
                
                    
                    this.position = next_pit.get_location(); //has the car drive to the pit stop before entering
                    while(true){
                        synchronized(this){
                            if(next_pit.enter(this) == true){
                                break;
                            }
                            else{
                                try {
                                    this.wait();
                                } 
                                catch (InterruptedException e) {}
                            }
                        }
                    }
                    synchronized(manager){
                        for(int i = 0; i < this.pit_time; ++i){
                            try {
                                System.out.printf("Car #%d is in Pit Stop(Location: %d m) | ", this.ID, this.position);
                                this.manager.wait();
                            } catch (InterruptedException e) {}
                            next_pit.leave(this);
                        }
                        System.out.printf("Car #%d leaves Pit Stop(Location: %d m) | ", this.ID, this.position);
                    }
                    
                }
            synchronized(manager){
                try {
                    this.manager.wait();
                } catch (InterruptedException e) {}
            }

            if (finished == true){
                break;
            }
        }
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

        System.out.printf("Car #%d (Speed: %d, Location: %d m) | ", this.ID, this.current_speed, this.position);
        current_speed = (int)(current_speed * speed_decay);

        if(current_speed < max_speed * .1){ //the minimum speed a car can reach is 1/10th of its maximum speed
            current_speed = (int)(max_speed * .1); 
        }
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

        double speed_perc = this.current_speed/this.max_speed;

        //run pitstop formula, and return null if the decision is made not to stop
        if (speed_perc >= 0.9){ // if current speed is 90% or higher of max speed dont pit
           return null;
        }
        else if (speed_perc < 0.9 && speed_perc > 0.2){ // if current speed is between 20% and 90%
            Random rand = new Random();
            int rand_int = rand.nextInt(100);
            int pit_chance = (int)(1 - speed_perc) * 100;  // percent chance of making a pit stop depending on how much fuel is left
            if (rand_int <= pit_chance){  
                return next_pit;
            }
            else{
                return null;
            }
        }

        return next_pit;
    }

    public void refuel() {
        this.current_speed = this.max_speed;
    }

    public int get_location() {
        return this.position;
    }

    public int get_id() {
        return this.ID;
    }

}
