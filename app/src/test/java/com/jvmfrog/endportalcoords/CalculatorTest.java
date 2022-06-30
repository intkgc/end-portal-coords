package com.jvmfrog.endportalcoords;

import com.jvmfrog.endportalcoords.exception.AnglesEqualException;
import com.jvmfrog.endportalcoords.exception.AnglesOppositeException;
import com.jvmfrog.endportalcoords.util.EndPortalCalculator;
import com.jvmfrog.endportalcoords.util.Point;

import org.junit.Test;

public class CalculatorTest {
    @Test
    public void bedrock(){
        try {
            System.out.println(EndPortalCalculator.calculate(
                    new Point(-74.3f, 235.3f),
                    new Point(-141.6f, 247.1f),
                    new Point(202.79f, 565.74f),
                    new Point(180.51f, 565.52f)
            ));
            System.out.println(EndPortalCalculator.calculate(
                    new Point(0, -13),
                    new Point(8, -17),
                    new Point(19, 204),
                    new Point(38, 192)
            ));
        } catch (AnglesEqualException | AnglesOppositeException e) {
            e.printStackTrace();
        }
    }
}
