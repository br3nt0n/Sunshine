package com.bc.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_location) {
            openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap(){
        String userLocation = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        Uri location = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q",userLocation)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(location);
        //Check if user has any mapping apps installed
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            CharSequence errorMsg = getString(R.string.error_maps);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, errorMsg, duration);
            toast.show();
        }
    }
}
