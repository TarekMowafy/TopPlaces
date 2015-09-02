package ulmon.task.com.topplaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    private RequestQueue requestQueue ;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private String URL ="https://hub.ulmon.com/rest/map/discovery?access_token=C3AE7&installation_uuid=6fy&device=x86_64";
    JSONObject params ;
    public TopImage topImage;
    private CardAdapter cardAdapter;
    private  RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);

        makeJsonRequest();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);



    }

    public void makeJsonRequest(){

        params = new JSONObject();
        try {
            params.put("mapId", 3);
            params.put("userLang","en");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONObject returnOBJ = response.getJSONObject("return");
                    JSONObject topimageOBJ = returnOBJ.getJSONObject("topImage");
                    JSONArray poisArr = returnOBJ.getJSONArray("pois");
                    JSONArray imagesArr = returnOBJ.getJSONArray("images");

                    topImage = new TopImage();
                    topImage.urlPreview = topimageOBJ.getString("urlPreview");
                    topImage.urlLarge = topimageOBJ.getString("urlLarge");

                    Log.e("image url", topImage.urlPreview);
                    cardAdapter = new CardAdapter(getBaseContext(),topImage);
                    mRecyclerView.setAdapter(cardAdapter);

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener(){
            @Override
            public void  onErrorResponse(VolleyError error){
                Log.e("Error: ", error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
