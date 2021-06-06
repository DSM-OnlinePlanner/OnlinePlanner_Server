package online.planner.online_planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OnlinePlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinePlannerApplication.class, args);
    }

}
