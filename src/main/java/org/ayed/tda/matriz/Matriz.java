package org.ayed.tda.matriz;
import org.ayed.poe.Item;
/**
* Implementación de una matriz genérica de tamaño fijo.
*
* @param <T> Tipo de dato a almacenar en la matriz.
*/
@SuppressWarnings("unused")
public class Matriz<T> {
	private T[][] datos;
	private int filas;
	private int columnas;
	
	/**
	 * Inicializa la estructura interna de la matriz con las dimensiones dadas.
	 * <p>
	 * Reserva memoria para la matriz y establece la cantidad de filas y columnas.
	 *
	 * @param filas Cantidad de filas de la matriz.
	 * @param columnas Cantidad de columnas de la matriz.
	 *
	 * @pre: filas y columnas deben ser mayores o iguales a 1.
	 * @post: se crea una nueva matriz de dimensiones filas x columnas, y se
	 *        actualizan los atributos internos filas y columnas; todas las
	 *        posiciones quedan inicializadas en null.
	 */
	@SuppressWarnings("unchecked")
	private void inicializar(int filas, int columnas) {
		this.datos = (T[][]) new Object[filas][columnas];
		this.filas = filas;
		this.columnas = columnas;
	}
	
	/**
	 * Llena toda la matriz con un valor dado.
	 * <p>
	 * Recorre todas las posiciones de la matriz y asigna el mismo valor.
	 *
	 * @param valor Valor a asignar en todas las celdas.
	 *
	 * @pre: la matriz debe estar inicializada correctamente (datos, filas y columnas).
	 * @post: todas las posiciones de la matriz contienen el valor indicado, sin
	 *        modificar las dimensiones de la estructura.
	 */
	private void llenar(T valor) {
       for (int i = 0; i < filas; i++) {
           for (int j = 0; j < columnas; j++) {
               datos[i][j] = valor;
           }
       }
   }
	
	/**
	 * Valida que los índices de fila y columna sean válidos dentro de la matriz.
	 *
	 * @param i Índice de la fila.
	 * @param j Índice de la columna.
	 *
	 * @pre:
	 * @post: Si 0 <= i < filas y 0 <= j < columnas, no produce efectos. En caso
	 *        contrario, lanza excepcion.
	 */
	private void validarIndices(int i, int j) {
		if (i < 0 || i >= filas || j < 0 || j >= columnas) {
			throw new IndiceNoValidoException();
		}
	}
	
	
	/**
	 * Constructor para matriz cuadrada inicializada con un valor.
	 *
	 * @param tamanio Dimensiones de la matriz (tamanio * tamanio). No puede ser
	 *                menor que 1.
	 * @param valor   Valor inicial para todas las celdas.
	 * @throws IllegalArgumentException Si el tamaño es menor que 1.
	 *
	 * @pre: el valor de tamanio debe ser mayor o igual a 1.
	 * @post: crea una matriz con cantidad de filas y cantidad de columnas iguales
	 *        al valor tamanio, con todas sos posiciones incializadas con valor
	 *        valor.
	 */
	
	public Matriz(int tamanio, T valor) {
		if (tamanio < 1) {
			throw new IllegalArgumentException();
		}
		inicializar(tamanio, tamanio);
       llenar(valor);
	}
	/**
	 * Constructor para matriz cuadrada vacía (inicializada en null).
	 *
	 * @param tamanio Dimensiones de la matriz (tamanio * tamanio). No puede ser
	 *                menor que 1.
	 * @throws IllegalArgumentException Si el tamaño es menor que 1.
	 *
	 * @pre: el valor de tamanio debe ser mayor o igual a 1.
	 * @post: crea una matriz con cantidad de filas y cantidad de columnas iguales
	 *        al valor tamanio, con todas sos posiciones incializadas con valor null
	 *        (automatico en java).
	 */
	
	public Matriz(int tamanio) {
		if (tamanio < 1) {
			throw new IllegalArgumentException();
		}
		inicializar(tamanio, tamanio);
	}
	/**
	 * Constructor para matriz rectangular inicializada con un valor.
	 *
	 * @param filas    Cantidad de filas. No puede ser menor que 1.
	 * @param columnas Cantidad de columnas. No puede ser menor que 1.
	 * @param valor    Valor inicial para todas las celdas.
	 * @throws IllegalArgumentException Si filas o columnas son menores que 1.
	 *
	 * @pre: los valores de filas y columnas deben ser mayores a 1
	 * @post: crea una matriz rectangular con cantidad de filas y cantidad de
	 *        columnas segun filas y columnas, con todas sos posiciones incializadas
	 *        con valor valor.
	 */
	
	public Matriz(int filas, int columnas, T valor) {
		if (filas < 1 || columnas < 1) {
			throw new IllegalArgumentException();
		}
		inicializar(filas, columnas);
       llenar(valor);
	}
	/**
	 * Constructor para matriz rectangular vacía (inicializada en null).
	 *
	 * @param filas    Cantidad de filas. No puede ser menor que 1.
	 * @param columnas Cantidad de columnas. No puede ser menor que 1.
	 * @throws IllegalArgumentException Si filas o columnas son menores que 1.
	 *
	 * @pre: los valores de filas y columnas deben ser mayores a 1
	 * @post: crea una matriz rectangular con cantidad de filas y cantidad de
	 *        columnas segun filas y columnas, con todas sos posiciones incializadas
	 *        con valor null (automatico en java).
	 */
	public Matriz(int filas, int columnas) {
		if (filas < 1 || columnas < 1) {
			throw new IllegalArgumentException();
		}
		 inicializar(filas, columnas);
	}
	/**
	 * Constructor por copia.
	 *
	 * @param otra Matriz a copiar.
	 * @throws IllegalArgumentException Si la matriz a copiar es nula.
	 *
	 * @pre:
	 * @post:se crea una nueva matriz a partir de copiar a otro, de las mismas
	 *          dimensiones y contenido.
	 */
	public Matriz(Matriz<T> otra) {
		if (otra == null) {
			throw new IllegalArgumentException();
		}
		inicializar(otra.filas(), otra.columnas());
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				this.datos[i][j] = otra.datos[i][j];
			}
		}
	}
	/**
	 * Obtiene el elemento en la posición indicada.
	 * <p>
	 * El índice debe ser válido (0 <= i < filas, 0 <= j < columnas).
	 *
	 * @param i Índice de la fila.
	 * @param j Índice de la columna.
	 * @return El elemento en (i, j).
	 * @throws IndiceNoValidoException Si el índice está fuera de rango.
	 *
	 * @pre el indice para i y j deben ser validos (0 <= i < filas y 0 <= j <
	 *      columnas).
	 * @post: retorna el dato que se encuentra ubicado en los indices i y j
	 *        indicados.
	 */
	public T elemento(int i, int j) {
		validarIndices(i, j);
		return datos[i][j];
	}
	/**
	 * Asigna un valor en la posición indicada.
	 * <p>
	 * El índice debe ser válido (0 <= i < filas, 0 <= j < columnas).
	 *
	 * @param i     Índice de la fila.
	 * @param j     Índice de la columna.
	 * @param valor Valor a asignar.
	 * @throws IndiceNoValidoException Si el índice está fuera de rango.
	 *
	 * @pre: el indice para i y j deben ser validos (0 <= i < filas y 0 <= j <
	 *       columnas).
	 * @post: asigna un nuevo valor (valor) en la posicion indicada (indices i y j).
	 */
	public void asignar(int i, int j, T valor) {
		validarIndices(i, j);
		this.datos[i][j] = valor;
	}
	/**
	 * Devuelve la cantidad de filas de la matriz.
	 *
	 * @return Cantidad de filas.
	 *
	 * @pre:
	 * @post: retorna cantidad de filas de la matriz.
	 */
	public int filas() {
		return filas;
	}
	/**
	 * Devuelve la cantidad de columnas de la matriz.
	 *
	 * @return Cantidad de columnas.
	 *
	 * @pre:
	 * @post: retorna cantidad de columnas de la matriz.
	 */
	public int columnas() {
		return columnas;
	}
	/**
	 * Indica si la celda en la posición (i, j) está vacía.
	 *
	 * @param i Índice de la fila.
	 * @param j Índice de la columna.
	 *
	 * @pre: 0 <= i < filas y 0 <= j < columnas.
	 * @post: - Retorna true si la celda (i, j) contiene null. - Retorna false en
	 *        caso contrario. - No modifica el estado de la matriz.
	 */
	public boolean estaVacia(int i, int j) {
		validarIndices(i, j);
		return datos[i][j] == null;
	}
	/**
	 * Limpia la celda en la posición (i, j), asignándole null.
	 *
	 * @param i Índice de la fila.
	 * @param j Índice de la columna.
	 *
	 * @pre: 0 <= i < filas y 0 <= j < columnas.
	 * @post: - La celda (i, j) pasa a contener null. - El resto de la matriz
	 *        permanece sin cambios.
	 */
	public void limpiar(int i, int j) {
		validarIndices(i, j);
		datos[i][j] = null;
	}
}
