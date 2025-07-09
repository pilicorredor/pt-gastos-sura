package com.sura.gastos_viajes_api.model.gasto;
import java.time.LocalDate;

public class Gasto {
    private final int id;
    private final String nombre;
    private final LocalDate fecha;
    private final double monto;

    public Gasto(int id, String nombre, LocalDate fecha, double monto) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.monto = monto;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFecha() { return fecha; }
    public double getMonto() { return monto; }
}
