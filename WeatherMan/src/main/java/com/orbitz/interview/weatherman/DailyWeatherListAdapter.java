package com.orbitz.interview.weatherman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orbitz.interview.weatherman.model.DailyWeather;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Based on: http://thinkandroid.wordpress.com/2012/06/13/lazy-loading-images-from-urls-to-listviews/
 * Created by sjain on 6/1/13.
 */
public class DailyWeatherListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<DailyWeather> dailyWeatherList = new ArrayList<DailyWeather>();
    private Map<String, Bitmap> imageMap = new HashMap<String, Bitmap>();

    public DailyWeatherListAdapter(Context context, List dailyWeatherList) {
        layoutInflater = LayoutInflater.from(context);
        this.dailyWeatherList = dailyWeatherList;
        loadImages();
    }

    public int getCount() {
        return dailyWeatherList.size();
    }

    public DailyWeather getItem(int position) {
        return dailyWeatherList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        DailyWeather s = dailyWeatherList.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.daily_weather_row_layout, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.view_weather_info);
            holder.pic = (ImageView) convertView.findViewById(R.id.image_weather_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(s.toDebugString());
        Bitmap image = imageMap.get(s.getWeatherIconUrl());
        if (image != null) {
            holder.pic.setImageBitmap(image);
        } else {
            // display default image
//            holder.pic.setImageResource(R.drawable.generic_weather_icon);
        }
        return convertView;
    }

    public void setList(List<DailyWeather> dailyWeathers) {
        this.dailyWeatherList = dailyWeathers;
        loadImages();
    }

    static class ViewHolder {
        TextView name;
        ImageView pic;
    }

    private void loadImages() {
        for(DailyWeather daily: dailyWeatherList) {
            if (!imageMap.containsKey(daily.getWeatherIconUrl())) {
                loadImage(daily.getWeatherIconUrl());
            }
        }
    }

    public void loadImage(String url) {
        if (url != null && !url.equals("")) {
            new ImageLoadTask().execute(url);
        }
    }

    private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

        private String url;

        protected Bitmap doInBackground(String... param) {
            url = param[0];
            Log.i("ImageLoadTask", "Attempting to load image URL: " + url);
            try {
                Bitmap b = getBitmapFromURL(url);
                return b;
            } catch (Exception e) {
                Log.e("ImageLoadTask", "failed to fetch image", e);
                return null;
            }
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                DailyWeatherListAdapter.this.imageMap.put(url, image);
                DailyWeatherListAdapter.this.notifyDataSetChanged();
            }
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}