package Analizador.TS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, EntradaTabla> tabla; // para que se busque por el identificador
    private Map<Integer, String> tablaAux; // para que se busque por el número de entrada
    private int id;

    private int posVoy;

    public TablaSimbolos(int id) {
        this.tabla = new HashMap<String, EntradaTabla>();
        this.tablaAux = new HashMap<Integer, String>();
        this.posVoy = id == 0 ? 0 : 1;
        this.id = id;
    }

    public Integer checkTabla(String identificador) {
        EntradaTabla aux = tabla.get(identificador);
        if (aux != null) {
            return aux.getPosicion(); // devuelve la posición en la tabla de símbolos
        } else {
            return null;
        }
    }

    public String getNombreIdent(int pos) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        return ent.getEtiqueta();
    }

    public void añadirTipoYDespl(int pos, String tipo, int despl) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        ent.setDespl(despl);
        ent.setTipo(tipo);
    }

    public void añadirTipo(int pos, String tipo) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        ent.setTipo(tipo);
    }

    public void añadirTipoDevolucion(int pos, String tipo) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        ent.setTipoDevolucion(tipo);
    }

    public void añadirNumYTipoParams(int pos, ArrayList<String> tipoParams) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        ent.setNumParams(tipoParams.size());
        ent.setTipoParams(tipoParams);
        ent.setEtiq(ent.getCadena());
    }

    public String buscaTipo(Integer pos) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        if (ent != null)
            return ent.getTipo();
        return null;
    }

    public int añadirEntrada(String cadena) {
        EntradaTabla nuevaEntrada = new EntradaTabla(posVoy, cadena);
        tabla.put(cadena, nuevaEntrada);
        tablaAux.put(posVoy, cadena);
        int aux = posVoy;
        posVoy += 2;
        return aux;
    }

    public void liberarTSL(FileWriter fileOut) throws IOException {
        print(fileOut);
    }

    public void liberarTSG(FileWriter fileOut) throws IOException {
        print(fileOut);
    }

    public int buscaNumParams(int pos) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        return ent.getNumParams();
    }

    public ArrayList<String> buscaTipoParams(int pos) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        return ent.getTipoParams();
    }

    public String buscaTipoRet(int pos) {
        EntradaTabla ent = tabla.get(tablaAux.get(pos));
        return ent.getTipoDevolucion();
    }

    public void print(FileWriter fileOut) throws IOException {
        fileOut.write("TABLA #" + this.id + " :" + '\n');
        for (int i = this.id == 0 ? 0 : 1; i < posVoy; i += 2) {
            String cad = tablaAux.get(i);
            EntradaTabla aux = tabla.get(cad);
            fileOut.write(" * Lexema : \'" + aux.getCadena() + "\'" + '\n');
            fileOut.write("   Atributos :" + '\n');
            fileOut.write("   + tipo : \'" + aux.getTipo() + "\'" + '\n');
            if (aux.getTipo().equals("function")) {
                fileOut.write("     + numParam : \'" + aux.getNumParams() + "\'" + '\n');
                for (int j = 0; j < aux.getTipoParams().size(); j++) {
                    fileOut.write("       + TipoParam" + (j + 1) + " : \'" + aux.getTipoParams().get(j) + "\'" + '\n');
                }
                fileOut.write("       + TipoRetorno : \'" + aux.getTipoDevolucion() + "\'" + '\n');
                fileOut.write("     + EtiqFuncion : \'" + aux.getEtiqueta() + "\'" + '\n');
            } else {
                fileOut.write("   + despl : " + aux.getDespl() + '\n');
            }
            fileOut.write("---------------------------------\n");
        }

    }
}
