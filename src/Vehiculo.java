import java.util.Collections;
import java.util.List;

public class Vehiculo {
    private final String placa;
    private int anio;
    private Ciudadano propietario;
    private final MultaHistory historialMultas;

    public Vehiculo(String placa, int anio, Ciudadano propietario) {
        if (!ValidationUtils.isValidPlaca(placa)) {
            throw new IllegalArgumentException("Placa inválida. El formato válido es ABC-1234.");
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
        this.historialMultas = new MultaHistory();
    }

    public String getPlaca() {
        return placa;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        if (!ValidationUtils.isValidAnio(anio)) {
            throw new IllegalArgumentException("Año inválido para el vehículo.");
        }
        this.anio = anio;
    }

    public Ciudadano getPropietario() {
        return propietario;
    }

    public void setPropietario(Ciudadano propietario) {
        if (propietario == null) {
            throw new IllegalArgumentException("El vehículo debe tener un propietario.");
        }
        this.propietario = propietario;
    }

    public int getTotalmultas() {
        return historialMultas.size();
    }

    public void agregarMulta(Multas multa) {
        if (multa == null) {
            throw new IllegalArgumentException("La multa no puede ser nula.");
        }
        historialMultas.add(multa);
    }

    public boolean eliminarMulta(int multaId) {
        return historialMultas.removeById(multaId);
    }

    public boolean actualizarMulta(int multaId, double nuevoPrecio, String nuevoMotivo) {
        return historialMultas.updateById(multaId, nuevoPrecio, nuevoMotivo);
    }

    public Multas buscarMulta(int multaId) {
        return historialMultas.findById(multaId);
    }

    public List<Multas> getHistorialMultas() {
        return Collections.unmodifiableList(historialMultas.toList());
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", anio=" + anio +
                ", propietario=" + propietario.getNombre() +
                ", totalMultas=" + getTotalmultas() +
                '}';
    }
}
