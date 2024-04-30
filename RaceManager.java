import java.util.Random;

public class RaceManager{
    private static final int MAX_CAR_SPEED = 250;

    private Car[] racers;
    private PitStop[] pitstops;
    private int length; 

    public RaceManager(int cars, int pits, int length){
        this.racers = new Car[cars];
        Random rand = new Random();
        System.out.println("========== Cars ==========");
        for (int x = 0; x < cars; ++x){
            //decides random Max Speed for car
            int speed = (int)(MAX_CAR_SPEED * rand.nextDouble(.85, 1.15));
            //decides random Speed Decay for car
            double decay = rand.nextDouble(.90, .99);
            //decides random number of iterations a car stays in a pit for
            int pit_time = rand.nextInt(1,4);
            racers[x] = new Car(x, speed, decay, pit_time, this);

            //print details about the created car to the terminal
            System.out.printf("Car %d | Max Speed: %d | Decay Rate: %.4f | Pit Time: %d\n", x, speed, decay, pit_time);
        }

        this.pitstops = new PitStop[pits];
        System.out.println("\n========== Pit Stops ==========");
        for (int y = 0; y < pits; ++y){
            int pit_location = (y+1)*length/(pits + 1);
            this.pitstops[y] = new PitStop(pit_location);    
            //print a the pitstops as they are created
            if(y < pits - 1){
                System.out.printf("PitStop #%d Location: %d | ", y+1, pit_location);
            }
            else{
                System.out.printf("PitStop #%d Location: %d \n", y+1, pit_location);
            }
        }
        
        this.length = length;
    }

    /**
     * checks where the next pit stop is from a point in the race. used by the next_pit_stop function from Car
     * 
     * @param distance the distance into the race to check beyond for the next pit stop
     * @return the next pit stop, null if there is no pit stop before the end of the race
     */
    public PitStop next_pit(int location){
        PitStop next_pit = null;
        int shortest_distance = Integer.MAX_VALUE;
        for (PitStop p : this.pitstops){
            int distance = p.get_location() - location;
            if(distance > 0 && distance < shortest_distance){
                shortest_distance = distance;
                next_pit = p;
            }
        }
        return next_pit;
    }

    /** 
     * updates the cars distance when it makes the drive selection and checks if it has completed the race
     * 
     * @param position the position of the car after it moves
     * @return whether the car has finished the race or not
     */
    public boolean check_finish(int position){
        return position >= this.length;
    }

    /**
     * runs the race. iterates through each car looking for an action
     * 
     * @return an array of strings in the form "Car #(Number) | Iteration Finished"
     */
    public String[] run_race(){
        String[] standings = new String[racers.length];
        int placement = 0; //the index for how many racers have completed the race
        
        
        return standings;
    }
}