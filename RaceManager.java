public class RaceManager{
    private Car[] racers;
    private PitStop[] pitstops;
    private int length; 

    public RaceManager(int cars, int pits, int length){
        this.racers = new Car[cars];
        for (int x = 0; x < cars; ++x){
            /**create and add racers to racers array */
        }
        this.pitstops = new PitStop[pits];
        for (int y = 0; y < pits; ++y){
            int pit_location = (y+1)*length/(pits + 1);
            this.pitstops[y] = new PitStop(pit_location);    
        }
        this.length = length;
    }

    public double pit_formula(){
        return 0.0;
    }

    public void drive_car(float distance){

    }
}