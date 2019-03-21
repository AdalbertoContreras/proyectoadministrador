package com.example.serviamigoadmin.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_imagen_noticia;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Mensaje_chat_asesoria;
import com.example.modelo.Noticia;
import com.example.modelo.Usuario;
import com.example.serviamigoadmin.Activity.ModificarNoticiaActivity;
import com.example.serviamigoadmin.Fragment.Modificar_noticiaFragment;
import com.example.servimaigoadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptaderNoticiaModificar extends  RecyclerView.Adapter<AdaptaderNoticiaModificar.ViewHolderDatos>{
    private ArrayList<Noticia> noticiaArrayList;
    private FragmentManager fragmentManager;

    public AdaptaderNoticiaModificar(ArrayList<Noticia> noticiaArrayList, FragmentManager fragmentManager)
    {
        this.noticiaArrayList = noticiaArrayList;
        this.fragmentManager =  fragmentManager;
    }


    @NonNull
    @Override
    public AdaptaderNoticiaModificar.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_noticia_modificar,null, false);
        return new AdaptaderNoticiaModificar.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull AdaptaderNoticiaModificar.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(noticiaArrayList.get(i), fragmentManager);
    }

    @Override
    public int getItemCount() {
        return noticiaArrayList.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder{
        private TextView tituloArticuloTextView;
        private TextView numeroMeGustaTextView;
        private CircleImageView imagenArticuloCircleImageView;
        private View view;

        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            view = itemView;
            tituloArticuloTextView = view.findViewById(R.id.tituloArticuloTextView);
            numeroMeGustaTextView = view.findViewById(R.id.numeroMeGustaTextView);
            imagenArticuloCircleImageView = view.findViewById(R.id.imagenArticuloCircleImageView);
        }

        public void setDatos(final Noticia noticia, final FragmentManager fragmentManager)
        {
            tituloArticuloTextView.setText(noticia.titulo_noticia);
            numeroMeGustaTextView.setText(noticia.numero_me_gusta + " me gusta");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModificarNoticiaActivity.noticiaModificar = noticia;
                    Intent intent = new Intent(view.getContext(), ModificarNoticiaActivity.class);
                    (view.getContext()).startActivity(intent);
                }
            });
            ArrayList<Imagen_noticia> imagen_noticias = new Gestion_imagen_noticia().generar_json(noticia.json_imagenes);
            if(imagen_noticias.isEmpty())
            {
                Picasso.with(view.getContext()).load(R.drawable.perfil2).into(imagenArticuloCircleImageView);
            }
            else
            {
                Picasso.with(view.getContext()).load(imagen_noticias.get(0).url_imagen_noticia).placeholder(R.drawable.perfil2)
                        .error(R.drawable.perfil2).into(imagenArticuloCircleImageView);
            }

        }
    }
}