package de.mindmatters.weatherfail;

import org.springframework.stereotype.Service;

@Service
public class GreeterService {
    public GreeterService() {
    }

    public String generateGreeting(String name) {
        return "Hello, " + name + "!";
    }
}
