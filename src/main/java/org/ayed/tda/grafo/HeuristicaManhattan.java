package org.ayed.tda.grafo;

import org.ayed.juego.Posicion;

public class HeuristicaManhattan

implements Heuristica<Posicion> {

    @Override
    public int calcularPuntaje(
            Posicion origen,
            Posicion destino
    ) {

        return Math.abs(
                origen.getFila()
                - destino.getFila()
        )
        +
        Math.abs(
                origen.getColumna()
                - destino.getColumna()
        );
    }
}