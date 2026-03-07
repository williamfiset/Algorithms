package com.williamfiset.algorithms.datastructures.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie (Prefix Tree)
 *
 * A tree-like data structure for efficient storage and retrieval of strings.
 * Each node stores a character and a map of children. Supports insert, delete,
 * prefix counting, and containment checks.
 *
 * Use cases:
 *   - Autocomplete and spell-checking
 *   - Prefix-based searching (e.g., IP routing, phone directories)
 *   - Detecting if a string is a prefix of another
 *
 * Time:  O(L) for insert, delete, contains, and count, where L = key length
 * Space: O(N * L) where N = number of keys, L = average key length
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class Trie {

  private final char rootCharacter = '\0';
  private Node root = new Node(rootCharacter);

  private static class Node {
    char ch;
    int count = 0;
    boolean isWordEnding = false;
    Map<Character, Node> children = new HashMap<>();

    Node(char ch) {
      this.ch = ch;
    }
  }

  /**
   * Inserts a key into the trie with a given multiplicity.
   *
   * @param key the string to insert
   * @param numInserts number of times to insert the key
   * @return true if the exact word already existed in the trie before this insert
   */
  public boolean insert(String key, int numInserts) {
    if (key == null) throw new IllegalArgumentException("Null not permitted in trie");
    if (numInserts <= 0)
      throw new IllegalArgumentException("numInserts has to be greater than zero");

    Node node = root;
    for (int i = 0; i < key.length(); ++i) {
      char ch = key.charAt(i);
      Node nextNode = node.children.get(ch);

      if (nextNode == null) {
        nextNode = new Node(ch);
        node.children.put(ch, nextNode);
      }

      node = nextNode;
      node.count += numInserts;
    }

    // Empty string is not a valid word
    if (node == root) return false;

    boolean alreadyExisted = node.isWordEnding;
    node.isWordEnding = true;

    return alreadyExisted;
  }

  /** Inserts a key into the trie once. */
  public boolean insert(String key) {
    return insert(key, 1);
  }

  /**
   * Deletes a key from the trie. If numDeletions exceeds the count of a
   * node along the path, that subtree is pruned — cutting off access to
   * all strings below that prefix.
   *
   * @param key the string to delete
   * @param numDeletions number of times to delete the key
   * @return true if the key existed and was deleted
   */
  public boolean delete(String key, int numDeletions) {
    if (!contains(key)) return false;
    if (numDeletions <= 0) throw new IllegalArgumentException("numDeletions has to be positive");

    Node node = root;
    for (int i = 0; i < key.length(); i++) {
      char ch = key.charAt(i);
      Node cur = node.children.get(ch);
      cur.count -= numDeletions;

      // Prune this subtree if count drops to zero or below
      if (cur.count <= 0) {
        node.children.remove(ch);
        return true;
      }

      node = cur;
    }
    return true;
  }

  /** Deletes a key from the trie once. */
  public boolean delete(String key) {
    return delete(key, 1);
  }

  /**
   * Checks if a key (or prefix) exists in the trie with count > 0.
   *
   * @param key the string to search for
   * @return true if the key is contained in the trie
   */
  public boolean contains(String key) {
    return count(key) != 0;
  }

  /**
   * Returns the insert count for a given prefix.
   *
   * @param key the prefix to look up
   * @return the count, or 0 if the prefix is not in the trie
   */
  public int count(String key) {
    if (key == null) throw new IllegalArgumentException("Null not permitted");

    Node node = root;
    for (int i = 0; i < key.length(); i++) {
      if (node == null) return 0;
      node = node.children.get(key.charAt(i));
    }

    return (node != null) ? node.count : 0;
  }

  /** Clears all entries from the trie. */
  public void clear() {
    root = new Node(rootCharacter);
  }
}
