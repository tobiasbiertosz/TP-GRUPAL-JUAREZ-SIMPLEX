package org.ayed.juego;

import org.ayed.tda.matriz.Matriz;

public class Mazmorra {

	private Matriz<Celda> celdas;
	private int filas;
	private int columnas;

	// PRE: Reciba la cantidad de filas y columnas de la mazmorra
	// POS: Crea una mazmorra con las dimensiones recibidas y llena cada celda con
	// una celda de tipo NORMAL
	public Mazmorra(int filas, int columnas) {

		int i;
		int j;
		Posicion posicionAuxiliar;

		this.filas = filas;
		this.columnas = columnas;

		celdas = new Matriz<Celda>(filas, columnas);

		for (i = 0; i < filas; i++) {
			for (j = 0; j < columnas; j++) {

				posicionAuxiliar = new Posicion(i, j);
				celdas.asignar(i, j, new Celda(posicionAuxiliar, TipoCelda.NORMAL));
			}
		}
	}

	public Celda getCelda(int fila, int columna) {
		return celdas.elemento(fila, columna);
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}

	/**
	 * PRE: Recibe las coordenadas de una celda dentro de la mazmorra.
	 * POS: Si las coordenadas pertenecen a una posición válida dentro de la
	 * mazmorra, cambia el tipo de la celda elegida a PARED.
	 */
	public void colocarPared(int fila, int columna) {
		if (esPosicionValida(fila, columna)) {
			celdas.elemento(fila, columna).setTipo(TipoCelda.PARED);
		}
	}

	/**
	 * PRE: Recibe las coordenadas de una celda dentro de la mazmorra.
	 * POS: Si las coordenadas pertenecen a una posición válida dentro de la
	 * mazmorra, cambia el tipo de la celda elegida a CONGELADA.
	 */
	public void colocarHielo(int fila, int columna) {
		if (esPosicionValida(fila, columna)) {
			celdas.elemento(fila, columna).setTipo(TipoCelda.CONGELADA);
		}
	}

	/**
	 * PRE: Recibe las coordenadas de una celda dentro de la mazmorra.
	 * POS: Si las coordenadas pertenecen a una posición válida dentro de la
	 * mazmorra, cambia el tipo de la celda elegida a INCENDIADA.
	 */
	public void colocarFuego(int fila, int columna) {
		if (esPosicionValida(fila, columna)) {
			celdas.elemento(fila, columna).setTipo(TipoCelda.INCENDIADA);
		}
	}

	/**
	 * PRE: Recibe las coordenadas de una celda dentro de la mazmorra.
	 * POS: Si las coordenadas pertenecen a una posición válida dentro de la
	 * mazmorra, cambia el tipo de la celda elegida a ELECTRIFICADA.
	 */
	public void colocarElectricidad(int fila, int columna) {
		if (esPosicionValida(fila, columna)) {
			celdas.elemento(fila, columna).setTipo(TipoCelda.ELECTRIFICADA);
		}
	}

	/**
	 * PRE: Recibe las coordenadas de una celda dentro de la mazmorra.
	 * POS: Devuelve true si las coordenadas pertenecen a una posición válida dentro
	 * de la mazmorra. De lo contrario, devuelve false.
	 */
	public boolean esPosicionValida(int fila, int columna) {

		return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
	}

	/**
	 * PRE: Recibe las coordenadas de una celda dentro de la mazmorra.
	 * POST: Devuelve verdadero si la posición es válida y la celda en dicha
	 * coordenada
	 * no es de tipo PARED. De lo contrario, devuelve falso.
	 */
	public boolean esTransitable(int fila, int columna) {
		boolean transitable;

		transitable = false;
		if (esPosicionValida(fila, columna)) {

			transitable = celdas.elemento(fila, columna).esTransitable();
		}

		return transitable;
	}

	/**
	 * PRE: La entidad no debe ser nula. Recibe las coordenadas de una celda dentro
	 * de la mazmorra y una entidad a ubicar.
	 * POST: Si la posición es válida y transitable, ubica a la entidad en la celda
	 * correspondiente y actualiza la posición interna de la entidad.
	 */
	public void ubicarEntidad(int fila, int columna, Entidad entidad) {
		if (esTransitable(fila, columna)) {
			celdas.elemento(fila, columna).setEntidad(entidad);
			// Seteamos los datos de ubicación para que la identidad sepa donde está.
			entidad.getPosicion().setFila(fila);
			entidad.getPosicion().setColumna(columna);
		}
	}

	/**
	 * PRE: Las posiciones de origen y destino no deben ser nulas y deben ser
	 * válidas.
	 * POST: Si la celda de destino es transitable, traslada la entidad de la celda
	 * de origen
	 * a la de destino.
	 */
	public void moverEntidad(Posicion origen, Posicion destino) {
		int filaOrigen;
		int columnaOrigen;
		int filaDestino;
		int columnaDestino;
		Entidad entidadAuxiliar;

		filaOrigen = origen.getFila();
		columnaOrigen = origen.getColumna();
		filaDestino = destino.getFila();
		columnaDestino = destino.getColumna();

		if (esTransitable(filaDestino, columnaDestino)) {
			// Sacamos la entidad del origen
			// Obtenemos el dato de la identidad a mover
			entidadAuxiliar = celdas.elemento(filaOrigen, columnaOrigen).getEntidad();
			// Barremos la entidad que se encuentra en el origen
			celdas.elemento(filaOrigen, columnaOrigen).setEntidad(null);

			// La ponemos en el destino
			celdas.elemento(filaDestino, columnaDestino).setEntidad(entidadAuxiliar);

			// Actualizamos las coordenadas de la entidad
			entidadAuxiliar.getPosicion().setFila(filaDestino);
			entidadAuxiliar.getPosicion().setColumna(columnaDestino);
		}
	}

	/**
	 * PRE: El jugador no debe ser nulo y debe estar ubicado en una posición válida.
	 * 'direccion' debe ser un carácter válido ('W', 'A', 'S', 'D') en mayúscula o
	 * minúscula.
	 * POS: Si la celda destino en esa dirección es transitable, desplaza al
	 * jugador y aplica el efecto elemental de la nueva celda.
	 */
	public void desplazarJugador(Entidad jugador, char direccion) {
		int filaActual;
		int columnaActual;
		int nuevaFila;
		int nuevaColumna;
		char direccionMayuscula;
		Posicion origen;
		Posicion destino;

		filaActual = jugador.getPosicion().getFila();
		columnaActual = jugador.getPosicion().getColumna();
		nuevaFila = filaActual;
		nuevaColumna = columnaActual;

		direccionMayuscula = Character.toUpperCase(direccion);

		switch (direccionMayuscula) {
			case 'W':
				nuevaFila = filaActual - 1;
				break;
			case 'S':
				nuevaFila = filaActual + 1;
				break;
			case 'A':
				nuevaColumna = columnaActual - 1;
				break;
			case 'D':
				nuevaColumna = columnaActual + 1;
				break;
		}

		if (esTransitable(nuevaFila, nuevaColumna)) {
			origen = jugador.getPosicion();
			destino = new Posicion(nuevaFila, nuevaColumna);

			// Movemos la entidad al lugar destino
			moverEntidad(origen, destino);

			// Al terminar de moverse, el jugador sufre o recibe los efectos elementales de
			// la celda
			aplicarEfectoCelda(jugador);
		}
	}

	/**
	 * PRE: La entidad no debe ser nula y debe estar ubicada en una posición válida
	 * de la mazmorra.
	 * POS: Evalúa el tipo de terreno de la celda donde está parada la entidad y
	 * aplica las alteraciones de estado correspondientes en sus estadísticas de
	 * combate.
	 */
	public void aplicarEfectoCelda(Entidad entidad) {
		int fila;
		int columna;
		TipoCelda tipo;

		fila = entidad.getPosicion().getFila();
		columna = entidad.getPosicion().getColumna();

		if (esPosicionValida(fila, columna)) {
			tipo = celdas.elemento(fila, columna).getTipo();

			switch (tipo) {
				case INCENDIADA:
					// Recibe +10 de daño directo por pisar fuego
					entidad.recibirDanio(10);
					break;

				case CONGELADA:
					// DañoFinal = dañoBase * 0.5
					// Se debilita: inflige la mitad de su daño (0.5)
					entidad.setMultiplicadorDanioEmitido(0.5);
					break;

				case ELECTRIFICADA:
					// DañoSufridoReal = DañoDeLaEntidad * 1.5
					// Se vuelve vulnerable: recibe un 50% más de daño de los ataques (1.5)
					entidad.setMultiplicadorDanioRecibido(1.5);
					break;

				case NORMAL:
					// Al volver a pisar tierra normal, se restauran sus multiplicadores a 1.0
					entidad.setMultiplicadorDanioEmitido(1.0);
					entidad.setMultiplicadorDanioRecibido(1.0);
					break;

				case PARED:
					// Una entidad no debería poder pisar una pared por el método esTransitable
					break;
			}
		}
	}

	/**
	 * PRE: -
	 * POS: Imprime en la consola (CLI) una representación visual del estado actual
	 * de la mazmorra, mostrando las celdas especiales, las paredes y las entidades.
	 */
	public void mostrar() {
		int i;
		int j;
		Celda celdaActual;
		TipoCelda tipo;

		for (i = 0; i < filas; i++) {
			for (j = 0; j < columnas; j++) {
				celdaActual = celdas.elemento(i, j);

				Entidad e = celdaActual.getEntidad();

				if (e != null) {
					if (e instanceof Personaje) {
						System.out.print("👾 ");
					} else if (e instanceof Monstruo) {
						System.out.print("👹 ");
					}

				} else {
					// Sino dibujamos según el tipo de terreno.
					tipo = celdaActual.getTipo();
					switch (tipo) {
						case NORMAL:
							System.out.print("⬜ ");
							break;
						case PARED:
							System.out.print("🧱 ");
							break;
						case INCENDIADA:
							System.out.print("🔥 ");
							break;
						case CONGELADA:
							System.out.print("❄️ ");
							break;
						case ELECTRIFICADA:
							System.out.print("⚡ ");
							break;
					}
				}
			}

			System.out.println();
		}
		System.out.println();
	}

}
