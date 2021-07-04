package online.planner.online_planner.entity.exp.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ExpTypeConvertor implements AttributeConverter<ExpType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ExpType attribute) {
        return attribute.getGiveExp();
    }

    @Override
    public ExpType convertToEntityAttribute(Integer dbData) {
        return Stream.of(ExpType.values())
                .filter(expType -> expType.getGiveExp().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
