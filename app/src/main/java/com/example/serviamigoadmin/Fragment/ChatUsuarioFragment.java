package com.example.serviamigoadmin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_mensaje_chat_asesoria;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Mensaje_chat_asesoria;
import com.example.serviamigoadmin.Adapter.Adapter_Mensajes_Chat;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatUsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatUsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static Chat_asesoria chat_asesoria;
    private int ultimo_id;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias;
    private RecyclerView recyclerView_chat_asesoria;
    private EditText mensajeEditText;
    private Button enviarButton;
    private Boolean mensaje_enviado;
    private Boolean consultando;
    private Adapter_Mensajes_Chat adapter_mensajes_chat_asesoria;
    private boolean fragment_activo;

    private OnFragmentInteractionListener mListener;

    public ChatUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatUsuarioFragment newInstance(String param1, String param2) {
        ChatUsuarioFragment fragment = new ChatUsuarioFragment();
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
    public void onResume() {
        super.onResume();
        fragment_activo = true;
        mensaje_enviado = false;
        iniciar_conexion_chat();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_chat_usuario, container, false);
        mensaje_chat_asesorias = new ArrayList<>();
        fragment_activo = true;
        recyclerView_chat_asesoria = view.findViewById(R.id.mensajes_chat_asesoria_recyclerview);
        recyclerView_chat_asesoria.setLayoutManager(new GridLayoutManager(view.getContext(),1));

        mensajeEditText = view.findViewById(R.id.mensajeEdittext);
        enviarButton = view.findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!mensajeEditText.getText().toString().isEmpty())
            {
                if(chat_asesoria != null)
                {
                    Mensaje_chat_asesoria mensaje_chat_asesoria = new Mensaje_chat_asesoria();
                    mensaje_chat_asesoria.chat_mensaje_chat_asesoria = chat_asesoria.id_chat_asesoria;
                    mensaje_chat_asesoria.id_creador_mensaje_chat_asesoria = Gestion_administrador.getAdministrador_actual().id_administrador;
                    mensaje_chat_asesoria.contenido_mensaje_chat_asesoria = mensajeEditText.getText().toString();
                    mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria = 2;
                    HashMap<String,String> params = new Gestion_mensaje_chat_asesoria().registrar_mensaje_chat_asesoria(mensaje_chat_asesoria);
                    Response.Listener<String> stringListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                        mensaje_enviado = true;
                        consultando = true;
                        if(!consultando)
                        {
                            consultar_mensajes_nuevos();
                        }
                        }
                    };
                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            consultando = false;
                            Log.d("Reponse.Error",error.toString());
                        }
                    };
                    StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
                    MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
                }
                mensajeEditText.setText("");
            }
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        fragment_activo = false;
    }

    private void consultar_mensajes()
    {
        HashMap<String,String> params = new Gestion_mensaje_chat_asesoria().mensajes_asesoria_por_asesoria(chat_asesoria.id_chat_asesoria);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                cargar_mensajes_de_inicio(response);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_mensajes_de_inicio(String response)
    {
        consultando = true;
        mensaje_chat_asesorias = new Gestion_mensaje_chat_asesoria().generar_json(response);
        //Collections.reverse(mensaje_chat_asesorias);
        adapter_mensajes_chat_asesoria = new Adapter_Mensajes_Chat(mensaje_chat_asesorias);
        if(!mensaje_chat_asesorias.isEmpty())
        {
            recyclerView_chat_asesoria.smoothScrollToPosition(mensaje_chat_asesorias.size() - 1);
            ultimo_id = mensaje_chat_asesorias.get(mensaje_chat_asesorias.size() - 1).id_mensaje_chat_asesoria;
        }
        recyclerView_chat_asesoria.setAdapter(adapter_mensajes_chat_asesoria);
        recyclerView_chat_asesoria.setHasFixedSize(true);
        consultando = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(fragment_activo)
                {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(!consultando)
                    {
                        consultando = true;
                        if(getActivity() != null)
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    consultar_mensajes_nuevos();
                                }
                            });
                        }
                    }
                }
            }
        }).start();
    }

    private void consultar_mensajes_nuevos()
    {
        HashMap<String,String> params;
        if(!mensaje_chat_asesorias.isEmpty())
        {
            params = new Gestion_mensaje_chat_asesoria().mensaje_chat_asesoria_por_chat_mayor(ultimo_id, chat_asesoria.id_chat_asesoria);
        }
        else
        {
            params = new Gestion_mensaje_chat_asesoria().mensajes_asesoria_por_asesoria(chat_asesoria.id_chat_asesoria);
        }
        Log.d("Parametros",params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                cargar_mensajes_nuevos(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                consultando = false;
                Log.d("Reponse.Error",error.toString());
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void iniciar_conexion_chat()
    {
        consultar_mensajes();
    }

    private void cargar_mensajes_nuevos(String response)
    {
        ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias_aux = new Gestion_mensaje_chat_asesoria().generar_json(response);
        if(!mensaje_chat_asesorias_aux.isEmpty())
        {
            ultimo_id = mensaje_chat_asesorias_aux.get(mensaje_chat_asesorias_aux.size() - 1).id_mensaje_chat_asesoria;
            mensaje_chat_asesorias.addAll(mensaje_chat_asesorias_aux);
            adapter_mensajes_chat_asesoria.notifyItemInserted(mensaje_chat_asesorias.size() - 1 );
            if(mensaje_enviado)
            {
                recyclerView_chat_asesoria.smoothScrollToPosition(mensaje_chat_asesorias.size() - 1);
                mensaje_enviado = false;
            }
        }
        consultando = false;
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
