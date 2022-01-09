/*
Student: Alexey Vartanov
ID: 321641086
Maman 15 - Question 2 - Airport simulator
 */

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Class of Airport object with number of ways
 */
public class Airport {

    /*Status of the way in the airport*/
    enum wayStatus {FREE, BUSY}

    private final String AirportName;
    private final int NumberOfRunways;
    private final Map<Integer, wayStatus> Runways;

    public Airport(String name, int numOfRunways) {
        AirportName = name;
        NumberOfRunways = numOfRunways;
        Runways = new HashMap<>();
        /* set all runways to free */
        for (int i = 0; i < numOfRunways; i++) {
            Runways.put(i, wayStatus.FREE);
        }
    }

    public String getAirportName() {
        return AirportName;
    }

    /**
     * Synchronized method to get the number of first free way to departure/land
     * Flight will wait if all the ways are busy, till on of the ways will become free again
     *
     * @return the number of the free way
     */
    private synchronized int getFreeWayNumber() {
        do {
            try {
                for (int i = 0; i <= NumberOfRunways; i++) {
                    if (Runways.get(i) == wayStatus.FREE) {
                        Runways.replace(i, wayStatus.BUSY);
                        notifyAll();
                        return i;
                    }
                }
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        while (true);
    }

    public int depart(int flyNumber) {
        int departureWay = getFreeWayNumber();
        System.out.printf("%s - Flight: %d will depart from way: %d, from: %s\n",
                LocalTime.now(), flyNumber, departureWay, AirportName);
        return departureWay;
    }

    public int land(int flyNumber) {
        int landWay = getFreeWayNumber();
        System.out.printf("%s - Flight: %d will land on way: %d, in: %s \n",
                LocalTime.now(), flyNumber, landWay, AirportName);
        return landWay;
    }

    /**
     * Free the way that flight left
     * @param flyNumber: flight number - for print
     * @param wayNumber: way number - to free it
     */
    public synchronized void freeRunway(int flyNumber, int wayNumber) {
        System.out.printf("%s - Flight: %d currently left the way: %d, in %s\n",
                LocalTime.now(), flyNumber, wayNumber, AirportName);
        Runways.replace(wayNumber, wayStatus.FREE);
        notifyAll();
    }
}