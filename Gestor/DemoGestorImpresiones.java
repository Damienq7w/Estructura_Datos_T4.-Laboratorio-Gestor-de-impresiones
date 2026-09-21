public class DemoGestorImpresiones {

    public static void main(String[] args) {
        GestorImpresiones gestor = new GestorImpresiones();

        paso(1, "Imprimir con la cola de pendientes vacia");
        String r1 = gestor.imprimirSiguiente();
        System.out.println(r1 == null ? "No hay documentos pendientes" : "Se imprimio: " + r1);
        gestor.mostrarEstado();

        paso(2, "Recuperar con el historial vacio");
        String r2 = gestor.recuperarUltima();
        System.out.println(r2 == null ? "No hay impresiones para recuperar" : "Se recupero: " + r2);
        gestor.mostrarEstado();

        paso(3, "Registrar Informe.pdf");
        System.out.println("Registrado: " + gestor.registrarDocumento("Informe.pdf"));
        gestor.mostrarEstado();

        paso(4, "Registrar Tarea.docx");
        System.out.println("Registrado: " + gestor.registrarDocumento("Tarea.docx"));
        gestor.mostrarEstado();

        paso(5, "Registrar Foto.png");
        System.out.println("Registrado: " + gestor.registrarDocumento("Foto.png"));
        gestor.mostrarEstado();

        paso(6, "Imprimir siguiente");
        System.out.println("Se imprimio: " + gestor.imprimirSiguiente());
        gestor.mostrarEstado();

        paso(7, "Imprimir siguiente");
        System.out.println("Se imprimio: " + gestor.imprimirSiguiente());
        gestor.mostrarEstado();

        paso(8, "Recuperar ultima impresion");
        System.out.println("Se recupero: " + gestor.recuperarUltima());
        gestor.mostrarEstado();

        paso(9, "Imprimir siguiente");
        System.out.println("Se imprimio: " + gestor.imprimirSiguiente());
        gestor.mostrarEstado();

        paso(10, "Imprimir siguiente");
        System.out.println("Se imprimio: " + gestor.imprimirSiguiente());
        gestor.mostrarEstado();
    }

    private static void paso(int numero, String descripcion) {
        System.out.println();
        System.out.printf("=== Paso %d: %s ===%n", numero, descripcion);
    }
}
