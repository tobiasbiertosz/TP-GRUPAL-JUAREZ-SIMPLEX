package org.ayed.juego;

public class Combate {

    public void atacar(Entidad atacante, Entidad defensor) {
        defensor.recibirDanio(atacante.getDano());
    }
}
