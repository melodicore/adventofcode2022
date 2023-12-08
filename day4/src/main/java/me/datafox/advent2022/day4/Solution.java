package me.datafox.advent2022.day4;

import me.datafox.advent2022.SolutionBase;

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
        String[] parts = s.split(",");
        String[] a = parts[0].split("-");
        String[] b = parts[1].split("-");
        int a1 = Integer.parseInt(a[0]);
        int a2 = Integer.parseInt(a[1]);
        int b1 = Integer.parseInt(b[0]);
        int b2 = Integer.parseInt(b[1]);
        return (a1 <= b1 && a2 >= b2) || (a1 >= b1 && a2 <= b2);
    }

    private boolean isAnyOverlap(String s) {
        String[] parts = s.split(",");
        String[] a = parts[0].split("-");
        String[] b = parts[1].split("-");
        int a1 = Integer.parseInt(a[0]);
        int a2 = Integer.parseInt(a[1]);
        int b1 = Integer.parseInt(b[0]);
        int b2 = Integer.parseInt(b[1]);
        return a1 <= b2 && b1 <= a2;
    }
}
