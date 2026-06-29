import java.time.Year;
import java.util.regex.Pattern;

public final class ValidationUtils {
    // Validaciones puntuales para placa, cédula, montos y año.
    private static final Pattern ECUADOR_PLACA_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{4}$");
    private static final Pattern ECUADOR_CEDULA_PATTERN = Pattern.compile("^\\d{10}$");

    private ValidationUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isValidPlaca(String placa) {
        return placa != null && ECUADOR_PLACA_PATTERN.matcher(placa.trim().toUpperCase()).matches();
    }

    public static boolean isValidCedula(String cedula) {
        return cedula != null && ECUADOR_CEDULA_PATTERN.matcher(cedula.trim()).matches();
    }

    public static boolean isValidMonto(double monto) {
        return monto >= 0;
    }

    public static boolean isValidAnio(int anio) {
        int currentYear = Year.now().getValue();
        return anio >= 1900 && anio <= currentYear;
    }

    public static boolean isValidMulta(Multas multa) {
        if (multa == null) return false;
        if (multa.getId() <= 0) return false;
        if (multa.getPrecio() < 0) return false;
        if (multa.getMotivo() == null || multa.getMotivo().isBlank()) return false;
        return true;
    }
}
