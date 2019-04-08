package com.comfacesar.serviamigoadmin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.extra.MySocialMediaSingleton;
import com.comfacesar.extra.WebService;
import com.comfacesar.gestion.Gestion_estadistica_usuario;
import com.comfacesar.modelo.Estadistica_usuario;
import com.comfacesar.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstadisticaUsuariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EstadisticaUsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadisticaUsuariosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView usuarioTotalTextView;
    private TextView usuarioFemeninoTotalTextView;
    private TextView usuarioMasculinoTotalTextView;
    private TextView usuarioInfanteTextView;
    private TextView usuarioFemeninoInfanteTextView;
    private TextView usuarioMasculinosInfanteTextView;
    private TextView usuarioAdolecenteTextView;
    private TextView usuarioFemeninoAdolecenteTextView;
    private TextView usuarioMasculinoAdolecenteTextView;
    private TextView usuarioJovenTextView;
    private TextView usuarioFemeninoJovenTextView;
    private TextView usuarioMasculinoJovenTextView;
    private TextView usuarioAdultoTextView;
    private TextView usuarioFemeninoAdultoTextView;
    private TextView usuarioMasculinoAdultoTextView;
    private TextView usuarioMayorTextView;
    private TextView usuarioFemeninoMayorTextView;
    private TextView usuarioMasculinoMayorTextView;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EstadisticaUsuariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstadisticaUsuariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstadisticaUsuariosFragment newInstance(String param1, String param2) {
        EstadisticaUsuariosFragment fragment = new EstadisticaUsuariosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        view =  inflater.inflate(R.layout.fragment_estadistica_usuarios, container, false);
        usuarioTotalTextView = view.findViewById(R.id.usuarioTotalTextView);
        usuarioFemeninoTotalTextView = view.findViewById(R.id.usuarioFemeninoTotalTextView);
        usuarioMasculinoTotalTextView = view.findViewById(R.id.usuarioMasculinoTotalTextView);
        usuarioInfanteTextView = view.findViewById(R.id.usuarioInfanteTextView);
        usuarioFemeninoInfanteTextView = view.findViewById(R.id.usuarioFemeninoInfanteTextView);
        usuarioMasculinosInfanteTextView = view.findViewById(R.id.usuarioMasculinoInfanteTextView);
        usuarioAdolecenteTextView = view.findViewById(R.id.usuarioAdolecenteTextView);
        usuarioFemeninoAdolecenteTextView = view.findViewById(R.id.usuarioFemeninoAdolecenteTextView);
        usuarioMasculinoAdolecenteTextView = view.findViewById(R.id.usuarioMasculinoAdolecenteTextView);
        usuarioJovenTextView = view.findViewById(R.id.usuarioJovenTextView);
        usuarioFemeninoJovenTextView = view.findViewById(R.id.usuarioFemeninoJovenTextView);
        usuarioMasculinoJovenTextView = view.findViewById(R.id.usuarioMasculinoJovenTextView);
        usuarioAdultoTextView = view.findViewById(R.id.usuarioAdultoTextView);
        usuarioFemeninoAdultoTextView = view.findViewById(R.id.usuarioFemeninoAdultoTextView);
        usuarioMasculinoAdultoTextView = view.findViewById(R.id.usuarioMasculinoAdultoTextView);
        usuarioMayorTextView = view.findViewById(R.id.usuarioMayorTextView);
        usuarioFemeninoMayorTextView = view.findViewById(R.id.usuarioFemeninoMayorTextView);
        usuarioMasculinoMayorTextView = view.findViewById(R.id.usuarioMasculinoMayorTextView);
        cosultar_estadisticas();
        return view;
    }

    private void cosultar_estadisticas()
    {
        final HashMap<String, String> hashMap = new Gestion_estadistica_usuario().consultar();
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                cargar_estadisticas(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo registrar asesor, error en el servidor", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_estadisticas(String json)
    {
        ArrayList<Estadistica_usuario> estadistica_usuarios = new Gestion_estadistica_usuario().generar_json(json);
        if(!estadistica_usuarios.isEmpty())
        {
            Estadistica_usuario estadistica_usuario = estadistica_usuarios.get(0);
            usuarioTotalTextView.setText(estadistica_usuario.numero_usuarios_total + "");
            usuarioFemeninoTotalTextView.setText(estadistica_usuario.numero_usuarios_femeninos + "");
            usuarioMasculinoTotalTextView.setText(estadistica_usuario.numero_usuarios_masculinos + "");
            usuarioInfanteTextView.setText(estadistica_usuario.numero_usuarios_infancia + "");
            usuarioFemeninoInfanteTextView.setText(estadistica_usuario.numero_usuarios_infancia_femenino + "");
            usuarioMasculinosInfanteTextView.setText(estadistica_usuario.numero_usuarios_infancia_masculino + "");
            usuarioAdolecenteTextView.setText(estadistica_usuario.numero_usuarios_adolecente + "");
            usuarioFemeninoAdolecenteTextView.setText(estadistica_usuario.numero_usuarios_adolecente_femenino + "");
            usuarioMasculinoAdolecenteTextView.setText(estadistica_usuario.numero_usuarios_adolecente_masculino + "");
            usuarioJovenTextView.setText(estadistica_usuario.numero_usuarios_joven + "");
            usuarioFemeninoJovenTextView.setText(estadistica_usuario.numero_usuarios_joven_femenino + "");
            usuarioMasculinoJovenTextView.setText(estadistica_usuario.numero_usuarios_joven_masculino + "");
            usuarioAdultoTextView.setText(estadistica_usuario.numero_usuarios_adulto + "");
            usuarioFemeninoAdultoTextView.setText(estadistica_usuario.numero_usuarios_adulto_femenino + "");
            usuarioMasculinoAdultoTextView.setText(estadistica_usuario.numero_usuarios_adulto_masculino + "");
            usuarioMayorTextView.setText(estadistica_usuario.numero_usuarios_mayor + "");
            usuarioFemeninoMayorTextView.setText(estadistica_usuario.numero_usuarios_mayor_femenino + "");
            usuarioMasculinoMayorTextView.setText(estadistica_usuario.numero_usuarios_mayor_masculino + "");
        }
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
