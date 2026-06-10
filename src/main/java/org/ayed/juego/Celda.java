package org.ayed.juego;

public class Celda {

    private Posicion posicion;
    private TipoCelda tipo;

    public Celda(Posicion posicion, TipoCelda tipo) {
        this.posicion = posicion;
        this.tipo = tipo;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public TipoCelda getTipo() {
        return tipo;
    }

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }
}
