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

    /**
     * has a car enter the pit stop
     * 
     * @param c the car
     * @param time the number of iterations the car will stay
     * @throws InterruptedException 
     */
    public void enter(Car c) throws InterruptedException{
        if (check_line() == -1){
            this.current_cars += 1;
        }
        else{
            this.pit_queue.add(c);
            synchronized(c){
                c.wait();
            }
            this.current_cars += 1;
        }
    }

    /**
     * has a car leave the pit stop
     * 
     * @param c the car
     */
    public void leave(Car c){
        if(check_line() <= 0){
            current_cars -= 1;
        }
        else{
            Car c2 = this.pit_queue.pop();
            c2.notify();
            current_cars -= 1;
        }

    }

    /**
     * @return the pit stop location
     */
    public int get_location(){
        return this.location;
    }

    /**
     * check the number of cars in line for the pit stop
     * 
     * @return the number of cars in line
     */
    public int check_line(){
        if (this.current_cars < MAX_CARS){
            return -1;
        }
        return pit_queue.size();
    }
}
