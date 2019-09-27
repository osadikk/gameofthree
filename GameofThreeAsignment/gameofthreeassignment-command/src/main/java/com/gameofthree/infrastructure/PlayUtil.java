package com.gameofthree.infrastructure;

public class PlayUtil {

	public static boolean isFinished(int adj, int number) {

		if (isDivisible(adj, number)) {
			return (((adj + number) / 3) == 1);
		}

		return false;
	}

	public static boolean isDivisible(int adj, int number) {
		int sumNumber = adj + number;
		return (((sumNumber) % 3) == 0);
	}

	public static int getDivined(int adj, int number) {

		return ((adj + number) / 3);

	}

}
