package nsa.com.museum.AdminActivity;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import nsa.com.museum.R;

/**
 * A login screen that offers login via email/password.
 */
public class AdminLogin extends Activity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get inputs to string and check if they are equal to admin and password
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

                if (email.contains("admin") && password.contains("password")) {
                    Intent admin = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(admin);
                }

                else {
                    Snackbar snackbar = Snackbar
                            .make(v, "Try again!", Snackbar.LENGTH_LONG);
                    snackbar.show();


                }

            }
        });
    }
}

