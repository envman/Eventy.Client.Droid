package com.myleshumphreys.joinin.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myleshumphreys.joinin.R;
import com.myleshumphreys.joinin.repositories.UserRepository;
import com.myleshumphreys.joinin.validation.InputValidation;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RegisterActivity extends Activity {

    private EditText editTextRegisterUserName;
    private EditText editTextRegisterEmailAddress;
    private EditText editTextRegisterPassword;
    private UserRepository userRepository;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_right2, R.anim.push_left2);
        setContentView(R.layout.activity_register);
        setupWidgets();
        addTextWatcher();
        registerButtonListener();
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
                String userInfo = "Value{" + "UserName=" + userName + "Email=" + emailAddress + ", Password'" + password + '\'' + '}';
                RegisterUser(userInfo);
            }
        });
    }

    private void RegisterUser(String userInfo)
    {
        String url = "http://joinin.azurewebsites.net/api/Account/Register";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.getForObject(url, String.class, "userInfo");
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

    private void endActivity() {
        RegisterActivity.this.finish();
        overridePendingTransition(R.anim.push_left, R.anim.push_right);
    }

    public void onBackPressed() {
        endActivity();
    }
}