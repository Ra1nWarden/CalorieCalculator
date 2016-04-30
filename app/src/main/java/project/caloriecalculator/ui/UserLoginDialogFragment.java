package project.caloriecalculator.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import project.caloriecalculator.R;

/**
 * A fragment for the user to log in.
 */
public final class UserLoginDialogFragment extends DialogFragment {

    public static final String TAG = "LoginDiag";
    private LinearLayout forms;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        forms = (LinearLayout) inflater.inflate(R.layout.login_window, null);
        final EditText userNameField = (EditText) forms.findViewById(R.id.use_name_field);
        final EditText passwordField = (EditText) forms.findViewById(R.id.password_field);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.login)
                .setView(forms)
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (userNameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(UserLoginDialogFragment.this)
                                    .commit();
                        }
                    }
                })
                .setCancelable(false);
        Dialog diag = builder.create();
        diag.setCanceledOnTouchOutside(false);
        return diag;
    }

}
