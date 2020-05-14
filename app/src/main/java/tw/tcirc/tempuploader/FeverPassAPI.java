package tw.tcirc.tempuploader;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FeverPassAPI {

    private RequestQueue requestQueue;

    FeverPassAPI(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    private static StringRequest makePostRequest(String url, final Map<String, String> param) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FeverPassAPI.makePostRequest", response);
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
        StringRequest login = makePostRequest("https://tcfsh.feverpass.life/login", new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }});
        requestQueue.add(login);
        StringRequest submit = makePostRequest("https://tcfsh.feverpass.life/", new HashMap<String, String>() {{
            put("temperature", temperature);
            put("type", "2");
        }});
        requestQueue.add(submit);
    }
}
