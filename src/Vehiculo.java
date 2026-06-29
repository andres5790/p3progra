public class Vehiculo {

    private final String placa;
    private int anio;
    private Ciudadano propietario;
    private int totalMultas;

    public Vehiculo(String placa, int anio, Ciudadano propietario) {

        if (!ValidationUtils.isValidPlaca(placa)) {
            throw new IllegalArgumentException("Placa inválida. Formato ABC-1234.");
        }

        if (!ValidationUtils.isValidAnio(anio)) {
            throw new IllegalArgumentException("Año inválido para el vehículo.");
        }

        if (propietario == null) {
            throw new IllegalArgumentException("El vehículo debe tener un propietario.");
        }

        this.placa = placa.trim().toUpperCase();
        this.anio = anio;
        this.propietario = propietario;
        this.totalMultas = 0;
    }

    // =========================
    // GETTERS
    // =========================

    public String getPlaca() {
        return placa;
    }

    public int getAnio() {
        return anio;
    }

    public Ciudadano getPropietario() {
        return propietario;
    }

    public int getTotalMultas() {
        return totalMultas;
    }

    // =========================
    // SETTERS
    // =========================

    public void setAnio(int anio) {

        if (!ValidationUtils.isValidAnio(anio)) {
            throw new IllegalArgumentException("Año inválido.");
        }

        this.anio = anio;
    }

    public void setPropietario(Ciudadano propietario) {

        if (propietario == null) {
            throw new IllegalArgumentException("Propietario inválido.");
        }

        this.propietario = propietario;
    }

    // =========================
    // CONTROL DE MULTAS (SIN HISTORIAL)
    // =========================

    public void incrementarMultas() {
        this.totalMultas++;
    }

    public void disminuirMultas() {
        if (this.totalMultas > 0) {
            this.totalMultas--;
        }
    }

    // =========================
    // TO STRING
    // =========================

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", anio=" + anio +
                ", propietario=" + propietario.getNombre() +
                ", totalMultas=" + totalMultas +
                '}';
    }
}