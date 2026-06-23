package org.ayed.ia;

import org.ayed.juego.Mazmorra;
import org.ayed.juego.Monstruo;
import org.ayed.juego.Personaje;
import org.ayed.juego.Posicion;
import org.ayed.tda.grafo.Grafo;
import org.ayed.tda.grafo.HeuristicaManhattan;

import org.ayed.tda.vector.*;
/*
 * CONTROLADOR DEL MONSTRUO
 *
 * PRE:
 * - monstruo, jugador y mazmorra inicializados
 * - grafo del mapa disponible
 *
 * POS:
 * - actualiza comportamiento del monstruo:
 *   ALERTA -> persigue con A*
 *   EXPLORACION -> movimiento aleatorio
 */

public class ControladorMonstruo {

    private final DetectorJugador detector = new DetectorJugador();
    private final MovimientoAleatorio random = new MovimientoAleatorio();

    private EstadoAlerta estado = EstadoAlerta.EXPLORACION;

    public void actualizar(Monstruo m, Personaje p, Mazmorra mazmorra, Grafo<Posicion> grafo) {

        if (detector.detectar(m, p)) {
            estado = EstadoAlerta.ALERTA;
            VectorDinamico<Posicion> camino =grafo.buscarAStar(m.getPosicion(), p.getPosicion(), new HeuristicaManhattan());

            if (camino.tamanio() > 1) {
                Posicion next = camino.obtener(1);
                m.mover(mazmorra, next.getFila(), next.getColumna());
            }

        } else {
            estado = EstadoAlerta.EXPLORACION;
            random.moverAleatorio(m, mazmorra);
        }
    }

    public EstadoAlerta getEstado() {
        return estado;
    }
}