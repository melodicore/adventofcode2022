package me.datafox.advent2022.day5;

import me.datafox.advent2022.SolutionBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Solution for advent of code 2022 day 5.
 *
 * @see <a href=https://adventofcode.com/2022/day/5>https://adventofcode.com/2022/day/5</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        List<Stack<Character>> stacks = getStacks(input.split("\n\n")[0]);
        List<int[]> moves = getMoves(input.split("\n\n")[1]);
        for(int[] move : moves) {
            for(int i = 0; i < move[0]; i++) {
                stacks.get(move[2]).push(stacks.get(move[1]).pop());
            }
        }
        return stacks.stream()
                .map(Stack::pop)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    protected String solution2(String input) {
        List<Stack<Character>> stacks = getStacks(input.split("\n\n")[0]);
        List<int[]> moves = getMoves(input.split("\n\n")[1]);
        for(int[] move : moves) {
            List<Character> temp = new ArrayList<>();
            for(int i = 0; i < move[0]; i++) {
                temp.add(0, stacks.get(move[1]).pop());
            }
            stacks.get(move[2]).addAll(temp);
        }
        return stacks.stream()
                .map(Stack::pop)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private List<Stack<Character>> getStacks(String blocks) {
        List<Stack<Character>> stacks = new ArrayList<>();
        for(String s : blocks.split("\n")) {
            int i = 0;
            for(int j = 1; j < s.length(); j += 4) {
                if(stacks.size() <= i) {
                    stacks.add(new Stack<>());
                }
                Stack<Character> stack = stacks.get(i++);
                char c = s.charAt(j);
                if(Character.isDigit(c)) {
                    break;
                } else if(c != ' '){
                    stack.add(0, c);
                }
            }
        }
        return stacks;
    }

    private List<int[]> getMoves(String moves) {
        List<int[]> list = new ArrayList<>();
        for(String s : moves.split("\n")) {
            String[] split = s.split(" ");
            list.add(new int[] { Integer.parseInt(split[1]),
                    Integer.parseInt(split[3]) - 1,
                    Integer.parseInt(split[5]) - 1 });
        }
        return list;
    }
}
