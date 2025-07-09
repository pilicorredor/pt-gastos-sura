package com.sura.gastos_viajes_api.model.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DetalleMensualTest {

    @Test
    void constructorYGetters_RetornarValoresCorrectos() {
        String mes = "07";
        double total = 1000.0;
        double totalConIva = 1190.0;
        String asumidoPor = "SURA";

        DetalleMensual detalle = new DetalleMensual(mes, total, totalConIva, asumidoPor);

        assertEquals(mes, detalle.getMes());
        assertEquals(total, detalle.getTotal());
        assertEquals(totalConIva, detalle.getTotalConIva());
        assertEquals(asumidoPor, detalle.getAsumidoPor());
    }

    @Test
    void manejarValoresEnCeroYNegativos() {
        String mes = "01";
        double total = 0.0;
        double totalConIva = -100.0;
        String asumidoPor = "Empleado";

        DetalleMensual detalle = new DetalleMensual(mes, total, totalConIva, asumidoPor);

        assertEquals(0.0, detalle.getTotal());
        assertEquals(-100.0, detalle.getTotalConIva());
        assertEquals("Empleado", detalle.getAsumidoPor());
    }

    @Test
    void aceptarValoresNulos() {
        DetalleMensual detalle = new DetalleMensual(null, 500.0, 595.0, null);

        assertNull(detalle.getMes());
        assertEquals(500.0, detalle.getTotal());
        assertEquals(595.0, detalle.getTotalConIva());
        assertNull(detalle.getAsumidoPor());
    }
}
