package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return getWords(text).parallelStream()
                .unordered()
                .mapToInt(String::length)
                .sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return getWords(text).size();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return getUniqueWords(text).size();
    }

    @Override
    public List<String> getWords(String text) {
        return Arrays.stream(text.split("\\W+")).collect(Collectors.toList());
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return new HashSet<>(getWords(text));
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        return getWords(text).stream()
                .collect(Collectors.groupingBy(
                        (s) -> s,
                        Collectors.reducing(
                                0,
                                (s) -> 1,
                                Integer::sum)));
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        return getWords(text).stream()
                .sorted(getComparatorByDirection(direction))
                .collect(Collectors.toList());
    }

    private Comparator<String> getComparatorByDirection(Direction direction) {
        return direction == Direction.ASC ?
                Comparator.comparingInt(String::length) :
                Comparator.comparingInt(String::length).reversed();
    }
}
