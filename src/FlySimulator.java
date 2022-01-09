/*
Student: Alexey Vartanov
ID: 321641086
Maman 15 - Question 2 - Airport simulator
 */

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlySimulator {

    public static void main(String [] args) throws InterruptedException {

        /* create 2 airports with 3 ways each one */
        Airport newYork = new Airport("\"John F. Kennedy Int Airport\"", 3);
        Airport telAviv = new Airport("\"Ben Gurion Int Airport\"", 3);

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Airport> airports = Arrays.asList(newYork, telAviv);
        Random rand = new Random();

        /* create and execute 10 flight with random departure location
        * using 4 digits for number flight */
        for (int i=0; i<10; i++){
            Airport source = airports.get(rand.nextInt(airports.size()));
            Airport destination = source == newYork ? telAviv : newYork;
            Flight f = new Flight(i+1000, source, destination);
            executor.execute(f);
        }
        executor.shutdown();
    }
}