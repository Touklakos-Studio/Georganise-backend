package isima.georganise.app.service.util;

import isima.georganise.app.entity.util.Right;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jetbrains.annotations.NotNull;

/**
 * Converter class for converting between Right enum and Character for database storage.
 * This class is automatically applied to all entities with Right attributes.
 */
@Converter(autoApply = true)
public class RightConverter implements AttributeConverter<Right, Character> {

    /**
     * Converts a Right enum to a Character for database storage.
     *
     * @param right The Right enum to be converted.
     * @return The corresponding Character representation of the Right enum.
     */
    @Override
    public @NotNull Character convertToDatabaseColumn(@NotNull Right right) {
        return right.toString().charAt(0);
    }

    /**
     * Converts a Character from the database to a Right enum.
     *
     * @param character The Character to be converted.
     * @return The corresponding Right enum of the Character.
     */
    @Override
    public @NotNull Right convertToEntityAttribute(Character character) {
        return Right.valueOf(character == 'W' ? "WRITER" : "READER");
    }
}