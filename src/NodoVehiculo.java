public class NodoVehiculo {
    Vehiculo vehiculo;
    NodoVehiculo izq;
    NodoVehiculo der;

    public NodoVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.der=null;
        this.izq=null;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
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

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
