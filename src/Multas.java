public class Multas {
    private double precio;
    private String motivo;
    private  Vehiculo vehiculo;
    private  Empleado empleado;
    private  int id;

    public Multas(double precio, String motivo, Vehiculo vehiculo, Empleado empleado,int id) {
        this.precio = precio;
        this.motivo = motivo;
        this.vehiculo = vehiculo;
        this.empleado = empleado;
        this.id=id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Multas{" +
                "precio=" + precio +
                ", motivo='" + motivo + '\'' +
                ", vehiculo=" + vehiculo +
                ", empleado=" + empleado +
                ", id=" + id +
                '}';
    }
}
