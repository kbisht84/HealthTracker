package com.kanakb.healthtracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kanakb.healthtracker.dialog.ErrorDialog;
import com.kanakb.healthtracker.validation.Validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signUpButton;
    RequestQueue requestQueue;
    String insertUserUrl = "http://10.100.10.68/tutorial/insertUser.php";
    TextWatcher watcher;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstname = (EditText) findViewById(R.id.editText);
        lastName = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        confirmPassword = (EditText) findViewById(R.id.editText5);
        signUpButton = (Button) findViewById(R.id.button3);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    firstname.setError("First Name can't be empty");
                else if (!s.toString().matches("^[a-zA-Z\\\\s]+")) {
                    firstname.setError("First name not valid");
                } else if (s.length() > 19)
                    firstname.setError("Limit exceeds");


            }
        });


        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    lastName.setError("Last Name can't be empty");
                else if (!s.toString().matches("^[a-zA-Z\\\\s]+")) {
                    lastName.setError("Last name not valid");
                } else if (s.length() > 19)
                    lastName.setError("Limit exceeds");


            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    email.setError("Email can't be empty");
                else if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    email.setError("Email is not valid");
                } else if (s.length() > 19)
                    email.setError("Limit exceeds");


            }
        });


        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().equals(password.getText().toString())) {
                    confirmPassword.setError("Password does not match");
                } else if (s.length() > 19)
                    confirmPassword.setError("Limit exceeds");


            }
        });


        //adding listener


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!firstname.getText().toString().equals("") && !lastName.getText().toString().equals("") && !email.getText().toString().equals("") && !password.getText().toString().equals("") && !confirmPassword.getText().toString().equals("")) {



                    StringRequest request = new StringRequest(Request.Method.POST, insertUserUrl, new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {

                            Intent intent;
                            if (response.equals("")) {
                                intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                                }
                            new AlertDialog.Builder(SignUpActivity.this)
                                    .setTitle("Email Already exists")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("Email", email.getText().toString());
                            parameters.put("FirstName", firstname.getText().toString());
                            parameters.put("LastName", lastName.getText().toString());
                            parameters.put("Password", password.getText().toString());
                            parameters.put("ConfirmPassword", confirmPassword.getText().toString());

                            return parameters;

                        }


                    };
                    requestQueue.add(request);




                    //adding another event



                }
            }

        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
