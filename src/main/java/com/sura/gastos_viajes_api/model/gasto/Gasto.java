package com.sura.gastos_viajes_api.model.gasto;
import java.time.LocalDate;

public class Gasto {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private double monto;

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
