package org.ayed.tda.lista;

import org.ayed.tda.iterador.Iterador;

public class Lista<T> {
	Nodo<T> primero;
	Nodo<T> ultimo;
	int cantidadDatos;

	/**
	 * Constructor de Lista.
	 */
	public Lista() {
		primero = null;
		ultimo = null;
		cantidadDatos = 0;
	}

	/**
	 * Constructor de copia de Lista.
	 *
	 * @param lista Lista a copiar. No puede ser nula.
	 * @throws ExcepcionLista si la lista es nula.
	 */
	public Lista(Lista<T> lista) {
		if (lista == null) {
			throw new ExcepcionLista("Lista a copiar no puede ser nula");
		}

		this.cantidadDatos = 0;
		this.primero = null;
		this.ultimo = null;

		Nodo<T> actual = lista.primero;

		while (actual != null) {

			Nodo<T> nuevo = new Nodo(actual.getDato());

			if (this.primero == null) {
				this.primero = nuevo;
				this.ultimo = nuevo;
			} else {
				nuevo.setAnterior(this.ultimo);
				this.ultimo.setSiguiente(nuevo);
				this.ultimo = nuevo;
			}

			this.cantidadDatos++;

			actual = actual.getSiguiente();
		}
	}

	/**
	 * Agrega un dato al final de la lista.
	 *
	 * @param dato Dato a agregar.
	 */
	public void agregar(T dato) {

		Nodo<T> nuevo = new Nodo<>(dato);

		if (primero == null) {

			primero = nuevo;
			ultimo = nuevo;

		} else {

			nuevo.setAnterior(ultimo);
			ultimo.setSiguiente(nuevo);
			ultimo = nuevo;
		}

		cantidadDatos++;
	}

	/**
     * Agrega un dato a la lista en el índice indicado.
     * <p>
     * Ejemplo:
     * <pre>
     * {@code
     * >> 0 -> 1 -> 5 -> 3
     * agregar(4, 1);
     * >> 0 -> 4 -> 1 -> 5 -> 3
     * }
     * </pre>
     *
     * @param dato   Dato a agregar.
     * @param indice Índice en el que se inserta el dato.
     *               No puede ser negativo.
     *               No puede ser mayor que el tamaño de la lista.
     * @throws ExcepcionLista si el índice no es válido.
     */
	public void agregar(T dato, int indice) {

	    if (indice < 0 || indice > this.cantidadDatos) {
	        throw new ExcepcionLista("índice no es válido");
	    }

	    Nodo<T> nodoNuevo = new Nodo<T>(dato, null, null);

	    // Lista vacía
	    if (vacio()) {
	        this.primero = nodoNuevo;
	        this.ultimo = nodoNuevo;

	    // Insertar al principio
	    } else if (indice == 0) {

	        nodoNuevo.setSiguiente(this.primero);
	        this.primero.setAnterior(nodoNuevo);
	        this.primero = nodoNuevo;

	    // Insertar al final
	    } else if (indice == this.cantidadDatos) {

	        nodoNuevo.setAnterior(this.ultimo);
	        this.ultimo.setSiguiente(nodoNuevo);
	        this.ultimo = nodoNuevo;

	    // Insertar en el medio
	    } else {

	        Nodo<T> anterior = obtenerNodo(indice - 1);
	        Nodo<T> siguiente = anterior.getSiguiente();

	        nodoNuevo.setAnterior(anterior);
	        nodoNuevo.setSiguiente(siguiente);

	        anterior.setSiguiente(nodoNuevo);
	        siguiente.setAnterior(nodoNuevo);
	    }

	    cantidadDatos++;
	}
    
 // Devuelve una referencia al Nodo que esta en la posicion pos
 	// (la primera posicion es la 0)
	// PRE: 0 <= pos < cantidadDatos
 	private Nodo<T> obtenerNodo(int pos) {
 		Nodo<T> aux = primero;
 		for (int i = 0; i < pos; i++)
 			aux = aux.getSiguiente();
 		return aux;
 	}

	/**
	 * Elimina el último dato de la lista
	 *
	 * @return el dato eliminado.
	 * @throws ExcepcionLista si la lista está vacía.
	 */
 	public T eliminar() {

 	    if (vacio()) {
 	        throw new ExcepcionLista("La lista esta vacia");
 	    }

 	    T datoEliminado = this.ultimo.getDato();

 	    // Caso: un solo elemento
 	    if (cantidadDatos == 1) {

 	        this.primero = null;
 	        this.ultimo = null;

 	    } else {

 	        Nodo<T> anterior = obtenerNodo(cantidadDatos - 2);

 	        anterior.setSiguiente(null);
 	        this.ultimo = anterior;
 	    }

 	    this.cantidadDatos--;

 	    return datoEliminado;
 	}

	/**
	 * Elimina el dato de la lista en el índice indicado por parámetro.
	 * <p>
	 * Ejemplo:
	 * 
	 * <pre>
	 * {@code
	 * >> 0 -> 1 -> 5 -> 3
	 * eliminar(1);
	 * >> 0 -> 5 -> 3
	 * }
	 * </pre>
	 *
	 * @param indice Índice del dato a eliminar. No puede ser negativo. No puede ser
	 *               mayor o igual que el tamaño de la lista.
	 * @return el dato eliminado.
	 */
 	public T eliminar(int indice) {

 	    if (indice < 0 || indice >= this.cantidadDatos) {
 	        throw new ExcepcionLista("índice no es válido");
 	    }

 	    if (vacio()) {
 	        throw new ExcepcionLista("La lista esta vacia");
 	    }

 	    T eliminado;

 	    // Caso: un único elemento
 	    if (cantidadDatos == 1) {

 	        eliminado = primero.getDato();

 	        primero = null;
 	        ultimo = null;

 	    // Caso: eliminar primero
 	    } else if (indice == 0) {

 	        eliminado = primero.getDato();

 	        primero = primero.getSiguiente();
 	        primero.setAnterior(null);

 	    // Caso: eliminar último
 	    } else if (indice == cantidadDatos - 1) {

 	        Nodo<T> anterior = obtenerNodo(indice - 1);

 	        eliminado = ultimo.getDato();

 	        anterior.setSiguiente(null);
 	        ultimo = anterior;

 	    // Caso: eliminar del medio
 	    } else {

 	        Nodo<T> anterior = obtenerNodo(indice - 1);
 	        Nodo<T> actual = anterior.getSiguiente();
 	        Nodo<T> siguiente = actual.getSiguiente();

 	        eliminado = actual.getDato();

 	        anterior.setSiguiente(siguiente);
 	        siguiente.setAnterior(anterior);
 	    }

 	    cantidadDatos--;

 	    return eliminado;
 	}

	/**
	 * Obtiene el dato de la lista en el índice indicado.
	 *
	 * @param indice Índice del dato a obtener. No puede ser negativo. No puede ser
	 *               mayor o igual que el tamaño de la lista.
	 * @return el dato en el índice indicado.
	 * @throws ExcepcionLista si el índice no es válido.
	 */
	public T dato(int indice) {
		
		if (indice < 0 || indice >= this.cantidadDatos) {
    		throw new ExcepcionLista("índice no es válido"); 
    	}

		return obtenerNodo(indice).getDato();
	}

	/**
	 * Modifica el dato de la lista en el índice indicado por el dato indicado por
	 * parámetro.
	 *
	 * @param indice Índice del dato a modificar. No puede ser negativo. No puede
	 *               ser mayor o igual que el tamaño de la lista.
	 * @throws ExcepcionLista si el índice no es válido.
	 */
	public void modificarDato(T dato, int indice) {
		if (indice < 0 || indice >= this.cantidadDatos) {
    		throw new ExcepcionLista("índice no es válido"); 
    	}
		obtenerNodo(indice).setDato(dato);
	}

	/**
	 * Obtiene el tamaño de la lista.
	 *
	 * @return el tamaño de la lista.
	 */
	public int tamanio() {
		return this.cantidadDatos;
	}

	/**
	 * Evalúa si la lista está vacía.
	 *
	 * @return true si la lista está vacía.
	 */
	public boolean vacio() {
		
		return (cantidadDatos == 0);
	}

	/**
	 * Obtiene un iterador bidireccional posicionado en el primer dato de la lista.
	 *
	 * @return el iterador.
	 * @see Iterador
	 */
	public Iterador<T> iterador() {
		return new IteradorLista<T>(this);
	}

	/**
	 * Obtiene un iterador bidireccional posicionado en el índice indicado por
	 * parámetro.
	 *
	 * @param indice Índice del nodo inicial del iterador. No puede ser negativo. No
	 *               puede ser mayor que el tamaño de la lista.
	 * @return el iterador.
	 * @throws ExcepcionLista si el índice no es válido.
	 * @see Iterador
	 */
	public Iterador<T> iterador(int indice) {
		if (indice < 0 || indice > this.cantidadDatos) {
    		throw new ExcepcionLista("índice no es válido"); 
    	}
		return new IteradorLista<T>(this, indice);
	}
}
