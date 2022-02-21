package com.jvmfrog.endportalcoords;

import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;

/**
 * Code from <a href="https://skrepkaq.ru/stronghold">https://skrepkaq.ru/stronghold</a>
 */

public class EndPortal {
    public static Point getPortalCoords(Point throw0, Point throw1, float angle0, float angle1)
            throws AnglesEqualException, AnglesOppositeException {
        Point endPortal = new Point(0, 0);

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
                    endPortal.z = Math.round(cot(-angle1 * p) * throw0.x - (throw1.x * cot(-angle1 * p) - throw1.z));
                    break;
                case -90:
                case 90:
                    endPortal.z = Math.round(throw0.z);
                    endPortal.x = Math.round(Math.round(throw1.x * cot(-angle1 * p) - throw1.z + throw0.z) / cot(-angle1 * p));
                    break;
                default:
                    switch (Math.round(angle1)) {
                        case -180:
                        case 0:
                        case 180:
                            endPortal.x = Math.round(throw1.x);
                            endPortal.z = Math.round(cot(-angle0 * p) * throw1.x - (throw0.x * cot(-angle0 * p) - throw0.z));
                            break;
                        case -90:
                        case 90:
                            endPortal.z = Math.round(throw1.z);
                            endPortal.x = Math.round((throw0.x * cot(-angle0 * p) - throw0.z + throw1.z) / cot(-angle0 * p));
                            break;
                        default:
                            endPortal.x = Math.round(((throw0.x * cot(-angle0 * p) - throw0.z) - (throw1.x * cot(-angle1 * p) - throw1.z)) / (cot(-angle0 * p) - cot(-angle1 * p)));
                            endPortal.z = Math.round(cot(-angle0 * p) * endPortal.x - (throw0.x * cot(-angle0 * p) - throw0.z));
                    }
            }
        }
        return endPortal;
    }

    private static final double p = Math.PI / 180;

    private static double cot(double x) {
        return 1 / Math.tan(x);
    }
}
