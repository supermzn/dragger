package com.example.dragger;


import android.support.v4.util.Pair;
import android.view.View;

public class GeometryHelper {

    public static Pair<Float, Float> getPosition(View view) {
        return new Pair<>(view.getX() + getRadius(view),
                view.getY() + getRadius(view));
    }

    public static float getRadius(View view) {
        if (view.getHeight() == view.getWidth())
            return view.getHeight() / 2;
        else throw new IllegalStateException("The given view is not a square!");
    }

    public static double getDistance(Pair<Float, Float> point1, Pair<Float, Float> point2) {
        double xDistance = Math.pow((point1.first - point2.first), 2);
        double yDistance = Math.pow((point1.second - point2.second), 2);
        return Math.sqrt(xDistance + yDistance);
    }
}
