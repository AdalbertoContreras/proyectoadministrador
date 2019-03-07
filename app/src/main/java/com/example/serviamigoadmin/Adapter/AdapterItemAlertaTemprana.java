package com.example.serviamigoadmin.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_asunto;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;
import com.example.modelo.Usuario;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterItemAlertaTemprana extends  RecyclerView.Adapter<AdapterItemAlertaTemprana.ViewHolderDatos>{
    private ArrayList<Alerta_temprana> alertas;
    public int h;
    public AdapterItemAlertaTemprana(ArrayList<Alerta_temprana> alerta_tempranas)
    {
        this.alertas = alerta_tempranas;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alerta_temprana,null, false);
        return new ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(alertas.get(i));
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView telefono;
        private TextView direccion;
        private TextView descripcion;
        private TextView asunto;
        private View view;
        private Usuario datos_usuario;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreTextViiewConsultaAlertaTemprana);
            direccion = itemView.findViewById(R.id.direccionTextViewConsultaAlertaTemprana);
            telefono = itemView.findViewById(R.id.telefonoTextViewConsultaAlertaTemprana);
            descripcion = itemView.findViewById(R.id.descripcionTextViewConsultaAlertaTemprana);
            asunto = itemView.findViewById(R.id.asuntoTextViiewCobsultaAlertaTemprana);
            this.view = itemView;
        }

        private void consultar_datos_usuario(Alerta_temprana alerta_temprana)
        {
            llenar_datos_usuario(alerta_temprana.usuario);
        }

        private void llenar_datos_usuario(String response)
        {
            ArrayList<Usuario> aux = new Gestion_usuario().generar_json(response);
            if(!aux.isEmpty())
            {
                datos_usuario = aux.get(0);
                nombre.setText(datos_usuario.nombres_usuario + " " +  datos_usuario.apellidos_usuario);
                direccion.setText(datos_usuario.direccion_usuario);
                telefono.setText(datos_usuario.telefono_usuario);
            }
            else
            {
                nombre.setText("No encontrado");
                direccion.setText("No encontradp");
                telefono.setText("No encontrado");
            }
        }

        private void consultar_asunto(final Alerta_temprana alerta_temprana)
        {
            ArrayList<Asunto> aux = new Gestion_asunto().generar_json(alerta_temprana.asunto);
            if(!aux.isEmpty())
            {
                asunto.setText(aux.get(0).nombre_asunto);
            }
            else
            {
                asunto.setText("no enconrado");
            }
        }
        public void setDatos(final Alerta_temprana Alerta_temprana)
        {
            consultar_datos_usuario(Alerta_temprana);
            consultar_asunto(Alerta_temprana);
            descripcion.setText(Alerta_temprana.descripcion_alerta_temprana);
        }
    }
}
