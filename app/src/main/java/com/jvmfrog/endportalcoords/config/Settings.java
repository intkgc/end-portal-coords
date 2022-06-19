package com.jvmfrog.endportalcoords.config;

public class Settings {
    @Parameter(jsonKey = "first_x_coordinate")
    public static float firstXCoordinate;

    @Parameter(jsonKey = "first_z_coordinate")
    public static float firstZCoordinate;

    @Parameter(jsonKey = "first_throw_angle")
    public static float firstThrowAngle;

    @Parameter(jsonKey = "second_x_coordinate")
    public static float secondXCoordinate;

    @Parameter(jsonKey = "first_z_coordinate")
    public static float secondZCoordinate;

    @Parameter(jsonKey = "second_throw_angle")
    public static float secondThrowAngle;
}
