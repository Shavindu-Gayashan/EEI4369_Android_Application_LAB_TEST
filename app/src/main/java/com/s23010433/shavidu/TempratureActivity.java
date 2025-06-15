package com.s23010433.shavidu;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TempratureActivity extends AppCompatActivity implements SensorEventListener {
    private static final float THRESHOLD = 33.0f;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private MediaPlayer mediaPlayer;
    private boolean alertPlayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temprature);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (tempSensor == null) {
            Toast.makeText(this, "No temperature sensor found!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float temp = event.values[0];
        TextView tempView = findViewById(R.id.current_temp);
        TextView alarmStateView = findViewById(R.id.alarm_state);
        tempView.setText("Current: " + temp + " °C");

        if (temp > THRESHOLD && !alertPlayed) {
            mediaPlayer = MediaPlayer.create(this, R.raw.alert);
            mediaPlayer.start();
            alertPlayed = true;
            alarmStateView.setText("Alarm: ON");
        } else if (temp <= THRESHOLD && alertPlayed) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            alertPlayed = false;
            alarmStateView.setText("Alarm: OFF");
        } else {
            alarmStateView.setText(alertPlayed ? "Alarm: ON" : "Alarm: OFF");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}