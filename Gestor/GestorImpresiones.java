import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

// TDA: pendientes es una cola FIFO de documentos por imprimir e historial es
// una pila LIFO de documentos ya impresos. Se usa ArrayDeque porque implementa
// Deque de forma eficiente sin las desventajas de Stack ni LinkedList.
// guardarImpresion es privado a proposito: imprimir y archivar son una sola
// transaccion, asi el historial nunca queda desincronizado de lo impreso.
public class GestorImpresiones {

    // pendientes se usa solo como COLA: offerLast, pollFirst, isEmpty, size.
    // Unica excepcion: addFirst dentro de recuperarUltima (ver comentario alli).
    private final Deque<String> pendientes;
    // historial se usa solo como PILA: push, pop, isEmpty, size.
    private final Deque<String> historial;

    public GestorImpresiones() {
        pendientes = new ArrayDeque<>();
        historial = new ArrayDeque<>();
    }

    // Entra: nombre del documento a imprimir.
    // Sale: true si se registro; false si el nombre es null o vacio.
    // Queda: el documento entra al final de pendientes.
    public boolean registrarDocumento(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        pendientes.offerLast(nombre);
        return true;
    }

    // Entra: nada.
    // Sale: el nombre del documento mas antiguo de pendientes; null si no hay pendientes.
    // Queda: el documento retirado se guarda en la cima de historial.
    public String imprimirSiguiente() {
        if (pendientes.isEmpty()) {
            return null;
        }
        String actual = pendientes.pollFirst();
        guardarImpresion(actual);
        return actual;
    }

    // Entra: nombre del documento ya impreso.
    // Sale: nada.
    // Queda: el nombre en la cima de historial.
    private void guardarImpresion(String nombre) {
        historial.push(nombre);
    }

    // Entra: nada.
    // Sale: el nombre de la ultima impresion; null si el historial esta vacio.
    // Queda: ese nombre vuelve al frente de pendientes.
    public String recuperarUltima() {
        if (historial.isEmpty()) {
            return null;
        }
        String ultimo = historial.pop();
        // addFirst es la unica excepcion al uso de pendientes como cola pura:
        // el documento ya espero su turno una vez, asi que vuelve al frente
        // en lugar de hacer fila de nuevo al final.
        pendientes.addFirst(ultimo);
        return ultimo;
    }

    public boolean estaVaciaPendientes() {
        return pendientes.isEmpty();
    }

    public boolean estaVacioHistorial() {
        return historial.isEmpty();
    }

    public int tamanioPendientes() {
        return pendientes.size();
    }

    public int tamanioHistorial() {
        return historial.size();
    }

    // Un solo recorrido sirve para ambas estructuras: el iterator de ArrayDeque
    // siempre va de cabeza a cola. En pendientes la cabeza es el frente (cola
    // FIFO normal). En historial la cabeza tambien es la cima, porque push()
    // equivale a addFirst(), asi que cabeza y cima son el mismo elemento.
    public void mostrarEstado() {
        System.out.println("Pendientes (frente -> final): " + representar(pendientes));
        System.out.println("Historial (cima -> fondo): " + representar(historial));
    }

    private String representar(Deque<String> estructura) {
        return construirTexto(estructura.iterator());
    }

    private String construirTexto(Iterator<String> it) {
        if (!it.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
