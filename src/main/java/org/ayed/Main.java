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
		
		// ----------------------------
        // MAPA (GRAFO SIMPLE)
        // ----------------------------
        Grafo<Posicion> grafo = new Grafo<>();

        Posicion a = new Posicion(0, 0);
        Posicion b = new Posicion(0, 1);
        Posicion c = new Posicion(0, 2);
        Posicion d = new Posicion(1, 0);
        Posicion e = new Posicion(1, 1);

        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarVertice(c);
        grafo.agregarVertice(d);
        grafo.agregarVertice(e);

        grafo.agregarArista(a, b, 1);
        grafo.agregarArista(b, c, 1);
        grafo.agregarArista(a, d, 2);
        grafo.agregarArista(d, e, 1);
        grafo.agregarArista(e, c, 1);

        // ----------------------------
        // ENTIDADES
        // ----------------------------
        Mazmorra mazmorra = new Mazmorra(3, 3);

        Monstruo monstruo = new Monstruo("Goblin", 50, 10, a);

        Personaje jugador = new Personaje("Heroe", 100, 15, c);

        // ----------------------------
        // IA
        // ----------------------------
        ControladorMonstruo ia = new ControladorMonstruo();

        // ----------------------------
        // SIMULACION (TICKS)
        // ----------------------------
        for (int i = 0; i < 5; i++) {

            System.out.println("\n--- TICK " + i + " ---");

            ia.actualizar(monstruo, jugador, mazmorra, grafo);

            System.out.println("Monstruo en: "
                    + monstruo.getPosicion().getFila()
                    + ", "
                    + monstruo.getPosicion().getColumna()
            );

            System.out.println("Estado: " + ia.getEstado());

            // mover jugador para ver comportamiento dinámico (opcional)
            if (i == 2) {
                jugador = new Personaje("Heroe", 100, 15, a);
            }
        }

		Personaje heroe = new Personaje("Guerrero", 100, 20, new Posicion(0, 0));

		Monstruo goblin = new Monstruo("Goblin", 50, 10, new Posicion(1, 1));

		Combate combate = new Combate();

		combate.atacar(heroe, goblin);

		System.out.println(goblin.getVidaActual());

		///Mazmorra mazmorra = new Mazmorra(5, 5);

		System.out.println(mazmorra.getCelda(2, 2).getTipo());

		mazmorra.colocarPared(2, 2);

		System.out.println(mazmorra.getCelda(2, 2).getTipo());

	}

}



//hola grupo
