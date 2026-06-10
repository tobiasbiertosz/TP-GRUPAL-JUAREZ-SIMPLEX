package org.ayed.tda.lista;


public class Pila<T> {
    private Nodo<T> ultimo;
    private int cantidadDatos;


    /**
     * Constructor de Pila.
     *
     * PRE: Inicializa una pila vacía. Elemento último es null y cantidad de datos
     * es 0.
     * POS: -
     */
    public Pila() {
        this.ultimo = null;
        this.cantidadDatos = 0;
    }


    /**
     * Constructor de copia de Pila.
     *
     * @param pila Pila a copiar.
     *             No puede ser nula.
     * @throws ExcepcionLista si la pila es nula.
     *
     *                        PRE: Recibe una pila no nula.
     *                        Copia todos los elementos de la pila recibida. Los
     *                        copia del primer elemento ingresado al último.
     *                        [1, 2, 3] se copia como [1, 2, 3] y no como [3, 2, 1].
     *                        POS: -
     */
    public Pila(Pila<T> pila) {
        if (pila == null) {
            throw new ExcepcionLista("La pila a copiar no puede ser nula.");
        }


        this.ultimo = null;
        this.cantidadDatos = 0;


        Nodo<T> nodoActual = pila.ultimo;


        if (nodoActual != null) {


            while (nodoActual.getAnterior() != null) {
                nodoActual = nodoActual.getAnterior();
            }


            while (nodoActual != null) {
                this.agregar(nodoActual.getDato());
                nodoActual = nodoActual.getSiguiente();
            }
        }
    }


    /**
     * Agrega el dato al final de la pila.
     *
     * @param dato Dato a agregar.
     *
     *             PRE: Recibe un dato a agregar al final de la pila.
     *             El dato no puede ser nulo.
     *             POS: -
     */
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);


        if (this.ultimo != null) {
            this.ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(this.ultimo);
        }


        this.ultimo = nuevo;
        this.cantidadDatos++;
    }


    /**
     * Elimina el siguiente dato de la pila (LIFO).
     *
     * @return el siguiente dato de la pila.
     * @throws ExcepcionLista si la pila está vacía.
     *
     *                        PRE: La pila no puede estar vacía.
     *                        Elimina el último dato ingresado a la pila.
     *                        POS: Retorna el dato eliminado.
     */
    public T eliminar() {
        if (ultimo == null) {
            throw new ExcepcionLista("La pila está vacía.");
        }


        Nodo<T> nodoEliminado = this.ultimo;
        this.ultimo = this.ultimo.getAnterior();


        if (this.ultimo != null) {
            this.ultimo.setSiguiente(null);
        }


        this.cantidadDatos--;
        return nodoEliminado.getDato();
    }


    /**
     * Obtiene el siguiente dato de la pila (LIFO).
     *
     * @return el siguiente dato de la pila.
     * @throws ExcepcionLista si la pila está vacía.
     *
     *                        PRE: La pila no puede estar vacía.
     *                        POS: Retorna el siguiente dato de la pila.
     */
    public T siguiente() {
        if (ultimo == null) {
            throw new ExcepcionLista("La pila está vacía.");
        }


        return this.ultimo.getDato();
    }


    /**
     * Obtiene el tamaño de la pila.
     *
     * @return el tamaño de la pila.
     *
     *         PRE: -
     *         POS: Retorna el tamaño de la pila.
     */
    public int tamanio() {
        return this.cantidadDatos;
    }


    /**
     * Evalúa si la pila está vacía.
     *
     * @return true si la pila está vacía.
     *
     *         PRE: -
     *         POS: Retorna true si la pila está vacía, false en caso contrario.
     */
    public boolean vacio() {
        return this.ultimo == null;
    }
}



