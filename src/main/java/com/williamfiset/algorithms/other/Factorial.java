package com.williamfiset.algorithms.other;

public class Factorial {

	public static int calcFactorial(int limit) {
		int factorial = 1;
		for (int i = 1; i <= limit; i++) {
			factorial *= i;
		}
		return factorial;
	}

	public static void main(String[] args) {
		int limit = 10;

		System.out.printf("%d! = %d", limit, calcFactorial(limit));
		System.out.println("");
	}
}
