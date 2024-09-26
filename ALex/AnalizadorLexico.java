package Analizador.ALex;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Analizador.Error;
import Analizador.TS.TablaSimbolos;

public class AnalizadorLexico {
	private int c, linea;

	private Map<String, Integer> palRes;
	private MatrizTransicion matriz;
	private FileReader file;
	private TablaSimbolos tsg;
	private TablaSimbolos tsl;
	private boolean tablaActivaTSG;
	private boolean zonaDecl;

	public AnalizadorLexico(FileReader file, FileWriter fileTablaSimbolos, TablaSimbolos tsg)
			throws IOException {
		this.file = file;
		this.c = file.read();

		this.generarTablaPalabrasReservadas();
		this.generarMapaMatriz();
		this.zonaDecl = false;
		// Se guardan las referencias a las tablas de símbolos, local y global,
		// creadas por el Semántico
		this.tsg = tsg;
		this.tablaActivaTSG = true;

		this.linea = 1;
	}

	public void setZonaDecl(boolean b) {
		this.zonaDecl = b;
	}

	public void setEsTSG(boolean n) {
		this.tablaActivaTSG = n;
	}

	public void setTSL(TablaSimbolos tsl) {
		this.tsl = tsl;
	}

	public Token getToken() throws IOException {
		int estado = 0;
		String accion;
		String cadena = "";
		int numero = 0;
		FuturaAccionOError aux;
		while (estado <= 6) {

			aux = getFuturaAccion(estado);

			if (aux == null) {
				// Genera error y termina: caracter no soportado
				estado = Error.lanzarError(200, this.linea, (char) this.c);
				this.c = file.read();
				continue;
			}

			if (aux.isError()) {
				// Genera error y termina
				estado = Error.lanzarError(aux.getNumError(), this.linea);
				// continue;
				return null;
			}

			estado = aux.getSigEstado();
			accion = aux.getAccionSem();

			switch (accion) {
				case "A1": // "="
					c = file.read();
					return new Token(18);
				case "A2": // ","
					c = file.read();
					return new Token(19);
				case "A3": // ";"
					c = file.read();
					return new Token(20);
				case "A4": // ":"
					c = file.read();
					return new Token(21);
				case "A5": // "("
					c = file.read();
					return new Token(22);
				case "A6": // ")"
					c = file.read();
					return new Token(23);
				case "A7": // "{"
					c = file.read();
					return new Token(24);
				case "A8": // "}"
					c = file.read();
					return new Token(25);
				case "A9": // "*"
					c = file.read();
					return new Token(26);
				case "A10": // "!"
					c = file.read();
					return new Token(27);
				case "A11": // ">"
					c = file.read();
					return new Token(28);
				case "A12": // "eof"
					return new Token(30);

				// ----------- CADENAS -----------
				case "C1": // "'"
					c = file.read();
					break;
				case "C2": // Cadena
					cadena = cadena + (char) c;
					c = file.read();
					break;
				case "C3": // "'"
					c = file.read();
					if (cadena.length() > 64)
						estado = Error.lanzarError(103, this.linea);
					else
						return new Token(15, cadena);

					// ----------- ENTEROS -----------
				case "D1": // "d"
					numero = Character.getNumericValue((char) c);
					c = file.read();
					break;
				case "D2": // entero
					numero = numero * 10 + Character.getNumericValue((char) c);
					c = file.read();
					break;
				case "D3": // termina digito
					if (numero > 32767)
						estado = Error.lanzarError(104, this.linea);
					else
						return new Token(14, numero);

					// ----------- += -----------
				case "G1": // "+"
					c = file.read();
					break;
				case "G2": // "="
					c = file.read();
					return new Token(17);

				case "H": // Del
					if (this.c == '\n') {
						this.linea++;
					}
					c = file.read();
					break;
				// ----------- IDENTIFICADORES -----------
				case "I1": // Primer char identificador
					cadena = "" + (char) c;
					c = file.read();
					break;
				case "I3":
					Integer codPalRes = palRes.get(cadena);
					if (codPalRes != null) { // es una palabra reservada
						return new Token(codPalRes);
					} else { // es un identificador normal
						Integer entradaTabla;
						if (this.tablaActivaTSG) {
							entradaTabla = this.tsg.checkTabla(cadena);
							// Variable ya declarada previamente
							if (zonaDecl && entradaTabla != null) {
								Error.lanzarErrorSemantico(616, getLinea(), cadena);
								return null;
							}
							// Declaración implícita
							if (!zonaDecl && entradaTabla == null) {
								entradaTabla = this.tsg.añadirEntrada(cadena);
							}
							// Se está declarando la variable
							if (entradaTabla == null) {
								entradaTabla = this.tsg.añadirEntrada(cadena);
							}
						} else {
							entradaTabla = this.tsl.checkTabla(cadena);
							if (!zonaDecl && entradaTabla == null) {
								entradaTabla = this.tsg.checkTabla(cadena);
							}
							if (zonaDecl && entradaTabla != null) {
								Error.lanzarErrorSemantico(616, getLinea(), cadena);
								return null;
							}
							if (!zonaDecl && entradaTabla == null) {
								entradaTabla = this.tsg.añadirEntrada(cadena);
							}
							if (entradaTabla == null) {
								entradaTabla = this.tsl.añadirEntrada(cadena);
							}
						}
						return new Token(16, entradaTabla);

					}

			}

		}
		return null;

	}

	public int getLinea() {
		return this.linea;
	}

	public FuturaAccionOError getFuturaAccion(int estado) {
		switch (estado) {
			case 0:
				// EOF
				if (c == -1)
					return this.matriz.getAction(estado, 'e');
				// COMIENZO ENTERO
				if (Character.isDigit((char) this.c))
					return this.matriz.getAction(estado, 'd');
				// COMIENZO PR O IDENTIFICADOR
				if (Character.isLetter((char) this.c) || (char) this.c == '_')
					return this.matriz.getAction(estado, 'i');
				// Es ; , = ...
				else
					return this.matriz.getAction(estado, (char) this.c);

			case 1:
				// BUCLE ENTEROS
				if (Character.isDigit((char) this.c))
					return this.matriz.getAction(estado, 'd');
				// O.C
				if (!Character.isDigit((char) this.c))
					return this.matriz.getAction(estado, 'o');

			case 2:
				// ERROR, NO SE CIERRA CADENA
				if (c == -1)
					return this.matriz.getAction(estado, 'e');
				// BUCLE CADENA
				if (this.c != '\'')
					return this.matriz.getAction(estado, 'c');
				// ERROR, NO SE PERMITE SALTO DE LINEA EN CADENA
				if (this.c == '\n')
					return this.matriz.getAction(estado, '\n');
				// FIN CADENA
				else
					return this.matriz.getAction(estado, (char) this.c);

			case 3:
				// BUCLE IDENTIFICADOR
				if (Character.isDigit((char) this.c) || Character.isLetter((char) this.c) || (char) this.c == '_')
					// Es letra, digito o _
					return this.matriz.getAction(estado, 'i');
				else
					// O.C
					return this.matriz.getAction(estado, 'o');

			case 4:
				// ERROR, + DEBE IR SEGUIDO DE =
				if (this.c != '=')
					return this.matriz.getAction(estado, 'o');
				else
					return this.matriz.getAction(estado, (char) this.c);

			case 5:
				// ERROR, COMENTARIO DEBE SER // SEGUIDAS
				if (this.c != '/')
					return this.matriz.getAction(estado, 'o');
				else
					return this.matriz.getAction(estado, (char) this.c);

			case 6:
				if (c == -1)
					return this.matriz.getAction(estado, 'e');
				// BUCLE COMENTARIO
				if (this.c != '\n')
					return this.matriz.getAction(estado, 'o');
				else
					return this.matriz.getAction(estado, (char) this.c);
		}
		return null;

	}

	private void generarMapaMatriz() {
		this.matriz = new MatrizTransicion();

		// ESTADO 0
		this.matriz.addTransition(0, '=', 12, "A1");
		this.matriz.addTransition(0, ',', 13, "A2");
		this.matriz.addTransition(0, ';', 14, "A3");
		this.matriz.addTransition(0, ':', 15, "A4");
		this.matriz.addTransition(0, '(', 16, "A5");
		this.matriz.addTransition(0, ')', 17, "A6");
		this.matriz.addTransition(0, '{', 18, "A7");
		this.matriz.addTransition(0, '}', 19, "A8");
		this.matriz.addTransition(0, '*', 9, "A9");
		this.matriz.addTransition(0, '!', 10, "A10");
		this.matriz.addTransition(0, '>', 11, "A11");
		this.matriz.addTransition(0, '+', 4, "G1");
		this.matriz.addTransition(0, '/', 5, "H");
		this.matriz.addTransition(0, 'e', 20, "A12"); // eof
		this.matriz.addTransition(0, '\n', 0, "H"); // del
		this.matriz.addTransition(0, ' ', 0, "H"); // del
		this.matriz.addTransition(0, '\t', 0, "H"); // del
		this.matriz.addTransition(0, 'd', 1, "D1"); // digito **
		this.matriz.addTransition(0, '\'', 2, "C1"); // cadena
		this.matriz.addTransition(0, 'i', 3, "I1"); // identificador (si es letra o _)
		this.matriz.addTransition(0, 'o', 200);

		// ESTADO 1 : Numeros enteros
		this.matriz.addTransition(1, 'd', 1, "D2"); // Es digito
		this.matriz.addTransition(1, 'o', 21, "D3"); // Es o.c

		// ESTADO 2: Cadena
		this.matriz.addTransition(2, 'c', 2, "C2"); // Cualquier char
		this.matriz.addTransition(2, '\'', 22, "C3");
		this.matriz.addTransition(2, 'e', 100); // Si no se cierra la cadena con ', error
		this.matriz.addTransition(2, '\n', 101); // Salto de linea, error

		// ESTADO 3: Identificador
		this.matriz.addTransition(3, 'i', 3, "C2"); // Bucle de estado 3
		this.matriz.addTransition(3, 'o', 7, "I3"); // Termina id
		// this.matriz.addTransition(3, 'e', 7, "I3"); // Termina id

		// ESTADO 4: +=
		this.matriz.addTransition(4, '=', 8, "G2");
		this.matriz.addTransition(4, 'o', 102); // Si el + no va seguido de =, error

		// ESTADO 5: Comentario
		this.matriz.addTransition(5, '/', 6, "H");
		this.matriz.addTransition(5, 'o', 105);

		// ESTADO 6: Comentario
		this.matriz.addTransition(6, 'o', 6, "H");
		this.matriz.addTransition(6, '\n', 0, "H");
		this.matriz.addTransition(6, 'e', 0, "J");

	}

	private void generarTablaPalabrasReservadas() {
		String[] palabrasReservadas = { "boolean", "break", "case", "function", "get", "if", "int", "let", "put",
				"return", "string", "switch", "void", "default" };
		int[] codigoPalRes = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 29 };
		this.palRes = new HashMap<String, Integer>();
		for (int i = 0; i < palabrasReservadas.length; i++) {
			this.palRes.put(palabrasReservadas[i], codigoPalRes[i]);
		}
	}
}
