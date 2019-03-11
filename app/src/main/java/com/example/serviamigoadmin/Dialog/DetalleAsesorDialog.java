package com.example.serviamigoadmin.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_especialidad_administrador;
import com.example.modelo.Administrador;
import com.example.modelo.Especialidad;
import com.example.modelo.Especialidad_administrador;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DetalleAsesorDialog extends DialogFragment{
    public Administrador administrador;
    public ArrayList<Boolean> especialidades;
    public ArrayList<Boolean> especialidades_espejo;
    public AceptoCambios aceptoCambios;
    private TextView nombresTextView;
    private TextView apellidosTextView;
    private TextView telefonoTextView;
    private TextView correoElectronicoTextView;
    private TextView direccionTextView;
    private TextView nombreCuentaTextView;
    private TextView sexoTextView;
    private CheckBox permisoAccesoAsesorCheckBox;
    private CheckBox sexualidadCheckBox;
    private CheckBox identidadCheckBox;
    private CheckBox embarazoCheckBox;
    private CheckBox maltratoCheckBox;
    private View view;

    public static DetalleAsesorDialog newIntancia(Administrador administrador, ArrayList<Boolean>  especialidades,AceptoCambios aceptoCambios)
    {
        DetalleAsesorDialog detalleAsesorDialog = new DetalleAsesorDialog();
        detalleAsesorDialog.administrador = administrador;
        detalleAsesorDialog.especialidades = especialidades;
        detalleAsesorDialog.especialidades_espejo = new ArrayList<>();
        detalleAsesorDialog.especialidades_espejo.addAll(0, especialidades);
        detalleAsesorDialog.aceptoCambios = aceptoCambios;
        return detalleAsesorDialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.detalles_asesor_dialog, null);
        nombresTextView = view.findViewById(R.id.nombreAsesorTextView);
        apellidosTextView = view.findViewById(R.id.apellidosAsesorTextView);
        telefonoTextView = view.findViewById(R.id.numeroTelefonoAsesorTextView);
        correoElectronicoTextView = view.findViewById(R.id.correoElectronicoAsesorTextView);
        direccionTextView = view.findViewById(R.id.direccionAsesorTextView);
        nombreCuentaTextView = view.findViewById(R.id.nombreCuentaAsesorTextView);
        sexoTextView = view.findViewById(R.id.sexoAsesorTextView);
        permisoAccesoAsesorCheckBox = view.findViewById(R.id.permisoAccesoAsesorCheckBox);
        sexualidadCheckBox = view.findViewById(R.id.sexualidadCheckBox);
        identidadCheckBox = view.findViewById(R.id.identidadCheckBox);
        maltratoCheckBox = view.findViewById(R.id.maltratoCheckBox);
        embarazoCheckBox = view.findViewById(R.id.embarazoCheckBox);
        cargar_datos_asesor();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.detalles_asesor_dialog, null))
                // Add action buttons
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                actualizar_administrador();
                aceptoCambios.aceptar(administrador, especialidades);
                dismiss();
            }
        })
        .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        })
        .setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private void actualizar_administrador()
    {
        if(!especialidades.get(0).equals(especialidades_espejo.get(0)))
        {
            if(especialidades.get(0))
            {
                registrar_eliminar_especialidad_administrador(1, true);
            }
            else
            {
                registrar_eliminar_especialidad_administrador(1, false);
            }
        }
        if(!especialidades.get(1).equals(especialidades_espejo.get(1)))
        {
            if(especialidades.get(1))
            {
                registrar_eliminar_especialidad_administrador(2, true);
            }
            else
            {
                registrar_eliminar_especialidad_administrador(2, false);
            }
        }
        if(!especialidades.get(2).equals(especialidades_espejo.get(2)))
        {
            if(especialidades.get(2))
            {
                registrar_eliminar_especialidad_administrador(3, true);
            }
            else
            {
                registrar_eliminar_especialidad_administrador(3, false);
            }
        }
        if(!especialidades.get(3).equals(especialidades_espejo.get(3)))
        {
            if(especialidades.get(3))
            {
                registrar_eliminar_especialidad_administrador(4, true);
            }
            else
            {
                registrar_eliminar_especialidad_administrador(4, false);
            }
        }
    }

    private void registrar_eliminar_especialidad_administrador(int especialidad, boolean registrar)
    {
        HashMap<String,String> params;
        Especialidad_administrador especialidad_administrador = new Especialidad_administrador();
        especialidad_administrador.administrador_especialidad_administrador = administrador.id_administrador;
        especialidad_administrador.especialidad_especialidad_admnistrador = especialidad;
        if(registrar)
        {
            params = new Gestion_especialidad_administrador().registrar_especialidad_administrador(especialidad_administrador);
        }
        else
        {
            params = new Gestion_especialidad_administrador().eliminar_especialidad_administrador(especialidad_administrador);
        }
        Log.d("parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                Log.d("respuesta", response);
            }
        };
        Response.ErrorListener errorListener =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Reponse.Error",error.toString());
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_datos_asesor()
    {
        nombresTextView.setText(administrador.nombres_administrador);
        apellidosTextView.setText(administrador.apellidos_administrador);
        if(administrador.sexo_administrador == 0)
        {
            sexoTextView.setText("Masculino");
        }
        else
        {
            sexoTextView.setText("Femenino");
        }
        telefonoTextView.setText(administrador.numero_telefono_administrador);
        correoElectronicoTextView.setText(administrador.correo_electronico_administrador);
        direccionTextView.setText(administrador.direccion_administrador);
        nombreCuentaTextView.setText(administrador.nombre_cuenta_administrador);
        if(administrador.estado_administrador == 0)
        {
            permisoAccesoAsesorCheckBox.setChecked(false);
        }
        else
        {
            permisoAccesoAsesorCheckBox.setChecked(true);
        }
        if(especialidades.get(0))
        {
            sexualidadCheckBox.setChecked(true);
        }
        else
        {
            sexualidadCheckBox.setChecked(false);
        }
        if(especialidades.get(1))
        {
            identidadCheckBox.setChecked(true);
        }
        else
        {
            identidadCheckBox.setChecked(false);
        }
        if(especialidades.get(2))
        {
            maltratoCheckBox.setChecked(true);
        }
        else
        {
            maltratoCheckBox.setChecked(false);
        }
        if(especialidades.get(3))
        {
            embarazoCheckBox.setChecked(true);
        }
        else
        {
            embarazoCheckBox.setChecked(false);
        }
        sexualidadCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                especialidades.set(0, isChecked);
            }
        });
        identidadCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                especialidades.set(1, isChecked);
            }
        });
        maltratoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                especialidades.set(2, isChecked);
            }
        });
        embarazoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                especialidades.set(3, isChecked);
            }
        });
    }

    public interface AceptoCambios
    {
        void aceptar(Administrador administrador, ArrayList<Boolean> especialidades);
    }
}
