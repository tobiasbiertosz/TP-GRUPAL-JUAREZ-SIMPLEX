package org.ayed.tda.lista;

public class Cola<T> {
	private Nodo<T> primero;
	private Nodo<T> ultimo;
	private int cantidadDatos;

	/**
	 * Constructor de Cola.
	 */
	public Cola() {
		this.primero = null;
		this.ultimo = null;
		this.cantidadDatos = 0;
	}

	/**
	 * Constructor de copia de Cola.
	 *
	 * @param cola Cola a copiar. No puede ser nula.
	 * @throws ExcepcionLista si la cola es nula.
	 */
	public Cola(Cola<T> cola) {
		if (cola == null) {
			throw new ExcepcionLista("La cola a copiar no puede ser nula.");
		}
		this.primero = null;
		this.ultimo = null;
		this.cantidadDatos = 0;

		Nodo<T> nodoActual = cola.primero;

		while (nodoActual != null) {
			this.agregar(nodoActual.getDato());
			nodoActual = nodoActual.getSiguiente();
		}

	}

	/**
	 * Agrega el dato al final de la cola.
	 *
	 * @param dato Dato a agregar.
	 */
	public void agregar(T dato) {
		Nodo<T> nodoNuevo = new Nodo<>(dato);

		// Caso base: la cola está vacía.
		if (this.primero == null) {
			this.primero = nodoNuevo;
		}

		// Caso general: la cola no está vacía.
		else {
			this.ultimo.setSiguiente(nodoNuevo);
			nodoNuevo.setAnterior(this.ultimo);
		}

		this.ultimo = nodoNuevo;
		this.cantidadDatos++;

	}

	/**
	 * Elimina el siguiente dato de la cola (FIFO).
	 *
	 * @return el siguiente dato de la cola.
	 * @throws ExcepcionLista si la cola está vacía.
	 */
	public T eliminar() {
		if (this.primero == null) {
			throw new ExcepcionLista("La cola está vacía.");
		}

		Nodo<T> nodoEliminado = this.primero;

		this.primero = this.primero.getSiguiente();

		if (this.primero == null) {
			this.ultimo = null;
		} else {
			this.primero.setAnterior(null);
		}

		this.cantidadDatos--;

		return nodoEliminado.getDato();
	}

	/**
	 * Obtiene el siguiente dato de la cola (FIFO).
	 *
	 * @return el siguiente dato de la cola.
	 * @throws ExcepcionLista si la cola está vacía.
	 */
	public T siguiente() {
		if (primero == null) {
			throw new ExcepcionLista("La cola está vacía.");
		}

		return this.primero.getDato();
	}

	/**
	 * Obtiene el tamaño de la cola.
	 *
	 * @return el tamaño de la cola.
	 */
	public int tamanio() {
		return cantidadDatos;
	}

	/**
	 * Evalúa si la cola está vacía.
	 *
	 * @return true si la cola está vacía.
	 */
	public boolean vacio() {
		return this.primero == null;
	}
}