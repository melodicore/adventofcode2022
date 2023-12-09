package me.datafox.advent2022.day13;

import me.datafox.advent2022.SolutionBase;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solution for advent of code 2022 day 13.
 *
 * @see <a href=https://adventofcode.com/2022/day/13>https://adventofcode.com/2022/day/13</a>
 *
 * @author datafox
 */
public class Solution extends SolutionBase {
    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    protected String solution1(String input) {
        List<Tuple<ListNode,ListNode>> nodes = Arrays
                .stream(input.split("\n\n"))
                .map(this::toNodes)
                .toList();
        int result = 0;
        for(int i = 0; i < nodes.size(); i++) {
            Tuple<ListNode,ListNode> tuple = nodes.get(i);
            if(compareListNodes(tuple.t1, tuple.t2) < 1) {
                result += i + 1;
            }
        }
        return String.valueOf(result);
    }

    @Override
    protected String solution2(String input) {
        input = "[[2]]\n[[6]]\n\n" + input;
        List<ListNode> nodes = Arrays
                .stream(input.split("\n\n"))
                .map(this::toNodes)
                .flatMap(this::flatMapTuple)
                .collect(Collectors.toCollection(ArrayList::new));
        ListNode start = nodes.get(0);
        ListNode end = nodes.get(1);
        nodes.sort(this::compareListNodes);
        return String.valueOf((nodes.indexOf(start) + 1) * (nodes.indexOf(end) + 1));
    }

    private Tuple<ListNode,ListNode> toNodes(String s) {
        String[] split = s.split("\n");
        return new Tuple<>(toNode(split[0]), toNode(split[1]));
    }

    private int compareListNodes(ListNode left, ListNode right) {
        if(left.nodes.isEmpty() && right.nodes.isEmpty()) {
            return 0;
        }
        if(left.nodes.isEmpty()) {
            return -1;
        }
        if(right.nodes.isEmpty()) {
            return 1;
        }
        for(int i = 0; i < left.nodes.size(); i++) {
            if(i >= right.nodes.size()) {
                return 1;
            }
            Node l = left.nodes.get(i);
            Node r = right.nodes.get(i);
            int j;
            if(l.isList() && r.isList()) {
                j = compareListNodes((ListNode) l, (ListNode) r);
            } else if(l.isList()) {
                j = compareListNodes((ListNode) l, ((IntNode) r).toList());
            } else if(r.isList()) {
                j = compareListNodes(((IntNode) l).toList(), (ListNode) r);
            } else {
                j = Integer.compare(((IntNode) l).value, ((IntNode) r).value);
            }
            if(j != 0) {
                return j;
            }
        }
        return 0;
    }

    private ListNode toNode(String s) {
        Stack<ListNode> stack = new Stack<>();
        stack.add(new ListNode());
        s = s.substring(1, s.length() - 1);
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()) {
            if(c == '[') {
                ListNode node = new ListNode();
                stack.peek().nodes.add(node);
                stack.push(node);
            } else if(c == ']' || c == ',') {
                if(!sb.isEmpty()) {
                    stack.peek().nodes.add(new IntNode(Integer.parseInt(sb.toString())));
                    sb = new StringBuilder();
                }
                if(c == ']') {
                    stack.pop();
                }
            } else if(Character.isDigit(c)) {
                sb.append(c);
            }
        }
        if(!sb.isEmpty()) {
            stack.peek().nodes.add(new IntNode(Integer.parseInt(sb.toString())));
        }
        return stack.peek();
    }

    private Stream<ListNode> flatMapTuple(Tuple<ListNode,ListNode> t) {
        return Stream.of(t.t1, t.t2);
    }

    private record Tuple<T1,T2>(T1 t1, T2 t2) {}

    private abstract static class Node {
        public abstract boolean isList();
    }

    private static class ListNode extends Node {
        private final List<Node> nodes;

        private ListNode() {
            this.nodes = new ArrayList<>();
        }

        @Override
        public boolean isList() {
            return true;
        }
    }

    private static class IntNode extends Node {
        private final int value;

        private IntNode(int value) {
            this.value = value;
        }

        private ListNode toList() {
            ListNode node = new ListNode();
            node.nodes.add(this);
            return node;
        }

        @Override
        public boolean isList() {
            return false;
        }
    }
}
