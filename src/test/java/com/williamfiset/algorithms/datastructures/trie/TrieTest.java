package com.williamfiset.algorithms.datastructures.trie;

import static com.google.common.truth.Truth.assertThat;

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
    assertThat(t1.contains("")).isFalse();
    t1.insert("");
    assertThat(t1.contains("")).isFalse();
    t1.insert("");
    assertThat(t1.contains("")).isFalse();

    Trie t2 = new Trie();
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    assertThat(t2.contains("aaaaa")).isTrue();
    assertThat(t2.contains("aaaa")).isTrue();
    assertThat(t2.contains("aaa")).isTrue();
    assertThat(t2.contains("aa")).isTrue();
    assertThat(t2.contains("a")).isTrue();

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

    assertThat(t3.contains("A")).isTrue();
    assertThat(t3.contains("AH")).isTrue();
    assertThat(t3.contains("A7")).isTrue();
    assertThat(t3.contains("AE")).isTrue();
    assertThat(t3.contains("AH7")).isTrue();
    assertThat(t3.contains("7")).isTrue();
    assertThat(t3.contains("B")).isTrue();

    assertThat(t3.contains("Ar")).isFalse();
    assertThat(t3.contains("A8")).isFalse();
    assertThat(t3.contains("AH6")).isFalse();
    assertThat(t3.contains("C")).isFalse();
  }

  @Test
  public void testCount() {

    Trie t1 = new Trie();

    // This implementation doesn't count the empty string as
    // a valid string to be inserted into the trie (although it
    // would be easy to account for)
    t1.insert("");
    assertThat(t1.count("")).isEqualTo(0);
    t1.insert("");
    assertThat(t1.count("")).isEqualTo(0);
    t1.insert("");
    assertThat(t1.count("")).isEqualTo(0);

    Trie t2 = new Trie();
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    t2.insert("aaaaa");
    assertThat(t2.count("aaaaa")).isEqualTo(5);
    assertThat(t2.count("aaaa")).isEqualTo(5);
    assertThat(t2.count("aaa")).isEqualTo(5);
    assertThat(t2.count("aa")).isEqualTo(5);
    assertThat(t2.count("a")).isEqualTo(5);

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

    assertThat(t3.count("A")).isEqualTo(6);
    assertThat(t3.count("AH")).isEqualTo(3);
    assertThat(t3.count("A7")).isEqualTo(1);
    assertThat(t3.count("AE")).isEqualTo(2);
    assertThat(t3.count("AH7")).isEqualTo(1);
    assertThat(t3.count("7")).isEqualTo(2);
    assertThat(t3.count("B")).isEqualTo(4);
    assertThat(t3.count("Ar")).isEqualTo(0);
    assertThat(t3.count("A8")).isEqualTo(0);
    assertThat(t3.count("AH6")).isEqualTo(0);
    assertThat(t3.count("C")).isEqualTo(0);
  }

  @Test
  public void testInsert() {

    Trie t = new Trie();
    assertThat(t.insert("a")).isFalse();
    assertThat(t.insert("b")).isFalse();
    assertThat(t.insert("c")).isFalse();
    assertThat(t.insert("d")).isFalse();
    assertThat(t.insert("x")).isFalse();

    assertThat(t.insert("ab")).isTrue();
    assertThat(t.insert("xkcd")).isTrue();
    assertThat(t.insert("dogs")).isTrue();
    assertThat(t.insert("bears")).isTrue();

    assertThat(t.insert("mo")).isFalse();
    assertThat(t.insert("mooooose")).isTrue();

    t.clear();

    assertThat(t.insert("aaaa", 4)).isFalse();
    assertThat(t.count("aaaa")).isEqualTo(4);

    assertThat(t.insert("aaa", 3)).isTrue();
    assertThat(t.count("a")).isEqualTo(7);
    assertThat(t.count("aa")).isEqualTo(7);
    assertThat(t.count("aaa")).isEqualTo(7);
    assertThat(t.count("aaaa")).isEqualTo(4);
    assertThat(t.count("aaaaa")).isEqualTo(0);

    assertThat(t.insert("a", 5)).isTrue();
    assertThat(t.count("a")).isEqualTo(12);
    assertThat(t.count("aa")).isEqualTo(7);
    assertThat(t.count("aaa")).isEqualTo(7);
    assertThat(t.count("aaaa")).isEqualTo(4);
    assertThat(t.count("aaaaa")).isEqualTo(0);
  }

  @Test
  public void testClear() {

    Trie t = new Trie();

    assertThat(t.insert("a")).isFalse();
    assertThat(t.insert("b")).isFalse();
    assertThat(t.insert("c")).isFalse();

    assertThat(t.contains("a")).isTrue();
    assertThat(t.contains("b")).isTrue();
    assertThat(t.contains("c")).isTrue();

    t.clear();

    assertThat(t.contains("a")).isFalse();
    assertThat(t.contains("b")).isFalse();
    assertThat(t.contains("c")).isFalse();

    t.insert("aaaa");
    t.insert("aaab");
    t.insert("aaab5");
    t.insert("aaac");
    t.insert("aaacb");

    assertThat(t.contains("aaa")).isTrue();
    assertThat(t.contains("aaacb")).isTrue();
    assertThat(t.contains("aaab5")).isTrue();

    t.clear();

    assertThat(t.contains("aaaa")).isFalse();
    assertThat(t.contains("aaab")).isFalse();
    assertThat(t.contains("aaab5")).isFalse();
    assertThat(t.contains("aaac")).isFalse();
    assertThat(t.contains("aaacb")).isFalse();
  }

  @Test
  public void testDelete() {

    Trie t = new Trie();
    t.insert("AAC");
    t.insert("AA");
    t.insert("A");

    assertThat(t.delete("AAC")).isTrue();
    assertThat(t.contains("AAC")).isFalse();
    assertThat(t.contains("AA")).isTrue();
    assertThat(t.contains("A")).isTrue();

    assertThat(t.delete("AA")).isTrue();
    assertThat(t.contains("AAC")).isFalse();
    assertThat(t.contains("AA")).isFalse();
    assertThat(t.contains("A")).isTrue();

    assertThat(t.delete("A")).isTrue();
    assertThat(t.contains("AAC")).isFalse();
    assertThat(t.contains("AA")).isFalse();
    assertThat(t.contains("A")).isFalse();

    t.clear();

    t.insert("AAC");
    t.insert("AA");
    t.insert("A");

    assertThat(t.delete("AA")).isTrue();
    assertThat(t.delete("AA")).isTrue();

    assertThat(t.contains("AAC")).isFalse();
    assertThat(t.contains("AA")).isFalse();
    assertThat(t.contains("A")).isTrue();

    t.clear();

    t.insert("$A");
    t.insert("$B");
    t.insert("$C");

    assertThat(t.delete("$", 3)).isTrue();

    assertThat(t.delete("$")).isFalse();
    assertThat(t.contains("$")).isFalse();
    assertThat(t.contains("$A")).isFalse();
    assertThat(t.contains("$B")).isFalse();
    assertThat(t.contains("$C")).isFalse();
    assertThat(t.delete("$A")).isFalse();
    assertThat(t.delete("$B")).isFalse();
    assertThat(t.delete("$C")).isFalse();

    t.clear();

    t.insert("$A");
    t.insert("$B");
    t.insert("$C");

    assertThat(t.delete("$", 2)).isTrue();
    assertThat(t.delete("$")).isTrue();

    assertThat(t.contains("$")).isFalse();
    assertThat(t.contains("$A")).isFalse();
    assertThat(t.contains("$B")).isFalse();
    assertThat(t.contains("$C")).isFalse();
    assertThat(t.delete("$A")).isFalse();
    assertThat(t.delete("$B")).isFalse();
    assertThat(t.delete("$C")).isFalse();

    t.clear();

    t.insert("$A");
    t.insert("$B");
    t.insert("$C");

    assertThat(t.delete("$", 2)).isTrue();

    assertThat(t.contains("$")).isTrue();
    assertThat(t.contains("$A")).isTrue();
    assertThat(t.contains("$B")).isTrue();
    assertThat(t.contains("$C")).isTrue();
    assertThat(t.delete("$A")).isTrue();
    assertThat(t.delete("$B")).isFalse();
    assertThat(t.delete("$C")).isFalse();

    t.clear();

    t.insert("CAT", 3);
    t.insert("DOG", 3);

    assertThat(t.delete("parrot", 50)).isFalse();

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    assertThat(t.delete("12", 6)).isTrue();
    assertThat(t.delete("12")).isFalse();
    assertThat(t.delete("1")).isFalse();
    assertThat(t.contains("1234")).isFalse();
    assertThat(t.contains("123")).isFalse();
    assertThat(t.contains("12")).isFalse();
    assertThat(t.contains("1")).isFalse();

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    t.delete("12", 999999);

    assertThat(t.contains("1234")).isFalse();
    assertThat(t.contains("123")).isFalse();
    assertThat(t.contains("12")).isFalse();
    assertThat(t.contains("1")).isFalse();

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    t.delete("12", 999999);

    assertThat(t.contains("1234")).isFalse();
    assertThat(t.contains("123")).isFalse();
    assertThat(t.contains("12")).isFalse();
    assertThat(t.contains("1")).isFalse();

    t.clear();

    t.insert("1234");
    t.insert("122", 2);
    t.insert("123", 3);

    assertThat(t.delete("1234")).isTrue();
    assertThat(t.delete("123", 4)).isTrue();
    assertThat(t.delete("122", 2)).isTrue();

    assertThat(t.contains("1")).isFalse();
    assertThat(t.contains("12")).isFalse();
    assertThat(t.contains("122")).isFalse();
    assertThat(t.contains("123")).isFalse();
    assertThat(t.contains("1234")).isFalse();
  }

  @Test
  public void testEdgeCases() {

    Trie t = new Trie();
    assertThat(t.count("")).isEqualTo(0);
    assertThat(t.count("\0")).isEqualTo(0);
    assertThat(t.count("\0\0")).isEqualTo(0);
    assertThat(t.count("\0\0\0")).isEqualTo(0);

    for (char c = 0; c < 128; c++) assertThat(t.count("" + c)).isEqualTo(0);

    assertThat(t.contains("")).isFalse();
    assertThat(t.contains("\0")).isFalse();
    assertThat(t.contains("\0\0")).isFalse();
    assertThat(t.contains("\0\0\0")).isFalse();

    for (char c = 0; c < 128; c++) assertThat(t.contains("" + c)).isFalse();
  }
}
