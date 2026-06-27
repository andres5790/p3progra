public class Ciudadano {
    private int cedula;
    private String nombre;
    private double deudat;

    public Ciudadano(int cedula, String nombre,double deudat) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.deudat=deudat;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDeudat() {
        return deudat;
    }

    public void setDeudat(double deudat) {
        this.deudat = deudat;
    }

    @Override
    public String toString() {
        return "Ciudadano{" +
                "cedula=" + cedula +
                ", nombre='" + nombre + '\'' +
                ", deudat=" + deudat +
                '}';
    }
}
