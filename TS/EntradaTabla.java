package Analizador.TS;

import java.util.ArrayList;

public class EntradaTabla {
    private int posicion;
    private String cadena;
    private int numParams;
    private String tipo;

    private ArrayList<String> tipoParams;
    private String tipoDevolucion;
    private String etiqueta;

    private int despl;

    public EntradaTabla(int pos, String cadena) {
        this.posicion = pos;
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getNumParams() {
        return this.numParams;
    }

    public String getTipo() {
        return this.tipo;
    }

    public ArrayList<String> getTipoParams() {
        return this.tipoParams;
    }

    public String getTipoDevolucion() {
        return this.tipoDevolucion;
    }

    public String getEtiqueta() {
        return this.etiqueta;
    }

    public int getDespl() {
        return this.despl;
    }

    public void setDespl(int despl) {
        this.despl = despl;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNumParams(int numParams) {
        this.numParams = numParams;
    }

    public void setTipoParams(ArrayList<String> params) {
        this.tipoParams = (ArrayList<String>) params.clone();
    }

    public void setTipoDevolucion(String tipoDev) {
        this.tipoDevolucion = tipoDev;
    }

    public void setEtiq(String etiq) {
        this.etiqueta = etiq;
    }

}
