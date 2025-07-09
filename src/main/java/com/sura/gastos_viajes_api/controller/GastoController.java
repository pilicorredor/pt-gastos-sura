package com.sura.gastos_viajes_api.controller;

import com.sura.gastos_viajes_api.model.response.ProcesamientoGastosResponse;
import com.sura.gastos_viajes_api.service.GastoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GastoController {
    private final GastoService service;

    public GastoController(GastoService service) {
        this.service = service;
    }

    @GetMapping("/obtener-gastos")
    public ProcesamientoGastosResponse procesar() {
        return service.procesarGastos();
    }
}
