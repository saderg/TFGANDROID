package com.example.autoregistros.conectaAPI;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.autoregistros.entidades.User;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Methods extends AppCompatActivity {

    Context context = this;

    //USER

    public void getById(int id){
        final ProgressDialog loading = new ProgressDialog(Methods.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String url = "http://192.168.1.31:8086/app/users/user/get?id_usuario=" + id;
        User user = new User();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());


                try {
                    Log.d("JSON", String.valueOf(response));
                    loading.dismiss();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    Log.d("JSONOBJECT", String.valueOf(jsonObject));
                    user.setId_usuario(jsonObject.getInt("id_usuario"));
                    user.setUser_name(jsonObject.getString("user_name"));
                    user.setPassword(jsonObject.getString("password"));
                    user.setEmail_adress(jsonObject.getString("email_address"));
                    user.setDate_of_birth(jsonObject.getString("date_of_birth"));

                    //PRUEBA
                    System.out.println(user.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Toast.makeText(Methods.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        Volley.newRequestQueue(this).add(req);

    }





    //EMOTIONS
    private void deleteEmotion(int id) {

        final ProgressDialog loading = new ProgressDialog(Methods.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String url = "http://192.168.56.1:8086/app/emotions/delete/emotion?id_emotion=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.d("response_del", response + "  " + url);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.d("error del", ""+error);
            }
        }){
            protected HashMap<String, String> getParams() throws AuthFailureError {
                loading.dismiss();
                HashMap<String, String> map = new HashMap<>();
                map.put("id_Emotion", "4");
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }





}
