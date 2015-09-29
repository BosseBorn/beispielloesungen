package blackjack.karten;

import blackjack.Konstanten;

/**
 * Objekte dieser Klasse repr�sentieren das Blatt eines Spielers. Es ist im
 * Wesentlichen eine Ansammlung von Spielkarten.
 * 
 * @author Malte Knutz
 */
public class Kartenhand {

	/**
	 * In diesem Array werden die Karten auf der Kartenhand gespeichert. Es wird
	 * ausreichend gro� angelegt werden, dass es nie Gr��enprobleme gibt.
	 * Gef�llt wird es aufsteigend.
	 */
	private Spielkarte[] kartenAufHand;

	/**
	 * Erzeugt eine neue Kartenhand ohne Karten.
	 * 
	 */
	public Kartenhand() {
		// Wir machen das Array so gro�, wie es Spielkarten geben kann. Dann
		// kann nix passieren
		kartenAufHand = new Spielkarte[Spielkarte.ermittleGueltigeFarben().length
				* Spielkarte.ermittleGueltigeKartenbezeichnungen().length];
	}

	/**
	 * Nimmt von dem �bergebenen Kartenstapel die oberste Karte und f�gt Sie
	 * dieser Kartenhand hinzu.
	 * 
	 * @param stapel
	 *            Der Kartenstapel, von dem die oberste Karte genommen wird.
	 */
	public void nimmKarte(Spielkartenstapel stapel) {
		Spielkarte karte = stapel.getObersteKarte();
		// Nachsehen, wo der erste freie Platz f�r eine Karte ist
		// (wenn wir 5 Karten auf der Hand haben, sind Indizees 0...4 belegt und
		// 5 frei)
		int freieStelle = getAnzahlKartenAufHand();
		// und die Karte merken
		kartenAufHand[freieStelle] = karte;
	}

	/**
	 * Ermittelt den Punktwert der Karten auf der Hand. Dabei werden die Asse so
	 * gez�hlt, dass es f�r den Spieler das optimalste Ergebnis ergibt.
	 * Beispiele: Eine 9 und ein Ass w�rden als 20 Punkte gez�hlt werden, Zwei
	 * 9en und ein Ass 19 Punkte und eine 9 und zwei Asse als 21 Punkte.
	 * 
	 * @return den optimalsten Punktwert der Kartenhand
	 */
	public int ermittleWertOptimal() {
		// Vorgehen: wir z�hlen erstmal nur die Werte der Nicht-Asse und
		// wieviele Asse es gibt
		int wertOhneAsse = 0;
		int anzahlAsse = 0;
		for (int i = 0; i < getAnzahlKartenAufHand(); i++) {
			if (kartenAufHand[i].istAss()) {
				anzahlAsse++;
			} else {
				wertOhneAsse = wertOhneAsse + kartenAufHand[i].getBasiswert();
			}
		}
		// Jetzt wird eine Liste aller irgendwie m�glichen Kartenhandwerte
		// erstellt.
		// Zuerst die Frage: Wieviele M�glichkeiten gibt es bei 1, 2, 3 oder 4
		// Assen?
		// L�sung: Bei 1 Ass kann man es mit 1 oder 11 z�hlen (2 M�glichkeiten)
		// Bei 2 Assen kann man keins, eins oder beide mit 1 z�hlen und den Rest
		// mit 11 (3 M�glichkeiten)
		// Bei 3 Assen kann man keins, eins, zwei oder alle mit 1 z�hlen und den
		// Rest mit 11 (4 M�glichkeiten)
		// Bei 4 Assen kann man keins, eins, zwei, drei oder alle mit 1 z�hlen
		// und den Rest mit 11 (5 M�glichkeiten)
		int[] listeAllerMoeglichenWerte = new int[anzahlAsse + 1];
		// Nun muss die Liste noch gef�llt werden.
		for (int i = 0; i < listeAllerMoeglichenWerte.length; i++) {
			// F�r den 0. eintrag in der Liste z�hlen wir 0 Asse mit 1 und und
			// den Rest mit 11
			// F�r den 1. eintrag in der Liste z�hlen wir 1 Asse mit 1 und und
			// den Rest mit 11 usw
			int asseMitEinsGezaehlt = i;
			int asseMitElfGezaehlt = anzahlAsse - asseMitEinsGezaehlt;
			int wertAsse = asseMitEinsGezaehlt + (asseMitElfGezaehlt * 11);

			listeAllerMoeglichenWerte[i] = wertOhneAsse + wertAsse;
		}

		// Nun haben wir eine Liste aller Varianten, mit denen man theoretisch
		// z�hlen kann.
		// Wir m�ssen nur noch die beste ausw�hlen. Dabei soll noch
		// ber�cksichtigt werden,
		// dass z.B. 22 besser ist als 32 (zwar ist beides irgendwie nix wert,
		// doch dem Anwender vorzurechnen
		// dass {Zwei Ass Ass Acht Bube} als 32 gez�hlt wird ist Mist)
		// Wir nehmen dazu erstmal den ersten Eintrag und sehen nach, was man
		// besseres findet
		int wert = listeAllerMoeglichenWerte[0];
		for (int alternativerWert : listeAllerMoeglichenWerte) {
			// wir haben also 2 Werte zur Auswahl: "wert" und "alternativerWert"
			// und m�ssen, wenn
			// "alternativerWert" besser ist, Ihn unter "wert" ablegen

			// Gr�nde daf�r, dass "alternativerWert" besser ist:
			// 1. Wenn der bisher beste wert > 21 ist und wir mit
			// "alternativerWert" unter 22 kommen, nehmen wir nat�rlich den!
			boolean alternativerWertKommtUnter21 = wert > Konstanten.ZIELWERT && alternativerWert <= Konstanten.ZIELWERT;
			// 2. Wenn beide unter 22 liegen, nehmen wir "alternativerWert",
			// wenn er gr��er als "wert" ist.
			boolean beideUnter22UndAlternativerWertGroe�er = wert <= Konstanten.ZIELWERT
					&& alternativerWert <= Konstanten.ZIELWERT && alternativerWert > wert;
			// 3. Wenn beide �ber 21 liegen, nehmen wir "alternativerWert", wenn
			// er kleiner als "wert" ist.
			boolean beideUeber21UndAlternativerWertKleiner = wert > Konstanten.ZIELWERT
					&& alternativerWert > Konstanten.ZIELWERT && alternativerWert < wert;

			if (alternativerWertKommtUnter21 || beideUnter22UndAlternativerWertGroe�er
					|| beideUeber21UndAlternativerWertKleiner) {
				wert = alternativerWert;
			}

		}

		// Nun ist alles klar. In Wert steht die beste Z�hlvariante
		return wert;

	}

	/**
	 * Ermittelt den Punktwert der Karten auf der Hand. Dabei werden alle Asse
	 * mit 11 Punkten gez�hlt.
	 * 
	 * @return Den Punktwert der Karten auf der Hand, wobei jedes Ass 11 z�hlt
	 */
	public int ermittleWertJedesAssZaehlt11() {
		int wert = 0;
		for (int i = 0; i < getAnzahlKartenAufHand(); i++) {
			wert = wert + kartenAufHand[i].getBasiswert();
		}
		return wert;
	}

	private int getAnzahlKartenAufHand() {
		// den ersten Platz(index) suchen, der nicht belegt ist
		// Bei Index 0 anfangen
		int index = 0;
		// Solange index hochz�hlen, wie die entsprechenden Stelle nicht leer
		// ist
		while (kartenAufHand[index] != null) {
			index++;
		}
		// Nach der Schleife hat Index den Wert der ersten leeren Stelle
		// (was gleichzeitig die Gr��e ist)
		return index;
	}

	/**
	 * Liefert eine Textdarstellung der Kartenhand.
	 * 
	 * @return eine Textdarstellung der Kartenhand
	 */
	public String getTextdarstellung() {
		int anzahl = getAnzahlKartenAufHand();
		if (anzahl == 0) {
			return "Keine Karten auf der Hand";
		}
		// mind. eine Karte ist auf der Hand
		String text = kartenAufHand[0].getTextdarstellung();
		// Alle weiteren evtl. vorhandenen Karten anh�ngen
		for (int i = 1; i < anzahl; i++) {
			text = text + " | " + kartenAufHand[i].getTextdarstellung();
		}
		text = text + " = " + ermittleWertOptimal() + " Punkte";
		return text;
	}

}
