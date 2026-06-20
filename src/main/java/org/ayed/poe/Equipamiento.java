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
	
//Getters
	
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
	
//Equipar
	public void equiparArmadura(Item item) {
		if (item != null && item.getTipo() != TipoItem.ARMADURA) {
			throw new IllegalArgumentException();
		}

		this.armadura = item;
	}

	public void equiparBotas(Item item) {
		if (item != null && item.getTipo() != TipoItem.BOTAS) {
			throw new IllegalArgumentException();
		}

		this.botas = item;
	}

	public void equiparGuantes(Item item) {
		if (item != null && item.getTipo() != TipoItem.GUANTES) {
			throw new IllegalArgumentException();
		}

		this.guantes = item;
	}

	public void equiparCasco(Item item) {
		if (item != null && item.getTipo() != TipoItem.CASCO) {
			throw new IllegalArgumentException();
		}

		this.casco = item;
	}

	public void equiparCinturon(Item item) {
		if (item != null && item.getTipo() != TipoItem.CINTURON) {
			throw new IllegalArgumentException();
		}

		this.cinturon = item;
	}

	public void equiparAnillo1(Item item) {
		if (item != null && item.getTipo() != TipoItem.ANILLO) {
			throw new IllegalArgumentException();
		}

		this.anillo1 = item;
	}

	public void equiparAnillo2(Item item) {
		if (item != null && item.getTipo() != TipoItem.ANILLO) {
			throw new IllegalArgumentException();
		}

		this.anillo2 = item;
	}

	public void equiparPendiente(Item item) {
		if (item != null && item.getTipo() != TipoItem.PENDIENTE) {
			throw new IllegalArgumentException();
		}

		this.pendiente = item;
	}

	public void equiparArma(Item item) {
		if (item != null && item.getTipo() != TipoItem.ARMA) {
			throw new IllegalArgumentException();
		}

		this.armaPrincipal = item;
	}

	//Desequipar
	public Item desequiparArmadura() {
		Item item = armadura;
		armadura = null;
		return item;
	}

	public Item desequiparBotas() {
		Item item = botas;
		botas = null;
		return item;
	}

	public Item desequiparGuantes() {
		Item item = guantes;
		guantes = null;
		return item;
	}

	public Item desequiparCasco() {
		Item item = casco;
		casco = null;
		return item;
	}

	public Item desequiparCinturon() {
		Item item = cinturon;
		cinturon = null;
		return item;
	}

	public Item desequiparAnillo1() {
		Item item = anillo1;
		anillo1 = null;
		return item;
	}

	public Item desequiparAnillo2() {
		Item item = anillo2;
		anillo2 = null;
		return item;
	}

	public Item desequiparPendiente() {
		Item item = pendiente;
		pendiente = null;
		return item;
	}

	public Item desequiparArma() {
		Item item = armaPrincipal;
		armaPrincipal = null;
		return item;
	}

	//Estadisticas totales
	public int getVidaTotal() {

		return obtenerVida(armadura)
				+ obtenerVida(botas)
				+ obtenerVida(guantes)
				+ obtenerVida(casco)
				+ obtenerVida(cinturon)
				+ obtenerVida(anillo1)
				+ obtenerVida(anillo2)
				+ obtenerVida(pendiente)
				+ obtenerVida(armaPrincipal);
	}

	public int getManaTotal() {

		return obtenerMana(armadura)
				+ obtenerMana(botas)
				+ obtenerMana(guantes)
				+ obtenerMana(casco)
				+ obtenerMana(cinturon)
				+ obtenerMana(anillo1)
				+ obtenerMana(anillo2)
				+ obtenerMana(pendiente)
				+ obtenerMana(armaPrincipal);
	}

	public int getDanoAtaqueTotal() {

		return obtenerDanoAtaque(armadura)
				+ obtenerDanoAtaque(botas)
				+ obtenerDanoAtaque(guantes)
				+ obtenerDanoAtaque(casco)
				+ obtenerDanoAtaque(cinturon)
				+ obtenerDanoAtaque(anillo1)
				+ obtenerDanoAtaque(anillo2)
				+ obtenerDanoAtaque(pendiente)
				+ obtenerDanoAtaque(armaPrincipal);
	}

	public int getDanoHechizoTotal() {

		return obtenerDanoHechizo(armadura)
				+ obtenerDanoHechizo(botas)
				+ obtenerDanoHechizo(guantes)
				+ obtenerDanoHechizo(casco)
				+ obtenerDanoHechizo(cinturon)
				+ obtenerDanoHechizo(anillo1)
				+ obtenerDanoHechizo(anillo2)
				+ obtenerDanoHechizo(pendiente)
				+ obtenerDanoHechizo(armaPrincipal);
	}

	public int getArmaduraTotal() {

		return obtenerArmadura(armadura)
				+ obtenerArmadura(botas)
				+ obtenerArmadura(guantes)
				+ obtenerArmadura(casco)
				+ obtenerArmadura(cinturon)
				+ obtenerArmadura(anillo1)
				+ obtenerArmadura(anillo2)
				+ obtenerArmadura(pendiente)
				+ obtenerArmadura(armaPrincipal);
	}

	//Auxiliares
	private int obtenerVida(Item item) {

		if (item == null) {
			return 0;
		}

		return item.getVida();
	}

	private int obtenerMana(Item item) {

		if (item == null) {
			return 0;
		}

		return item.getMana();
	}

	private int obtenerDanoAtaque(Item item) {

		if (item == null) {
			return 0;
		}

		return item.getDanoAtaque();
	}

	private int obtenerDanoHechizo(Item item) {

		if (item == null) {
			return 0;
		}

		return item.getDanoHechizo();
	}

	private int obtenerArmadura(Item item) {

		if (item == null) {
			return 0;
		}

		return item.getArmadura();
	}
	
	
}
