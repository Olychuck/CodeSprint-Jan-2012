import java.math.BigInteger;
import java.util.Scanner;
//Tyson Olychuck
//CodeSprint Jan 2012
//Coin Tosses

public class CoinTosses {

	//the formula to find number of flips with x= the number
	//of required heads and y = the number of already flipped heads
	// is ( 2^(x + 1) - 2^(y + 1))
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int cases = scan.nextInt();
		int x;
		int y;
		BigInteger ret;
		BigInteger twopow;
		for (int a = 0; a < cases; a++) {
			ret = new BigInteger("2");
			twopow = new BigInteger("2");
			x = scan.nextInt();
			y = scan.nextInt();
			ret = ret.pow(x + 1);
			twopow = twopow.pow(y + 1);
			ret = ret.subtract(twopow);
			System.out.println(ret + ".00"); //for some reason print .00 
		}
	}
}