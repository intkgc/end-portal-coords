package com.jvmfrog.endportalcoords.config;

public class Settings {
    @Parameter(jsonKey = "feedback_counter")
    public static int feedbackCounter = 0;

    @Parameter(jsonKey = "is_feedback_showed")
    public static boolean isFeedbackShowed = false;
}
