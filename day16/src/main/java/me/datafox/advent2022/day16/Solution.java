package me.datafox.advent2022.day16;

import me.datafox.advent2022.SolutionBase;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solution for advent of code 2022 day 16.
 *
 * @see <a href=https://adventofcode.com/2022/day/16>https://adventofcode.com/2022/day/16</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        Map<String,Tunnel> tunnels = input
                .lines()
                .collect(Collectors.toMap(
                        s -> s.substring(6, 8), 
                        s -> new Tunnel(Integer.parseInt(s.split("[=;]")[1]))));
        input.lines().forEach(s -> tunnels
                .get(s.substring(6,8))
                .leadsTo()
                .addAll(Arrays
                        .stream(s
                                .split("valves |valve ")[1]
                                .split(", "))
                        .map(tunnels::get)
                        .toList()));
        Set<Path> paths = new HashSet<>();
        paths.add(new Path(tunnels.get("AA")));
        for(int i = 0; i < 30; i++) {
            Set<Path> copy = new HashSet<>(paths);
            paths.clear();
            for(Path p : copy) {
                p = p.copyCounter();
                if(p.current.rate != 0 && !p.opened.contains(p.current)) {
                    paths.add(p.copyOpened());
                }
                for(Tunnel t : p.current.leadsTo) {
                    paths.add(p.copyCurrent(t));
                }
            }
        }
        return String.valueOf(paths.stream().mapToInt(Path::counter).max().orElse(-1));
    }

    @Override
    protected String solution2(String input) {
        return "";
    }

    private static final class Tunnel {
        private final int rate;

        private final List<Tunnel> leadsTo;

        private Tunnel(int rate, List<Tunnel> leadsTo) {
            this.rate = rate;
            this.leadsTo = leadsTo;
        }

        private Tunnel(int rate) {
            this(rate, new ArrayList<>());
        }

        public int rate() {
            return rate;
        }

        public List<Tunnel> leadsTo() {
            return leadsTo;
        }
    }

    private record Path(int counter, Tunnel current, Set<Tunnel> opened) {
        private Path(Tunnel first) {
            this(0, first, Set.of());
        }

        public Path copyCounter() {
            return new Path(counter + opened.stream().mapToInt(Tunnel::rate).sum(), current, opened);
        }

        public Path copyCurrent(Tunnel current) {
            return new Path(counter, current, opened);
        }

        public Path copyOpened() {
            return new Path(counter, current, Stream
                    .concat(opened.stream(), Stream.of(current))
                    .collect(Collectors.toSet()));
        }
    }
}
