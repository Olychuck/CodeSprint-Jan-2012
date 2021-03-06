import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
//Tyson Olychuck
//Olychuck@gmail.com
//Fraud Prevention
public class FraudPrevention {

	//I created two objects for email and address to make it possible to
	//sort and search them for occurrences.Where each occurrence is given
	//the credit card number and order id so they can each detect fraud independently
	
	public static String[] Adressformat(String[] arr) { // formats address
		for (int i = 2; i <= 5; i++) {
			arr[i] = arr[i].toLowerCase();
			arr[i] = arr[i].replace(" ", "");
		}
		if (arr[3].substring(arr[3].length() - 3).equals("st.")) {
			arr[3] = arr[3].substring(0, arr[3].length() - 3) + "street";
		}
		if (arr[3].substring(arr[3].length() - 3).equals("rd.")) {
			arr[3] = arr[3].substring(0, arr[3].length() - 3) + "road";
		}

		if (arr[5].equals("ny"))
			arr[5] = "newyork";
		if (arr[5].equals("ca"))
			arr[5] = "california";
		if (arr[5].equals("il"))
			arr[5] = "illinois";

		return arr;
	}

	public static String[] emailformat(String[] arr) {//formats email
		arr[2] = arr[2].toLowerCase();
		boolean s = false;
		String first = "";
		int ind = 0;
		for (int i = 0; i < arr[2].length(); i++) {
			if (arr[2].charAt(i) == '@') {
				ind = i;
				break;
			}
			if (arr[2].charAt(i) == '+')
				s = true;
			if (!s)
				first = first + arr[2].charAt(i);
		}
		first = first.replace(".", "");
		arr[2] = first + arr[2].substring(ind);

		return arr;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int NUMCASES = scan.nextInt();
		scan.nextLine();
		String[] cur;
		ArrayList<emaildeal> earr = new ArrayList<emaildeal>();		//holds email portion of deal
		ArrayList<addressdeal> adarr = new ArrayList<addressdeal>();//holds address portion of deal
		for (int i = 1; i <= NUMCASES; i++) {
			cur = scan.nextLine().split(",");						//get input by splitting into a string array
			Adressformat(cur);										//and format
			emailformat(cur);
			earr.add(new emaildeal(Integer.parseInt(cur[0]), Integer
					.parseInt(cur[1]), cur[2], Long.parseLong(cur[7])));
			adarr.add(new addressdeal(Integer.parseInt(cur[0]), Integer
					.parseInt(cur[1]), cur, Long.parseLong(cur[7])));
		}
		Collections.sort(earr);
		Collections.sort(adarr);
		int a;
		int d;
		emaildeal ed;
		addressdeal ad;
		ArrayList<Integer> fraud = new ArrayList<Integer>();//holds order number of
		for (int i = 0; i < earr.size(); i++) {				//fraudulent purchases
			ed = earr.remove(i);
			ad = adarr.remove(i);
			a = Collections.binarySearch(earr, ed);
			d = Collections.binarySearch(adarr, ad);
			if (a >= 0) {						//a>=0 means occurence found
				if (earr.get(a).creditcard != ed.creditcard
						&& earr.get(a).dealid == ed.dealid) {
					fraud.add(ed.orderid);		//so add to fraud
				}
			}

			if (d >= 0) {						//d>=0 means occurence found
				if (adarr.get(d).creditcard != ad.creditcard
						&& adarr.get(d).dealid == ad.dealid) {
					fraud.add(ad.orderid);		//so add to fraud
				}
			}
			earr.add(i, ed);
			adarr.add(i, ad);

		}
		Collections.sort(fraud);				//sort for output to be increasing
		int last = fraud.get(0);				//then print
		System.out.print(fraud.get(0));
		for (int i = 1; i < fraud.size(); i++) {
			if (last != fraud.get(i))
				System.out.print("," + fraud.get(i));
			last = fraud.get(i);
		}

	}

	public static class emaildeal implements Comparable<emaildeal> {// the emails portion of the deal
		int orderid;
		int dealid;
		String email;
		long creditcard;

		public emaildeal(int oid, int deaid, String em, long cc) {
			orderid = oid;
			dealid = deaid;
			email = em;
			creditcard = cc;
		}

		public int compareTo(emaildeal o) {
			int base = email.compareTo(o.email);
			if (base == 0) {
				if (o.dealid > dealid)
					return -1;
				if (o.dealid < dealid)
					return 1;
				return 0;
			}
			return base;

		}

		public String toString() {
			return email;
		}

		}

	public static class addressdeal implements Comparable<addressdeal> {//the address's part of the deal
		int orderid;
		int dealid;
		String max;
		long creditcard;

		public addressdeal(int oid, int deaid, String[] arr, long cc) {
			orderid = oid;
			dealid = deaid;
			max = arr[3] + arr[4] + arr[5] + arr[6];
			creditcard = cc;
		}

		public int compareTo(addressdeal o) {
			int base = max.compareTo(o.max);
			if (base == 0) {
				if (o.dealid > dealid)
					return -1;
				if (o.dealid < dealid)
					return 1;
				return 0;
			}
			return base;

		}

		public String toString() {
			return max;
		}

	}
}