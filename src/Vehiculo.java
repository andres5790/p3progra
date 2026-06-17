public class Vehiculo  {
    private String placa;
    private int anio;
    private  Ciudadano propietario;
    private int totalmultas;

    public Vehiculo(String placa, int anio, Ciudadano propietario, int totalmultas) {
        this.placa = placa;
        this.anio = anio;
        this.propietario = propietario;
        this.totalmultas = totalmultas;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Ciudadano getPropietario() {
        return propietario;
    }

    public void setPropietario(Ciudadano propietario) {
        this.propietario = propietario;
    }

    public int getTotalmultas() {
        return totalmultas;
    }

    public void setTotalmultas(int totalmultas) {
        this.totalmultas = totalmultas;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", anio=" + anio +
                ", propietario=" + propietario.getNombre() +
                ", totalmultas=" + totalmultas +
                '}';
    }
}
