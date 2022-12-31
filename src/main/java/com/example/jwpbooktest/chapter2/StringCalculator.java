package com.example.jwpbooktest.chapter2;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public int add(String str) {
        if (!StringUtils.hasText(str)) {
            return 0;
        }

        List<String> numStrList = split(str);

        return numStrList.stream()
                .map(this::parseNumber)
                .reduce(Integer::sum)
                .orElseThrow(RuntimeException::new);
    }

    public int parseNumber(String strNum) {
        int num = Integer.parseInt(strNum);

        if (num < 0) {
            throw new RuntimeException();
        }

        return num;
    }

    public List<String> split(String str) {
        String separator = getSeparator(str);

        Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(str);
        if (matcher.find()) {
            return Arrays.stream(matcher.group(2).split(separator)).toList();
        }

        return Arrays.stream(str.split(separator)).toList();
    }

    public String getSeparator(String str) {
        String result = "";

        Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(str);
        if (matcher.find()) {
            result += matcher.group(1);
        }
        if (str.contains(",")) {
            if (result.length() > 0) result += "|";
            result += ",";
        }
        if (str.contains(":")) {
            if (result.length() > 0) result += "|";
            result += ":";
        }

        return result;
    }
}
