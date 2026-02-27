package javaExperi.evenFilter;

import java.util.*;

/**
 *
 * @author kyle
 */

public class Main {

    static final int FILTER_WIDTH_X = 3;
    static final int FILTER_WIDTH_Y = 3;
    static final int SAMPLE_SIZE = 10000000;
    static final int RAND_MAX = 1000;

    static final double TIME_MAGNITUDE = 1000000;
    static final int NUMERIC_PRECISION = 6;
    static final String ROUND_TO = "%.4f";

    public static void movingAverage(int[] sample, double magnifier)
    {
        Deque<Integer> window = new LinkedList<>();
        int cache_y = 0;
        int cache_x;
        double test;

        for(int i = 1; i < FILTER_WIDTH_X; i++)
            window.add(0);

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            cache_x = sample[i];
            window.add(cache_x);

            cache_y += cache_x;

            test = cache_y/FILTER_WIDTH_X/magnifier;

            //System.err.println(String.format(ROUND_TO,test));

            cache_y -= window.poll();

            test++;
        }
    }

    public static void filterFIR(int[] sample, int[] weights_x, double magnifier)
    {
        Deque<Integer> window_x = new LinkedList<>();
        int cache_y;

        Iterator<Integer> iterator_x;

        for(int i = 1; i < FILTER_WIDTH_X; i++)
            window_x.add(0);

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            cache_y = 0;
            window_x.add(sample[i]);

            iterator_x = window_x.descendingIterator();
            for (int j = 0; iterator_x.hasNext(); j++)
                cache_y += iterator_x.next()*weights_x[j];

            //System.err.println(String.format(ROUND_TO,cache_y/magnifier));

            window_x.poll();
        }

        cache_y = 0;
        cache_y++;
    }

    public static void filterBA(int[] sample, int[] weights_x, int[] weights_y, double magnifier)
    {
        Deque<Integer> window_x = new LinkedList<>();
        Deque<Integer> window_y = new LinkedList<>();
        int cache_y;

        Iterator<Integer> iterator_x;
        Iterator<Integer> iterator_y;

        for(int i = 1; i < FILTER_WIDTH_X; i++)
            window_x.add(0);

        for(int i = 1; i < FILTER_WIDTH_Y; i++)
            window_y.add(0);

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            cache_y = 0;
            window_x.add(sample[i]);

            iterator_x = window_x.descendingIterator();
            for (int j = 0; iterator_x.hasNext(); j++)
                cache_y += iterator_x.next()*weights_x[j];

            //System.err.print(cache_y/weights_y[0]/1000000f);
            //System.err.print(": ");

            iterator_y = window_y.descendingIterator();
            for (int j = 1; iterator_y.hasNext(); j++)
                cache_y -= iterator_y.next()*weights_y[j];

            //System.err.println(String.format(ROUND_TO,cache_y/weights_y[0]/magnifier));

            window_x.poll();
            window_y.poll();
            window_y.add(cache_y/weights_y[0]);
        }
    }

    public static void main(String args[]){

        Random r = new Random();
        int x[] = new int[SAMPLE_SIZE];
        int[] weights_x = {100,200,300,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1};
        int[] weights_y = {100,-156,64};
        double magnifier = Math.pow(10,NUMERIC_PRECISION);

        int test[] = {1,2,3,4,5,6,7,8,9,10};
        for (int i = 0; i < test.length; i++) {
            test[i] *= magnifier;
        }

        long start;
        long diff;

        for (int i = 0; i < SAMPLE_SIZE; i++)
            x[i] = r.nextInt(RAND_MAX);

        
        start = System.nanoTime();
        movingAverage(x, magnifier);
        diff = (System.nanoTime() - start);
        System.err.println(String.format(ROUND_TO,diff/TIME_MAGNITUDE));

        start = System.nanoTime();
        filterFIR(x, weights_x, magnifier);
        diff = (System.nanoTime() - start);
        System.err.println(String.format(ROUND_TO,diff/TIME_MAGNITUDE));

        start = System.nanoTime();
        filterBA(x, weights_x, weights_y, magnifier);
        diff = (System.nanoTime() - start);
        System.err.println(String.format(ROUND_TO,diff/TIME_MAGNITUDE));
    }
}