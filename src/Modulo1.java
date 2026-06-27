import java.util.ArrayList;
import java.util.List;

public class Modulo1 {

    private NodoPago cima;
    private int cantidad;

    public Modulo1() {
        this.cima = null;
        this.cantidad = 0;
    }

    public boolean registrarPago(Pago pago) {
        if (pago == null) {
            throw new IllegalArgumentException("El pago no puede ser nulo.");
        }
        cima = new NodoPago(pago, cima);
        cantidad++;
        return true;
    }

    public Pago verUltimoPago() {
        return cima == null ? null : cima.pago;
    }

    public boolean ultimoPagoAplicado() {
        return cima != null && cima.aplicado;
    }

    public boolean aplicarPagoActual() {
        if (cima == null || cima.aplicado) {
            return false;
        }
        cima.pago.getCiudadano().aplicarPago(cima.pago.getCantidad());
        cima.aplicado = true;
        return true;
    }

    public Pago rollbackUltimoPago() {
        if (cima == null) {
            return null;
        }

        Pago pago = cima.pago;
        if (cima.aplicado) {
            pago.getCiudadano().incrementarDeuda(pago.getCantidad());
        }
        cima = cima.siguiente;
        cantidad--;
        return pago;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int cantidadPagos() {
        return cantidad;
    }

    public List<RegistroPago> obtenerRegistros() {
        List<RegistroPago> registros = new ArrayList<>();
        NodoPago actual = cima;
        while (actual != null) {
            registros.add(new RegistroPago(actual.pago, actual.aplicado));
            actual = actual.siguiente;
        }
        return registros;
    }

    public static class RegistroPago {
        private final Pago pago;
        private final boolean aplicado;

        public RegistroPago(Pago pago, boolean aplicado) {
            this.pago = pago;
            this.aplicado = aplicado;
        }

        public Pago getPago() {
            return pago;
        }

        public boolean isAplicado() {
            return aplicado;
        }
    }

    private static class NodoPago {
        private final Pago pago;
        private final NodoPago siguiente;
        private boolean aplicado;

        private NodoPago(Pago pago, NodoPago siguiente) {
            this.pago = pago;
            this.siguiente = siguiente;
            this.aplicado = false;
        }
    }
}
