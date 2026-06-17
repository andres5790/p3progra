import java.util.ArrayList;
public class Datosatm {
    private ArrayList<Ciudadano> ciudadanos;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Empleado> empleados;

    public void Subirdatos() {
        ciudadanos = new ArrayList<>();
        vehiculos = new ArrayList<>();
        empleados = new ArrayList<>();

        cargarDatos();
    }

    public  void cargarDatos(){
        Ciudadano c1 = new Ciudadano(17258537, "Carlos Perez", 40.00);
        Ciudadano c2 = new Ciudadano(17258538, "Ana Rodriguez", 25.00);
        Ciudadano c3 = new Ciudadano(17258539, "Luis Martinez", 15.50);
        Ciudadano c4 = new Ciudadano(17258540, "Maria Gonzalez", 60.00);
        Ciudadano c5 = new Ciudadano(17258541, "Jorge Ramirez", 10.00);
        Ciudadano c6 = new Ciudadano(17258542, "Sofia Herrera", 75.25);
        Ciudadano c7 = new Ciudadano(17258543, "Diego Torres", 0.00);
        Ciudadano c8 = new Ciudadano(17258544, "Valentina Castro", 32.75);
        Ciudadano c9 = new Ciudadano(17258545, "Andres Morales", 18.00);
        Ciudadano c10 = new Ciudadano(17258546, "Camila Vega", 50.50);

        Empleado e1= new Empleado("A0012","Andres Peralta");
        Empleado e2 = new Empleado("A0013", "Carla Mendoza");
        Empleado e3 = new Empleado("A0014", "Miguel Herrera");

        Vehiculo v1 = new Vehiculo("PBC4578", 2023, c1, 0);
        Vehiculo v2 = new Vehiculo("ABC1234", 2020, c2, 1);
        Vehiculo v3 = new Vehiculo("QWE5678", 2021, c3, 0);
        Vehiculo v4 = new Vehiculo("XYZ9012", 2019, c4, 2);
        Vehiculo v5 = new Vehiculo("MNO3456", 2022, c5, 0);
        Vehiculo v6 = new Vehiculo("JKL7890", 2018, c6, 3);
        Vehiculo v7 = new Vehiculo("DEF2345", 2024, c7, 0);
        Vehiculo v8 = new Vehiculo("GHI6789", 2021, c8, 1);
        Vehiculo v9 = new Vehiculo("RST1122", 2020, c9, 0);
        Vehiculo v10 = new Vehiculo("UVW3344", 2023, c10, 2);

// Vehículos adicionales con propietarios repetidos
        Vehiculo v11 = new Vehiculo("LMN5566", 2022, c1, 0);   // Carlos tiene 2 vehículos
        Vehiculo v12 = new Vehiculo("OPQ7788", 2021, c3, 1);   // Luis tiene 2 vehículos
        Vehiculo v13 = new Vehiculo("HIJ9900", 2024, c6, 0);   // Sofia tiene 2 vehículos
        Vehiculo v14 = new Vehiculo("BCD2244", 2019, c1, 2);   // Carlos tiene 3 vehículos

        vehiculos.addAll(
                java.util.Arrays.asList(
                        v1, v2, v3, v4, v5, v6, v7,
                        v8, v9, v10, v11, v12, v13, v14
                )
        );

        ciudadanos.addAll(
                java.util.Arrays.asList(
                        c1, c2, c3, c4, c5,
                        c6, c7, c8, c9, c10
                )
        );

        empleados.addAll(
                java.util.Arrays.asList(
                        e1, e2, e3
                )
        );

    }

}
