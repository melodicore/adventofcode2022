package me.datafox.advent2022.day3;

import me.datafox.advent2022.SolutionBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Solution for advent of code 2022 day 3.
 *
 * @see <a href=https://adventofcode.com/2022/day/3>https://adventofcode.com/2022/day/3</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    private static final String score = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        return String.valueOf(input
                .lines()
                .map(this::getCommonChar1)
                .mapToInt(score::indexOf)
                .sum());
    }

    @Override
    protected String solution2(String input) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for(String s : input.split("\n")) {
            sb.append(s).append("\n");
            if(++i == 3) {
                sb.append("\n");
                i = 0;
            }
        }
        return String.valueOf(Arrays
                .stream(sb
                        .toString()
                        .split("\n\n"))
                .map(this::getCommonChar2)
                .mapToInt(score::indexOf)
                .sum());
    }

    private char getCommonChar1(String s) {
        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();
        int half = s.length() / 2;
        for(int i = 0; i < s.length(); i++) {
            if(i < half) {
                set1.add(s.charAt(i));
            } else {
                set2.add(s.charAt(i));
            }
        }
        set1.retainAll(set2);
        return set1.iterator().next();
    }

    private char getCommonChar2(String s) {
        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();
        Set<Character> set3 = new HashSet<>();
        int set = 0;
        for(String s1 : s.split("\n")) {
            for(char c : s1.toCharArray()) {
                switch(set) {
                    case 0 -> set1.add(c);
                    case 1 -> set2.add(c);
                    case 2 -> set3.add(c);
                }
            }
            set++;
        }
        set1.retainAll(set2);
        set1.retainAll(set3);
        return set1.iterator().next();
    }
}
