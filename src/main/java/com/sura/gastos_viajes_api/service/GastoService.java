package com.sura.gastos_viajes_api.service;

import com.sura.gastos_viajes_api.model.Gasto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GastoService {

    private static final double IVA_RATE = 0.19;
    private static final double LIMITE = 1_000_000;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");

    private List<Gasto> cargarGastos() {
        return List.of(
                new Gasto(1, "Adam", parse("1/01/21"), 2_000_000),
                new Gasto(1, "Adam", parse("2/01/21"), 1_000_000),
                new Gasto(2, "Bolton", parse("1/01/21"), 400_000),
                new Gasto(3, "Chelsea", parse("2/01/21"), 900_000),
                new Gasto(2, "Bolton", parse("3/02/21"), 1_100_000),
                new Gasto(6, "Warden", parse("2/01/21"), 5_100_000),
                new Gasto(4, "Elsy", parse("2/01/21"), 4_000_000),
                new Gasto(5, "Vincent", parse("3/02/21"), 899_999),
                new Gasto(3, "Chelsea", parse("2/01/21"), 59_999),
                new Gasto(1, "Adam", parse("3/02/21"), 500_000),
                new Gasto(2, "Bolton", parse("2/01/21"), 500_000),
                new Gasto(6, "Warden", parse("3/02/21"), 1_100_000),
                new Gasto(3, "Chelsea", parse("3/02/21"), 1_100_000)
        );
    }

    private LocalDate parse(String fecha) {
        return LocalDate.parse(fecha, formatter);
    }

    public Map<String, Object> procesarGastos() {
        List<Gasto> gastos = cargarGastos();

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
