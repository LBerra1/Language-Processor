package Analizador.ASin;

import Analizador.ALex.Token;
import java.util.ArrayList;

public class EntradaPila {
    private int estado;
    private Token token;
    private String noTerminal;
    private String tipo;
    private ArrayList<String> tipoParams;
    private String tipoRet;
    private int ancho;
    private boolean hayBreak;
    private int pos;

    public EntradaPila() {
        this.tipoParams = new ArrayList<>();
    }

    public EntradaPila(int estado) {
        this.estado = estado;
    }

    public EntradaPila(Token token) {
        this.token = token;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getTipoRet() {
        return this.tipoRet;
    }

    public ArrayList<String> getTipoParams() {
        return this.tipoParams;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAncho() {
        return this.ancho;
    }

    public void setNoTerminal(String noTerminal) {
        this.noTerminal = noTerminal;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void addTipoParams(String tipo) {
        this.tipoParams.add(tipo);
    }

    public void addTipoParams(ArrayList<String> list) {
        this.tipoParams.addAll(list);
    }

    public void setTipoRet(String tipoRet) {
        this.tipoRet = tipoRet;
    }

    public void setHayBreak(boolean b) {
        this.hayBreak = b;
    }

    public int getEstado() {
        return estado;
    }

    public Token getToken() {
        return token;
    }

    public String getNoTerminal() {
        return noTerminal;
    }

    public int getNumParams() {
        return tipoParams.size();
    }

    public boolean getHayBreak() {
        return hayBreak;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
