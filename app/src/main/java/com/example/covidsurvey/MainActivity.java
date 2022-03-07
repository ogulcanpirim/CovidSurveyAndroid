package com.example.covidsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {


    public TextInputLayout birthdateLayout;
    public TextInputEditText birthdateEditText;
    public Button sendButton;
    public MaterialDatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birthdateLayout = findViewById(R.id.textInputBirthdate);
        birthdateEditText = findViewById(R.id.textInputEditTextBirthdate);
        birthdateEditText.setCursorVisible(false);
        sendButton = findViewById(R.id.sendButton);

        datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Birthdate").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        birthdateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && birthdateEditText.getText().length() == 0){
                    openBirthdatePicker();
                }
            }
        });

        birthdateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBirthdatePicker();
            }
        });

    }

    public void openBirthdatePicker(){
        datePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                birthdateEditText.setText(datePicker.getHeaderText());
            }
        });
    }
}