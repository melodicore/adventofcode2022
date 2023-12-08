package me.datafox.advent2022.day2;

import me.datafox.advent2022.SolutionBase;

/**
 * Solution for advent of code 2022 day 2.
 *
 * @see <a href=https://adventofcode.com/2022/day/2>https://adventofcode.com/2022/day/2</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    private static final String player1 = "ABC";
    private static final String player2 = "XYZ";
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        return String.valueOf(input.lines().mapToInt(this::getResult).sum());
    }

    @Override
    protected String solution2(String input) {
        return "";
    }

    private int getResult(String s) {
        int i = player1.indexOf(s.charAt(0));
        int j = player2.indexOf(s.charAt(2));
        if(i == j) {
            return j + 4;
        }
        if((i + 1) % 3 == j) {
            return j + 7;
        }
        return j + 1;
    }
}
