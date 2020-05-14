/**
 * 05/14, 2020
 *
 * @author nevikw39
 */

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

/**
 * A simple API to submit daily body temperature to feverpass.life.
 */
class FeverPassAPI {

    private CookieManager cookieManager;
    private RequestQueue requestQueue;

    /**
     * Initialize the API instance with an {@code Context}.
     * @param context An {@code android.content.Context} object, such as a MainActivity.
     */
    FeverPassAPI(Context context) {
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Make a post request based on Volley library.
     * @param url The target url.
     * @param param The form data.
     * @param after An request which will be added to requestQueue after this request success. If it is null, then nothing happen.
     * @return The {@code com.android.volley.toolbox.StringRequest} object.
     */
    private StringRequest makePostRequest(String url, final Map<String, String> param, final StringRequest after) {
        return new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
    }

    /**
     * Submit given body temperature.
     *
     * @param username    The student id which is used to login to feverpass.life
     * @param password    The password.
     * @param temperature The temperature in Celsius degree.
     */
    void submitTemp(final String username, final String password, final String temperature) {
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
