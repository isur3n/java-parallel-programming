package me.suren.java_parallel_programming.task;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class RandomDelay {

    public static Random RANDOM_INTEGER = new Random();

    public static Supplier<Integer> task = () -> {

        int delay = RANDOM_INTEGER.nextInt(4999);
        log.info("Sleeping for {} ms", delay);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            log.warn("Thread::sleep ran into error - {}", e.getMessage());
        }

        log.info("Sleeping for {} ms - completed.", delay);
        return delay;
    };

    public static Function<Future, Integer> getDelayValue = f -> {
        int delay = -1;
        try {
            delay = (int) f.get();
        } catch (InterruptedException | ExecutionException e) {
            log.warn("Error occurred while getting delay value - {}", e.getMessage());
        }
        return delay;
    };
}
