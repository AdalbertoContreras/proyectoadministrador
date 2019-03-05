package com.example.serviamigoadmin.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gestion.Gestion_usuario;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Usuario;
import com.example.serviamigoadmin.Fragment.ChatUsuarioFragment;
import com.example.servimaigoadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterChat extends  RecyclerView.Adapter<AdapterChat.ViewHolderDatos>{
    private ArrayList<Chat_asesoria> chat_asesorias;
    private FragmentManager fragmentManager;
    public AdapterChat(ArrayList<Chat_asesoria> chat_asesorias, FragmentManager fragmentManager)
    {
        this.chat_asesorias = chat_asesorias;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AdapterChat.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_chat_asesoria,null, false);
        return new AdapterChat.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(chat_asesorias.get(i), this.fragmentManager);
    }

    @Override
    public int getItemCount() {
        return chat_asesorias.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder{
        private View view;
        private ImageView asesoriaImageView;
        private TextView nombre_usuarioTextView;
        private TextView ultimo_mensajeTextView;
        private TextView ultima_fechaTextView;

        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            asesoriaImageView = view.findViewById(R.id.imagen_asesoriaImageView);
            nombre_usuarioTextView = view.findViewById(R.id.nombre_usuarioTextView);
            ultimo_mensajeTextView = view.findViewById(R.id.ultimo_mensajeTextView);
            ultima_fechaTextView = view.findViewById(R.id.fecha_ultimo_mensajeTextView);
        }

        public void setDatos(final Chat_asesoria chat_asesoria, final FragmentManager fragmentManager)
        {
            ArrayList<Usuario> usuarios =  new Gestion_usuario().generar_json(chat_asesoria.usuario);
            final Usuario usuario;
            if(!usuarios.isEmpty())
            {
                usuario = usuarios.get(0);
                nombre_usuarioTextView.setText(usuario.nombres_usuario + " " + usuario.apellidos_usuario);
            }
            else
            {
                nombre_usuarioTextView.setText(" ");
            }
            ultima_fechaTextView.setText(chat_asesoria.ultima_fecha);
            ultimo_mensajeTextView.setText(chat_asesoria.ultimo_mensaje);
            Picasso.with(view.getContext()).load(R.drawable.imagen_nueva).into(asesoriaImageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new ChatUsuarioFragment();
                    ChatUsuarioFragment.chat_asesoria = chat_asesoria;
                    fragmentManager.beginTransaction().replace(R.id.framengMaster,fragment).commit();
                }
            });
        }
    }
}
