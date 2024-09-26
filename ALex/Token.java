package Analizador.ALex;

public class Token {
    private int codigo;
    private String cadena;
    private Integer numero; // podría contener el valor o el numero de ident en la tabla de simbolos

    public Token(int newCod, String newCad) {
        this.codigo = newCod;
        this.cadena = newCad;
    }

    public Token(int newCod, int newNum) {
        this.codigo = newCod;
        this.numero = newNum;
    }

    public Token(int newCod) {
        this.codigo = newCod;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCadena() {
        return cadena;
    }

    public Integer getNumero() {
        return numero;
    }

    // hacer función to string
    public String tokString() {
        if (cadena != null)
            return ("< " + codigo + " , \"" + cadena + "\" >");
        if (numero != null)
            return ("< " + codigo + " , " + numero + " >");
        else
            return ("< " + codigo + " , >");

    }

}
