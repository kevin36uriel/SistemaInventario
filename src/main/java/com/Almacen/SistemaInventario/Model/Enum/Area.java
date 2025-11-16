package com.Almacen.SistemaInventario.Model.Enum;

public enum Area {
    SALACOMPUTO(Carrera.ISC),
    ACADEMIA(Carrera.ISC),
    AREAREDES(Carrera.ISC),
    AREA1(Carrera.IA),
    AREA2(Carrera.IA),
    AREA3(Carrera.IA),
    AREA4(Carrera.II),
    AREA5(Carrera.II),
    AREA6(Carrera.II),
    AREA7(Carrera.IGE),
    AREA8(Carrera.IGE),
    AREA9(Carrera.IGE),
    AREA10(Carrera.ALMACEN),
    AREA11(Carrera.ALMACEN),
    AREA12(Carrera.ALMACEN);

    private final Carrera carrera;

    Area(Carrera carrera) {
        this.carrera = carrera;
    }

    public Carrera getCarrera() {
        return carrera;
    }
}