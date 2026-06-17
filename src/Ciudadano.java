public class Ciudadano {
    private int id;
    private String nombre;
    private double deudat;

    public Ciudadano(int id, String nombre,double deudat) {
        this.id = id;
        this.nombre = nombre;
        this.deudat=deudat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", deudat=" + deudat +
                '}';
    }
}
