public class Conexionaccidente {
    private Accidente destino;
    private  int longitud;

    public Conexionaccidente(Accidente destino, int longitud) {
        this.destino = destino;
        this.longitud = longitud;
    }

    public Accidente getDestino() {
        return destino;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
}
