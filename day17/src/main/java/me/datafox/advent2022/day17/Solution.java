package me.datafox.advent2022.day17;

import me.datafox.advent2022.SolutionBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solution for advent of code 2022 day 17.
 *
 * @see <a href=https://adventofcode.com/2022/day/17>https://adventofcode.com/2022/day/17</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    private static final boolean[][][] blocks = {
            { { true, true, true, true } },

            { { false, true, false },
              { true,  true, true  },
              { false, true, false } },

            { { true,  true,  true },
              { false, false, true },
              { false, false, true } },

            { { true },
              { true },
              { true },
              { true } },

            { { true, true },
              { true, true } } };
    private static final int width = 7;
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        List<boolean[]> world = new ArrayList<>();
        int[] counter = { 0 };
        for(int i = 0; i < 2022; i++) {
            dropBlock(world, i, counter, input.toCharArray());
        }
        return String.valueOf(world.size());
    }

    @Override
    protected String solution2(String input) {
        List<boolean[]> world = new ArrayList<>();
        int[] counter = { 0 };
        Set<String> repeats = new HashSet<>();
        int fi = -1;
        int fs = -1;
        int si = -1;
        int ss = -1;
        for(int i = 0; i < 10000; i++) {
            dropBlock(world, i, counter, input.toCharArray());
            String s = (i % blocks.length) + ", " + (counter[0] % input.length());
            if(repeats.contains(s)) {
                if(fi == -1) {
                    fi = i;
                    fs = world.size();
                    repeats.clear();
                } else {
                    si = i;
                    ss = world.size();
                    break;
                }
            }
            repeats.add(s);
        }
        int iteration = si - fi;
        int rowsPerIteration = ss - fs;
        long rowsAfterLoop = 1000000000000L - fi;
        long iterations = (rowsAfterLoop / iteration);
        int remainingIterations = (int) (rowsAfterLoop % iteration);
        for(int i = si + 1; i < si + remainingIterations; i++) {
            dropBlock(world, i, counter, input.toCharArray());
        }
        return String.valueOf(fs + (iterations * rowsPerIteration) + world.size() - ss);
    }

    private void dropBlock(List<boolean[]> world, int i, int[] counter, char[] directions) {
        boolean[][] block = blocks[i % blocks.length];
        int x = 2;
        int y = world.size() + 3;
        l: while(true) {
            boolean dir = directions[counter[0] % directions.length] == '<';
            counter[0]++;
            if(!(dir && x == 0) && !(!dir && x == width - block[0].length)) {
                int wind = dir ? -1 : 1;
                w: for(int ys = 0; ys < block.length; ys++) {
                    if(y + ys < world.size()) {
                        boolean[] slice = world.get(y + ys);
                        int st = dir ? 0 : block[ys].length - 1;
                        int en = dir ? block[ys].length : 0;
                        for(int xs = st; dir == (xs < en); xs -= wind) {
                            int si = x + xs + wind;
                            if(block[ys][xs] && slice[si]) {
                                wind = 0;
                                break w;
                            }
                        }
                    }
                }
                x += wind;
            }
            if(y == 0) {
                break;
            }
            for(int ys = 0; ys < block.length; ys++) {
                if(y + ys <= world.size()) {
                    boolean[] slice = world.get(y + ys - 1);
                    for(int xs = 0; xs < block[ys].length; xs++) {
                        if(block[ys][xs] && slice[x + xs]) {
                            break l;
                        }
                    }
                }
            }
            y--;
        }
        for(int ys = 0; ys < block.length; ys++) {
            boolean[] slice;
            if(y + ys < world.size()) {
                slice = world.get(y + ys);
            } else {
                slice = new boolean[7];
            }
            for(int xs = 0; xs < block[ys].length; xs++) {
                if(block[ys][xs]) {
                    slice[x + xs] = true;
                }
            }
            if(y + ys >= world.size()) {
                world.add(slice);
            }
        }
    }
}
