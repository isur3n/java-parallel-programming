package me.suren.java_parallel_programming.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("${api.root.uri}")
public class HealthController {

    @GetMapping("/health")
    public Map getHealth() {
        return Map.of("status", "running");
    }
}
