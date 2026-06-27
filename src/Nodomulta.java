public class Nodomulta {
    Multas multa;
    Nodomulta izq;
    Nodomulta der;

    public Nodomulta(Multas multa) {
        this.multa = multa;
        this.izq = null;
        this.der = null;
    }

    public Multas getMulta() {
        return multa;
    }

    public Nodomulta getIzq() {
        return izq;
    }

    public Nodomulta getDer() {
        return der;
    }

    public void setDer(Nodomulta der) {
        this.der = der;
    }

    public void setIzq(Nodomulta izq) {
        this.izq = izq;
    }
}
