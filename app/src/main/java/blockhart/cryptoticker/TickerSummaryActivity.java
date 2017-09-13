package blockhart.cryptoticker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

import classes.CoinData;
import classes.Requester;

public class TickerSummaryActivity extends AppCompatActivity {

    private Requester requester;
    ArrayList<CoinData> coins;
    ArrayList<String> favoriteCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO Get coin data from coinmarketcap
        requester = new Requester();
        favoriteCoins = new ArrayList();
        coins = retrieveCoinData(favoriteCoins);
        //TODO put coin data into ExpandableListView

        //TODO make table row with coin's data
        TableLayout coinTable = (TableLayout) findViewById(R.id.coinData);
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
//        row.addView(COLUMN?);



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

    private ArrayList<CoinData> retrieveCoinData(ArrayList<String> coinsToRetrieve) {
        ArrayList coinData = new ArrayList();

        //for each coin to retrieve, get the data and
        requester.getCoinData();



        return coinData;
    }
}
