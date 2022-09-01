package de.mindmatters.weatherfail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private GreeterService greeterService;

    public HelloController(GreeterService greeterService) {
        this.greeterService = greeterService;
    }

    @GetMapping("/")
    public String index() {
        // return "Greetings from Spring Boot!";
        return this.greeterService.generateGreeting("Spring Boot");
    }
}