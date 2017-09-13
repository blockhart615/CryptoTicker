package classes;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Requester {

    private final String API_URL = "https://api.coinmarketcap.com/v1/ticker/";
    ArrayList<String> favorites;
//    FAVORITE_COINS = ["bitcoin", "ethereum", "omisego", "district0x", "ripple", "tierion", "monaco"]

    /**
     * Constructor
     */
    public Requester() {

    }


    /**
     * perform API request to coinmarketcap.com to get coin data as JSON object.
     * @return
     */
    private JSONObject getCoinData() {
        JSONObject jsonCoinData = new JSONObject();

        return jsonCoinData;
    }


    /**
     * Log the user in
     * @param password
     * @param context
     */
    public void login(final String username,
                      final String password,
                      final Context context) {
        this.username = username;
        this.password = password;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the resposne from the server
                        try {
                            loginResponse = new JSONObject(response);
                            if (!loginResponse.getBoolean("error")) {
                                //if login is successful, send user to Inbox
                                jwt = loginResponse.getString("jwt");
                                Intent intent = new Intent("com.toastabout.SecureChat.InboxActivity");
                                intent.putExtra("jwt", jwt);
                                intent.putExtra("username", username);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, loginResponse.getString("error_msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }//END ON_RESPONSE
                },//END RESPONSE LISTENER
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected HashMap<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

}
