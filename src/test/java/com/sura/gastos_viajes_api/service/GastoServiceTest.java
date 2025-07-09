package com.sura.gastos_viajes_api.service;

import com.sura.gastos_viajes_api.model.gasto.Gasto;
import com.sura.gastos_viajes_api.model.response.DetalleMensual;
import com.sura.gastos_viajes_api.model.response.EmpleadoResumen;
import com.sura.gastos_viajes_api.model.response.ProcesamientoGastosResponse;
import com.sura.gastos_viajes_api.repository.GastoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GastoServiceTest {

    private GastoRepository gastoRepository;
    private GastoService gastoService;

    @BeforeEach
    void setUp() {
        gastoRepository = mock(GastoRepository.class);
        gastoService = new GastoService(gastoRepository);
    }

    @Test
    void procesarGastos_deberiaProcesarCorrectamenteGastos() {
        List<Gasto> gastos = List.of(
                new Gasto(1, "Juan", LocalDate.of(2025, 7, 5), 500_000),
                new Gasto(1, "Juan", LocalDate.of(2025, 7, 15), 300_000),
                new Gasto(2, "Ana", LocalDate.of(2025, 6, 20), 1_200_000)
        );
        when(gastoRepository.obtenerGastos()).thenReturn(gastos);

        ProcesamientoGastosResponse response = gastoService.procesarGastos();

        assertNotNull(response);
        assertEquals(2, response.getEmpleados().size());

        EmpleadoResumen juan = response.getEmpleados().stream()
                .filter(e -> e.getNombre().equals("Juan")).findFirst().orElseThrow();

        assertEquals(800_000, juan.getTotalEmpleado());
        assertEquals(1, juan.getDetalleMensual().size());

        DetalleMensual detalleJuan = juan.getDetalleMensual().get(0);
        assertEquals("07", detalleJuan.getMes());
        assertEquals(800_000, detalleJuan.getTotal());
        assertEquals("SURA", detalleJuan.getAsumidoPor());

        EmpleadoResumen ana = response.getEmpleados().stream()
                .filter(e -> e.getNombre().equals("Ana")).findFirst().orElseThrow();

        assertEquals(1_200_000, ana.getTotalEmpleado());
        assertEquals(1, ana.getDetalleMensual().size());

        DetalleMensual detalleAna = ana.getDetalleMensual().get(0);
        assertEquals("06", detalleAna.getMes());
        assertEquals("Empleado", detalleAna.getAsumidoPor());

        assertEquals(2_000_000, response.getTotalGeneral().doubleValue());
        assertEquals(2_380_000, response.getTotalGeneralConIva().doubleValue(), 0.01);
    }

    @Test
    void procesarGastos_conListaVaciaDebeRetornarTotalesEnCero() {
        when(gastoRepository.obtenerGastos()).thenReturn(List.of());

        ProcesamientoGastosResponse response = gastoService.procesarGastos();

        assertNotNull(response);
        assertEquals(0, response.getEmpleados().size());
        assertEquals(0.0, response.getTotalGeneral().doubleValue());
        assertEquals(0.0, response.getTotalGeneralConIva().doubleValue());
    }
}
