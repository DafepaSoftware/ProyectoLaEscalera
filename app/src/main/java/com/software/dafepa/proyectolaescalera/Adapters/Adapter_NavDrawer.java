package com.software.dafepa.proyectolaescalera.Adapters;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.software.dafepa.proyectolaescalera.R;

import java.util.ArrayList;

/**
 * Created by pablolira on 16/2/18.
 */

public class Adapter_NavDrawer extends BaseAdapter {

    private Activity activity_;
    private ArrayList<String> title_;
    private ArrayList<Integer> img_id_;
    private LayoutInflater inflater_;

    public Adapter_NavDrawer(Activity activity, ArrayList<String> title, ArrayList<Integer> img_id){
        activity_ = activity;
        title_ = title;
        img_id_ = img_id;
        inflater_ = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return title_.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater_.inflate(R.layout.navdrawer_button, null);
        ViewHolder holder = new ViewHolder();

        holder.img_ = view.findViewById(R.id.img);
        holder.txt_ = view.findViewById(R.id.txt);
        holder.ly_main_ = view.findViewById(R.id.ly_main);

        holder.img_.setImageDrawable(activity_.getDrawable(img_id_.get(i)));
        holder.txt_.setText(title_.get(i));




        return view;
    }

    static class ViewHolder{
        ImageView img_ = null;
        TextView txt_ = null;
        LinearLayout ly_main_ = null;
    }
}
