import java.util.ArrayList;

public class Modulo5 {

    private ArrayList<NodoAccidente> nodos;

    public Modulo5() {
        nodos = new ArrayList<>();
    }

    // Creacion de vertices o aristas
    public void agregarAccidente(Accidente a) {
        nodos.add(new NodoAccidente(a));
    }

    public void conectar(int id1, int id2, int peso) {
        if (id1 == id2) {
            return;
        }


        if (!existe(id1) || !existe(id2)) {
            return;
        }

        NodoAccidente n1 = buscar(id1);
        NodoAccidente n2 = buscar(id2);


        for (Conexionaccidente c : n1.getConexion()) {
            if (c.getDestino().getId() == id2) {
                return;
            }
        }


        n1.agregarconexiones(new Conexionaccidente(n2.getAccidente(), peso));
        n2.agregarconexiones(new Conexionaccidente(n1.getAccidente(), peso));
    }

    // consulta de rutas
    public NodoAccidente buscar(int id) {
        for (NodoAccidente n : nodos) {
            if (n.getAccidente().getId() == id) {
                return n;
            }
        }
        return null;
    }




    public String mostrar() {
        StringBuilder sb = new StringBuilder();

        for (NodoAccidente n : nodos) {
            sb.append(n.getAccidente().toString()).append("\n");

            for (Conexionaccidente c : n.getConexion()) {
                sb.append("   -> ").append(c.getDestino().getId())
                        .append(" peso: ").append(c.getLongitud()).append("\n");
            }
        }

        return sb.toString();
    }

    //Uso de Dijkstra para determinar rutaa mas rapida

    public String dijkstra(int inicio, int fin) {

        NodoAccidente start = buscar(inicio);
        NodoAccidente end = buscar(fin);

        if (start == null || end == null) {
            return "No existe ruta";
        }

        ArrayList<NodoAccidente> nodosVisitados = new ArrayList<>();
        ArrayList<Integer> dist = new ArrayList<>();
        ArrayList<NodoAccidente> prev = new ArrayList<>();

        for (NodoAccidente n : nodos) {
            dist.add(Integer.MAX_VALUE);
            prev.add(null);
        }

        dist.set(nodos.indexOf(start), 0);

        for (int i = 0; i < nodos.size(); i++) {

            // buscar nodo con menor distancia
            int minIndex = -1;
            int minDist = Integer.MAX_VALUE;

            for (int j = 0; j < nodos.size(); j++) {
                if (!nodosVisitados.contains(nodos.get(j)) &&
                        dist.get(j) < minDist) {

                    minDist = dist.get(j);
                    minIndex = j;
                }
            }

            if (minIndex == -1) break;

            NodoAccidente actual = nodos.get(minIndex);
            nodosVisitados.add(actual);

            // si llegó al destino
            if (actual.getAccidente().getId() == fin) {
                break;
            }

            // relajar vecinos
            for (Conexionaccidente c : actual.getConexion()) {

                NodoAccidente vecino = buscar(c.getDestino().getId());

                int alt = dist.get(minIndex) + c.getLongitud();

                int idxVecino = nodos.indexOf(vecino);

                if (alt < dist.get(idxVecino)) {
                    dist.set(idxVecino, alt);
                    prev.set(idxVecino, actual);
                }
            }
        }

        return reconstruirRuta(start, end, prev);
    }

    private String reconstruirRuta(NodoAccidente start,
                                   NodoAccidente end,
                                   ArrayList<NodoAccidente> prev) {

        ArrayList<Integer> ruta = new ArrayList<>();

        NodoAccidente actual = end;

        while (actual != null) {
            ruta.add(actual.getAccidente().getId());
            int idx = nodos.indexOf(actual);
            actual = prev.get(idx);
        }

        String resultado = "Ruta más rápida: ";

        for (int i = ruta.size() - 1; i >= 0; i--) {
            resultado += ruta.get(i);

            if (i != 0) {
                resultado += " -> ";
            }
        }

        return resultado;
    }

    //Cambiar peso de arista
    public boolean cambiararista(int id,int id2,int nlongitud){

        NodoAccidente n1 = buscar(id);
        NodoAccidente n2 = buscar(id2);

        if (n1 == null || n2 == null) {
            return false;
        }

        boolean actualizado = false;

        for (Conexionaccidente c : n1.getConexion()) {
            if (c.getDestino().getId() == id2) {
                c.setLongitud(nlongitud);
                actualizado = true;
            }
        }

        for (Conexionaccidente c : n2.getConexion()) {
            if (c.getDestino().getId() == id) {
                c.setLongitud(nlongitud);
                actualizado = true;
            }
        }

        return actualizado;
    }


    public boolean eliminarConexion(int id1, int id2) {

        NodoAccidente n1 = buscar(id1);
        NodoAccidente n2 = buscar(id2);

        if (n1 == null || n2 == null) {
            return false;
        }

        n1.getConexion().removeIf(c ->
                c.getDestino().getId() == id2);

        n2.getConexion().removeIf(c ->
                c.getDestino().getId() == id1);

        return true;
    }

    public boolean existe(int id) {
        return buscar(id) != null;
    }
}