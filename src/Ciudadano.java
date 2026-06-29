public class Ciudadano {
    private final String cedula;
    private String nombre;
    private double deuda;

    public Ciudadano(String cedula, String nombre, double deuda) {
        if (!ValidationUtils.isValidCedula(cedula)) {
            throw new IllegalArgumentException("Cédula inválida. Debe contener exactamente 10 dígitos.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (!ValidationUtils.isValidMonto(deuda)) {
            throw new IllegalArgumentException("La deuda no puede ser negativa.");
        }

        this.cedula = cedula.trim();
        this.nombre = nombre.trim();
        this.deuda = deuda;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        if (!ValidationUtils.isValidMonto(deuda)) {
            throw new IllegalArgumentException("La deuda no puede ser negativa.");
        }
        this.deuda = deuda;
    }

    public void aplicarPago(double monto) {
        if (!ValidationUtils.isValidMonto(monto)) {
            throw new IllegalArgumentException("El monto de pago no puede ser negativo.");
        }
        this.deuda = Math.max(0, this.deuda - monto);
    }

    public void incrementarDeuda(double monto) {
        if (!ValidationUtils.isValidMonto(monto)) {
            throw new IllegalArgumentException("El monto de deuda no puede ser negativo.");
        }
        this.deuda += monto;
    }

    @Override
    public String toString() {
        return "Ciudadano{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", deuda=" + deuda +
                '}';
    }
}
