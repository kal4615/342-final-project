import java.util.LinkedList;

public class PitStop {
    private int MAX_CARS = 3;
    private int location;
    private int current_cars;
    private LinkedList<Car> pit_queue = new LinkedList<Car>();

    public PitStop(int loc){
        this.location = loc;
        this.current_cars = 0;
    }

    public void enter(Car c){
        
    }

    public void leave(Car c){

    }
}
