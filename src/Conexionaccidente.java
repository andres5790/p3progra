public class Conexionaccidente {
    private final NodoAccidente destino;
    private int longitud;

    public Conexionaccidente(NodoAccidente destino, int longitud) {
        this.destino = destino;
        this.longitud = longitud;
    }

    public NodoAccidente getDestino() {
        return destino;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
}
