package org.ayed.ia;

import org.ayed.juego.Monstruo;
import org.ayed.juego.Personaje;

public class DetectorJugador {

    /*
     * DETECTOR DEL JUGADOR
     *
     * PRE:
     * - posiciones válidas de monstruo y jugador
     *
     * POS:
     * - retorna true si el jugador está dentro del rango de detección
     * - retorna false en caso contrario
     */

    public boolean detectar(Monstruo m, Personaje p) {

        int distancia =
                Math.abs(m.getPosicion().getFila()
                - p.getPosicion().getFila())
                +
                Math.abs(m.getPosicion().getColumna()
                - p.getPosicion().getColumna());

        return distancia <= 3;
    }
}