package net.smallacademy.askpermissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.camera){
           if(hasPermission(this, Manifest.permission.CAMERA)){
               Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(camera,1000);
           }else {
               // ask for camera permissions 
               ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},102);
           }
        }

        if(item.getItemId() == R.id.gallery){
            // open gallery
            if(hasPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // open gallery 
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},103);
            }

        }
        return super.onOptionsItemSelected(item);
    }
    
    private boolean hasPermission(Context context,String permission){
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 102){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                // permission is granted 
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,1000);
                
            }else {
                Toast.makeText(this, "Permission is Denied.", Toast.LENGTH_SHORT).show();
            }
        }
        
        if(requestCode == 103){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                // permission is granted
                Toast.makeText(this, "Permission is Granted.", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Permission is Denied.", Toast.LENGTH_SHORT).show();
            }
        }

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            // alert dialog

            AlertDialog.Builder extraInfo = new AlertDialog.Builder(this);
            extraInfo.setTitle("Storage Permission is Required.");
            extraInfo.setMessage("To Run this app, App needs access to storage to save the file.");

            extraInfo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},103);
                }
            });
            
            extraInfo.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Some Feature of App Might not Work.", Toast.LENGTH_SHORT).show();
                }
            });
            
            extraInfo.create().show();
        }else {
            Toast.makeText(this, "Permission is denied , Please go to app settings to give permission Manually.", Toast.LENGTH_SHORT).show();
        }
    }
}
