package com.jvmfrog.endportalcoords.util;

public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector fromPoints(Point start, Point end) {
        return new Vector(start.x - end.x, start.y - end.y);
    }

    public static double angleOfReference(Vector v) {
        return Math.atan2(v.y, v.x) / Math.PI * 180 + 90;
    }
}
