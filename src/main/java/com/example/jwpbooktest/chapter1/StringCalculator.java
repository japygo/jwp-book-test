package com.example.jwpbooktest.chapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    public int add(String str) {
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
        List<String> result = new ArrayList<>();
        List<String> separatorList = getSeparator(str);

        if (str.startsWith("//") && str.contains("\n")) {
            str = str.split("\n")[1];
        }

        if (separatorList.size() == 1) {
            return Arrays.stream(str.split(separatorList.get(0))).toList();
        } else if (separatorList.size() == 2) {
            return Arrays.stream(str.split(separatorList.get(0)))
                    .map(s -> s.split(separatorList.get(1)))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
        } else if (separatorList.size() == 3) {
            return Arrays.stream(str.split(separatorList.get(0)))
                    .map(s -> s.split(separatorList.get(1)))
                    .flatMap(Arrays::stream)
                    .map(s -> s.split(separatorList.get(2)))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
        }

        return result;
    }

    public List<String> getSeparator(String str) {
        List<String> result = new ArrayList<>();
        if (str.startsWith("//") && str.contains("\n")) {
            result.add(str.split("\n")[0].substring(2));
        }
        if (str.contains(",")) {
            result.add(",");
        }
        if (str.contains(":")) {
            result.add(":");
        }

        return result;
    }
}
