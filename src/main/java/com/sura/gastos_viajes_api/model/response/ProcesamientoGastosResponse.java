package com.sura.gastos_viajes_api.model.response;

import java.util.List;

public class ProcesamientoGastosResponse {
    private final double totalGeneral;
    private final List<EmpleadoResumen> empleados;

    public ProcesamientoGastosResponse(double totalGeneral, List<EmpleadoResumen> empleados) {
        this.totalGeneral = totalGeneral;
        this.empleados = empleados;
    }

    public double getTotalGeneral() { return totalGeneral; }
    public List<EmpleadoResumen> getEmpleados() { return empleados; }
}
