import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
//Tyson Olychuck
//CodeSprint Jan 2012
//Picking Cards
public class PickingCards {

	//to compute this from an array containing 
	//occurences of a number such that a[2]=3; means that there are three 2's
	//the total ways to pick them up is equal to
	// Occ = ( a[0] ) * (a[0]+a[1]-1)*(a[0]+a[1]+a[2]-2)*....*(a[0]+a[1]+...+a[n]-n)
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		BigInteger MOD = new BigInteger(1000000007 + "");
		int f = scan.nextInt();
		BigInteger total;
		int arr[];
		int nar[];
		boolean print;
		for (int i = 0; i < f; i++) {
			int r = scan.nextInt();					//new Case
			arr = new int[r];
			print = true;
			for (int k = 0; k < r; k++) {
				arr[k] = scan.nextInt();
			}
			total = BigInteger.ZERO;
			Arrays.sort(arr);
			nar = new int[arr.length];
			for (int a = 0; a < arr.length; a++) {	//Check to see if all cards are
				if (arr[a] > a) {					//able to be picked up
					System.out.println(0);			//if no then print 0
					print = false;
					break;
				}

				nar[arr[a]]++;
			}
			if (print) {							//if yes than use the formula at the top
				total = total.add(new BigInteger(String.valueOf(nar[0])));
				for (int a = 1; a < nar.length; a++) {
					nar[a] += nar[a - 1];
					nar[a] -= 1;
					total = total.multiply(new BigInteger(String.valueOf(nar[a])));
					total = total.remainder(MOD);
				}
				System.out.println(total);

			}
		}

	}

}
