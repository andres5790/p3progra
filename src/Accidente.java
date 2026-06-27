public class Accidente {
    private String direccion;
    private int id;
    private  String tipo;
    private String causa;

    public Accidente(String direccion, int id, String tipo, String causa) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador de la zona debe ser positivo.");
        }
        validarTexto(direccion, "direccion");
        validarTexto(tipo, "tipo");
        validarTexto(causa, "causa");

        this.direccion = direccion.trim();
        this.id = id;
        this.tipo = tipo.trim();
        this.causa = causa.trim();
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

    public void setDireccion(String direccion) {
        validarTexto(direccion, "direccion");
        this.direccion = direccion.trim();
    }

    public void setTipo(String tipo) {
        validarTexto(tipo, "tipo");
        this.tipo = tipo.trim();
    }

    public void setCausa(String causa) {
        validarTexto(causa, "causa");
        this.causa = causa.trim();
    }

    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacio.");
        }
    }

    @Override
    public String toString() {
        return "Zona{" +
                "direccion='" + direccion + '\'' +
                ", id=" + id +
                ", tipo='" + tipo + '\'' +
                ", causa='" + causa + '\'' +
                '}';
    }
}
