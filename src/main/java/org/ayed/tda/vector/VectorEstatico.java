package org.ayed.tda.vector;
/**
* Implementación de un Vector Estático.
* <p>
* Esta clase representa un arreglo de tamaño fijo. No permite agregar ni
* eliminar elementos que modifiquen el tamaño del contenedor, aunque sí permite
* limpiar posiciones (asignar null). Los elementos se acceden y modifican
* directamente por índice.
*
* @param <T> Tipo de dato a almacenar en el vector.
*/
public class VectorEstatico<T> {
	private T[] datos;
	/**
	 * Constructor de Vector Estático.
	 * <p>
	 * Crea un vector con una capacidad fija.
	 *
	 * @param tamanio Tamaño fijo del vector.
	 * @throws TamanioInvalidoException si el tamaño es negativo.
	 *
	 * @pre: tamanio debe ser >= 0 para poder crear el vector.
	 * @post: crea el vector esatitico de tamanio fijo ("tamanio") inicializado en
	 *        null.
	 */
	@SuppressWarnings("unchecked")
	public VectorEstatico(int tamanio) {
		if (tamanio < 0) {
			throw new TamanioInvalidoException();
		}
		this.datos = (T[]) new Object[tamanio];
	}
	/**
	 * Constructor de copia de Vector Estático.
	 * <p>
	 * Crea un nuevo vector con el mismo tamaño y contenido que el original.
	 *
	 * @param otro Vector a copiar.
	 * @throws IllegalArgumentException si el vector a copiar es nulo.
	 *
	 * @pre: otro debe ser distinto de null.
	 * @post: se crea un nuevo vector a partir de copiar a otro, del mismo tamaño y
	 *        contenido.
	 */
	@SuppressWarnings("unchecked")
	public VectorEstatico(VectorEstatico<T> otro) {
		if (otro == null) {
			throw new IllegalArgumentException();
		}
		int tamanio = otro.tamanio();
		this.datos = (T[]) new Object[tamanio];
		for (int i = 0; i < tamanio; i++) {
			this.datos[i] = otro.datos[i];
		}
	}
	/**
	 * Obtiene el dato en la posición indicada.
	 *
	 * @param indice Índice del dato a obtener. Debe estar entre 0 y tamanio() - 1.
	 * @return El dato en la posición indicada (puede ser null).
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual
	 *                                     al tamaño.
	 *
	 * @pre: el indice debe ser valido (estar entre 0 y tamanio() -1).
	 * @post: retorna el dato que se encuentra ubicado en el indice indicado.
	 */
	public T obtener(int indice) {
		validarIndice(indice);
		return datos[indice];
	}
	/**
	 * Asigna un dato en la posición indicada, reemplazando el valor anterior.
	 *
	 * @param indice Índice donde asignar el dato. Debe estar entre 0 y tamanio() -
	 *               1.
	 * @param dato   Dato a asignar (puede ser null).
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual
	 *                                     al tamaño.
	 *
	 * @pre: el indice debe ser valido(estar entre 0 y tamanio() -1).
	 * @post: asigna un nuevo valor (dato) en la posicion indicada (indice).
	 */
	public void asignar(int indice, T dato) {
		validarIndice(indice);
		this.datos[indice] = dato;
	}
	/**
	 * Limpia la posición indicada (asigna null).
	 * <p>
	 * Esta operación no reduce el tamaño del vector, simplemente deja la posición
	 * vacía.
	 *
	 * @param indice Índice a limpiar. Debe estar entre 0 y tamanio() - 1.
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual
	 *                                     al tamaño.
	 *
	 * @pre: el indice debe ser valido(estar entre 0 y tamanio() -1).
	 * @post: el valor almacenado en la posicion indice pasa a ser null.
	 */
	public void limpiar(int indice) {
		validarIndice(indice);
		this.datos[indice] = null;
	}
	/**
	 * Obtiene el tamaño fijo del vector.
	 *
	 * @return La capacidad total del vector.
	 *
	 * @pre:
	 * @post: retorna cantidad de posiciones del vector.
	 */
	public int tamanio() {
		return datos.length;
	}
	
	/**
	 * Valida que el índice esté dentro del rango permitido.
	 *
	 * @param indice Indice a validar.
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual al tamaño.
	 *
	 * @pre:
	 * @post: si el indice es valido no ocurre ningun cambio; si no es valido se lanza una excepcion.
	 */
	private void validarIndice(int indice) {
		if (indice < 0 || indice >= tamanio()) {
			throw new IndiceFueraDeRangoException();
		}
	}
}
