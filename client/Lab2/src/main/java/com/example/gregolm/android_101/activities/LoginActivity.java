package com.example.gregolm.android_101.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gregolm.android_101.R;


public class LoginActivity extends Activity {

    EditText username, password;
    Spinner map;

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

    }
}
