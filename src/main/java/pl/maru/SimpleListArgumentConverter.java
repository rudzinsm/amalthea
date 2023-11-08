package pl.maru;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.TypedArgumentConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class SimpleListArgumentConverter extends TypedArgumentConverter<String, List> {

    private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\\s*,\\s*");

    public SimpleListArgumentConverter() {
        super(String.class, List.class);
    }

    @Override
    protected List convert(String stringList) throws ArgumentConversionException {
        if (stringList == null) {
            return null;
        }
        String trimmed = StringUtils.trim(stringList);

        if (!(StringUtils.startsWith(trimmed, "[") && StringUtils.endsWith(trimmed, "]"))) {
            throw new ArgumentConversionException("Representation of list should start with '[' and end with ']' provided value was " + trimmed);
        }
        String listValues = StringUtils.substringBetween(trimmed, "[", "]");
        if (StringUtils.isBlank(listValues)) {
            return Collections.emptyList();
        }
        return Arrays.asList(SEPARATOR_PATTERN.split(listValues.trim()));
    }
}
