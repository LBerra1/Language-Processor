package Analizador.ASin;

import java.util.HashMap;
import java.util.Map;

//import Analizador.ALex.Token;

public class Automata {
    private static int BOOLEAN = 1;
    private static int BREAK = 2;
    private static int CASE = 3;
    private static int FUNCTION = 4;
    private static int GET = 5;
    private static int IF = 6;
    private static int INT = 7;
    private static int LET = 8;
    private static int PUT = 9;
    private static int RETURN = 10;
    private static int STRING = 11;
    private static int SWITCH = 12;
    private static int VOID = 13;
    private static int ENTERO = 14;
    private static int CADENA = 15;
    private static int IDENTIFICADOR = 16;
    private static int MASIGUAL = 17;
    private static int IGUAL = 18;
    private static int COMA = 19;
    private static int PUNTOYCOMA = 20;
    private static int DOSPUNTOS = 21;
    private static int PARENTESISABIERTO = 22;
    private static int PARENTESISCERRADO = 23;
    private static int LLAVEABIERTA = 24;
    private static int LLAVECERRADA = 25;
    private static int ASTERISCO = 26;
    private static int EXCLAMACION = 27;
    private static int MAYORQUE = 28;
    private static int DEFAULT = 29;
    private static int EOF = 30;

    private Map<Integer, Map<Integer, Accion>> accion;
    private Map<Integer, Map<String, Integer>> goTo;

    public Automata() {
        accion = new HashMap<>();
        goTo = new HashMap<>();
        generarTablaAccion();
        generarTablaGoTo();
    }

    // MIRAR NOMBRES
    // Añade celda a la tabla accion
    private void addCeldaAccion(int currState, Integer token, String tipoAccion, Integer valor) {
        if (this.accion.get(currState) == null) {
            this.accion.put(currState, new HashMap<>());
        }
        this.accion.get(currState).put(token, new Accion(tipoAccion, valor));
    }

    // añade celda a la tabla goTo
    private void addCeldaGoto(int currState, String noTerminal, Integer valor) {
        if (this.goTo.get(currState) == null) {
            this.goTo.put(currState, new HashMap<>());
        }
        this.goTo.get(currState).put(noTerminal, valor);
    }

    public Accion getFromAccion(int currState, Integer token) {
        return accion.get(currState).get(token);
    }

    public Integer getFromGoTo(int currState, String noTerminal) {
        return goTo.get(currState).get(noTerminal);
    }

    private void generarTablaGoTo() {

        addCeldaGoto(0, "S", 7);
        addCeldaGoto(0, "B", 2);
        addCeldaGoto(0, "F", 3);
        addCeldaGoto(0, "F1", 9);
        addCeldaGoto(0, "F2", 15);
        addCeldaGoto(0, "P", 1);

        addCeldaGoto(2, "S", 7);
        addCeldaGoto(2, "B", 2);
        addCeldaGoto(2, "F", 3);
        addCeldaGoto(2, "F1", 9);
        addCeldaGoto(2, "F2", 15);
        addCeldaGoto(2, "P", 17);

        addCeldaGoto(3, "S", 7);
        addCeldaGoto(3, "B", 2);
        addCeldaGoto(3, "F", 3);
        addCeldaGoto(3, "F1", 9);
        addCeldaGoto(3, "F2", 15);
        addCeldaGoto(3, "P", 18);

        addCeldaGoto(11, "E", 26);
        addCeldaGoto(11, "U", 27);
        addCeldaGoto(11, "V'", 28);
        addCeldaGoto(11, "V", 30);

        addCeldaGoto(13, "E", 37);
        addCeldaGoto(13, "U", 27);
        addCeldaGoto(13, "V'", 28);
        addCeldaGoto(13, "V", 30);
        addCeldaGoto(13, "X", 36);

        addCeldaGoto(19, "E", 41);
        addCeldaGoto(19, "U", 27);
        addCeldaGoto(19, "V'", 28);
        addCeldaGoto(19, "V", 30);

        addCeldaGoto(20, "T", 42);

        addCeldaGoto(21, "E", 46);
        addCeldaGoto(21, "U", 27);
        addCeldaGoto(21, "V'", 28);
        addCeldaGoto(21, "V", 30);

        addCeldaGoto(22, "S", 7);
        addCeldaGoto(22, "B", 48);
        addCeldaGoto(22, "C", 47);

        addCeldaGoto(23, "E", 49);
        addCeldaGoto(23, "U", 27);
        addCeldaGoto(23, "V'", 28);
        addCeldaGoto(23, "V", 30);

        addCeldaGoto(24, "E", 50);
        addCeldaGoto(24, "U", 27);
        addCeldaGoto(24, "V'", 28);
        addCeldaGoto(24, "V", 30);

        addCeldaGoto(25, "E", 52);
        addCeldaGoto(25, "U", 27);
        addCeldaGoto(25, "V'", 28);
        addCeldaGoto(25, "V", 30);
        addCeldaGoto(25, "L", 51);

        addCeldaGoto(29, "V", 30);
        addCeldaGoto(29, "V'", 56);

        addCeldaGoto(32, "E", 58);
        addCeldaGoto(32, "U", 27);
        addCeldaGoto(32, "V'", 28);
        addCeldaGoto(32, "V", 30);

        addCeldaGoto(39, "T", 62);
        addCeldaGoto(39, "A", 61);

        addCeldaGoto(40, "T", 65);
        addCeldaGoto(40, "H", 64);

        addCeldaGoto(48, "S", 7);
        addCeldaGoto(48, "B", 48);
        addCeldaGoto(48, "C", 71);

        addCeldaGoto(52, "Q", 75);

        addCeldaGoto(54, "U", 77);
        addCeldaGoto(54, "V'", 28);
        addCeldaGoto(54, "V", 30);

        addCeldaGoto(55, "V'", 78);
        addCeldaGoto(55, "V", 30);

        addCeldaGoto(57, "E", 52);
        addCeldaGoto(57, "U", 27);
        addCeldaGoto(57, "V'", 28);
        addCeldaGoto(57, "V", 30);
        addCeldaGoto(57, "L", 79);

        addCeldaGoto(67, "S", 83);

        addCeldaGoto(76, "E", 86);
        addCeldaGoto(76, "U", 27);
        addCeldaGoto(76, "V'", 28);
        addCeldaGoto(76, "V", 30);

        addCeldaGoto(82, "K", 88);

        addCeldaGoto(84, "Y", 90);

        addCeldaGoto(86, "Q", 93);

        addCeldaGoto(89, "T", 94);

        addCeldaGoto(97, "S", 7);
        addCeldaGoto(97, "B", 101);
        addCeldaGoto(97, "Z", 100);

        addCeldaGoto(98, "K", 102);

        addCeldaGoto(99, "S", 7);
        addCeldaGoto(99, "B", 101);
        addCeldaGoto(99, "Z", 103);

        addCeldaGoto(100, "Y", 104);

        addCeldaGoto(101, "S", 7);
        addCeldaGoto(101, "B", 101);
        addCeldaGoto(101, "Z", 106);

        addCeldaGoto(103, "Y", 105);
    }

    private void generarTablaAccion() {
        addCeldaAccion(0, BREAK, "desplazar", 14);
        addCeldaAccion(0, FUNCTION, "desplazar", 16);
        addCeldaAccion(0, GET, "desplazar", 12);
        addCeldaAccion(0, IF, "desplazar", 5);
        addCeldaAccion(0, LET, "desplazar", 6);
        addCeldaAccion(0, PUT, "desplazar", 11);
        addCeldaAccion(0, RETURN, "desplazar", 13);
        addCeldaAccion(0, SWITCH, "desplazar", 8);
        addCeldaAccion(0, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(0, EOF, "desplazar", 4);

        addCeldaAccion(1, EOF, "aceptar", -1);

        addCeldaAccion(2, BREAK, "desplazar", 14);
        addCeldaAccion(2, FUNCTION, "desplazar", 16);
        addCeldaAccion(2, GET, "desplazar", 12);
        addCeldaAccion(2, IF, "desplazar", 5);
        addCeldaAccion(2, LET, "desplazar", 6);
        addCeldaAccion(2, PUT, "desplazar", 11);
        addCeldaAccion(2, RETURN, "desplazar", 13);
        addCeldaAccion(2, SWITCH, "desplazar", 8);
        addCeldaAccion(2, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(2, EOF, "desplazar", 4);

        addCeldaAccion(3, BREAK, "desplazar", 14);
        addCeldaAccion(3, FUNCTION, "desplazar", 16);
        addCeldaAccion(3, GET, "desplazar", 12);
        addCeldaAccion(3, IF, "desplazar", 5);
        addCeldaAccion(3, LET, "desplazar", 6);
        addCeldaAccion(3, PUT, "desplazar", 11);
        addCeldaAccion(3, RETURN, "desplazar", 13);
        addCeldaAccion(3, SWITCH, "desplazar", 8);
        addCeldaAccion(3, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(3, EOF, "desplazar", 4);

        addCeldaAccion(4, EOF, "reducir", 4);

        addCeldaAccion(5, PARENTESISABIERTO, "desplazar", 19);

        addCeldaAccion(6, IDENTIFICADOR, "desplazar", 20);

        addCeldaAccion(7, BREAK, "reducir", 34);
        addCeldaAccion(7, CASE, "reducir", 34);
        addCeldaAccion(7, FUNCTION, "reducir", 34);
        addCeldaAccion(7, GET, "reducir", 34);
        addCeldaAccion(7, IF, "reducir", 34);
        addCeldaAccion(7, LET, "reducir", 34);
        addCeldaAccion(7, PUT, "reducir", 34);
        addCeldaAccion(7, RETURN, "reducir", 34);
        addCeldaAccion(7, SWITCH, "reducir", 34);
        addCeldaAccion(7, IDENTIFICADOR, "reducir", 34);
        addCeldaAccion(7, LLAVECERRADA, "reducir", 34);
        addCeldaAccion(7, DEFAULT, "reducir", 34);
        addCeldaAccion(7, EOF, "reducir", 34);

        addCeldaAccion(8, PARENTESISABIERTO, "desplazar", 21);

        addCeldaAccion(9, LLAVEABIERTA, "desplazar", 22);

        addCeldaAccion(10, MASIGUAL, "desplazar", 24);
        addCeldaAccion(10, IGUAL, "desplazar", 23);
        addCeldaAccion(10, PARENTESISABIERTO, "desplazar", 25);

        addCeldaAccion(11, ENTERO, "desplazar", 33);
        addCeldaAccion(11, CADENA, "desplazar", 34);
        addCeldaAccion(11, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(11, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(11, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(12, IDENTIFICADOR, "desplazar", 35);

        addCeldaAccion(13, ENTERO, "desplazar", 33);
        addCeldaAccion(13, CADENA, "desplazar", 34);
        addCeldaAccion(13, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(13, PUNTOYCOMA, "reducir", 27);
        addCeldaAccion(13, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(13, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(14, PUNTOYCOMA, "desplazar", 38);

        addCeldaAccion(15, PARENTESISABIERTO, "desplazar", 39);

        addCeldaAccion(16, IDENTIFICADOR, "desplazar", 40);

        addCeldaAccion(17, EOF, "reducir", 2);

        addCeldaAccion(18, EOF, "reducir", 3);

        addCeldaAccion(19, ENTERO, "desplazar", 33);
        addCeldaAccion(19, CADENA, "desplazar", 34);
        addCeldaAccion(19, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(19, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(19, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(20, BOOLEAN, "desplazar", 44);
        addCeldaAccion(20, INT, "desplazar", 43);
        addCeldaAccion(20, STRING, "desplazar", 45);

        addCeldaAccion(21, ENTERO, "desplazar", 33);
        addCeldaAccion(21, CADENA, "desplazar", 34);
        addCeldaAccion(21, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(21, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(21, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(22, BREAK, "desplazar", 14);
        addCeldaAccion(22, GET, "desplazar", 12);
        addCeldaAccion(22, IF, "desplazar", 5);
        addCeldaAccion(22, LET, "desplazar", 6);
        addCeldaAccion(22, PUT, "desplazar", 11);
        addCeldaAccion(22, RETURN, "desplazar", 13);
        addCeldaAccion(22, SWITCH, "desplazar", 8);
        addCeldaAccion(22, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(22, LLAVECERRADA, "reducir", 51);

        addCeldaAccion(23, ENTERO, "desplazar", 33);
        addCeldaAccion(23, CADENA, "desplazar", 34);
        addCeldaAccion(23, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(23, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(23, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(24, ENTERO, "desplazar", 33);
        addCeldaAccion(24, CADENA, "desplazar", 34);
        addCeldaAccion(24, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(24, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(24, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(25, ENTERO, "desplazar", 33);
        addCeldaAccion(25, CADENA, "desplazar", 34);
        addCeldaAccion(25, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(25, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(25, PARENTESISCERRADO, "reducir", 20);
        addCeldaAccion(25, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(26, PUNTOYCOMA, "desplazar", 53);
        addCeldaAccion(26, MAYORQUE, "desplazar", 54);

        addCeldaAccion(27, COMA, "reducir", 6);
        addCeldaAccion(27, PUNTOYCOMA, "reducir", 6);
        addCeldaAccion(27, PARENTESISCERRADO, "reducir", 6);
        addCeldaAccion(27, ASTERISCO, "desplazar", 55);
        addCeldaAccion(27, MAYORQUE, "reducir", 6);

        addCeldaAccion(28, COMA, "reducir", 8);
        addCeldaAccion(28, PUNTOYCOMA, "reducir", 8);
        addCeldaAccion(28, PARENTESISCERRADO, "reducir", 8);
        addCeldaAccion(28, ASTERISCO, "reducir", 8);
        addCeldaAccion(28, MAYORQUE, "reducir", 8);

        addCeldaAccion(29, ENTERO, "desplazar", 33);
        addCeldaAccion(29, CADENA, "desplazar", 34);
        addCeldaAccion(29, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(29, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(29, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(30, COMA, "reducir", 10);
        addCeldaAccion(30, PUNTOYCOMA, "reducir", 10);
        addCeldaAccion(30, PARENTESISCERRADO, "reducir", 10);
        addCeldaAccion(30, ASTERISCO, "reducir", 10);
        addCeldaAccion(30, MAYORQUE, "reducir", 10);

        addCeldaAccion(31, COMA, "reducir", 11);
        addCeldaAccion(31, PUNTOYCOMA, "reducir", 11);
        addCeldaAccion(31, PARENTESISABIERTO, "desplazar", 57);
        addCeldaAccion(31, PARENTESISCERRADO, "reducir", 11);
        addCeldaAccion(31, ASTERISCO, "reducir", 11);
        addCeldaAccion(31, MAYORQUE, "reducir", 11);

        addCeldaAccion(32, ENTERO, "desplazar", 33);
        addCeldaAccion(32, CADENA, "desplazar", 34);
        addCeldaAccion(32, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(32, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(32, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(33, COMA, "reducir", 14);
        addCeldaAccion(33, PUNTOYCOMA, "reducir", 14);
        addCeldaAccion(33, PARENTESISCERRADO, "reducir", 14);
        addCeldaAccion(33, ASTERISCO, "reducir", 14);
        addCeldaAccion(33, MAYORQUE, "reducir", 14);

        addCeldaAccion(34, COMA, "reducir", 15);
        addCeldaAccion(34, PUNTOYCOMA, "reducir", 15);
        addCeldaAccion(34, PARENTESISCERRADO, "reducir", 15);
        addCeldaAccion(34, ASTERISCO, "reducir", 15);
        addCeldaAccion(34, MAYORQUE, "reducir", 15);

        addCeldaAccion(35, PUNTOYCOMA, "desplazar", 59);

        addCeldaAccion(36, PUNTOYCOMA, "desplazar", 60);

        addCeldaAccion(37, PUNTOYCOMA, "reducir", 26);
        addCeldaAccion(37, MAYORQUE, "desplazar", 54);

        addCeldaAccion(38, BREAK, "reducir", 28);
        addCeldaAccion(38, CASE, "reducir", 28);
        addCeldaAccion(38, FUNCTION, "reducir", 28);
        addCeldaAccion(38, GET, "reducir", 28);
        addCeldaAccion(38, IF, "reducir", 28);
        addCeldaAccion(38, LET, "reducir", 28);
        addCeldaAccion(38, PUT, "reducir", 28);
        addCeldaAccion(38, RETURN, "reducir", 28);
        addCeldaAccion(38, SWITCH, "reducir", 28);
        addCeldaAccion(38, IDENTIFICADOR, "reducir", 28);
        addCeldaAccion(38, LLAVECERRADA, "reducir", 28);
        addCeldaAccion(38, DEFAULT, "reducir", 28);
        addCeldaAccion(38, EOF, "reducir", 28);

        addCeldaAccion(39, BOOLEAN, "desplazar", 44);
        addCeldaAccion(39, INT, "desplazar", 43);
        addCeldaAccion(39, STRING, "desplazar", 45);
        addCeldaAccion(39, VOID, "desplazar", 63);

        addCeldaAccion(40, BOOLEAN, "desplazar", 44);
        addCeldaAccion(40, INT, "desplazar", 43);
        addCeldaAccion(40, STRING, "desplazar", 45);
        addCeldaAccion(40, VOID, "desplazar", 66);

        addCeldaAccion(41, PARENTESISCERRADO, "desplazar", 67);
        addCeldaAccion(41, MAYORQUE, "desplazar", 54);

        addCeldaAccion(42, PUNTOYCOMA, "desplazar", 68);

        addCeldaAccion(43, IDENTIFICADOR, "reducir", 31);
        addCeldaAccion(43, PUNTOYCOMA, "reducir", 31);
        addCeldaAccion(43, PARENTESISABIERTO, "reducir", 31);

        addCeldaAccion(44, IDENTIFICADOR, "reducir", 32);
        addCeldaAccion(44, PUNTOYCOMA, "reducir", 32);
        addCeldaAccion(44, PARENTESISABIERTO, "reducir", 32);

        addCeldaAccion(45, IDENTIFICADOR, "reducir", 33);
        addCeldaAccion(45, PUNTOYCOMA, "reducir", 33);
        addCeldaAccion(45, PARENTESISABIERTO, "reducir", 33);

        addCeldaAccion(46, PARENTESISCERRADO, "desplazar", 69);
        addCeldaAccion(46, MAYORQUE, "desplazar", 54);

        addCeldaAccion(47, LLAVECERRADA, "desplazar", 70);

        addCeldaAccion(48, BREAK, "desplazar", 14);
        addCeldaAccion(48, GET, "desplazar", 12);
        addCeldaAccion(48, IF, "desplazar", 5);
        addCeldaAccion(48, LET, "desplazar", 6);
        addCeldaAccion(48, PUT, "desplazar", 11);
        addCeldaAccion(48, RETURN, "desplazar", 13);
        addCeldaAccion(48, SWITCH, "desplazar", 8);
        addCeldaAccion(48, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(48, LLAVECERRADA, "reducir", 51);

        addCeldaAccion(49, PUNTOYCOMA, "desplazar", 72);
        addCeldaAccion(49, MAYORQUE, "desplazar", 54);

        addCeldaAccion(50, PUNTOYCOMA, "desplazar", 73);
        addCeldaAccion(50, MAYORQUE, "desplazar", 54);

        addCeldaAccion(51, PARENTESISCERRADO, "desplazar", 74);

        addCeldaAccion(52, COMA, "desplazar", 76);
        addCeldaAccion(52, PARENTESISCERRADO, "reducir", 22);
        addCeldaAccion(52, MAYORQUE, "desplazar", 54);

        addCeldaAccion(53, BREAK, "reducir", 23);
        addCeldaAccion(53, CASE, "reducir", 23);
        addCeldaAccion(53, FUNCTION, "reducir", 23);
        addCeldaAccion(53, GET, "reducir", 23);
        addCeldaAccion(53, IF, "reducir", 23);
        addCeldaAccion(53, LET, "reducir", 23);
        addCeldaAccion(53, PUT, "reducir", 23);
        addCeldaAccion(53, RETURN, "reducir", 23);
        addCeldaAccion(53, SWITCH, "reducir", 23);
        addCeldaAccion(53, IDENTIFICADOR, "reducir", 23);
        addCeldaAccion(53, LLAVECERRADA, "reducir", 23);
        addCeldaAccion(53, DEFAULT, "reducir", 23);
        addCeldaAccion(53, EOF, "reducir", 23);

        addCeldaAccion(54, ENTERO, "desplazar", 33);
        addCeldaAccion(54, CADENA, "desplazar", 34);
        addCeldaAccion(54, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(54, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(54, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(55, ENTERO, "desplazar", 33);
        addCeldaAccion(55, CADENA, "desplazar", 34);
        addCeldaAccion(55, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(55, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(55, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(56, COMA, "reducir", 9);
        addCeldaAccion(56, PUNTOYCOMA, "reducir", 9);
        addCeldaAccion(56, PARENTESISCERRADO, "reducir", 9);
        addCeldaAccion(56, ASTERISCO, "reducir", 9);
        addCeldaAccion(56, MAYORQUE, "reducir", 9);

        addCeldaAccion(57, ENTERO, "desplazar", 33);
        addCeldaAccion(57, CADENA, "desplazar", 34);
        addCeldaAccion(57, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(57, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(57, PARENTESISCERRADO, "reducir", 20);
        addCeldaAccion(57, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(58, PARENTESISCERRADO, "desplazar", 80);
        addCeldaAccion(58, MAYORQUE, "desplazar", 54);

        addCeldaAccion(59, BREAK, "reducir", 24);
        addCeldaAccion(59, CASE, "reducir", 24);
        addCeldaAccion(59, FUNCTION, "reducir", 24);
        addCeldaAccion(59, GET, "reducir", 24);
        addCeldaAccion(59, IF, "reducir", 24);
        addCeldaAccion(59, LET, "reducir", 24);
        addCeldaAccion(59, PUT, "reducir", 24);
        addCeldaAccion(59, RETURN, "reducir", 24);
        addCeldaAccion(59, SWITCH, "reducir", 24);
        addCeldaAccion(59, IDENTIFICADOR, "reducir", 24);
        addCeldaAccion(59, LLAVECERRADA, "reducir", 24);
        addCeldaAccion(59, DEFAULT, "reducir", 24);
        addCeldaAccion(59, EOF, "reducir", 24);

        addCeldaAccion(60, BREAK, "reducir", 25);
        addCeldaAccion(60, CASE, "reducir", 25);
        addCeldaAccion(60, FUNCTION, "reducir", 25);
        addCeldaAccion(60, GET, "reducir", 25);
        addCeldaAccion(60, IF, "reducir", 25);
        addCeldaAccion(60, LET, "reducir", 25);
        addCeldaAccion(60, PUT, "reducir", 25);
        addCeldaAccion(60, RETURN, "reducir", 25);
        addCeldaAccion(60, SWITCH, "reducir", 25);
        addCeldaAccion(60, IDENTIFICADOR, "reducir", 25);
        addCeldaAccion(60, LLAVECERRADA, "reducir", 25);
        addCeldaAccion(60, DEFAULT, "reducir", 25);
        addCeldaAccion(60, EOF, "reducir", 25);

        addCeldaAccion(61, PARENTESISCERRADO, "desplazar", 81);

        addCeldaAccion(62, IDENTIFICADOR, "desplazar", 82);

        addCeldaAccion(63, PARENTESISCERRADO, "reducir", 47);

        addCeldaAccion(64, PARENTESISABIERTO, "reducir", 43);

        addCeldaAccion(65, PARENTESISABIERTO, "reducir", 44);

        addCeldaAccion(66, PARENTESISABIERTO, "reducir", 45);

        addCeldaAccion(67, BREAK, "desplazar", 14);
        addCeldaAccion(67, GET, "desplazar", 12);
        addCeldaAccion(67, PUT, "desplazar", 11);
        addCeldaAccion(67, RETURN, "desplazar", 13);
        addCeldaAccion(67, IDENTIFICADOR, "desplazar", 10);

        addCeldaAccion(68, BREAK, "reducir", 30);
        addCeldaAccion(68, CASE, "reducir", 30);
        addCeldaAccion(68, FUNCTION, "reducir", 30);
        addCeldaAccion(68, GET, "reducir", 30);
        addCeldaAccion(68, IF, "reducir", 30);
        addCeldaAccion(68, LET, "reducir", 30);
        addCeldaAccion(68, PUT, "reducir", 30);
        addCeldaAccion(68, RETURN, "reducir", 30);
        addCeldaAccion(68, SWITCH, "reducir", 30);
        addCeldaAccion(68, IDENTIFICADOR, "reducir", 30);
        addCeldaAccion(68, LLAVECERRADA, "reducir", 30);
        addCeldaAccion(68, DEFAULT, "reducir", 30);
        addCeldaAccion(68, EOF, "reducir", 30);

        addCeldaAccion(69, LLAVEABIERTA, "desplazar", 84);

        addCeldaAccion(70, BREAK, "reducir", 41);
        addCeldaAccion(70, FUNCTION, "reducir", 41);
        addCeldaAccion(70, GET, "reducir", 41);
        addCeldaAccion(70, IF, "reducir", 41);
        addCeldaAccion(70, LET, "reducir", 41);
        addCeldaAccion(70, PUT, "reducir", 41);
        addCeldaAccion(70, RETURN, "reducir", 41);
        addCeldaAccion(70, SWITCH, "reducir", 41);
        addCeldaAccion(70, IDENTIFICADOR, "reducir", 41);
        addCeldaAccion(70, EOF, "reducir", 41);

        addCeldaAccion(71, LLAVECERRADA, "reducir", 50);

        addCeldaAccion(72, BREAK, "reducir", 16);
        addCeldaAccion(72, CASE, "reducir", 16);
        addCeldaAccion(72, FUNCTION, "reducir", 16);
        addCeldaAccion(72, GET, "reducir", 16);
        addCeldaAccion(72, IF, "reducir", 16);
        addCeldaAccion(72, LET, "reducir", 16);
        addCeldaAccion(72, PUT, "reducir", 16);
        addCeldaAccion(72, RETURN, "reducir", 16);
        addCeldaAccion(72, SWITCH, "reducir", 16);
        addCeldaAccion(72, IDENTIFICADOR, "reducir", 16);
        addCeldaAccion(72, LLAVECERRADA, "reducir", 16);
        addCeldaAccion(72, DEFAULT, "reducir", 16);
        addCeldaAccion(72, EOF, "reducir", 16);

        addCeldaAccion(73, BREAK, "reducir", 17);
        addCeldaAccion(73, CASE, "reducir", 17);
        addCeldaAccion(73, FUNCTION, "reducir", 17);
        addCeldaAccion(73, GET, "reducir", 17);
        addCeldaAccion(73, IF, "reducir", 17);
        addCeldaAccion(73, LET, "reducir", 17);
        addCeldaAccion(73, PUT, "reducir", 17);
        addCeldaAccion(73, RETURN, "reducir", 17);
        addCeldaAccion(73, SWITCH, "reducir", 17);
        addCeldaAccion(73, IDENTIFICADOR, "reducir", 17);
        addCeldaAccion(73, LLAVECERRADA, "reducir", 17);
        addCeldaAccion(73, DEFAULT, "reducir", 17);
        addCeldaAccion(73, EOF, "reducir", 17);

        addCeldaAccion(74, PUNTOYCOMA, "desplazar", 85);

        addCeldaAccion(75, PARENTESISCERRADO, "reducir", 19);

        addCeldaAccion(76, ENTERO, "desplazar", 33);
        addCeldaAccion(76, CADENA, "desplazar", 34);
        addCeldaAccion(76, IDENTIFICADOR, "desplazar", 31);
        addCeldaAccion(76, PARENTESISABIERTO, "desplazar", 32);
        addCeldaAccion(76, EXCLAMACION, "desplazar", 29);

        addCeldaAccion(77, COMA, "reducir", 5);
        addCeldaAccion(77, PUNTOYCOMA, "reducir", 5);
        addCeldaAccion(77, PARENTESISCERRADO, "reducir", 5);
        addCeldaAccion(77, ASTERISCO, "desplazar", 55);
        addCeldaAccion(77, MAYORQUE, "reducir", 5);

        addCeldaAccion(78, COMA, "reducir", 7);
        addCeldaAccion(78, PUNTOYCOMA, "reducir", 7);
        addCeldaAccion(78, PARENTESISCERRADO, "reducir", 7);
        addCeldaAccion(78, ASTERISCO, "reducir", 7);
        addCeldaAccion(78, MAYORQUE, "reducir", 7);

        addCeldaAccion(79, PARENTESISCERRADO, "desplazar", 87);

        addCeldaAccion(80, COMA, "reducir", 12);
        addCeldaAccion(80, PUNTOYCOMA, "reducir", 12);
        addCeldaAccion(80, PARENTESISCERRADO, "reducir", 12);
        addCeldaAccion(80, ASTERISCO, "reducir", 12);
        addCeldaAccion(80, MAYORQUE, "reducir", 12);

        addCeldaAccion(81, LLAVEABIERTA, "reducir", 42);

        addCeldaAccion(82, COMA, "desplazar", 89);
        addCeldaAccion(82, PARENTESISCERRADO, "reducir", 49);

        addCeldaAccion(83, BREAK, "reducir", 29);
        addCeldaAccion(83, CASE, "reducir", 29);
        addCeldaAccion(83, FUNCTION, "reducir", 29);
        addCeldaAccion(83, GET, "reducir", 29);
        addCeldaAccion(83, IF, "reducir", 29);
        addCeldaAccion(83, LET, "reducir", 29);
        addCeldaAccion(83, PUT, "reducir", 29);
        addCeldaAccion(83, RETURN, "reducir", 29);
        addCeldaAccion(83, SWITCH, "reducir", 29);
        addCeldaAccion(83, IDENTIFICADOR, "reducir", 29);
        addCeldaAccion(83, LLAVECERRADA, "reducir", 29);
        addCeldaAccion(83, DEFAULT, "reducir", 29);
        addCeldaAccion(83, EOF, "reducir", 29);

        addCeldaAccion(84, CASE, "desplazar", 91);
        addCeldaAccion(84, LLAVECERRADA, "reducir", 38);
        addCeldaAccion(84, DEFAULT, "desplazar", 92);

        addCeldaAccion(85, BREAK, "reducir", 18);
        addCeldaAccion(85, CASE, "reducir", 18);
        addCeldaAccion(85, FUNCTION, "reducir", 18);
        addCeldaAccion(85, GET, "reducir", 18);
        addCeldaAccion(85, IF, "reducir", 18);
        addCeldaAccion(85, LET, "reducir", 18);
        addCeldaAccion(85, PUT, "reducir", 18);
        addCeldaAccion(85, RETURN, "reducir", 18);
        addCeldaAccion(85, SWITCH, "reducir", 18);
        addCeldaAccion(85, IDENTIFICADOR, "reducir", 18);
        addCeldaAccion(85, LLAVECERRADA, "reducir", 18);
        addCeldaAccion(85, DEFAULT, "reducir", 18);
        addCeldaAccion(85, EOF, "reducir", 18);

        addCeldaAccion(86, COMA, "desplazar", 76);
        addCeldaAccion(86, PARENTESISCERRADO, "reducir", 22);
        addCeldaAccion(86, MAYORQUE, "desplazar", 54);

        addCeldaAccion(87, COMA, "reducir", 13);
        addCeldaAccion(87, PUNTOYCOMA, "reducir", 13);
        addCeldaAccion(87, PARENTESISCERRADO, "reducir", 13);
        addCeldaAccion(87, ASTERISCO, "reducir", 13);
        addCeldaAccion(87, MAYORQUE, "reducir", 13);

        addCeldaAccion(88, PARENTESISCERRADO, "reducir", 46);

        addCeldaAccion(89, BOOLEAN, "desplazar", 44);
        addCeldaAccion(89, INT, "desplazar", 43);
        addCeldaAccion(89, STRING, "desplazar", 45);

        addCeldaAccion(90, LLAVECERRADA, "desplazar", 95);

        addCeldaAccion(91, ENTERO, "desplazar", 96);

        addCeldaAccion(92, DOSPUNTOS, "desplazar", 97);

        addCeldaAccion(93, PARENTESISCERRADO, "reducir", 21);

        addCeldaAccion(94, IDENTIFICADOR, "desplazar", 98);

        addCeldaAccion(95, BREAK, "reducir", 35);
        addCeldaAccion(95, CASE, "reducir", 35);
        addCeldaAccion(95, FUNCTION, "reducir", 35);
        addCeldaAccion(95, GET, "reducir", 35);
        addCeldaAccion(95, IF, "reducir", 35);
        addCeldaAccion(95, LET, "reducir", 35);
        addCeldaAccion(95, PUT, "reducir", 35);
        addCeldaAccion(95, RETURN, "reducir", 35);
        addCeldaAccion(95, SWITCH, "reducir", 35);
        addCeldaAccion(95, IDENTIFICADOR, "reducir", 35);
        addCeldaAccion(95, LLAVECERRADA, "reducir", 35);
        addCeldaAccion(95, DEFAULT, "reducir", 35);
        addCeldaAccion(95, EOF, "reducir", 35);

        addCeldaAccion(96, DOSPUNTOS, "desplazar", 99);

        addCeldaAccion(97, BREAK, "desplazar", 14);
        addCeldaAccion(97, CASE, "reducir", 40);
        addCeldaAccion(97, GET, "desplazar", 12);
        addCeldaAccion(97, IF, "desplazar", 5);
        addCeldaAccion(97, LET, "desplazar", 6);
        addCeldaAccion(97, PUT, "desplazar", 11);
        addCeldaAccion(97, RETURN, "desplazar", 13);
        addCeldaAccion(97, SWITCH, "desplazar", 8);
        addCeldaAccion(97, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(97, LLAVECERRADA, "reducir", 40);
        addCeldaAccion(97, DEFAULT, "reducir", 40);

        addCeldaAccion(98, COMA, "desplazar", 89);
        addCeldaAccion(98, PARENTESISCERRADO, "reducir", 49);

        addCeldaAccion(99, BREAK, "desplazar", 14);
        addCeldaAccion(99, CASE, "reducir", 40);
        addCeldaAccion(99, GET, "desplazar", 12);
        addCeldaAccion(99, IF, "desplazar", 5);
        addCeldaAccion(99, LET, "desplazar", 6);
        addCeldaAccion(99, PUT, "desplazar", 11);
        addCeldaAccion(99, RETURN, "desplazar", 13);
        addCeldaAccion(99, SWITCH, "desplazar", 8);
        addCeldaAccion(99, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(99, LLAVECERRADA, "reducir", 40);
        addCeldaAccion(99, DEFAULT, "reducir", 40);

        addCeldaAccion(100, CASE, "desplazar", 91);
        addCeldaAccion(100, LLAVECERRADA, "reducir", 38);

        addCeldaAccion(101, BREAK, "desplazar", 14);
        addCeldaAccion(101, CASE, "reducir", 40);
        addCeldaAccion(101, GET, "desplazar", 12);
        addCeldaAccion(101, IF, "desplazar", 5);
        addCeldaAccion(101, LET, "desplazar", 6);
        addCeldaAccion(101, PUT, "desplazar", 11);
        addCeldaAccion(101, RETURN, "desplazar", 13);
        addCeldaAccion(101, SWITCH, "desplazar", 8);
        addCeldaAccion(101, IDENTIFICADOR, "desplazar", 10);
        addCeldaAccion(101, LLAVECERRADA, "reducir", 40);
        addCeldaAccion(101, DEFAULT, "reducir", 40);

        addCeldaAccion(102, PARENTESISCERRADO, "reducir", 48);

        addCeldaAccion(103, CASE, "desplazar", 91);
        addCeldaAccion(103, LLAVECERRADA, "reducir", 38);
        addCeldaAccion(103, DEFAULT, "desplazar", 92);

        addCeldaAccion(104, LLAVECERRADA, "reducir", 37);

        addCeldaAccion(105, LLAVECERRADA, "reducir", 36);

        addCeldaAccion(106, CASE, "reducir", 39);
        addCeldaAccion(106, LLAVECERRADA, "reducir", 39);
        addCeldaAccion(106, DEFAULT, "reducir", 39);
    }
}
