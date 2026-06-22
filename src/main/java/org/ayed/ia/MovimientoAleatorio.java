package org.ayed.ia;

import org.ayed.juego.Monstruo;
import org.ayed.juego.Posicion;
import org.ayed.juego.Mazmorra;

/*
 * MOVIMIENTO ALEATORIO
 *
 * PRE:
 * - monstruo en una posición válida del mapa
 *
 * POS:
 * - mueve al monstruo a una celda vecina aleatoria
 */

public class MovimientoAleatorio {

	public void moverAleatorio(Monstruo m, Mazmorra mazmorra) {

	    int[][] dirs = {
	            {1,0}, {-1,0}, {0,1}, {0,-1}
	    };

	    int i = (int)(Math.random() * 4);

	    Posicion p = m.getPosicion();

	    int nuevaFila = p.getFila() + dirs[i][0];
	    int nuevaColumna = p.getColumna() + dirs[i][1];

	    m.mover(mazmorra, nuevaFila, nuevaColumna);
	}
}