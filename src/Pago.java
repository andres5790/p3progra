import java.time.LocalDate;

public class Pago {
    private int idp;
    private int cantidad;
    private Ciudadano ciudadano;
    private String fecha;

    public Pago(int idp,int cantidad, Ciudadano ciudadano, String fecha) {
        this.idp=idp;
        this.cantidad = cantidad;
        this.ciudadano = ciudadano;
        this.fecha=fecha;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idp=" + idp +
                ", cantidad=" + cantidad +
                ", ciudadano=" + ciudadano.getNombre() +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
