package me.datafox.advent2022.day18;

import me.datafox.advent2022.SolutionBase;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Solution for advent of code 2022 day 18.
 *
 * @see <a href=https://adventofcode.com/2022/day/18>https://adventofcode.com/2022/day/18</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    private static final Coord[] adjacent = {
            new Coord(1, 0, 0),
            new Coord(-1, 0, 0),
            new Coord(0, 1, 0),
            new Coord(0, -1, 0),
            new Coord(0, 0, 1),
            new Coord(0, 0, -1),
    };

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        Set<Coord> cubes = input.lines().map(this::getCoord).collect(Collectors.toSet());
        int sides = 0;
        for(Coord cube : cubes) {
            for(Coord coord : adjacent) {
                if(!cubes.contains(cube.add(coord))) {
                    sides++;
                }
            }
        }
        return String.valueOf(sides);
    }

    @Override
    protected String solution2(String input) {
        return "";
    }

    private Coord getCoord(String s) {
        String[] split = s.split(",");
        return new Coord(Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]));
    }

    private record Coord(int x, int y, int z) {
        private Coord add(Coord o) {
            return new Coord(x + o.x, y + o.y, z + o.z);
        }
    }
}
