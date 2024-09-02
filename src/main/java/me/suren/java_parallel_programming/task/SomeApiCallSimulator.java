package me.suren.java_parallel_programming.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class SomeApiCallSimulator implements Callable<Integer> {

    private final Integer input;

    public SomeApiCallSimulator(int input) {
        this.input = input;
    }

    @Override
    public Integer call() throws Exception {
        log.info("Sleeping for {} ms", input);
        try {
            Thread.sleep(input);
        } catch (InterruptedException e) {
            log.warn("Thread::sleep ran into error - {}", e.getMessage());
        }

        log.info("Sleeping for {} ms - completed.", input);
        return input;
    }
}
