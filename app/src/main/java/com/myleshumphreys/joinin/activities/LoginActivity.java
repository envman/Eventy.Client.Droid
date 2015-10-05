package com.myleshumphreys.joinin.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myleshumphreys.joinin.R;
import com.myleshumphreys.joinin.validation.InputValidation;

public class LoginActivity extends Activity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private SharedPreferences sharedPreferences;
    public static final String ApplicationPreferences = "ApplicationPreferences" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInternetConnection();
        checkSharedPreferences();
    }

    private void checkSharedPreferences() {
        sharedPreferences = getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
        String dataReturned = sharedPreferences.getString("token", "");
        if (InputValidation.IsNotNullOrEmpty(dataReturned)) {
            startEventActivity();
            endActivity();

        } else {
            setupActivity();
        }
    }

    private void setupActivity() {
        setContentView(R.layout.activity_login);
        setupWidgets();
        addTextWatcher();
        registerButtonListener();
        loginButtonListener();
    }

    private void setupWidgets() {
        editTextUsername = (EditText) findViewById(R.id.textLoginUserName);
        editTextPassword = (EditText) findViewById(R.id.textLoginPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogIn);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
    }

    private void startEventActivity()
    {
        Intent intentEvent = new Intent(getApplicationContext(), EventActivity.class);
        startActivity(intentEvent);
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
        boolean validUserName = InputValidation.IsNotNullOrEmpty(username);
        boolean validPassword = InputValidation.IsNotNullOrEmpty(password);

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

    private void checkInternetConnection()
    {
        if (!hasInternetConnection()) {
            Toast.makeText(getApplicationContext(), "Check your Internet access", Toast.LENGTH_LONG).show();
        }
    }

    public boolean hasInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (NetworkInfo != null && NetworkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void endActivity() {
        LoginActivity.this.finish();
    }
}