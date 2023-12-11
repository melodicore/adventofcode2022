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
                        s -> new Tunnel(s.substring(6, 8), Integer.parseInt(s.split("[=;]")[1]))));
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
            int max = paths.stream().mapToInt(p -> p.opened.size()).max().orElseThrow();
            paths.retainAll(paths.stream().filter(p -> p.opened.size() >= max - 2).collect(Collectors.toSet()));
        }
        return String.valueOf(paths.stream().mapToInt(Path::counter).max().orElse(-1));
    }

    @Override
    protected String solution2(String input) {
        Map<String,Tunnel> tunnels = input
                .lines()
                .collect(Collectors.toMap(
                        s -> s.substring(6, 8),
                        s -> new Tunnel(s.substring(6, 8), Integer.parseInt(s.split("[=;]")[1]))));
        input.lines().forEach(s -> tunnels
                .get(s.substring(6,8))
                .leadsTo()
                .addAll(Arrays
                        .stream(s
                                .split("valves |valve ")[1]
                                .split(", "))
                        .map(tunnels::get)
                        .toList()));
        Set<ElephantPath> paths = new HashSet<>();
        paths.add(new ElephantPath(tunnels.get("AA")));
        for(int i = 0; i < 26; i++) {
            Set<ElephantPath> copy = new HashSet<>(paths);
            paths.clear();
            for(ElephantPath p : copy) {
                p = p.copyCounter();
                if(p.current.equals(p.eCurrent)) {
                    if(p.canOpenCurrent()) {
                        ElephantPath path = p.copyOpened();
                        for(Tunnel t : path.current.leadsTo) {
                            paths.add(path.copyCurrent(p.current, t));
                        }
                    }
                } else {
                    if(p.canOpenCurrent() && p.canOpenECurrent()) {
                        paths.add(p.copyBOpened());
                    }
                    if(p.canOpenCurrent()) {
                        ElephantPath path = p.copyOpened();
                        for(Tunnel t : path.eCurrent.leadsTo) {
                            paths.add(path.copyCurrent(p.current, t));
                        }
                    }
                    if(p.canOpenECurrent()) {
                        ElephantPath path = p.copyEOpened();
                        for(Tunnel t : path.current.leadsTo) {
                            paths.add(path.copyCurrent(t, p.eCurrent));
                        }
                    }
                }
                for(Tunnel t1 : p.current.leadsTo) {
                    for(Tunnel t2 : p.eCurrent.leadsTo) {
                        paths.add(p.copyCurrent(t1, t2));
                    }
                }
            }
            int max = paths.stream().mapToInt(p -> p.opened.size()).max().orElseThrow();
            paths.retainAll(paths.stream().filter(p -> p.opened.size() >= max - 4).collect(Collectors.toSet()));
            int maxc = paths.stream().mapToInt(p -> p.counter).max().orElseThrow();
            paths.retainAll(paths.stream().filter(p -> p.counter >= maxc - 100).collect(Collectors.toSet()));
        }
        return String.valueOf(paths.stream().mapToInt(ElephantPath::counter).max().orElse(-1));
    }

    private record Tunnel(String id, int rate, List<Tunnel> leadsTo) {
        private Tunnel(String id, int rate) {
            this(id, rate, new ArrayList<>());
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            Tunnel tunnel = (Tunnel) o;
            return Objects.equals(id, tunnel.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
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

    private record ElephantPath(int counter, Tunnel current, Tunnel eCurrent, Set<Tunnel> opened) {
        private ElephantPath(Tunnel first) {
            this(0, first, first, Set.of());
        }

        public ElephantPath copyCounter() {
            return new ElephantPath(counter + opened.stream().mapToInt(Tunnel::rate).sum(),
                    current, eCurrent, opened);
        }

        public ElephantPath copyCurrent(Tunnel current, Tunnel eCurrent) {
            return new ElephantPath(counter, current, eCurrent, opened);
        }

        public ElephantPath copyOpened() {
            return new ElephantPath(counter, current, eCurrent, Stream
                    .concat(opened.stream(), Stream.of(current))
                    .collect(Collectors.toSet()));
        }

        public ElephantPath copyEOpened() {
            return new ElephantPath(counter, current, eCurrent, Stream
                    .concat(opened.stream(), Stream.of(eCurrent))
                    .collect(Collectors.toSet()));
        }

        public ElephantPath copyBOpened() {
            return new ElephantPath(counter, current, eCurrent, Stream
                    .concat(opened.stream(), Stream.of(current, eCurrent))
                    .collect(Collectors.toSet()));
        }

        public boolean canOpenCurrent() {
            return current.rate != 0 && !opened.contains(current);
        }

        public boolean canOpenECurrent() {
            return eCurrent.rate != 0 && !opened.contains(eCurrent);
        }
    }
}
