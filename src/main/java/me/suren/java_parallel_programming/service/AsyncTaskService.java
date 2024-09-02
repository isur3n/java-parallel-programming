package me.suren.java_parallel_programming.service;

import lombok.extern.slf4j.Slf4j;
import me.suren.java_parallel_programming.task.RandomDelay;
import me.suren.java_parallel_programming.task.SomeApiCallSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AsyncTaskService {

    private AsyncTaskExecutor asyncTaskExecutor;

    public AsyncTaskService(@Autowired @Qualifier("applicationTaskExecutor")
                            AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    public List<Integer> doSomething(int input) throws InterruptedException, TimeoutException, ExecutionException {

        var scope = new StructuredTaskScope.ShutdownOnFailure();
        List<Supplier<Integer>> supplierList = new ArrayList<>();

        for(int count = 0; count < input; count++) {
            int delay = RandomDelay.RANDOM_INTEGER.nextInt(100, 500);
            SomeApiCallSimulator task = new SomeApiCallSimulator(delay);
            supplierList.add(scope.fork(task));
        }

        scope.join()
                .throwIfFailed(ExecutionException::new);

        return supplierList.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
    }
}
