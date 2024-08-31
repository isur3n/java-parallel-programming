package me.suren.java_parallel_programming.controller;

import lombok.extern.slf4j.Slf4j;
import me.suren.java_parallel_programming.task.RandomDelay;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("${api.root.uri}")
public class CompletableFutures {

    @GetMapping("/completable-futures")
    public List<Integer> getRandomDelays() {

        int tasks = RandomDelay.RANDOM_INTEGER.nextInt(5);
        log.info("Number of tasks - {}", tasks);

        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for(int count = 0; count < tasks; count++) {
            futures.add(CompletableFuture.supplyAsync(RandomDelay.task));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return futures.stream()
                .map(RandomDelay.getDelayValue)
                .collect(Collectors.toList());
    }
}
