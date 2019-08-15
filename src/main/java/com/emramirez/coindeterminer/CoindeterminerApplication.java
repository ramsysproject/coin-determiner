package com.emramirez.coindeterminer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

/**
 * Challenge
 * Have the function CoinDeterminer(num) take the input, which will be an integer ranging from 1 to 250, and return an integer output that will specify the least number of coins, that when added, equal the input integer. Coins are based on a system as follows: there are coins representing the integers 1, 5, 7, 9, and 11. So for example: if num is 16, then the output should be 2 because you can achieve the number 16 with the coins 9 and 7. If num is 25, then the output should be 3 because you can achieve 25 with either 11, 9, and 5 coins or with 9, 9, and 7 coins.
 * Sample Test Cases
 * Input:6
 *
 * Output:2
 *
 * Input:16
 *
 * Output:2
 */
@SpringBootApplication
public class CoindeterminerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoindeterminerApplication.class, args);

		List<Integer> coins = new ArrayList<>(Arrays.asList(1, 5, 7, 9, 11));
		coins.sort(Comparator.reverseOrder());

		Integer input = 21;

		System.out.println(findLeastCoins(coins, input));
	}

	/**
	 * Recursive function to find least amount of coins to cover the given input
	 * @param coins
	 * @param input
	 * @return the least amount of coins necessary
	 */
	public static int findLeastCoins(List<Integer> coins, Integer input) {
		int coinsQuantity = 0;
		int inputCopy = input;
		for(Integer coinValue: coins) {
			if (evaluateSkippingCondition(inputCopy, coinValue)) continue;
			if (evaluateBreakingCondition(inputCopy)) break;

			// If none of the conditions apply, calculate the coins that fits and reduce for next iteration
			Integer remain = inputCopy % coinValue;
			Integer fittingCoins = findFittingCoins(inputCopy, coinValue, remain);
			coinsQuantity += fittingCoins;
			inputCopy -= fittingCoins * coinValue;
		}

		// If we have a remaining amount after all the coins have been evaluated, we need to recalculate
		if (inputCopy > 0) {
			executeRecursion(coins, input);
		}

		return coinsQuantity;
	}

	/**
	 * Calls the recursive function removing the evaluated coin
	 * @param coins
	 * @param input
	 */
	private static void executeRecursion(List<Integer> coins, Integer input) {
		List<Integer> candidateCoins = new ArrayList<>(coins);
		candidateCoins.remove(0);
		findLeastCoins(candidateCoins, input);
	}

	/**
	 * Finds the maximum number of coins of the given value that fits in the amount
	 * @param amount
	 * @param coinValue
	 * @param remain
	 * @return
	 */
	private static Integer findFittingCoins(int amount, Integer coinValue, Integer remain) {
		Integer fittingCoins;
		if (remain == 0) {
			fittingCoins = amount / coinValue;
		} else {
			fittingCoins = (amount - remain) / coinValue;
		}
		return fittingCoins;
	}

	/**
	 * If input is 0, we should break the recursion
	 * @param input
	 * @return
	 */
	private static boolean evaluateBreakingCondition(int input) {
		if (input == 0) {
			return true;
		}
		return false;
	}

	/**
	 * If the evaluated coin value is higher than the input, we should skip the iteration
	 * @param input
	 * @param evaluatedCoin
	 * @return
	 */
	private static boolean evaluateSkippingCondition(int input, Integer evaluatedCoin) {
		if (evaluatedCoin > input) {
			return true;
		}
		return false;
	}

}
