import java.util.ArrayList;
import java.util.List;

public class Modulo2 {

    private NodoTramite frente;
    private NodoTramite fin;
    private int tamano;

    public Modulo2() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }

    public boolean encolarTramite(Tramite tramite) {
        if (tramite == null) {
            throw new IllegalArgumentException("El tramite no puede ser nulo.");
        }
        if (existeTramiteAbierto(tramite.getCiudadano().getCedula(), tramite.getTipo())) {
            return false;
        }

        NodoTramite nuevo = new NodoTramite(tramite);
        if (frente == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamano++;
        return true;
    }

    public Tramite verFrente() {
        return frente == null ? null : frente.tramite;
    }

    public boolean iniciarTramiteActual() {
        Tramite tramite = verFrente();
        if (tramite == null) {
            return false;
        }
        tramite.setEstado(Estado.ENPROCESO);
        return true;
    }

    public Tramite finalizarTramiteActual() {
        if (frente == null) {
            return null;
        }

        Tramite tramite = frente.tramite;
        tramite.setEstado(Estado.FINALIZADO);
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamano--;
        return tramite;
    }

    public boolean cancelarTramite(String cedula, Tipotramite tipo) {
        if (cedula == null || tipo == null) {
            return false;
        }

        NodoTramite actual = frente;
        NodoTramite anterior = null;
        while (actual != null) {
            Tramite tramite = actual.tramite;
            boolean coincide = tramite.getCiudadano().getCedula().equals(cedula.trim())
                    && tramite.getTipo() == tipo
                    && tramite.getEstado() != Estado.FINALIZADO;

            if (coincide) {
                tramite.setEstado(Estado.CANCELADO);
                if (anterior == null) {
                    frente = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                if (actual == fin) {
                    fin = anterior;
                }
                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamanoCola() {
        return tamano;
    }

    public int tamañoCola() {
        return tamanoCola();
    }

    public List<Tramite> obtenerTramites() {
        List<Tramite> resultado = new ArrayList<>();
        NodoTramite actual = frente;
        while (actual != null) {
            resultado.add(actual.tramite);
            actual = actual.siguiente;
        }
        return resultado;
    }

    private boolean existeTramiteAbierto(String cedula, Tipotramite tipo) {
        NodoTramite actual = frente;
        while (actual != null) {
            Tramite tramite = actual.tramite;
            if (tramite.getCiudadano().getCedula().equals(cedula)
                    && tramite.getTipo() == tipo
                    && tramite.getEstado() != Estado.FINALIZADO
                    && tramite.getEstado() != Estado.CANCELADO) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    private static class NodoTramite {
        private final Tramite tramite;
        private NodoTramite siguiente;

        private NodoTramite(Tramite tramite) {
            this.tramite = tramite;
            this.siguiente = null;
        }
    }
}
