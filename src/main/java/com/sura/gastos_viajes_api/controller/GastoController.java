package com.sura.gastos_viajes_api.controller;

import com.sura.gastos_viajes_api.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {
    @Autowired
    private GastoService gastoService;

    @GetMapping
    public Map<String, Object> obtenerGastos() {
        return gastoService.procesarGastos();
    }
}
