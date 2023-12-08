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
        int result = -1;
        for(int i = 0; i < input.length() - 3; i++) {
            Set<Character> set = new HashSet<>();
            for(int j = i; j < i + 4; j++) {
                set.add(input.charAt(j));
            }
            System.out.println(set);
            if(set.size() == 4) {
                result = i + 4;
                break;
            }
        }
        return String.valueOf(result);
    }

    @Override
    protected String solution2(String input) {
        return "";
    }
}
