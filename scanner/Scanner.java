// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

//PK: henter alt av referanser fra pakken scanner og main og importerer andre java instrumenter
package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;
import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

// PK: klassen scanner | alle variablene er også en del av PK
public class Scanner {
	private LineNumberReader sourceFile = null; // klasse som tar imot filer og holder styr på hvor i linjen vi befinne
												// oss
	private String curFileName; // den nåværende navnet på filen vi håndterer
	private ArrayList<Token> curLineTokens = new ArrayList<>(); // en liste av alle Tokens'a i en gitt linje
	private Stack<Integer> indents = new Stack<>(); // En stack for å holde styr over indents og dedents
													// og den er av typen Integer for å holde styr hvor mange ganger vi
													// har indentet
	private final int TABDIST = 4; // distansen til en enkel tab utgjør 4 av space sin distanse -- utgitt som en
									// variabel

	// PK: konsturktøren til Scanner klassen som har en String parameter som tar
	// imot navnet på en fil
	public Scanner(String fileName) {
		curFileName = fileName; // gjør om curFileName om til den oppgitte filnavnet i konstruktøren
		indents.push(0); //

		// hele try-catch blokket er for å sjekke og håndtere om det blir oppgitt en
		// ugyldig fil
		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
							new FileInputStream(fileName),
							"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
	}

	// PK: metode gir tilbake en melding basert på oppgitt argument og posisjonen
	// til hvor
	// errorern befinner seg
	private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
	}

	// PK: returnerer den første tokenen i tokenlisten så lenge listen ikke er tom;
	// hvis den skulle være det går den videre til neste linje i filen
	public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
	}

	// PK: går videre til neste token i listen, men først fjerner den nåværende
	// første token i tokenlisten
	public void readNextToken() {
		if (!curLineTokens.isEmpty())
			curLineTokens.remove(0);
	}

	// hensikten med denne metoden er å sette de tokensene i en linje i
	// curlinetokens listen
	private void readNextLine() {

		// leser inn den nåværende linjen med kode og gjør det om til en tom String
		curLineTokens.clear();
		String line = null;
		//
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();

				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		// Ved slutten av filen:
		// Hvis det er tellt i forhånd at det finnes noen indents, skal de poppes
		// og det skal settes inn dedentTokens i curLineTokens, hvis linjen skulle være
		// tom
		// Dermed skal det settes en eofToken i curLineTokens før vi kjører
		// -testScanner, med Main.log.noteToken(n)
		// metoden readNextLine er rekursiv og det er derfor return er så høyt oppe i
		// koden. Det er basistilfellet.
		if (line == null) {
			if (indents.peek() > 0) {
				for (Integer dents : indents) {
					indents.pop();
					curLineTokens.add(new Token(dedentToken, curLineNum()));
				}
			}
			curLineTokens.add(new Token(eofToken, curLineNum()));
			for (Token n : curLineTokens) {

				Main.log.noteToken(n);
			}
			return;

		}

		// Koden herfra i metoden er hvis basistilfellet ikke stemmer

		// Fikser om riktig indentering for koden i næværende linje hvis det skulle
		// trenges
		// n her er en variabel som holder styr på hvor mange spaces det fantes i linjen
		// før starten av selve koden
		// VÆR OPPS!!! findIndent() teller antall spaces og ikke indents som man ville
		// ha trodd ut fra navnet,
		// men det har fått beholdt det navnet siden det var sånn den het i PK.
		String linje = this.expandLeadingTabs(line);
		int n = findIndent(expandLeadingTabs(line));

		// teller antall tabs det er i koden etter at indentingen er fikset på den, med
		// ant_dents.
		// hvis linjen er en kommentar (merket med symbolet #) hopper vi over hele
		// linjen
		// hvis det er regnet at det finnes flere tabs i linjen en det er i
		// indets-stacken,
		// aderes en indentToken i curLineToken,
		// hvis ikke popes det fra indent-stacken og det aderes en dedentToken
		// og det her gjentar seg til indents-stacken er stemmer med antall indents det
		// er i næværende kode.
		int ant_dents = n / 4;
		if (!linje.trim().isEmpty()) {
			if (linje.charAt(n) != '#') {

				if (ant_dents > indents.peek()) {
					indents.push(ant_dents);
					Token ind = new Token(indentToken, curLineNum());
					curLineTokens.add(ind);
					Main.log.noteToken(ind);

				} else {
					while (ant_dents < indents.peek()) {
						indents.pop();
						Token ind = new Token(dedentToken, curLineNum());
						curLineTokens.add(ind);
						Main.log.noteToken(ind);
					}
				}
			}
		}

		// Variabel for å sjekke 'kode-ord' for 'kode-ord' i en linje
		String symbol = "";

		// Med for-loopen itereres det gjennom kode-linjen char for char
		for (int c = 0; c < linje.length(); c++) {
			// Hopp over spaces i en linje
			if (linje.charAt(c) == ' ') {
				c++;
			}
			// Hopp over hele linjen hvis det er et kommentar
			if (linje.charAt(c) == '#') {
				break;
			}

			// NAMETOKEN && ANDRE TOKENS / NØKKELORD (Keywords)
			else if (this.isLetterAZ(linje.charAt(c)) == true) {
				int i = c;
				// finner ut hva den nåværende kode-ordet er
				while (i < linje.length() && (this.isLetterAZ(linje.charAt(i)) || this.isDigit(linje.charAt(i)))) {
					symbol += linje.charAt(i);
					i++;

				}

				c = i;

				// sjekker om kode-ordet tilsvarer noen av tokens'a fra TokenKind klassen fra og
				// med andToken til og med yieldToken
				// Hvis det skulle stemme adderer vi den tilsvarende tokenet til curLineTokens
				for (TokenKind tk : EnumSet.range(TokenKind.andToken, TokenKind.yieldToken)) {
					if (symbol.equals(tk.image)) {
						Token token = new Token(tk, curLineNum());
						token.name = symbol;
						curLineTokens.add(token);
						Main.log.noteToken(token);
						symbol = "";
						c--;
						break;
					}

				}
				// Hvis symbolet / kode-ordet tilsvarer ingen tokens'a i EnumSet-rangen så må
				// den da tilsvare en nameToken altså en variabel
				if (!symbol.equals("")) {
					Token navn_token = (new Token(nameToken, curLineNum()));
					navn_token.name = symbol;
					curLineTokens.add(navn_token);
					Main.log.noteToken(navn_token);
					symbol = "";
					c--;
				}

				// INT TOKEN
			} else if (this.isDigit(linje.charAt(c)) == true) {
				int i = c + 1;
				Token numberToken;
				symbol += linje.charAt(c);

				// Finnner ut hva den nåværende nummeret er siffer for siffer
				while (i < linje.length() && this.isDigit(linje.charAt(i))) {
					symbol += linje.charAt(i);
					i++;
				}

				// Hvis vi oppdager en '.' og det etterfølges av flere sifferet er den nåværende
				// tallet et float
				// Og det adderes i curLineToken. Hvis det skulle oppdages feilformatering for
				// man en error.
				if (i < linje.length() && linje.charAt(i) == ('.')) {
					symbol += linje.charAt(i);
					i++;
					if (this.isDigit(linje.charAt(i))) {
						while (i < linje.length() && this.isDigit(linje.charAt(i))) {
							symbol += linje.charAt(i);
							i++;
						}
						numberToken = new Token(floatToken, curLineNum());
						numberToken.floatLit = Double.valueOf(symbol);
						curLineTokens.add(numberToken);
						Main.log.noteToken(numberToken);
						symbol = "";
						c = i - 1;
						continue;

					} else if (!this.isDigit(linje.charAt(i))) {
						scannerError("Feil formatering");
					}
				}

				// Hvis det ikke oppdages noe '.' så adderes det en integerToken til
				// curLineTokens
				c = i - 1;
				numberToken = new Token(integerToken, curLineNum());
				numberToken.integerLit = Long.valueOf(symbol);
				curLineTokens.add(numberToken);
				Main.log.noteToken(numberToken);
				symbol = "";
			}

			// STRING TOKEN
			// i asp kan string verdier deklareres på to mmåter enten ordet omringet med " "
			// eller ' '
			// med en gang det oppdages en av de string-markeringene settes alle symbolene i
			// symbol til det oppdages en annen string-markering av samme type.
			else if (linje.charAt(c) == '"') {
				int i = c;
				// symbol += ' ';

				while (linje.charAt(i + 1) != '"') {
					symbol += linje.charAt(i + 1);
					i++;
				}
				// symbol += ' ';
				Token strToken = new Token(stringToken, curLineNum());

				strToken.stringLit = symbol;
				symbol = "";
				curLineTokens.add(strToken);
				Main.log.noteToken(strToken);
				c = i + 1;

			} else if (linje.charAt(c) == '\'') {
				int i = c;
				// symbol += "'";

				while (linje.charAt(i + 1) != '\'') {
					symbol += linje.charAt(i + 1);
					i++;
					if (i + 1 == linje.length()) {
						scannerError("Feil formatering av String");
					}
				}

				Token strToken = new Token(stringToken, curLineNum());
				strToken.stringLit = symbol;
				curLineTokens.add(strToken);
				Main.log.noteToken(strToken);

				c = i + 1;

				// Operatorer
				// Operatorer vil bestå av enten to eller en symbol
				// sjekk symbol med tokenKinds? hvis ja sett den inn i curLineTokens
			} else if (c + 1 < linje.length()) {
				symbol += linje.charAt(c);
				symbol += linje.charAt(c + 1);
				for (TokenKind tk : EnumSet.range(TokenKind.astToken, TokenKind.semicolonToken)) {
					if (symbol.equals(tk.image)) {
						Token token = new Token(tk, curLineNum());
						token.name = symbol;
						curLineTokens.add(token);
						Main.log.noteToken(token);
						symbol = "";
						c = c + 2;
						break;
					}
				}
				symbol = "";

				for (TokenKind tk : EnumSet.range(TokenKind.astToken, TokenKind.semicolonToken)) {
					String enkel = "";
					if (c < linje.length()) {
						enkel += linje.charAt(c);
						if (enkel.equals(tk.image)) {
							Token token = new Token(tk, curLineNum());
							token.name = enkel;
							curLineTokens.add(token);
							Main.log.noteToken(token);
							enkel = "";
							break;
						}
					}
				}

			} else if (c == linje.length() - 1) {
				String siste = "";
				siste += linje.charAt(c);
				for (TokenKind tk : EnumSet.range(TokenKind.astToken, TokenKind.semicolonToken)) {
					if (siste.equals(tk.image)) {
						Token token = new Token(tk, curLineNum());
						token.name = siste;
						curLineTokens.add(token);
						Main.log.noteToken(token);
						siste = "";
					}
				}
			}

		}

		// Terminate line:
		if (curLineTokens.size() != 0) {
			Token new_line = new Token(newLineToken, curLineNum());
			new_line.name = "Newline";
			curLineTokens.add(new_line);
			Main.log.noteToken(new_line);
		}

	}

	// returnerer nåværende linje nummer
	public int curLineNum() {
		return sourceFile != null ? sourceFile.getLineNumber() : 0;
	}

	// leser ikke indents men antall space for stringen s
	// viser antall indents
	private int findIndent(String s) {
		int indent = 0;

		while (indent < s.length() && s.charAt(indent) == ' ')
			indent++;
		return indent;
	}

	// leser en linje med kode og redigerer den med riktig indentering hvis det
	// skulle være nødvendig
	private String expandLeadingTabs(String s) {

		String new_s = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\t') {
				new_s += " ";
				while (new_s.length() % 4 != 0) {
					new_s += " ";
				}
			} else if (s.charAt(i) == ' ') {
				new_s += " ";
			} else {
				while (new_s.length() % 4 != 0) {
					new_s += " ";
				}
				new_s += s.substring(i, s.length());
				break;
			}
		}

		return new_s;
	}

	private boolean isLetterAZ(int i) {
		return ('A' <= i && i <= 'Z') || ('a' <= i && i <= 'z') || (i == '_');
	}

	private boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}

	public boolean isCompOpr() {
		switch (this.curToken().kind) {
			case lessToken:
			case greaterToken:
			case doubleEqualToken:
			case greaterEqualToken:
			case lessEqualToken:
			case notEqualToken:
				return true;
			default:
				return false;
		}
	}

	public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		return false;
	}

	public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		switch (this.curToken().kind) {
			case astToken:
			case slashToken:
			case percentToken:
			case doubleSlashToken:
				return true;
			default:
				return false;
		}
	}

	public boolean isTermOpr() {
		switch (this.curToken().kind) {
			case plusToken:
			case minusToken:
				return true;
			default:
				return false;
		}

	}

	public boolean anyEqualToken() {
		for (Token t : curLineTokens) {
			if (t.kind == equalToken)
				return true;
			if (t.kind == semicolonToken)
				return false;
		}
		return false;
	}

}
