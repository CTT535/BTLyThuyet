package hcmus.angtonyvincent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentRed extends Fragment implements FragmentCallbacks {
    MainActivity main;
    TextView txtRed;
    Button btnFirst, btnBack, btnNext, btnLast;

    public static FragmentRed newInstance(String strArg1) {
        FragmentRed fragment = new FragmentRed();
        Bundle bundle = new Bundle();
        bundle.putString("arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }// newInstance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Activities containing this fragment must implement interface: MainCallbacks
        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException(" Activity must implement MainCallbacks");
        }
        main = (MainActivity) getActivity(); // use this reference to invoke main callbacks
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate res/layout_red.xml which includes a textview and a button
        LinearLayout view_layout_red = (LinearLayout) inflater.inflate(R.layout.layout_red, null);

        // plumbing - get a reference to widgets in the inflated layout
        txtRed = (TextView) view_layout_red.findViewById(R.id.textView1Red);

        // show string argument supplied by constructor (if any!)
        try {
            Bundle arguments = getArguments();
            String redMessage = arguments.getString("arg1", "");
            txtRed.setText(redMessage);
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR - ", "" + e.getMessage());
        }

        btnFirst = (Button) view_layout_red.findViewById(R.id.buttonFirst);
        btnFirst.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.onMsgFromFragToMain("RED-FRAG", "first"); // send "first"
            }
        });

        btnBack = (Button) view_layout_red.findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.onMsgFromFragToMain("RED-FRAG", "back"); // send "back"
            }
        });

        btnNext = (Button) view_layout_red.findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.onMsgFromFragToMain("RED-FRAG", "next"); // send "next"
            }
        });

        btnLast = (Button) view_layout_red.findViewById(R.id.buttonLast);
        btnLast.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.onMsgFromFragToMain("RED-FRAG", "last"); // send "last"
            }
        });

        return view_layout_red;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        // receiving a message from MainActivity (it may happen at any point in time)
        txtRed.setText(strValue);
    }
}
