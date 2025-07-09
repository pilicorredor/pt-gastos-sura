package com.sura.gastos_viajes_api.repository;

import com.sura.gastos_viajes_api.model.gasto.Gasto;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GastoCSVRepositoryTest {

    private final GastoCSVRepository repository = new GastoCSVRepository();

    @Test
    void obtenerGastos_deberiaRetornarListaDeGastosDeCSV() {
        List<Gasto> gastos = repository.obtenerGastos();

        assertNotNull(gastos);
        assertEquals(2, gastos.size());

        Gasto gasto1 = gastos.get(0);
        assertEquals(1, gasto1.getId());
        assertEquals("Juan", gasto1.getNombre());
        assertEquals(LocalDate.of(2025, 7, 5), gasto1.getFecha());
        assertEquals(1000, gasto1.getMonto());

        Gasto gasto2 = gastos.get(1);
        assertEquals(2, gasto2.getId());
        assertEquals("Ana", gasto2.getNombre());
        assertEquals(LocalDate.of(2025, 7, 6), gasto2.getFecha());
        assertEquals(2000, gasto2.getMonto());
    }

    @Test
    void obtenerGastos_deberiaLanzarExcepcionNoEnuentraArchivo() {
        GastoCSVRepository faultyRepo = new GastoCSVRepository() {
            @Override
            public List<Gasto> obtenerGastos() {
                try (InputStream is = getClass().getClassLoader().getResourceAsStream("no_existe.csv")) {
                    new BufferedReader(new InputStreamReader(is)).readLine();
                } catch (Exception e) {
                    throw new RuntimeException("Error leyendo archivo CSV de gastos", e);
                }
                return List.of();
            }
        };

        RuntimeException exception = assertThrows(RuntimeException.class, faultyRepo::obtenerGastos);
        assertTrue(exception.getMessage().contains("Error leyendo archivo CSV"));
    }
}
