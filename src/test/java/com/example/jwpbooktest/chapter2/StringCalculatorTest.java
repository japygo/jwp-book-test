package com.example.jwpbooktest.chapter2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new StringCalculator();
    }

    @Test
    public void add() {
        // given
        String str = "1,2:3";

        // when
        int sum = calculator.add(str);

        // then
        assertThat(sum).isEqualTo(6);
    }

    @Test
    public void parseNumber() {
        // given
        String minusNumStr = "-1";
        String str = "num";
        String numStr = "1";

        // when
        Throwable minusNumStrThrow = catchThrowable(() -> calculator.parseNumber(minusNumStr));
        Throwable strThrow = catchThrowable(() -> calculator.parseNumber(str));
        int num = calculator.parseNumber(numStr);

        // then
        assertThat(minusNumStrThrow).isInstanceOf(RuntimeException.class);
        assertThat(strThrow).isInstanceOf(RuntimeException.class);
        assertThat(num).isEqualTo(1);
    }

    @Test
    public void split() {
        // given
        String onlyComma = "1,2,3";
        String onlyColon = "1:2:3";
        String commaColon = "1,2:3";
        String custom = "//;\n1;2;3";
        String customColon = "//;\n1;2:3";
        String all = "//;\n1;2,3:4";

        // when
        List<String> onlyCommaNumList = calculator.split(onlyComma);
        List<String> onlyColonNumList = calculator.split(onlyColon);
        List<String> commaColonNumList = calculator.split(commaColon);
        List<String> customNumList = calculator.split(custom);
        List<String> customColonNumList = calculator.split(customColon);
        List<String> allNumList = calculator.split(all);

        // then
        assertThat(onlyCommaNumList).hasSize(3).containsAll(Arrays.asList("1", "2", "3"));
        assertThat(onlyColonNumList).hasSize(3).containsAll(Arrays.asList("1", "2", "3"));
        assertThat(commaColonNumList).hasSize(3).containsAll(Arrays.asList("1", "2", "3"));
        assertThat(customNumList).hasSize(3).containsAll(Arrays.asList("1", "2", "3"));
        assertThat(customColonNumList).hasSize(3).containsAll(Arrays.asList("1", "2", "3"));
        assertThat(allNumList).hasSize(4).containsAll(Arrays.asList("1", "2", "3", "4"));
    }

    @Test
    public void getSeparator() {
        // given
        String onlyComma = "1,2,3";
        String onlyColon = "1:2:3";
        String commaColon = "1,2:3";
        String custom = "//;\n1;2;3";
        String customColon = "//;\n1;2:3";
        String all = "//;\n1;2,3:4";

        // when
        String onlyCommaSeparator = calculator.getSeparator(onlyComma);
        String onlyColonSeparator = calculator.getSeparator(onlyColon);
        String commaColonSeparator = calculator.getSeparator(commaColon);
        String customSeparator = calculator.getSeparator(custom);
        String customColonSeparator = calculator.getSeparator(customColon);
        String allSeparator = calculator.getSeparator(all);

        // then
        assertThat(onlyCommaSeparator).isEqualTo(",");
        assertThat(onlyColonSeparator).isEqualTo(":");
        assertThat(commaColonSeparator).isEqualTo(",|:");
        assertThat(customSeparator).isEqualTo(";");
        assertThat(customColonSeparator).isEqualTo(";|:");
        assertThat(allSeparator).isEqualTo(";|,|:");
    }
}
