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
        // FUNCTION NOT USED YET}
        boolean finished = false;
        while(true){
            synchronized(manager){
        
                PitStop next_pit = next_pit_stop();
                if (next_pit == null){
                    finished = drive();
                }
                else{
                    synchronized(next_pit){
                        try {
                            this.position = next_pit.get_location(); //has the car drive to the pit stop before entering

                            System.out.printf("Car #%d Enters Pit Stop(Location: %d m) | ", this.ID, this.position);
                            next_pit.enter(this);
                        } catch (InterruptedException e) {}

                        for(int i = 0; i < this.pit_time; ++i){
                            try {
                                System.out.printf("Car #%d is in Pit Stop(Location: %d m) | ", this.ID, this.position);
                                this.manager.wait();
                            } catch (InterruptedException e) {}
                        }
                    }
                }
            
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

        //run pitstop formula, and return null if the decision is made not to stop
        if (10 > new Random().nextInt(11)){ //REPLACE WITH FORMULA
            return null;
        }

        return next_pit;
    }

    public int get_location() {
        return this.position;
    }

    public int get_id() {
        return this.ID;
    }

}
