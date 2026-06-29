import java.util.ArrayList;
import java.util.List;

public class NodoAccidente {
    private final Accidente accidente;
    private final List<Conexionaccidente> conexion;

    public NodoAccidente(Accidente accidente) {
        this.accidente = accidente;
        this.conexion = new ArrayList<>();
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public List<Conexionaccidente> getConexion() {
        return conexion;
    }

    public void agregarConexion(Conexionaccidente conexion) {
        this.conexion.add(conexion);
    }
}
