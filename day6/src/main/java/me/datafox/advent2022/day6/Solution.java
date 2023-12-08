package me.datafox.advent2022.day6;

import me.datafox.advent2022.SolutionBase;

import java.util.HashSet;
import java.util.Set;

/**
 * Solution for advent of code 2022 day 6.
 *
 * @see <a href=https://adventofcode.com/2022/day/6>https://adventofcode.com/2022/day/6</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        return String.valueOf(getFirstNDistinct(input, 4));
    }

    @Override
    protected String solution2(String input) {
        return String.valueOf(getFirstNDistinct(input, 14));
    }

    private int getFirstNDistinct(String input, int n) {
        for(int i = 0; i < input.length() - n - 1; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = i; j < i + n; j++) {
                set.add(input.charAt(j));
            }
            if(set.size() == n) {
                return i + n;
            }
        }
        return -1;
    }
}
