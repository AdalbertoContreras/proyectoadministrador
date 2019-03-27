package com.example.serviamigoadmin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.Config;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Usuario;
import com.example.servimaigoadmin.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CambiarContrasenaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CambiarContrasenaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CambiarContrasenaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText nombre_cuentaEditText;
    private EditText contraseñaCuentaEditText;
    private EditText contraseñaNuevaEditText;
    private EditText comprobarContraseñaNuevaEditText;
    private Button cambiarContraseñaButton;
    private String contraseña_anterior;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CambiarContrasenaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CambiarContrasenaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CambiarContrasenaFragment newInstance(String param1, String param2) {
        CambiarContrasenaFragment fragment = new CambiarContrasenaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private Administrador administrador_espejo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cambiar_contrasena, container, false);
        nombre_cuentaEditText = view.findViewById(R.id.nombre_cuentaEditText);
        contraseñaCuentaEditText = view.findViewById(R.id.contraseña_cuentaEditText);
        contraseñaNuevaEditText = view.findViewById(R.id.contraseña_nuevaEditText);
        comprobarContraseñaNuevaEditText = view.findViewById(R.id.comprobar_contraseña_nuevaEditText);
        cambiarContraseñaButton = view.findViewById(R.id.cambiarContraseñaCuentaButton);
        nombre_cuentaEditText.setEnabled(false);
        nombre_cuentaEditText.setText(Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
        administrador_espejo = new Administrador();
        administrador_espejo.id_administrador = Gestion_administrador.getAdministrador_actual().id_administrador;
        eventoModificarAdministrador();
        cargarDatosAdministrador();

        return view;
    }

    private void cargarDatosAdministrador()
    {
        nombre_cuentaEditText.setText(Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
        contraseñaNuevaEditText.setText("");
        comprobarContraseñaNuevaEditText.setText("");
    }

    private void eventoModificarAdministrador()
    {
        cambiarContraseñaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contraseñaCuentaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese la contraseña de esta cuenta.", Toast.LENGTH_LONG).show();
                    return;
                }
                validar_cuenta();
            }
        });
    }

    private void validar_cuenta()
    {
        contraseña_anterior = Gestion_administrador.getAdministrador_actual().contrasena_administrador;
        Administrador administrador_con_contraseña_validad = Gestion_administrador.getAdministrador_actual();
        administrador_con_contraseña_validad.contrasena_administrador = contraseñaCuentaEditText.getText().toString();
        Gestion_administrador.setAdministrador_actual(administrador_con_contraseña_validad);
        HashMap<String, String> hashMap = new Gestion_administrador().validar_administrador(administrador_con_contraseña_validad);
        Log.d("parametros", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d("Response : ", response);
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        if(contraseñaNuevaEditText.getText().toString().isEmpty())
                        {
                            Toast.makeText(view.getContext(), "Ingrese la nueva contraseña de su cuenta.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(contraseñaNuevaEditText.getText().toString().length() < 4)
                        {
                            Toast.makeText(view.getContext(), "La contraseña solo permite como minimo 4 caracteres y un maximo de 32 caracteres.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(contraseñaNuevaEditText.getText().toString().length() > 32)
                        {
                            Toast.makeText(view.getContext(), "La contraseña solo permite como minimo 4 caracteres y un maximo de 32 caracteres.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(comprobarContraseñaNuevaEditText.getText().toString().isEmpty())
                        {
                            Toast.makeText(view.getContext(), "Ingrese de nuevo la nueva contraseña.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(!contraseñaNuevaEditText.getText().toString().equals(comprobarContraseñaNuevaEditText.getText().toString()))
                        {
                            Toast.makeText(view.getContext(), "Las contraseñas ingresadas no coinciden.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            actualizar_contraseña();
                        }
                    }
                    else
                    {
                        Toast.makeText(view.getContext(), "No cuenta con acceso a cambiar la contraseña de esta cuenta", Toast.LENGTH_LONG).show();
                        Gestion_administrador.getAdministrador_actual().contrasena_administrador = contraseña_anterior;
                    }
                }
                catch (NumberFormatException exc)
                {
                    Toast.makeText(view.getContext(), "No cuenta con acceso a cambiar la contraseña de esta cuenta", Toast.LENGTH_LONG).show();
                    Gestion_usuario.getUsuario_online().contrasena_usuario = contraseña_anterior;
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),"Ha ocurrido un error en el servidor", Toast.LENGTH_LONG).show();
                Gestion_usuario.getUsuario_online().contrasena_usuario = contraseña_anterior;
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void actualizar_contraseña()
    {
        administrador_espejo.contrasena_administrador = contraseñaNuevaEditText.getText().toString();
        Gestion_administrador.getAdministrador_actual().contrasena_administrador = contraseña_anterior;
        HashMap<String, String> hashMap = new Gestion_administrador().cambiar_contrasena(administrador_espejo);
        Log.d("parametros", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d("Response : ", response);
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        Gestion_administrador.getAdministrador_actual().contrasena_administrador = administrador_espejo.contrasena_administrador;
                        limpiar_contraseñas();
                        Toast.makeText(view.getContext(),"Datos de la cuenta actualizados", Toast.LENGTH_LONG).show();
                    }
                }
                catch (NumberFormatException exc)
                {
                    Toast.makeText(view.getContext(),"Error al actualizar datos de la cuenta", Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),"Ha ocurrido un error en el servidor", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void limpiar_contraseñas()
    {
        contraseñaCuentaEditText.setText("");
        contraseñaNuevaEditText.setText("");
        comprobarContraseñaNuevaEditText.setText("");
    }
    private void carmbiar_contraseña(Administrador administrador)
    {
        HashMap<String, String> hashMap = new Gestion_administrador().cambiar_contrasena(administrador);
        Log.d("parametros", hashMap.toString());
        HashMap<String,String> params;
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try
                {
                    int val = Integer.parseInt(response);
                    if(val == 1)
                    {
                        Toast.makeText(view.getContext(), "Contraseña cambiada con exito", Toast.LENGTH_SHORT).show();
                        Gestion_administrador.getAdministrador_actual().contrasena_administrador = contraseñaNuevaEditText.getText().toString();
                        contraseñaNuevaEditText.setText("");
                        comprobarContraseñaNuevaEditText.setText("");
                    }
                    else
                    {
                        Toast.makeText(view.getContext(), "Contraseña no cambiada", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NumberFormatException exc)
                {
                    Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                Log.d("Reponse.Error",error.toString());
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
