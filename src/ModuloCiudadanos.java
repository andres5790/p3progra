import java.util.ArrayList;
import java.util.List;

/**
 * Módulo 4 extendido: BST de propietarios por cédula.
 * Permite CRUD en tiempo O(log n) promedio.
 */
public class ModuloCiudadanos {

    private NodoCiudadano raiz;

    public ModuloCiudadanos() {
        this.raiz = null;
    }

    // Inserta un ciudadano en el BST por cédula.
    public boolean registrarCiudadano(Ciudadano ciudadano) {
        if (ciudadano == null) {
            throw new IllegalArgumentException("El ciudadano no puede ser nulo.");
        }
        if (raiz == null) {
            raiz = new NodoCiudadano(ciudadano);
            return true;
        }
        return insertar(raiz, ciudadano);
    }

    private boolean insertar(NodoCiudadano actual, Ciudadano ciudadano) {
        int comparacion = ciudadano.getCedula().compareTo(actual.getCiudadano().getCedula());
        if (comparacion == 0) {
            return false; // cedula duplicada
        }
        if (comparacion < 0) {
            if (actual.getIzq() == null) {
                actual.setIzq(new NodoCiudadano(ciudadano));
                return true;
            }
            return insertar(actual.getIzq(), ciudadano);
        }
        if (actual.getDer() == null) {
            actual.setDer(new NodoCiudadano(ciudadano));
            return true;
        }
        return insertar(actual.getDer(), ciudadano);
    }

    // Busca un ciudadano por cédula en el árbol.
    public Ciudadano buscarCiudadano(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return null;
        }
        return buscarCiudadano(raiz, cedula.trim());
    }

    private Ciudadano buscarCiudadano(NodoCiudadano actual, String cedula) {
        if (actual == null) {
            return null;
        }
        int comparacion = cedula.compareTo(actual.getCiudadano().getCedula());
        if (comparacion == 0) {
            return actual.getCiudadano();
        }
        if (comparacion < 0) {
            return buscarCiudadano(actual.getIzq(), cedula);
        }
        return buscarCiudadano(actual.getDer(), cedula);
    }

    // Actualiza los datos de un ciudadano existente.
    public boolean actualizarCiudadano(String cedula, String nuevoNombre, Double nuevaDeuda) {
        Ciudadano ciudadano = buscarCiudadano(cedula);
        if (ciudadano == null) {
            return false;
        }
        boolean cambiado = false;
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            ciudadano.setNombre(nuevoNombre);
            cambiado = true;
        }
        if (nuevaDeuda != null && ValidationUtils.isValidMonto(nuevaDeuda)) {
            ciudadano.setDeuda(nuevaDeuda);
            cambiado = true;
        }
        return cambiado; // true solo si hubo cambios válidos
    }

    public boolean eliminarCiudadano(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }
        String cedulaNormalizada = cedula.trim();
        if (buscarCiudadano(cedulaNormalizada) == null) {
            return false;
        }
        raiz = eliminar(raiz, cedulaNormalizada);
        return true;
    }

    private NodoCiudadano eliminar(NodoCiudadano actual, String cedula) {
        if (actual == null) {
            return null;
        }
        int comparacion = cedula.compareTo(actual.getCiudadano().getCedula());
        if (comparacion < 0) {
            actual.setIzq(eliminar(actual.getIzq(), cedula));
            return actual;
        }
        if (comparacion > 0) {
            actual.setDer(eliminar(actual.getDer(), cedula));
            return actual;
        }
        if (actual.getIzq() == null) {
            return actual.getDer();
        }
        if (actual.getDer() == null) {
            return actual.getIzq();
        }
        NodoCiudadano sucesor = encontrarMinimo(actual.getDer());
        actual.setCiudadano(sucesor.getCiudadano());
        actual.setDer(eliminar(actual.getDer(), sucesor.getCiudadano().getCedula()));
        return actual;
    }

    private NodoCiudadano encontrarMinimo(NodoCiudadano actual) {
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    public String listarCiudadanosInOrden() {
        if (raiz == null) {
            return "No hay ciudadanos registrados.";
        }
        return listarInOrden(raiz).trim();
    }

    private String listarInOrden(NodoCiudadano actual) {
        if (actual == null) {
            return "";
        }
        return listarInOrden(actual.getIzq())
                + actual.getCiudadano().toString() + "\n"
                + listarInOrden(actual.getDer());
    }

    public List<Ciudadano> obtenerCiudadanos() {
        List<Ciudadano> resultado = new ArrayList<>();
        recorrerInOrden(raiz, resultado);
        return resultado;
    }

    private void recorrerInOrden(NodoCiudadano actual, List<Ciudadano> lista) {
        if (actual == null) {
            return;
        }
        recorrerInOrden(actual.getIzq(), lista);
        lista.add(actual.getCiudadano());
        recorrerInOrden(actual.getDer(), lista);
    }
}
