package online.planner.online_planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableScheduling
public class OnlinePlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinePlannerApplication.class, args);
    }

}
