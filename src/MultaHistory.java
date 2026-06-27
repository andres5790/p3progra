import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Lista enlazada simple para almacenar el historial de multas de un vehículo.
public class MultaHistory {
    private Nodo cabeza;
    private Nodo cola;
    private int size;

    public MultaHistory() {
        cabeza = null;
        cola = null;
        size = 0;
    }

    // Inserta la multa al final de la lista.
    public void add(Multas multa) {
        if (multa == null) {
            throw new IllegalArgumentException("La multa no puede ser nula");
        }

        Nodo nuevo = new Nodo(multa);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.next = nuevo;
            cola = nuevo;
        }
        size++;
    }

    // Elimina la multa con el ID indicado.
    public boolean removeById(int id) {
        Nodo actual = cabeza;
        Nodo previo = null;

        while (actual != null) {
            if (actual.multas.getId() == id) {
                if (previo == null) {
                    cabeza = actual.next;
                } else {
                    previo.next = actual.next;
                }
                if (actual == cola) {
                    cola = previo;
                }
                size--;
                return true;
            }
            previo = actual;
            actual = actual.next;
        }
        return false;
    }

    // Actualiza los datos de una multa dentro de la lista.
    public boolean updateById(int id, double nuevoPrecio, String nuevoMotivo) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.multas.getId() == id) {
                actual.multas.setPrecio(nuevoPrecio);
                actual.multas.setMotivo(nuevoMotivo);
                return true;
            }
            actual = actual.next;
        }
        return false;
    }

    // Busca una multa por ID.
    public Multas findById(int id) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.multas.getId() == id) {
                return actual.multas;
            }
            actual = actual.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public List<Multas> toList() {
        if (cabeza == null) {
            return Collections.emptyList();
        }
        List<Multas> lista = new ArrayList<>(size);
        Nodo actual = cabeza;
        while (actual != null) {
            lista.add(actual.multas);
            actual = actual.next;
        }
        return lista;
    }

    private static class Nodo {
        private final Multas multas;
        private Nodo next;

        private Nodo(Multas multas) {
            this.multas = multas;
        }
    }
}
