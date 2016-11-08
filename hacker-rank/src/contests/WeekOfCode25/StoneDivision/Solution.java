package contests.WeekOfCode25.StoneDivision;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {
	static long[] s;
	static int m;
	static long n;
	static HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();

	public static void main(String[] args) throws FileNotFoundException {

		File fileInput = new File("./TEST/contests/WeekOfCode25/StoneDivision/input/input00.txt");
		Scanner in = new Scanner(fileInput);
		n = in.nextLong();
		m = in.nextInt();
		s = new long[m];
		for (int i = 0; i < m; ++i)
			s[i] = in.nextLong();
		
		System.out.println(find(n) ? "First" : "Second");
	}

	static boolean find(long x) {
		if (hm.containsKey(x))
			return hm.get(x);
		
		for (int i = 0; i < m; ++i) {
			if (x % s[i] == 0) {
				boolean b = find(x / s[i]);
				if (!b || (x / s[i]) % 2 == 0) {
					hm.put(x, true);
					return true;
				}
			}
		}
		hm.put(x, false);
		return false;
	}
}