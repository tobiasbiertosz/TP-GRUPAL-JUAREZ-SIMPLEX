package org.ayed.juego;

/**
 * Representa cualquier entidad presente en la mazmorra. Puede ser un personaje
 * o un monstruo.
 */
public abstract class Entidad {

	protected String nombre;

	protected int vidaActual;

	// Capacidad máxima de vida de la Entidad
	protected int vidaBase;

	protected int danoBase;

	protected Posicion posicion;

	// Nuevos atributos para soportar los efectos de las celdas especiales
	protected double multiplicadorDanioEmitido; // Para el estado CONGELADA
	protected double multiplicadorDanioRecibido; // Para el estado ELECTRIFICADA

	/**
	 * Constructor de una entidad.
	 *
	 * @param nombre   Nombre de la entidad.
	 * @param vida     Vida inicial de la entidad.
	 * @param dano     Daño base de la entidad.
	 * @param posicion Posición inicial.
	 *
	 * @pre: posicion != null
	 * @post: se crea una nueva entidad con los atributos indicados y los
	 *        multiplicadores elementales inicializados en 1.0 (estado normal).
	 */
	public Entidad(String nombre, int vida, int dano, Posicion posicion) {

		this.nombre = nombre;

		this.vidaBase = vida;
		this.vidaActual = vida;

		this.danoBase = dano;

		this.posicion = posicion;

		// En la clase Combate se deberán utilizar:

		// DañoFinal = dañoBase * 0.5
		this.multiplicadorDanioEmitido = 1.0;

		// DañoSufridoReal = DañoDeLaEntidad * 1.5
		this.multiplicadorDanioRecibido = 1.0;
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
	 * 
	 *         Es una versión más simple del método <desplazarJugador> que se
	 *         encuentra en la clase Mazmorra
	 *         Ideal para que se use este método en la IA.
	 */
	public boolean mover(Mazmorra mazmorra, int nuevaFila, int nuevaColumna) {

		boolean movio = false;

		if (mazmorra.esTransitable(nuevaFila, nuevaColumna)) {

			posicion.setFila(nuevaFila);
			posicion.setColumna(nuevaColumna);
			movio = true;
		}

		return movio;
	}

	/**
	 * @pre: -
	 * @post: Devuelve la posición actual de la entidad.
	 */
	public Posicion getPosicion() {
		return this.posicion;
	}

	/**
	 * @pre: -
	 * @post: Devuelve el modificador de daño que emite la entidad.
	 */
	public double getMultiplicadorDanioEmitido() {
		return this.multiplicadorDanioEmitido;
	}

	/**
	 * @pre: multiplicador >= 0.0
	 * @post: Establece el nuevo multiplicador para el daño que realiza la entidad.
	 */
	public void setMultiplicadorDanioEmitido(double multiplicador) {
		this.multiplicadorDanioEmitido = multiplicador;
	}

	/**
	 * @pre: -
	 * @post: Devuelve el modificador de daño que recibe la entidad.
	 */
	public double getMultiplicadorDanioRecibido() {
		return this.multiplicadorDanioRecibido;
	}

	/**
	 * @pre: multiplicador >= 0.0
	 * @post: Establece el nuevo multiplicador para el daño que sufre la entidad.
	 */
	public void setMultiplicadorDanioRecibido(double multiplicador) {
		this.multiplicadorDanioRecibido = multiplicador;
	}
}
