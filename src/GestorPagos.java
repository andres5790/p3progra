import java.util.Stack;

public class GestorPagos {
    private Stack<Pago> pagos;

    public GestorPagos(Stack<Pago> pagos) {
        this.pagos = pagos;
    }

    public boolean registrarpagos(Pago pago){
        if (pagos.isEmpty()){
            pagos.push(pago);
            return true;
        }else{
            if (pago.getIdp()>pagos.peek().getIdp()){
                pagos.push(pago);
                return true;
            }
            return false;
        }
    }

    public Pago consulta(){
        return pagos.peek();
    }

    public void estadodeuda() {
        Pago pago = pagos.peek();
        Ciudadano ciudadano = pago.getCiudadano();
        ciudadano.aplicarPago(pago.getCantidad());
    }

    public void anularpago() {
        Pago pago = pagos.pop();
        Ciudadano ciudadano = pago.getCiudadano();
        ciudadano.incrementarDeuda(pago.getCantidad());
    }
}
