import java.time.LocalDate;

public class Multas {
    private final int id;
    private double precio;
    private String motivo;
    private Vehiculo vehiculo;
    private Empleado empleado;
    private final LocalDate fecha;

    public Multas(double precio, String motivo, Vehiculo vehiculo, Empleado empleado, int id) {
        this(precio, motivo, vehiculo, empleado, id, LocalDate.now());
    }

    public Multas(double precio, String motivo, Vehiculo vehiculo, Empleado empleado, int id, LocalDate fecha) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador de la multa debe ser positivo.");
        }
        if (!ValidationUtils.isValidMonto(precio)) {
            throw new IllegalArgumentException("El precio de la multa no puede ser negativo.");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de la multa no puede estar vacío.");
        }
        if (vehiculo == null) {
            throw new IllegalArgumentException("La multa debe asociarse a un vehículo.");
        }
        if (empleado == null) {
            throw new IllegalArgumentException("La multa debe registrar un empleado.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de la multa no puede ser nula.");
        }

        this.id = id;
        this.precio = precio;
        this.motivo = motivo.trim();
        this.vehiculo = vehiculo;
        this.empleado = empleado;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (!ValidationUtils.isValidMonto(precio)) {
            throw new IllegalArgumentException("El precio de la multa no puede ser negativo.");
        }
        this.precio = precio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de la multa no puede estar vacío.");
        }
        this.motivo = motivo.trim();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("La multa debe asociarse a un vehículo.");
        }
        this.vehiculo = vehiculo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("La multa debe registrar un empleado.");
        }
        this.empleado = empleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Multas{" +
                "id=" + id +
                ", precio=" + precio +
                ", motivo='" + motivo + '\'' +
                ", vehiculo=" + vehiculo.getPlaca() +
                ", empleado=" + empleado.getNombre() +
                ", fecha=" + fecha +
                '}';
    }
}
