package com.sura.gastos_viajes_api.service;

import com.sura.gastos_viajes_api.model.Gasto;
import com.sura.gastos_viajes_api.repository.GastoRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;
    private static final double IVA_RATE = 0.19;
    private static final double LIMITE = 1_000_000;

    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public Map<String, Object> procesarGastos() {
        List<Gasto> gastos = gastoRepository.obtenerGastos();

        Map<String, Map<String, Double>> porEmpleadoMes = new HashMap<>();
        Map<String, Integer> nombreToId = new HashMap<>();

        agruparGastosPorEmpleado(gastos, porEmpleadoMes, nombreToId);

        List<Map<String, Object>> empleados = generarResumenEmpleados(porEmpleadoMes, nombreToId);

        double totalGeneral = calcularTotalGeneral(empleados);

        return Map.of("total_general", totalGeneral, "empleados", empleados);
    }

    private void agruparGastosPorEmpleado(List<Gasto> gastos,
                                          Map<String, Map<String, Double>> porEmpleadoMes,
                                          Map<String, Integer> nombreToId) {
        for (Gasto g : gastos) {
            String nombre = g.getNombre();
            String mes = String.format("%02d", g.getFecha().getMonthValue());
            double monto = g.getMonto();

            if (!porEmpleadoMes.containsKey(nombre)) {
                porEmpleadoMes.put(nombre, new HashMap<>());
            }

            Map<String, Double> gastosPorMes = porEmpleadoMes.get(nombre);

            double montoActual = gastosPorMes.getOrDefault(mes, 0.0);
            gastosPorMes.put(mes, montoActual + monto);

            nombreToId.put(nombre, g.getId());
        }
    }

    private List<Map<String, Object>> generarResumenEmpleados(Map<String, Map<String, Double>> porEmpleadoMes,
                                                              Map<String, Integer> nombreToId) {
        List<Map<String, Object>> empleados = new ArrayList<>();
        List<String> nombresOrdenados = new ArrayList<>(porEmpleadoMes.keySet());
        Collections.sort(nombresOrdenados);

        for (String nombre : nombresOrdenados) {
            int id = nombreToId.get(nombre);
            Map<String, Double> gastosMes = porEmpleadoMes.get(nombre);

            List<Map<String, Object>> detalle = new ArrayList<>();
            double totalEmpleado = 0;

            for (Map.Entry<String, Double> mesEntry : gastosMes.entrySet()) {
                double total = mesEntry.getValue();
                double conIva = total * (1 + IVA_RATE);
                String asumidoPor = conIva > LIMITE ? "Empleado" : "SURA";
                totalEmpleado += total;

                detalle.add(Map.of(
                        "mes", mesEntry.getKey(),
                        "total", total,
                        "total_con_iva", Math.round(conIva),
                        "asumido_por", asumidoPor
                ));
            }

            Map<String, Object> resumenEmpleado = Map.of(
                    "id", id,
                    "nombre", nombre,
                    "total_empleado", totalEmpleado,
                    "detalle_mensual", detalle
            );

            empleados.add(resumenEmpleado);
        }
        return empleados;
    }

    private double calcularTotalGeneral(List<Map<String, Object>> empleados) {
        return empleados.stream()
                .mapToDouble(e -> (double) e.get("total_empleado"))
                .sum();
    }
}
