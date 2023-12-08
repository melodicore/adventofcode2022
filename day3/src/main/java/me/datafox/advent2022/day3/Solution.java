package me.datafox.advent2022.day3;

import me.datafox.advent2022.SolutionBase;

import java.util.HashSet;
import java.util.LinkedHashSet;
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
        return String.valueOf(input.lines()
                .map(this::getCommonChar)
                .mapToInt(score::indexOf)
                .sum());
    }

    private char getCommonChar(String s) {
        Set<Character> set1 = new LinkedHashSet<>();
        Set<Character> set2 = new LinkedHashSet<>();
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

    @Override
    protected String solution2(String input) {
        return "";
    }
}
