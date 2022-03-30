package com.example.covidsurvey;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
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
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    public TextInputLayout nameLayout, birthdateLayout, cityLayout, vaccineLayout, positiveCaseLayout;
    public TextInputEditText nameEditText,birthdateEditText;
    public Button sendButton;
    public MaterialDatePicker datePicker;
    public ArrayAdapter<String> cityAdapter, vaccineAdapter, positiveCaseAdapter;
    public AutoCompleteTextView autoCompleteTextViewCity, autoCompleteTextViewVaccineType, autoCompleteTextViewPositiveCase;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        vaccineLayout = findViewById(R.id.textInputLayoutVaccineType);
        autoCompleteTextViewVaccineType = findViewById(R.id.autoCompleteTextViewVaccineType);

        positiveCaseLayout = findViewById(R.id.textInputLayoutPositiveCase);
        autoCompleteTextViewPositiveCase = findViewById(R.id.autoCompleteTextViewPositiveCase);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setEnabled(false);
        setDatePicker();

        String[] vaccineTypes = {"Sinovac", "Biontech", "Other"};
        String[] positiveCaseChoices = {"Yes", "No"};
        ArrayList <String> citiesCountries  = new ArrayList<>();
        setupCitiesList (citiesCountries);

        //Adapters
        cityAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, citiesCountries);
        vaccineAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, vaccineTypes);
        positiveCaseAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, positiveCaseChoices);


        autoCompleteTextViewCity.setAdapter(cityAdapter);
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
                checkButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(nameEditText.getText().length() > 0){
                }
                if (nameEditText.length() == 0){
                    nameLayout.setError("Required*");
                }
                if (!nameEditText.getText().toString().matches("[a-zA-Z ]+")) {
                    nameLayout.setError("illegal characters in name*");
                }
                else {
                    nameLayout.setError(null);
                }
                checkButton();

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
                if (birthdateEditText.length() == 0){
                    birthdateLayout.setError("Required*");
                }
                checkButton();
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
                if (autoCompleteTextViewCity.length() == 0){
                    cityLayout.setError("Required*");
                }
                checkButton();
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

                if (autoCompleteTextViewVaccineType.length() == 0){
                    vaccineLayout.setError("Required*");
                }

                checkButton();
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
                if (autoCompleteTextViewPositiveCase.length() == 0){
                    positiveCaseLayout.setError("Required*");
                }
                checkButton();
            }
        });

        //Handle Send
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
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
    void checkButton (){
        if (TextUtils.isEmpty(nameLayout.getError()) &&
                TextUtils.isEmpty(vaccineLayout.getError()) &&
                TextUtils.isEmpty(positiveCaseLayout.getError()) &&
                TextUtils.isEmpty(cityLayout.getError()) &&
                TextUtils.isEmpty(birthdateLayout.getError()) &&
                !(nameEditText.getText().length() == 0) &&
                !(birthdateEditText.getText().length() == 0) &&
                !(autoCompleteTextViewCity.getText().length() == 0) &&
                !(autoCompleteTextViewVaccineType.getText().length() == 0) &&
                !(autoCompleteTextViewPositiveCase.getText().length() == 0)){
            sendButton.setEnabled(true);
        }
        else {
            sendButton.setEnabled(false);
        }
    }

    void setupCitiesList (ArrayList citiesCountries ){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            Iterator<?> keys = obj.keys();

            while( keys.hasNext() ) {
                String key = (String)keys.next();
                if ( obj.get(key) instanceof JSONArray ) {
                    JSONArray jArray = obj.getJSONArray(key);
                    for (int i=0; i < jArray.length() % 10 ; i++)
                    {
                        try {
                            if (!jArray.getString(i).equals(""))
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
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setDatePicker (){
        Calendar min = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        min.set(1900, 01, 1);
        min.getTimeInMillis();
        Calendar max = Calendar.getInstance();

        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();
        CalendarConstraints.DateValidator dateValidatorMin = DateValidatorPointForward.from(min.getTimeInMillis());
        CalendarConstraints.DateValidator dateValidatorMax = DateValidatorPointBackward.before(max.getTimeInMillis());

        ArrayList<CalendarConstraints.DateValidator> listValidators =
                new ArrayList<CalendarConstraints.DateValidator>();
        listValidators.add(dateValidatorMin);
        listValidators.add(dateValidatorMax);
        CalendarConstraints.DateValidator validators = CompositeDateValidator.allOf(listValidators);
        constraintsBuilderRange.setValidator(validators);

        datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Birthdate").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilderRange.build())
                .build();
    }
}