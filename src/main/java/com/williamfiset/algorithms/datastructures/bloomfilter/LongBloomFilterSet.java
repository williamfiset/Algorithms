package com.williamfiset.algorithms.datastructures.bloomfilter;

public class LongBloomFilterSet extends BloomFilterSet {
    private final long[] bitset;
    private static final long MOD64_MASK = 0x7F;
    private static final long DIV64_SHIFT = 6;

    public LongBloomFilterSet(int size) {
        bitset = new long[size];
    }

    @Override
    public void add(long hash) {
        int block = (int) (hash >> DIV64_SHIFT);
        bitset[block] |= (1L << (hash & MOD64_MASK));
    }

    @Override
    public boolean contains(long hash) {
        int block = (int) (hash >> DIV64_SHIFT);
        long MASK = 1L << (hash & MOD64_MASK);
        return (bitset[block] & MASK) == MASK;
    }

    @Override
    public int getSize() {
        return bitset.length;
    }
}
