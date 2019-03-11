package com.example.serviamigoadmin.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gestion.Gestion_especialidad;
import com.example.modelo.Administrador;
import com.example.modelo.Especialidad;
import com.example.serviamigoadmin.Dialog.DetalleAsesorDialog;
import com.example.serviamigoadmin.Fragment.ConsultarAsesoresFragment;
import com.example.servimaigoadmin.R;
import java.util.ArrayList;
import java.util.EnumSet;

public class AdapterItemAsesor extends  RecyclerView.Adapter<AdapterItemAsesor.ViewHolderDatos>{
    private ArrayList<Administrador> alertas;
    public int h;
    public AdapterItemAsesor(ArrayList<Administrador> alerta_tempranas)
    {
        this.alertas = alerta_tempranas;
    }

    @NonNull
    @Override
    public AdapterItemAsesor.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_asesor,null, false);
        return new AdapterItemAsesor.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull AdapterItemAsesor.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(alertas.get(i));
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        private View view;
        private ImageView imagenAsesorImageView;
        private TextView nombreAsesorTextView;
        private ImageButton detallesImageButton;
        private ImageView sexualidadImageView;
        private ImageView identidadImageView;
        private ImageView maltratoImageView;
        private ImageView embarazoImageView;
        private ArrayList<Boolean> especialidades_boolean;
        private Administrador administrador;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            imagenAsesorImageView = view.findViewById(R.id.magenAsesorImageView);
            nombreAsesorTextView = view.findViewById(R.id.nombreAsesorTextView);
            detallesImageButton = view.findViewById(R.id.detallesAsesorImageButton);
            sexualidadImageView = view.findViewById(R.id.sexualidadImageView);
            identidadImageView = view.findViewById(R.id.identidadImageView);
            maltratoImageView = view.findViewById(R.id.maltratoImageView);
            embarazoImageView = view.findViewById(R.id.embarazoImageView);
            especialidades_boolean = new ArrayList<>();

        }

        private void cargar_datos_asesor()
        {
            nombreAsesorTextView.setText(administrador.nombres_administrador + " " + administrador.apellidos_administrador);
            if(especialidades_boolean.get(0))
            {
                sexualidadImageView.setVisibility(View.VISIBLE);
            }
            else
            {
                sexualidadImageView.setVisibility(View.GONE);
            }
            if(especialidades_boolean.get(1))
            {
                identidadImageView.setVisibility(View.VISIBLE);
            }
            else
            {
                identidadImageView.setVisibility(View.GONE);
            }
            if(especialidades_boolean.get(2))
            {
                maltratoImageView.setVisibility(View.VISIBLE);
            }
            else
            {
                maltratoImageView.setVisibility(View.GONE);
            }
            if(especialidades_boolean.get(3))
            {
                embarazoImageView.setVisibility(View.VISIBLE);
            }
            else
            {
                embarazoImageView.setVisibility(View.GONE);
            }
        }

        public void setDatos(final Administrador asesor)
        {
            this.administrador = asesor;
            ArrayList<Especialidad> especialidades = new Gestion_especialidad().generar_json(administrador.especialidades);
            especialidades_boolean.add(tengoEspecialidad(1, especialidades));
            especialidades_boolean.add(tengoEspecialidad(2, especialidades));
            especialidades_boolean.add(tengoEspecialidad(3, especialidades));
            especialidades_boolean.add(tengoEspecialidad(4, especialidades));
            detallesImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetalleAsesorDialog detalleAsesorDialog = DetalleAsesorDialog.newIntancia(administrador, especialidades_boolean, new DetalleAsesorDialog.AceptoCambios() {
                        @Override
                        public void aceptar(Administrador administrador, ArrayList<Boolean> especialidades) {
                            ViewHolderDatos.this.administrador = administrador;
                            ViewHolderDatos.this.especialidades_boolean = especialidades;
                            ViewHolderDatos.this.cargar_datos_asesor();
                        }
                    });
                    try {
                        detalleAsesorDialog.show(ConsultarAsesoresFragment.fragmentManager, "missiles");
                    } catch (IllegalStateException ignored) {

                    }
                }
            });
            cargar_datos_asesor();
        }

        private boolean tengoEspecialidad(int especialidad, ArrayList<Especialidad> especialidades)
        {
            for(Especialidad item : especialidades)
            {
                if(item.id_especialidad == especialidad)
                {
                    return true;
                }
            }
            return false;
        }
    }
}