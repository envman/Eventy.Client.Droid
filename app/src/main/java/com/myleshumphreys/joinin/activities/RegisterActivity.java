package com.myleshumphreys.joinin.activities;

import android.app.Activity;
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
import com.myleshumphreys.joinin.RetrofitService.ResponseHandler;
import com.myleshumphreys.joinin.models.Account;
import com.myleshumphreys.joinin.validation.InputValidation;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
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

    private final String baseUrl = "http://joinin.azurewebsites.net/";
    private Retrofit retrofit;
    private IApiService apiService;
    private ResponseHandler responseHandler;
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
                RegisterAccount(account);
            }
        });
    }

    private void setupRetrofit()
    {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(IApiService.class);
    }

    private void RegisterAccount(Account account) {
        Call<ResponseBody> call = apiService.registerAccount(account);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                int statusCode = response.code();

                if(response.body() != null)
                {
                    RegisteredAccount();
                }

                if(response.errorBody() != null)
                {
                    try {
                        String test = response.errorBody().string();
                        Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserInput(EditText editTextEmailAddress, EditText editTextPassword) {
        String emailAddress = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();
        checkFieldsForEmptyValues(emailAddress, password);
    }

    private void checkFieldsForEmptyValues(String emailAddress, String password) {
        boolean validEmailAddress = InputValidation.IsNullOrEmpty(emailAddress);
        boolean validPassword = InputValidation.IsNullOrEmpty(password);

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

    private void RegisteredAccount()
    {
        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
        endActivity();
    }

    private void endActivity() {
        RegisterActivity.this.finish();
        overridePendingTransition(R.anim.push_left, R.anim.push_right);
    }

    public void onBackPressed() {
        endActivity();
    }
}