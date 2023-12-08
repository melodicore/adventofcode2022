package me.datafox.advent2022.day;

import me.datafox.advent2022.SolutionBase;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * Solution for advent of code 2022 day 1.
 *
 * @see <a href=https://adventofcode.com/2022/day/1>https://adventofcode.com/2022/day/1</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        return String.valueOf(Arrays.stream(
                        input.split("\n\n"))
                .mapToInt(s -> s
                        .lines()
                        .mapToInt(Integer::parseInt)
                        .sum())
                .max().orElse(-1));
    }

    @Override
    protected String solution2(String input) {
        return "";
    }
}
