package hcmus.angtonyvincent.registerform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterForm extends AppCompatActivity {

    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        buttonSignUp = (Button)findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterForm.this, "HI", Toast.LENGTH_SHORT).show();
                SignUp();
            }
        });

    }

    public void SignUp() {
        Intent intent = new Intent(RegisterForm.this, ResultForm.class);
        startActivity(intent);
    }

}
