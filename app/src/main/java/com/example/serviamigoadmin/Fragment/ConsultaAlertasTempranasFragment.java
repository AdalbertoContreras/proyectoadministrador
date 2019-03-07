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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_alerta_temprana;
import com.example.modelo.Alerta_temprana;
import com.example.serviamigoadmin.Adapter.AdapterItemAlertaTemprana;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultaAlertasTempranasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultaAlertasTempranasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaAlertasTempranasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ConsultaAlertasTempranasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultaAlertasTempranasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultaAlertasTempranasFragment newInstance(String param1, String param2) {
        ConsultaAlertasTempranasFragment fragment = new ConsultaAlertasTempranasFragment();
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

    private View view_permanente;
    private RecyclerView recyclerView_alertas_tempranas;
    private ArrayList<Alerta_temprana> alerta_tempranaArrayList = new ArrayList<>();
    private static int num_alertas = 0;
    private boolean seguir;
    private int cont_m;
    private int id_maximo = 0;
    private AdapterItemAlertaTemprana adapterItemCliente;
    private boolean agregar_nuevas_alertas = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_permanente =  inflater.inflate(R.layout.fragment_consulta_alertas_tempranas, container, false);
        consultar_alertas_tempranas();
        return view_permanente;
    }

    private boolean generando_consulta;
    @Override
    public void onResume() {
        super.onResume();
        cont_m = 5000;
        seguir = true;
        generando_consulta = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(seguir)
                {
                    if(cont_m >= 5000)
                    {
                        cont_m = 0;
                        while(generando_consulta)
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if(seguir)
                        {
                            consultar_alertas_tempranas();
                        }
                    }
                    try {
                        Thread.sleep(100);
                        cont_m += 100;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        cont_m = 0;
        seguir = false;
    }


    private void consultar_alertas_tempranas()
    {
        HashMap<String,String> params;
        if(alerta_tempranaArrayList.isEmpty())
        {
            params = new Gestion_alerta_temprana().consultar_alerta_temprana();
        }
        else
        {
            agregar_nuevas_alertas = true;
            params = new Gestion_alerta_temprana().consultar_mayor(id_maximo);
        }
        Log.d("parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                Log.d("respuesta", response);
                llenar_alertas_tempranas(response);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    private void llenar_alertas_tempranas(String json)
    {
        ArrayList<Alerta_temprana> aux = new Gestion_alerta_temprana().generar_json(json);
        if(!alerta_tempranaArrayList.isEmpty())
        {
            alerta_tempranaArrayList.addAll(0, aux);
            id_maximo = alerta_tempranaArrayList.get(0).id_alerta_temprana;
        }
        else
        {
            alerta_tempranaArrayList = aux;
        }
        if(agregar_nuevas_alertas)
        {
            adapterItemCliente.notifyItemInserted(0);
        }
        else
        {
            num_alertas = alerta_tempranaArrayList.size();
            recyclerView_alertas_tempranas = view_permanente.findViewById(R.id.alertas_tempranas_Recycler_view);
            recyclerView_alertas_tempranas.setLayoutManager(new GridLayoutManager(getContext(),1));
            adapterItemCliente = new AdapterItemAlertaTemprana(alerta_tempranaArrayList);
            recyclerView_alertas_tempranas.setAdapter(adapterItemCliente);
            recyclerView_alertas_tempranas.setHasFixedSize(true);
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
