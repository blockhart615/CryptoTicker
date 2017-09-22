package blockhart.cryptoticker;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import classes.CoinData;
import classes.Constants;
import classes.MySingleton;

public class TickerSummaryActivity extends AppCompatActivity {

    private TableLayout m_tableLayout;
    private HashMap<String, CoinData> m_coins;
    private ArrayList<String> m_favoriteCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_coins = new HashMap<>();
        initializeTable();

//        favoriteCoins = new ArrayList();
        m_favoriteCoins = new ArrayList<>(Arrays.asList("bitcoin",
                                                    "ethereum",
                                                    "omisego",
                                                    "district0x",
                                                    "ripple",
                                                    "tierion",
                                                    "monaco"));
        retrieveCoinData(m_favoriteCoins);



        // The sample FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ticker_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * perform API request to coinmarketcap.com to get coin data as JSON object.
     * @return
     */
    public void retrieveCoinData(ArrayList<String> coinsToRetrieve) {

        //Get the coin data for each of the coins in the favorites list
        for (String coinName : coinsToRetrieve) {

            // API request
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    Constants.API_URL + coinName,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //TODO do stuff
                            try {
                                JSONArray responseArray = new JSONArray(response);
                                JSONObject jsonResponse = responseArray.getJSONObject(0);

                                if (!jsonResponse.has("error")) {

                                    CoinData coinData = parseResponse(jsonResponse);
                                    m_coins.put(coinData.getSymbol(), coinData);
                                    coinData.printData();
                                    addRowToTable(coinData);
                                    //TODO populate data into table


                                } else {
                                    Toast.makeText(TickerSummaryActivity.this, jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(TickerSummaryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(TickerSummaryActivity.this, "API Request Error", Toast.LENGTH_SHORT).show();
                        }
                    });  // END StringRequest

            MySingleton.getInstance(TickerSummaryActivity.this).addToRequestQueue(stringRequest);
        }  // END for each favorite coin

    }

    /**
     * parses JSONObject that is returned from coinmarketcap into coinData objects
     * @param coinsJSON
     * @return the coin data in a CoinData object
     */
    private CoinData parseResponse(JSONObject coinsJSON) {
        try {
            String name = coinsJSON.getString("name");
            String symbol = coinsJSON.getString("symbol");
            float priceUSD = Float.parseFloat(coinsJSON.getString("price_usd"));
            float priceBTC = Float.parseFloat(coinsJSON.getString("price_btc"));
            float change1h = Float.parseFloat(coinsJSON.getString("percent_change_1h"));
            float change24h = Float.parseFloat(coinsJSON.getString("percent_change_24h"));

            CoinData coinData = new CoinData(name, symbol, priceUSD, priceBTC, change1h, change24h);
            return coinData;
        }
        catch (JSONException e){
            //TODO handle this exception
        }
        return null;
    }

    /**
     *
     */
    private void initializeTable() {
        m_tableLayout = (TableLayout) findViewById(R.id.coinData);
        m_tableLayout.setStretchAllColumns(true);
        m_tableLayout.setShrinkAllColumns(true);
        m_tableLayout.setPadding(0,15,0,15);
        final int TEXT_SIZE = 16;

        TableRow header = new TableRow(this);

        // Coin's symbol
        TextView symbol = new TextView(this);
        symbol.setTextSize(TEXT_SIZE);
        symbol.setTypeface(null, Typeface.BOLD);

        // USD price
        TextView priceUSD = new TextView(this);
        priceUSD.setTextSize(TEXT_SIZE);
        priceUSD.setTypeface(null, Typeface.BOLD);

        // BTC price
        TextView priceBTC = new TextView(this);
        priceBTC.setTextSize(TEXT_SIZE);
        priceBTC.setTypeface(null, Typeface.BOLD);

        // 1h change
        TextView change1h = new TextView(this);
        change1h.setTextSize(TEXT_SIZE);
        change1h.setTypeface(null, Typeface.BOLD);

        // 24h change
        TextView change24h = new TextView(this);
        change24h.setTextSize(TEXT_SIZE);
        change24h.setTypeface(null, Typeface.BOLD);

        symbol.setText("Symbol");
        header.addView(symbol);
        priceUSD.setText("Price USD");
        header.addView(priceUSD);
        priceBTC.setText("Price BTC");
        header.addView(priceBTC);
//        change1h.setText("1h Change");
//        header.addView(change1h);
        change24h.setText("24h Change");
        header.addView(change24h);

        m_tableLayout.addView(header);
    }

    /**
     *
     * @param coinData
     */
    private void addRowToTable(CoinData coinData) {
        //TODO make table row with coin's data
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));
        row.setPadding(25,25,25,25);


        TextView symbol = new TextView(TickerSummaryActivity.this);
        TextView priceUSD = new TextView(TickerSummaryActivity.this);
        TextView priceBTC = new TextView(TickerSummaryActivity.this);
        TextView change1h = new TextView(TickerSummaryActivity.this);
        TextView change24h = new TextView(TickerSummaryActivity.this);


        symbol.setText(coinData.getSymbol());
        row.addView(symbol);

        priceUSD.setText("$" + Float.toString(coinData.getPriceUSD()));
        row.addView(priceUSD);

        priceBTC.setText('Ƀ' + Float.toString(coinData.getPriceBTC()));
        row.addView(priceBTC);

//        change1h.setText(Float.toString(coinData.get1hChange()) + "%");
//        row.addView(change1h);

        change24h.setText(Float.toString(coinData.get24hChange()) + "%");
        if (coinData.get24hChange() < 0) {
            change24h.setTextColor(Constants.RED);
        }
        else if (coinData.get24hChange() > 0) {
            change24h.setTextColor(Constants.GREEN);
        }
        change24h.setGravity(Gravity.RIGHT);
        row.addView(change24h);

        m_tableLayout.addView(row);
    }

}
