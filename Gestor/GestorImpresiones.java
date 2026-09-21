import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class GestorImpresiones {

    // pendientes se usa solo como COLA: offerLast, pollFirst, peekFirst y addFirst al recuperar.
    private Deque<String> pendientes;
    // historial se usa solo como PILA: push, pop y peek.
    private Deque<String> historial;

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

    // Muestra pendientes de frente a final e historial de cima a fondo.
    public void mostrarEstado() {
        System.out.println("Pendientes (frente -> final): " + textoDesdeFrente(pendientes));
        System.out.println("Historial (cima -> fondo): " + textoDesdeCima(historial));
    }

    // pendientes es cola: iterator() de ArrayDeque recorre de cabeza (frente) a cola (final).
    private String textoDesdeFrente(Deque<String> estructura) {
        return construirTexto(estructura.iterator());
    }

    // historial es pila armada con push (equivale a addFirst), por lo que iterator()
    // recorre de cabeza (cima) a cola (fondo).
    private String textoDesdeCima(Deque<String> estructura) {
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
