import java.util.ArrayList;

public class NodoAccidente {
    private Accidente accidente;
    private ArrayList<Conexionaccidente> conexion;

    public NodoAccidente(Accidente accidente) {
        this.accidente = accidente;
        this.conexion = new ArrayList<>();
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public ArrayList<Conexionaccidente> getConexion() {
        return conexion;
    }
    public  void agregarconexiones(Conexionaccidente c){
        conexion.add(c);

    }
}
