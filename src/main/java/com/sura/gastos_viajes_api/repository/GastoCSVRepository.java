package com.sura.gastos_viajes_api.repository;

import com.sura.gastos_viajes_api.model.gasto.Gasto;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GastoCSVRepository implements GastoRepository {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");

    @Override
    public List<Gasto> obtenerGastos() {
        List<Gasto> gastos = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("persistencia/gastos.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String linea;
            reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                LocalDate fecha = LocalDate.parse(partes[2], formatter);
                double monto = Double.parseDouble(partes[3]);
                gastos.add(new Gasto(id, nombre, fecha, monto));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error leyendo archivo CSV de gastos", e);
        }

        return gastos;
    }
}
