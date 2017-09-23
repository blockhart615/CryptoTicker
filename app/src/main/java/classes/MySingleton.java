package classes;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private int requestsPending;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    /**
     *
     * @param context
     */
    private MySingleton(Context context)
    {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        requestsPending = 0;
    }

    /**
     *
     * @param context
     * @return
     */
    public static synchronized MySingleton getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    /**
     *
     * @return
     */
    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     *
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
        requestsPending++;
    }

    /**
     *
     */
    public void decrementPendingRequests()
    {
        requestsPending--;
    }

    /**
     *
     */
    public int getNumRequestsPending()
    {
        return requestsPending;
    }
}