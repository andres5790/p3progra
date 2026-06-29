import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GestionRedVial {

    private final List<NodoAccidente> nodos;

    public GestionRedVial() {
        this.nodos = new ArrayList<>();
    }

    public boolean agregarAccidente(Accidente accidente) {
        if (accidente == null) {
            throw new IllegalArgumentException("La zona no puede ser nula.");
        }
        if (existe(accidente.getId())) {
            return false;
        }
        nodos.add(new NodoAccidente(accidente));
        return true;
    }

    public boolean actualizarAccidente(int id, String direccion, String tipo, String causa) {
        NodoAccidente nodo = buscar(id);
        if (nodo == null) {
            return false;
        }
        nodo.getAccidente().setDireccion(direccion);
        nodo.getAccidente().setTipo(tipo);
        nodo.getAccidente().setCausa(causa);
        return true;
    }

    public boolean eliminarAccidente(int id) {
        NodoAccidente nodo = buscar(id);
        if (nodo == null) {
            return false;
        }

        for (NodoAccidente actual : nodos) {
            actual.getConexion().removeIf(conexion -> conexion.getDestino().equals(nodo));
        }
        nodos.remove(nodo);
        return true;
    }

    public boolean conectar(int id1, int id2, int distancia) {
        if (id1 == id2 || distancia <= 0) {
            return false;
        }
        NodoAccidente n1 = buscar(id1);
        NodoAccidente n2 = buscar(id2);
        if (n1 == null || n2 == null || existeConexion(n1, n2)) {
            return false;
        }
        n1.agregarConexion(new Conexionaccidente(n2, distancia));
        n2.agregarConexion(new Conexionaccidente(n1, distancia));
        return true;
    }

    public boolean existe(int id) {
        return buscar(id) != null;
    }

    public NodoAccidente buscar(int id) {
        for (NodoAccidente nodo : nodos) {
            if (nodo.getAccidente().getId() == id) {
                return nodo;
            }
        }
        return null;
    }

    private boolean existeConexion(NodoAccidente origen, NodoAccidente destino) {
        for (Conexionaccidente conexion : origen.getConexion()) {
            if (conexion.getDestino().equals(destino)) {
                return true;
            }
        }
        return false;
    }

    public String mostrarMapa() {
        if (nodos.isEmpty()) {
            return "El mapa no contiene zonas.";
        }
        StringBuilder sb = new StringBuilder();
        for (NodoAccidente nodo : nodos) {
            sb.append(nodo.getAccidente()).append("\n");
            for (Conexionaccidente conexion : nodo.getConexion()) {
                sb.append("  -> ")
                        .append(conexion.getDestino().getAccidente().getId())
                        .append(" (distancia = ")
                        .append(conexion.getLongitud())
                        .append(" km)\n");
            }
        }
        return sb.toString();
    }

    public String encontrarRutaMasCorta(int inicio, int fin) {
        RutaResultado resultado = calcularRutaMasCorta(inicio, fin);
        if (!resultado.existeRuta()) {
            return resultado.getMensaje();
        }
        StringBuilder ruta = new StringBuilder("Ruta mas cercana: ");
        List<Integer> recorrido = resultado.getRecorrido();
        for (int i = 0; i < recorrido.size(); i++) {
            ruta.append(recorrido.get(i));
            if (i < recorrido.size() - 1) {
                ruta.append(" -> ");
            }
        }
        ruta.append(" | Distancia total: ").append(resultado.getDistanciaTotal()).append(" km");
        return ruta.toString();
    }

    public RutaResultado calcularRutaMasCorta(int inicio, int fin) {
        NodoAccidente origen = buscar(inicio);
        NodoAccidente destino = buscar(fin);
        if (origen == null || destino == null) {
            return RutaResultado.sinRuta("Ruta invalida: zona no encontrada.");
        }

        DijkstraResultado resultado = ejecutarDijkstra(origen);
        Integer distanciaDestino = resultado.distancias.get(destino);
        if (distanciaDestino == null || distanciaDestino == Integer.MAX_VALUE) {
            return RutaResultado.sinRuta("No existe ruta entre las zonas indicadas.");
        }

        List<Integer> recorrido = new ArrayList<>();
        NodoAccidente actual = destino;
        while (actual != null) {
            recorrido.add(actual.getAccidente().getId());
            actual = resultado.anterior.get(actual);
        }
        Collections.reverse(recorrido);
        return RutaResultado.conRuta(recorrido, distanciaDestino);
    }

    private DijkstraResultado ejecutarDijkstra(NodoAccidente origen) {
        Map<NodoAccidente, Integer> distancia = new HashMap<>();
        Map<NodoAccidente, NodoAccidente> anterior = new HashMap<>();
        PriorityQueue<NodoAccidente> cola = new PriorityQueue<>((a, b) -> distancia.get(a) - distancia.get(b));

        for (NodoAccidente nodo : nodos) {
            distancia.put(nodo, Integer.MAX_VALUE);
            anterior.put(nodo, null);
        }
        distancia.put(origen, 0);
        cola.add(origen);

        while (!cola.isEmpty()) {
            NodoAccidente actual = cola.poll();
            int distanciaActual = distancia.get(actual);

            for (Conexionaccidente conexion : actual.getConexion()) {
                NodoAccidente vecino = conexion.getDestino();
                int nuevoCosto = distanciaActual + conexion.getLongitud();
                if (nuevoCosto < distancia.get(vecino)) {
                    distancia.put(vecino, nuevoCosto);
                    anterior.put(vecino, actual);
                    cola.remove(vecino);
                    cola.add(vecino);
                }
            }
        }

        return new DijkstraResultado(distancia, anterior);
    }

    public boolean cambiarPesoArista(int inicio, int fin, int nuevaDistancia) {
        if (nuevaDistancia <= 0) {
            return false;
        }
        NodoAccidente n1 = buscar(inicio);
        NodoAccidente n2 = buscar(fin);
        if (n1 == null || n2 == null) {
            return false;
        }
        boolean actualizado = false;
        for (Conexionaccidente conexion : n1.getConexion()) {
            if (conexion.getDestino().equals(n2)) {
                conexion.setLongitud(nuevaDistancia);
                actualizado = true;
            }
        }
        for (Conexionaccidente conexion : n2.getConexion()) {
            if (conexion.getDestino().equals(n1)) {
                conexion.setLongitud(nuevaDistancia);
                actualizado = true;
            }
        }
        return actualizado;
    }

    public boolean eliminarConexion(int inicio, int fin) {
        NodoAccidente n1 = buscar(inicio);
        NodoAccidente n2 = buscar(fin);
        if (n1 == null || n2 == null) {
            return false;
        }
        boolean eliminado1 = n1.getConexion().removeIf(conexion -> conexion.getDestino().equals(n2));
        boolean eliminado2 = n2.getConexion().removeIf(conexion -> conexion.getDestino().equals(n1));
        return eliminado1 || eliminado2;
    }

    public List<Accidente> obtenerAccidentes() {
        List<Accidente> resultado = new ArrayList<>();
        for (NodoAccidente nodo : nodos) {
            resultado.add(nodo.getAccidente());
        }
        return resultado;
    }

    public List<ConexionVista> obtenerConexiones() {
        List<ConexionVista> resultado = new ArrayList<>();
        for (NodoAccidente nodo : nodos) {
            int origen = nodo.getAccidente().getId();
            for (Conexionaccidente conexion : nodo.getConexion()) {
                int destino = conexion.getDestino().getAccidente().getId();
                if (origen < destino) {
                    resultado.add(new ConexionVista(origen, destino, conexion.getLongitud()));
                }
            }
        }
        return resultado;
    }

    public static class ConexionVista {
        private final int origen;
        private final int destino;
        private final int distancia;

        public ConexionVista(int origen, int destino, int distancia) {
            this.origen = origen;
            this.destino = destino;
            this.distancia = distancia;
        }

        public int getOrigen() {
            return origen;
        }

        public int getDestino() {
            return destino;
        }

        public int getDistancia() {
            return distancia;
        }
    }

    public static class RutaResultado {
        private final boolean existeRuta;
        private final String mensaje;
        private final List<Integer> recorrido;
        private final int distanciaTotal;

        private RutaResultado(boolean existeRuta, String mensaje, List<Integer> recorrido, int distanciaTotal) {
            this.existeRuta = existeRuta;
            this.mensaje = mensaje;
            this.recorrido = recorrido;
            this.distanciaTotal = distanciaTotal;
        }

        private static RutaResultado conRuta(List<Integer> recorrido, int distanciaTotal) {
            return new RutaResultado(true, "Ruta calculada correctamente.", recorrido, distanciaTotal);
        }

        private static RutaResultado sinRuta(String mensaje) {
            return new RutaResultado(false, mensaje, Collections.emptyList(), 0);
        }

        public boolean existeRuta() {
            return existeRuta;
        }

        public String getMensaje() {
            return mensaje;
        }

        public List<Integer> getRecorrido() {
            return recorrido;
        }

        public int getDistanciaTotal() {
            return distanciaTotal;
        }
    }

    private static class DijkstraResultado {
        private final Map<NodoAccidente, Integer> distancias;
        private final Map<NodoAccidente, NodoAccidente> anterior;

        private DijkstraResultado(Map<NodoAccidente, Integer> distancias,
                                  Map<NodoAccidente, NodoAccidente> anterior) {
            this.distancias = distancias;
            this.anterior = anterior;
        }
    }
}
