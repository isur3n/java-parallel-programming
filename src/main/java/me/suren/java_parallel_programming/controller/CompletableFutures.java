package me.suren.java_parallel_programming.controller;

import lombok.extern.slf4j.Slf4j;
import me.suren.java_parallel_programming.service.CompletableFutureService;
import me.suren.java_parallel_programming.task.RandomDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequestMapping("${api.root.uri}")
public class CompletableFutures {

    private ExecutorService executorService;

    private CompletableFutureService completableFutureService;

    public CompletableFutures(
            @Autowired(required = false)
            ExecutorService executorService,
            @Autowired CompletableFutureService completableFutureService) {
        this.executorService = executorService;
        this.completableFutureService = completableFutureService;
        log.info("Virtual thread executor service available - {}", executorService != null);
    }

    @GetMapping("/completable-futures")
    public List<Integer> getRandomDelays() {

        int tasks = RandomDelay.RANDOM_INTEGER.nextInt(5);
        log.info("Number of tasks - {}", tasks);

        return completableFutureService.doSomething(tasks);
    }
}
