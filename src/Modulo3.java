import java.util.List;

public class Modulo3 {

    /**
     * Módulo 3: Historial de Infracciones usando lista enlazada interna.
     * Cada vehículo almacena su propio historial de multas en una lista enlazada.
     *
     * Operaciones:
     * - Create: insertarMulta -> O(1) amortizado.
     * - Read: generarReporteHistorial -> O(n).
     * - Update: actualizarMulta -> O(n).
     * - Delete: eliminarMulta -> O(n).
     */

    public boolean insertarMulta(Vehiculo vehiculo, Multas multa) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }
        if (multa == null) {
            throw new IllegalArgumentException("La multa no puede ser nula.");
        }

        vehiculo.agregarMulta(multa);
        return true;
    }

    public String generarReporteHistorial(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return "Vehículo inválido.";
        }

        List<Multas> historial = vehiculo.getHistorialMultas();
        if (historial.isEmpty()) {
            return "No hay multas registradas para este vehículo.";
        }

        StringBuilder reporte = new StringBuilder("Historial de infracciones:\n");
        for (Multas multa : historial) {
            reporte.append(multa.toString()).append("\n");
        }
        return reporte.toString();
    }

    public List<Multas> obtenerHistorial(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehiculo no puede ser nulo.");
        }
        return vehiculo.getHistorialMultas();
    }

    public boolean actualizarMulta(Vehiculo vehiculo, int multaId, double nuevoPrecio, String nuevoMotivo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }

        return vehiculo.actualizarMulta(multaId, nuevoPrecio, nuevoMotivo);
    }

    public boolean eliminarMulta(Vehiculo vehiculo, int multaId) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }

        return vehiculo.eliminarMulta(multaId);
    }

    public Multas buscarMulta(Vehiculo vehiculo, int multaId) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }
        return vehiculo.buscarMulta(multaId);
    }
}
