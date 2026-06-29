import java.util.ArrayList;
import java.util.List;

public class ModuloCiudadanos {

    private final ArrayList<Ciudadano> ciudadanos;

    public ModuloCiudadanos() {
        ciudadanos = new ArrayList<>();
    }

    // REGISTRAR CIUDADANO
    public boolean registrarCiudadano(Ciudadano ciudadano) {

        if (ciudadano == null) {
            return false;
        }

        if (!ValidationUtils.isValidCedula(ciudadano.getCedula())) {
            return false;
        }

        // evitar duplicados por cédula
        for (Ciudadano c : ciudadanos) {
            if (c.getCedula().equals(ciudadano.getCedula())) {
                return false;
            }
        }

        ciudadanos.add(ciudadano);
        return true;
    }

    // BUSCAR POR CÉDULA
    public Ciudadano buscarCedula(String cedula) {

        if (!ValidationUtils.isValidCedula(cedula)) {
            return null;
        }

        for (Ciudadano c : ciudadanos) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }

        return null;
    }

    // LISTA SEGURA (NO MODIFICABLE)
    public List<Ciudadano> getCiudadanos() {
        return new ArrayList<>(ciudadanos);
    }
}