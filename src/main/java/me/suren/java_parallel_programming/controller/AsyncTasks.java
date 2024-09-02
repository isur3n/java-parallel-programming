package me.suren.java_parallel_programming.controller;

import lombok.extern.slf4j.Slf4j;
import me.suren.java_parallel_programming.service.AsyncTaskService;
import me.suren.java_parallel_programming.service.CompletableFutureService;
import me.suren.java_parallel_programming.task.RandomDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("${api.root.uri}")
public class AsyncTasks {

    private AsyncTaskService asyncTaskService;

    public AsyncTasks(@Autowired AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    @GetMapping("/async-task")
    public List<Integer> getRandomDelays()
            throws ExecutionException, InterruptedException, TimeoutException {

        int tasks = RandomDelay.RANDOM_INTEGER.nextInt(1,5);
        log.info("Number of tasks - {}", tasks);

        return asyncTaskService.doSomething(tasks);
    }
}
