import java.time.LocalDate;

public class Pago {
    private final int idp;
    private final double cantidad;
    private final Ciudadano ciudadano;
    private final LocalDate fecha;

    public Pago(int idp, double cantidad, Ciudadano ciudadano, LocalDate fecha) {
        if (idp <= 0) {
            throw new IllegalArgumentException("El identificador del pago debe ser positivo.");
        }
        if (!ValidationUtils.isValidMonto(cantidad)) {
            throw new IllegalArgumentException("El monto del pago no puede ser negativo.");
        }
        if (ciudadano == null) {
            throw new IllegalArgumentException("El pago debe asignarse a un ciudadano.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del pago no puede ser nula.");
        }

        this.idp = idp;
        this.cantidad = cantidad;
        this.ciudadano = ciudadano;
        this.fecha = fecha;
    }

    public Pago(int idp, double cantidad, Ciudadano ciudadano) {
        this(idp, cantidad, ciudadano, LocalDate.now());
    }

    public int getIdp() {
        return idp;
    }

    public double getCantidad() {
        return cantidad;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idp=" + idp +
                ", cantidad=" + cantidad +
                ", ciudadano=" + ciudadano.getNombre() +
                ", fecha=" + fecha +
                '}';
    }
}
