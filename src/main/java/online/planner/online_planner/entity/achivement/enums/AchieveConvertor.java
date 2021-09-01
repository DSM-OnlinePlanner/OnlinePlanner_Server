package online.planner.online_planner.entity.achivement.enums;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class AchieveConvertor implements AttributeConverter<Achieve, String> {

    @Override
    public String convertToDatabaseColumn(Achieve attribute) {
        return attribute.getAchieve();
    }

    @Override
    public Achieve convertToEntityAttribute(String dbData) {
        return Stream.of(Achieve.values())
                .filter(achieve -> achieve.getAchieve().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
