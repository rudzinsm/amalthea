package pl.maru;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class SimpleListArgumentConverterTest {

    final SimpleListArgumentConverter listArgumentConverter = new SimpleListArgumentConverter();

    static Stream<Arguments> listArgumentConverterValues() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(" [ ] ", Collections.emptyList()),
                Arguments.of("[]", Collections.emptyList()),
                Arguments.of("  [  a  ,  b  ]  ", Arrays.asList("a", "b")),
                Arguments.of("[a,b]", Arrays.asList("a", "b"))
        );
    }

    @ParameterizedTest
    @MethodSource("listArgumentConverterValues")
    void listArgumentConverterTest(@ConvertWith(SimpleListArgumentConverter.class) List<String> converted, List<String> expected) {
        // expect
        Assertions.assertThat(converted)
                .isEqualTo(expected);
    }

    static Stream<Arguments> listArgumentConverterErrorsValues() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(" [ ] ", Collections.emptyList()),
                Arguments.of("[]", Collections.emptyList()),
                Arguments.of("  [  a  ,  b  ]  ", Arrays.asList("a", "b")),
                Arguments.of("[a,b]", Arrays.asList("a", "b"))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "[",
            "]",
            "][",
            "ab",
    })
    void listArgumentConverterErrorsTest(String value) {
        // given
        SimpleListArgumentConverter simpleListArgumentConverter = new SimpleListArgumentConverter();

        // expect
        Assertions.assertThatThrownBy(
                        () -> simpleListArgumentConverter.convert(value)
                ).isInstanceOf(ArgumentConversionException.class);
    }

}