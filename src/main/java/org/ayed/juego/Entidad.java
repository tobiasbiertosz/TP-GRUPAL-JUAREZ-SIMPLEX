package org.ayed.juego;

/**
 * Representa cualquier entidad presente en la mazmorra. Puede ser un personaje
 * o un monstruo.
 */
public abstract class Entidad {

	protected String nombre;

	protected int vidaActual;
	protected int vidaBase;

	protected int danoBase;

	protected Posicion posicion;

	/**
	 * Constructor de una entidad.
	 *
	 * @param nombre   Nombre de la entidad.
	 * @param vida     Vida inicial de la entidad.
	 * @param dano     Daño base de la entidad.
	 * @param posicion Posición inicial.
	 *
	 * @pre: posicion != null
	 * @post: se crea una nueva entidad con los atributos indicados.
	 */
	public Entidad(String nombre, int vida, int dano, Posicion posicion) {

		this.nombre = nombre;

		this.vidaBase = vida;
		this.vidaActual = vida;

		this.danoBase = dano;

		this.posicion = posicion;
	}

	/**
	 * Reduce la vida actual de la entidad.
	 *
	 * @param cantidad Daño recibido.
	 *
	 * @pre: cantidad >= 0
	 * @post: la vida actual disminuye.
	 */
	public void recibirDanio(int cantidad) {

		vidaActual -= cantidad;

		if (vidaActual < 0) {
			vidaActual = 0;
		}
	}

	/**
	 * Recupera vida.
	 *
	 * @param cantidad Vida a recuperar.
	 *
	 * @pre: cantidad >= 0
	 * @post: la vida actual aumenta sin superar la vida máxima.
	 */
	public void curar(int cantidad) {

		vidaActual += cantidad;

		if (vidaActual > vidaBase) {
			vidaActual = vidaBase;
		}
	}

	/**
	 * Indica si la entidad sigue con vida.
	 *
	 * @return true si la vida actual es mayor a 0.
	 */
	public boolean estaVivo() {
		return vidaActual > 0;
	}

	/**
	 * Intenta mover la entidad a una nueva posición.
	 *
	 * @param mazmorra     Mazmorra donde se encuentra.
	 * @param nuevaFila    Fila destino.
	 * @param nuevaColumna Columna destino.
	 *
	 * @return true si pudo moverse.
	 */
	public boolean mover(Mazmorra mazmorra, int nuevaFila, int nuevaColumna) {

		boolean movio = false;

		if (mazmorra.esTransitable(nuevaFila, nuevaColumna)) {

			posicion.setFila(nuevaFila);
			posicion.setColumna(nuevaColumna);

			mazmorra.aplicarEfectoCelda(this);

			movio = true;
		}

		return movio;
	}

	public String getNombre() {
		return nombre;
	}

	public int getVidaActual() {
		return vidaActual;
	}

	public int getVidaBase() {
		return vidaBase;
	}

	public int getDanoBase() {
		return danoBase;
	}
	
	public int getDano() {
		return danoBase  ; // IDEA A FUTURO: return danoBAse + danoExtraDeLasArmas...habilidades, etc
	}

	public Posicion getPosicion() {
		return posicion;
	}
}