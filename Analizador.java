package Analizador;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Analizador.ASin.AnalizadorSintactico;

public class Analizador {

	public static void main(String[] args) throws IOException {
		if (args.length != 4) {
			System.out
					.println("Uso: java -jar PDL.jar nombreFichFuente nombreFichTokens nombreFichTS nombreFichParse ");
			System.exit(1);
		}

		FileReader fileIn = new FileReader(args[0]);
		FileWriter fileTokens = new FileWriter(args[1]);
		FileWriter fileTablaSimbolos = new FileWriter(args[2]);
		FileWriter fileParse = new FileWriter(args[3]);

		AnalizadorSintactico analizadorSin = new AnalizadorSintactico(fileIn, fileTokens, fileTablaSimbolos, fileParse);
		analizadorSin.execAnalizadorSintactico();
		fileIn.close();
		fileTokens.close();
		fileParse.close();
		fileTablaSimbolos.close();
	}
}
