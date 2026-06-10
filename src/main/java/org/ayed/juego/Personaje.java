package org.ayed.juego;

import org.ayed.poe.Equipamiento;
import org.ayed.poe.Inventario;

/**
 * Representa al personaje controlado por el jugador.
 */
public class Personaje extends Entidad {

	private Inventario inventario;
	private Equipamiento equipamiento;

	public Personaje(String nombre, int vida, int dano, Posicion posicion) {

		super(nombre, vida, dano, posicion);

		this.inventario = new Inventario(10, 6);
		this.equipamiento = new Equipamiento();
	}

	/**
	 * Obtiene el inventario del personaje.
	 *
	 * @return inventario asociado al personaje.
	 */
	public Inventario getInventario() {
		return inventario;
	}

	/**
	 * Obtiene el equipamiento actual del personaje.
	 *
	 * @return equipamiento del personaje.
	 */
	public Equipamiento getEquipamiento() {
		return equipamiento;
	}
}
