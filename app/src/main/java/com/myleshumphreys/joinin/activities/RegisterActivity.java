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
import com.myleshumphreys.joinin.RetrofitService.IApiService;
import com.myleshumphreys.joinin.R;
import com.myleshumphreys.joinin.RetrofitService.RegisterResponse.RegisterResponse;
import com.myleshumphreys.joinin.RetrofitService.TokenResponse.TokenResponse;
import com.myleshumphreys.joinin.RetrofitService.TokenResponse.TokenResponseError;
import com.myleshumphreys.joinin.models.Account;
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

public class RegisterActivity extends Activity {

    private EditText editTextRegisterUserName;
    private EditText editTextRegisterEmailAddress;
    private EditText editTextRegisterPassword;
    private Button buttonRegister;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String ApplicationPreferences = "ApplicationPreferences";
    private final String baseUrl = "http://joinin.azurewebsites.net/";
    private Retrofit retrofit;
    private IApiService apiService;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_right2, R.anim.push_left2);
        setContentView(R.layout.activity_register);
        setupWidgets();
        addTextWatcher();
        registerButtonListener();
        setupRetrofit();
    }

    private void setupWidgets() {
        editTextRegisterUserName = (EditText) findViewById(R.id.textRegisterUserName);
        editTextRegisterEmailAddress = (EditText) findViewById(R.id.textRegisterEmailAddress);
        editTextRegisterPassword = (EditText) findViewById(R.id.textRegisterPassword);
        buttonRegister = (Button) findViewById(R.id.buttonCreateUser);
    }

    private void registerButtonListener() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = String.valueOf(editTextRegisterUserName.getText());
                String emailAddress = String.valueOf(editTextRegisterEmailAddress.getText());
                String password = String.valueOf(editTextRegisterPassword.getText());
                Account account = new Account(userName, emailAddress, password);
                registerAccount(account);
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

    private void registerAccount(final Account account) {
        Call<ResponseBody> call = apiService.registerAccount(account);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                int statusCode = response.code();

                if (response.body() != null) {
                    if (statusCode == HttpURLConnection.HTTP_OK) {
                        getToken(account.username, account.password);
                    }
                }

                if (response.errorBody() != null) {
                    try {
                        String errorBodyString = response.errorBody().string();
                        RegisterResponse registerResponse = gson.fromJson(errorBodyString, RegisterResponse.class);
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
            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
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

    private void getUserInput(EditText editTextEmailAddress, EditText editTextPassword) {
        String emailAddress = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();
        checkFieldsForEmptyValues(emailAddress, password);
    }

    private void checkFieldsForEmptyValues(String emailAddress, String password) {
        boolean validEmailAddress = InputValidation.IsNotNullOrEmpty(emailAddress);
        boolean validPassword = InputValidation.IsNotNullOrEmpty(password);

        if (validEmailAddress && validPassword) {
            buttonRegister.setEnabled(true);
        } else {
            buttonRegister.setEnabled(false);
        }
    }

    private void addTextWatcher() {
        editTextRegisterEmailAddress.addTextChangedListener(textWatcher);
        editTextRegisterPassword.addTextChangedListener(textWatcher);
        getUserInput(editTextRegisterEmailAddress, editTextRegisterPassword);
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
            getUserInput(editTextRegisterEmailAddress, editTextRegisterPassword);
        }
    };

    private void endActivity() {
        RegisterActivity.this.finish();
        overridePendingTransition(R.anim.push_left, R.anim.push_right);
    }

    public void onBackPressed() {
        endActivity();
    }
}