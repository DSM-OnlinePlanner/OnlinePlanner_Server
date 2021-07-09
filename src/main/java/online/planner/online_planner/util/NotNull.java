package online.planner.online_planner.util;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class NotNull {
    public <T> void setIfNotNull(Consumer<T> setter, T value) {
        if(value != null)
            setter.accept(value);
    }
}
