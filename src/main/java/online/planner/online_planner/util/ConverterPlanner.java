package online.planner.online_planner.util;

import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.planner.enums.Want;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ConverterPlanner {
    //convert enum by Integer
    public Want setWant(Planner planner) {
        Integer wantNum = planner.getPriority() % 5;
        if(wantNum.equals(0))
            wantNum = 5;

        Integer nWant = wantNum;

        return Stream.of(Want.values())
                .filter(want1 -> want1.getWant().equals(nWant))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Priority setPriority(Planner planner) {
        Integer wantNum = planner.getPriority() % 5;
        if(wantNum.equals(0))
            wantNum = 5;

        Integer pri = planner.getPriority() - wantNum;

        return Stream.of(Priority.values())
                .filter(priority1 -> priority1.getPriority().equals(pri))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
