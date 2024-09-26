package Analizador.ASin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import Analizador.ALex.AnalizadorLexico;
import Analizador.ALex.Token;
import Analizador.TS.TablaSimbolos;
import Analizador.Error;

public class AnalizadorSintactico {
    private FileWriter fileTokens;
    private FileWriter fileParse;
    private Automata tablas;
    private AnalizadorLexico aLex;
    private Stack<EntradaPila> pila;
    private Map<Integer, Integer> gramaticaNumeros;
    private Map<Integer, String> gramaticaParteIzquierda;
    private FileWriter fileTablaSimbolos;
    private TablaSimbolos TSG;
    private TablaSimbolos TSL;
    private int idTS;
    private int desplL;
    private int desplG;
    private boolean esTSG;
    private boolean letRecibido;
    private int linea;

    public AnalizadorSintactico(FileReader fileIn, FileWriter fileTokens, FileWriter fileTablaSimbolos,
            FileWriter fileParse) throws IOException {
        this.fileTokens = fileTokens;
        this.fileParse = fileParse;
        this.fileTablaSimbolos = fileTablaSimbolos;
        this.tablas = new Automata(); // con esto se generan la tabla accion y la tabla goTo

        this.idTS = 0;
        this.desplG = 0;
        this.TSG = new TablaSimbolos(this.idTS);
        this.idTS++;
        this.esTSG = true;
        this.letRecibido = false;

        this.aLex = new AnalizadorLexico(fileIn, fileTablaSimbolos, this.TSG);

        this.pila = new Stack<EntradaPila>();
        pila.push(new EntradaPila(0)); // se mete el estado 0

        this.gramaticaNumeros = new HashMap<Integer, Integer>();
        this.gramaticaParteIzquierda = new HashMap<Integer, String>();
        definirGramaticaNumeros();
        definirgramaticaParteIzquierda();
    }

    private Token getToken() throws IOException {
        this.linea = aLex.getLinea();
        Token token = aLex.getToken();
        if (token == null) {
            return null;
        }
        fileTokens.write(token.tokString() + '\n');
        return token;
    }

    public int execAnalizadorSintactico() throws IOException {
        EntradaPila cima;
        Token token, prevToken;
        Accion accion;
        int num;
        boolean terminar = false;
        int estadoGoTo;
        String parteIzquierda;
        EntradaPila nuevaEntrada;
        // int it = 0;
        fileParse.write("Ascendente ");
        token = getToken();
        if (token == null) {
            return -1;
        }
        prevToken = token;
        while (!terminar) {

            if (token.getCodigo() == 8) {
                this.letRecibido = true;
            }

            if (token.getCodigo() == 16 && this.letRecibido) {
                aLex.setZonaDecl(false);
                this.letRecibido = false;
            }
            // if (it == 123) {
            // System.out.println("Aqui");
            // }

            cima = pila.peek();
            accion = tablas.getFromAccion(cima.getEstado(), token.getCodigo());
            // System.out.println(
            // "---------- Iteración " + it + ". Cima: " + cima.getEstado() + ". TOKEN: " +
            // token.getCodigo());

            if (accion == null) {
                gestionarError(cima.getEstado(), prevToken, token);
                break;
            }

            if (accion.esDesplazar()) {
                pila.push(new EntradaPila(token));
                pila.push(new EntradaPila(accion.getValor()));
                prevToken = token;
                // let o function, zona declaracion a true
                if (token.getCodigo() == 8 || token.getCodigo() == 4) {
                    aLex.setZonaDecl(true);
                }
                // Para que no genere dos veces el token eof
                if (token.getCodigo() != 30) {
                    token = getToken();

                    if (token == null) {
                        return -1;
                    }
                }

            } else if (accion.esReducir()) {
                num = gramaticaNumeros.get(accion.getValor()); // guardamos el numero
                num = num * 2;

                nuevaEntrada = actuaSemantico(accion.getValor(), token);
                if (nuevaEntrada == null) {
                    return -1;
                }
                for (int i = 0; i < num; i++) {
                    pila.pop();
                }
                cima = pila.peek();
                parteIzquierda = gramaticaParteIzquierda.get(accion.getValor());
                nuevaEntrada.setNoTerminal(parteIzquierda);
                pila.push(nuevaEntrada); // meter A en la pila

                estadoGoTo = tablas.getFromGoTo(cima.getEstado(), parteIzquierda);

                pila.push(new EntradaPila(estadoGoTo));
                fileParse.write(String.valueOf(accion.getValor()) + " ");

            } else if (accion.esAceptar()) {
                terminar = true;
                fileParse.write('1');
                TSG.liberarTSG(fileTablaSimbolos);
            }
            // it++;
        }
        return 0;
    }

    private String getTipoTS(Token tok) {
        String tipo;
        if (this.esTSG) {
            tipo = TSG.buscaTipo(tok.getNumero());
        } else {
            tipo = TSL.buscaTipo(tok.getNumero());
            if (tipo == null) {
                tipo = TSG.buscaTipo(tok.getNumero());
            }
        }
        if (tipo == null) {
            TSG.añadirTipoYDespl(tok.getNumero(), "int", desplG);
            desplG = desplG + 1;
            tipo = TSG.buscaTipo(tok.getNumero());
        }
        return tipo;
    }

    private EntradaPila actuaSemantico(int nRegla, Token token) throws IOException {
        EntradaPila nuevaEntrada = new EntradaPila();
        int size = pila.size();
        String tipo;
        int numParamsTS;
        ArrayList<String> arrTS;
        ArrayList<String> arrL;
        switch (nRegla) {
            case 2:
                tipo = pila.get(size - 4).getTipoRet();
                if (tipo != null) {
                    Error.lanzarErrorSemantico(609, this.linea);
                    nuevaEntrada = null;
                }
                if (pila.get(size - 4).getHayBreak()) {
                    Error.lanzarErrorSemantico(610, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 3:
                if (pila.get(size - 4).getHayBreak()) {
                    Error.lanzarErrorSemantico(610, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 5:
                if (pila.get(size - 2).getTipo().equals(pila.get(size - 6).getTipo())
                        && pila.get(size - 6).getTipo().equals("int")) {
                    nuevaEntrada.setTipo("boolean");
                } else {
                    Error.lanzarErrorSemantico(600, this.linea);
                    nuevaEntrada = null;
                }
                break;
            case 6:
                nuevaEntrada.setTipo(pila.get(size - 2).getTipo());
                break;
            case 7:
                if (pila.get(size - 2).getTipo() == pila.get(size - 6).getTipo()
                        && pila.get(size - 6).getTipo().equals("int")) {
                    nuevaEntrada.setTipo("int");
                } else {
                    Error.lanzarErrorSemantico(601, this.linea);
                    nuevaEntrada = null;
                }
                break;
            case 8:
                nuevaEntrada.setTipo(pila.get(size - 2).getTipo());
                break;

            case 9:
                if (pila.get(size - 2).getTipo().equals("boolean")) {
                    nuevaEntrada.setTipo("boolean");
                } else {
                    Error.lanzarErrorSemantico(602, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 10:
                nuevaEntrada.setTipo(pila.get(size - 2).getTipo());
                break;

            case 11:
                tipo = getTipoTS(pila.get(size - 2).getToken());
                nuevaEntrada.setTipo(tipo);
                break;

            case 12:
                nuevaEntrada.setTipo(pila.get(size - 4).getTipo());
                break;

            case 13:
                numParamsTS = TSG.buscaNumParams(pila.get(size - 8).getToken().getNumero());
                if (numParamsTS != pila.get(size - 4).getNumParams()
                        && TSG.buscaTipo(pila.get(size - 8).getToken().getNumero()) == null) {
                    Error.lanzarErrorSemantico(618, this.linea);
                    return null;
                } else if (numParamsTS != pila.get(size - 4).getNumParams()) {
                    Error.lanzarErrorSemantico(603, this.linea);
                    return null;
                }

                arrTS = TSG.buscaTipoParams(pila.get(size - 8).getToken().getNumero());
                arrL = pila.get(size - 4).getTipoParams();
                for (int i = 0; i < numParamsTS; i++) {
                    if (!arrTS.get(i).equals(arrL.get(i))) {
                        Error.lanzarErrorSemantico(604, this.linea, arrTS.get(i), arrL.get(i), i,
                                TSG.getNombreIdent(pila.get(size - 8).getToken().getNumero()));
                        return null;
                    }
                }
                nuevaEntrada.setTipo(TSG.buscaTipoRet(pila.get(size - 8).getToken().getNumero()));
                break;
            case 14:
                nuevaEntrada.setTipo("int");
                break;

            case 15:
                nuevaEntrada.setTipo("string");
                break;

            case 16:
                tipo = getTipoTS(pila.get(size - 8).getToken());
                if (pila.get(size - 4).getTipo().equals(tipo)) {
                    nuevaEntrada.setTipo("tipo_ok");
                    nuevaEntrada.setTipoRet(null);
                    nuevaEntrada.setHayBreak(false);
                } else {
                    Error.lanzarErrorSemantico(605, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 17:
                tipo = getTipoTS(pila.get(size - 8).getToken());
                if (pila.get(size - 4).getTipo().equals(tipo) && tipo.equals("int")) {
                    nuevaEntrada.setTipo("tipo_ok");
                    nuevaEntrada.setTipoRet(null);
                    nuevaEntrada.setHayBreak(false);
                } else {
                    Error.lanzarErrorSemantico(606, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 18:
                numParamsTS = TSG.buscaNumParams(pila.get(size - 8).getToken().getNumero());
                if (numParamsTS != pila.get(size - 4).getNumParams()) {
                    Error.lanzarErrorSemantico(603, this.linea);
                    nuevaEntrada = null;
                }
                arrTS = TSG.buscaTipoParams(pila.get(size - 8).getToken().getNumero());
                arrL = pila.get(size - 4).getTipoParams();
                for (int i = 0; i < numParamsTS; i++) {
                    if (!arrTS.get(i).equals(arrL.get(i))) {
                        Error.lanzarErrorSemantico(604, this.linea, arrTS.get(i), arrL.get(i), i,
                                TSG.getNombreIdent(pila.get(size - 10).getToken().getNumero()));
                        return null;
                    }
                }
                nuevaEntrada.setHayBreak(false);
                break;
            case 19:
                nuevaEntrada.addTipoParams(pila.get(size - 4).getTipo());
                if (pila.get(size - 2).getTipoParams().size() != 0) {
                    nuevaEntrada.addTipoParams(pila.get(size - 2).getTipoParams());
                }
                break;

            case 20:
                nuevaEntrada.setTipo(null);
                break;

            case 21:
                nuevaEntrada.addTipoParams(pila.get(size - 4).getTipo());

                if (pila.get(size - 2).getTipoParams().size() != 0) {
                    nuevaEntrada.addTipoParams(pila.get(size - 2).getTipoParams());
                }
                break;

            case 22:
                nuevaEntrada.setTipo(null);
                break;

            case 23:
                tipo = pila.get(size - 4).getTipo();
                if (tipo.equals("string") || tipo.equals("int")) {
                    nuevaEntrada.setTipo("tipo_ok");
                    nuevaEntrada.setTipoRet(null);
                    nuevaEntrada.setHayBreak(false);
                } else {
                    Error.lanzarErrorSemantico(607, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 24:
                tipo = getTipoTS(pila.get(size - 4).getToken());
                if (tipo.equals("string") || tipo.equals("int")) {
                    nuevaEntrada.setTipo("tipo_ok");
                    nuevaEntrada.setTipoRet(null);
                    nuevaEntrada.setHayBreak(false);
                } else {
                    Error.lanzarErrorSemantico(608, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 25:
                tipo = pila.get(size - 4).getTipo();
                nuevaEntrada.setTipoRet(tipo);
                nuevaEntrada.setHayBreak(false);
                break;

            case 26:
                nuevaEntrada.setTipo(pila.get(size - 2).getTipo());
                break;

            case 27:
                nuevaEntrada.setTipo("void");
                break;

            case 28:
                nuevaEntrada.setHayBreak(true);
                break;

            case 29:
                if (pila.get(size - 6).getTipo().equals("boolean")) {
                    nuevaEntrada.setTipo(pila.get(size - 2).getTipo());
                    nuevaEntrada.setTipoRet(pila.get(size - 2).getTipoRet());
                    nuevaEntrada.setHayBreak(false);
                } else {
                    Error.lanzarErrorSemantico(611, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 30:
                tipo = pila.get(size - 4).getTipo();
                if (tipo.equals("void")) {
                    Error.lanzarErrorSemantico(612, this.linea);
                    nuevaEntrada = null;
                } else {
                    if (this.esTSG) {
                        TSG.añadirTipoYDespl(pila.get(size - 6).getToken().getNumero(), tipo, desplG);
                        desplG += pila.get(size - 4).getAncho();
                    } else {
                        TSL.añadirTipoYDespl(pila.get(size - 6).getToken().getNumero(), tipo, desplL);
                        desplL += pila.get(size - 4).getAncho();
                    }
                    nuevaEntrada.setTipoRet(null);
                }
                aLex.setZonaDecl(false);

                break;

            case 31:
                nuevaEntrada.setTipo("int");
                nuevaEntrada.setAncho(1);
                break;

            case 32:
                nuevaEntrada.setTipo("boolean");
                nuevaEntrada.setAncho(1);
                break;

            case 33:
                nuevaEntrada.setTipo("string");
                nuevaEntrada.setAncho(64);
                break;

            case 34:
                nuevaEntrada.setTipo(pila.get(size - 2).getTipo());
                nuevaEntrada.setTipoRet(pila.get(size - 2).getTipoRet());
                nuevaEntrada.setHayBreak(pila.get(size - 2).getHayBreak());
                break;

            case 35:
                if (pila.get(size - 10).getTipo().equals("int")) {
                    nuevaEntrada.setTipo(pila.get(size - 4).getTipo());
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());
                    nuevaEntrada.setHayBreak(false);
                } else {
                    Error.lanzarErrorSemantico(613, this.linea);
                    nuevaEntrada = null;
                }
                break;

            case 36:

                // Si Y1 es null
                if (pila.get(size - 2).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());

                    // Si C es null
                } else if (pila.get(size - 4).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 2).getTipoRet());

                    // Si los 2 tipos son != NULL y coinciden
                } else if (pila.get(size - 4).getTipoRet().equals(pila.get(size - 2).getTipoRet())) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());
                }
                // Si ambos tipos existen y son diferentes
                else {
                    Error.lanzarErrorSemantico(614, this.linea);
                    return null;
                }
                nuevaEntrada.setHayBreak(pila.get(size - 4).getHayBreak() || pila.get(size - 2).getHayBreak());
                break;

            case 37:
                // Si Y1 es null
                if (pila.get(size - 2).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());

                    // Si C es null
                } else if (pila.get(size - 4).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 2).getTipoRet());

                    // Si los 2 tipos son != NULL y coinciden
                } else if (pila.get(size - 4).getTipoRet().equals(pila.get(size - 2).getTipoRet())) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());
                }
                // Si ambos tipos existen y son diferentes
                else {
                    Error.lanzarErrorSemantico(614, this.linea);
                    return null;
                }
                nuevaEntrada.setHayBreak(pila.get(size - 4).getHayBreak() || pila.get(size - 2).getHayBreak());
                break;

            case 38:
                nuevaEntrada.setTipo("tipo_ok");
                nuevaEntrada.setTipoRet(null);
                break;
            /// ***CUIDADO QUE ESTAS SON LAS QUITADAS
            case 39:

                // Si C1 es null
                if (pila.get(size - 2).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());

                    // Si B es null
                } else if (pila.get(size - 4).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 2).getTipoRet());

                    // Si los 2 tipos son != NULL y coinciden
                } else if (pila.get(size - 4).getTipoRet().equals(pila.get(size - 2).getTipoRet())) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());
                }
                // Si ambos tipos existen y son diferentes
                else {
                    Error.lanzarErrorSemantico(614, this.linea);
                    return null;
                }
                nuevaEntrada.setHayBreak(pila.get(size - 4).getHayBreak() || pila.get(size - 2).getHayBreak());

                break;

            case 40:
                nuevaEntrada.setTipo(null);
                nuevaEntrada.setTipoRet(null);
                nuevaEntrada.setHayBreak(false);
                break;

            case 41:
                tipo = pila.get(size - 4).getTipoRet();
                if (tipo != null && !tipo.equals(pila.get(size - 8).getTipoRet())) {
                    Error.lanzarErrorSemantico(615, this.linea, pila.get(size - 8).getTipoRet(), tipo,
                            pila.get(size - 8).getPos(),
                            TSG.getNombreIdent(pila.get(size - 8).getPos()));

                    return null;
                }
                TSL.liberarTSL(fileTablaSimbolos);
                esTSG = true;
                aLex.setEsTSG(true);
                nuevaEntrada.setHayBreak(pila.get(size - 4).getHayBreak());
                break;

            case 42:
                TSG.añadirTipo(pila.get(size - 8).getPos(), "function");
                TSG.añadirNumYTipoParams(pila.get(size - 8).getPos(), pila.get(size - 4).getTipoParams());
                TSG.añadirTipoDevolucion(pila.get(size - 8).getPos(), pila.get(size - 8).getTipoRet());
                nuevaEntrada.setTipoRet(pila.get(size - 8).getTipoRet());
                aLex.setZonaDecl(false);
                nuevaEntrada.setPos(pila.get(size - 8).getPos());
                break;

            case 43:
                TSL = new TablaSimbolos(idTS);
                this.esTSG = false;
                aLex.setEsTSG(false);
                aLex.setTSL(TSL);
                desplL = 0;
                nuevaEntrada.setTipoRet(pila.get(size - 2).getTipo());
                nuevaEntrada.setPos(pila.get(size - 4).getToken().getNumero());
                idTS++;
                break;

            case 44:
                nuevaEntrada.setTipo(pila.get(size - 2).getTipo());
                break;

            case 45:
                nuevaEntrada.setTipo("void");
                break;

            case 46:
                TSL.añadirTipoYDespl(pila.get(size - 4).getToken().getNumero(), pila.get(size - 6).getTipo(), desplL);

                desplL += pila.get(size - 6).getAncho();
                nuevaEntrada.addTipoParams(pila.get(size - 6).getTipo());

                if (pila.get(size - 2).getTipoParams().size() != 0) {
                    nuevaEntrada.addTipoParams(pila.get(size - 2).getTipoParams());
                }

                break;

            case 47:
                nuevaEntrada.setTipo("void");
                break;

            case 48:
                TSL.añadirTipoYDespl(pila.get(size - 4).getToken().getNumero(), pila.get(size - 6).getTipo(), desplL);

                desplL += pila.get(size - 6).getAncho();
                nuevaEntrada.addTipoParams(pila.get(size - 6).getTipo());

                if (pila.get(size - 2).getTipoParams().size() != 0) {
                    nuevaEntrada.addTipoParams(pila.get(size - 2).getTipoParams());
                }
                break;
            case 49:
                nuevaEntrada.setTipo(null);
                break;

            case 50:
                // Si C1 es null
                if (pila.get(size - 2).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());

                    // Si B es null
                } else if (pila.get(size - 4).getTipoRet() == null) {
                    nuevaEntrada.setTipoRet(pila.get(size - 2).getTipoRet());

                    // Si los 2 tipos son != NULL y coinciden
                } else if (pila.get(size - 4).getTipoRet().equals(pila.get(size - 2).getTipoRet())) {
                    nuevaEntrada.setTipoRet(pila.get(size - 4).getTipoRet());
                }
                // Si ambos tipos existen y son diferentes
                else {
                    Error.lanzarErrorSemantico(614, this.linea);
                    return null;
                }
                nuevaEntrada.setHayBreak(pila.get(size - 4).getHayBreak() || pila.get(size - 2).getHayBreak());

                break;

            case 51:
                nuevaEntrada.setTipo(null);
                nuevaEntrada.setTipoRet(null);
                nuevaEntrada.setHayBreak(false);
                break;
        }

        return nuevaEntrada;

    }

    private void gestionarError(int estado, Token prevToken, Token currToken) {
        switch (estado) {
            case 0:
                Error.lanzarError(300, aLex.getLinea(), prevToken, currToken);
                break;
            case 2:
                Error.lanzarError(302, aLex.getLinea(), prevToken, currToken);
                break;
            case 3:
                Error.lanzarError(303, aLex.getLinea(), prevToken, currToken);
                break;
            /*
             * case 4:
             * Error.lanzarError(300, aLex.getLinea(), prevToken, currToken);
             * break;
             */
            case 5:
                Error.lanzarError(501, aLex.getLinea(), prevToken, currToken);
                break;
            case 6:
                Error.lanzarError(306, aLex.getLinea(), prevToken, currToken);
                break;
            case 7:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 8:
                Error.lanzarError(501, aLex.getLinea(), prevToken, currToken);
                break;
            case 9:
                Error.lanzarError(309, aLex.getLinea(), prevToken, currToken);
                break;
            case 10:
                Error.lanzarError(310, aLex.getLinea(), prevToken, currToken);
                break;
            case 11:
                Error.lanzarError(502, aLex.getLinea(), prevToken, currToken);
                break;
            case 12:
                Error.lanzarError(312, aLex.getLinea(), prevToken, currToken);
                break;
            case 13:
                Error.lanzarError(313, aLex.getLinea(), prevToken, currToken);
                break;
            case 14:
                Error.lanzarError(500, aLex.getLinea(), prevToken, currToken);
                break;
            case 15:
                Error.lanzarError(501, aLex.getLinea(), prevToken, currToken);
                break;
            case 16:
                Error.lanzarError(316, aLex.getLinea(), prevToken, currToken);
                break;
            case 17:
                Error.lanzarError(317, aLex.getLinea(), prevToken, currToken);
                break;
            case 18:
                Error.lanzarError(318, aLex.getLinea(), prevToken, currToken);
                break;
            case 19:
                Error.lanzarError(502, aLex.getLinea(), prevToken, currToken);
                break;
            case 20:
                Error.lanzarError(320, aLex.getLinea(), prevToken, currToken);
                break;
            case 21:
                Error.lanzarError(502, aLex.getLinea(), prevToken, currToken);
                break;
            case 22:
                Error.lanzarError(322, aLex.getLinea(), prevToken, currToken);
                break;
            case 23:
                Error.lanzarError(502, aLex.getLinea(), prevToken, currToken);
                break;
            case 24:
                Error.lanzarError(502, aLex.getLinea(), prevToken, currToken);
                break;
            case 25:
                Error.lanzarError(325, aLex.getLinea(), prevToken, currToken);
                break;
            case 26:
                Error.lanzarError(326, aLex.getLinea(), prevToken, currToken);
                break;
            case 27:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 28:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 29:
                Error.lanzarError(329, aLex.getLinea(), prevToken, currToken);
                break;
            case 30:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 31:
                Error.lanzarError(331, aLex.getLinea(), prevToken, currToken);
                break;
            case 32:
                Error.lanzarError(502, aLex.getLinea(), prevToken, currToken);
                break;
            case 33:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 34:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 35:
                Error.lanzarError(500, aLex.getLinea(), prevToken, currToken);
                break;
            case 36:
                Error.lanzarError(500, aLex.getLinea(), prevToken, currToken);
                break;
            case 37:
                Error.lanzarError(337, aLex.getLinea(), prevToken, currToken);
                break;
            case 38:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 39:
                Error.lanzarError(339, aLex.getLinea(), prevToken, currToken);
                break;
            case 40:
                Error.lanzarError(340, aLex.getLinea(), prevToken, currToken);
                break;
            case 41:
                Error.lanzarError(341, aLex.getLinea(), prevToken, currToken);
                break;
            case 42:
                Error.lanzarError(500, aLex.getLinea(), prevToken, currToken);
                break;
            case 43:
                Error.lanzarError(343, aLex.getLinea(), prevToken, currToken);
                break;
            case 44:
                Error.lanzarError(344, aLex.getLinea(), prevToken, currToken);
                break;
            case 45:
                Error.lanzarError(345, aLex.getLinea(), prevToken, currToken);
                break;
            case 46:
                Error.lanzarError(346, aLex.getLinea(), prevToken, currToken);
                break;
            case 47:
                Error.lanzarError(347, aLex.getLinea(), prevToken, currToken);
                break;
            case 48:
                Error.lanzarError(348, aLex.getLinea(), prevToken, currToken);
                break;
            case 49:
                Error.lanzarError(349, aLex.getLinea(), prevToken, currToken);
                break;
            case 50:
                Error.lanzarError(350, aLex.getLinea(), prevToken, currToken);
                break;
            case 51:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 52:
                Error.lanzarError(352, aLex.getLinea(), prevToken, currToken);
                break;
            case 53:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 54:
                Error.lanzarError(354, aLex.getLinea(), prevToken, currToken);
                break;
            case 55:
                Error.lanzarError(355, aLex.getLinea(), prevToken, currToken);
                break;
            case 56:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 57:
                Error.lanzarError(357, aLex.getLinea(), prevToken, currToken);
                break;
            case 58:
                Error.lanzarError(358, aLex.getLinea(), prevToken, currToken);
                break;
            case 59:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 60:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 61:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 62:
                Error.lanzarError(362, aLex.getLinea(), prevToken, currToken);
                break;
            case 63:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 64:
                Error.lanzarError(501, aLex.getLinea(), prevToken, currToken);
                break;
            case 65:
                Error.lanzarError(501, aLex.getLinea(), prevToken, currToken);
                break;
            case 66:
                Error.lanzarError(501, aLex.getLinea(), prevToken, currToken);
                break;
            case 67:
                Error.lanzarError(367, aLex.getLinea(), prevToken, currToken);
                break;
            case 68:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 69:
                Error.lanzarError(369, aLex.getLinea(), prevToken, currToken);
                break;
            case 70:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 71:
                Error.lanzarError(371, aLex.getLinea(), prevToken, currToken);
                break;
            case 72:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 73:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 74:
                Error.lanzarError(500, aLex.getLinea(), prevToken, currToken);
                break;
            case 75:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 76:
                Error.lanzarError(502, aLex.getLinea(), prevToken, currToken);
                break;
            case 77:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 78:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 79:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 80:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 81:
                Error.lanzarError(381, aLex.getLinea(), prevToken, currToken);
                break;
            case 82:
                Error.lanzarError(382, aLex.getLinea(), prevToken, currToken);
                break;
            case 83:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 84:
                Error.lanzarError(384, aLex.getLinea(), prevToken, currToken);
                break;
            case 85:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 86:
                Error.lanzarError(386, aLex.getLinea(), prevToken, currToken);
                break;
            case 87:
                Error.lanzarError(503, aLex.getLinea(), prevToken, currToken);
                break;
            case 88:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 89:
                Error.lanzarError(389, aLex.getLinea(), prevToken, currToken);
                break;
            case 90:
                Error.lanzarError(390, aLex.getLinea(), prevToken, currToken);
                break;
            case 91:
                Error.lanzarError(391, aLex.getLinea(), prevToken, currToken);
                break;
            case 92:
                Error.lanzarError(392, aLex.getLinea(), prevToken, currToken);
                break;
            case 93:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 94:
                Error.lanzarError(394, aLex.getLinea(), prevToken, currToken);
                break;
            case 95:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 96:
                Error.lanzarError(396, aLex.getLinea(), prevToken, currToken);
                break;
            case 97:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 98:
                Error.lanzarError(398, aLex.getLinea(), prevToken, currToken);
                break;
            case 99:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 100:
                Error.lanzarError(400, aLex.getLinea(), prevToken, currToken);
                break;
            case 101:
                Error.lanzarError(504, aLex.getLinea(), prevToken, currToken);
                break;
            case 102:
                Error.lanzarError(505, aLex.getLinea(), prevToken, currToken);
                break;
            case 103:
                Error.lanzarError(403, aLex.getLinea(), prevToken, currToken);
                break;
            case 104:
                Error.lanzarError(404, aLex.getLinea(), prevToken, currToken);
                break;
            case 105:
                Error.lanzarError(405, aLex.getLinea(), prevToken, currToken);
                break;
            case 106:
                Error.lanzarError(406, aLex.getLinea(), prevToken, currToken);
                break;

        }
    }

    private void definirGramaticaNumeros() {
        gramaticaNumeros.put(1, 1);
        gramaticaNumeros.put(2, 2);
        gramaticaNumeros.put(3, 2);
        gramaticaNumeros.put(4, 1);
        gramaticaNumeros.put(5, 3);
        gramaticaNumeros.put(6, 1);
        gramaticaNumeros.put(7, 3);
        gramaticaNumeros.put(8, 1);
        gramaticaNumeros.put(9, 2);
        gramaticaNumeros.put(10, 1);
        gramaticaNumeros.put(11, 1);
        gramaticaNumeros.put(12, 3);
        gramaticaNumeros.put(13, 4);
        gramaticaNumeros.put(14, 1);
        gramaticaNumeros.put(15, 1);
        gramaticaNumeros.put(16, 4);
        gramaticaNumeros.put(17, 4);
        gramaticaNumeros.put(18, 5);
        gramaticaNumeros.put(19, 2);
        gramaticaNumeros.put(20, 0);
        gramaticaNumeros.put(21, 3);
        gramaticaNumeros.put(22, 0);
        gramaticaNumeros.put(23, 3);
        gramaticaNumeros.put(24, 3);
        gramaticaNumeros.put(25, 3);
        gramaticaNumeros.put(26, 1);
        gramaticaNumeros.put(27, 0);
        gramaticaNumeros.put(28, 2);
        gramaticaNumeros.put(29, 5);
        gramaticaNumeros.put(30, 4);
        gramaticaNumeros.put(31, 1);
        gramaticaNumeros.put(32, 1);
        gramaticaNumeros.put(33, 1);
        gramaticaNumeros.put(34, 1);
        gramaticaNumeros.put(35, 7);
        gramaticaNumeros.put(36, 5);
        gramaticaNumeros.put(37, 4);
        gramaticaNumeros.put(38, 0);
        gramaticaNumeros.put(39, 2);
        gramaticaNumeros.put(40, 0);
        gramaticaNumeros.put(41, 4);
        gramaticaNumeros.put(42, 4);
        gramaticaNumeros.put(43, 3);
        gramaticaNumeros.put(44, 1);
        gramaticaNumeros.put(45, 1);
        gramaticaNumeros.put(46, 3);
        gramaticaNumeros.put(47, 1);
        gramaticaNumeros.put(48, 4);
        gramaticaNumeros.put(49, 0);
        gramaticaNumeros.put(50, 2);
        gramaticaNumeros.put(51, 0);
    }

    private void definirgramaticaParteIzquierda() {
        gramaticaParteIzquierda.put(1, "P'");
        gramaticaParteIzquierda.put(2, "P");
        gramaticaParteIzquierda.put(3, "P");
        gramaticaParteIzquierda.put(4, "P");
        gramaticaParteIzquierda.put(5, "E");
        gramaticaParteIzquierda.put(6, "E");
        gramaticaParteIzquierda.put(7, "U");
        gramaticaParteIzquierda.put(8, "U");
        gramaticaParteIzquierda.put(9, "V'");
        gramaticaParteIzquierda.put(10, "V'");
        gramaticaParteIzquierda.put(11, "V");
        gramaticaParteIzquierda.put(12, "V");
        gramaticaParteIzquierda.put(13, "V");
        gramaticaParteIzquierda.put(14, "V");
        gramaticaParteIzquierda.put(15, "V");
        gramaticaParteIzquierda.put(16, "S");
        gramaticaParteIzquierda.put(17, "S");
        gramaticaParteIzquierda.put(18, "S");
        gramaticaParteIzquierda.put(19, "L");
        gramaticaParteIzquierda.put(20, "L");
        gramaticaParteIzquierda.put(21, "Q");
        gramaticaParteIzquierda.put(22, "Q");
        gramaticaParteIzquierda.put(23, "S");
        gramaticaParteIzquierda.put(24, "S");
        gramaticaParteIzquierda.put(25, "S");
        gramaticaParteIzquierda.put(26, "X");
        gramaticaParteIzquierda.put(27, "X");
        gramaticaParteIzquierda.put(28, "S");
        gramaticaParteIzquierda.put(29, "B");
        gramaticaParteIzquierda.put(30, "B");
        gramaticaParteIzquierda.put(31, "T");
        gramaticaParteIzquierda.put(32, "T");
        gramaticaParteIzquierda.put(33, "T");
        gramaticaParteIzquierda.put(34, "B");
        gramaticaParteIzquierda.put(35, "B");
        gramaticaParteIzquierda.put(36, "Y");
        gramaticaParteIzquierda.put(37, "Y");
        gramaticaParteIzquierda.put(38, "Y");
        gramaticaParteIzquierda.put(39, "Z");
        gramaticaParteIzquierda.put(40, "Z");
        gramaticaParteIzquierda.put(41, "F");
        gramaticaParteIzquierda.put(42, "F1");
        gramaticaParteIzquierda.put(43, "F2");
        gramaticaParteIzquierda.put(44, "H");
        gramaticaParteIzquierda.put(45, "H");
        gramaticaParteIzquierda.put(46, "A");
        gramaticaParteIzquierda.put(47, "A");
        gramaticaParteIzquierda.put(48, "K");
        gramaticaParteIzquierda.put(49, "K");
        gramaticaParteIzquierda.put(50, "C");
        gramaticaParteIzquierda.put(51, "C");
    }

}
