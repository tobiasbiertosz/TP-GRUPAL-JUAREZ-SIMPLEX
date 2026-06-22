package org.ayed;

import org.ayed.juego.Combate;
import org.ayed.juego.Mazmorra;
import org.ayed.juego.Monstruo;
import org.ayed.juego.Personaje;
import org.ayed.juego.Posicion;


import org.ayed.tda.grafo.Heuristica;
import org.ayed.tda.grafo.HeuristicaManhattan;
import org.ayed.ia.ControladorMonstruo;
import org.ayed.tda.grafo.Grafo;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		

		Personaje heroe = new Personaje("Guerrero", 100, 20, new Posicion(0, 0));

		Monstruo goblin = new Monstruo("Goblin", 50, 10, new Posicion(1, 1));

		Combate combate = new Combate();

		combate.atacar(heroe, goblin);

		System.out.println(goblin.getVidaActual());

		Mazmorra mazmorra = new Mazmorra(5, 5);

		System.out.println(mazmorra.getCelda(2, 3).getTipo());

		mazmorra.colocarPared(2, 2);

		System.out.println(mazmorra.getCelda(2, 2).getTipo());

	}

}



//hola grupo
