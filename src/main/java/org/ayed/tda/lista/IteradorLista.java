package org.ayed.tda.lista;


import org.ayed.tda.iterador.ExcepcionNoHayDato;
import org.ayed.tda.iterador.ExcepcionOperacionNoPermitida;
import org.ayed.tda.iterador.Iterador;


class IteradorLista<T> implements Iterador<T> {
    private Lista<T> lista;
    private Nodo<T> cursor;
    private int indice;


    /**
     * Constructor de Iter.
     *
     * @param lista Lista a iterar.
     *
     */
    IteradorLista(Lista<T> lista) {
        this(lista, 0);
    }


    /**
     * Constructor de Iter.
     *
     * @param lista  Lista a iterar.
     * @param indice Índice inicial del iterador.
     *
     *               PRE: Recibe una lista y un ínidice.
     *               POS: El iterador se posiciona en el nodo (cursor)
     *               correspondiente al índice recibido.
     */
    IteradorLista(Lista<T> lista, int indice) {
        this.lista = lista;
        this.indice = indice;


        Nodo<T> nodoActual = lista.primero;
        int i = 0;


        while (i < indice) {
            nodoActual = nodoActual.getSiguiente();
            i++;
        }


        this.cursor = nodoActual;
    }


    /**
     * @return el dato actual.
     * @throws ExcepcionNoHayDato si no hay dato a acceder
     *                            (si el iterador está al final).
     *
     *                            PRE: El iterador no está al final de la lista.
     *                            POS: Devuelve el dato del nodo actual (cursor).
     */
    @Override
    public T dato() {
        if (this.cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato a acceder.");
        }


        return this.cursor.getDato();
    }


    /**
     * Evalúa si se puede avanzar el iterador.
     *
     * @return true si se puede avanzar.
     *
     *         PRE: -
     *         POS: Devuelve true si el iterador no está al final de la lista sino
     *         false (llego al final).
     */
    @Override
    public boolean haySiguiente() {
        return this.cursor != null;
    }


    /**
     * Avanza el iterador.
     *
     * @throws ExcepcionNoHayDato si no hay dato siguiente.
     *
     *                            PRE: El iterador no está al final de la lista.
     *                            POS: El iterador se posiciona en el siguiente nodo
     *                            de la lista.
     */
    @Override
    public void siguiente() {
        if (!this.haySiguiente()) {
            throw new ExcepcionNoHayDato("No hay dato siguiente.");
        }


        this.cursor = this.cursor.getSiguiente();


        this.indice++;
    }


    /**
     * Evalúa si se puede retroceder el iterador.
     *
     * @return true si se puede retroceder.
     *
     *         PRE: -
     *         POS: Devuelve true si el iterador no está al inicio de la lista sino
     *         false
     *         (llego al inicio).
     */
    @Override
    public boolean hayAnterior() {
        return this.indice > 0;
    }


    /**
     * Retrocede el iterador.
     *
     * @throws ExcepcionNoHayDato si no hay dato anterior.
     *
     *                            PRE: El iterador no está al inicio de la lista.
     *                            POS: El iterador se posiciona en el nodo anterior
     *                            de la lista.
     */
    @Override
    public void anterior() {
        if (!this.hayAnterior()) {
            throw new ExcepcionNoHayDato("No hay dato anterior.");
        }
        // Caso particular: Final de la lista
        if (this.cursor == null) {
            this.cursor = this.lista.ultimo;
        }
        // Caso general: Cualquier otro lado.
        else {
            this.cursor = this.cursor.getAnterior();
        }


        this.indice--;
    }


    /**
     * Agrega el dato indicado por parámetro antes
     * de la posición actual del iterador.
     * El iterador queda posicionado en la posición siguiente.
     * <p>
     * Ejemplos:
     *
     * <pre>
     * {@code
     * >> [0, 1, 5, 3]
     * >>     ^
     * agregar(4);
     * >> [0, 4, 1, 5, 3]
     * >>        ^
     * }
     * </pre>
     *
     * <pre>
     * {@code
     * >> [0, 1, 5, 3]
     * >>              ^ (final)
     * agregar(7);
     * >> [0, 1, 5, 3, 7]
     * >>                 ^
     * }
     * </pre>
     *
     * @param dato Dato a agregar.
     * @throws ExcepcionOperacionNoPermitida si la estructura no permite la
     *                                       operación.
     *                                       PRE: Recibe un dato a agregar.
     *                                       POS: Agrega un nuevo nodo con el dato
     *                                       recibido antes del nodo actual
     *                                       (cursor).
     *                                       Casos particulares: Cursor es null
     *                                       1) La lista está vacía.
     *                                       2) El cursor está al final de la lista.
     *
     *                                       Casos generales: Cursor no es null
     *                                       1) El cursor está en el medio de la
     *                                       lista.
     *                                       2) El cursor está al inicio de la
     *                                       lista.
     */
    @Override
    public void agregar(T dato) {
        if (dato == null) {
            throw new ExcepcionOperacionNoPermitida("La lista no permite agregar datos nulos.");
        }


        Nodo<T> nuevo = new Nodo<>(dato);


        if (this.cursor == null) {
            nuevo.setAnterior(this.lista.ultimo);


            if (this.lista.ultimo != null) {
                this.lista.ultimo.setSiguiente(nuevo);
            } else {
                this.lista.primero = nuevo;
            }
            this.lista.ultimo = nuevo;
        } else {
            Nodo<T> anterior = this.cursor.getAnterior();


            nuevo.setSiguiente(this.cursor);
            nuevo.setAnterior(anterior);
            this.cursor.setAnterior(nuevo);


            if (anterior != null) {
                anterior.setSiguiente(nuevo);
            } else {
                this.lista.primero = nuevo;
            }
        }


        // Sincronizo los contadores.
        this.lista.cantidadDatos++;
        this.indice++;
    }


    /**
     * Modifica el dato actual del iterador
     * por el indicado por parámetro.
     *
     * @throws ExcepcionNoHayDato si no hay dato a acceder
     *                            (si el iterador está al final).
     *
     *                            PRE: El iterador no está al final de la lista.
     *                            POS: Modifica el dato del nodo actual (cursor) por
     *                            el dato recibido.
     */
    @Override
    public void modificarDato(T dato) {
        if (this.cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato a acceder.");
        }


        this.cursor.setDato(dato);
    }


    /**
     * Elimina el dato actual del iterador.
     * El iterador queda posicionado en la posición actual.
     * <p>
     * Ejemplos:
     *
     * <pre>
     * {@code
     * >> [0, 1, 5, 3]
     * >>     ^
     * eliminar();
     * >> [0, 5, 3]
     * >>     ^
     * }
     * </pre>
     *
     * <pre>
     * {@code
     * >> [0, 1, 5, 3]
     * >>           ^
     * eliminar();
     * >> [0, 1, 5]
     * >>           ^ (final)
     * }
     * </pre>
     *
     * @return el dato eliminado.
     * @throws ExcepcionNoHayDato si no hay dato a eliminar
     *                            (si el iterador está al final).
     */
    @Override
    public T eliminar() {
        if (this.cursor == null) {
            throw new ExcepcionNoHayDato("No hay dato a eliminar.");
        }


        T datoEliminado = this.cursor.getDato();


        Nodo<T> nodoAnterior = this.cursor.getAnterior();
        Nodo<T> nodoSiguiente = this.cursor.getSiguiente();


        if (nodoAnterior != null) {
            nodoAnterior.setSiguiente(nodoSiguiente);
        } else {
            this.lista.primero = nodoSiguiente;
        }


        if (nodoSiguiente != null) {
            nodoSiguiente.setAnterior(nodoAnterior);
        } else {
            this.lista.ultimo = nodoAnterior;
        }


        this.lista.cantidadDatos--;
        this.cursor = nodoSiguiente;


        return datoEliminado;
    }
}

