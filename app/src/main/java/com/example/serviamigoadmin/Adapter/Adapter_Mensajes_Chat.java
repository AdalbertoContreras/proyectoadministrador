package com.example.serviamigoadmin.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.modelo.Chat_asesoria;
import com.example.modelo.Mensaje_chat_asesoria;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;

public class Adapter_Mensajes_Chat extends  RecyclerView.Adapter<Adapter_Mensajes_Chat.ViewHolderDatos>{
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesoria;
    public Adapter_Mensajes_Chat(ArrayList<Mensaje_chat_asesoria> chat_asesorias)
    {
        this.mensaje_chat_asesoria = chat_asesorias;
    }

    @NonNull
    @Override
    public Adapter_Mensajes_Chat.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_mensaje_chat,null, false);
        return new Adapter_Mensajes_Chat.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull Adapter_Mensajes_Chat.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(mensaje_chat_asesoria.get(i));
    }

    @Override
    public int getItemCount() {
        return mensaje_chat_asesoria.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder{
        private TextView contenidoTextView;
        private TextView fechatextView;
        private ConstraintLayout constraintLayout;
        private View view;

        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            contenidoTextView = itemView.findViewById(R.id.contenidotextView_itemMensaje);
            fechatextView = itemView.findViewById(R.id.fechatextViewItem_mensaje);
            constraintLayout = itemView.findViewById(R.id.cuerpoLinearLayoutItemmensaje);
            this.view = itemView;
        }

        public void setDatos(final Mensaje_chat_asesoria mensaje_chat_asesoria)       {
            contenidoTextView.setText(mensaje_chat_asesoria.contenido_mensaje_chat_asesoria);
            fechatextView.setText(mensaje_chat_asesoria.fecha_envio_mensaje_chat_asesoria + " " + mensaje_chat_asesoria.hora_envio_mensaje_asesoria);
            if(mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria == 2)
            {
                contenidoTextView.setTextColor(Color.BLUE);
            }
            else
            {
                contenidoTextView.setTextColor(Color.RED);
            }
        }
    }
}
