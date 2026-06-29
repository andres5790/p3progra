public class RegistroVehicular {

    private NodoVehiculo raiz;

    public RegistroVehicular() {
        this.raiz = null;
    }

    // =========================
    // INSERTAR VEHÍCULO
    // =========================
    public void insertar(Vehiculo vehiculo) {

        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }

        // 🔥 VALIDACIONES AGREGADAS
        if (!ValidationUtils.isValidPlaca(vehiculo.getPlaca())) {
            throw new IllegalArgumentException("Placa inválida.");
        }

        if (!ValidationUtils.isValidAnio(vehiculo.getAnio())) {
            throw new IllegalArgumentException("Año inválido.");
        }

        if (vehiculo.getPropietario() == null) {
            throw new IllegalArgumentException("El vehículo debe tener propietario.");
        }

        if (raiz == null) {
            raiz = new NodoVehiculo(vehiculo);
        } else {
            insertar(raiz, vehiculo);
        }
    }

    private void insertar(NodoVehiculo actual, Vehiculo vehiculo) {

        int cmp = vehiculo.getPlaca()
                .compareTo(actual.getVehiculo().getPlaca());

        if (cmp == 0) {
            return; // duplicado, no inserta
        }

        if (cmp < 0) {
            if (actual.getIzq() == null) {
                actual.setIzq(new NodoVehiculo(vehiculo));
            } else {
                insertar(actual.getIzq(), vehiculo);
            }
        } else {
            if (actual.getDer() == null) {
                actual.setDer(new NodoVehiculo(vehiculo));
            } else {
                insertar(actual.getDer(), vehiculo);
            }
        }
    }

    // =========================
    // BUSCAR
    // =========================
    public Vehiculo buscar(String placa) {

        if (!ValidationUtils.isValidPlaca(placa)) {
            return null;
        }

        return buscar(raiz, placa.trim().toUpperCase());
    }

    private Vehiculo buscar(NodoVehiculo actual, String placa) {

        if (actual == null) {
            return null;
        }

        int cmp = placa.compareTo(actual.getVehiculo().getPlaca());

        if (cmp == 0) {
            return actual.getVehiculo();
        }

        if (cmp < 0) {
            return buscar(actual.getIzq(), placa);
        } else {
            return buscar(actual.getDer(), placa);
        }
    }

    // =========================
    // ACTUALIZAR
    // =========================
    public boolean actualizar(String placa, Ciudadano nuevoPropietario, int nuevoAnio) {

        if (!ValidationUtils.isValidPlaca(placa)) {
            return false;
        }

        Vehiculo v = buscar(placa);

        if (v == null) {
            return false;
        }

        boolean cambio = false;

        if (nuevoPropietario != null) {
            v.setPropietario(nuevoPropietario);
            cambio = true;
        }

        if (ValidationUtils.isValidAnio(nuevoAnio)) {
            v.setAnio(nuevoAnio);
            cambio = true;
        }

        return cambio;
    }

    // =========================
    // ELIMINAR
    // =========================
    public void eliminar(String placa) {

        if (!ValidationUtils.isValidPlaca(placa)) {
            return;
        }

        raiz = eliminar(raiz, placa.trim().toUpperCase());
    }

    private NodoVehiculo eliminar(NodoVehiculo actual, String placa) {

        if (actual == null) {
            return null;
        }

        int cmp = placa.compareTo(actual.getVehiculo().getPlaca());

        if (cmp < 0) {
            actual.setIzq(eliminar(actual.getIzq(), placa));

        } else if (cmp > 0) {
            actual.setDer(eliminar(actual.getDer(), placa));

        } else {

            if (actual.getIzq() == null) return actual.getDer();
            if (actual.getDer() == null) return actual.getIzq();

            NodoVehiculo sucesor = minimo(actual.getDer());

            actual.setVehiculo(sucesor.getVehiculo());

            actual.setDer(
                    eliminar(actual.getDer(),
                            sucesor.getVehiculo().getPlaca())
            );
        }

        return actual;
    }

    private NodoVehiculo minimo(NodoVehiculo actual) {
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    // =========================
    // INORDEN
    // =========================
    public String inOrden() {

        if (raiz == null) {
            return "No hay vehículos registrados.";
        }

        return inOrden(raiz);
    }

    private String inOrden(NodoVehiculo actual) {

        if (actual == null) {
            return "";
        }

        return inOrden(actual.getIzq())
                + actual.getVehiculo().toString() + "\n"
                + inOrden(actual.getDer());
    }
}