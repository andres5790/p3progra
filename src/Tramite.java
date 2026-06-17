public class Tramite {
    private Ciudadano ciudadano;
    private Empleado empleado;
    private  Estado estado;
    private Tipotramite tipo;

    public Tramite(Estado estado, Empleado empleado, Ciudadano ciudadano,Tipotramite tipo) {
        this.estado = estado;
        this.empleado = empleado;
        this.ciudadano = ciudadano;
        this.tipo=tipo;
    }

    public Tipotramite getTipo() {
        return tipo;
    }

    public void setTipo(Tipotramite tipo) {
        this.tipo = tipo;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Tramite{" +
                "ciudadano=" + ciudadano.getNombre() +
                ", empleado=" + empleado.getNombre() +
                ", estado=" + estado +
                ", tipo=" + tipo +
                '}';
    }
}
