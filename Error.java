package Analizador;

import Analizador.ALex.Token;

public class Error {

    public static int lanzarError(int codError, int linea, char c) {
        System.err.println("Error léxico en la línea: " + linea + ". Código " + codError + ": El carácter "
                + c + " no está permitido");
        return 0;

    }

    public static int lanzarError(int codError, int linea) {
        switch (codError) {
            case 100:
                System.err.println("Error léxico en la línea: " + linea + ". Cadena no cerrada correctamente, falta '");
                break;
            case 101:
                System.err.println(
                        "Error léxico en la línea: " + linea + ". No se permiten saltos de línea en las cadenas");
                break;
            case 102:
                System.err.println("Error léxico en la línea: " + linea
                        + ". El operador '+' no está permitido. A lo mejor querías decir '+='");
                break;
            case 103:
                System.err.println(
                        "Error léxico en la línea: " + linea + ". El tamaño máximo de la cadena es de 64 caracteres");
                break;
            case 104:
                System.err.println(
                        "Error léxico en la línea: " + linea + ". Has rebasado el tamaño máximo de entero (32767)");
                break;
            case 105:
                System.err.println("Error léxico en la línea: " + linea + ". El comentario debe comenzar por //");
                break;
        }

        return 0;

    }

    public static void lanzarError(int codError, int linea, Token prevTok, Token currTok) {
        String prevToken = obtenerEquivalencia(prevTok);
        String currToken = obtenerEquivalencia(currTok);
        switch (codError) {
            case 300:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". El programa no puede comenzar por '" + prevToken
                                + "'");
                break;

            case 302:
                System.err.println("Error sintáctico en la línea: " + linea + ". No puede ir '" + prevToken
                        + "' nada más terminar una sentencia.");
                break;

            case 303:
                System.err.println("Error sintáctico en la línea: " + linea + ". No puede ir '" + prevToken
                        + "' nada más terminar una función.");
                break;

            case 306:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de 'let' debe ir un identificador");
                break;

            case 309:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un '{'");
                break;

            case 310:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un '=', '+=' o '('");
                break;

            case 312:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de 'get' debe ir un identificador");
                break;

            case 313:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de 'return' debe ir una expresión o ';'");
                break;

            case 316:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de 'function' debe ir un identificador");
                break;

            case 317:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' no debe haber nada.");
                break;

            case 318:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' no debe haber nada.");
                break;

            case 320:
                System.err.println("Error sintáctico en la línea: " + linea
                        + ". Después de un identificador debe ir 'int', 'boolean' o 'string'");
                break;

            case 322:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir una o más sentencias o '}'");
                break;

            case 325:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un ')' o una lista de argumentos.");
                break;

            case 326:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un ';' o '>'");
                break;

            case 329:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un '(', una cadena, un entero o un identificador");
                break;
            case 331:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un '*', ',', ';', '>', '(' o ')'");
                break;
            case 337:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un  ';' o '>'");
                break;
            case 339:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un  'int', 'boolean', 'string' o 'void'");
                break;
            case 340:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un  'int', 'boolean', 'string' o 'void'");
                break;
            case 341:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un  ')' o '>'");
                break;
            case 343:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un  ';', '(' o un identificador");
                break;
            case 344:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un  ';', '(' o un identificador");
                break;
            case 345:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un  ';', '(' o un identificador");
                break;
            case 346:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un  ')' o '>'");
                break;
            case 347:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un  '}'");
                break;

            case 348:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir una o más sentencias o '}'");
                break;
            case 349:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un  ';' o '>'");
                break;
            case 350:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un  ';' o '>'");
                break;

            case 352:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un ',', '>' o ')'");
                break;

            case 354:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir una operación aritmética (*), lógica (!), un identificador, '(', un entero, o una cadena");
                break;

            case 355:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir una operación lógica (!), un identificador, '(', un entero, o una cadena");
                break;

            case 357:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un ')' o una lista de argumentos.");
                break;

            case 358:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un  ')' o '>'");
                break;
            case 362:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un identificador");
                break;
            case 367:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un identificador, 'put', 'get', 'return' o 'break'");
                break;
            case 369:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un '{'");
                break;
            case 371:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un '}'");
                break;
            case 381:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un '{'");
                break;
            case 382:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir una ',' o ')'");
                break;
            case 384:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir 'case', '}' o 'default'");
                break;
            case 386:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un '>', ',' o '}'");
                break;
            case 389:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un  'int', 'boolean' o 'string'");
                break;
            case 390:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un  '}'");
                break;
            case 391:
                System.err
                        .println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un entero");
                break;
            case 392:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir ':'");
                break;
            case 394:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir un identificador");
                break;
            case 396:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir ':'");
                break;
            case 398:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir una ',' o ')'");
                break;

            case 400:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir 'case', '}' o 'default'");
                break;
            case 403:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir 'case', '}' o 'default'");
                break;
            case 404:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un  '}'");
                break;
            case 405:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un  '}'");
                break;
            case 406:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir 'case', '}' o 'default'");
                break;

            // Error de falta ; 14, 35, 36, 42, 74
            case 500:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Falta ';' después de '" + prevToken + "'");
                break;
            // Error de falta ( en el 5, 8, 15, 31,64, 65, 66
            case 501:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un '('");
                break;
            // Después debe ir una expresión: 11, 19, 21, 23, 24, 32, 76
            case 502:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                                + "' debe ir una expresión.");
                break;

            // Follow de U, V, V': 27,28, 30,33, 34, 56, 77, 78, 80, 87
            case 503:
                System.err.println("Error sintáctico en la línea: " + linea + ". Después de '" + prevToken
                        + "' debe ir un '*', ',', ';', '>' o ')'");
                break;

            // GENERICA GENERICA: 7, 38, 53, 59, 60, 68,70, 72, 73, 83, 85, 95, 97, 99, 101
            case 504:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' no puede ir '"
                                + currToken + "'");
                break;

            // Despues tiene q ir ) : 51, 61, 63,75, 79,88, 93, 102
            case 505:
                System.err.println(
                        "Error sintáctico en la línea: " + linea + ". Después de '" + prevToken + "' debe ir un ')'");
                break;

        }
    }

    public static void lanzarErrorSemantico(int codError, int linea) {
        switch (codError) {
            case 600:
                System.err.println("Error semántico en la línea: " + linea
                        + ". Los dos operandos del '>' deben ser de tipo 'int'.");
                break;
            case 601:
                System.err.println(
                        "Error semántico en la línea: " + linea + ". Solo se pueden multiplicar datos de tipo 'int'.");
                break;
            case 602:
                System.err.println(
                        "Error semántico en la línea: " + linea + ". Solo se pueden negar datos de tipo 'boolean'.");
                break;
            case 603:
                System.err.println("Error semántico en la línea: " + linea
                        + ". El número de argumentos no coincide con el esperado.");
                break;
            case 605:
                System.err.println("Error semántico en la línea: " + (linea)
                        + ". Ambos lados de la asignación deben ser del mismo tipo.");
                break;
            case 606:
                System.err.println(
                        "Error semántico en la línea: " + linea + ". Solo se pueden sumar datos de tipo 'int'.");
                break;
            case 607:
                System.err.println("Error semántico en la línea: " + linea
                        + ". Solo se puede hacer 'put' de una cadena o un entero");
                break;
            case 608:
                System.err.println("Error semántico en la línea: " + linea
                        + ". Solo se puede hacer 'get' de una cadena o un entero");
                break;
            case 609:
                System.err.println("Error semántico en la línea: " + linea
                        + ". Sólo se puede hacer 'return' dentro de una función.");
                break;
            case 610:
                System.err.println("Error semántico en la línea: " + linea
                        + ". No se puede hacer un 'break' fuera de un 'if' o un 'switch'");
                break;
            case 611:
                System.err.println("Error semántico en la línea: " + linea
                        + ". La Condición del if debe ser de tipo lógico");
                break;
            case 612:
                System.err.println(
                        "Error semántico en la línea: " + linea + ". Una variable no puede ser de tipo 'void'.");
                break;
            case 613:
                System.err.println(
                        "Error semántico en la línea: " + linea
                                + ". El tipo de los 'case' del switch debe ser de tipo ‘int’.");
                break;
            case 614:
                System.err.println(
                        "Error semántico en la línea: " + linea + ". No se puede hacer ‘return’ de tipos distintos. ");
                break;
            case 618:
                System.err.println(
                        "Error semántico en la línea: " + linea + ". La función no ha sido declarada previamente. ");
                break;

        }
    }

    public static void lanzarErrorSemantico(int codError, int linea, String lexema) {
        switch (codError) {
            case 616:
                System.err.println("Error semántico en la línea: " + linea + ". La variable '" + lexema
                        + "' ya ha sido declarada previamente.");
                break;
            case 617:
                System.err.println("Error semántico en la línea: " + linea + ". La variable '" + lexema
                        + "' no se ha declarado aún.");
                break;

        }
    }

    public static void lanzarErrorSemantico(int codError, int linea, String tipoOriginal, String tipoEncontrado,
            int pos, String funcion) {
        switch (codError) {
            case 604:
                System.err.println("Error semántico en la línea: " + linea + ". En la función '" + funcion
                        + "', el argumento de la posición " + pos + " es de tipo '"
                        + tipoEncontrado
                        + "' pero debería ser de tipo '" + tipoOriginal + "'.");
                break;
            case 615:
                System.err.println("Error semántico en la línea: " + linea + ". En la función '" + funcion
                        + "', el tipo devuelto: '" + tipoEncontrado
                        + "' no coincide con el esperado '"
                        + tipoOriginal
                        + "'.");
                break;

        }
    }

    private static String obtenerEquivalencia(Token tok) {
        // int cod[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
        // 19, 20, 21, 22, 23, 24, 25, 26, 27,
        // 28, 29, 30 };
        String token[] = { "boolean", "break", "case", "function", "get", "if", "int", "let", "put", "return", "string",
                "switch", " void", "un entero", "una cadena", "un identificador", "+=", "=", ",", ";", ":", "(", ")",
                "{", "}", "*", "!", ">", "default", "fin de fichero" };
        return token[tok.getCodigo() - 1];
    }

}