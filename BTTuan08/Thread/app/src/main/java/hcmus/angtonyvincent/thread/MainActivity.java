package hcmus.angtonyvincent.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar bar;
    EditText number;
    Button run;

    int value;

    // this is a control var used by backg. threads
    protected boolean isRunning = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            //do something with the value sent by the background thread here

            bar.incrementProgressBy(1);

            //testing early termination
            if (bar.getProgress() == bar.getMax()) {
                run.setVisibility(View.VISIBLE);
                number.setText("0");
            } else {
                value--;
                number.setText("" + value);
            }
        }
    }; //handler

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        number = (EditText) findViewById(R.id.editTextNumber);

        bar = (ProgressBar) findViewById(R.id.progress);
        bar.setProgress(0);
        bar.setMax(0);

        run = (Button) findViewById(R.id.buttonRun);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                run.setVisibility(View.INVISIBLE);

                value = Integer.parseInt(number.getText().toString());

                bar.setProgress(0);
                bar.setMax(value);

                // this code creates the background activity where busy work is done
                Thread background = new Thread(new Runnable() {
                    public void run() {
                        try {
                            for (int i = value; i > 0 && isRunning; i--) {

                                Message msg = handler.obtainMessage(1, "");

                                if (isRunning) {
                                    handler.sendMessage(msg);
                                }
                            }
                        } catch (Throwable t) {
                            // just end the background thread
                            isRunning = false;
                        }
                    }
                });// Thread

                isRunning = true;

                background.start();
            }
        });
    }//onCreate
}
