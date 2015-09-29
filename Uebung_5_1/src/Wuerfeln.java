public class Wuerfeln {
	public static void main(String[] args) {

		// Anlegen eines Arrays vom Typ int mit 215 Elementen
		int[] augen = new int[216];

		// Z�hlschleife mit 1000 Durchl�ufen
		for (int i = 0; i < 1000; i++) {
			// Dreimal eine zuf�llige Zahl von 1 bis 6 erzeugen
			int wurf1 = Zufall.getZufallInt(1, 6);
			int wurf2 = Zufall.getZufallInt(1, 6);
			int wurf3 = Zufall.getZufallInt(1, 6);

			int ergebnis = wurf1 * wurf2 * wurf3;
			// Wir merken uns an der entsprechenden Position, dass die Zahl
			// gew�rfelt wurde
			// Da unser Array ab 0 z�hlt, m�ssen wir 1 abziehen
			augen[ergebnis - 1] = augen[ergebnis - 1] + 1;
		}

		// Nach den 50 W�rfen geben wir den Inhalt der 216 Felder des Arrays aus
		for (int i = 0; i < 216; i++) {
			System.out.println((i + 1) + ": " + augen[i]);
		}
	}
}
