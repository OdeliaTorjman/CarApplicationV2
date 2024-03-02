package com.example.appli3voiture.Model;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorMoove {
    public interface CallBackSteps {


        void moveLeft();

        void moveRight();

    }

    private CallBackSteps callBackSteps;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float previousX = 0;
    private float previousY = 0;

    public SensorMoove(Context context, CallBackSteps callBackSteps) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBackSteps = callBackSteps;

    }

    //register to the sensors
    public void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //unregister to the sensors
    public void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            registerMovement(x, y);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void registerMovement(float x, float y) {
        if ((x - previousX) >= 1.5) {
            if (callBackSteps != null) {
                callBackSteps.moveLeft();
                previousX = x;
            }
        } else if ((x - previousX) <= -1.5) {
            if (callBackSteps != null) {
                callBackSteps.moveRight();
                previousX = x;
            }
        }
    }
}
