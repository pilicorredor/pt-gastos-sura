package com.sura.gastos_viajes_api.model.response;

import java.util.List;

public class EmpleadoResumen {
    private final int id;
    private final String nombre;
    private final double totalEmpleado;
    private final List<DetalleMensual> detalleMensual;

    public EmpleadoResumen(int id, String nombre, double totalEmpleado, List<DetalleMensual> detalleMensual) {
        this.id = id;
        this.nombre = nombre;
        this.totalEmpleado = totalEmpleado;
        this.detalleMensual = detalleMensual;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getTotalEmpleado() { return totalEmpleado; }
    public List<DetalleMensual> getDetalleMensual() { return detalleMensual; }
}
