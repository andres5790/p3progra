public class HistorialMultas {

    private Nodomulta raiz;

    public HistorialMultas() {
        raiz = null;
    }

    // INSERTAR
    public void insertar(Multas multa) {

        // (opcional doble validación de seguridad)
        if (!ValidationUtils.isValidMulta(multa)) {
            System.out.println("Multa inválida, no se inserta");
            return;
        }

        raiz = insertarRec(raiz, multa);
    }

    private Nodomulta insertarRec(Nodomulta actual, Multas multa) {

        if (actual == null) {
            return new Nodomulta(multa);
        }

        if (multa.getId() < actual.getMulta().getId()) {
            actual.setIzq(insertarRec(actual.getIzq(), multa));
        } else {
            actual.setDer(insertarRec(actual.getDer(), multa));
        }

        return actual;
    }

    // INORDEN
    public String inOrden() {
        if (raiz == null) return "No hay datos";
        return inOrdenRec(raiz);
    }

    private String inOrdenRec(Nodomulta actual) {
        if (actual == null) return "";

        return inOrdenRec(actual.getIzq())
                + actual.getMulta().toString() + "\n"
                + inOrdenRec(actual.getDer());
    }

    // ACTUALIZAR
    public boolean actualizar(int id, double precio, String motivo, boolean cp, boolean cm) {

        if (!ValidationUtils.isValidMonto(precio)) return false;
        if (motivo == null || motivo.isBlank()) return false;

        return actualizarRec(raiz, id, precio, motivo, cp, cm);
    }

    private boolean actualizarRec(Nodomulta actual, int id, double precio, String motivo, boolean cp, boolean cm) {

        if (actual == null) return false;

        if (id == actual.getMulta().getId()) {

            if (cp) actual.getMulta().setPrecio(precio);
            if (cm) actual.getMulta().setMotivo(motivo);

            return true;
        }

        if (id < actual.getMulta().getId()) {
            return actualizarRec(actual.getIzq(), id, precio, motivo, cp, cm);
        } else {
            return actualizarRec(actual.getDer(), id, precio, motivo, cp, cm);
        }
    }

    // ELIMINAR
    public void eliminar(int id) {
        raiz = eliminarRec(raiz, id);
    }

    private Nodomulta eliminarRec(Nodomulta actual, int id) {

        if (actual == null) return null;

        if (id < actual.getMulta().getId()) {
            actual.setIzq(eliminarRec(actual.getIzq(), id));

        } else if (id > actual.getMulta().getId()) {
            actual.setDer(eliminarRec(actual.getDer(), id));

        } else {

            // caso 1: hoja
            if (actual.getIzq() == null && actual.getDer() == null) {
                return null;
            }

            // caso 2: un hijo
            if (actual.getIzq() == null) return actual.getDer();
            if (actual.getDer() == null) return actual.getIzq();

            // caso 3: dos hijos
            Nodomulta sucesor = minimo(actual.getDer());

            actual.getMulta().setId(sucesor.getMulta().getId());
            actual.getMulta().setPrecio(sucesor.getMulta().getPrecio());
            actual.getMulta().setMotivo(sucesor.getMulta().getMotivo());

            actual.setDer(eliminarRec(actual.getDer(), sucesor.getMulta().getId()));
        }

        return actual;
    }

    private Nodomulta minimo(Nodomulta actual) {
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    // BUSCAR
    public Multas buscar(int id) {
        return buscarRec(raiz, id);
    }

    private Multas buscarRec(Nodomulta actual, int id) {

        if (actual == null) return null;

        if (id == actual.getMulta().getId()) return actual.getMulta();

        if (id < actual.getMulta().getId()) {
            return buscarRec(actual.getIzq(), id);
        } else {
            return buscarRec(actual.getDer(), id);
        }
    }

    // SIZE (para Vehiculo)
    public int size() {
        return sizeRec(raiz);
    }

    private int sizeRec(Nodomulta actual) {
        if (actual == null) return 0;
        return 1 + sizeRec(actual.getIzq()) + sizeRec(actual.getDer());
    }
}