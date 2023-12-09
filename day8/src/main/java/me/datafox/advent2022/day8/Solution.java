package me.datafox.advent2022.day8;

import me.datafox.advent2022.SolutionBase;

/**
 * Solution for advent of code 2022 day 8.
 *
 * @see <a href=https://adventofcode.com/2022/day/8>https://adventofcode.com/2022/day/8</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        int[][] map = input
                .lines()
                .map(this::toIntArray)
                .toArray(int[][]::new);
        int visible = 0;
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[x].length; y++) {
                if(isVisible(map, x, y)) {
                    visible++;
                }
            }
        }
        return String.valueOf(visible);
    }

    @Override
    protected String solution2(String input) {
        int[][] map = input
                .lines()
                .map(this::toIntArray)
                .toArray(int[][]::new);
        int max = 0;
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[x].length; y++) {
                max = Math.max(getScore(map, x, y), max);
            }
        }
        return String.valueOf(max);
    }

    private boolean isVisible(int[][] map, int x, int y) {
        for(int dir = 0; dir < 4; dir++) {
            if(isVisible(map, x, y, dir)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVisible(int[][] map, int x, int y, int dir) {
        boolean xy = (dir & 1) == 0;
        boolean r = dir < 2;
        int largest = -1;
        boolean larger = false;
        for(int i = r ? 0 : (xy ? map.length : map[x].length) - 1; r ? i <= (xy ? x : y) : i >= (xy ? x : y); i += r ? 1 : -1) {
            int current = xy ? map[i][y] : map[x][i];
            if(i != (xy ? x : y) && current >= map[x][y]) {
                return false;
            }
            if(current > largest) {
                larger = true;
                largest = current;
            } else {
                larger = false;
            }
        }
        return larger;
    }

    private int getScore(int[][] map, int x, int y) {
        int result = 1;
        for(int dir = 0; dir < 4; dir++) {
            result *= getScore(map, x, y, dir);
            if(result == 0) return 0;
        }
        return result;
    }

    private int getScore(int[][] map, int x, int y, int dir) {
        boolean xy = (dir & 1) == 0;
        boolean r = dir < 2;
        int og = map[x][y];
        int count = 0;
        for(int i = xy ? x : y; r ? i < (xy ? map.length : map[x].length) : i >= 0; i += r ? 1 : -1) {
            if(i == (xy ? x : y)) {
                continue;
            }
            int current = xy ? map[i][y] : map[x][i];
            count++;
            if(current >= og) {
                break;
            }
        }
        return count;
    }

    private int[] toIntArray(String s) {
        int[] arr = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            arr[i] = Integer.parseInt(s.substring(i, i + 1));
        }
        return arr;
    }
}
