package com.sura.gastos_viajes_api.service;

import com.sura.gastos_viajes_api.model.gasto.Gasto;
import com.sura.gastos_viajes_api.model.response.DetalleMensual;
import com.sura.gastos_viajes_api.model.response.EmpleadoResumen;
import com.sura.gastos_viajes_api.model.response.ProcesamientoGastosResponse;
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

    public ProcesamientoGastosResponse procesarGastos() {
        List<Gasto> gastos = gastoRepository.obtenerGastos();
        Map<String, Map<String, Double>> porEmpleadoMes = new HashMap<>();
        Map<String, Integer> nombreToId = new HashMap<>();
        agruparGastosPorEmpleado(gastos, porEmpleadoMes, nombreToId);
        List<EmpleadoResumen> empleados = generarResumenEmpleados(porEmpleadoMes, nombreToId);
        double totalGeneral = calcularTotalGeneral(empleados);

        return new ProcesamientoGastosResponse(totalGeneral, empleados);
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

    private List<EmpleadoResumen> generarResumenEmpleados(Map<String, Map<String, Double>> porEmpleadoMes,
                                                          Map<String, Integer> nombreToId) {
        List<EmpleadoResumen> listaEmpleados = new ArrayList<>();
        List<String> nombresOrdenados = new ArrayList<>(porEmpleadoMes.keySet());
        Collections.sort(nombresOrdenados);

        for (String nombre : nombresOrdenados) {
            int id = nombreToId.get(nombre);
            Map<String, Double> gastosMes = porEmpleadoMes.get(nombre);
            List<DetalleMensual> detalle = new ArrayList<>();
            double totalEmpleado = 0;

            for (Map.Entry<String, Double> mesEntry : gastosMes.entrySet()) {
                double total = mesEntry.getValue();
                double conIva = total * (1 + IVA_RATE);
                String asumidoPor = conIva > LIMITE ? "Empleado" : "SURA";
                totalEmpleado += total;

                detalle.add(new DetalleMensual(mesEntry.getKey(), total, conIva, asumidoPor));
            }
            listaEmpleados.add(new EmpleadoResumen(id, nombre, totalEmpleado, detalle));
        }
        return listaEmpleados;
    }

    private double calcularTotalGeneral(List<EmpleadoResumen> empleados) {
        return empleados.stream()
                .mapToDouble(EmpleadoResumen::getTotalEmpleado)
                .sum();
    }
}
