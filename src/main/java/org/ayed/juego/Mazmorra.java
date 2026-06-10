package org.ayed.juego;

import org.ayed.tda.matriz.Matriz;

public class Mazmorra {

	private Matriz<Celda> celdas;
	private int filas;
	private int columnas;

	public Mazmorra(int filas, int columnas) {

		int i;
		int j;

		this.filas = filas;
		this.columnas = columnas;

		celdas = new Matriz<Celda>(filas, columnas);

		for (i = 0; i < filas; i++) {
			for (j = 0; j < columnas; j++) {

				celdas.asignar(i, j, new Celda(new Posicion(i, j), TipoCelda.NORMAL));
			}
		}
	}

	public void colocarPared(int fila, int columna) {
		celdas.elemento(fila, columna).setTipo(TipoCelda.PARED);
	}

	public void colocarHielo(int fila, int columna) {
		celdas.elemento(fila, columna).setTipo(TipoCelda.CONGELADA);
	}

	public void colocarFuego(int fila, int columna) {
		celdas.elemento(fila, columna).setTipo(TipoCelda.INCENDIADA);
	}

	public void colocarElectricidad(int fila, int columna) {
		celdas.elemento(fila, columna).setTipo(TipoCelda.ELECTRIFICADA);
	}

	public boolean esPosicionValida(int fila, int columna) {

		return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
	}

	public boolean esTransitable(int fila, int columna) {

		boolean transitable = false;

		if (esPosicionValida(fila, columna)) {

			transitable = celdas.elemento(fila, columna).getTipo() != TipoCelda.PARED;
		}

		return transitable;
	}

	public void aplicarEfectoCelda(Entidad entidad) {

		TipoCelda tipo;

		tipo = celdas.elemento(entidad.getPosicion().getFila(), entidad.getPosicion().getColumna()).getTipo();

		switch (tipo) {

		case INCENDIADA:
			entidad.recibirDanio(10);
			break;

		case CONGELADA:
			entidad.recibirDanio(5);
			break;

		case ELECTRIFICADA:
			entidad.recibirDanio(15);
			break;

		default:
			break;
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
}