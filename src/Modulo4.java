import java.util.ArrayList;
import java.util.List;

/**
 * Módulo 4: Gestión de flota y placas usando un árbol binario de búsqueda.
 * Cada vehículo se inserta por placa para búsqueda rápida en O(log n) promedio.
 *
 *   * - Create: insertarMulta -> O(1) amortizado.
 *   * - Read: generarReporteHistorial -> O(n).
 *   * - Update: actualizarMulta -> O(n).
 *   * - Delete: eliminarMulta -> O(n).
 */
public class Modulo4 {

    private NodoVehiculo raiz;

    public Modulo4() {
        this.raiz = null;
    }

    // Inserta un vehículo en el BST según la placa.
    public boolean insertarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }
        if (raiz == null) {
            raiz = new NodoVehiculo(vehiculo);
            return true; // O(1)
        }
        return insertar(raiz, vehiculo);
    }

    private boolean insertar(NodoVehiculo actual, Vehiculo vehiculo) {
        int comparacion = vehiculo.getPlaca().compareTo(actual.getVehiculo().getPlaca());
        if (comparacion == 0) {
            return false; // placa duplicada
        }
        if (comparacion < 0) {
            if (actual.getIzq() == null) {
                actual.setIzq(new NodoVehiculo(vehiculo));
                return true;
            }
            return insertar(actual.getIzq(), vehiculo);
        }
        if (actual.getDer() == null) {
            actual.setDer(new NodoVehiculo(vehiculo));
            return true;
        }
        return insertar(actual.getDer(), vehiculo);
    }

    // Busca un vehículo por placa usando recorrido binario.
    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return null;
        }
        return buscarVehiculo(raiz, placa.trim().toUpperCase()); // O(log n) promedio
    }

    private Vehiculo buscarVehiculo(NodoVehiculo actual, String placa) {
        if (actual == null) {
            return null;
        }
        int comparacion = placa.compareTo(actual.getVehiculo().getPlaca());
        if (comparacion == 0) {
            return actual.getVehiculo();
        }
        if (comparacion < 0) {
            return buscarVehiculo(actual.getIzq(), placa);
        }
        return buscarVehiculo(actual.getDer(), placa);
    }

    public boolean actualizarVehiculo(String placa, Ciudadano propietario, int nuevoAnio) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            return false;
        }
        boolean cambiado = false;
        if (propietario != null) {
            vehiculo.setPropietario(propietario);
            cambiado = true;
        }
        if (ValidationUtils.isValidAnio(nuevoAnio)) {
            vehiculo.setAnio(nuevoAnio);
            cambiado = true;
        }
        return cambiado; // true solo si hubo cambios válidos
    }

    // Elimina un vehículo del BST cuando es chatarrizado.
    public boolean eliminarVehiculo(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return false;
        }
        placa = placa.trim().toUpperCase();
        if (buscarVehiculo(placa) == null) {
            return false;
        }
        raiz = eliminar(raiz, placa);
        return true; // O(log n) promedio
    }

    private NodoVehiculo eliminar(NodoVehiculo actual, String placa) {
        if (actual == null) {
            return null;
        }
        int comparacion = placa.compareTo(actual.getVehiculo().getPlaca());
        if (comparacion < 0) {
            actual.setIzq(eliminar(actual.getIzq(), placa));
            return actual;
        }
        if (comparacion > 0) {
            actual.setDer(eliminar(actual.getDer(), placa));
            return actual;
        }

        if (actual.getIzq() == null) {
            return actual.getDer();
        }
        if (actual.getDer() == null) {
            return actual.getIzq();
        }

        NodoVehiculo sucesor = encontrarMinimo(actual.getDer());
        actual.setVehiculo(sucesor.getVehiculo());
        actual.setDer(eliminar(actual.getDer(), sucesor.getVehiculo().getPlaca()));
        return actual;
    }

    private NodoVehiculo encontrarMinimo(NodoVehiculo actual) {
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    public String listarVehiculosInOrden() {
        if (raiz == null) {
            return "No hay vehículos registrados.";
        }
        return listarVehiculosInOrden(raiz).trim();
    }

    private String listarVehiculosInOrden(NodoVehiculo actual) {
        if (actual == null) {
            return "";
        }
        return listarVehiculosInOrden(actual.getIzq())
                + actual.getVehiculo().toString() + "\n"
                + listarVehiculosInOrden(actual.getDer());
    }

    public List<Vehiculo> obtenerVehiculos() {
        List<Vehiculo> resultado = new ArrayList<>();
        recorrerInOrden(raiz, resultado);
        return resultado; // O(n)
    }

    private void recorrerInOrden(NodoVehiculo actual, List<Vehiculo> lista) {
        if (actual == null) {
            return;
        }
        recorrerInOrden(actual.getIzq(), lista);
        lista.add(actual.getVehiculo());
        recorrerInOrden(actual.getDer(), lista);
    }
}
