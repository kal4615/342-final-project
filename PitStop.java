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
    public boolean enter(Car c){
        synchronized(this){
            if (check_line() == -1){
                this.current_cars += 1;
                return true;
            }
            else{
                this.pit_queue.add(c);
                return false;
            }
        }
    }

    /**
     * has a car leave the pit stop
     * 
     * @param c the car
     */
    public void leave(Car c){
        synchronized(this){
            c.refuel();
            if(check_line() <= 0){
                current_cars -= 1;
            }
            else{
                Car car = this.pit_queue.pop();
                synchronized(car){
                    car.notify();
                }
                current_cars -= 1;
            }
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
