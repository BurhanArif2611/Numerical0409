package com.numerical.numerical.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.numerical.numerical.Models.LatestFeed.Example;
import com.numerical.numerical.Models.LatestFeed.Numeron;
import com.numerical.numerical.R;
import com.numerical.numerical.Utility.ErrorMessage;
import com.numerical.numerical.activity.LatestFeedDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Latest_numeruns_Adapter extends RecyclerView.Adapter<Latest_numeruns_Adapter.ViewHolder> {
    Context context;
    List<Numeron> stringList;


    public Latest_numeruns_Adapter(Context context, List<Numeron> numeronList) {
        this.stringList = numeronList;
        this.context = context;
        /* */
    }


    @NonNull
    @Override
    public Latest_numeruns_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.numerons_adapter, parent, false);
        Latest_numeruns_Adapter.ViewHolder viewHolder = new Latest_numeruns_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final Latest_numeruns_Adapter.ViewHolder holder, final int position) {
        String color = "", htmaltext = "";
        if (stringList.get(position).getSubType().equals("panel-angry")) {
            color = "#ef473a";
            htmaltext = "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + ".text_style{\n" + "line-height: 22px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + ".text1\n" + "{ background: " + color + ";\n" + "width: 112px;\n" + "border-radius:3px;\n" + "text-align: center;\n" + "padding: 6px 9px;\n" + "color: #fff;\n" + "float: left;\n" + "margin-right: 10px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"text_style\"><div class=\"text1\" style=\"\"> " + stringList.get(position).getNumeral() + " </div> \n" + "" + stringList.get(position).getTitle() + "\n" + "</div>\n" + "</body>\n" + "</html>";
        } else if (stringList.get(position).getSubType().equals("panel-formal")) {
            color = "#576657";
            htmaltext = "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + ".text_style{\n" + "line-height: 22px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + ".text1\n" + "{ background: " + color + ";\n" + "width: 112px;\n" + "border-radius:3px;\n" + "text-align: center;\n" + "padding: 6px 9px;\n" + "color: #fff;\n" + "float: left;\n" + "margin-right: 10px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"text_style\"><div class=\"text1\" style=\"\"> " + stringList.get(position).getNumeral() + " </div> \n" + "" + stringList.get(position).getTitle() + "\n" + "</div>\n" + "</body>\n" + "</html>";
        } else if (stringList.get(position).getSubType().equals("panel-sad")) {
            color = "#7F7F7F";
            htmaltext = "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + ".text_style{\n" + "line-height: 22px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + ".text1\n" + "{ background: " + color + ";\n" + "width: 112px;\n" + "border-radius:3px;\n" + "text-align: center;\n" + "padding: 6px 9px;\n" + "color: #fff;\n" + "float: left;\n" + "margin-right: 10px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"text_style\"><div class=\"text1\" style=\"\"> " + stringList.get(position).getNumeral() + " </div> \n" + "" + stringList.get(position).getTitle() + "\n" + "</div>\n" + "</body>\n" + "</html>";
        } else if (stringList.get(position).getSubType().equals("panel-calm")) {
            color = "#11c1f3";
            htmaltext = "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + ".text_style{\n" + "line-height: 22px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + ".text1\n" + "{ background: " + color + ";\n" + "width: 112px;\n" + "border-radius:3px;\n" + "text-align: center;\n" + "padding: 6px 9px;\n" + "color: #fff;\n" + "float: left;\n" + "margin-right: 10px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"text_style\"><div class=\"text1\" style=\"\"> " + stringList.get(position).getNumeral() + " </div> \n" + "" + stringList.get(position).getTitle() + "\n" + "</div>\n" + "</body>\n" + "</html>";
        } else if (stringList.get(position).getSubType().equals("panel-positive")) {
            color = "#4F54DD";
            htmaltext = "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + ".text_style{\n" + "line-height: 22px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + ".text1\n" + "{ background: " + color + ";\n" + "width: 112px;\n" + "border-radius:3px;\n" + "text-align: center;\n" + "padding: 6px 9px;\n" + "color: #fff;\n" + "float: left;\n" + "margin-right: 10px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"text_style\"><div class=\"text1\" style=\"\"> " + stringList.get(position).getNumeral() + " </div> \n" + "" + stringList.get(position).getTitle() + "\n" + "</div>\n" + "</body>\n" + "</html>";
        } else if (stringList.get(position).getSubType().equals("panel-royal")) {
            color = "#C441BB";
            htmaltext = "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + ".text_style{\n" + "line-height: 22px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + ".text1\n" + "{ background: " + color + ";\n" + "width: 112px;\n" + "border-radius:3px;\n" + "text-align: center;\n" + "padding: 6px 9px;\n" + "color: #fff;\n" + "float: left;\n" + "margin-right: 10px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"text_style\"><div class=\"text1\" style=\"\"> " + stringList.get(position).getNumeral() + " </div> \n" + "" + stringList.get(position).getTitle() + "\n" + "</div>\n" + "</body>\n" + "</html>";
        } else if (stringList.get(position).getSubType().equals("panel-happy")) {
            color = "#3c763d";
            htmaltext = "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + ".text_style{\n" + "line-height: 22px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + ".text1\n" + "{ background: " + color + ";\n" + "width: 112px;\n" + "border-radius:3px;\n" + "text-align: center;\n" + "padding: 6px 9px;\n" + "color: #fff;\n" + "float: left;\n" + "margin-right: 10px;\n" + "font-family: 'Titillium Web', sans-serif;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"text_style\"><div class=\"text1\" style=\"\"> " + stringList.get(position).getNumeral() + " </div> \n" + "" + stringList.get(position).getTitle() + "\n" + "</div>\n" + "</body>\n" + "</html>";
        } else {
            color = "#f57b20";
            //holder.numeral_count_tv.setBackgroundColor(context.getResources().getColor(R.color.panel_happy));
            /*holder.webview.setVisibility(View.GONE);
            holder.main_layout.setVisibility(View.VISIBLE);
            holder.numeral_tv.setText(stringList.get(position).getNumeral());
            holder.title_tv.setText(stringList.get(position).getTitle());
            if (stringList.get(position).getUrl().contains("https://numerical.co.in/numerons/")){
                holder.title_tv.setTextColor(context.getResources().getColor(R.color.weblink));
            }
            if (stringList.get(position).getAssetPath().contains("https://")) {
                Glide.with(context).load(stringList.get(position).getAssetPath()).into(holder.numerical_Image);
            } else if (stringList.get(position).getAssetPath().contains("http://")) {
                Glide.with(context).load(stringList.get(position).getAssetPath()).into(holder.numerical_Image);
            } else {
                Glide.with(context).load("https://numerical.co.in" + stringList.get(position).getAssetPath()).into(holder.numerical_Image);
            }*/
            String ImagePath = "";
            if (stringList.get(position).getAssetPath().contains("https://")) {
                ImagePath = stringList.get(position).getAssetPath();
            } else if (stringList.get(position).getAssetPath().contains("http://")) {
                ImagePath = stringList.get(position).getAssetPath();
            } else {
                ImagePath = "https://numerical.co.in" + stringList.get(position).getAssetPath();
            }
            try {
                if (stringList.get(position).getUrl().contains("https://numerical.co.in/numerons/")) {
                    htmaltext = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + "\n" + ".img_size img{\n" + "\n" + "max-width: 140px;\n" + "height: auto;\n" + "}\n" + ".entry-content{\n" + "padding: 0px 15px;\n" + "}\n" + ".img_size img.alignleft {\n" + "float: left;\n" + "margin: 0px 10px 8px 0px;\n" + "}\n" + ".heading-content{\n" + "color: Black;\n margin-bottom: 5px " + "}\n" + ".content{\n" + "color: " + "#f9a465" + ";\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"entry-content\">\n" + "<p class=\"img_size\">\n" + "<img class=\"alignleft\" src=\"" + ImagePath + "\" data-no-retina=\"\">\n" + "</p>\n" + "<h3 class=\"heading-content\">" + stringList.get(position).getNumeral() + "</h3>\n" + "<p class=\"content\">" + stringList.get(position).getTitle() + "</p>\n" + "</div>\n" + "</body>\n" + "</html>";
                } else {
                    htmaltext = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style type=\"text/css\">\n" + "\n" + ".img_size img{\n" + "\n" + "max-width: 140px;\n" + "height: auto;\n" + "}\n" + ".entry-content{\n" + "padding: 0px 15px;\n" + "}\n" + ".img_size img.alignleft {\n" + "float: left;\n" + "margin: 0px 10px 8px 0px;\n" + "}\n" + ".heading-content{\n" + "color: Black;\n margin-bottom: 5px " + "}\n" + ".content{\n" + "color:  ;\n" + "}\n" + "</style>\n" + "</head>\n" + "<body>\n" + "<div class=\"entry-content\">\n" + "<p class=\"img_size\">\n" + "<img class=\"alignleft\" src=\"" + ImagePath + "\" data-no-retina=\"\">\n" + "</p>\n" + "<h3 class=\"heading-content\">" + stringList.get(position).getNumeral() + "</h3>\n" + "<p class=\"content\">" + stringList.get(position).getTitle() + "</p>\n" + "</div>\n" + "</body>\n" + "</html>";
                }
            } catch (Exception e) {
            }
        }
        holder.webview.setWebViewClient(new WebViewClient());
        /*holder.webview.setClickable(false);*/
        /*holder.webview.setEnabled(false);*/
        holder.webview.setLongClickable(false);
        holder.webview.setHapticFeedbackEnabled(false);
        holder.webview.loadData(htmaltext, "text/html; charset=UTF-8", null);


        /*holder.webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stringList.get(position).getUrl().contains("https://numerical.co.in/numerons/")){
                    ((LatestFeedDetailActivity)context).getCalllink(stringList.get(position).getUrl());
                }
            }
        });*/
        holder.webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    if (stringList.get(position).getUrl().contains("https://numerical.co.in/numerons/")) {
                        ((LatestFeedDetailActivity) context).getCalllink(stringList.get(position).getUrl());
                    }
                } catch (Exception e) {
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numeral_tv, title_tv, maintitle_tv;
        WebView webview;
        LinearLayout main_layout;
        ImageView numerical_Image;

        public ViewHolder(View itemView) {
            super(itemView);
//            numeral_count_tv = (TextView) itemView.findViewById(R.id.numeral_count_tv);
//            title_tv = (TextView) itemView.findViewById(R.id.title_tv);
            webview = (WebView) itemView.findViewById(R.id.webview);
            main_layout = (LinearLayout) itemView.findViewById(R.id.main_layout);
            numerical_Image = (ImageView) itemView.findViewById(R.id.numerical_Image);
            numeral_tv = (TextView) itemView.findViewById(R.id.numeral_tv);
            title_tv = (TextView) itemView.findViewById(R.id.title_tv);


        }
    }

}