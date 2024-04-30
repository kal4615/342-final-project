public class Race{
    private static final int NUMBER_RACERS = 5;
    private static final int RACE_LENGTH = 4000;
    private static final int NUMBER_PITSTOPS = 3;

    public static void main(String[] args) {
        RaceManager manager = new RaceManager(NUMBER_RACERS, NUMBER_PITSTOPS, RACE_LENGTH);
        String[] results = manager.run_race();
        for (String result : results){
            System.out.println(result);
        }
    }
}