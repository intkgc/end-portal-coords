package com.jvmfrog.endportalcoords.util;

import android.content.Context;

public class NetherCoordsCalculator {
    public static void overworldSet(Context context, String coordinate, double val) {
        setField(context, "nether_" + coordinate, val / 8);
    }

    public static double overworldSet(double val) {
        return val / 8;
    }

    public static void netherSet(Context context, String coordinate, double val) {
        setField(context, "overworld_" + coordinate, val * 8);
    }

    public static void setField(Context context, String coordinateString, double val) {
        // Здесь вы можете выполнить операции для установки значения поля в вашем Android-приложении
        // Например, вы можете использовать TextView для отображения значений координат
    }
}
