public class Nodovehiculo {
    Vehiculo vehiculo;
    Nodovehiculo izq;
    Nodovehiculo der;

    public Nodovehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.der=null;
        this.izq=null;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Nodovehiculo getIzq() {
        return izq;
    }

    public Nodovehiculo getDer() {
        return der;
    }

    public void setDer(Nodovehiculo der) {
        this.der = der;
    }

    public void setIzq(Nodovehiculo izq) {
        this.izq = izq;
    }
}
