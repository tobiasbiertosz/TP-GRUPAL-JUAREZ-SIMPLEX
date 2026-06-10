package org.ayed.tda.diccionario;

import org.ayed.tda.iterador.Iterador;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.tupla.Tupla;
import org.ayed.tda.vector.VectorEstatico;

/**
 * Diccionario asociativo (clave, valor) que NO mantiene necesariamente el orden
 * de los datos. Está implementado con un hash abierto.
 *
 * @param <C> El tipo de dato de la clave. Este tipo debe implementar el método
 *            {@code equals} e, idealmente, el método {@code hashCode}.
 * @param <V> El tipo de dato del valor. Este tipo no necesita ser comparable
 *            obligatoriamente, pero puede ser útil para el usuario si se decide
 *            implementar métodos para consultar si un valor está en el
 *            diccionario.
 */
public class Diccionario<C, V> {
	private VectorEstatico<Lista<Tupla<C, V>>> datos;
	// Completar con un valor apropiado según la teoría.
	private static final double FACTOR_DEFAULT = 0.8;
	private double factorDeCarga;
	private int tamanioTabla;
	private int cantidadDatos;

	/**
	 * Función de hash. Obtiene el hash de la clave.
	 *
	 * @param clave Clave a hashear.
	 * @return el hash.
	 */
	private int hashear(C clave) {
		if (clave == null) {
			return 0;
		}
		int hash = clave.hashCode();
		return hash ^ hash >>> 16;
	}

	/**
	 * Obtiene el índice de la tabla para la clave.
	 *
	 * @param clave Clave a acceder.
	 * @return el índice de la clave.
	 */
	private int obtenerIndice(C clave) {
		return hashear(clave) % tamanioTabla;
	}

	/**
	 * Calcula el tamaño de la tabla. Debe ser el primer número primo que permita
	 * guardar la cantidad de datos deseada, según el factor de carga a utilizar.
	 *
	 * @param tamanio Cantidad de datos a guardar. Ya validado.
	 * @return el tamaño de la tabla.
	 */
	private int calcularTamanioTabla(int tamanio) {
		int minimo = (int) Math.ceil((double) tamanio / factorDeCarga);

		while (!esPrimo(minimo)) {
			minimo++;
		}

		return minimo;

	}

	private boolean esPrimo(int numero) {
		if (numero < 2) {
			return false;
		}

		for (int i = 2; i * i <= numero; i++) {
			if (numero % i == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Constructor.
	 * <p>
	 * NOTA: El tamaño de la tabla debe ser el primer número primo que permita
	 * guardar la cantidad de datos deseada, según el factor de carga a utilizar.
	 *
	 * @param tamanio       Cantidad de datos a guardar. Debe ser positivo.
	 * @param factorDeCarga Factor de carga a utilizar. Debe estar entre 0 y 1.
	 * @throws ExcepcionDiccionario si el tamaño o el factor de carga no es válido.
	 */
	public Diccionario(int tamanio, double factorDeCarga) {
		if (tamanio <= 0) {
			throw new ExcepcionDiccionario("Tamaño inválido");
		}

		if (factorDeCarga <= 0 || factorDeCarga > 1) {
			throw new ExcepcionDiccionario("Factor de carga inválido");
		}

		this.factorDeCarga = factorDeCarga;
		this.tamanioTabla = calcularTamanioTabla(tamanio);

		this.datos = new VectorEstatico<>(tamanioTabla);

		for (int i = 0; i < tamanioTabla; i++) {
			datos.asignar(i, new Lista<>());
		}

		this.cantidadDatos = 0;
	}

	/**
	 * Constructor. Usa el factor de carga por defecto.
	 *
	 * @param tamanio Cantidad de datos a guardar. Debe ser positivo.
	 * @throws ExcepcionDiccionario si el tamaño no es válido.
	 */
	public Diccionario(int tamanio) {
		this(tamanio, FACTOR_DEFAULT);
	}

	/**
	 * Constructor de copia de Diccionario.
	 *
	 * @param diccionario Diccionario a copiar. No puede ser nulo.
	 * @throws ExcepcionDiccionario si el diccionario es nulo.
	 */
	public Diccionario(Diccionario<C, V> diccionario) {
		if (diccionario == null) {
			throw new ExcepcionDiccionario("El diccionario no puede ser nulo");
		}

		this.factorDeCarga = diccionario.factorDeCarga;
		this.tamanioTabla = diccionario.tamanioTabla;

		datos = new VectorEstatico<>(tamanioTabla);

		for (int i = 0; i < tamanioTabla; i++) {
			datos.asignar(i, new Lista<>(diccionario.datos.obtener(i)));
		}

		cantidadDatos = diccionario.cantidadDatos;
	}

	/**
	 * Agrega un mapeo {clave, valor} al diccionario. Si ya existía la clave en el
	 * diccionario, reemplaza el valor anterior y lo devuelve.
	 *
	 * @param clave Clave a agregar.
	 * @param valor Valor a agregar.
	 * @return el valor anterior. Si no había un valor anterior, devuelve null.
	 */
	public V agregar(C clave, V valor) {
		int indice = obtenerIndice(clave);

		Lista<Tupla<C, V>> lista = datos.obtener(indice);

		Iterador<Tupla<C, V>> iterador = lista.iterador();

		while (iterador.haySiguiente()) {

			Tupla<C, V> actual = iterador.dato();

			if (actual.clave().equals(clave)) {

				V valorAnterior = actual.valor();

				iterador.modificarDato(new Tupla<>(clave, valor));

				return valorAnterior;
			}

			iterador.siguiente();
		}

		lista.agregar(new Tupla<>(clave, valor));
		cantidadDatos++;

		return null;
	}

	/**
	 * Elimina un mapeo {clave, valor} del diccionario, si existe. Si no existe, el
	 * diccionario queda en el mismo estado.
	 * <p>
	 * NOTA: Para eliminar nodos interiores, se utiliza el sucesor inmediato.
	 *
	 * @param clave Clave a eliminar.
	 * @return el valor eliminado. Si no se eliminó un valor, devuelve null.
	 */
	public V eliminar(C clave) {
		int indice = obtenerIndice(clave);

		Lista<Tupla<C, V>> lista = datos.obtener(indice);

		Iterador<Tupla<C, V>> iterador = lista.iterador();

		while (iterador.haySiguiente()) {

			Tupla<C, V> actual = iterador.dato();

			if (actual.clave().equals(clave)) {

				V valorEliminado = actual.valor();

				iterador.eliminar();

				cantidadDatos--;

				return valorEliminado;
			}

			iterador.siguiente();
		}

		return null;
	}

	/**
	 * Obtiene un mapeo {clave, valor} del diccionario, si existe.
	 *
	 * @param clave Clave a buscar.
	 * @return el valor buscado. Si no existe, devuelve null.
	 */
	public V obtenerValor(C clave) {
		int indice = obtenerIndice(clave);

		Lista<Tupla<C, V>> lista = datos.obtener(indice);

		Iterador<Tupla<C, V>> iterador = lista.iterador();

		while (iterador.haySiguiente()) {

			Tupla<C, V> actual = iterador.dato();

			if (actual.clave().equals(clave)) {
				return actual.valor();
			}

			iterador.siguiente();
		}

		return null;
	}

	/**
	 * Obtiene el tamaño del diccionario.
	 *
	 * @return el tamaño del diccionario.
	 */
	public int tamanio() {
		return cantidadDatos;
	}

	/**
	 * Evalúa si el diccionario está vacío.
	 *
	 * @return true si el diccionario está vacío.
	 */
	public boolean vacio() {
		return cantidadDatos == 0;
	}

	/**
	 * Obtiene una lista de todos los valores. Este método es para testear el TDA,
	 * no utilizar en el proyecto.
	 *
	 * @return los valores.
	 */
	public Lista<V> valores() {
		Lista<V> valores = new Lista<>();
		Iterador<Tupla<C, V>> iterador;
		for (int i = 0; i < tamanioTabla; i++) {
			iterador = this.datos.obtener(i).iterador();
			while (iterador.haySiguiente()) {
				valores.agregar(iterador.dato().valor());
				iterador.siguiente();
			}
		}
		return valores;
	}
}


