package com.software.dafepa.proyectolaescalera.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.software.dafepa.proyectolaescalera.Objects.Evento;
import com.software.dafepa.proyectolaescalera.R;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by pablolira on 3/3/18.
 */

public class Adapter_eventos extends BaseAdapter {

    private ArrayList<Evento> eventos;
    private LayoutInflater inflater;
    private Activity activity;

    public Adapter_eventos(Activity activity){
        eventos = new ArrayList<>();
        inflater = LayoutInflater.from(activity);
        this.activity = activity;

    }

    @Override
    public int getCount() {
        return eventos.size();
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
        view = inflater.inflate(R.layout.customcard_eventos, null);
        ViewHolder holder = new ViewHolder();

        holder.titulo = view.findViewById(R.id.txt_title);
        holder.tipo = view.findViewById(R.id.txt_tipo);
        holder.descripcion = view.findViewById(R.id.txt_description);

        Evento e;
        if (i < eventos.size()) {
            e = eventos.get(i);

            holder.titulo.setText(e.getTitulo());
            String tipo;
            if (e.getBusco()) {
                tipo = "Busco";
            } else {
                tipo = "Ofrezco";
            }
            holder.tipo.setText(tipo);
            holder.descripcion.setText(e.getDescripcion());

            holder.ly_img = view.findViewById(R.id.ly_image_body);
            holder.img_perfil = view.findViewById(R.id.img_perfil);
            Drawable img = new BitmapDrawable(activity.getResources(), e.getImg());
            Drawable img_perfil = new BitmapDrawable(activity.getResources(), e.getImg_usuario());
            holder.ly_img.setBackground(img);
            holder.img_perfil.setImageDrawable(img_perfil);

            if (eventos.size() >= i) {
                holder.ly_main = (LinearLayout) view.findViewById(R.id.ly_main);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(0, 0, 0, 65);
                holder.ly_main.setLayoutParams(params);
            }
        }
        return view;
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    static class ViewHolder{
        TextView titulo;
        TextView tipo;
        TextView descripcion;

        LinearLayout ly_main;
        RelativeLayout ly_img;
        ImageView img_perfil;
    }
}
