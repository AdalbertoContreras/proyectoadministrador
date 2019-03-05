package com.example.serviamigoadmin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_chat_asesoria;
import com.example.gestion.Gestion_especialidad;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Especialidad;
import com.example.serviamigoadmin.Adapter.AdapterChat;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisAsesoriasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisAsesoriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisAsesoriasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private RecyclerView chat_asesorRecyclerView;
    private Spinner tipo_asesoriasSpinner;
    private ArrayList<Especialidad> especialidades;
    private ArrayList<Chat_asesoria> chat_asesoria_general;
    private ArrayList<Chat_asesoria> chat_asesoria_filtrada;
    private AdapterChat adapterChat;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public MisAsesoriasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisAsesoriasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MisAsesoriasFragment newInstance(String param1, String param2) {
        MisAsesoriasFragment fragment = new MisAsesoriasFragment();
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
        view = inflater.inflate(R.layout.fragment_mis_asesorias, container, false);
        chat_asesorRecyclerView = view.findViewById(R.id.mis_asesoriasRecyclerView);
        tipo_asesoriasSpinner = view.findViewById(R.id.tipo_asesoriasSpinner);
        tipo_asesoriasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                {
                    filtrar_chat(especialidades.get(position - 1).id_especialidad);
                }
                else
                {
                    chat_asesoria_filtrada = chat_asesoria_general;
                }
                adapterChat = new AdapterChat(chat_asesoria_filtrada, getFragmentManager());
                chat_asesorRecyclerView.setAdapter(adapterChat);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        consultar_chat();
        consultar_especializaciones();
        return view;
    }

    private void filtrar_chat(int filtro)
    {
        chat_asesoria_filtrada = new ArrayList<>();
        int cont = 0;
        for(Chat_asesoria item : chat_asesoria_general) {
            if (item.especializacion_chat_asesoria == filtro) {
                chat_asesoria_filtrada.add(item);
            }
        }
    }

    private void consultar_especializaciones()
    {
        HashMap<String, String> hashMap = new Gestion_especialidad().consultar_especialidades();
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                cargar_especializaciones(response);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_especializaciones(String json)
    {
        especialidades = new Gestion_especialidad().generar_json(json);
        String[] asuntosArray;
        if(!especialidades.isEmpty())
        {
            asuntosArray = asuntos_a_array_string();
        }
        else
        {
            asuntosArray= asuntos_a_array_string_vacio();

        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_spinner_item, asuntosArray);
        tipo_asesoriasSpinner.setAdapter(arrayAdapter);
    }

    private String[] asuntos_a_array_string()
    {
        String[] array = new String[especialidades.size() + 1];
        array[0] = "Selecione un asunto";
        int cont = 1;
        for(Especialidad item : especialidades)
        {
            array[cont] = item.nombre_especialidad;
            cont ++;
        }
        return array;
    }
    private String[] asuntos_a_array_string_vacio()
    {
        String[] array = new String[1];
        array[0] = "No hay asunto";
        return array;
    }

    private void consultar_chat()
    {
        HashMap<String,String> params = new Gestion_chat_asesoria().consultar_por_administrador(Gestion_administrador.getAdministrador_actual().id_administrador);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                cargar_chat(response);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_chat(String json)
    {
        chat_asesoria_general = new Gestion_chat_asesoria().generar_json(json);
        chat_asesoria_filtrada = chat_asesoria_general;
        chat_asesorRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapterChat = new AdapterChat(chat_asesoria_filtrada, getFragmentManager());
        chat_asesorRecyclerView.setAdapter(adapterChat);
        chat_asesorRecyclerView.setHasFixedSize(true);
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
