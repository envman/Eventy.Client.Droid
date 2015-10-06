package com.myleshumphreys.joinin.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myleshumphreys.joinin.Handlers.ConnectivityManagerHandler;
import com.myleshumphreys.joinin.R;
import com.myleshumphreys.joinin.RetrofitService.IApiService;
import com.myleshumphreys.joinin.RetrofitService.TokenResponse.TokenResponse;
import com.myleshumphreys.joinin.RetrofitService.TokenResponse.TokenResponseError;
import com.myleshumphreys.joinin.validation.InputValidation;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends Activity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String ApplicationPreferences = "ApplicationPreferences" ;
    public ConnectivityManagerHandler connectivityManagerHandler;

    private final String baseUrl = "http://joinin.azurewebsites.net/";
    private Retrofit retrofit;
    private IApiService apiService;
    private Gson gson;

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
        setupRetrofit();
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
                getToken(username, password);
            }
        });
    }

    private void setupRetrofit() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(IApiService.class);
    }

    private void getToken(String username, String password) {
        List<Map<String, String>> mappings = createRequestMappings(username, password);
        Call<ResponseBody> call = apiService.getToken(mappings.get(0), mappings.get(1), mappings.get(2));
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                int statusCode = response.code();

                if (response.body() != null) {
                    try {
                        if (statusCode == HttpURLConnection.HTTP_OK) {
                            String bodyString = response.body().string();
                            TokenResponse tokenResponse = gson.fromJson(bodyString, TokenResponse.class);
                            loginUser(tokenResponse.AccessToken);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (response.errorBody() != null) {
                    try {
                        String errorBodyString = response.errorBody().string();
                        TokenResponseError tokenResponse = gson.fromJson(errorBodyString, TokenResponseError.class);
                        Toast.makeText(getApplicationContext(), errorBodyString, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Log
            }
        });
    }

    private void loginUser(String token) {
        if (InputValidation.IsNotNullOrEmpty(token)) {
            Intent intentEvent = new Intent(getApplicationContext(), EventActivity.class);
            storeToken(token);
            intentEvent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentEvent);
            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Token", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Map<String, String>> createRequestMappings(String username, String password) {
        List<Map<String, String>> mappings = new ArrayList<>();

        Map<String, String> grantTypeMap = new HashMap<>();
        grantTypeMap.put("grant_type", "password");
        mappings.add(grantTypeMap);

        Map<String, String> userNameMap = new HashMap<>();
        userNameMap.put("username", username);
        mappings.add(userNameMap);

        Map<String, String> passwordMap = new HashMap<>();
        passwordMap.put("password", password);
        mappings.add(passwordMap);

        return mappings;
    }

    private void storeToken(String token) {
        sharedPreferences = getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
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
        connectivityManagerHandler = new ConnectivityManagerHandler();
        if (!connectivityManagerHandler.hasInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Check your Internet access", Toast.LENGTH_LONG).show();
        }
    }

    private void endActivity() {
        LoginActivity.this.finish();
    }
}