package Analizador.ASin;

public class Accion {

    private boolean desplazar;
    private boolean reducir;
    private boolean aceptar;
    private Integer valor;

    public Accion(String tipoAccion, int valor) {
        if (tipoAccion.equals("desplazar")) {
            this.desplazar = true;
            this.reducir = false;
            this.aceptar = false;
        }
        if (tipoAccion.equals("reducir")) {
            this.desplazar = false;
            this.reducir = true;
            this.aceptar = false;
        }
        if (tipoAccion.equals("aceptar")) {
            this.desplazar = false;
            this.reducir = false;
            this.aceptar = true;
        }
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public boolean esDesplazar() {
        return this.desplazar;
    }

    public boolean esReducir() {
        return this.reducir;
    }

    public boolean esAceptar() {
        return this.aceptar;
    }

}
