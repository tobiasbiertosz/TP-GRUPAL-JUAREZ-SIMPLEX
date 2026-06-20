package org.ayed.juego;

// Cada elemento del enum queda por defecto asignado con un valor ordinal
// Si yo quisiera saber de que se trata 
// TipoCelda celda = TipoCelda.INCENDIADA;
// System.out.println(celda.name()); -> Imprime: "INCENDIADA"
// System.out.println(celda.name()); -> Imprime: 2
public enum TipoCelda {
    NORMAL, // 0
    PARED, // 1
    INCENDIADA, // 2
    CONGELADA, // 3
    ELECTRIFICADA // 4
}
