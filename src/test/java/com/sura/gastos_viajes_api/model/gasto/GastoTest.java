package com.sura.gastos_viajes_api.model.gasto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GastoTest {

    @Test
    void constructorYGetters_RetornarValoresCorrectos() {
        int id = 1;
        String nombre = "Carlos";
        LocalDate fecha = LocalDate.of(2025, 7, 8);
        double monto = 150.75;

        Gasto gasto = new Gasto(id, nombre, fecha, monto);

        assertEquals(id, gasto.getId());
        assertEquals(nombre, gasto.getNombre());
        assertEquals(fecha, gasto.getFecha());
        assertEquals(monto, gasto.getMonto());
    }
}
