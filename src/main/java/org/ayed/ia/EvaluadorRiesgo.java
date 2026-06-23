package org.ayed.ia;

import org.ayed.tda.grafo.Grafo;
import org.ayed.juego.Posicion;

/*
 * EVALUADOR DE RIESGO
 *
 * PRE:
 * - acceso al grafo del mapa
 *
 * POS:
 * - devuelve el costo de moverse a una posición
 * - penaliza zonas peligrosas
 */

public class EvaluadorRiesgo {

    private final Grafo<Posicion> grafo;

    public EvaluadorRiesgo(Grafo<Posicion> grafo) {
        this.grafo = grafo;
    }

    public int costo(Posicion origen, Posicion destino) {

        // costo base del grafo
        int base = grafo.obtenerArista(origen, destino);

        // aquí podrías aumentar riesgo 
        // ejemplo simple: sin modificación

        return base;
    }
}