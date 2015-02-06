package com.example.gregolm.android_101.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gregolm.android_101.utilities.network.Network;
import com.example.gregolm.android_101.R;
import com.example.gregolm.android_101.dto.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {

    protected EditText username, password;
    protected Spinner map;
    protected Map[] availableMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        map = (Spinner) findViewById(R.id.login_map);

/*      Old school, but userful for fragments
        ((Button)findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playClick(view);
            }
        });*/
        new AsyncTask<Void, Void, Map[]>(){

            @Override
            protected Map[] doInBackground(Void... voids) {
                try {
                    return Network.getMaps();
                } catch (IOException ex) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Map[] maps) {
                updateMapList(maps);
            }
        }.execute();

    }

    private void updateMapList(Map[] maps) {
        availableMaps = maps;
        List<String> mapList = new ArrayList<String>();
        for (int i = 0; i < maps.length; i++) {
            mapList.add(maps[i].getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                mapList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        map.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
            Intent i = new Intent(getApplicationContext(), CreditsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playClick(View target) {
        boolean hasErrors = false;
        if (username.getText().toString().isEmpty()) {
            username.setError(getResources().getString(R.string.login_username_empty_error));
            hasErrors = true;
        }
        if (password.getText().toString().length() < 3) {
            password.setError(getResources().getString(R.string.login_password_short_error));
            hasErrors = true;
        }
        if (hasErrors)return;

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... password) {
                if (password.length == 0) return false; // no password
                try {
                    return Network.login(password[0]);
                }
                catch (IOException ex) {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Intent i = new Intent(getApplicationContext(),GameActivity.class);
                    i.putExtra("mapToPlay", (String) map.getSelectedItem());
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Login Failed")
                            .setMessage("Invalid username and password combination..")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {}
                            })
                            .show();
                }
            }
        }.execute(password.getText().toString());
    }
}
