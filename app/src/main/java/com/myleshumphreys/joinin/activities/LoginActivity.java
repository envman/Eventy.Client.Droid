package com.myleshumphreys.joinin.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myleshumphreys.joinin.R;
import com.myleshumphreys.joinin.repositories.UserRepository;
import com.myleshumphreys.joinin.validation.InputValidation;

public class LoginActivity extends Activity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userRepository = new UserRepository(getApplicationContext());
        setupWidgets();
        addTextWatcher();
        registerButtonListener();
        loginButtonListener();

        if ( !HasInternetConnection()) {
            Toast.makeText(getApplicationContext(), "Check your Internet access", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupWidgets() {
        editTextUsername = (EditText) findViewById(R.id.textLoginUserName);
        editTextPassword = (EditText) findViewById(R.id.textLoginPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogIn);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
    }

    private void registerButtonListener() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegisterUser = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegisterUser);
            }
        });
    }

    private void loginButtonListener() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = String.valueOf(editTextUsername.getText());
                String password = String.valueOf(editTextPassword.getText());
            }
        });
    }

    private void getUserInput(EditText editTextUserName, EditText editTextPassword) {
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        checkFieldsForEmptyValues(username, password);
    }

    private void checkFieldsForEmptyValues(String username, String password) {
        boolean validUserName = InputValidation.IsNullOrEmpty(username);
        boolean validPassword = InputValidation.IsNullOrEmpty(password);

        if (validUserName && validPassword) {
            buttonLogin.setEnabled(true);
        } else {
            buttonLogin.setEnabled(false);
        }
    }

    private void addTextWatcher() {
        editTextUsername.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);
        getUserInput(editTextUsername, editTextPassword);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            getUserInput(editTextUsername, editTextPassword);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean HasInternetConnection () {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (NetworkInfo != null && NetworkInfo.isConnectedOrConnecting()) {
            return true ;
        } else {
            return false ;
        }
    }

    private void EndActivity() {
        LoginActivity.this.finish();
    }
}