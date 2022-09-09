package de.mindmatters.weatherfail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // <-- @Scheduling benutzt der ScheduledRunner, aber die ganzen @Enabled* Annotations macht man lieber an der Application, weil die global sind
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}