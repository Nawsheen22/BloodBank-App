package com.nawsheen.bloodline.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nawsheen.bloodline.R;
import com.nawsheen.bloodline.Utils.Endpoints;
import com.nawsheen.bloodline.Utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText numberEt, passwordEt;
    Button submit_button;
    TextView signUpText;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);
        numberEt= findViewById(R.id.number);
        passwordEt = findViewById(R.id.password);
        submit_button=findViewById(R.id.submit_button);
        signUpText=findViewById(R.id.sign_up_text);
        signUpText.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));


            }
        });


        submit_button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                numberEt.setError(null);
                passwordEt.setError(null);
                String number = numberEt.getText().toString();
                String password= passwordEt.getText().toString();
                if(isValid(number,password)){
                    login(number,password);

              }

            }
        });
    }

















   private void login(String number,String password){
       StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.login_url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               if (response.equals("Success")){
                   Toast.makeText(LoginActivity.this,response, Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(LoginActivity.this,MainActivity.class));
                   LoginActivity.this.finish();


               }else{
                   Toast.makeText(LoginActivity.this,response, Toast.LENGTH_SHORT).show();
               }
           }
       }, new Response.ErrorListener (){
           @Override
           public void onErrorResponse(VolleyError error){
               Toast.makeText(LoginActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
               Log.d("VOLLEY",error.getMessage());
           }}){

           @Override
       protected Map<String,String> getParams()throws AuthFailureError {
           Map<String, String> params = new HashMap<>();

           params.put("password",password);
           params.put("number",number);

           return params;
       }

       };

       VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

   }

private boolean isValid(String number,String password){
       if (number.isEmpty()){
           showMessage("Empty Mobile Number");
           numberEt.setError("Empty Mobile Number");
          return false;
       } else if(password.isEmpty()){
           showMessage("Empty Password");
           passwordEt.setError("Empty Mobile Number");
           return false;
       }
      return true;

}
private void showMessage(String msg){
       Toast.makeText(  this,msg, Toast.LENGTH_SHORT).show();
}

}