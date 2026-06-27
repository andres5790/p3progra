import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;



public class Ventana {

    // Componentes enlazados desde Ventana.form por IntelliJ GUI Designer.
    private JPanel panelPrincipal;
    private JTabbedPane tabbedPane;

    private JPanel panelPagos;
    private JTextField txtPagoCedula;
    private JTextField txtPagoNombre;
    private JTextField txtPagoDeuda;
    private JTextField txtPagoMonto;
    private JButton btnPagoRegistrar;
    private JButton btnPagoVerUltimo;
    private JButton btnPagoAnular;
    private JButton btnPagoLimpiar;
    private JTable tblPagos;

    private JPanel panelTramites;
    private JTextField txtTramiteCedula;
    private JTextField txtTramiteNombre;
    private JTextField txtTramiteEmpleadoId;
    private JTextField txtTramiteEmpleadoNombre;
    private JComboBox<Tipotramite> cmbTramiteTipo;
    private JButton btnTramiteEncolar;
    private JButton btnTramiteIniciar;
    private JButton btnTramiteFinalizar;
    private JButton btnTramiteCancelar;
    private JButton btnTramiteLimpiar;
    private JTable tblTramites;

    private JPanel panelInfracciones;
    private JTextField txtMultaPlaca;
    private JTextField txtMultaId;
    private JTextField txtMultaPrecio;
    private JTextField txtMultaMotivo;
    private JTextField txtMultaEmpleadoId;
    private JTextField txtMultaEmpleadoNombre;
    private JButton btnMultaRegistrar;
    private JButton btnMultaBuscar;
    private JButton btnMultaActualizar;
    private JButton btnMultaEliminar;
    private JButton btnMultaLimpiar;
    private JTable tblMultas;

    private JPanel panelVehiculos;
    private JTextField txtVehiculoPlaca;
    private JTextField txtVehiculoAnio;
    private JTextField txtVehiculoCedula;
    private JTextField txtVehiculoPropietario;
    private JTextField txtVehiculoDeuda;
    private JButton btnVehiculoRegistrar;
    private JButton btnVehiculoBuscar;
    private JButton btnVehiculoActualizar;
    private JButton btnVehiculoEliminar;
    private JButton btnVehiculoLimpiar;
    private JTable tblVehiculos;

    private JPanel panelZonas;
    private JTextField txtZonaId;
    private JTextField txtZonaDireccion;
    private JTextField txtZonaTipo;
    private JTextField txtZonaCausa;
    private JTextField txtZonaOrigen;
    private JTextField txtZonaDestino;
    private JTextField txtZonaDistancia;
    private JButton btnZonaRegistrar;
    private JButton btnZonaActualizar;
    private JButton btnZonaEliminar;
    private JButton btnZonaConectar;
    private JButton btnZonaActualizarConexion;
    private JButton btnZonaEliminarConexion;
    private JButton btnZonaRuta;
    private JButton btnZonaLimpiar;
    private JTable tblZonas;
    private JTable tblConexiones;
    private JTable tblRuta;

    // Modelos usados para llenar las tablas sin permitir edicion directa.
    private DefaultTableModel modeloPagos;
    private DefaultTableModel modeloTramites;
    private DefaultTableModel modeloMultas;
    private DefaultTableModel modeloVehiculos;
    private DefaultTableModel modeloZonas;
    private DefaultTableModel modeloConexiones;
    private DefaultTableModel modeloRuta;

    // Gestores en memoria para cada estructura de datos del proyecto.
    private final ModuloCiudadanos moduloCiudadanos = new ModuloCiudadanos();
    private final Modulo1 moduloPagos = new Modulo1();
    private final Modulo2 moduloTramites = new Modulo2();
    private final Modulo3 moduloInfracciones = new Modulo3();
    private final Modulo4 moduloVehiculos = new Modulo4();
    private final Modulo5 moduloZonas = new Modulo5();

    private int siguientePagoId = 1;

    // Constructor: solo configura logica, eventos y datos iniciales.
    public Ventana() {
        configurarModelos();
        registrarEventos();
        cargarDatosSemilla();
        refrescarTodo();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    // Define columnas y conecta cada JTable con su modelo.
    private void configurarModelos() {
        cmbTramiteTipo.setModel(new DefaultComboBoxModel<>(Tipotramite.values()));

        modeloVehiculos = crearModelo("Placa", "Anio", "Cedula", "Propietario", "Deuda", "Multas");
        modeloMultas = crearModelo("ID", "Placa", "Monto", "Motivo", "Empleado", "Fecha");
        modeloPagos = crearModelo("ID", "Cedula", "Ciudadano", "Monto", "Fecha", "Estado", "Deuda actual");
        modeloTramites = crearModelo("Orden", "Cedula", "Ciudadano", "Empleado", "Tipo", "Estado");
        modeloZonas = crearModelo("ID", "Direccion", "Tipo", "Causa");
        modeloConexiones = crearModelo("Origen", "Destino", "Distancia km");
        modeloRuta = crearModelo("Orden", "Zona", "Direccion");

        tblVehiculos.setModel(modeloVehiculos);
        tblMultas.setModel(modeloMultas);
        tblPagos.setModel(modeloPagos);
        tblTramites.setModel(modeloTramites);
        tblZonas.setModel(modeloZonas);
        tblConexiones.setModel(modeloConexiones);
        tblRuta.setModel(modeloRuta);
    }

    // Crea tablas de solo lectura para evitar edicion directa.
    private DefaultTableModel crearModelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    // Botones: validan entrada y delegan operaciones a los modulos.
    private void registrarEventos() {
        btnVehiculoRegistrar.addActionListener(e -> ejecutarSeguro(this::registrarVehiculo));
        btnVehiculoBuscar.addActionListener(e -> ejecutarSeguro(this::buscarVehiculo));
        btnVehiculoActualizar.addActionListener(e -> ejecutarSeguro(this::actualizarVehiculo));
        btnVehiculoEliminar.addActionListener(e -> ejecutarSeguro(this::eliminarVehiculo));
        btnVehiculoLimpiar.addActionListener(e -> limpiarVehiculos());

        btnMultaRegistrar.addActionListener(e -> ejecutarSeguro(this::registrarMulta));
        btnMultaBuscar.addActionListener(e -> ejecutarSeguro(this::buscarMulta));
        btnMultaActualizar.addActionListener(e -> ejecutarSeguro(this::actualizarMulta));
        btnMultaEliminar.addActionListener(e -> ejecutarSeguro(this::eliminarMulta));
        btnMultaLimpiar.addActionListener(e -> limpiarMultas());

        btnPagoRegistrar.addActionListener(e -> ejecutarSeguro(this::registrarPago));
        btnPagoVerUltimo.addActionListener(e -> ejecutarSeguro(this::verUltimoPago));
        btnPagoAnular.addActionListener(e -> ejecutarSeguro(this::anularUltimoPago));
        btnPagoLimpiar.addActionListener(e -> limpiarPagos());

        btnTramiteEncolar.addActionListener(e -> ejecutarSeguro(this::encolarTramite));
        btnTramiteIniciar.addActionListener(e -> ejecutarSeguro(this::iniciarTramite));
        btnTramiteFinalizar.addActionListener(e -> ejecutarSeguro(this::finalizarTramite));
        btnTramiteCancelar.addActionListener(e -> ejecutarSeguro(this::cancelarTramite));
        btnTramiteLimpiar.addActionListener(e -> limpiarTramites());

        btnZonaRegistrar.addActionListener(e -> ejecutarSeguro(this::registrarZona));
        btnZonaActualizar.addActionListener(e -> ejecutarSeguro(this::actualizarZona));
        btnZonaEliminar.addActionListener(e -> ejecutarSeguro(this::eliminarZona));
        btnZonaConectar.addActionListener(e -> ejecutarSeguro(this::conectarZonas));
        btnZonaActualizarConexion.addActionListener(e -> ejecutarSeguro(this::actualizarConexion));
        btnZonaEliminarConexion.addActionListener(e -> ejecutarSeguro(this::eliminarConexion));
        btnZonaRuta.addActionListener(e -> ejecutarSeguro(this::calcularRuta));
        btnZonaLimpiar.addActionListener(e -> limpiarZonas());
    }

    // Datos base para que la defensa no inicie con el sistema vacio.
    private void cargarDatosSemilla() {
        Empleado agenteNorte = new Empleado("ATM-001", "Carolina Almeida");
        Empleado agenteCentro = new Empleado("ATM-002", "Sebastian Ponce");
        Empleado agenteSur = new Empleado("ATM-003", "Daniela Moreira");

        Ciudadano andres = new Ciudadano("1712345678", "Andres Mena", 100.00);
        Ciudadano valeria = new Ciudadano("1723456789", "Valeria Cevallos", 50.00);
        Ciudadano luis = new Ciudadano("1709876543", "Luis Paredes", 0.00);
        Ciudadano camila = new Ciudadano("1756789012", "Camila Torres", 25.00);
        Ciudadano diego = new Ciudadano("1711122233", "Diego Salazar", 0.00);
        Ciudadano maria = new Ciudadano("1722233344", "Maria Fernanda Ruiz", 0.00);
        Ciudadano jose = new Ciudadano("1733344455", "Jose Hidalgo", 0.00);
        Ciudadano paula = new Ciudadano("1744455566", "Paula Guerrero", 0.00);

        moduloCiudadanos.registrarCiudadano(andres);
        moduloCiudadanos.registrarCiudadano(valeria);
        moduloCiudadanos.registrarCiudadano(luis);
        moduloCiudadanos.registrarCiudadano(camila);
        moduloCiudadanos.registrarCiudadano(diego);
        moduloCiudadanos.registrarCiudadano(maria);
        moduloCiudadanos.registrarCiudadano(jose);
        moduloCiudadanos.registrarCiudadano(paula);

        Vehiculo vehiculoAndres = new Vehiculo("PBX-1234", 2020, andres);
        Vehiculo vehiculoValeria = new Vehiculo("PCQ-9876", 2019, valeria);
        Vehiculo vehiculoLuis = new Vehiculo("PAA-4567", 2022, luis);
        Vehiculo vehiculoCamila = new Vehiculo("PBC-2468", 2018, camila);
        Vehiculo vehiculoDiego = new Vehiculo("PCD-1357", 2021, diego);

        moduloVehiculos.insertarVehiculo(vehiculoAndres);
        moduloVehiculos.insertarVehiculo(vehiculoValeria);
        moduloVehiculos.insertarVehiculo(vehiculoLuis);
        moduloVehiculos.insertarVehiculo(vehiculoCamila);
        moduloVehiculos.insertarVehiculo(vehiculoDiego);

        Multas multaAndres1 = new Multas(45.00, "Exceso de velocidad", vehiculoAndres, agenteNorte, 101);
        Multas multaAndres2 = new Multas(30.00, "Estacionamiento en zona prohibida", vehiculoAndres, agenteCentro, 102);
        Multas multaValeria1 = new Multas(57.50, "No respetar semaforo", vehiculoValeria, agenteSur, 201);

        moduloInfracciones.insertarMulta(vehiculoAndres, multaAndres1);
        andres.incrementarDeuda(multaAndres1.getPrecio());
        moduloInfracciones.insertarMulta(vehiculoAndres, multaAndres2);
        andres.incrementarDeuda(multaAndres2.getPrecio());
        moduloInfracciones.insertarMulta(vehiculoValeria, multaValeria1);
        valeria.incrementarDeuda(multaValeria1.getPrecio());

        moduloTramites.encolarTramite(new Tramite(Estado.PENDIENTE, agenteNorte, maria, Tipotramite.RENOVACION_LICENCIA));
        moduloTramites.encolarTramite(new Tramite(Estado.PENDIENTE, agenteCentro, jose, Tipotramite.CAMBIO_PROPETARIO));
        moduloTramites.encolarTramite(new Tramite(Estado.PENDIENTE, agenteSur, paula, Tipotramite.DUPLICADO_MATRICULA));

        Pago pagoAndres = new Pago(siguientePagoId++, 40.00, andres);
        moduloPagos.registrarPago(pagoAndres);
        moduloPagos.aplicarPagoActual();

        Pago pagoValeria = new Pago(siguientePagoId++, 25.00, valeria);
        moduloPagos.registrarPago(pagoValeria);
        moduloPagos.aplicarPagoActual();

        moduloZonas.agregarAccidente(new Accidente("Av. Galo Plaza Lasso y De Los Arupos", 1, "Agencia", "Atencion vehicular norte"));
        moduloZonas.agregarAccidente(new Accidente("Av. Mariscal Sucre y Condor Nan", 2, "Agencia", "Atencion vehicular sur"));
        moduloZonas.agregarAccidente(new Accidente("Av. Amazonas y Republica", 3, "Agencia", "Zona comercial con alto trafico"));
        moduloZonas.agregarAccidente(new Accidente("Autopista General Ruminahui", 4, "Control", "Acceso al valle de Los Chillos"));
        moduloZonas.agregarAccidente(new Accidente("Panamericana Norte y Giovanni Calles", 5, "Control", "Ingreso norte a Quito"));

        moduloZonas.conectar(1, 3, 8);
        moduloZonas.conectar(1, 5, 10);
        moduloZonas.conectar(3, 5, 12);
        moduloZonas.conectar(3, 2, 14);
        moduloZonas.conectar(3, 4, 18);
        moduloZonas.conectar(4, 2, 16);
    }

    // Modulo 4: CRUD de vehiculos sobre el arbol BST.
    private void registrarVehiculo() {
        String placa = leerPlaca(txtVehiculoPlaca);
        int anio = leerAnio(txtVehiculoAnio);
        Ciudadano propietario = leerPropietarioDesdeVehiculo(true);

        if (!moduloVehiculos.insertarVehiculo(new Vehiculo(placa, anio, propietario))) {
            throw new IllegalArgumentException("Ya existe un vehiculo con esa placa.");
        }
        refrescarTodo();
        mostrarInfo("Vehiculo insertado en el arbol BST.");
    }

    private void buscarVehiculo() {
        String placa = leerPlaca(txtVehiculoPlaca);
        Vehiculo vehiculo = moduloVehiculos.buscarVehiculo(placa);
        if (vehiculo == null) {
            mostrarInfo("No existe un vehiculo con esa placa.");
            return;
        }

        txtVehiculoAnio.setText(String.valueOf(vehiculo.getAnio()));
        txtVehiculoCedula.setText(vehiculo.getPropietario().getCedula());
        txtVehiculoPropietario.setText(vehiculo.getPropietario().getNombre());
        txtVehiculoDeuda.setText(dinero(vehiculo.getPropietario().getDeuda()));
    }

    private void actualizarVehiculo() {
        String placa = leerPlaca(txtVehiculoPlaca);
        int anio = leerAnio(txtVehiculoAnio);
        Ciudadano propietario = leerPropietarioDesdeVehiculo(true);

        if (!moduloVehiculos.actualizarVehiculo(placa, propietario, anio)) {
            mostrarInfo("No se encontro el vehiculo o no hubo cambios validos.");
            return;
        }
        refrescarTodo();
        mostrarInfo("Vehiculo actualizado.");
    }

    private void eliminarVehiculo() {
        String placa = leerPlaca(txtVehiculoPlaca);
        if (!moduloVehiculos.eliminarVehiculo(placa)) {
            mostrarInfo("No se encontro el vehiculo.");
            return;
        }
        refrescarTodo();
        mostrarInfo("Vehiculo eliminado del BST.");
    }

    // Modulo 3: CRUD de infracciones en la lista enlazada del vehiculo.
    private void registrarMulta() {
        Vehiculo vehiculo = buscarVehiculoObligatorio(leerPlaca(txtMultaPlaca));
        int id = leerEnteroPositivo(txtMultaId, "ID de multa");
        double precio = leerMontoPositivo(txtMultaPrecio, "monto de multa");
        String motivo = leerTexto(txtMultaMotivo, "motivo");
        String empleadoId = leerTexto(txtMultaEmpleadoId, "ID del empleado");
        String empleadoNombre = leerTexto(txtMultaEmpleadoNombre, "nombre del empleado");

        if (moduloInfracciones.buscarMulta(vehiculo, id) != null) {
            throw new IllegalArgumentException("Ya existe una multa con ese ID para la placa indicada.");
        }

        Multas multa = new Multas(precio, motivo, vehiculo, new Empleado(empleadoId, empleadoNombre), id);
        moduloInfracciones.insertarMulta(vehiculo, multa);
        vehiculo.getPropietario().incrementarDeuda(precio);
        refrescarTodo();
        mostrarInfo("Multa registrada en la lista enlazada del vehiculo.");
    }

    private void buscarMulta() {
        Vehiculo vehiculo = buscarVehiculoObligatorio(leerPlaca(txtMultaPlaca));
        int id = leerEnteroPositivo(txtMultaId, "ID de multa");
        Multas multa = moduloInfracciones.buscarMulta(vehiculo, id);
        if (multa == null) {
            mostrarInfo("No se encontro la multa.");
            return;
        }

        txtMultaPrecio.setText(dinero(multa.getPrecio()));
        txtMultaMotivo.setText(multa.getMotivo());
        txtMultaEmpleadoId.setText(multa.getEmpleado().getId());
        txtMultaEmpleadoNombre.setText(multa.getEmpleado().getNombre());
        refrescarTablaMultas();
    }

    private void actualizarMulta() {
        Vehiculo vehiculo = buscarVehiculoObligatorio(leerPlaca(txtMultaPlaca));
        int id = leerEnteroPositivo(txtMultaId, "ID de multa");
        double nuevoPrecio = leerMontoPositivo(txtMultaPrecio, "nuevo monto");
        String nuevoMotivo = leerTexto(txtMultaMotivo, "nuevo motivo");

        Multas multa = moduloInfracciones.buscarMulta(vehiculo, id);
        if (multa == null) {
            mostrarInfo("No se encontro la multa.");
            return;
        }

        double precioAnterior = multa.getPrecio();
        if (!moduloInfracciones.actualizarMulta(vehiculo, id, nuevoPrecio, nuevoMotivo)) {
            mostrarInfo("No se pudo actualizar la multa.");
            return;
        }
        ajustarDeudaPorDiferencia(vehiculo.getPropietario(), nuevoPrecio - precioAnterior);
        refrescarTodo();
        mostrarInfo("Multa actualizada.");
    }

    private void eliminarMulta() {
        Vehiculo vehiculo = buscarVehiculoObligatorio(leerPlaca(txtMultaPlaca));
        int id = leerEnteroPositivo(txtMultaId, "ID de multa");
        Multas multa = moduloInfracciones.buscarMulta(vehiculo, id);
        if (multa == null) {
            mostrarInfo("No se encontro la multa.");
            return;
        }

        if (moduloInfracciones.eliminarMulta(vehiculo, id)) {
            vehiculo.getPropietario().aplicarPago(multa.getPrecio());
            refrescarTodo();
            mostrarInfo("Multa eliminada del historial.");
        }
    }

    // Modulo 1: pagos en pila, con anulacion LIFO.
    private void registrarPago() {
        String cedula = leerCedula(txtPagoCedula);
        String nombre = leerTexto(txtPagoNombre, "nombre del ciudadano");
        double deudaInicial = leerMonto(txtPagoDeuda, "deuda inicial");
        double monto = leerMontoPositivo(txtPagoMonto, "monto del pago");

        Ciudadano ciudadano = obtenerCiudadanoParaPago(cedula, nombre, deudaInicial);
        Pago pago = new Pago(siguientePagoId++, monto, ciudadano);
        moduloPagos.registrarPago(pago);
        moduloPagos.aplicarPagoActual();
        refrescarTodo();
        mostrarInfo("Pago registrado y aplicado.");
    }

    private void verUltimoPago() {
        Pago pago = moduloPagos.verUltimoPago();
        if (pago == null) {
            mostrarInfo("No hay pagos registrados.");
            return;
        }
        mostrarInfo("Ultimo pago: ID " + pago.getIdp()
                + " | Cedula " + pago.getCiudadano().getCedula()
                + " | Monto " + dinero(pago.getCantidad()));
    }

    private void anularUltimoPago() {
        Pago pago = moduloPagos.rollbackUltimoPago();
        if (pago == null) {
            mostrarInfo("No hay pagos para anular.");
            return;
        }
        refrescarTodo();
        mostrarInfo("Pago anulado por regla LIFO: ID " + pago.getIdp());
    }

    // Modulo 2: tramites en cola, con atencion FIFO.
    private void encolarTramite() {
        String cedula = leerCedula(txtTramiteCedula);
        String nombre = leerTexto(txtTramiteNombre, "nombre del ciudadano");
        String empleadoId = leerTexto(txtTramiteEmpleadoId, "ID del empleado");
        String empleadoNombre = leerTexto(txtTramiteEmpleadoNombre, "nombre del empleado");
        Tipotramite tipo = (Tipotramite) cmbTramiteTipo.getSelectedItem();

        Ciudadano ciudadano = obtenerOCrearCiudadano(cedula, nombre, 0, false);
        Empleado empleado = new Empleado(empleadoId, empleadoNombre);
        Tramite tramite = new Tramite(Estado.PENDIENTE, empleado, ciudadano, tipo);

        if (!moduloTramites.encolarTramite(tramite)) {
            throw new IllegalArgumentException("Ya existe un tramite abierto para esa cedula y tipo.");
        }
        refrescarTablaTramites();
        mostrarInfo("Tramite agregado a la cola FIFO.");
    }

    private void iniciarTramite() {
        if (!moduloTramites.iniciarTramiteActual()) {
            mostrarInfo("No hay tramites pendientes.");
            return;
        }
        refrescarTablaTramites();
        mostrarInfo("El tramite del frente paso a ENPROCESO.");
    }

    private void finalizarTramite() {
        Tramite tramite = moduloTramites.finalizarTramiteActual();
        if (tramite == null) {
            mostrarInfo("No hay tramites para finalizar.");
            return;
        }
        refrescarTablaTramites();
        mostrarInfo("Tramite finalizado por regla FIFO.");
    }

    private void cancelarTramite() {
        String cedula = leerCedula(txtTramiteCedula);
        Tipotramite tipo = (Tipotramite) cmbTramiteTipo.getSelectedItem();
        if (!moduloTramites.cancelarTramite(cedula, tipo)) {
            mostrarInfo("No se encontro un tramite abierto con esos datos.");
            return;
        }
        refrescarTablaTramites();
        mostrarInfo("Tramite cancelado.");
    }

    // Modulo 5: zonas como grafo y rutas con Dijkstra.
    private void registrarZona() {
        int id = leerEnteroPositivo(txtZonaId, "ID de zona");
        String direccion = leerTexto(txtZonaDireccion, "direccion");
        String tipo = leerTexto(txtZonaTipo, "tipo");
        String causa = leerTexto(txtZonaCausa, "causa");

        if (!moduloZonas.agregarAccidente(new Accidente(direccion, id, tipo, causa))) {
            throw new IllegalArgumentException("Ya existe una zona con ese ID.");
        }
        refrescarTablasZonas();
        mostrarInfo("Zona agregada al grafo.");
    }

    private void actualizarZona() {
        int id = leerEnteroPositivo(txtZonaId, "ID de zona");
        String direccion = leerTexto(txtZonaDireccion, "direccion");
        String tipo = leerTexto(txtZonaTipo, "tipo");
        String causa = leerTexto(txtZonaCausa, "causa");

        if (!moduloZonas.actualizarAccidente(id, direccion, tipo, causa)) {
            mostrarInfo("No se encontro la zona.");
            return;
        }
        refrescarTablasZonas();
        mostrarInfo("Zona actualizada.");
    }

    private void eliminarZona() {
        int id = leerEnteroPositivo(txtZonaId, "ID de zona");
        if (!moduloZonas.eliminarAccidente(id)) {
            mostrarInfo("No se encontro la zona.");
            return;
        }
        refrescarTablasZonas();
        mostrarInfo("Zona eliminada junto con sus conexiones.");
    }

    private void conectarZonas() {
        int origen = leerEnteroPositivo(txtZonaOrigen, "origen");
        int destino = leerEnteroPositivo(txtZonaDestino, "destino");
        int distancia = leerEnteroPositivo(txtZonaDistancia, "distancia");

        if (!moduloZonas.conectar(origen, destino, distancia)) {
            throw new IllegalArgumentException("No se pudo crear la conexion. Revise IDs, distancia o duplicados.");
        }
        refrescarTablasZonas();
        mostrarInfo("Conexion bidireccional registrada.");
    }

    private void actualizarConexion() {
        int origen = leerEnteroPositivo(txtZonaOrigen, "origen");
        int destino = leerEnteroPositivo(txtZonaDestino, "destino");
        int distancia = leerEnteroPositivo(txtZonaDistancia, "distancia");

        if (!moduloZonas.cambiarPesoArista(origen, destino, distancia)) {
            mostrarInfo("No se encontro la conexion.");
            return;
        }
        refrescarTablasZonas();
        mostrarInfo("Distancia de conexion actualizada.");
    }

    private void eliminarConexion() {
        int origen = leerEnteroPositivo(txtZonaOrigen, "origen");
        int destino = leerEnteroPositivo(txtZonaDestino, "destino");

        if (!moduloZonas.eliminarConexion(origen, destino)) {
            mostrarInfo("No se encontro la conexion.");
            return;
        }
        refrescarTablasZonas();
        mostrarInfo("Conexion eliminada.");
    }

    private void calcularRuta() {
        int origen = leerEnteroPositivo(txtZonaOrigen, "origen");
        int destino = leerEnteroPositivo(txtZonaDestino, "destino");
        Modulo5.RutaResultado resultado = moduloZonas.calcularRutaMasCorta(origen, destino);

        modeloRuta.setRowCount(0);
        if (!resultado.existeRuta()) {
            mostrarInfo(resultado.getMensaje());
            return;
        }

        int orden = 1;
        for (Integer idZona : resultado.getRecorrido()) {
            NodoAccidente nodo = moduloZonas.buscar(idZona);
            String direccion = nodo == null ? "" : nodo.getAccidente().getDireccion();
            modeloRuta.addRow(new Object[]{orden++, idZona, direccion});
        }
        mostrarInfo("Ruta calculada. Distancia total: " + resultado.getDistanciaTotal() + " km.");
    }

    // Sincroniza la interfaz con los datos que viven en memoria.
    private void refrescarTodo() {
        refrescarTablaVehiculos();
        refrescarTablaMultas();
        refrescarTablaPagos();
        refrescarTablaTramites();
        refrescarTablasZonas();
    }

    private void refrescarTablaVehiculos() {
        modeloVehiculos.setRowCount(0);
        for (Vehiculo vehiculo : moduloVehiculos.obtenerVehiculos()) {
            Ciudadano propietario = vehiculo.getPropietario();
            modeloVehiculos.addRow(new Object[]{
                    vehiculo.getPlaca(),
                    vehiculo.getAnio(),
                    propietario.getCedula(),
                    propietario.getNombre(),
                    dinero(propietario.getDeuda()),
                    vehiculo.getTotalmultas()
            });
        }
    }

    private void refrescarTablaMultas() {
        modeloMultas.setRowCount(0);
        List<Vehiculo> vehiculos = moduloVehiculos.obtenerVehiculos();
        for (Vehiculo vehiculo : vehiculos) {
            for (Multas multa : vehiculo.getHistorialMultas()) {
                modeloMultas.addRow(new Object[]{
                        multa.getId(),
                        vehiculo.getPlaca(),
                        dinero(multa.getPrecio()),
                        multa.getMotivo(),
                        multa.getEmpleado().getNombre(),
                        multa.getFecha()
                });
            }
        }
    }

    private void refrescarTablaPagos() {
        modeloPagos.setRowCount(0);
        for (Modulo1.RegistroPago registro : moduloPagos.obtenerRegistros()) {
            Pago pago = registro.getPago();
            Ciudadano ciudadano = pago.getCiudadano();
            modeloPagos.addRow(new Object[]{
                    pago.getIdp(),
                    ciudadano.getCedula(),
                    ciudadano.getNombre(),
                    dinero(pago.getCantidad()),
                    pago.getFecha(),
                    registro.isAplicado() ? "APLICADO" : "PENDIENTE",
                    dinero(ciudadano.getDeuda())
            });
        }
    }

    private void refrescarTablaTramites() {
        modeloTramites.setRowCount(0);
        int orden = 1;
        for (Tramite tramite : moduloTramites.obtenerTramites()) {
            modeloTramites.addRow(new Object[]{
                    orden++,
                    tramite.getCiudadano().getCedula(),
                    tramite.getCiudadano().getNombre(),
                    tramite.getEmpleado().getNombre(),
                    tramite.getTipo(),
                    tramite.getEstado()
            });
        }
    }

    private void refrescarTablasZonas() {
        modeloZonas.setRowCount(0);
        for (Accidente accidente : moduloZonas.obtenerAccidentes()) {
            modeloZonas.addRow(new Object[]{
                    accidente.getId(),
                    accidente.getDireccion(),
                    accidente.getTipo(),
                    accidente.getCausa()
            });
        }

        modeloConexiones.setRowCount(0);
        for (Modulo5.ConexionVista conexion : moduloZonas.obtenerConexiones()) {
            modeloConexiones.addRow(new Object[]{
                    conexion.getOrigen(),
                    conexion.getDestino(),
                    conexion.getDistancia()
            });
        }
    }

    // Reutiliza ciudadanos existentes para evitar cedulas duplicadas.
    private Ciudadano leerPropietarioDesdeVehiculo(boolean actualizarDeuda) {
        String cedula = leerCedula(txtVehiculoCedula);
        String nombre = leerTexto(txtVehiculoPropietario, "propietario");
        double deuda = leerMonto(txtVehiculoDeuda, "deuda");
        return obtenerOCrearCiudadano(cedula, nombre, deuda, actualizarDeuda);
    }

    private Ciudadano obtenerCiudadanoParaPago(String cedula, String nombre, double deudaInicial) {
        Ciudadano ciudadano = moduloCiudadanos.buscarCiudadano(cedula);
        if (ciudadano == null) {
            ciudadano = new Ciudadano(cedula, nombre, deudaInicial);
            moduloCiudadanos.registrarCiudadano(ciudadano);
            return ciudadano;
        }
        ciudadano.setNombre(nombre);
        return ciudadano;
    }

    private Ciudadano obtenerOCrearCiudadano(String cedula, String nombre, double deuda, boolean actualizarDeuda) {
        Ciudadano ciudadano = moduloCiudadanos.buscarCiudadano(cedula);
        if (ciudadano == null) {
            ciudadano = new Ciudadano(cedula, nombre, deuda);
            moduloCiudadanos.registrarCiudadano(ciudadano);
            return ciudadano;
        }
        ciudadano.setNombre(nombre);
        if (actualizarDeuda) {
            ciudadano.setDeuda(deuda);
        }
        return ciudadano;
    }

    private Vehiculo buscarVehiculoObligatorio(String placa) {
        Vehiculo vehiculo = moduloVehiculos.buscarVehiculo(placa);
        if (vehiculo == null) {
            throw new IllegalArgumentException("Debe registrar primero el vehiculo con placa " + placa + ".");
        }
        return vehiculo;
    }

    private void ajustarDeudaPorDiferencia(Ciudadano ciudadano, double diferencia) {
        if (diferencia > 0) {
            ciudadano.incrementarDeuda(diferencia);
        } else if (diferencia < 0) {
            ciudadano.aplicarPago(Math.abs(diferencia));
        }
    }

    // Validaciones de entrada antes de crear objetos o llamar modulos.
    private String leerCedula(JTextField campo) {
        String cedula = campo.getText().trim();
        if (!ValidationUtils.isValidCedula(cedula)) {
            throw new IllegalArgumentException("La cedula debe tener exactamente 10 digitos.");
        }
        return cedula;
    }

    private String leerPlaca(JTextField campo) {
        String placa = campo.getText().trim().toUpperCase(Locale.ROOT);
        if (!ValidationUtils.isValidPlaca(placa)) {
            throw new IllegalArgumentException("La placa debe tener formato ABC-1234.");
        }
        campo.setText(placa);
        return placa;
    }

    private String leerTexto(JTextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
        return texto;
    }

    private int leerAnio(JTextField campo) {
        int anio = leerEnteroPositivo(campo, "anio");
        if (!ValidationUtils.isValidAnio(anio)) {
            throw new IllegalArgumentException("El anio del vehiculo debe estar entre 1900 y el anio actual.");
        }
        return anio;
    }

    private int leerEnteroPositivo(JTextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (!texto.matches("\\d+")) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser un numero entero positivo.");
        }
        int valor = Integer.parseInt(texto);
        if (valor <= 0) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser mayor que cero.");
        }
        return valor;
    }

    private double leerMonto(JTextField campo, String nombreCampo) {
        String texto = campo.getText().trim().replace(",", ".");
        if (!texto.matches("\\d+(\\.\\d{1,2})?")) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser un monto valido.");
        }
        double valor = Double.parseDouble(texto);
        if (!ValidationUtils.isValidMonto(valor)) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede ser negativo.");
        }
        return valor;
    }

    private double leerMontoPositivo(JTextField campo, String nombreCampo) {
        double valor = leerMonto(campo, nombreCampo);
        if (valor <= 0) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser mayor que cero.");
        }
        return valor;
    }

    private String dinero(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }

    // Ejecuta acciones de botones y muestra errores controlados.
    private void ejecutarSeguro(Runnable accion) {
        try {
            accion.run();
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Ocurrio un error inesperado: " + ex.getMessage());
        }
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(panelPrincipal, mensaje, "ATM", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(panelPrincipal, mensaje, "Validacion", JOptionPane.ERROR_MESSAGE);
    }

    // Limpieza de campos por pestana.
    private void limpiarVehiculos() {
        txtVehiculoPlaca.setText("");
        txtVehiculoAnio.setText("");
        txtVehiculoCedula.setText("");
        txtVehiculoPropietario.setText("");
        txtVehiculoDeuda.setText("");
    }

    private void limpiarMultas() {
        txtMultaPlaca.setText("");
        txtMultaId.setText("");
        txtMultaPrecio.setText("");
        txtMultaMotivo.setText("");
        txtMultaEmpleadoId.setText("");
        txtMultaEmpleadoNombre.setText("");
    }

    private void limpiarPagos() {
        txtPagoCedula.setText("");
        txtPagoNombre.setText("");
        txtPagoDeuda.setText("");
        txtPagoMonto.setText("");
    }

    private void limpiarTramites() {
        txtTramiteCedula.setText("");
        txtTramiteNombre.setText("");
        txtTramiteEmpleadoId.setText("");
        txtTramiteEmpleadoNombre.setText("");
        cmbTramiteTipo.setSelectedIndex(0);
    }

    private void limpiarZonas() {
        txtZonaId.setText("");
        txtZonaDireccion.setText("");
        txtZonaTipo.setText("");
        txtZonaCausa.setText("");
        txtZonaOrigen.setText("");
        txtZonaDestino.setText("");
        txtZonaDistancia.setText("");
    }

    // Punto de prueba local desde IntelliJ.
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
