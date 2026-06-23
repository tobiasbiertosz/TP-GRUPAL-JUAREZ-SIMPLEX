package org.ayed.informe;

import org.ayed.juego.Posicion;
import org.ayed.tda.grafo.*;

public class MainComplejidad {

    public static void main(String[] args) {

        int[] tamaños = {10, 30, 50, 100};

        for (int n : tamaños) {

            System.out.println("\n========================");
            System.out.println("Tamaño: " + n + " x " + n);
            System.out.println("========================");

            Grafo<Posicion> grafo = GeneradorGrafo.crear(n);

            Posicion origen = new Posicion(0, 0);
            Posicion destino = new Posicion(n - 1, n - 1);

            // =========================
            // A*
            // =========================
            Estadisticas.reset();

            long inicioA = System.nanoTime();
            grafo.buscarAStar(origen, destino, new HeuristicaManhattan() );
            long finA = System.nanoTime();

            System.out.println("\n[A*]");
            System.out.println("Tiempo: " + (finA - inicioA));
            System.out.println("Operaciones: " + Estadisticas.get());

            // =========================
            // BFS
            // =========================
            Estadisticas.reset();

            long inicioB = System.nanoTime();
            grafo.bfs(origen, destino);
            long finB = System.nanoTime();

            System.out.println("\n[BFS]");
            System.out.println("Tiempo: " + (finB - inicioB));
            System.out.println("Operaciones: " + Estadisticas.get());

            // =========================
            // DIJKSTRA
            // =========================
            Estadisticas.reset();

            long inicioD = System.nanoTime();
            grafo.dijkstra(origen, destino);
            long finD = System.nanoTime();

            System.out.println("\n[Dijkstra]");
            System.out.println("Tiempo: " + (finD - inicioD));
            System.out.println("Operaciones: " + Estadisticas.get());
        }
    }
}