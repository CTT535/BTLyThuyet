package hcmus.angtonyvincent.registerform;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class RegisterForm extends AppCompatActivity {

    // widget attributes
    private Button bttnSignUp;
    private Button bttnReset;
    private Button bttnSelectDate;
    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtPasswordRetype;
    private EditText txtBthDate;
    private RadioGroup rdoGrpGender;
    private RadioButton rdbttnMale;
    private RadioButton rdobttnFemale;
    private CheckBox checkboxTennis;
    private CheckBox checkboxFutbal;
    private CheckBox checkboxOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        // Get button
        bttnSignUp = (Button)findViewById(R.id.buttonSignUp);
        bttnReset = (Button)findViewById(R.id.buttonReset);
        bttnSelectDate = (Button)findViewById((R.id.buttonSelectDate));

        // get text
        txtUser = (EditText)findViewById(R.id.editTextUsername);
        txtPassword = (EditText)this.findViewById(R.id.editTextPassword);
        txtPasswordRetype = (EditText)this.findViewById(R.id.editTextRetype);
        txtBthDate = (EditText)this.findViewById(R.id.editTextBirthdate);

        // Get checkboxes
        rdoGrpGender = (RadioGroup)this.findViewById(R.id.radioGroup);
        checkboxTennis = (CheckBox)this.findViewById(R.id.checkBoxTennis);
        checkboxFutbal = (CheckBox)this.findViewById(R.id.checkBoxFutbal);
        checkboxOthers = (CheckBox)this.findViewById(R.id.checkboxOthers);

        // get radio buttons
        rdbttnMale = (RadioButton)this.findViewById(R.id.radioButtonMale);
        rdobttnFemale = (RadioButton)this.findViewById(R.id.radioButtonFemale);

        // Set action event listener for button
        // sign up action
        bttnSignUp.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        // reset action
        bttnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ResetInfo();
            }
        });

        // select date
        bttnSelectDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SignUp() {
        // (in case it exists) use saved data telling backg color
        SharedPreferences myPrefContainer = getSharedPreferences("DATA", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefContainer.edit();

        // save name, password & birthdate
        editor.putString("username",txtUser.getText().toString());
        editor.putString("password",txtPassword.getText().toString());
        editor.putString("birthdate",txtBthDate.getText().toString());

        // Save gender text to SharedPreferences
        if (this.rdbttnMale.isChecked())
            editor.putString("gender", "Male");
        else if (this.rdobttnFemale.isChecked())
            editor.putString("gender", "Female");

        // Save hobbies
        String hobbies = "";
        if (checkboxTennis.isChecked())
            hobbies += "Tennis";
        if (checkboxFutbal.isChecked()) {
            if (hobbies.equals(""))
                hobbies += "Futbal";
            else
                hobbies += ", Futbal";
        }
        editor.putString("hobbies", hobbies);

        // check date and password invalid
        if (!this.isValidDate(txtBthDate.getText().toString())) {
            Toast.makeText(RegisterForm.this, "Not invalid date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!txtPassword.getText().toString().equals(txtPasswordRetype.getText().toString())) {
            Toast.makeText(RegisterForm.this, "The password retyped is not match with your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // apply changes
        editor.commit();

        // intent, change screen
        Intent intent = new Intent(RegisterForm.this, ResultForm.class);
        startActivity(intent);

        // dispose this screen
        finish();
        System.exit(1);
    }

    public void ExitApp(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(1);
    }

    public void ResetInfo() {
        // reset text
        txtUser.setText("", TextView.BufferType.EDITABLE);
        txtPassword.setText("", TextView.BufferType.EDITABLE);
        this.txtPasswordRetype.setText("", TextView.BufferType.EDITABLE);
        this.txtBthDate.setText("", TextView.BufferType.EDITABLE);

        // uncheck check RadioGroup
        this.rdoGrpGender.clearCheck();

        // uncheck check box
        this.checkboxTennis.setChecked(false);
        this.checkboxFutbal.setChecked(false);
        this.checkboxOthers.setChecked(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SelectDate(){
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        txtBthDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
