package com.jvmfrog.endportalcoords.util;

import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;

/**
 * Code from <a href="https://skrepkaq.ru/stronghold">https://skrepkaq.ru/stronghold</a>
 */

public class EndPortalCalculator {
    public static Point calculate(Point throw0, Point throw1, float angle0, float angle1)
            throws AnglesEqualException, AnglesOppositeException {
        Point endPortal = new Point(0, 0);
        System.out.println(throw0.x + " " + throw0.y);
        System.out.println(throw1.x + " " + throw1.y);
        System.out.println(angle0 + " " + angle1);
        if (Math.abs(angle0 - angle1) < 1)
            throw new AnglesEqualException();
        else if ((((angle0 < 0) && (angle1 > 0)) ||
                ((angle0 > 0) && (angle1 < 0)))
                && (Math.abs(Math.abs(Math.abs(angle0) - 180) - Math.abs(angle1)) < 1))
            throw new AnglesOppositeException();
        else {
            switch (Math.round(angle0)) {
                case -180:
                case 0:
                case 180:
                    endPortal.x = Math.round(throw0.x);
                    endPortal.y = Math.round(cot(-angle1 * p) * throw0.x - (throw1.x * cot(-angle1 * p) - throw1.y));
                    break;
                case -90:
                case 90:
                    endPortal.y = Math.round(throw0.y);
                    endPortal.x = Math.round(Math.round(throw1.x * cot(-angle1 * p) - throw1.y + throw0.y) / cot(-angle1 * p));
                    break;
                default:
                    switch (Math.round(angle1)) {
                        case -180:
                        case 0:
                        case 180:
                            endPortal.x = Math.round(throw1.x);
                            endPortal.y = Math.round(cot(-angle0 * p) * throw1.x - (throw0.x * cot(-angle0 * p) - throw0.y));
                            break;
                        case -90:
                        case 90:
                            endPortal.y = Math.round(throw1.y);
                            endPortal.x = Math.round((throw0.x * cot(-angle0 * p) - throw0.y + throw1.y) / cot(-angle0 * p));
                            break;
                        default:
                            endPortal.x = Math.round(((throw0.x * cot(-angle0 * p) - throw0.y) - (throw1.x * cot(-angle1 * p) - throw1.y)) / (cot(-angle0 * p) - cot(-angle1 * p)));
                            endPortal.y = Math.round(cot(-angle0 * p) * endPortal.x - (throw0.x * cot(-angle0 * p) - throw0.y));
                    }
            }
        }
        endPortal.x = Math.round(endPortal.x);
        endPortal.y = Math.round(endPortal.y);
        return endPortal;
    }

    public static Point calculate(Point firstThrow0, Point firstThrow1,
                                  Point secondThrow0, Point secondThrow1) throws AnglesEqualException, AnglesOppositeException {
        return calculate(firstThrow0, secondThrow1,
                (float) Vector.angleOfReference(Vector.fromPoints(firstThrow0, firstThrow1)),
                (float) Vector.angleOfReference(Vector.fromPoints(secondThrow0, secondThrow1)));
    }


    private static final double p = Math.PI / 180;

    private static double cot(double x) {
        return 1 / Math.tan(x);
    }
}
