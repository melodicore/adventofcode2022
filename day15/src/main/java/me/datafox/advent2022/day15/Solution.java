package me.datafox.advent2022.day15;

import me.datafox.advent2022.SolutionBase;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solution for advent of code 2022 day 15.
 *
 * @see <a href=https://adventofcode.com/2022/day/15>https://adventofcode.com/2022/day/15</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        Set<Sensor> sensors = input
                .lines()
                .map(this::getSensor)
                .collect(Collectors.toSet());
        int y = 2000000;
        Set<Integer> beaconsInY = input
                .lines()
                .flatMap(s -> getBeaconXInY(s, y))
                .collect(Collectors.toSet());
        return String.valueOf(getKnownForY(sensors, y, beaconsInY));
    }

    @Override
    protected String solution2(String input) {
        Set<Sensor> sensors = input
                .lines()
                .map(this::getSensor)
                .collect(Collectors.toSet());
        return String.valueOf(getEmptyPosition(sensors, 0, 4000000));
    }

    private Sensor getSensor(String s) {
        String[] split = s.substring(12).split("[,=:]");
        int x = Integer.parseInt(split[0].strip());
        int y = Integer.parseInt(split[2].strip());
        int bx = Integer.parseInt(split[4].strip());
        int by = Integer.parseInt(split[6].strip());
        return new Sensor(x, y, Math.abs(x - bx) + Math.abs(y - by));
    }

    private Stream<Integer> getBeaconXInY(String s, int y) {
        String[] split = s.substring(12).split("[,=:]");
        if(Integer.parseInt(split[6].strip()) == y) {
            return Stream.of(Integer.parseInt(split[4].strip()));
        }
        return Stream.empty();
    }

    private int getKnownForY(Set<Sensor> sensors, int y, Set<Integer> beaconsInY) {
        int start = sensors.stream().mapToInt(Sensor::getMinX).min().orElse(-1);
        int end = sensors.stream().mapToInt(Sensor::getMaxX).max().orElse(-1);
        int counter = 0;
        for(int[] i = { start }; i[0] <= end; i[0]++) {
            if(!beaconsInY.contains(i[0]) && sensors.stream().anyMatch(s -> s.covers(i[0], y))) {
                counter++;
            }
        }
        return counter;
    }

    @SuppressWarnings("SameParameterValue")
    private long getEmptyPosition(Set<Sensor> sensors, int min, int max) {
        for(int[] y = {min}; y[0] <= max; y[0]++) {
            for(int[] x = {min}; x[0] <= max; x[0]++) {
                Set<Sensor> inRange = sensors
                        .stream()
                        .filter(s -> s.covers(x[0], y[0]))
                        .collect(Collectors.toSet());
                if(inRange.isEmpty()) {
                    return (long) x[0] * 4000000L + y[0];
                } else {
                    x[0] = inRange.stream().mapToInt(s -> s.lastCover(y[0])).max().orElse(max);
                }
            }
        }
        return -1;
    }

    private record Sensor(int x, int y, int range) {
        public int getMinX() {
            return x - range;
        }
        public int getMaxX() {
            return x + range;
        }

        public boolean covers(int x, int y) {
            return Math.abs(this.x - x) + Math.abs(this.y - y) <= range;
        }

        public int lastCover(int y) {
            return this.x + (range - Math.abs(this.y - y));
        }
    }
}
