package org.ayed.poe;

import java.util.Objects;

/**
 * Representa un item que puede almacenarse en el inventario.
 * <p>
 * Cada item posee un tamaño dentro de la grilla del inventario y puede otorgar
 * estadísticas al personaje cuando es equipado.
 */
public class Item {

	private final int id;
	private final int ancho;
	private final int alto;

	private String nombre;

	private RarezaItem rareza;
	private TipoItem tipo;

	private int vida;
	private int mana;
	private int danoAtaque;
	private int danoHechizo;
	private int armadura;

	private TipoArma tipoArma;
	private int rango;

	/**
	 * Constructor de un Item.
	 *
	 * @param id     Identificador único del item.
	 * @param ancho  Ancho del item dentro del inventario.
	 * @param alto   Alto del item dentro del inventario.
	 * @param nombre Nombre del item.
	 * @param rareza Rareza del item.
	 * @param tipo   Tipo de item.
	 *
	 * @pre: id >= 0, ancho > 0 y alto > 0.
	 * @post: se crea un item con las características indicadas y todas sus
	 *        estadísticas inicializadas en 0.
	 */
	public Item(int id, int ancho, int alto, String nombre, RarezaItem rareza, TipoItem tipo) {

		this.id = id;
		this.ancho = ancho;
		this.alto = alto;

		this.nombre = nombre;
		this.rareza = rareza;
		this.tipo = tipo;

		this.vida = 0;
		this.mana = 0;
		this.danoAtaque = 0;
		this.danoHechizo = 0;
		this.armadura = 0;

		this.tipoArma = null;
		this.rango = 0;
	}

	/**
	 * Constructor sin parámetros.
	 *
	 * @pre:
	 * @post: crea un item vacío con todos sus atributos inicializados con valores
	 *        por defecto.
	 */
	public Item() {
		this.id = 0;
		this.ancho = 0;
		this.alto = 0;

		this.nombre = "";

		this.rareza = RarezaItem.NORMAL;
		this.tipo = null;

		this.vida = 0;
		this.mana = 0;
		this.danoAtaque = 0;
		this.danoHechizo = 0;
		this.armadura = 0;

		this.tipoArma = null;
		this.rango = 0;
	}
	
	public Item(int id, int ancho, int alto) {
		this.id = id;
		this.ancho = ancho;
		this.alto = alto;

		this.nombre = "";

		this.rareza = RarezaItem.NORMAL;
		this.tipo = null;

		this.vida = 0;
		this.mana = 0;
		this.danoAtaque = 0;
		this.danoHechizo = 0;
		this.armadura = 0;

		this.tipoArma = null;
		this.rango = 0;
	}

	/**
	 * Verifica la igualdad basándose en todos los atributos del item.
	 *
	 * @param o Objeto a comparar.
	 * @return true si ambos items poseen exactamente los mismos atributos, false en
	 *         caso contrario.
	 *
	 * @pre:
	 * @post: no modifica el estado del objeto.
	 */
	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Item item = (Item) o;

		return id == item.id && ancho == item.ancho && alto == item.alto && vida == item.vida && mana == item.mana
				&& danoAtaque == item.danoAtaque && danoHechizo == item.danoHechizo && armadura == item.armadura
				&& rango == item.rango && Objects.equals(nombre, item.nombre) && rareza == item.rareza
				&& tipo == item.tipo && tipoArma == item.tipoArma;
	}

	/**
	 * Genera un código hash consistente con equals().
	 *
	 * @return valor hash del objeto.
	 *
	 * @pre:
	 * @post: no modifica el estado del objeto.
	 */
	@Override
	public int hashCode() {

		return Objects.hash(id, ancho, alto, nombre, rareza, tipo, vida, mana, danoAtaque, danoHechizo, armadura,
				tipoArma, rango);
	}

	/**
	 * Obtiene el identificador del item.
	 *
	 * @return id del item.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Obtiene el ancho del item.
	 *
	 * @return ancho del item.
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Obtiene el alto del item.
	 *
	 * @return alto del item.
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * Obtiene el nombre del item.
	 *
	 * @return nombre del item.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene la rareza del item.
	 *
	 * @return rareza del item.
	 */
	public RarezaItem getRareza() {
		return rareza;
	}

	/**
	 * Obtiene el tipo del item.
	 *
	 * @return tipo del item.
	 */
	public TipoItem getTipo() {
		return tipo;
	}

	/**
	 * Obtiene la bonificación de vida.
	 *
	 * @return vida otorgada por el item.
	 */
	public int getVida() {
		return vida;
	}

	/**
	 * Obtiene la bonificación de maná.
	 *
	 * @return maná otorgado por el item.
	 */
	public int getMana() {
		return mana;
	}

	public int getDanoAtaque() {
		return danoAtaque;
	}

	public int getDanoHechizo() {
		return danoHechizo;
	}

	public int getArmadura() {
		return armadura;
	}

	public TipoArma getTipoArma() {
		return tipoArma;
	}

	public int getRango() {
		return rango;
	}

	/**
	 * Modifica la bonificación de vida.
	 *
	 * @param vida Nueva bonificación de vida.
	 */
	public void setVida(int vida) {
		this.vida = vida;
	}

	/**
	 * Modifica la bonificación de maná.
	 *
	 * @param mana Nueva bonificación de maná.
	 */
	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setDanoAtaque(int danoAtaque) {
		this.danoAtaque = danoAtaque;
	}

	public void setDanoHechizo(int danoHechizo) {
		this.danoHechizo = danoHechizo;
	}

	public void setArmadura(int armadura) {
		this.armadura = armadura;
	}

	public void setTipoArma(TipoArma tipoArma) {
		this.tipoArma = tipoArma;
	}

	public void setRango(int rango) {
		this.rango = rango;
	}
}