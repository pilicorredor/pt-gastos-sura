package com.sura.gastos_viajes_api.controller;

import com.sura.gastos_viajes_api.model.response.DetalleMensual;
import com.sura.gastos_viajes_api.model.response.EmpleadoResumen;
import com.sura.gastos_viajes_api.model.response.ProcesamientoGastosResponse;
import com.sura.gastos_viajes_api.service.GastoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GastoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public GastoService gastoService() {
            return mock(GastoService.class);
        }
    }

    @Autowired
    private GastoService gastoService;

    @Test
    void procesar_deberiaRetornarResponseConHttp200() throws Exception {
        DetalleMensual detalle = new DetalleMensual("07", 1000.0, 1190.0, "SURA");
        EmpleadoResumen resumen = new EmpleadoResumen(1, "Juan", 1000.0, 1190.0, List.of(detalle));
        ProcesamientoGastosResponse response = new ProcesamientoGastosResponse(
                BigDecimal.valueOf(1000.0),
                BigDecimal.valueOf(1190.0),
                List.of(resumen)
        );

        when(gastoService.procesarGastos()).thenReturn(response);

        mockMvc.perform(get("/api/obtener-gastos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalGeneral").value(1000.0))
                .andExpect(jsonPath("$.totalGeneralConIva").value(1190.0))
                .andExpect(jsonPath("$.empleados[0].nombre").value("Juan"));
    }
}
