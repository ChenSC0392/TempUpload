package tw.tcirc.tempUpload;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;

public class FeverPassAPI {

    private CookieManager cookieManager;
    private RequestQueue requestQueue;

    FeverPassAPI(Context context) {
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        requestQueue = Volley.newRequestQueue(context);
    }

    private StringRequest makePostRequest(String url, final Map<String, String> param, final StringRequest after) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FeverPassAPI.makePostRequest", response);
                if (after != null) {
                    requestQueue.add(after);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("FeverPassAPI.makePostRequest", error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return param;
            }
        };
        return request;
    }

    public void submitTemp(final String username, final String password, final String temperature) {
        StringRequest submit = makePostRequest("https://tcfsh.feverpass.life/", new HashMap<String, String>() {{
            put("temperature", temperature);
            put("type", "2");
        }}, null);
        StringRequest login = makePostRequest("https://tcfsh.feverpass.life/login", new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }}, submit);
        requestQueue.add(login);
    }
}
