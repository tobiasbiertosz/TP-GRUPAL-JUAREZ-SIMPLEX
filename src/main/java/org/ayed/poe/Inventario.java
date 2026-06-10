package org.ayed.poe;

import org.ayed.tda.vector.VectorDinamico;
import org.ayed.tda.matriz.Matriz;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Inventario {

	private Matriz<Item> grilla;
	private int alto;
	private int ancho;
	private VectorDinamico<Integer> ids;

	/**
	 * Constructor del Inventario.
	 * <p>
	 * Crea una grilla de inventario vacía con las dimensiones especificadas.
	 *
	 * @param ancho El ancho de la grilla del inventario.
	 * @param alto  La altura de la grilla del inventario.
	 * @throws IllegalArgumentException si el ancho o la altura son menores a 1.
	 * 
	 * @pre: ancho y alto deben ser mayores o iguales a 1.
	 * @post: se crea una grilla vacia con cantidad de filas = alto y cantidad
	 *        columnas = ancho.
	 */
	public Inventario(int ancho, int alto) {
		if (ancho < 1 || alto < 1) {
			throw new IllegalArgumentException();
		}

		this.ancho = ancho;
		this.alto = alto;
		this.grilla = new Matriz<>(alto, ancho);
		this.ids = new VectorDinamico<>();

	}

	/**
	 * Obtiene el item en una coordenada específica.
	 *
	 * @param x La coordenada x.
	 * @param y La coordenada y.
	 * @return El Item en la coordenada especificada, o null si la celda está vacía.
	 * @throws InventarioFueraDeLimitesException Si x o y están fuera de las
	 *                                           dimensiones de la grilla.
	 * 
	 * @pre: las coordenadas x e y deben estar dentro de las dimenciones de la
	 *       grilla.
	 * @post: devuelve el item en la coordenada especificada o en caso de estar
	 *        vacio retorna null.
	 */
	public Item obtenerItem(int x, int y) {
		if (x < 0 || x >= ancho || y < 0 || y >= alto) {
			throw new InventarioFueraDeLimitesException();
		}
		return grilla.elemento(y, x);
	}

	/**
	 * Coloca un item en una coordenada específica (x, y).
	 * <p>
	 * <b>Éxito:</b> El item es colocado. {@link #obtenerItem(int, int)} para cada
	 * coordenada dentro del área del item devuelve el item colocado.
	 *
	 * @param x    La coordenada x de la esquina superior izquierda.
	 * @param y    La coordenada y de la esquina superior izquierda.
	 * @param item El item a colocar.
	 * @throws InventarioFueraDeLimitesException Si el área del item cae parcial o
	 *                                           totalmente fuera de la grilla.
	 * @throws EspacioOcupadoException           Si una o más coordenadas requeridas
	 *                                           por el área ya están ocupadas.
	 * @throws ItemDuplicadoException            Si un item con el mismo ID ya
	 *                                           existe en el inventario.
	 * 
	 * @pre: el item no debe salirse de los limites de la grilla, no debe ocupar
	 *       espacio que ya esta ocupado ni estar repetido (otro item con el mismo
	 *       ID).
	 * @post:teniendo como "ancla" la coordenada (x,y) en la matriz, se coloca
	 *                cierto item.
	 */
	public void colocarItem(int x, int y, Item item) {
		int altoItem = item.getAlto();
		int anchoItem = item.getAncho();
		int idItem = item.getId();

		if (x + anchoItem > this.ancho || y + altoItem > this.alto) {
			throw new InventarioFueraDeLimitesException();
		}

		for (int i = x; i < x + anchoItem; i++) {
			for (int j = y; j < y + altoItem; j++) {
				if (grilla.elemento(j, i) != null) {
					throw new EspacioOcupadoException();
				}
			}
		}

		if (existeId(idItem)) {
			throw new ItemDuplicadoException();
		}

		for (int i = x; i < x + anchoItem; i++) {
			for (int j = y; j < y + altoItem; j++) {
				this.grilla.asignar(j, i, item);
			}
		}

		this.ids.agregar(idItem);
	}

	/**
	 * Coloca automáticamente un item en el primer espacio disponible.
	 * <p>
	 * Escanea el inventario de izquierda a derecha y de arriba hacia abajo para
	 * encontrar el primer bloque contiguo de celdas vacías para el item.
	 * <p>
	 * <b>Éxito:</b> El item es colocado en el primer espacio lógicamente
	 * disponible.
	 *
	 * @param item El item a colocar automáticamente.
	 * @throws InventarioLlenoException Si el inventario no tiene un bloque contiguo
	 *                                  de celdas vacías lo suficientemente grande.
	 * @throws ItemDuplicadoException   Si un item con el mismo ID ya existe en el
	 *                                  inventario.
	 * 
	 * @pre: el item debe tener espacio suficiente y este item no debe estar
	 *       repetido (otro item con el mismo ID).
	 * @post: se coloca el item en la primera posicion disponible.
	 */
	public void colocarItem(Item item) {

		int idItem = item.getId();

		if (existeId(idItem)) {
			throw new ItemDuplicadoException();
		}

		for (int x = 0; x < ancho; x++) {
			for (int y = 0; y < alto; y++) {
				try {
					colocarItem(x, y, item);
					return;
				} catch (InventarioFueraDeLimitesException | EspacioOcupadoException e) {

				}
			}
		}

		throw new InventarioLlenoException();

	}

	/**
	 * Elimina un item por su ID.
	 * <p>
	 * <b>Éxito:</b> Elimina el item con el ID correspondiente.
	 * {@link #obtenerItem(int, int)} para todas las coordenadas que el item ocupaba
	 * ahora devuelve null.
	 *
	 * @param id El ID del item a eliminar.
	 * @return El Item eliminado.
	 * @throws ItemNoEncontradoException Si no existe ningún item con el ID dado.
	 * 
	 * @pre:
	 * @post:se elimina item con el id, asignando null todas sus posiciones del
	 *          inventario.
	 */
	public Item eliminarItem(int id) {
		boolean encontrado = false;
		Item extraido = null;
		int anchoItem;
		int altoItem;
		int posicionId = 0;

		for (int i = 0; i < ids.tamanio(); i++) {
			if (ids.obtener(i).equals(id) && !encontrado) {
				encontrado = true;
				posicionId = i;
			}
		}

		if (!encontrado) {
			throw new ItemNoEncontradoException();
		}

		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				Item actual = obtenerItem(i, j);

				if (actual != null && actual.getId() == id && extraido == null) {
					extraido = actual;
					anchoItem = extraido.getAncho();
					altoItem = extraido.getAlto();

					for (int x = i; x < i + anchoItem; x++) {
						for (int y = j; y < j + altoItem; y++) {
							grilla.asignar(y, x, null);
						}
					}
				}
			}
		}

		ids.eliminar(posicionId);
		return extraido;
	}

	/**
	 * Elimina el item que ocupa una coordenada específica.
	 * <p>
	 * <b>Éxito:</b> Elimina el item que ocupa (x, y).
	 * {@link #obtenerItem(int, int)} para todas las coordenadas que el item ocupaba
	 * ahora devuelve null.
	 *
	 * @param x La coordenada x.
	 * @param y La coordenada y.
	 * @return El Item eliminado.
	 * @throws ItemNoEncontradoException         Si la coordenada (x, y) está
	 *                                           actualmente vacía (null).
	 * @throws InventarioFueraDeLimitesException Si la coordenada (x, y) consultada
	 *                                           está fuera de la grilla.
	 * 
	 * @pre: para eliminar las corrdenadas deben estar dentro de las dimenciones de
	 *       las grilla y el item debe efectivamente estar en esa coordenada (!=
	 *       null).
	 * @post: Se eliminan todas las celdas del item en (x,y), se remueve su id y se
	 *        retorna el item eliminado.
	 */
	public Item eliminarItem(int x, int y) {
		if (x < 0 || y < 0 || x >= this.ancho || y >= this.alto) {
			throw new InventarioFueraDeLimitesException();
		}

		Item item = obtenerItem(x, y);

		if (item == null) {
			throw new ItemNoEncontradoException();
		}

		int anchoItem = item.getAncho();
		int altoItem = item.getAlto();

		int anclaX = x;
		while (anclaX > 0 && obtenerItem(anclaX - 1, y) == item) {
			anclaX--;
		}

		int anclaY = y;
		while (anclaY > 0 && obtenerItem(anclaX, anclaY - 1) == item) {
			anclaY--;
		}

		for (int i = anclaX; i < anclaX + anchoItem; i++) {
			for (int j = anclaY; j < anclaY + altoItem; j++) {
				grilla.asignar(j, i, null);
			}
		}

		boolean eliminado = false;

		for (int i = 0; i < ids.tamanio() && !eliminado; i++) {
			if (ids.obtener(i).equals(item.getId())) {
				ids.eliminar(i);
				eliminado = true;
			}
		}

		return item;
	}

	/**
	 * Transfiere una lista de items al inventario como una única transacción
	 * atómica. (paso todo o no paso nada)
	 * <p>
	 * <b>Éxito:</b> El inventario tiene espacio suficiente para colocar
	 * secuencialmente cada item de la lista.
	 * <p>
	 * <b>Fallo:</b> El inventario debe revertirse totalmente a como estaba antes de
	 * llamar a este método.
	 *
	 * @param items La lista de items a transferir.
	 * @throws InventarioLlenoException Si el inventario se queda sin espacio antes
	 *                                  de que se procese toda la lista.
	 * @throws ItemDuplicadoException   Si el ID de un item entrante ya existe en el
	 *                                  inventario, o si la lista entrante contiene
	 *                                  duplicados.
	 * 
	 * @pre: los items a colocar no deben esar duplicados (no estar ya en la grilla,
	 *       ni estar repetidos en la misma lista).
	 * @post: se colocan todos los elementos de la lista en el inventario siempre
	 *        que todos tengan espacio.
	 */

	public void transferirItems(VectorDinamico<Item> items) {

		VectorDinamico<Item> colocados = new VectorDinamico<>();
		VectorDinamico<Integer> idsVistos = new VectorDinamico<>();

		try {
			for (int i = 0; i < items.tamanio(); i++) {
				Item item = items.obtener(i);
				int id = item.getId();

				// chequeo inventario
				for (int j = 0; j < ids.tamanio(); j++) {
					if (ids.obtener(j).equals(id)) {
						throw new ItemDuplicadoException();
					}
				}

				// chequeo lista
				for (int j = 0; j < idsVistos.tamanio(); j++) {
					if (idsVistos.obtener(j).equals(id)) {
						throw new ItemDuplicadoException();
					}
				}

				idsVistos.agregar(id);

				colocarItem(item);
				colocados.agregar(item);
			}

		} catch (InventarioLlenoException | ItemDuplicadoException e) {

			for (int k = 0; k < colocados.tamanio(); k++) {
				eliminarItem(colocados.obtener(k).getId());
			}

			throw e;
		}
	}

	/**
	 * Guarda el estado actual del inventario en un archivo.
	 * <p>
	 * El formato es específico de la implementación, pero debe soportar la
	 * reconstrucción completa del estado.
	 *
	 * @param ruta La ruta al archivo donde se guardará el inventario.
	 * 
	 * @pre: la ruta debe ser valida.
	 * @post: se guarda el estado completo del inventario en el archivo.
	 */

	public void guardar(String ruta) {
		try {
			PrintWriter escritor = new PrintWriter(new FileWriter(ruta));

			escritor.println(ancho + " " + alto);

			VectorDinamico<Integer> guardados = new VectorDinamico<>();

			for (int y = 0; y < alto; y++) {
				for (int x = 0; x < ancho; x++) {
					Item item = grilla.elemento(y, x);

					if (item != null) {
						int id = item.getId();

						boolean yaGuardado = false;
						for (int i = 0; i < guardados.tamanio(); i++) {
							if (guardados.obtener(i).equals(id)) {
								yaGuardado = true;
							}
						}

						if (!yaGuardado) {
							escritor.println(id + " " + item.getAncho() + " " + item.getAlto() + " " + x + " " + y);
							guardados.agregar(id);
						}
					}
				}
			}

			escritor.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Carga el estado del inventario desde un archivo.
	 * <p>
	 * <b>Éxito:</b> El inventario se reconstruye por completo.
	 * <p>
	 * <b>Fallo:</b> El inventario permanece vacío.
	 *
	 * @param filepath La ruta al archivo a cargar.
	 * @throws ArchivoNoExistenteException Si la ruta del archivo no existe.
	 * 
	 * @pre:el archivo debe existir.
	 * @post: se reconstruye el inventario a partir del archivo.
	 */

	public void cargar(String filepath) {

		File archivo = new File(filepath);

		if (!archivo.exists()) {
			throw new ArchivoNoExistenteException();
		}

		try {
			Scanner lector = new Scanner(archivo);

			int nuevoAncho = lector.nextInt();
			int nuevoAlto = lector.nextInt();

			Matriz<Item> nuevaGrilla = new Matriz<>(nuevoAlto, nuevoAncho);
			VectorDinamico<Integer> nuevosIds = new VectorDinamico<>();

			while (lector.hasNext()) {
				int id = lector.nextInt();
				int anchoItem = lector.nextInt();
				int altoItem = lector.nextInt();
				int x = lector.nextInt();
				int y = lector.nextInt();

				Item item = new Item(id, anchoItem, altoItem);

				for (int i = x; i < x + anchoItem; i++) {
					for (int j = y; j < y + altoItem; j++) {
						nuevaGrilla.asignar(j, i, item);
					}
				}

				nuevosIds.agregar(id);
			}

			lector.close();

			this.ancho = nuevoAncho;
			this.alto = nuevoAlto;
			this.grilla = nuevaGrilla;
			this.ids = nuevosIds;

		} catch (Exception e) {
			this.grilla = new Matriz<>(this.alto, this.ancho);
			this.ids = new VectorDinamico<>();
		}
	}

	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

	/**
	 * Verifica si un identificador ya se encuentra almacenado.
	 *
	 * @param id Identificador a buscar.
	 * @return true si el id existe en la estructura, false en caso contrario.
	 * 
	 * @pre: la estructura ids debe estar correctamente inicializada.
	 * @post: devuelve true si existe al menos una posicion i tal que
	 *        ids.obtener(i).equals(id); devuelve false en caso contrario; no se
	 *        modifica el contenido de la estructura ids.
	 */
	private boolean existeId(int id) {
		for (int i = 0; i < ids.tamanio(); i++) {
			if (ids.obtener(i).equals(id)) {
				return true;
			}
		}
		return false;
	}

}

