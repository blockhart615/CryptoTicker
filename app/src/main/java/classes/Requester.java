package classes;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Requester {

    private final String API_URL = "https://api.coinmarketcap.com/v1/ticker/"; // EXAMPLE: https://api.coinmarketcap.com/v1/ticker/bitcoin/
    private ArrayList<String> favorites;
    private HashMap<String, CoinData> coinDatas;

    /**
     * Constructor
     */
    public Requester() {
        coinDatas = new HashMap<>();
    }


    /**
     * perform API request to coinmarketcap.com to get coin data as JSON object.
     * @return
     */
    public void getCoinData(final Context context, ArrayList<String> coinsToRetrieve) {

        this.favorites = coinsToRetrieve;

        //Get the coin data for each of the coins in the favorites list
        for (String coinName : favorites) {

            // API request
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    API_URL + coinName,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //TODO do stuff
                            try {
                                JSONArray responseArray = new JSONArray(response);
                                JSONObject jsonResponse = responseArray.getJSONObject(0);

                                if (!jsonResponse.has("error")) {

                                    CoinData coinData = parseResponse(jsonResponse);
                                    coinDatas.put(coinData.getSymbol(), coinData);
                                    coinData.printData();


                                } else {
                                    Toast.makeText(context, jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "API Request Error", Toast.LENGTH_SHORT).show();
                        }
                    });  // END StringRequest

            MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        }  // END for each favorite coin

        //TODO After all the requests have been made, populate data into table


    }

    /**
     * parses JSONObject that is returned from coinmarketcap into coinData objects
     * @param coinsJSON
     * @return the coin data in a CoinData object
     */
    private CoinData parseResponse(JSONObject coinsJSON) {
        try {
            String name = coinsJSON.getString("name");
            float priceUSD = Float.parseFloat(coinsJSON.getString("price_usd"));
            float priceBTC = Float.parseFloat(coinsJSON.getString("price_btc"));
            float change1h = Float.parseFloat(coinsJSON.getString("percent_change_1h"));
            float change24h = Float.parseFloat(coinsJSON.getString("percent_change_24h"));

            CoinData coinData = new CoinData(name, priceUSD, priceBTC, change1h, change24h);
            return coinData;
        }
        catch (JSONException e){
            //TODO handle this exception
        }
        return null;
    }

}  // END Requester class
