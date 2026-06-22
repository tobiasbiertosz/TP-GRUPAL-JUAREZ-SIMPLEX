package org.ayed.tda.grafo;

import java.util.*;

import org.ayed.tda.colaPrioridad.*;
import org.ayed.tda.comparador.Comparador;


/**
 * Esta implementación de Grafo representa un grafo no
 * dirigido.
 * Admite tipo genérico para indicarle el tipo de dato
 * de los vértices. Esto permite agregar datos/comportamiento
 * a los vértices (por ejemplo, si queremos buscar un AEM en
 * un contexto de red social), además de permitir la
 * implementación del algoritmo A*, donde la heurística se
 * define a partir de los vértices.
 */
public class Grafo<T> {
    protected final Map<T, Map<T, Integer>> adyacencias;
    protected static final int INFINITO = 99999; // Ajustar si es necesario.
    protected static final int SIN_PESO = 1; // Ajustar si es necesario.

    /**
     * Constructor de Grafo.
     */
    public Grafo() {
        adyacencias = new HashMap<>();
    }

    /**
     * Constructor de copia de Grafo.
     *
     * @param grafo Grafo a copiar.
     *              No puede ser nulo.
     * @throws ExcepcionGrafo si el grafo es nulo.
     */
    public Grafo(Grafo<T> grafo) {
        if (grafo == null) {
            throw new ExcepcionGrafo("El grafo no puede ser nulo.");
        }
        adyacencias = new HashMap<>();
        for (T vertice : grafo.adyacencias.keySet()) {
            adyacencias.put(vertice, new HashMap<>(grafo.obtenerAdyacentes(vertice)));
        }
    }

    /**
     * Agrega un vértice no existente al grafo.
     *
     * @param vertice Vértice a agregar.
     *                No puede ser nulo.
     *                No puede existir previamente en el grafo.
     * @throws ExcepcionGrafo si el vértice es nulo,
     *                        o si ya existía en el grafo.
     */
    public void agregarVertice(T vertice) {
        if (vertice == null) {
            throw new ExcepcionGrafo("El vértice no puede ser nulo.");
        }
        if (adyacencias.containsKey(vertice)) {
            throw new ExcepcionGrafo("El vértice ya existe.");
        }
        adyacencias.put(vertice, new HashMap<>());
    }

    /**
     * Elimina un vértice del grafo, junto con todas las
     * adyacencias.
     *
     * @param vertice Vértice a eliminar.
     *                Debe existir en el grafo.
     * @throws ExcepcionGrafo si el vértice no existe en el grafo.
     */
    public void eliminarVertice(T vertice) {
        if (!adyacencias.containsKey(vertice)) {
            throw new ExcepcionGrafo("El vértice no existe.");
        }
        adyacencias.remove(vertice);
        for (Map<T, Integer> adyacente : adyacencias.values()) {
            adyacente.remove(vertice);
        }
    }

    /**
     * Agrega una arista al grafo. Sobreescribe una arista anterior,
     * si existía.
     *
     * @param origen  Vértice origen.
     *                Debe existir en el grafo.
     * @param destino Vértice destino.
     *                Debe existir en el grafo.
     * @throws ExcepcionGrafo si alguno de los dos vértices no
     *                        existe en el grafo.
     */
    public void agregarArista(T origen, T destino, int peso) {
        if (!adyacencias.containsKey(origen) || !adyacencias.containsKey(destino)) {
            throw new ExcepcionGrafo("La arista no es válida.");
        }
        obtenerAdyacentes(origen).put(destino, peso);
        obtenerAdyacentes(destino).put(origen, peso);
    }

    /**
     * Elimina una arista del grafo. Si no existe, no hace nada.
     *
     * @param origen  Vértice origen.
     *                Debe existir en el grafo.
     * @param destino Vértice destino.
     *                Debe existir en el grafo.
     * @throws ExcepcionGrafo si alguno de los dos vértices no
     *                        existe en el grafo.
     */
    public void eliminarArista(T origen, T destino) {
        if (!adyacencias.containsKey(origen) || !adyacencias.containsKey(destino)) {
            throw new ExcepcionGrafo("La arista no es válida.");
        }
        obtenerAdyacentes(origen).remove(destino);
        obtenerAdyacentes(destino).remove(origen);
    }

    /**
     * Obtiene una arista existente del grafo.
     *
     * @param origen  Vértice origen.
     *                Debe existir en el grafo.
     * @param destino Vértice destino.
     *                Debe existir en el grafo.
     * @throws ExcepcionGrafo si alguno de los dos vértices no
     *                        existe en el grafo, o si la arista
     *                        no existe en el grafo.
     */
    public int obtenerArista(T origen, T destino) {
        if (!adyacencias.containsKey(origen) || !adyacencias.containsKey(destino)) {
            throw new ExcepcionGrafo("La arista no es válida.");
        }
        if (!obtenerAdyacentes(origen).containsKey(destino)) {
            throw new ExcepcionGrafo("La arista no existe.");
        }
        return obtenerAdyacentes(origen).get(destino);
    }

    /**
     * Obtiene las adyacencias de un vértice existente del grafo.
     *
     * @param vertice Vértice a obtener.
     *                Debe existir en el grafo.
     * @throws ExcepcionGrafo si el vértice no existe en el grafo.
     */
    public Map<T, Integer> obtenerAdyacentes(T vertice) {
        if (!adyacencias.containsKey(vertice)) {
            throw new ExcepcionGrafo("El vértice no existe.");
        }
        return adyacencias.get(vertice);
    }
    
    /*
     * ALGORITMO A*
     *
     * Objetivo:
     * Encontrar el camino más corto entre un origen y un destino en un grafo
     * ponderado usando una heurística.
     *
     * Fórmula:
     * f(n) = g(n) + h(n)
     *
     * Donde:
     * g(n): costo real desde el origen hasta el nodo actual
     * h(n): estimación del costo desde el nodo actual hasta el destino
     *        (en este caso, heurística Manhattan)
     *
     * Idea general:
     * - Expande primero los nodos con menor f(n)
     * - Usa una cola de prioridad para ordenar exploración
     * - Evita reexplorar nodos ya visitados
     * - Guarda padres para reconstruir el camino final
     *
     * Complejidad:
     * Tiempo: O(E log V)
     * Espacio: O(V)
     */
    
    public List<T> buscarAStar(T origen, T destino, Heuristica<T> h) {

        /*
         * PRE:
         * - origen y destino existen en el grafo
         * - h != null
         *
         * POS:
         * - devuelve el camino más corto o lista vacía
         */

        Map<T, Integer> g = new HashMap<>();
        Map<T, Integer> f = new HashMap<>();
        Map<T, T> padre = new HashMap<>();
        Set<T> cerrados = new HashSet<>();

        g.put(origen, 0);
        f.put(origen, h.calcularPuntaje(origen, destino));

        Comparador<T> comp = new Comparador<T>() {
            @Override
            public int comparar(T a, T b) {
                return f.get(a) - f.get(b);
            }
        };

        ColaPrioridad<T> abiertos = new ColaPrioridad<>(comp);
        abiertos.agregar(origen);

        while (!abiertos.vacio()) {

            T actual = abiertos.eliminar();

            if (actual.equals(destino)) {
                return reconstruir(padre, destino);
            }

            cerrados.add(actual);

            for (T vecino : obtenerAdyacentes(actual).keySet()) {

                if (cerrados.contains(vecino)) continue;

                int costo = g.get(actual) + obtenerArista(actual, vecino);

                if (!g.containsKey(vecino) || costo < g.get(vecino)) {

                    g.put(vecino, costo);

                    f.put(vecino,
                            costo + h.calcularPuntaje(vecino, destino)
                    );

                    padre.put(vecino, actual);

                    abiertos.agregar(vecino);
                }
            }
        }

        return new ArrayList<>();
    }
    
    
    private List<T> reconstruir(Map<T, T> padre, T actual) {

        /*
         * PRE:
         * actual != null
         * padre contiene relaciones válidas del A*
         *
         * POS:
         * retorna camino desde origen hasta destino
         */

        List<T> camino = new ArrayList<>();

        while (actual != null) {
            camino.add(actual);
            actual = padre.get(actual);
        }

        Collections.reverse(camino);
        return camino;
    }
    
}
