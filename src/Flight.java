/*
Student: Alexey Vartanov
ID: 321641086
Maman 15 - Question 2 - Airport simulator
 */
import java.time.LocalTime;
import java.util.Random;

/**
 * Class of Flight as a thread
 */
public class Flight extends Thread{

    private final int flightNumber;
    private final Airport source;
    private final Airport destination;

    public Flight(int flyNumber, Airport source, Airport destination){
        this.flightNumber = flyNumber;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void run() {

        /* ask for departure and get way to depart from */
        System.out.printf("%s - Flight: %d ask for departure: %s --> %s\n",
                LocalTime.now().toString(), flightNumber, source.getAirportName(), destination.getAirportName());
        int departWay = source.depart(flightNumber);

        /* simulate landing with random time (up to 5 sec) */
        SleepRandomTime(5);

        /* free departure way */
        source.freeRunway(flightNumber,departWay);

        /* wait random time (up to 10 seconds) to simulate fly */
        SleepRandomTime(15);

        /* ask for land and get way to land on */
        System.out.printf("%s - Flight: %d ask for landing in %s\n",
                LocalTime.now().toString(), flightNumber, destination.getAirportName());
        int landWay = destination.land(flightNumber);

        /* simulate landing with random time (up to 5 sec) */
        SleepRandomTime(5);

        /* free landing way */
        destination.freeRunway(flightNumber, landWay);
    }

    /**
     * get random time to sleep to simulate flight
     * @param bound: get random time up to bound value
     */
    private void SleepRandomTime(int bound){
        Random rand = new Random();
        try {
            int timeToSleep = rand.nextInt(bound)*1000;
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}