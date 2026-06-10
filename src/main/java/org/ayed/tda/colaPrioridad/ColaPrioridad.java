package org.ayed.tda.colaPrioridad;
import org.ayed.tda.comparador.Comparador;
import org.ayed.tda.vector.VectorDinamico;
public class ColaPrioridad<T> {
   private VectorDinamico<T> datos;
   private Comparador<T> comparador;
   /**
    * Constructor de ColaPrioridad.
    *
    * @param comparador Comparador a utilizar.
    *                   No puede ser nulo.
    * @throws ExcepcionColaPrioridad si el comparador es nulo.
    */
   public ColaPrioridad(Comparador<T> comparador) {
       if (comparador == null) {
           throw new ExcepcionColaPrioridad("El comparador no puede ser nulo.");
       }
       this.comparador = comparador;
       this.datos = new VectorDinamico<>();
   }
   /**
    * Constructor de copia de ColaPrioridad.
    *
    * @param colaPrioridad Cola a copiar.
    *                      No puede ser nula.
    * @throws ExcepcionColaPrioridad si la cola es nula.
    */
   public ColaPrioridad(ColaPrioridad<T> colaPrioridad) {
       if (colaPrioridad == null) {
           throw new ExcepcionColaPrioridad("La cola no puede ser nula.");
       }
       this.comparador = colaPrioridad.comparador;
       this.datos = new VectorDinamico<>(colaPrioridad.datos);
   }
      /**
    * Reordena el Heap para mantener el invariante.
    * Desplaza datos hacia arriba, comparando el dato actual
    * con su padre, hasta cumplir con el invariante.
    * Inicia en el último dato del vector.
    */
   private void heapificarHaciaArriba() {
       int indice = datos.tamanio() - 1;
       while (indice > 0 && comparador.comparar(datos.obtener(indice), datos.obtener((indice - 1) / 2)) > 0) {
           int indicePadre = (indice - 1) / 2;
           T actual = datos.obtener(indice);
           datos.cambiar(indice, datos.obtener(indicePadre));
           datos.cambiar(indicePadre, actual);
           indice = indicePadre;
       }
   }
   /**
    * Reordena el Heap para mantener el invariante.
    * Desplaza datos hacia abajo, comparando el dato actual
    * con el hijo con mayor prioridad, hasta cumplir con el
    * invariante. Inicia en el primer dato del vector.
    */
   private void heapificarHaciaAbajo() {
       int indice = 0;
       int tamanio = datos.tamanio();
       int hijoIzquierdo = 2 * indice + 1;
       int hijoDerecho = 2 * indice + 2;
       int mayor = indice;
       while (hijoIzquierdo < tamanio) {
           if (comparador.comparar(datos.obtener(hijoIzquierdo), datos.obtener(mayor)) > 0) {
               mayor = hijoIzquierdo;
           }
           if (hijoDerecho < tamanio && comparador.comparar(datos.obtener(hijoDerecho), datos.obtener(mayor)) > 0) {
               mayor = hijoDerecho;
           }
           if (mayor == indice) {
               hijoIzquierdo = tamanio;
           } else {
               T temp = datos.obtener(indice);
               datos.cambiar(indice, datos.obtener(mayor));
               datos.cambiar(mayor, temp);
               indice = mayor;
               hijoIzquierdo = 2 * indice + 1;
               hijoDerecho = 2 * indice + 2;
               mayor = indice;
           }
       }
           T temp = datos.obtener(indice);
           datos.cambiar(indice, datos.obtener(mayor));
           datos.cambiar(mayor, temp);
           indice = mayor;
   }


   /**
    * Agrega el dato a la cola, manteniendo el invariante del Heap.
    *
    * @param dato Dato a agregar.
    */
   public void agregar(T dato) {
       datos.agregar(dato);
       heapificarHaciaArriba();
   }
   /**
    * Elimina el siguiente dato de la cola (mayor prioridad),
    * manteniendo el invariante del Heap.
    *
    * @return el dato con mayor prioridad en la cola.
    * @throws ExcepcionColaPrioridad si la cola está vacía.
    */
   public T eliminar() {
       if (vacio()) {
           throw new ExcepcionColaPrioridad("La cola está vacía.");
       }
       T raiz = datos.obtener(0);
       T ultimo = datos.eliminar(); // elimina el último
       if (!vacio()) {
           datos.cambiar(0, ultimo);
           heapificarHaciaAbajo();
       }
       return raiz;
   }
   /**
    * Obtiene el siguiente dato de la cola (mayor prioridad).
    *
    * @return el dato con mayor prioridad en la cola.
    * @throws ExcepcionColaPrioridad si la cola está vacía.
    */
   public T siguiente() {
       if (vacio()) {
           throw new ExcepcionColaPrioridad("La cola está vacía.");
       }
       return datos.obtener(0);
   }
   /**
    * Obtiene el tamaño de la cola.
    *
    * @return el tamaño de la cola.
    */
   public int tamanio() {
       return datos.tamanio();
   }
   /**
    * Evalúa si la cola está vacía.
    *
    * @return true si la cola está vacía.
    */
   public boolean vacio() {
       return datos.vacio();
   }
}

