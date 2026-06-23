package org.ayed.informe;

/**
 * Contador global de operaciones para análisis
 * Se incrementa manualmente en los algoritmos (A*, BFS, etc.)
 */
public class Estadisticas {

    private static long operaciones = 0;

    /**
     * Reinicia el contador.
     * PRE: -
     * POS: operaciones = 0
     */
    public static void reset() {
        operaciones = 0;
    }

    /**
     * Incrementa en 1 el contador de operaciones.
     * PRE: -
     * POS: operaciones++
     */
    public static void op() {
        operaciones++;
    }

    /**
     * Devuelve la cantidad de operaciones registradas.
     *
     * PRE: -
     * POS: no modifica estado
     */
    public static long get() {
        return operaciones;
    }
}