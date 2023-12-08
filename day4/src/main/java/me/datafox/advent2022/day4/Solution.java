package me.datafox.advent2022.day4;

import me.datafox.advent2022.SolutionBase;

import java.util.Arrays;

/**
 * Solution for advent of code 2022 day 4.
 *
 * @see <a href=https://adventofcode.com/2022/day/4>https://adventofcode.com/2022/day/4</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        return String.valueOf(input
                .lines()
                .filter(this::isFullOverlap)
                .count());
    }

    @Override
    protected String solution2(String input) {
        return String.valueOf(input
                .lines()
                .filter(this::isAnyOverlap)
                .count());
    }

    private boolean isFullOverlap(String s) {
        int[] arr = rangesToArray(s);
        return (arr[0] <= arr[2] && arr[1] >= arr[3]) || (arr[0] >= arr[2] && arr[1] <= arr[3]);
    }

    private boolean isAnyOverlap(String s) {
        int[] arr = rangesToArray(s);
        return arr[0] <= arr[3] && arr[2] <= arr[1];
    }

    private int[] rangesToArray(String s) {
        return Arrays.stream(s.split("[,-]"))
                .mapToInt(Integer::parseInt).toArray();
    }
}
