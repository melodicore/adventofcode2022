package me.datafox.advent2022.day11;

import me.datafox.advent2022.SolutionBase;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Solution for advent of code 2022 day 11.
 *
 * @see <a href=https://adventofcode.com/2022/day/11>https://adventofcode.com/2022/day/11</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        Monkey[] monkeys = Arrays
                .stream(input.split("\n\n"))
                .map(this::getMonkey)
                .toArray(Monkey[]::new);
        int[] inspections = new int[monkeys.length];
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < monkeys.length; j++) {
                LinkedList<Long> items = monkeys[j].items;
                while(!items.isEmpty()) {
                    inspections[j]++;
                    long item = items.removeFirst();
                    item = monkeys[j].operation.apply(item);
                    item /= 3;
                    if(item % monkeys[j].test == 0) {
                        monkeys[monkeys[j].ifTrue].items.add(item);
                    } else {
                        monkeys[monkeys[j].ifFalse].items.add(item);
                    }
                }
            }
        }
        Arrays.sort(inspections);
        return String.valueOf(inspections[inspections.length - 1] * inspections[inspections.length - 2]);
    }

    @Override
    protected String solution2(String input) {
        Monkey[] monkeys = Arrays
                .stream(input.split("\n\n"))
                .map(this::getMonkey)
                .toArray(Monkey[]::new);
        long[] inspections = new long[monkeys.length];
        long max = Arrays
                .stream(monkeys)
                .mapToLong(Monkey::test)
                .reduce(1, (i, j) -> i * j);
        for(int i = 0; i < 10000; i++) {
            for(int j = 0; j < monkeys.length; j++) {
                LinkedList<Long> items = monkeys[j].items;
                while(!items.isEmpty()) {
                    inspections[j]++;
                    long item = items.removeFirst() % max;
                    item = monkeys[j].operation.apply(item);
                    if(item % monkeys[j].test == 0) {
                        monkeys[monkeys[j].ifTrue].items.add(item);
                    } else {
                        monkeys[monkeys[j].ifFalse].items.add(item);
                    }
                }
            }
        }
        Arrays.sort(inspections);
        return String.valueOf(inspections[inspections.length - 1] * inspections[inspections.length - 2]);
    }

    private Monkey getMonkey(String s) {
        String[] split = s.split("\n");
        return new Monkey(Arrays
                .stream(split[1].substring(18).split(", "))
                .map(Long::parseLong)
                .collect(Collectors.toCollection(LinkedList::new)),
                getOperation(split[2].substring(19)),
                Integer.parseInt(split[3].substring(21)),
                Integer.parseInt(split[4].substring(29)),
                Integer.parseInt(split[5].substring(30)));
    }

    private Function<Long,Long> getOperation(String s) {
        String[] split = s.split(" ");
        return old -> {
            long a, b;
            if(split[0].equals("old")) {
                a = old;
            } else {
                a = Integer.parseInt(split[0]);
            }
            if(split[2].equals("old")) {
                b = old;
            } else {
                b = Integer.parseInt(split[2]);
            }
            if(split[1].equals("+")) {
                return a + b;
            } else {
                return a * b;
            }
        };
    }

    private record Monkey(LinkedList<Long> items, Function<Long,Long> operation, long test, int ifTrue, int ifFalse) {
    }
}
