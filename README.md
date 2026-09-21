# T4 — Laboratorio Gestor de impresiones

**Estructura de Datos** · Ingenieria en Software · Universidad Tecnica de Ambato · Tercer semestre

Simulador de un gestor de cola de impresion construido sobre dos `Deque<String>`:
una **cola** (FIFO) con los documentos pendientes y una **pila** (LIFO) con el
historial de documentos ya impresos, que permite recuperar la ultima impresion.

---

## Estructuras utilizadas

| Variable | TDA | Disciplina | Implementacion | Por que |
|---|---|---|---|---|
| `pendientes` | Cola | FIFO | `ArrayDeque<String>` | Imprime primero el documento que lleva mas tiempo esperando |
| `historial` | Pila | LIFO | `ArrayDeque<String>` | Recuperar "la ultima" exige acceso por la cima |

Ambas se declaran como `Deque<String>` segun la consigna, pero **cada una respeta
su propio contrato**: el tipo es el mismo, la disciplina de acceso no.

## Operaciones implementadas

| Accion | Estructura | Metodo usado | Resultado |
|---|---|---|---|
| Registrar documento | `pendientes` | `offerLast(nombre)` | Entra al final |
| Imprimir siguiente | `pendientes` | `pollFirst()` | Sale el mas antiguo |
| Guardar impresion | `historial` | `push(nombre)` | Queda en la cima |
| Recuperar ultima | `historial` + `pendientes` | `pop()` y `addFirst()` | Vuelve al frente |

## Contrato de cada estructura

Metodos permitidos, para no romper la disciplina de acceso:

- **`pendientes` (cola)**: `offerLast`, `pollFirst`, `isEmpty`, `size`
- **`historial` (pila)**: `push`, `pop`, `isEmpty`, `size`

**Unica excepcion:** `pendientes.addFirst()` en `recuperarUltima()`. Esta
justificada porque el documento recuperado ya habia esperado su turno en la cola
y fue impreso; reencolarlo al final lo castigaria dos veces. Es una decision de
negocio explicita, no un descuido del FIFO.

## Decisiones de diseno

1. **`guardarImpresion` es privado.** Imprimir y archivar son una sola
   transaccion: si fueran dos llamadas publicas independientes, el historial
   podria desincronizarse de lo realmente impreso. La accion de la consigna
   esta implementada, pero encapsulada dentro de `imprimirSiguiente()`.
2. **Retorno `null` en lugar de excepcion.** `pollFirst()` y `pop()` se invocan
   solo tras comprobar `isEmpty()`; los metodos publicos devuelven `null` para
   que el llamador decida como reportarlo, sin propagar `NoSuchElementException`.
3. **`iterator()` solo para mostrar estado.** Es una operacion de lectura que no
   modifica ninguna estructura, por lo que no vulnera el contrato. Como
   `push()` equivale a `addFirst()`, el iterador de `historial` recorre
   naturalmente de cima a fondo.
4. **`ArrayDeque` no admite `null`**, por eso `registrarDocumento` rechaza
   nombres nulos o vacios antes de encolar.

## Validaciones

- Cola vacia antes de imprimir: no se retira nada, se informa al usuario.
- Historial vacio antes de recuperar: no se retira nada, se informa al usuario.
- Nombre `null` o en blanco: se rechaza el registro (`false`).

## Compilacion y ejecucion

```bash
cd Gestor
javac *.java
java DemoGestorImpresiones
```

> Las clases estan en el paquete por defecto. Si compilas desde la raiz del
> repositorio usa `javac Gestor/*.java && java -cp Gestor DemoGestorImpresiones`.

## Casos de prueba

La demo ejecuta **11 operaciones combinadas** (el laboratorio exige 6 como minimo):

| # | Operacion | Que demuestra |
|---|---|---|
| 1 | Imprimir con cola vacia | Validacion de estructura vacia |
| 2 | Recuperar con historial vacio | Validacion de estructura vacia |
| 3 | Registrar nombre en blanco | Rechazo de entrada invalida |
| 4-6 | Registrar `Informe.pdf`, `Tarea.docx`, `Foto.png` | `offerLast` — orden de llegada |
| 7-8 | Imprimir dos veces | `pollFirst` + `push` encadenados |
| 9 | Recuperar ultima | `pop` + `addFirst` — vuelve al frente |
| 10 | Imprimir siguiente | El recuperado sale primero |
| 11 | Imprimir siguiente | La cola queda vacia |

## Traza manual

Estado de ambas estructuras despues de cada operacion. El paso 9 es el unico
que toca la cola por el frente, y el paso 10 confirma que el documento
recuperado vuelve a salir primero.

| Paso | Operacion | Metodo invocado | Pendientes (frente -> final) | Historial (cima -> fondo) | Observacion |
|------|-----------|------------------|-------------------------------|----------------------------|-------------|
| 0 | Estado inicial | (ninguno) | [] | [] | Ambas estructuras vacias al crear el gestor |
| 1 | Imprimir con cola vacia | imprimirSiguiente() | [] | [] | Cola vacia: retorna null, nada se modifica |
| 2 | Recuperar con historial vacio | recuperarUltima() | [] | [] | Historial vacio: retorna null, nada se modifica |
| 3 | Registrar nombre en blanco "" | registrarDocumento("") | [] | [] | Validacion rechaza string vacio, retorna false |
| 4 | Registrar Informe.pdf | registrarDocumento("Informe.pdf") | [Informe.pdf] | [] | offerLast agrega el documento al final de la cola |
| 5 | Registrar Tarea.docx | registrarDocumento("Tarea.docx") | [Informe.pdf, Tarea.docx] | [] | offerLast agrega el documento al final de la cola |
| 6 | Registrar Foto.png | registrarDocumento("Foto.png") | [Informe.pdf, Tarea.docx, Foto.png] | [] | offerLast agrega el documento al final de la cola |
| 7 | Imprimir siguiente | imprimirSiguiente() | [Tarea.docx, Foto.png] | [Informe.pdf] | pollFirst saca el mas antiguo (FIFO); push lo guarda en la cima del historial |
| 8 | Imprimir siguiente | imprimirSiguiente() | [Foto.png] | [Tarea.docx, Informe.pdf] | pollFirst saca el mas antiguo (FIFO); push lo guarda en la cima del historial |
| 9 | Recuperar ultima impresion | recuperarUltima() | [Tarea.docx, Foto.png] | [Informe.pdf] | pop saca la cima; addFirst lo regresa al FRENTE de pendientes (no al final), porque ya esperaba su turno |
| 10 | Imprimir siguiente | imprimirSiguiente() | [Foto.png] | [Tarea.docx, Informe.pdf] | pollFirst vuelve a sacar Tarea.docx (ahora al frente); push lo guarda en la cima del historial |
| 11 | Imprimir siguiente | imprimirSiguiente() | [] | [Foto.png, Tarea.docx, Informe.pdf] | pollFirst saca el ultimo pendiente; la cola queda vacia |

## Salida de consola

La ejecucion real y completa esta en [`salida.txt`](salida.txt), generada con:

```bash
cd Gestor
javac *.java
java DemoGestorImpresiones > ../salida.txt
```

## Entregables

- Codigo fuente: [`Gestor/`](Gestor/)
- Salida de consola: [`salida.txt`](salida.txt)
- Traza manual: [`TRAZA.md`](TRAZA.md) (incluida tambien en este README)

## Estructura del repositorio

```
.
├── Gestor/
│   ├── GestorImpresiones.java     # TDA: cola de pendientes + pila de historial
│   └── DemoGestorImpresiones.java # Banco de pruebas con 11 operaciones
├── salida.txt                     # Ejecucion real capturada
├── TRAZA.md                       # Traza manual de ambas estructuras
└── README.md
```

## Autor

Damian Cunalata — Ingenieria en Software, UTA
