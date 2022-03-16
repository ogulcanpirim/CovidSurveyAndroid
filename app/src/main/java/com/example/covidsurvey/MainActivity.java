package com.example.covidsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    public TextInputLayout nameLayout, birthdateLayout, cityLayout, genderLayout, vaccineLayout, positiveCaseLayout;
    public TextInputEditText nameEditText,birthdateEditText;
    public Button sendButton;
    public MaterialDatePicker datePicker;
    public ArrayAdapter<String> cityAdapter, genderAdapter, vaccineAdapter, positiveCaseAdapter;
    public AutoCompleteTextView autoCompleteTextViewCity, autoCompleteTextViewGender, autoCompleteTextViewVaccineType, autoCompleteTextViewPositiveCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bindings
        nameLayout = findViewById(R.id.textInputNameSurname);
        nameEditText = findViewById(R.id.textInputEditNameSurname);

        birthdateLayout = findViewById(R.id.textInputBirthdate);
        birthdateEditText = findViewById(R.id.textInputEditTextBirthdate);
        birthdateEditText.setCursorVisible(false);

        cityLayout = findViewById(R.id.textInputLayoutCity);
        autoCompleteTextViewCity = findViewById(R.id.autoCompleteTextViewCity);

        genderLayout = findViewById(R.id.textInputLayoutGender);
        autoCompleteTextViewGender = findViewById(R.id.autoCompleteTextViewGender);

        vaccineLayout = findViewById(R.id.textInputLayoutVaccineType);
        autoCompleteTextViewVaccineType = findViewById(R.id.autoCompleteTextViewVaccineType);

        positiveCaseLayout = findViewById(R.id.textInputLayoutPositiveCase);
        autoCompleteTextViewPositiveCase = findViewById(R.id.autoCompleteTextViewPositiveCase);

        sendButton = findViewById(R.id.sendButton);

        datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Birthdate").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        //String arrays
        //String[] cities = {"Ankara", "İzmir", "İstanbul"};
        String[] genders = {"Male", "Female"};
        String[] vaccineTypes = {"Sinovac", "Biontech", "Other"};
        String[] positiveCaseChoices = {"Yes", "No"};
        ArrayList <String> citiesCountries  = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            Iterator<?> keys = obj.keys();

            while( keys.hasNext() ) {
                String key = (String)keys.next();
                if ( obj.get(key) instanceof JSONArray ) {
                    JSONArray jArray = obj.getJSONArray(key);
                    for (int i=0; i < jArray.length(); i++)
                    {
                        try {
                            citiesCountries.add(key + ": " + jArray.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Adapters
        cityAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, citiesCountries);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, genders);
        vaccineAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, vaccineTypes);
        positiveCaseAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, positiveCaseChoices);


        autoCompleteTextViewCity.setAdapter(cityAdapter);
        autoCompleteTextViewGender.setAdapter(genderAdapter);
        autoCompleteTextViewVaccineType.setAdapter(vaccineAdapter);
        autoCompleteTextViewPositiveCase.setAdapter(positiveCaseAdapter);


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


        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(nameEditText.getText().length() > 0){
                    nameLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        birthdateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (birthdateEditText.length() > 0){
                    birthdateLayout.setError(null);
                }
            }
        });
        autoCompleteTextViewCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (autoCompleteTextViewCity.length() > 0){
                    cityLayout.setError(null);
                }
            }
        });
        autoCompleteTextViewGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(autoCompleteTextViewGender.length() > 0){
                    genderLayout.setError(null);
                }
            }
        });
        autoCompleteTextViewVaccineType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (autoCompleteTextViewVaccineType.length() > 0){
                    vaccineLayout.setError(null);
                }
            }
        });
        autoCompleteTextViewPositiveCase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (autoCompleteTextViewPositiveCase.length() > 0){
                    positiveCaseLayout.setError(null);
                }
            }
        });

        //Handle Send
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSend();
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


    public void handleSend(){
        if (nameEditText.length() == 0){
            nameLayout.setError("Required*");
        }

        if (birthdateEditText.length() == 0){
            birthdateLayout.setError("Required*");
        }

        if (autoCompleteTextViewCity.length() == 0){
            cityLayout.setError("Required*");
        }

        if (autoCompleteTextViewGender.length() == 0){
            genderLayout.setError("Required*");
        }

        if (autoCompleteTextViewVaccineType.length() == 0){
            vaccineLayout.setError("Required*");
        }

        if (autoCompleteTextViewPositiveCase.length() == 0){
            positiveCaseLayout.setError("Required*");
        }


    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("countriesToCities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}