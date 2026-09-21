public class DemoGestorImpresiones {

    public static void main(String[] args) {
        GestorImpresiones gestor = new GestorImpresiones();

        paso(1, "Imprimir con la cola de pendientes vacia");
        reportarImpresion(gestor.imprimirSiguiente());
        gestor.mostrarEstado();

        paso(2, "Recuperar con el historial vacio");
        reportarRecuperacion(gestor.recuperarUltima());
        gestor.mostrarEstado();

        paso(3, "Registrar nombre en blanco");
        System.out.println("Registrado: " + gestor.registrarDocumento(""));
        gestor.mostrarEstado();

        paso(4, "Registrar Informe.pdf");
        System.out.println("Registrado: " + gestor.registrarDocumento("Informe.pdf"));
        gestor.mostrarEstado();

        paso(5, "Registrar Tarea.docx");
        System.out.println("Registrado: " + gestor.registrarDocumento("Tarea.docx"));
        gestor.mostrarEstado();

        paso(6, "Registrar Foto.png");
        System.out.println("Registrado: " + gestor.registrarDocumento("Foto.png"));
        gestor.mostrarEstado();

        paso(7, "Imprimir siguiente");
        reportarImpresion(gestor.imprimirSiguiente());
        gestor.mostrarEstado();

        paso(8, "Imprimir siguiente");
        reportarImpresion(gestor.imprimirSiguiente());
        gestor.mostrarEstado();

        paso(9, "Recuperar ultima impresion");
        reportarRecuperacion(gestor.recuperarUltima());
        gestor.mostrarEstado();

        paso(10, "Imprimir siguiente");
        reportarImpresion(gestor.imprimirSiguiente());
        gestor.mostrarEstado();

        paso(11, "Imprimir siguiente");
        reportarImpresion(gestor.imprimirSiguiente());
        gestor.mostrarEstado();
    }

    private static void paso(int numero, String descripcion) {
        System.out.println();
        System.out.printf("=== Paso %d: %s ===%n", numero, descripcion);
    }

    private static void reportarImpresion(String r) {
        System.out.println(r == null ? "No hay documentos pendientes" : "Se imprimio: " + r);
    }

    private static void reportarRecuperacion(String r) {
        System.out.println(r == null ? "No hay impresiones para recuperar" : "Se recupero: " + r);
    }
}
