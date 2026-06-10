package org.ayed.tda.vector;
/**
* Implementación de un Vector Dinámico.
* <p>
* Esta estructura de datos crece automáticamente cuando se llena y libera
* memoria cuando se vacía significativamente. Permite acceso aleatorio,
* inserción y eliminación de elementos.
* <p>
* Los métodos que modifican la estructura del vector (como
* {@link #agregar(Object, int)} y {@link #eliminar(int)}) desplazan los
* elementos según sea necesario.
*
* @param <T> Tipo de dato a almacenar en el vector.
*/
public class VectorDinamico<T> {
	// La capacidad inicial NO se puede cambiar.
	private static final int CAPACIDAD_INICIAL = 0;
	private T[] datos;
	private int cantidadElementos;
	/**
	 * Constructor de Vector. Inicializa un vector con capacidad inicial 0.
	 *
	 * @pre:
	 * @post: se crea un vector dinamico con capacidad inicial 0, ademas de cantidad
	 *        de elementos = 0 (vacio).
	 */
	@SuppressWarnings("unchecked")
	public VectorDinamico() {
		this.datos = (T[]) new Object[CAPACIDAD_INICIAL];
		this.cantidadElementos = 0;
	}
	/**
	 * Constructor de copia de Vector.
	 *
	 * @param otro Vector a copiar.
	 * @throws IllegalArgumentException si el vector a copiar es nulo.
	 *
	 * @pre: otro != null.
	 * @post:se crea un nuevo vector a partir de copiar a otro, de la misma
	 *          capacidad y contenido.
	 */
	@SuppressWarnings("unchecked")
	public VectorDinamico(VectorDinamico<T> otro) {
		if (otro == null) {
			throw new IllegalArgumentException();
		}
		this.datos = (T[]) new Object[otro.datos.length];
		this.cantidadElementos = otro.cantidadElementos;
		for (int i = 0; i < cantidadElementos; i++) {
			this.datos[i] = otro.datos[i];
		}
	}
	/**
	 * Agrega un dato al final del vector.
	 * <p>
	 * Si el vector está lleno, aumenta su capacidad.
	 *
	 * @param dato Dato a agregar.
	 *
	 * @pre:
	 * @post: agrega un dato al final del vector si este tiene espacio, de lo
	 *        contrario, primero aumenta capacidad y luego agrega el dato.
	 */
	public void agregar(T dato) {
		asegurarCapacidad();
		datos[cantidadElementos] = dato;
		cantidadElementos++;
	}
	/**
	 * Agrega un dato al vector en el índice indicado, desplazando los elementos
	 * posteriores.
	 * <p>
	 * Ejemplo:
	 *
	 * <pre>
	 * {@code
	 * Vector<Integer> v = new Vector<>();
	 * v.agregar(1);
	 * v.agregar(3); // [1, 3]
	 * v.agregar(2, 1); // [1, 2, 3]
	 * }
	 * </pre>
	 *
	 * @param dato   Dato a agregar.
	 * @param indice Índice en el que se inserta el dato. Debe estar entre 0 y el
	 *               tamaño lógico del vector (inclusive).
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor que el
	 *                                     tamaño lógico.
	 *
	 * @pre: el indice debe ser valido (estar entre 0 y la cantidad de elementos
	 *       reales en el vector).
	 * @post: se agrega dato en la posicion indice, moviendo a derecha los elementos
	 *        en las posiciones posteriores a indice.
	 */
	public void agregar(T dato, int indice) {
		validarIndiceInsercion(indice);
		asegurarCapacidad();
		for (int i = cantidadElementos; i > indice; i--) {
			datos[i] = datos[i - 1];
		}
		datos[indice] = dato;
		cantidadElementos++;
	}
	/**
	 * Elimina el último dato del vector.
	 * <p>
	 * Reduce la capacidad del vector si es necesario, dependiendo de la estrategia.
	 *
	 * @return el dato eliminado.
	 * @throws VectorVacioException si el vector está vacío.
	 *
	 * @pre:
	 * @post: retorna el ultimo dato del vector el cual fue eliminado.
	 */
	public T eliminar() {
		if (cantidadElementos == 0) {
			throw new VectorVacioException();
		}
		T eliminado = datos[cantidadElementos - 1];
		datos[cantidadElementos - 1] = null;
		cantidadElementos--;
		reducirCapacidadSiEsNecesario();
		return eliminado;
	}
	/**
	 * Elimina el dato del vector en el índice indicado, desplazando los elementos
	 * posteriores.
	 * <p>
	 * Ejemplo:
	 *
	 * <pre>
	 * {@code
	 * Vector<Integer> v = ...; // [1, 2, 3]
	 * v.eliminar(1);           // [1, 3]
	 * }
	 * </pre>
	 *
	 * @param indice Índice del dato a eliminar. Debe estar entre 0 y tamaño lógico
	 *               - 1.
	 * @return el dato eliminado.
	 * @throws VectorVacioException        si el vector está vacío.
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual
	 *                                     al tamaño lógico.
	 *
	 * @pre el indice debe ser valido (estar entre 0 y la cantidad de elementos
	 *      reales en el vector).
	 * @post: se elimina el dato en la posicion indice, moviendo a izquierda los
	 *        elementos en las posiciones posteriores a indice.
	 */
	public T eliminar(int indice) {
		if (cantidadElementos == 0) {
			throw new VectorVacioException();
		}
		validarIndice(indice);
		T eliminado = datos[indice];
		for (int i = indice; i < cantidadElementos - 1; i++) {
			datos[i] = datos[i + 1];
		}
		datos[cantidadElementos - 1] = null;
		cantidadElementos--;
		reducirCapacidadSiEsNecesario();
		return eliminado;
	}
	/**
	 * Obtiene el dato del vector en el índice indicado.
	 *
	 * @param indice Índice del dato a obtener. Debe estar entre 0 y tamaño lógico -
	 *               1.
	 * @return el dato en el índice indicado.
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual
	 *                                     al tamaño lógico.
	 *
	 * @pre el indice debe ser valido (estar entre 0 y la cantidad de elementos
	 *      reales en el vector).
	 * @post: retorna el dato almacenado en la posicion indice.
	 */
	public T obtener(int indice) {
		validarIndice(indice);
		return datos[indice];
	}
	/**
	 * Modifica el dato del vector en el índice indicado.
	 *
	 * @param indice Índice del dato a modificar. Debe estar entre 0 y tamaño lógico - 1.
	 * @param dato   Nuevo dato.
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual
	 *                                     al tamaño lógico.
	 *
	 * @pre el indice debe ser valido (estar entre 0 y la cantidad de elementos
	 *      reales en el vector).
	 * @post: se actualiza el valor almacenado en la posicion indice del vector por
	 *        el nuevo dato.
	 */
	public void cambiar(int indice, T dato) {
		validarIndice(indice);
		datos[indice] = dato;
	}
	/**
	 * Obtiene el tamaño lógico del vector (cantidad de elementos almacenados).
	 *
	 * @return el tamaño del vector.
	 *
	 * @pre:
	 * @post: retorna la cantidad de elementos que hay en el vector.
	 */
	public int tamanio() {
		return cantidadElementos;
	}
	/**
	 * Obtiene el tamaño físico actual del vector (capacidad).
	 * <p>
	 * NOTA: Este método es únicamente para probar el comportamiento dinámico.
	 *
	 * @return la capacidad actual del vector.
	 *
	 * @pre:
	 * @post: retorna el tamano real que tiene el vector.
	 */
	public int capacidad() {
		return datos.length;
	}
	/**
	 * Evalúa si el vector está vacío.
	 *
	 * @return true si el vector está vacío.
	 *
	 * @pre:
	 * @post: retorna true en el caso de que la cantidad de elementos sea igual a
	 *        cero.
	 */
	public boolean vacio() {
		return cantidadElementos == 0;
	}
	
	/**
	 * Valida que el índice esté dentro del rango permitido.
	 *
	 * @param indice Índice a validar.
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor o igual al tamaño.
	 *
	 * @pre:
	 * @post: si el indice es valido no ocurre ningun cambio; si no es valido se lanza una excepcion.
	 */
	private void validarIndice(int indice) {
		if (indice < 0 || indice >= cantidadElementos) {
			throw new IndiceFueraDeRangoException();
		}
	}
	/**
	 * Valida que el indice sea valido para una operacion de insercion.
	 *
	 * @param indice Indice donde se desea insertar.
	 * @throws IndiceFueraDeRangoException si el índice es menor a 0 o mayor a la cantidad de elementos.
	 *
	 * @pre:
	 * @post: si el indice es valido (entre 0 y cantidadElementos inclusive) no ocurre ningun cambio;
	 *        en caso contrario se lanza una excepcion.
	 */
	private void validarIndiceInsercion(int indice) {
		if (indice < 0 || indice > cantidadElementos) {
			throw new IndiceFueraDeRangoException();
		}
	}
	
	/**
	 * Redimensiona el arreglo interno del vector a una nueva capacidad.
	 *
	 * @param nuevaCapacidad Nueva capacidad del arreglo interno.
	 *
	 * @pre: nuevaCapacidad debe ser mayor o igual a cantidadElementos.
	 * @post: el arreglo interno datos pasa a tener capacidad nuevaCapacidad;
	 *        se conservan los elementos en las posiciones 0 hasta cantidadElementos - 1;
	 *        no se modifica la cantidad de elementos almacenados.
	 */
	private void redimensionar(int nuevaCapacidad) {
		T[] nuevo = (T[]) new Object[nuevaCapacidad];
		for (int i = 0; i < cantidadElementos; i++) {
			nuevo[i] = datos[i];
		}
		datos = nuevo;
	}
	
	/**
	 * Asegura que exista espacio disponible para agregar un nuevo elemento.
	 * En caso de que el arreglo esté lleno, aumenta su capacidad.
	 *
	 * @pre:
	 * @post: si cantidadElementos < datos.length, no se producen cambios;
	 *        si cantidadElementos == datos.length, el arreglo interno se redimensiona
	 *        a una capacidad mayor (al menos el doble o 1 si era 0);
	 *        en todos los casos, se conservan los elementos existentes y no se modifica
	 *        la cantidad de elementos.
	 */
	private void asegurarCapacidad() {
		if (cantidadElementos == datos.length) {
			int nuevaCapacidad = (datos.length == 0) ? 1 : datos.length * 2;
			redimensionar(nuevaCapacidad);
		}
	}
	/**
	 * Reduce la capacidad del arreglo interno si hay espacio ocioso significativo.
	 *
	 * @pre:
	 * @post: si datos.length > 1 y cantidadElementos <= datos.length / 2,
	 *        el arreglo interno se redimensiona a una capacidad menor (aproximadamente la mitad,
	 *        con un minimo de 1);
	 *        en caso contrario no se producen cambios;
	 *        en todos los casos, se conservan los elementos en las posiciones
	 *        0 hasta cantidadElementos - 1 y no se modifica la cantidad de elementos.
	 */
	private void reducirCapacidadSiEsNecesario() {
		if (datos.length > 1 && cantidadElementos <= datos.length / 2) {
			int nuevaCapacidad = Math.max(1, datos.length / 2);
			redimensionar(nuevaCapacidad);
		}
	}
}

