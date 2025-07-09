package com.sura.gastos_viajes_api.model.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcesamientoGastosResponseTest {

    @Test
    void deberiaCrearRespoonseCorrecto() {
        BigDecimal totalGeneral = new BigDecimal("1000.00");
        BigDecimal totalConIva = new BigDecimal("1190.00");
        List<EmpleadoResumen> empleados = List.of();

        ProcesamientoGastosResponse response = new ProcesamientoGastosResponse(totalGeneral, totalConIva, empleados);

        assertEquals(totalGeneral, response.getTotalGeneral());
        assertEquals(totalConIva, response.getTotalGeneralConIva());
        assertEquals(empleados, response.getEmpleados());
    }
}
