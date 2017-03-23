package hcmus.angtonyvincent.registerform;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultForm extends AppCompatActivity {

    private EditText txtUserRegis;
    private EditText txtPasswordRegis;
    private EditText txtBthDateRegis;
    private EditText txtGenderRegis;
    private EditText txtHobbiesRegis;
    private Button bttnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_form);

        bttnExit = (Button)this.findViewById(R.id.buttonExit);

        txtUserRegis = (EditText)this.findViewById(R.id.editTextUserResigted);
        txtBthDateRegis = (EditText)this.findViewById(R.id.editTextBirthdateResigted);
        txtPasswordRegis = (EditText)this.findViewById(R.id.editTextPasswordResigted);
        txtGenderRegis = (EditText)this.findViewById(R.id.editTextGenderResigted);
        txtHobbiesRegis = (EditText)this.findViewById(R.id.editTextHobbiesResigted);

        bttnExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ExitApp();
            }
        });

        // display saved info in SharedPreferences Database
        this.GetSavedInfo();
    }

    public void ExitApp(){
        finish();
        System.exit(1);
    }

    private void GetSavedInfo(){
        SharedPreferences myPrefContainer = getSharedPreferences("DATA", Activity.MODE_PRIVATE);
        txtUserRegis.setText(myPrefContainer.getString("username",""), TextView.BufferType.NORMAL);
        txtPasswordRegis.setText(myPrefContainer.getString("password",""), TextView.BufferType.NORMAL);
        txtBthDateRegis.setText(myPrefContainer.getString("birthdate",""), TextView.BufferType.NORMAL);
        txtGenderRegis.setText(myPrefContainer.getString("gender",""), TextView.BufferType.NORMAL);
        txtHobbiesRegis.setText(myPrefContainer.getString("hobbies",""), TextView.BufferType.NORMAL);
    }
}
