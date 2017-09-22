package classes;

/**
 * Created by root on 9/12/17.
 */

public class CoinData {
    private String name;
    private String symbol;
    private float priceUSD;
    private float priceBTC;
    private float change1h;
    private float change24h;


    /**
     * Construct object that will hold current data for a particular coin
     * @param symbol
     * @param price_usd
     * @param price_btc
     * @param change_1h
     * @param change_24h
     */
    public CoinData(String name,
                    String symbol,
                    float price_usd,
                    float price_btc,
                    float change_1h,
                    float change_24h) {
        this.name = name;
        this.symbol = symbol;
        this.priceUSD = price_usd;
        this.priceBTC = price_btc;
        this.change1h = change_1h;
        this.change24h = change_24h;
    }

    public String getSymbol() {
        return symbol;
    }
    public float getPriceUSD() {
        return priceUSD;
    }
    public float getPriceBTC() {
        return priceBTC;
    }
    public float get1hChange() {
        return change1h;
    }
    public float get24hChange() {
        return change24h;
    }

    /**
     * Prints the coin's data to the console
     */
    public void printData() {
        System.out.println("Symbol: " + symbol);
        System.out.println("Price (USD): " + priceUSD);
        System.out.println("Price (BTC): " + priceBTC);
        System.out.println("Change 1h: " + change1h);
        System.out.println("Change 24h: " + change24h);
    }


}
