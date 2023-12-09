package me.datafox.advent2022.day13;

import me.datafox.advent2022.SolutionBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
        List<Tuple<Node,Node>> nodes = Arrays
                .stream(input.split("\n\n"))
                .map(this::toNodes)
                .toList();
        int result = 0;
        for(int i = 0; i < nodes.size(); i++) {
            Tuple<Node,Node> tuple = nodes.get(i);
            ListNode left = (ListNode) tuple.t1;
            ListNode right = (ListNode) tuple.t2;
            if(compareListNodes(left, right) < 1) {
                result += i + 1;
            }
        }
        return String.valueOf(result);
    }

    private Tuple<Node,Node> toNodes(String s) {
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

    private Node toNode(String s) {
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

    @Override
    protected String solution2(String input) {
        return "";
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

        @Override
        public String toString() {
            return nodes.toString();
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

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
