public class BinomischeFormel {

	public static void main(String[] args) {
		double a = 8;
		double b = 3;

		// (a+b)� = a� + 2ab + b�
		double binomLinks = Math.pow((a + b), 2);
		double binomRechts = Math.pow(a, 2) + 2 * a * b + Math.pow(b, 2);

		System.out.println("(a+b)� = " + binomLinks);
		System.out.println("a� + 2ab + b� = " + binomRechts);
	}

}
