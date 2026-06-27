public class NodoCiudadano {
    private Ciudadano ciudadano;
    private NodoCiudadano izq;
    private NodoCiudadano der;

    public NodoCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
        this.izq = null;
        this.der = null;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    public NodoCiudadano getIzq() {
        return izq;
    }

    public NodoCiudadano getDer() {
        return der;
    }

    public void setIzq(NodoCiudadano izq) {
        this.izq = izq;
    }

    public void setDer(NodoCiudadano der) {
        this.der = der;
    }
}
