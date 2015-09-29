public class Wuerfeln {
	public static void main(String[] args) {

		// Anlegen eines Arrays vom Typ int mit 6 Elementen
		int[] augen = new int[6];

		// Z�hlschleife mit 50 Durchl�ufen
		for (int i = 0; i < 50; i++) {
			// Eine zuf�llige Zahl von 1 bis 6 erzeugen
			int wurf = Zufall.getZufallInt(1, 6);

			// Wir merken uns an der entsprechenden Position, dass die Zahl
			// gew�rfelt wurde
			// Da unser Array ab 0 z�hlt, m�ssen wir 1 abziehen
			augen[wurf - 1]++;
		}

		// Nach den 50 W�rfen geben wir den Inhalt der 6 Felder des Arrays aus
		for (int i = 0; i < augen.length; i++) {
			System.out.println((i + 1) + ": " + augen[i]);
		}
	}
}
