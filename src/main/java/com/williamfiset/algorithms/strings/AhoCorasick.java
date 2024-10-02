package com.williamfiset.algorithms.strings;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AhoCorasick {

    // Trie node
    static class TrieNode {
        Map<Character, TrieNode> children;
        TrieNode failure;
        List<Integer> outputs;

        TrieNode() {
            children = new HashMap<>();
            failure = null;
            outputs = new ArrayList<>();
        }
    }

    // Build the trie and failure functions
    static TrieNode buildTrie(String[] patterns) {
        TrieNode root = new TrieNode();

        for (int i = 0; i < patterns.length; i++) {
            TrieNode current = root;
            for (int j = 0; j < patterns[i].length(); j++) {
                char c = patterns[i].charAt(j);
                if (!current.children.containsKey(c)) {
                    current.children.put(c, new TrieNode());
                }
                current = current.children.get(c);
            }
            current.outputs.add(i);
        }

        // Build failure function
        List<TrieNode> queue = new ArrayList<>();
        for (TrieNode child : root.children.values()) {
            child.failure = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.remove(0);
            for (Map.Entry<Character, TrieNode> child : current.children.entrySet()) {
                char c = child.getKey();
                TrieNode node = child.getValue();
                queue.add(node);
                TrieNode failure = current.failure;
                while (failure != null && !failure.children.containsKey(c)) {
                    failure = failure.failure;
                }
                if (failure == null) {
                    node.failure = root;
                } else {
                    node.failure = failure.children.get(c);
                    node.outputs.addAll(failure.outputs);
                }
            }
        }

        return root;
    }

    // Search for patterns in the text
    static List<Integer> search(TrieNode root, String text) {
        List<Integer> matches = new ArrayList<>();
        TrieNode current = root;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (current != root && !current.children.containsKey(c)) {
                current = current.failure;
            }
            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            }
            for (Integer output : current.outputs) {
                matches.add(output);
            }
        }
        return matches;
    }

    public static void main(String[] args) {
        String[] patterns = {"he", "she", "hers", "his"};
        String text = "ahishers";
        TrieNode root = buildTrie(patterns);
        List<Integer> matches = search(root, text);
        System.out.println("Matched patterns at positions: " + matches);    }
}
