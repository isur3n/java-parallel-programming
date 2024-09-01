package me.suren.java_parallel_programming.service;

import lombok.extern.slf4j.Slf4j;
import me.suren.java_parallel_programming.task.RandomDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompletableFutureService {

    private ExecutorService executorService;

    public CompletableFutureService(
            @Autowired(required = false)
            ExecutorService executorService) {
        this.executorService = executorService;
        log.info("Virtual thread executor service available - {}", executorService != null);
    }

    public List<Integer> doSomething(int input) {

        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for(int count = 0; count < input; count++) {
            if(executorService != null)
                futures.add(CompletableFuture.supplyAsync(RandomDelay.task, executorService));
            else
                futures.add(CompletableFuture.supplyAsync(RandomDelay.task));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return futures.stream()
                .map(RandomDelay.getDelayValue)
                .collect(Collectors.toList());
    }
}
