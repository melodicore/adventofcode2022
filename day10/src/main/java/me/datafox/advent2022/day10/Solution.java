package me.datafox.advent2022.day10;

import me.datafox.advent2022.SolutionBase;

import java.util.Iterator;

/**
 * Solution for advent of code 2022 day 10.
 *
 * @see <a href=https://adventofcode.com/2022/day/10>https://adventofcode.com/2022/day/10</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        int x = 1;
        int cycles = 0;
        int strength = 0;
        Iterator<String> it = input.lines().iterator();
        int add = 0;
        boolean mid = false;
        while(it.hasNext()) {
            cycles++;
            if(cycles % 40 == 20) {
                strength += x * cycles;
            }
            if(!mid) {
                String op = it.next();
                if(op.startsWith("addx")) {
                    mid = true;
                    add = Integer.parseInt(op.split(" ")[1]);
                }
            } else {
                mid = false;
                x += add;
            }
        }
        return String.valueOf(strength);
    }

    @Override
    protected String solution2(String input) {
        return "";
    }
}
