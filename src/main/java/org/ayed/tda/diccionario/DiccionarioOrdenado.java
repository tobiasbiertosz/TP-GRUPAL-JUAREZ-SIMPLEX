package org.ayed.tda.diccionario;
import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.lista.Cola;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.tupla.Tupla;
/**
* Diccionario asociativo (clave, valor) que mantiene el orden de los datos en
* base a su clave y al comparador utilizado. Está implementado con un árbol
* binario de búsqueda no balanceado.
*
* @param <C> El tipo de dato de la clave. Este tipo debe ser comparable.
* @param <V> El tipo de dato del valor. Este tipo no necesita ser comparable
*            obligatoriamente, pero puede ser útil para el usuario si se decide
*            implementar métodos para consultar si un valor está en el
*            diccionario.
*/
public class DiccionarioOrdenado<C, V> {
	private Nodo<C, V> raiz;
	private Comparador<C> comparador;
	private int cantidadDatos;
	/**
	 * Constructor.
	 *
	 * @param comparador Comparador a utilizar. No puede ser nulo.
	 * @throws ExcepcionDiccionario si el comparador es nulo.
	 */
	public DiccionarioOrdenado(Comparador<C> comparador) {
		if (comparador == null) {
			throw new ExcepcionDiccionario("El comparador no puede ser nulo.");
		}
		this.comparador = comparador;
		this.raiz = null;
		this.cantidadDatos = 0;
	}
	/**
	 * Constructor de copia de DiccionarioOrdenado.
	 * <p>
	 * TIP: Implementar un método que clone un subárbol.
	 *
	 * @param diccionarioOrdenado Diccionario a copiar. No puede ser nulo.
	 * @throws ExcepcionDiccionario si el diccionario es nulo.
	 */
	public DiccionarioOrdenado(DiccionarioOrdenado<C, V> diccionarioOrdenado) {
		if (diccionarioOrdenado == null) {
			throw new ExcepcionDiccionario("Diccionario nulo.");
		}
		this.comparador = diccionarioOrdenado.comparador;
		this.cantidadDatos = diccionarioOrdenado.cantidadDatos;
		this.raiz = clonarSubarbol(diccionarioOrdenado.raiz, null);
	}
	/**
	 * Clona recursivamente un subárbol.
	 * <p>
	 * Crea una copia profunda de todos los nodos pertenecientes al subárbol cuya
	 * raíz es el nodo indicado por parámetro, preservando la estructura y las
	 * referencias a los padres.
	 *
	 * @param nodo  Raíz del subárbol a clonar.
	 * @param padre Nodo padre del nuevo subárbol.
	 * @return la raíz del subárbol clonado.
	 */
	private Nodo<C, V> clonarSubarbol(Nodo<C, V> nodo, Nodo<C, V> padre) {
		if (nodo == null) {
			return null;
		}
		Nodo<C, V> copia = new Nodo<>(nodo.clave, nodo.valor, padre);
		copia.hijoIzquierdo = clonarSubarbol(nodo.hijoIzquierdo, copia);
		copia.hijoDerecho = clonarSubarbol(nodo.hijoDerecho, copia);
		return copia;
	}
	/**
	 * Obtiene el sucesor inmediato del nodo.
	 *
	 * @param nodo Nodo inicial.
	 * @return el sucesor inmediato.
	 */
	private Nodo<C, V> obtenerSucesorInmediato(Nodo<C, V> nodo) {
		Nodo<C, V> actual = nodo.hijoDerecho;
		while (actual.hijoIzquierdo != null) {
			actual = actual.hijoIzquierdo;
		}
		return actual;
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
		if (raiz == null) {
			raiz = new Nodo<>(clave, valor);
			cantidadDatos++;
			return null;
		}
		Nodo<C, V> actual = raiz;
		Nodo<C, V> padre = null;
		while (actual != null) {
			padre = actual;
			int comparacion = comparador.comparar(clave, actual.clave);
			if (comparacion == 0) {
				V valorAnterior = actual.valor;
				actual.valor = valor;
				return valorAnterior;
			}
			if (comparacion < 0) {
				actual = actual.hijoIzquierdo;
			} else {
				actual = actual.hijoDerecho;
			}
		}
		Nodo<C, V> nuevo = new Nodo<>(clave, valor, padre);
		if (comparador.comparar(clave, padre.clave) < 0) {
			padre.hijoIzquierdo = nuevo;
		} else {
			padre.hijoDerecho = nuevo;
		}
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
		Nodo<C, V> nodo = raiz;
		while (nodo != null) {
			int cmp = comparador.comparar(clave, nodo.clave);
			if (cmp == 0) {
				break;
			}
			if (cmp < 0) {
				nodo = nodo.hijoIzquierdo;
			} else {
				nodo = nodo.hijoDerecho;
			}
		}
		if (nodo == null) {
			return null;
		}
		V valorEliminado = nodo.valor;
		/*
		 * Caso 4: tiene ambos hijos
		 */
		if (nodo.hijoIzquierdo != null && nodo.hijoDerecho != null) {
			Nodo<C, V> sucesor = obtenerSucesorInmediato(nodo);
			nodo.clave = sucesor.clave;
			nodo.valor = sucesor.valor;
			nodo = sucesor;
		}
		/*
		 * Casos: hoja solo hijo izquierdo solo hijo derecho
		 */
		Nodo<C, V> reemplazo;
		if (nodo.hijoIzquierdo != null) {
			reemplazo = nodo.hijoIzquierdo;
		} else {
			reemplazo = nodo.hijoDerecho;
		}
		if (reemplazo != null) {
			reemplazo.padre = nodo.padre;
			if (nodo.padre == null) {
				raiz = reemplazo;
			} else if (nodo == nodo.padre.hijoIzquierdo) {
				nodo.padre.hijoIzquierdo = reemplazo;
			} else {
				nodo.padre.hijoDerecho = reemplazo;
			}
		}
		else {
			if (nodo.padre == null) {
				raiz = null;
			} else if (nodo == nodo.padre.hijoIzquierdo) {
				nodo.padre.hijoIzquierdo = null;
			} else {
				nodo.padre.hijoDerecho = null;
			}
		}
		cantidadDatos--;
		return valorEliminado;
	}
	/**
	 * Obtiene un mapeo {clave, valor} del diccionario, si existe.
	 *
	 * @param clave Clave a buscar.
	 * @return el valor buscado. Si no existe, devuelve null.
	 */
	public V obtenerValor(C clave) {
		Nodo<C, V> actual = raiz;
		while (actual != null) {
			int comparacion = comparador.comparar(clave, actual.clave);
			if (comparacion == 0) {
				return actual.valor;
			}
			if (comparacion < 0) {
				actual = actual.hijoIzquierdo;
			} else {
				actual = actual.hijoDerecho;
			}
		}
		return null;
	}
	/**
	 * Devuelve el recorrido inorder del árbol.
	 *
	 * @return el recorrido.
	 */
	public Lista<Tupla<C, V>> inorder() {
		Lista<Tupla<C, V>> recorrido = new Lista<>();
		inorder(raiz, recorrido);
		return recorrido;
	}
	/**
	 * Realiza recursivamente el recorrido inorder de un subárbol.
	 * <p>
	 * Visita primero el subárbol izquierdo, luego el nodo actual y finalmente el
	 * subárbol derecho.
	 *
	 * @param nodo      Raíz del subárbol a recorrer.
	 * @param recorrido Lista donde se almacenan las tuplas visitadas.
	 */
	private void inorder(Nodo<C, V> nodo, Lista<Tupla<C, V>> recorrido) {
		if (nodo == null) {
			return;
		}
		inorder(nodo.hijoIzquierdo, recorrido);
		recorrido.agregar(new Tupla<>(nodo.clave, nodo.valor));
		inorder(nodo.hijoDerecho, recorrido);
	}
	/**
	 * Devuelve el recorrido preorder del árbol.
	 *
	 * @return el recorrido.
	 */
	public Lista<Tupla<C, V>> preorder() {
		Lista<Tupla<C, V>> recorrido = new Lista<>();
		preorder(raiz, recorrido);
		return recorrido;
	}
	/**
	 * Realiza recursivamente el recorrido preorder de un subárbol.
	 * <p>
	 * Visita primero el nodo actual, luego el subárbol izquierdo y finalmente el
	 * subárbol derecho.
	 *
	 * @param nodo      Raíz del subárbol a recorrer.
	 * @param recorrido Lista donde se almacenan las tuplas visitadas.
	 */
	private void preorder(Nodo<C, V> nodo, Lista<Tupla<C, V>> recorrido) {
		if (nodo == null) {
			return;
		}
		recorrido.agregar(new Tupla<>(nodo.clave, nodo.valor));
		preorder(nodo.hijoIzquierdo, recorrido);
		preorder(nodo.hijoDerecho, recorrido);
	}
	/**
	 * Devuelve el recorrido postorder del árbol.
	 *
	 * @return el recorrido.
	 */
	public Lista<Tupla<C, V>> postorder() {
		Lista<Tupla<C, V>> recorrido = new Lista<>();
		postorder(raiz, recorrido);
		return recorrido;
	}
	/**
	 * Realiza recursivamente el recorrido postorder de un subárbol.
	 * <p>
	 * Visita primero el subárbol izquierdo, luego el subárbol derecho y finalmente
	 * el nodo actual.
	 *
	 * @param nodo      Raíz del subárbol a recorrer.
	 * @param recorrido Lista donde se almacenan las tuplas visitadas.
	 */
	private void postorder(Nodo<C, V> nodo, Lista<Tupla<C, V>> recorrido) {
		if (nodo == null) {
			return;
		}
		postorder(nodo.hijoIzquierdo, recorrido);
		postorder(nodo.hijoDerecho, recorrido);
		recorrido.agregar(new Tupla<>(nodo.clave, nodo.valor));
	}
	/**
	 * Devuelve el recorrido en ancho del árbol.
	 *
	 * @return el recorrido.
	 */
	public Lista<Tupla<C, V>> ancho() {
		Lista<Tupla<C, V>> recorrido = new Lista<>();
		if (raiz == null) {
			return recorrido;
		}
		Cola<Nodo<C, V>> cola = new Cola<>();
		cola.agregar(raiz);
		while (!cola.vacio()) {
			Nodo<C, V> actual = cola.eliminar();
			recorrido.agregar(new Tupla<>(actual.clave, actual.valor));
			if (actual.hijoIzquierdo != null) {
				cola.agregar(actual.hijoIzquierdo);
			}
			if (actual.hijoDerecho != null) {
				cola.agregar(actual.hijoDerecho);
			}
		}
		return recorrido;
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
}

