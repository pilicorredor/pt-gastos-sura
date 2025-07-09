package com.sura.gastos_viajes_api.model.response;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EmpleadoResumenTest {

    @Test
    void constructorYGetters_RetornarValoresCorrectos() {
        DetalleMensual detalle1 = new DetalleMensual("01", 1000000.0, 1190000.0, "Empleado");
        DetalleMensual detalle2 = new DetalleMensual("02", 200000.0, 238000.0, "SURA");
        List<DetalleMensual> detalles = List.of(detalle1, detalle2);

        int id = 1;
        String nombre = "Juan";
        double totalEmpleado = 1200000.0;
        double totalEmpleadoConIva = 1428000.0;

        EmpleadoResumen resumen = new EmpleadoResumen(id, nombre, totalEmpleado, totalEmpleadoConIva, detalles);

        assertEquals(id, resumen.getId());
        assertEquals(nombre, resumen.getNombre());
        assertEquals(totalEmpleado, resumen.getTotalEmpleado());
        assertEquals(totalEmpleadoConIva, resumen.getTotalEmpleadoConIva());
        assertEquals(detalles, resumen.getDetalleMensual());
        assertEquals(2, resumen.getDetalleMensual().size());
        assertEquals("02", resumen.getDetalleMensual().get(1).getMes());
    }

    @Test
    void aceptarListaDetalleMensualVacia() {
        List<DetalleMensual> detalles = List.of();

        EmpleadoResumen resumen = new EmpleadoResumen(2, "Ana", 0.0, 0.0, detalles);

        assertEquals(2, resumen.getId());
        assertEquals("Ana", resumen.getNombre());
        assertEquals(0.0, resumen.getTotalEmpleado());
        assertEquals(0.0, resumen.getTotalEmpleadoConIva());
        assertTrue(resumen.getDetalleMensual().isEmpty());
    }

    @Test
    void manejarListaDetalleMensualNula() {
        EmpleadoResumen resumen = new EmpleadoResumen(3, "Carlos", 500.0, 595.0, null);

        assertEquals(3, resumen.getId());
        assertEquals("Carlos", resumen.getNombre());
        assertEquals(500.0, resumen.getTotalEmpleado());
        assertEquals(595.0, resumen.getTotalEmpleadoConIva());
        assertNull(resumen.getDetalleMensual());
    }
}
