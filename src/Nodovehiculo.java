public class NodoVehiculo {
    private Vehiculo vehiculo;
    private NodoVehiculo izq;
    private NodoVehiculo der;

    public NodoVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.izq = null;
        this.der = null;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public NodoVehiculo getIzq() {
        return izq;
    }

    public NodoVehiculo getDer() {
        return der;
    }

    public void setDer(NodoVehiculo der) {
        this.der = der;
    }

    public void setIzq(NodoVehiculo izq) {
        this.izq = izq;
    }
}
