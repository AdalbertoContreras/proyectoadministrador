package com.example.serviamigoadmin.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_alerta_temprana;
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
        private CheckBox atendidoCheckBox;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreTextViiewConsultaAlertaTemprana);
            direccion = itemView.findViewById(R.id.direccionTextViewConsultaAlertaTemprana);
            telefono = itemView.findViewById(R.id.telefonoTextViewConsultaAlertaTemprana);
            descripcion = itemView.findViewById(R.id.descripcionTextViewConsultaAlertaTemprana);
            asunto = itemView.findViewById(R.id.asuntoTextViiewCobsultaAlertaTemprana);
            atendidoCheckBox = itemView.findViewById(R.id.atendidoCheckBox);
            view = itemView;
        }

        private void atenderAlertaTemprana(Alerta_temprana alerta_temprana, boolean atender)
        {
            HashMap<String, String> hashMap;
            if(atender)
            {
                hashMap = new Gestion_alerta_temprana().atendido(Gestion_administrador.getAdministrador_actual().id_administrador, alerta_temprana.id_alerta_temprana);
            }
            else
            {
                hashMap = new Gestion_alerta_temprana().no_atendido(alerta_temprana.id_alerta_temprana);
            }
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {

                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
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
        public void setDatos(final Alerta_temprana alerta_temprana)
        {
            consultar_datos_usuario(alerta_temprana);
            consultar_asunto(alerta_temprana);
            descripcion.setText(alerta_temprana.descripcion_alerta_temprana);
            if(alerta_temprana.estado_atendido == 1)
            {
                atendidoCheckBox.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                atendidoCheckBox.setChecked(true);
                atendidoCheckBox.setText("Atendido");
                atendidoCheckBox.setTextColor(view.getResources().getColor(R.color.verde));
            }
            else
            {
                atendidoCheckBox.setButtonTintList(ColorStateList.valueOf(Color.BLACK));
                atendidoCheckBox.setChecked(false);
                atendidoCheckBox.setText("No atendido");
                atendidoCheckBox.setTextColor(view.getResources().getColor(R.color.rojo));
            }

            atendidoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        atendidoCheckBox.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                        atendidoCheckBox.setText("Atendido");
                        atendidoCheckBox.setTextColor(view.getResources().getColor(R.color.verde));
                    }
                    else
                    {
                        atendidoCheckBox.setButtonTintList(ColorStateList.valueOf(Color.BLACK));
                        atendidoCheckBox.setText("No atendido");
                        atendidoCheckBox.setTextColor(view.getResources().getColor(R.color.rojo));
                    }
                    atenderAlertaTemprana(alerta_temprana, isChecked);
                }
            });
            this.view = itemView;
        }
    }
}
