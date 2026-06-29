import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Clase principal de la Interfaz Gráfica (GUI).
 * Actúa como el controlador que conecta la vista (.form) con la lógica de negocio
 * y las diferentes estructuras de datos (Árboles Binarios, Pilas, Colas y Grafos).
 */
public class VentanaGestor {

    // ==========================================
    // COMPONENTES DE LA INTERFAZ GRÁFICA
    // ==========================================
    private JPanel panelPrincipal;
    private JTabbedPane tabbedPane1;

    // Pestaña: Vehículos
    private JPanel panelVehiculos;
    private JTextField txtVehiculoPlaca;
    private JTextField txtVehiculoAño;
    private JTextField textField1; // Cédula
    private JTextField txtVehiculoPropietario;
    private JTextField txtVehiculoDeuda;
    private JButton btnVehiculoRegistrar, btnVehiculoBuscar, btnVehiculoActualizar, btnVehiculoEliminar, btnVehiculoLimpiar;
    private JTable tblVehiculos;

    // Pestaña: Infracciones
    private JPanel panelInfracciones;
    private JTextField txtMultasPlacas;
    private JTextField txtMultaId;
    private JTextField txtMultaPrecio;
    private JTextField txtMultaMotivo;
    private JTextField txtMultaEmpleadoId;
    private JTextField txtMultaEmpleadoNombre;
    private JButton btnMultaRegistrar, btnMultaBuscar, btnMultaActualizar, btnMultaEliminar, btnMultaLimpiar;
    private JTable tblMultas;

    // Pestaña: Pagos (Solvencia)
    private JPanel panelPagos;
    private JTextField txtPagoCedula;
    private JTextField txtPagoNombre;
    private JTextField txtPagoDeuda;
    private JTextField txtPagoMonto;
    private JButton btnPagoRegistrar, btnPagoVerUltimo, btnPagoAnular, btnPagoLimpiar;
    private JTable tblPagos;

    // Pestaña: Trámites
    private JPanel panelTramites;
    private JTextField txtTramiteNombre;
    private JTextField txtTramiteEmpleadoId;
    private JTextField txtTramitesCedula;
    private JTextField txtTramiteEmpleadoNombre;
    private JComboBox<String> cmbTramiteTipo;
    private JButton btnTramiteEncolar, btnTramiteIniciar, btnTramiteFinalizar, btnTramiteCancelar, btnTramiteLimpiar;
    private JTable tblTramiteEncolar;

    // Pestaña: Zonas (Gestión Red Vial)
    private JPanel panelZonas;
    private JTextField txtZonaId, txtZonaDireccion, txtZonaCausa, txtZonaTipo;
    private JTextField txtZonaOrigen, txtZonaDestino, textField2; // textField2 = Distancia
    private JButton btnZonaRegistrar, btnZonaActualizar, btnZonaEliminar;
    private JButton btnZonaConectar, btnZonaActualizarConexion, btnZonaEliminarConexion, btnZonaRuta, btnZonaLimpiar;
    private JTable tblZonas, tblConexiones, tblRuta;

    // ==========================================
    // CONTROLADORES Y ESTRUCTURAS DE DATOS
    // ==========================================
    private final RegistroVehicular registroVehicular; // Árbol Binario de Búsqueda (BST)
    private final HistorialMultas historialMultas;     // Árbol Binario de Búsqueda (BST)
    private final Stack<Pago> pilaPagos;               // Pila (LIFO)
    private final GestorPagos gestorPagos;
    private final Queue<Tramite> colaTramites;         // Cola (FIFO)
    private final GestorTramites gestorTramites;
    private final GestionRedVial gestionRedVial;       // Grafos (Vértices y Aristas)
    private final ModuloCiudadanos moduloCiudadanos;
    private final ModuloEmpleados moduloEmpleados;

    public VentanaGestor() {
        // Inicialización segura de componentes de tabla
        if (tblVehiculos == null) tblVehiculos = new JTable();
        if (tblMultas == null) tblMultas = new JTable();
        if (tblPagos == null) tblPagos = new JTable();
        if (tblTramiteEncolar == null) tblTramiteEncolar = new JTable();
        if (tblZonas == null) tblZonas = new JTable();
        if (tblConexiones == null) tblConexiones = new JTable();
        if (tblRuta == null) tblRuta = new JTable();

        // 1. Instanciación de las estructuras de datos principales
        registroVehicular = new RegistroVehicular();
        historialMultas = new HistorialMultas();
        pilaPagos = new Stack<>();
        gestorPagos = new GestorPagos(pilaPagos);
        colaTramites = new LinkedList<>();
        gestorTramites = new GestorTramites(colaTramites);
        gestionRedVial = new GestionRedVial();
        moduloCiudadanos = new ModuloCiudadanos();
        moduloEmpleados = new ModuloEmpleados();

        // 2. Configuración inicial de la interfaz y carga del estado base
        configurarTablas();
        inicializarDatos();
        refrescarTodasLasTablas();

        // 3. Vinculación de eventos (Listeners) a los componentes visuales
        configurarEventosVehiculos();
        configurarEventosMultas();
        configurarEventosPagos();
        configurarEventosTramites();
        configurarEventosZonas();
    }

    // ==========================================
    // CONFIGURACIÓN INICIAL DEL ESTADO DEL SISTEMA
    // ==========================================
    private void inicializarDatos() {
        // Registro de Empleados del sistema
        Empleado emp1 = new Empleado("1", "Carolina Almeida");
        Empleado emp2 = new Empleado("2", "Sebastian Ponce");
        Empleado emp3 = new Empleado("3", "Daniela Moreira");
        moduloEmpleados.registrarEmpleado(emp1);
        moduloEmpleados.registrarEmpleado(emp2);
        moduloEmpleados.registrarEmpleado(emp3);

        // Registro de Ciudadanos
        Ciudadano c1 = new Ciudadano("1709876543", "Luis Paredes", 0.00);
        Ciudadano c2 = new Ciudadano("1756789012", "Camila Torres", 25.00);
        Ciudadano c3 = new Ciudadano("1712345678", "Andres Mena", 135.00);
        Ciudadano c4 = new Ciudadano("1711122233", "Diego Salazar", 0.00);
        Ciudadano c5 = new Ciudadano("1723456789", "Valeria Cevallos", 82.50);
        Ciudadano c6 = new Ciudadano("1722233344", "Maria Fernanda Ruiz", 0.00);
        Ciudadano c7 = new Ciudadano("1733344455", "Jose Hidalgo", 0.00);
        Ciudadano c8 = new Ciudadano("1744455566", "Paula Guerrero", 0.00);

        moduloCiudadanos.registrarCiudadano(c1);
        moduloCiudadanos.registrarCiudadano(c2);
        moduloCiudadanos.registrarCiudadano(c3);
        moduloCiudadanos.registrarCiudadano(c4);
        moduloCiudadanos.registrarCiudadano(c5);
        moduloCiudadanos.registrarCiudadano(c6);
        moduloCiudadanos.registrarCiudadano(c7);
        moduloCiudadanos.registrarCiudadano(c8);

        // Población del Árbol Binario de Búsqueda (Vehículos)
        Vehiculo v1 = new Vehiculo("PAA-4567", 2022, c1);
        Vehiculo v2 = new Vehiculo("PBC-2468", 2018, c2);
        Vehiculo v3 = new Vehiculo("PBX-1234", 2020, c3);
        Vehiculo v4 = new Vehiculo("PCD-1357", 2021, c4);
        Vehiculo v5 = new Vehiculo("PCQ-9876", 2019, c5);

        v3.incrementarMultas(); v3.incrementarMultas();
        v5.incrementarMultas();

        registroVehicular.insertar(v3); // Se inserta como raíz para un balanceo inicial adecuado
        registroVehicular.insertar(v1);
        registroVehicular.insertar(v2);
        registroVehicular.insertar(v4);
        registroVehicular.insertar(v5);

        // Población del Árbol Binario de Búsqueda (Multas e Infracciones)
        Multas m1 = new Multas(45.00, "Exceso de velocidad", v3, emp1, 101);
        Multas m2 = new Multas(30.00, "Estacionamiento en zona prohibida", v3, emp2, 102);
        Multas m3 = new Multas(57.50, "No respetar semaforo", v5, emp3, 201);
        historialMultas.insertar(m1);
        historialMultas.insertar(m2);
        historialMultas.insertar(m3);

        // Población de la Pila de Pagos (LIFO)
        Pago p1 = new Pago(1, 40.00, c3, LocalDate.of(2026, 6, 28));
        Pago p2 = new Pago(2, 25.00, c5, LocalDate.of(2026, 6, 28));
        gestorPagos.registrarpagos(p1);
        gestorPagos.registrarpagos(p2);

        // Población de la Cola de Trámites (FIFO)
        Tramite t1 = new Tramite(Estado.PENDIENTE, emp1, c6, Tipotramite.RENOVACION_LICENCIA);
        Tramite t2 = new Tramite(Estado.PENDIENTE, emp2, c7, Tipotramite.CAMBIO_PROPETARIO);
        Tramite t3 = new Tramite(Estado.PENDIENTE, emp3, c8, Tipotramite.DUPLICADO_MATRICULA);
        gestorTramites.registrartramite(t1);
        gestorTramites.registrartramite(t2);
        gestorTramites.registrartramite(t3);

        // Modelado del Grafo de la Red Vial (Nodos y Aristas)
        Accidente a1 = new Accidente("Av. Galo Plaza", 1, "Agencia", "Atencion vehicular");
        Accidente a2 = new Accidente("Av. Mariscal Sucre", 2, "Agencia", "Atencion vehicular");
        Accidente a3 = new Accidente("Av. Amazonas", 3, "Agencia", "Zona comercial");
        Accidente a4 = new Accidente("Autopista General", 4, "Control", "Acceso al valle");
        Accidente a5 = new Accidente("Panamericana", 5, "Control", "Ingreso norte");

        gestionRedVial.agregarAccidente(a1);
        gestionRedVial.agregarAccidente(a2);
        gestionRedVial.agregarAccidente(a3);
        gestionRedVial.agregarAccidente(a4);
        gestionRedVial.agregarAccidente(a5);

        // Creación de aristas (conexiones con peso/distancia)
        gestionRedVial.conectar(1, 3, 8);
        gestionRedVial.conectar(1, 5, 10);
        gestionRedVial.conectar(2, 3, 14);
        gestionRedVial.conectar(2, 4, 16);
        gestionRedVial.conectar(3, 4, 12);
        gestionRedVial.conectar(3, 5, 18);
    }

    // ==========================================
    // CONFIGURACIÓN DE MODELOS DE TABLA
    // ==========================================
    private void configurarTablas() {
        tblVehiculos.setModel(new DefaultTableModel(null, new String[]{"Placa", "Anio", "Cedula", "Propietario", "Deuda", "Multas"}));
        tblMultas.setModel(new DefaultTableModel(null, new String[]{"ID", "Placa", "Monto", "Motivo", "Empleado", "Fecha"}));
        tblPagos.setModel(new DefaultTableModel(null, new String[]{"ID", "Cedula", "Ciudadano", "Monto", "Fecha", "Estado", "Deuda actual"}));
        tblTramiteEncolar.setModel(new DefaultTableModel(null, new String[]{"Orden", "Cedula", "Ciudadano", "Empleado", "Tipo", "Estado"}));
        tblZonas.setModel(new DefaultTableModel(null, new String[]{"ID", "Direccion", "Tipo", "Causa"}));
        tblConexiones.setModel(new DefaultTableModel(null, new String[]{"Origen", "Destino", "Distancia km"}));
        tblRuta.setModel(new DefaultTableModel(null, new String[]{"Orden", "Zona", "Direccion"}));
    }

    // ==========================================
    // MÉTODOS DE SINCRONIZACIÓN Y REFRESCO VISUAL
    // ==========================================
    private void refrescarTodasLasTablas() {
        refrescarTablaVehiculos();
        refrescarTablaMultas();
        refrescarTablaPagos();
        refrescarTablaTramites();
        refrescarTablaZonas();
    }

    /**
     * Refresca la tabla de Vehículos utilizando Reflection para acceder a la raíz privada
     * del Árbol Binario de Búsqueda sin romper el encapsulamiento de la clase original.
     */
    private void refrescarTablaVehiculos() {
        DefaultTableModel model = (DefaultTableModel) tblVehiculos.getModel();
        model.setRowCount(0);
        try {
            Field field = RegistroVehicular.class.getDeclaredField("raiz");
            field.setAccessible(true);
            NodoVehiculo raiz = (NodoVehiculo) field.get(registroVehicular);
            recorrerArbolVehiculos(raiz, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recorrido In-Order (Izquierda -> Raíz -> Derecha) para listar los vehículos ordenados
    private void recorrerArbolVehiculos(NodoVehiculo nodo, DefaultTableModel model) {
        if (nodo != null) {
            recorrerArbolVehiculos(nodo.getIzq(), model);
            Vehiculo v = nodo.getVehiculo();
            model.addRow(new Object[]{
                    v.getPlaca(),
                    v.getAnio(),
                    v.getPropietario().getCedula(),
                    v.getPropietario().getNombre(),
                    String.format("%.2f", v.getPropietario().getDeuda()),
                    v.getTotalMultas()
            });
            recorrerArbolVehiculos(nodo.getDer(), model);
        }
    }

    private void refrescarTablaMultas() {
        DefaultTableModel model = (DefaultTableModel) tblMultas.getModel();
        model.setRowCount(0);
        try {
            Field field = HistorialMultas.class.getDeclaredField("raiz");
            field.setAccessible(true);
            Nodomulta raiz = (Nodomulta) field.get(historialMultas);
            recorrerArbolMultas(raiz, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recorrerArbolMultas(Nodomulta nodo, DefaultTableModel model) {
        if (nodo != null) {
            recorrerArbolMultas(nodo.getIzq(), model);
            Multas m = nodo.getMulta();
            model.addRow(new Object[]{
                    m.getId(),
                    m.getVehiculo().getPlaca(),
                    String.format("%.2f", m.getPrecio()),
                    m.getMotivo(),
                    m.getEmpleado().getNombre(),
                    LocalDate.now().toString() // Generación dinámica de fecha
            });
            recorrerArbolMultas(nodo.getDer(), model);
        }
    }

    /**
     * Refresca la tabla de Pagos iterando la Pila (Stack).
     * Se invierte temporalmente la estructura para garantizar la visualización LIFO pura.
     */
    private void refrescarTablaPagos() {
        DefaultTableModel model = (DefaultTableModel) tblPagos.getModel();
        model.setRowCount(0);

        Stack<Pago> temporal = new Stack<>();
        for (Pago p : pilaPagos) temporal.push(p);

        while(!temporal.isEmpty()){
            Pago p = temporal.pop();
            model.addRow(new Object[]{
                    p.getIdp(),
                    p.getCiudadano().getCedula(),
                    p.getCiudadano().getNombre(),
                    String.format("%.2f", p.getCantidad()),
                    p.getFecha().toString(),
                    "APLICADO",
                    String.format("%.2f", p.getCiudadano().getDeuda())
            });
        }
    }

    /**
     * Refresca la tabla de Trámites respetando el flujo FIFO de la Cola.
     */
    private void refrescarTablaTramites() {
        DefaultTableModel model = (DefaultTableModel) tblTramiteEncolar.getModel();
        model.setRowCount(0);
        int orden = 1;
        for (Tramite t : colaTramites) {
            model.addRow(new Object[]{
                    orden++,
                    t.getCiudadano().getCedula(),
                    t.getCiudadano().getNombre(),
                    t.getEmpleado().getNombre(),
                    t.getTipo(),
                    t.getEstado()
            });
        }
    }

    /**
     * Refresca la vista relacional de Grafos (Vértices/Zonas y Aristas/Conexiones).
     */
    private void refrescarTablaZonas() {
        DefaultTableModel modelZ = (DefaultTableModel) tblZonas.getModel();
        modelZ.setRowCount(0);
        for (Accidente a : gestionRedVial.obtenerAccidentes()) {
            String dir = a.getDireccion().length() > 14 ? a.getDireccion().substring(0, 14) + "..." : a.getDireccion();
            String causa = a.getCausa().length() > 14 ? a.getCausa().substring(0, 14) + "..." : a.getCausa();
            modelZ.addRow(new Object[]{a.getId(), dir, a.getTipo(), causa});
        }

        DefaultTableModel modelC = (DefaultTableModel) tblConexiones.getModel();
        modelC.setRowCount(0);
        for (GestionRedVial.ConexionVista cv : gestionRedVial.obtenerConexiones()) {
            modelC.addRow(new Object[]{cv.getOrigen(), cv.getDestino(), cv.getDistancia()});
        }
    }

    // ==========================================
    // ACTION LISTENERS: OPERACIONES CRUD
    // Todas las operaciones están protegidas con validación de excepciones (Try-Catch)
    // ==========================================

    private void configurarEventosVehiculos() {
        btnVehiculoRegistrar.addActionListener(e -> {
            try {
                String placa = txtVehiculoPlaca.getText().trim();
                int anio = Integer.parseInt(txtVehiculoAño.getText().trim());
                String cedula = textField1.getText().trim();
                String nombre = txtVehiculoPropietario.getText().trim();

                Ciudadano c = moduloCiudadanos.buscarCedula(cedula);
                if (c == null) {
                    c = new Ciudadano(cedula, nombre, 0);
                    moduloCiudadanos.registrarCiudadano(c);
                }

                Vehiculo v = new Vehiculo(placa, anio, c);
                registroVehicular.insertar(v);

                JOptionPane.showMessageDialog(panelPrincipal, "Vehículo registrado exitosamente.");
                refrescarTablaVehiculos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelPrincipal, "Verifique el formato del Año.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panelPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVehiculoBuscar.addActionListener(e -> {
            try {
                String placa = txtVehiculoPlaca.getText().trim();
                Vehiculo v = registroVehicular.buscar(placa);
                if (v != null) {
                    txtVehiculoAño.setText(String.valueOf(v.getAnio()));
                    textField1.setText(v.getPropietario().getCedula());
                    txtVehiculoPropietario.setText(v.getPropietario().getNombre());
                    txtVehiculoDeuda.setText(String.valueOf(v.getPropietario().getDeuda()));
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Vehículo no encontrado en el sistema.", "Atención", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panelPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVehiculoActualizar.addActionListener(e -> {
            try {
                String placa = txtVehiculoPlaca.getText().trim();
                int anio = Integer.parseInt(txtVehiculoAño.getText().trim());
                if(registroVehicular.actualizar(placa, null, anio)) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Información del vehículo actualizada.");
                    refrescarTablaVehiculos();
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Placa no encontrada.", "Atención", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, "Error en la actualización: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVehiculoEliminar.addActionListener(e -> {
            try {
                String placa = txtVehiculoPlaca.getText().trim();
                registroVehicular.eliminar(placa);
                JOptionPane.showMessageDialog(panelPrincipal, "Vehículo eliminado del sistema.");
                refrescarTablaVehiculos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVehiculoLimpiar.addActionListener(e -> {
            txtVehiculoPlaca.setText("");
            txtVehiculoAño.setText("");
            textField1.setText("");
            txtVehiculoPropietario.setText("");
            txtVehiculoDeuda.setText("");
        });
    }

    private void configurarEventosMultas() {
        btnMultaRegistrar.addActionListener(e -> {
            try {
                int idMulta = Integer.parseInt(txtMultaId.getText().trim());
                String placa = txtMultasPlacas.getText().trim();
                double precio = Double.parseDouble(txtMultaPrecio.getText().trim());
                String motivo = txtMultaMotivo.getText().trim();
                String empleadoId = txtMultaEmpleadoId.getText().trim();

                Vehiculo v = registroVehicular.buscar(placa);
                if (v == null) throw new IllegalArgumentException("El vehículo especificado no está registrado.");

                Empleado emp = moduloEmpleados.buscarCodigo(Integer.parseInt(empleadoId));
                if (emp == null) emp = new Empleado(empleadoId, txtMultaEmpleadoNombre.getText().trim());

                Multas multa = new Multas(precio, motivo, v, emp, idMulta);
                historialMultas.insertar(multa);

                // Actualiza la deuda global del ciudadano asociado al vehículo
                v.getPropietario().incrementarDeuda(precio);
                v.incrementarMultas();

                JOptionPane.showMessageDialog(panelPrincipal, "Infracción procesada correctamente.");
                refrescarTablaMultas();
                refrescarTablaVehiculos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, "Revise que los valores monetarios e identificadores sean numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnMultaBuscar.addActionListener(e -> {
            try {
                int idMulta = Integer.parseInt(txtMultaId.getText().trim());
                Multas m = historialMultas.buscar(idMulta);
                if(m != null){
                    txtMultasPlacas.setText(m.getVehiculo().getPlaca());
                    txtMultaPrecio.setText(String.valueOf(m.getPrecio()));
                    txtMultaMotivo.setText(m.getMotivo());
                    txtMultaEmpleadoId.setText(m.getEmpleado().getId());
                    txtMultaEmpleadoNombre.setText(m.getEmpleado().getNombre());
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Infracción no encontrada.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, "Identificador de multa inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnMultaActualizar.addActionListener(e -> {
            try {
                int idMulta = Integer.parseInt(txtMultaId.getText().trim());
                double precio = Double.parseDouble(txtMultaPrecio.getText().trim());
                String motivo = txtMultaMotivo.getText().trim();

                if (historialMultas.actualizar(idMulta, precio, motivo, true, true)) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Datos de la infracción actualizados.");
                    refrescarTablaMultas();
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "No se ejecutó la actualización.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, "Verifique el formato de los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnMultaEliminar.addActionListener(e -> {
            try {
                int idMulta = Integer.parseInt(txtMultaId.getText().trim());
                historialMultas.eliminar(idMulta);
                JOptionPane.showMessageDialog(panelPrincipal, "Infracción dada de baja exitosamente.");
                refrescarTablaMultas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, "Identificador inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnMultaLimpiar.addActionListener(e -> {
            txtMultaId.setText(""); txtMultasPlacas.setText(""); txtMultaPrecio.setText("");
            txtMultaMotivo.setText(""); txtMultaEmpleadoId.setText(""); txtMultaEmpleadoNombre.setText("");
        });
    }

    private void configurarEventosPagos() {
        btnPagoRegistrar.addActionListener(e -> {
            try {
                int idPago = pilaPagos.size() + 1;
                String cedula = txtPagoCedula.getText().trim();
                double monto = Double.parseDouble(txtPagoMonto.getText().trim());

                Ciudadano c = moduloCiudadanos.buscarCedula(cedula);
                if (c == null) throw new IllegalArgumentException("Ciudadano no encontrado en la base de datos.");

                Pago p = new Pago(idPago, monto, c);
                if (gestorPagos.registrarpagos(p)) {
                    gestorPagos.estadodeuda();
                    JOptionPane.showMessageDialog(panelPrincipal, "Transacción aprobada y apilada en el sistema.");
                    refrescarTablaPagos();
                    refrescarTablaVehiculos();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, ex.getMessage(), "Error en el Pago", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPagoVerUltimo.addActionListener(e -> {
            if(!pilaPagos.isEmpty()) {
                Pago p = gestorPagos.consulta();
                txtPagoCedula.setText(p.getCiudadano().getCedula());
                txtPagoNombre.setText(p.getCiudadano().getNombre());
                txtPagoDeuda.setText(String.valueOf(p.getCiudadano().getDeuda()));
                txtPagoMonto.setText(String.valueOf(p.getCantidad()));
            } else {
                JOptionPane.showMessageDialog(panelPrincipal, "El historial de pagos está vacío.");
            }
        });

        btnPagoAnular.addActionListener(e -> {
            if(!pilaPagos.isEmpty()) {
                gestorPagos.anularpago(); // Remueve (Pop) de la pila y restaura la deuda
                JOptionPane.showMessageDialog(panelPrincipal, "Reversión exitosa: Última transacción anulada.");
                refrescarTablaPagos();
                refrescarTablaVehiculos();
            } else {
                JOptionPane.showMessageDialog(panelPrincipal, "No existen transacciones para anular.");
            }
        });

        btnPagoLimpiar.addActionListener(e -> {
            txtPagoCedula.setText(""); txtPagoNombre.setText(""); txtPagoDeuda.setText(""); txtPagoMonto.setText("");
        });
    }

    private void configurarEventosTramites() {
        btnTramiteEncolar.addActionListener(e -> {
            try {
                String cedula = txtTramitesCedula.getText().trim();
                String empleadoId = txtTramiteEmpleadoId.getText().trim();

                Ciudadano c = moduloCiudadanos.buscarCedula(cedula);
                if (c == null) throw new IllegalArgumentException("Ciudadano no encontrado.");

                Empleado emp = moduloEmpleados.buscarCodigo(Integer.parseInt(empleadoId));
                if (emp == null) emp = new Empleado(empleadoId, "Empleado Generico");

                String tipoStr = cmbTramiteTipo.getSelectedItem() != null ? cmbTramiteTipo.getSelectedItem().toString() : "RENOVACION_LICENCIA";

                if (gestorTramites.registrartramite(new Tramite(Estado.PENDIENTE, emp, c, Tipotramite.valueOf(tipoStr)))) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Turno encolado correctamente.");
                    refrescarTablaTramites();
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "El usuario ya posee un trámite activo del mismo tipo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTramiteIniciar.addActionListener(e -> {
            if (!colaTramites.isEmpty()) {
                gestorTramites.estadotramite(); // Cambia el estado del frente (Peek) a ENPROCESO
                refrescarTablaTramites();
            }
        });

        btnTramiteFinalizar.addActionListener(e -> {
            if (!colaTramites.isEmpty()) {
                gestorTramites.finalizartramite(); // Desencola (Poll) y finaliza
                refrescarTablaTramites();
            }
        });

        btnTramiteCancelar.addActionListener(e -> {
            Tramite t = colaTramites.poll(); // Extracción forzada del frente
            if (t != null) {
                t.setEstado(Estado.CANCELADO);
                refrescarTablaTramites();
            }
        });

        btnTramiteLimpiar.addActionListener(e -> {
            txtTramitesCedula.setText(""); txtTramiteNombre.setText(""); txtTramiteEmpleadoId.setText(""); txtTramiteEmpleadoNombre.setText("");
        });
    }

    private void configurarEventosZonas() {
        btnZonaRegistrar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtZonaId.getText().trim());
                if (gestionRedVial.agregarAccidente(new Accidente(txtZonaDireccion.getText().trim(), id, txtZonaTipo.getText().trim(), txtZonaCausa.getText().trim()))) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Punto de control/accidente mapeado exitosamente.");
                    refrescarTablaZonas();
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "El ID proporcionado ya existe en la red.", "Error de Nodo", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnZonaActualizar.addActionListener(e -> {
            try {
                if (gestionRedVial.actualizarAccidente(Integer.parseInt(txtZonaId.getText().trim()), txtZonaDireccion.getText().trim(), txtZonaTipo.getText().trim(), txtZonaCausa.getText().trim())) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Información de la zona actualizada.");
                    refrescarTablaZonas();
                }
            } catch (Exception ex) {}
        });

        btnZonaEliminar.addActionListener(e -> {
            try {
                if (gestionRedVial.eliminarAccidente(Integer.parseInt(txtZonaId.getText().trim()))) refrescarTablaZonas();
            } catch (Exception ex) {}
        });

        btnZonaConectar.addActionListener(e -> {
            try {
                if (gestionRedVial.conectar(Integer.parseInt(txtZonaOrigen.getText().trim()), Integer.parseInt(txtZonaDestino.getText().trim()), Integer.parseInt(textField2.getText().trim()))) {
                    refrescarTablaZonas();
                }
            } catch (Exception ex) {}
        });

        btnZonaActualizarConexion.addActionListener(e -> {
            try {
                if (gestionRedVial.cambiarPesoArista(Integer.parseInt(txtZonaOrigen.getText().trim()), Integer.parseInt(txtZonaDestino.getText().trim()), Integer.parseInt(textField2.getText().trim()))) {
                    refrescarTablaZonas();
                }
            } catch (Exception ex) {}
        });

        btnZonaEliminarConexion.addActionListener(e -> {
            try {
                if (gestionRedVial.eliminarConexion(Integer.parseInt(txtZonaOrigen.getText().trim()), Integer.parseInt(txtZonaDestino.getText().trim()))) {
                    refrescarTablaZonas();
                }
            } catch (Exception ex) {}
        });

        btnZonaRuta.addActionListener(e -> {
            try {
                // Cálculo de la ruta más corta utilizando el algoritmo de Dijkstra implementado en GestiónRedVial
                String ruta = gestionRedVial.encontrarRutaMasCorta(Integer.parseInt(txtZonaOrigen.getText().trim()), Integer.parseInt(txtZonaDestino.getText().trim()));
                JOptionPane.showMessageDialog(panelPrincipal, ruta, "Ruta Más Corta (Dijkstra)", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {}
        });

        btnZonaLimpiar.addActionListener(e -> {
            txtZonaId.setText(""); txtZonaDireccion.setText(""); txtZonaCausa.setText(""); txtZonaTipo.setText(""); txtZonaOrigen.setText(""); txtZonaDestino.setText(""); textField2.setText("");
        });
    }

    // ==========================================
    // PUNTO DE ENTRADA PRINCIPAL (MAIN)
    // ==========================================

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaGestor");
        frame.setContentPane(new VentanaGestor().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}