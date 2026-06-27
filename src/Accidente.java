public class Accidente {
    private String direccion;
    private int id;
    private  String tipo;
    private String causa;

    public Accidente(String direccion, int id, String tipo, String causa) {
        this.direccion = direccion;
        this.id = id;
        this.tipo = tipo;
        this.causa = causa;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCausa() {
        return causa;
    }

    @Override
    public String toString() {
        return "NodoAccidente{" +
                "direccion='" + direccion + '\'' +
                ", id=" + id +
                ", tipo='" + tipo + '\'' +
                ", causa='" + causa + '\'' +
                '}';
    }
}
