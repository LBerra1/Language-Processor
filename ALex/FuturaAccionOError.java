package Analizador.ALex;

public class FuturaAccionOError {
    private int sigEstado;
    private String accionSem;
    private int numError;
    private boolean error = false;

    FuturaAccionOError(Integer sigEstado, String accSemantica) {
        this.sigEstado = sigEstado;
        this.accionSem = accSemantica;
    }

    FuturaAccionOError(Integer err) {
        this.error = true;
        this.numError = err;
    }

    public boolean isError() {
        return this.error;
    }

    public String getAccionSem() {
        return this.accionSem;
    }

    public int getSigEstado() {
        return this.sigEstado;
    }

    public int getNumError() {
        return this.numError;
    }
}
