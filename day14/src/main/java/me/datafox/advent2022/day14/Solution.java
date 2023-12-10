package me.datafox.advent2022.day14;

import me.datafox.advent2022.SolutionBase;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solution for advent of code 2022 day 14.
 *
 * @see <a href=https://adventofcode.com/2022/day/14>https://adventofcode.com/2022/day/14</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        Set<Block> universe = input
                .lines()
                .flatMap(this::getBlocks)
                .collect(Collectors.toSet());
        int maxy = universe.stream().mapToInt(Block::y).max().orElse(-1);
        Block pour = new Block(500, 0);
        int counter = 0;
        while(isPourWithinY(universe, pour, maxy)) {
            counter++;
        }
        return String.valueOf(counter);
    }

    @Override
    protected String solution2(String input) {
        Set<Block> universe = input
                .lines()
                .flatMap(this::getBlocks)
                .collect(Collectors.toSet());
        int floor = universe.stream().mapToInt(Block::y).max().orElse(-3) + 2;
        Block pour = new Block(500, 0);
        int counter = 0;
        while(canPour(universe, pour, floor)) {
            counter++;
        }
        return String.valueOf(counter + 1);
    }

    private Stream<Block> getBlocks(String s) {
        String[] split = s.split(" -> ");
        Block block = null;
        Set<Block> blocks = new HashSet<>();
        for(String pos : split) {
            if(block == null) {
                block = getBlock(pos);
            } else {
                Block other = getBlock(pos);
                blocks.addAll(line(block, other));
                block = other;
            }
        }
        blocks.add(block);
        return blocks.stream();
    }

    private Block getBlock(String s) {
        String[] split = s.split(",");
        return new Block(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private Set<Block> line(Block block, Block other) {
        if(block.equals(other)) {
            return Set.of(block);
        }
        boolean dir = block.x == other.x;
        int start, end;
        if(dir) {
            start = Math.min(block.y, other.y);
            end = Math.max(block.y, other.y);
        } else {
            start = Math.min(block.x, other.x);
            end = Math.max(block.x, other.x);
        }
        Set<Block> blocks = new HashSet<>();
        for(int i = start; i <= end; i++) {
            blocks.add(new Block(dir ? block.x : i, dir ? i : block.y));
        }
        return blocks;
    }

    private boolean isPourWithinY(Set<Block> universe, Block pour, int maxy) {
        Block last;
        Block next = pour;
        do {
            last = next;
            next = drop(last, universe, maxy + 2);
            if(next.y > maxy) {
                return false;
            }
        } while(!last.equals(next));
        universe.add(next);
        return true;
    }

    private boolean canPour(Set<Block> universe, Block pour, int floor) {
        Block last;
        Block next = pour;
        do {
            last = next;
            next = drop(last, universe, floor);
            if(next == pour) return false;
        } while(!last.equals(next));
        universe.add(next);
        return true;
    }

    private Block drop(Block block, Set<Block> universe, int floor) {
        Block next = block.drop(0);
        if(next.y == floor) {
            return block;
        }
        if(!universe.contains(next)) {
            return next;
        }
        next = block.drop(-1);
        if(!universe.contains(next)) {
            return next;
        }
        next = block.drop(1);
        if(!universe.contains(next)) {
            return next;
        }
        return block;
    }

    private record Block(int x, int y) {
        private Block drop(int x) {
            return new Block(this.x + x, this.y + 1);
        }
    }
}
