package Analizador.ALex;

import java.util.HashMap;
import java.util.Map;

public class MatrizTransicion {
    private Map<Integer, Map<Character, FuturaAccionOError>> matriz;

    public MatrizTransicion() {
        this.matriz = new HashMap<>();

    }

    // ERROR
    public void addTransition(int currState, char currChar, int error) {
        // Si aun no hay entrada para ese estado
        if (this.matriz.get(currState) == null) {
            this.matriz.put(currState, new HashMap<>());
        }
        this.matriz.get(currState).put(currChar, new FuturaAccionOError(error));
    }

    // ACCION SEMANTICA
    public void addTransition(int currState, char currChar, int nextState, String accSemantica) {
        if (this.matriz.get(currState) == null) {
            this.matriz.put(currState, new HashMap<>());
        }
        this.matriz.get(currState).put(currChar, new FuturaAccionOError(nextState, accSemantica));
    }

    public FuturaAccionOError getAction(int currState, char currChar) {
        return matriz.get(currState).get(currChar);
    }
}
