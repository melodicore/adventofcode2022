package me.datafox.advent2022.day7;

import me.datafox.advent2022.SolutionBase;

import java.util.*;

/**
 * Solution for advent of code 2022 day 7.
 *
 * @see <a href=https://adventofcode.com/2022/day/7>https://adventofcode.com/2022/day/7</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        Dir root = getFilesystem(input);
        List<Integer> list = new ArrayList<>();
        traverseAndAdd(root, list);
        return String.valueOf(list
                .stream()
                .mapToInt(Integer::intValue)
                .filter(i -> i <= 100000)
                .sum());
    }

    @Override
    protected String solution2(String input) {
        return "";
    }

    private Dir getFilesystem(String input) {
        Dir root = new Dir("", null);
        Stack<Dir> stack = new Stack<>();
        for(String s : input.split("\n")) {
            String[] terms = s.split(" ");
            if(s.equals("$ cd /")) {
                stack.clear();
                stack.push(root);
            } else if(terms[0].equals("$")) {
                if(terms[1].equals("cd")) {
                    if(terms[2].equals("..")) {
                        stack.pop();
                    } else {
                        stack.push((Dir) stack.peek().nodes.get(terms[2]));
                    }
                }
            } else if(terms[0].equals("dir")) {
                stack.peek().nodes.put(terms[1], new Dir(terms[1], stack.peek()));
            } else {
                stack.peek().nodes.put(terms[1], new File(terms[1],
                        Integer.parseInt(terms[0]), stack.peek()));
            }
        }
        return root;
    }

    private int traverseAndAdd(Dir dir, List<Integer> list) {
        int i = 0;
        for(Node node : dir.nodes.values()) {
            if(node instanceof Dir d) {
                i += traverseAndAdd(d, list);
            } else if(node instanceof File f) {
                i += f.size;
            }
        }
        list.add(i);
        return i;
    }

    private static class Node {
        protected final String name;
        protected final Dir parent;

        public Node(String name, Dir parent) {
            this.name = name;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static class Dir extends Node {
        private final Map<String,Node> nodes;

        public Dir(String name, Dir parent) {
            super(name, parent);
            nodes = new HashMap<>();
        }

        @Override
        public String toString() {
            return "Dir{" +
                    "nodes=" + nodes +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private static class File extends Node {
        private final int size;

        public File(String name, int size, Dir parent) {
            super(name, parent);
            this.size = size;
        }

        @Override
        public String toString() {
            return "File{" +
                    "size=" + size +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
