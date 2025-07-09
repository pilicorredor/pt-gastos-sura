package com.sura.gastos_viajes_api.model.response;

import java.math.BigDecimal;
import java.util.List;

public class ProcesamientoGastosResponse {
    private final BigDecimal totalGeneral;
    private final BigDecimal totalGeneralConIva;
    private final List<EmpleadoResumen> empleados;

    public ProcesamientoGastosResponse(BigDecimal totalGeneral, BigDecimal totalGeneralConIva, List<EmpleadoResumen> empleados) {
        this.totalGeneral = totalGeneral;
        this.totalGeneralConIva = totalGeneralConIva;
        this.empleados = empleados;
    }

    public BigDecimal getTotalGeneral() { return totalGeneral; }
    public BigDecimal getTotalGeneralConIva() { return totalGeneralConIva; }
    public List<EmpleadoResumen> getEmpleados() { return empleados; }
}
