package org.hse.android;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hse.basetimetable.R;
public class SettingsActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor light;
    private TextView sensorLight;

    private EditText nameEdit;
    private ImageView userPhoto;
    Uri photoURI;

    private PreferenceManager preferenceManager;

    private static final String TAG = "SettingsActivity";
    private static final String PERMISSION = "android.permission.CAMERA";
    private static final Integer REQUEST_PERMISSION_CODE = 1;
    private static final Integer REQUEST_IMAGE_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferenceManager = new PreferenceManager(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorLight = findViewById(R.id.lightLevel);
        sensorLight.setText("0 lux");

        nameEdit = findViewById(R.id.name);
        getName();

        TextView allSensors = findViewById(R.id.allSensors);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder allSensorsText = new StringBuilder();
        for (Sensor sensor : sensors)
            allSensorsText.append(sensor.getName()).append('\n');
        allSensors.setText(allSensorsText.toString());

        allSensors.setMovementMethod(new ScrollingMovementMethod());

        Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(v -> save());

        Button uploadPhoto = findViewById(R.id.uploadPhoto);
        uploadPhoto.setOnClickListener(v -> checkPermission());

        userPhoto = findViewById(R.id.userPhoto);
        getPhoto();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        sensorLight.setText(lux + " lux");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            int permissionCheck = ActivityCompat.checkSelfPermission(this, PERMISSION);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (photoURI != null) {
                userPhoto.setImageURI(photoURI);
                Log.d(TAG, "Image set successfully");
            } else {
                Log.e(TAG, "photoURI is null");
            }
        } else {
            Log.e(TAG, "Unexpected requestCode or resultCode");
        }
    }

    private void getName() {
        String name = preferenceManager.getValue("name", "");
        if (!name.isEmpty()) {
            nameEdit.setText(name);
        }
    }

    private void getPhoto() {
        String uri = preferenceManager.getValue("avatar", "");
        if (!uri.isEmpty()) {
            photoURI = Uri.parse(uri);
            userPhoto.setImageURI(photoURI);
        }
    }

    private void save() {
        if (nameEdit.getText() != null)
            preferenceManager.saveValue("name", nameEdit.getText().toString());
        if (photoURI != null)
            preferenceManager.saveValue("avatar", photoURI.toString());
        Toast.makeText(getApplicationContext(), getString(R.string.saved_message), Toast.LENGTH_SHORT).show();
    }

    public void checkPermission() {
        int permissionCheck = ActivityCompat.checkSelfPermission(this, PERMISSION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)){
                Toast.makeText(getApplicationContext(), getString(R.string.ask_photo_permits), Toast.LENGTH_LONG).show();
            }else {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, REQUEST_PERMISSION_CODE);
            }
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.e(TAG, "Create file", ex);
        }
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Start activity", e);
            }
        }
    }

    private File createImageFile() throws IOException {
        File pathOfStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePrefix = "img_" + timeStamp + "_";
        String suffix = ".jpg";

        return File.createTempFile(filePrefix, suffix, pathOfStorageDir);
    }

}
