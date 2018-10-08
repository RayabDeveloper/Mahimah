package net.rayab.mahimah.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import net.rayab.mahimah.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class WebApi {

    private onResponseResult onResponseResultListener;
    private onResponseResult2 onResponseResultListener2;
    private onResponseObjectResult onResponseObjectResultListener;
    private onResponseResultError onResponseResultErrorListener;

    public void setOnIncomingResult(onResponseResult2 onResponseResult2, onResponseResult onResponseResult, onResponseObjectResult onResponseObjectResult, onResponseResultError onResponseResultError) {
        this.onResponseResultListener = onResponseResult;
        this.onResponseResultListener2 = onResponseResult2;
        this.onResponseObjectResultListener = onResponseObjectResult;
        this.onResponseResultErrorListener = onResponseResultError;
    }

    private void ResultListener(JSONArray Result){
        onResponseResultListener.onResponseResults(Result);
    }
    private void ResultListener2(String Result){
        onResponseResultListener2.onResponseResults2(Result);
    }
    private void ResultObjectListener(JSONObject Result){
        onResponseObjectResultListener.onResponseObjectResults(Result);
    }

    private void ErrorResultListener(String Error){
        try{
            onResponseResultErrorListener.onResponseResultErrors(Error);
        }catch (Exception ignored){}
    }

    public interface onResponseResult{
        void onResponseResults(JSONArray Result);
    }
    public interface onResponseResult2{
        void onResponseResults2(String Result);
    }
    public interface onResponseObjectResult{
        void onResponseObjectResults(JSONObject Result);
    }

    public interface onResponseResultError{
        void onResponseResultErrors(String Error);
    }

    /*
    for(int i=0; i<ja.length(); i++){
        Object asd = ja.opt(i);
        Class<T> dClass = (Class<T>) asd.getClass();
        Object Obj = mClass.newInstance();
    }
     */

    public void API(final String WebApiUrl, final Map<String, String> Values){
        StringRequest putRequest = new StringRequest(Request.Method. POST, WebApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            ResultListener2(jo.getString("ok"));
                        }catch (Exception Ex) {
                            String Er = Ex.getMessage();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle Error Here , Like : String Er = error.getMessage.toString();
                        ErrorResultListener(error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                return Values;
            }

        };

        AppController.getInstance().addToRequestQueue(putRequest);
    }
    public void API2(final String WebApiUrl, final Map<String, String> Values){
        StringRequest putRequest = new StringRequest(Request.Method. POST, WebApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ResultListener2(response);
                        }catch (Exception Ex) {
                            String Er = Ex.getMessage();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle Error Here , Like : String Er = error.getMessage.toString();
                        ErrorResultListener(error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                return Values;
            }

        };

        AppController.getInstance().addToRequestQueue(putRequest);
    }

    public void API(final String WebApiUrl, final String ArrayName, final Map<String, String> Values, final Class mClass){
        StringRequest putRequest = new StringRequest(Request.Method. POST, WebApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONArray ja = jo.getJSONArray(ArrayName);
                            ResultListener(ja);
                        }catch (Exception Ex) {
                            String Er = Ex.getMessage();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle Error Here , Like : String Er = error.getMessage.toString();
                        ErrorResultListener(error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                return Values;
            }

        };

        AppController.getInstance().addToRequestQueue(putRequest);
    }

    public void API(final String WebApiUrl){
        StringRequest putRequest = new StringRequest(Request.Method. POST, WebApiUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            ResultObjectListener(jo);
                        }catch (Exception Ex) {
                            String Er = Ex.getMessage();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle Error Here , Like : String Er = error.getMessage.toString();
                        ErrorResultListener(error.toString());
                    }
                }
        ) {

        };

        AppController.getInstance().addToRequestQueue(putRequest);
    }

    /*
    How To Use :

    WebApi wAPI = new WebApi();
    wAPI.API("https://www.golsar-co.ir/Projects/Windows/mDatas/SELECT/Account.php", "ACCOUNT", Data_User.class);
    wAPI.setOnVariableChanged(new WebApi.onResponseResult() {
        @Override
        public void onResponseResults(JSONArray Result) {
            List<Data_Test> lDTest = new ArrayList<>();
            lDTest = JSONList.JToList(Result, Data_Test.class);
        }
    }, new WebApi.onResponseResultError() {
        @Override
        public void onResponseResultErrors(String Error) {
            //String Er = Error;
        }
    });
     */

}
