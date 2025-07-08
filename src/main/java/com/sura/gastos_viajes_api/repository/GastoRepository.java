package com.sura.gastos_viajes_api.repository;

import com.sura.gastos_viajes_api.model.Gasto;

import java.util.List;

public interface GastoRepository {
    List<Gasto> obtenerGastos();
}