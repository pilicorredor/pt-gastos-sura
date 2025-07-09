package com.sura.gastos_viajes_api.model.response;

public class DetalleMensual {
    private final String mes;
    private final double total;
    private final double totalConIva;
    private final String asumidoPor;

    public DetalleMensual(String mes, double total, double totalConIva, String asumidoPor) {
        this.mes = mes;
        this.total = total;
        this.totalConIva = totalConIva;
        this.asumidoPor = asumidoPor;
    }

    public String getMes() { return mes; }
    public double getTotal() { return total; }
    public double getTotalConIva() { return totalConIva; }
    public String getAsumidoPor() { return asumidoPor; }
}
