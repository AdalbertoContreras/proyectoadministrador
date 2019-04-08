package com.comfacesar.serviamigoadmin.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comfacesar.gestion.Gestion_especialidad;
import com.comfacesar.gestion.Gestion_usuario;
import com.comfacesar.modelo.Chat_asesoria;
import com.comfacesar.modelo.Especialidad;
import com.comfacesar.modelo.Usuario;
import com.comfacesar.serviamigoadmin.Activity.ChatAsesoriaActivity;
import com.comfacesar.servimaigoadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        private CircleImageView asesoriaImageView;
        private TextView nombre_usuarioTextView;
        private TextView ultimo_mensajeTextView;
        private TextView ultima_fechaTextView;
        private TextView tipoAsesoriaTextView;

        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            asesoriaImageView = view.findViewById(R.id.fotoPerfilCircleImageView);
            nombre_usuarioTextView = view.findViewById(R.id.nombre_usuarioTextView);
            ultimo_mensajeTextView = view.findViewById(R.id.ultimo_mensajeTextView);
            ultima_fechaTextView = view.findViewById(R.id.fecha_ultimo_mensajeTextView);
            tipoAsesoriaTextView = view.findViewById(R.id.tipoAsesoriaTextView);
        }

        public void setDatos(final Chat_asesoria chat_asesoria, final FragmentManager fragmentManager)
        {
            ArrayList<Usuario> usuarios =  new Gestion_usuario().generar_json(chat_asesoria.usuario);
            ArrayList<Especialidad> especialidades = new Gestion_especialidad().generar_json(chat_asesoria.especialidad);
            final Usuario usuario;
            if(!usuarios.isEmpty())
            {
                usuario = usuarios.get(0);
                nombre_usuarioTextView.setText(usuario.nombres_usuario);
                Picasso.with(view.getContext())
                        .load(usuario.foto_perfil_usuario)
                        .placeholder(R.drawable.perfil2)
                        .error(R.drawable.ic_iconousuario)
                        .into(asesoriaImageView);
            }
            else
            {
                nombre_usuarioTextView.setText(" ");
                Picasso.with(view.getContext())
                        .load(R.drawable.ic_iconousuario)
                        .into(asesoriaImageView);
            }
            if(!especialidades.isEmpty())
            {
                Especialidad especialidad = especialidades.get(0);
                tipoAsesoriaTextView.setText(especialidad.nombre_especialidad);
            }
            ultima_fechaTextView.setText(chat_asesoria.ultima_fecha_chat_asesoria + " " + chat_asesoria.ultima_hora_chat_asesoria);
            ultimo_mensajeTextView.setText(chat_asesoria.ultimo_mensaje_chat_asesoria);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), ChatAsesoriaActivity.class);
                    ChatAsesoriaActivity.chat_asesoria = chat_asesoria;
                    (view.getContext()).startActivity(intent);
                }
            });
        }
    }
}
