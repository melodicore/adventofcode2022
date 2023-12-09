package me.datafox.advent2022.day12;

import me.datafox.advent2022.SolutionBase;

import java.util.*;

/**
 * Solution for advent of code 2022 day 12.
 *
 * @see <a href=https://adventofcode.com/2022/day/12>https://adventofcode.com/2022/day/12</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    private static final Coord[] directions = {
            new Coord(1, 0),
            new Coord(0, 1),
            new Coord(-1, 0),
            new Coord(0, -1) };

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        char[][] map = input.lines().map(String::toCharArray).toArray(char[][]::new);
        Coord start = null;
        Coord end = null;
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                char c = map[y][x];
                if(c == 'S') {
                    start = new Coord(x, y);
                    map[y][x] = 'a';
                } else if(c == 'E') {
                    end = new Coord(x, y);
                    map[y][x] = 'z';
                }
            }
        }
        Set<Coord> visited = new HashSet<>();
        Set<Route> routes = new HashSet<>();
        visited.add(start);
        routes.add(new Route(start));
        Route result = getResultRoute(map, visited, routes, end);
        return String.valueOf(result.visited.size() - 1);
    }

    @Override
    protected String solution2(String input) {
        return "";
    }

    private Route getResultRoute(char[][] map, Set<Coord> visited, Set<Route> routes, Coord end) {
        while(true) {
            if(routes.isEmpty()) return null;
            for(Route route : new HashSet<>(routes)) {
                for(Coord dir : directions) {
                    Coord last = route.current;
                    Coord coord = last.add(dir);
                    if(coord.isOutOfBounds(map) || visited.contains(coord)) {
                        continue;
                    }
                    char c = map[coord.y][coord.x];
                    char d = map[last.y][last.x];
                    if(c - d <= 1) {
                        Route copy = new Route(route);
                        routes.add(copy);
                        copy.current = coord;
                        copy.visited.add(coord);
                        visited.add(coord);
                        if(coord.equals(end)) {
                            return copy;
                        }
                    }
                }
                routes.remove(route);
            }
        }
    }

    private record Coord(int x, int y) {
        public Coord add(Coord other) {
            return new Coord(this.x + other.x, this.y + other.y);
        }

        public boolean isOutOfBounds(char[][] map) {
            return x < 0 || y < 0 || y >= map.length || x >= map[y].length;
        }
    }

    private static class Route {
        private static long counter = 0;

        private final long id;
        private Coord current;
        private final Set<Coord> visited;

        private Route(Coord start) {
            id = counter++;
            current = start;
            visited = new LinkedHashSet<>();
            visited.add(start);
        }

        private Route(Route other) {
            id = counter++;
            current = other.current;
            visited = new LinkedHashSet<>(other.visited);
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            Route route = (Route) o;
            return id == route.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
