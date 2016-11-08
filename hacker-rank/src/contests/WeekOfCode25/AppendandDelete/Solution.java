package contests.WeekOfCode25.AppendandDelete;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

	public static boolean verifyValue = false;

	public static void main(String[] args) throws FileNotFoundException {

		File fileOutput = null;
		File fileInput = new File("./TEST/contests/AppendandDelete/append-and-delete-testcases/input/input01.txt");
		fileOutput = new File("./TEST/contests/AppendandDelete/append-and-delete-testcases/output/output01.txt");
		Scanner in = new Scanner(fileInput);
		// Scanner in = new Scanner(System.in);

		String line;

		String S = "";
		String T = "";

		S = in.nextLine();
		T = in.nextLine();

		int K = in.nextInt();

		String total = canConvert(S, T, K);

		if (verifyValue) {
			in = new Scanner(fileOutput);
			line = in.nextLine();
			if (line.equals(total))
				System.out.println("[CORRECT] " + total);
			else
				System.out.println("[WRONG] my value:" + total + ", expected value: " + line);
		} else {
			System.out.println(total);
		}
	}

	public static String canConvert(String S, String T, int K) {

		if (S.length() == 0 && T.length() == 0)
			return "Yes";

		int i = 0;
		for (i = 0; i < S.length() && i < T.length(); ++i) {
			if (S.charAt(i) != T.charAt(i))
				break;
		}

		int remS = S.length() - i;
		int remT = T.length() - i;

		// if the two strings are equals
		if (remS == remT) {
			K -= remS;
			if (K == 0)
				return "No";
		} else {
			K -= remS;
			if (remS < remT) {
				if (K == 0)
					return "No";
			} else {
				if (K <= 0)
					return "No";
			}
		}

		if (K < remT)
			return "No";
		else {
			if (K == remT)
				return "Yes";
			else {
				K -= remT;
				if (K == 0)
					return "Yes";
				else {
					if (K % 2 == 0)
						return "Yes";
					else { // aba / aba 7
						if (remT == 0) {
							if (K % 2 == 0)
								return "Yes";
							else { // aba / aba 7
								if (i == 0)
									return "Yes";
								K -= S.length();
								if (K < S.length())
									return "No";
								if (K % 2 == 0)
									return "Yes";
								else
									return "No";
							}
						} else { // abc / add 7
							if (i == 0)
								return "Yes";
							K -= T.length();
							if (K < 0)
								return "No";
							return "Yes";
						}
					}

				}
			}
		}
	}
}