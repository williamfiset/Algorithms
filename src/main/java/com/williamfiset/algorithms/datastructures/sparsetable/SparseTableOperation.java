package com.williamfiset.algorithms.datastructures.sparsetable;

abstract class SparseTableOperation {
    abstract long apply(long a, long b);
}

class SparseTableMinOp extends SparseTableOperation {
    @Override
    long apply(long a, long b) {
        return Math.min(a, b);
    }
}

class SparseTableMaxOp extends SparseTableOperation {
    @Override
    long apply(long a, long b) {
        return Math.max(a, b);
    }
}

class SparseTableSumOp extends SparseTableOperation {
    @Override
    long apply(long a, long b) {
        return a + b;
    }
}

class SparseTableMultOp extends SparseTableOperation {
    @Override
    long apply(long a, long b) {
        return a * b;
    }
}

class SparseTableGcdOp extends SparseTableOperation {
    @Override
    long apply(long a, long b) {
        long gcd = a;
        while (b != 0) {
            gcd = b;
            b = a % b;
            a = gcd;
        }
        return Math.abs(gcd);
    }
}