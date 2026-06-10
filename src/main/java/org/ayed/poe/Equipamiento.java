package org.ayed.poe;

/**
 * Representa los objetos equipados actualmente por el personaje.
 */
public class Equipamiento {

	private Item armadura;
	private Item botas;
	private Item guantes;
	private Item casco;
	private Item cinturon;

	private Item anillo1;
	private Item anillo2;

	private Item pendiente;

	private Item armaPrincipal;

	public Equipamiento() {
	}

	public Item getArmadura() {
		return armadura;
	}

	public Item getBotas() {
		return botas;
	}

	public Item getGuantes() {
		return guantes;
	}

	public Item getCasco() {
		return casco;
	}

	public Item getCinturon() {
		return cinturon;
	}

	public Item getAnillo1() {
		return anillo1;
	}

	public Item getAnillo2() {
		return anillo2;
	}

	public Item getPendiente() {
		return pendiente;
	}

	public Item getArmaPrincipal() {
		return armaPrincipal;
	}

	public void equiparArmadura(Item item) {
		armadura = item;
	}

	public void equiparBotas(Item item) {
		botas = item;
	}

	public void equiparGuantes(Item item) {
		guantes = item;
	}

	public void equiparCasco(Item item) {
		casco = item;
	}

	public void equiparCinturon(Item item) {
		cinturon = item;
	}

	public void equiparAnillo1(Item item) {
		anillo1 = item;
	}

	public void equiparAnillo2(Item item) {
		anillo2 = item;
	}

	public void equiparPendiente(Item item) {
		pendiente = item;
	}

	public void equiparArma(Item item) {
		armaPrincipal = item;
	}
}
