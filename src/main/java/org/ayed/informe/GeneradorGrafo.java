package org.ayed.informe;

import org.ayed.tda.grafo.Grafo;
import org.ayed.juego.Posicion;

/**
 * Generador de grafos tipo grilla para pruebas experimentales.
 */
public class GeneradorGrafo {

    /**
     * Crea un grafo cuadrado tamaño n x n.
     *
     * Cada nodo se conecta con:
     * - derecha
     * - abajo
     *
     * PRE: n > 0
     * POS: grafo conectado tipo grid
     */
    public static Grafo<Posicion> crear(int n) {
        Grafo<Posicion> grafo = new Grafo<>();
        Posicion[][] matriz = new Posicion[n][n];

        // crear nodos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = new Posicion(i, j);
                grafo.agregarVertice(matriz[i][j]);
            }
        }

        // conectar nodos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i + 1 < n) {
                    grafo.agregarArista(matriz[i][j], matriz[i + 1][j], 1);
                }

                if (j + 1 < n) {
                    grafo.agregarArista(matriz[i][j], matriz[i][j + 1], 1);
                }
            }
        }

        return grafo;
    }
}