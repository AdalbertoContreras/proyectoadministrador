package com.example.extra;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;


public class MySocialMediaSingleton
{
    private static MySocialMediaSingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private MySocialMediaSingleton(Context context) {
        MySocialMediaSingleton.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySocialMediaSingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new MySocialMediaSingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public  void addToRequestQueue(Request req) {
        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        getRequestQueue().add(req);

    }

    public static StringRequest volley_consulta(String url, final Map<String, String> parametros, Response.Listener<String> stringListener, Response.ErrorListener errorListener)
    {
        return new StringRequest(Request.Method.POST, url,stringListener,errorListener) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params_aux = parametros;
                return parametros;
            }
        };
        //MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(postRequest);
    }

    public static Response.ErrorListener errorListener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
    }
}
