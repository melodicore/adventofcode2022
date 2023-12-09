package me.datafox.advent2022.day9;

import me.datafox.advent2022.SolutionBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Solution for advent of code 2022 day 9.
 *
 * @see <a href=https://adventofcode.com/2022/day/9>https://adventofcode.com/2022/day/9</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        Coord head = new Coord(0, 0);
        Coord tail = head;
        Set<Coord> visited = new HashSet<>();
        for(String s : input.split("\n")) {
            for(int i = 0; i < Integer.parseInt(s.split(" ")[1]); i++) {
                switch(s.charAt(0)) {
                    case 'U' -> head = head.move(0, 1);
                    case 'D' -> head = head.move(0, -1);
                    case 'R' -> head = head.move(1, 0);
                    case 'L' -> head = head.move(-1, 0);
                }
                tail = moveTail(head, tail);
                visited.add(tail);
            }
        }
        return String.valueOf(visited.size());
    }

    @Override
    protected String solution2(String input) {
        Coord head = new Coord(0, 0);
        Coord[] tails = new Coord[9];
        Arrays.fill(tails, head);
        Set<Coord> visited = new HashSet<>();
        for(String s : input.split("\n")) {
            for(int i = 0; i < Integer.parseInt(s.split(" ")[1]); i++) {
                switch(s.charAt(0)) {
                    case 'U' -> head = head.move(0, 1);
                    case 'D' -> head = head.move(0, -1);
                    case 'R' -> head = head.move(1, 0);
                    case 'L' -> head = head.move(-1, 0);
                }
                tails[0] = moveTail(head, tails[0]);
                for(int j = 1; j < tails.length; j++) {
                    tails[j] = moveTail(tails[j - 1], tails[j]);
                }
                visited.add(tails[8]);
            }
        }
        return String.valueOf(visited.size());
    }

    private Coord moveTail(Coord head, Coord tail) {
        int xd = Math.abs(head.x - tail.x);
        int yd = Math.abs(head.y - tail.y);
        if(xd < 2 && yd < 2) {
            return tail;
        }
        int x = 0;
        int y = 0;
        if(xd != 0) {
            x = (head.x - tail.x) / xd;
        }
        if(yd != 0) {
            y = (head.y - tail.y) / yd;
        }
        return tail.move(x, y);
    }

    private record Coord(int x, int y) {
        private Coord move(int x, int y) {
            return new Coord(this.x + x, this.y + y);
        }
    }
}
