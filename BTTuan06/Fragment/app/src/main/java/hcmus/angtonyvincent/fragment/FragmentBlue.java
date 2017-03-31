package hcmus.angtonyvincent.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentBlue extends Fragment implements FragmentCallbacks {
    // this fragment shows a ListView
    MainActivity main;
    Context context = null;
    TextView txtBlue;
    ListView listView;
    int currentPosition = 0;

    // data to fill-up the ListView
    private String items[] = {"Item 01",
            "Item 02", "Item 03", "Item 04",
            "Item 05", "Item 06", "Item 07",
            "Item 08", "Item 09", "Item 10"
    };

    // data to fill-up the TextView
    private String infos[] = {"Data 001",
            "Data 002", "Data 003", "Data 004",
            "Data 005", "Data 006", "Data 007",
            "Data 008", "Data 009", "Item 010"
    };

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static FragmentBlue newInstance(String strArg) {
        FragmentBlue fragment = new FragmentBlue();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException(
                    "MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate res/layout_blue.xml to make GUI holding a TextView and a ListView
        final LinearLayout layout_blue = (LinearLayout) inflater.inflate(R.layout.layout_blue, null);

        // plumbing – get a reference to textview and listview
        txtBlue = (TextView) layout_blue.findViewById(R.id.textView1Blue);

        // define a simple adapter to fill rows of the listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);

        listView = (ListView) layout_blue.findViewById(R.id.listView1Blue);
        listView.setBackgroundColor(Color.BLUE);
        listView.setAdapter(adapter);
        listView.setSelection(0); // show listview from the top
        listView.smoothScrollToPosition(0);

        // react to click events on listview’s rows
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // update current position
                currentPosition = position;

                // update listview
                updateListView();
            }
        });

        // do this for each row (ViewHolder-Pattern could be used for better performance!)
        return layout_blue;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        switch (strValue) {
            case "first":
                currentPosition = 0;
                break;

            case "back":
                if (currentPosition > 0) {
                    currentPosition--;
                }
                break;

            case "next":
                if (currentPosition < items.length - 1) {
                    currentPosition++;
                }
                break;

            case "last":
                currentPosition = items.length - 1;
                break;

            default:
                // nothing to do
                break;
        }

        // update listview
        updateListView();
    }

    void updateListView () {
        // change listview's background color
        for (int i = 0; i < items.length; i++) {
            if (i == currentPosition) {
                // selected item
                listView.getChildAt(i).setBackgroundColor(Color.CYAN);
            } else {
                listView.getChildAt(i).setBackgroundColor(Color.BLUE);
            }
        }

        // inform enclosing MainActivity of the row’s position just selected
        main.onMsgFromFragToMain("BLUE-FRAG", infos[currentPosition]); // send selected item's information
        txtBlue.setText(items[currentPosition]); // show selected item
    }
}
