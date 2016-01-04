package com.example.volleytest.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.transform.ErrorListener;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.volleytest.R;
import com.example.volleytest.bean.Info;
import com.example.volleytest.cache.BitmapCache;

public class MListAdapter extends BaseAdapter{
	private Context ctx;
	private ArrayList<Info> infos;
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
	public MListAdapter(Context ctx, ArrayList<Info> infos) {
		this.ctx = ctx;
		this.infos = infos;
		mQueue = Volley.newRequestQueue(ctx);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Info getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 加载网络数据
	 */
    private void getJSONByVolley() {  
       //初始化请求对象
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        String JSONDataUrl = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";  
  
        
        //添加请求动作及监听
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(  
                Request.Method.GET,   
                JSONDataUrl,   
                null,  
               new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject arg0) {
                        Log.e("status", "Success");
                    }
            },
            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                        Log.e("status", "Fail");
                }
                
            });  
        jsonObjectRequest.setTag("reques");//用于标记此次请求
        requestQueue.add(jsonObjectRequest); 
    }  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(ctx).inflate(R.layout.item, null);
		ImageView imageView  = (ImageView) convertView.findViewById(R.id.item);
		ImageListener listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
        mImageLoader.get(getItem(position).getUrl(), listener);
        getJSONByVolley();
		return convertView;
	}


}
