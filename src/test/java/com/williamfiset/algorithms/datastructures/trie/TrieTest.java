package com.williamfiset.algorithms.datastructures.trie;

import static org.junit.Assert.*;

import org.junit.*;

public class TrieTest {

  // @Before public void setup() { }

  @Test(expected = IllegalArgumentException.class)
  public void testBadTrieDelete1() {
    Trie t = new Trie();
    t.insert("some string");
    t.delete("some string", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadTrieDelete2() {
    Trie t = new Trie();
    t.insert("some string");
    t.delete("some string", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadTrieDelete3() {
    Trie t = new Trie();
    t.insert("some string");
    t.delete("some string", -345);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadTrieInsert() {
    (new Trie()).insert(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadTrieCount() {
    (new Trie()).count(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadTrieContains() {
    (new Trie()).contains(null);
  }

  @Test
  public void testContains() {

    Trie t1 = new Trie();

    // This implementation doesn't count the empty string as
    // a valid string to be inserted into the trie (although it
    // would be easy to account for)
    t1.insert("");
    assertFalse(t1.contains(""));
    t1.insert("");
    assertFalse(t1.contains(""));
    t1.insert("");
    assertFalse(t1.contains(""));

    Trie t2 = new Trie();
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    assertTrue(t2.contains("aaaaa"));
    assertTrue(t2.contains("aaaa"));
    assertTrue(t2.contains("aaa"));
    assertTrue(t2.contains("aa"));
    assertTrue(t2.contains("a"));

    Trie t3 = new Trie();

    t3.insert("AE");
    t3.insert("AE");
    t3.insert("AH");
    t3.insert("AH");
    t3.insert("AH7");
    t3.insert("A7");
    t3.insert("7");
    t3.insert("7");
    t3.insert("B");
    t3.insert("B");
    t3.insert("B");
    t3.insert("B");

    assertTrue(t3.contains("A"));
    assertTrue(t3.contains("AH"));
    assertTrue(t3.contains("A7"));
    assertTrue(t3.contains("AE"));
    assertTrue(t3.contains("AH7"));
    assertTrue(t3.contains("7"));
    assertTrue(t3.contains("B"));

    assertFalse(t3.contains("Ar"));
    assertFalse(t3.contains("A8"));
    assertFalse(t3.contains("AH6"));
    assertFalse(t3.contains("C"));
  }

  @Test
  public void testCount() {

    Trie t1 = new Trie();

    // This implementation doesn't count the empty string as
    // a valid string to be inserted into the trie (although it
    // would be easy to account for)
    t1.insert("");
    assertEquals(t1.count(""), 0);
    t1.insert("");
    assertEquals(t1.count(""), 0);
    t1.insert("");
    assertEquals(t1.count(""), 0);

    Trie t2 = new Trie();
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    assertEquals(t2.count("aaaaa"), 5);
    assertEquals(t2.count("aaaa"), 5);
    assertEquals(t2.count("aaa"), 5);
    assertEquals(t2.count("aa"), 5);
    assertEquals(t2.count("a"), 5);

    Trie t3 = new Trie();

    t3.insert("AE");
    t3.insert("AE");
    t3.insert("AH");
    t3.insert("AH");
    t3.insert("AH7");
    t3.insert("A7");

    t3.insert("7");
    t3.insert("7");

    t3.insert("B");
    t3.insert("B");
    t3.insert("B");
    t3.insert("B");

    assertEquals(t3.count("A"), 6);
    assertEquals(t3.count("AH"), 3);
    assertEquals(t3.count("A7"), 1);
    assertEquals(t3.count("AE"), 2);
    assertEquals(t3.count("AH7"), 1);
    assertEquals(t3.count("7"), 2);
    assertEquals(t3.count("B"), 4);
    assertEquals(t3.count("Ar"), 0);
    assertEquals(t3.count("A8"), 0);
    assertEquals(t3.count("AH6"), 0);
    assertEquals(t3.count("C"), 0);
  }

  @Test
  public void testInsert() {

    Trie t = new Trie();
    assertFalse(t.insert("a"));
    assertFalse(t.insert("b"));
    assertFalse(t.insert("c"));
    assertFalse(t.insert("d"));
    assertFalse(t.insert("x"));

    assertTrue(t.insert("ab"));
    assertTrue(t.insert("xkcd"));
    assertTrue(t.insert("dogs"));
    assertTrue(t.insert("bears"));

    assertFalse(t.insert("mo"));
    assertTrue(t.insert("mooooose"));

    t.clear();

    assertFalse(t.insert("aaaa", 4));
    assertEquals(t.count("aaaa"), 4);

    assertTrue(t.insert("aaa", 3));
    assertEquals(t.count("a"), 7);
    assertEquals(t.count("aa"), 7);
    assertEquals(t.count("aaa"), 7);
    assertEquals(t.count("aaaa"), 4);
    assertEquals(t.count("aaaaa"), 0);

    assertTrue(t.insert("a", 5));
    assertEquals(t.count("a"), 12);
    assertEquals(t.count("aa"), 7);
    assertEquals(t.count("aaa"), 7);
    assertEquals(t.count("aaaa"), 4);
    assertEquals(t.count("aaaaa"), 0);
  }

  @Test
  public void testClear() {

    Trie t = new Trie();

    assertFalse(t.insert("a"));
    assertFalse(t.insert("b"));
    assertFalse(t.insert("c"));

    assertTrue(t.contains("a"));
    assertTrue(t.contains("b"));
    assertTrue(t.contains("c"));

    t.clear();

    assertFalse(t.contains("a"));
    assertFalse(t.contains("b"));
    assertFalse(t.contains("c"));

    t.insert("aaaa");
    t.insert("aaab");
    t.insert("aaab5");
    t.insert("aaac");
    t.insert("aaacb");

    assertTrue(t.contains("aaa"));
    assertTrue(t.contains("aaacb"));
    assertTrue(t.contains("aaab5"));

    t.clear();

    assertFalse(t.contains("aaaa"));
    assertFalse(t.contains("aaab"));
    assertFalse(t.contains("aaab5"));
    assertFalse(t.contains("aaac"));
    assertFalse(t.contains("aaacb"));
  }

  @Test
  public void testDelete() {

    Trie t = new Trie();
    t.insert("AAC");
    t.insert("AA");
    t.insert("A");

    assertTrue(t.delete("AAC"));
    assertFalse(t.contains("AAC"));
    assertTrue(t.contains("AA"));
    assertTrue(t.contains("A"));

    assertTrue(t.delete("AA"));
    assertFalse(t.contains("AAC"));
    assertFalse(t.contains("AA"));
    assertTrue(t.contains("A"));

    assertTrue(t.delete("A"));
    assertFalse(t.contains("AAC"));
    assertFalse(t.contains("AA"));
    assertFalse(t.contains("A"));

    t.clear();

    t.insert("AAC");
    t.insert("AA");
    t.insert("A");

    assertTrue(t.delete("AA"));
    assertTrue(t.delete("AA"));

    assertFalse(t.contains("AAC"));
    assertFalse(t.contains("AA"));
    assertTrue(t.contains("A"));

    t.clear();

    t.insert("$A");
    t.insert("$B");
    t.insert("$C");

    assertTrue(t.delete("$", 3));

    assertFalse(t.delete("$"));
    assertFalse(t.contains("$"));
    assertFalse(t.contains("$A"));
    assertFalse(t.contains("$B"));
    assertFalse(t.contains("$C"));
    assertFalse(t.delete("$A"));
    assertFalse(t.delete("$B"));
    assertFalse(t.delete("$C"));

    t.clear();

    t.insert("$A");
    t.insert("$B");
    t.insert("$C");

    assertTrue(t.delete("$", 2));
    assertTrue(t.delete("$"));

    assertFalse(t.contains("$"));
    assertFalse(t.contains("$A"));
    assertFalse(t.contains("$B"));
    assertFalse(t.contains("$C"));
    assertFalse(t.delete("$A"));
    assertFalse(t.delete("$B"));
    assertFalse(t.delete("$C"));

    t.clear();

    t.insert("$A");
    t.insert("$B");
    t.insert("$C");

    assertTrue(t.delete("$", 2));

    assertTrue(t.contains("$"));
    assertTrue(t.contains("$A"));
    assertTrue(t.contains("$B"));
    assertTrue(t.contains("$C"));
    assertTrue(t.delete("$A"));
    assertFalse(t.delete("$B"));
    assertFalse(t.delete("$C"));

    t.clear();

    t.insert("CAT", 3);
    t.insert("DOG", 3);

    assertFalse(t.delete("parrot", 50));

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    assertTrue(t.delete("12", 6));
    assertFalse(t.delete("12"));
    assertFalse(t.delete("1"));
    assertFalse(t.contains("1234"));
    assertFalse(t.contains("123"));
    assertFalse(t.contains("12"));
    assertFalse(t.contains("1"));

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    t.delete("12", 999999);

    assertFalse(t.contains("1234"));
    assertFalse(t.contains("123"));
    assertFalse(t.contains("12"));
    assertFalse(t.contains("1"));

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    t.delete("12", 999999);

    assertFalse(t.contains("1234"));
    assertFalse(t.contains("123"));
    assertFalse(t.contains("12"));
    assertFalse(t.contains("1"));

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    assertTrue(t.delete("1234"));
    assertTrue(t.delete("123", 4));
    assertTrue(t.delete("122", 2));

    assertFalse(t.contains("1"));
    assertFalse(t.contains("12"));
    assertFalse(t.contains("122"));
    assertFalse(t.contains("123"));
    assertFalse(t.contains("1234"));
  }

  @Test
  public void testEdgeCases() {

    Trie t = new Trie();
    assertEquals(t.count(""), 0);
    assertEquals(t.count("\0"), 0);
    assertEquals(t.count("\0\0"), 0);
    assertEquals(t.count("\0\0\0"), 0);

    for (char c = 0; c < 128; c++) assertEquals(t.count("" + c), 0);

    assertFalse(t.contains(""));
    assertFalse(t.contains("\0"));
    assertFalse(t.contains("\0\0"));
    assertFalse(t.contains("\0\0\0"));

    for (char c = 0; c < 128; c++) assertFalse(t.contains("" + c));
  }
}
