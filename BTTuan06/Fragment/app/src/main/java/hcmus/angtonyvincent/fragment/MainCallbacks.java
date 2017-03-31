package hcmus.angtonyvincent.fragment;

// method(s) to pass messages from fragments to MainActivity
public interface MainCallbacks {
    public void onMsgFromFragToMain (String sender, String strValue);
}