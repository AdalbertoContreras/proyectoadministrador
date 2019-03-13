package com.example.serviamigoadmin.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestion.Gestion_administrador;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Mensaje_chat_asesoria;
import com.example.servimaigoadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Mensajes_Chat extends  RecyclerView.Adapter<Adapter_Mensajes_Chat.ViewHolderDatos>{
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias;
    private Chat_asesoria chat_asesoria;
    public Adapter_Mensajes_Chat(ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias, Chat_asesoria chat_asesoria)
    {
        this.mensaje_chat_asesorias = mensaje_chat_asesorias;
        this.chat_asesoria = chat_asesoria;
    }

    @Override
    public int getItemViewType(int position) {

        return mensaje_chat_asesorias.get(position).tipo_creador_mensaje_chat_asesoria;
    }

    @NonNull
    @Override
    public Adapter_Mensajes_Chat.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == 2)
        {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_mensaje_chat_assoria,null, false);
        }
        else
        {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_mensaje_chat_asesoria_recibir,null, false);
        }

        return new Adapter_Mensajes_Chat.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull Adapter_Mensajes_Chat.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(mensaje_chat_asesorias.get(i), chat_asesoria);
    }

    @Override
    public int getItemCount() {
        return mensaje_chat_asesorias.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder{
        private TextView contenidoTextView;
        private TextView fechatextView;
        private ConstraintLayout constraintLayout;
        private CircleImageView circleImageView;
        private View view;

        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            view = itemView;
            contenidoTextView = view.findViewById(R.id.contenidotextView_itemMensaje);
            fechatextView = view.findViewById(R.id.fechatextViewItem_mensaje);
            constraintLayout = view.findViewById(R.id.cuerpoLinearLayoutItemmensaje);
            circleImageView = view.findViewById(R.id.imagenPerfil);
        }

        public void setDatos(final Mensaje_chat_asesoria mensaje_chat_asesoria, Chat_asesoria chat_asesoria)       {
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
            if(mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria == 1)
            {

            }
            else
            {
                ArrayList<Administrador> administrador = new Gestion_administrador().generar_json(chat_asesoria.administrador);
                if(!administrador.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Imagen de perfil : " + administrador.get(0).url_foto_perfil_anterior, Toast.LENGTH_SHORT).show();
                    Picasso.with(view.getContext()).load(administrador.get(0).url_foto_perfil_administrador).into(circleImageView);
                }
                else
                {
                    Toast.makeText(view.getContext(), "Administrador no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
