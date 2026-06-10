package org.ayed.tda.lista;

/**
 * Nodo para el uso exclusivo del paquete lista (Package-private).
 */
class Nodo<T> {
    T dato;
    Nodo<T> anterior;
    Nodo<T> siguiente;

    /**
     * Constructor de Nodo.
     *
     * @param dato      Dato a guardar.
     * @param anterior  Nodo anterior.
     * @param siguiente Nodo siguiente.
     */
    public Nodo(T dato, Nodo<T> anterior, Nodo<T> siguiente) {
        this.dato = dato;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }

    /**
     * Constructor de Nodo.
     *
     * @param dato Dato a guardar.
     */
    public Nodo(T dato) {
        this(dato, null, null);
    }

	public T getDato() {
		return dato;
	}

	public void setDato(T dato) {
		this.dato = dato;
	}

	public Nodo<T> getAnterior() {
		return anterior;
	}

	public void setAnterior(Nodo<T> anterior) {
		this.anterior = anterior;
	}

	public Nodo<T> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}
    
    
}
