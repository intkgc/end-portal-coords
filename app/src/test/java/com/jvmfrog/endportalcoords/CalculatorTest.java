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
                    new Point(13, -4),
                    new Point(-52, 120),
                    new Point(-124, 254),
                    new Point(-201, 392)
            ));
        } catch (AnglesEqualException | AnglesOppositeException e) {
            e.printStackTrace();
        }
    }
}
