package com.sura.gastos_viajes_api.model.response;

import java.util.List;

public class EmpleadoResumen {
    private final int id;
    private final String nombre;
    private final double totalEmpleado;
    private final double totalEmpleadoConIva;
    private final List<DetalleMensual> detalleMensual;

    public EmpleadoResumen(int id, String nombre, double totalEmpleado, double totalEmpleadoConIva, List<DetalleMensual> detalleMensual) {
        this.id = id;
        this.nombre = nombre;
        this.totalEmpleado = totalEmpleado;
        this.totalEmpleadoConIva = totalEmpleadoConIva;
        this.detalleMensual = detalleMensual;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getTotalEmpleado() { return totalEmpleado; }
    public double getTotalEmpleadoConIva() { return totalEmpleadoConIva; }
    public List<DetalleMensual> getDetalleMensual() { return detalleMensual; }
}
