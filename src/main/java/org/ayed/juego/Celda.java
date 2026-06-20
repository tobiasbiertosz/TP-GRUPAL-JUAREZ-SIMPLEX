package org.ayed.juego;

import org.ayed.poe.*;

public class Celda {

    private Posicion posicion;
    private TipoCelda tipo; // Incendida - Normal - Congelada - Pared - Electrificada

    private Item item; // armas
    private Entidad entidad; // Jugador - Monstruo

    // PRE: Reciba una posicion y un tipo de celda
    // POS: -
    public Celda(Posicion posicion, TipoCelda tipo) {
        this.posicion = posicion;
        this.tipo = tipo;
    }

    public Posicion getPosicion() {
        return this.posicion;
    }

    public TipoCelda getTipo() {
        return this.tipo;
    }

    public Item getItem() {
        return this.item;
    }

    public Entidad getEntidad() {
        return this.entidad;
    }

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    // PRE: -
    // POS: Devuelve true si la celda es transitable (no es pared), false en caso
    // contrario
    public boolean esTransitable() {
        return tipo != TipoCelda.PARED;
    }

    // PRE: -
    // POS: Devuelve el item que se encuentra en la celda y lo elimina de la misma
    public Item recogerItem() {

        Item aux = item;

        item = null;

        return aux;
    }

    // PRE: -
    // POS: Elimina la entidad que se encuentra en la celda
    public void eliminarEntidad() {
        this.entidad = null;
    }

}
