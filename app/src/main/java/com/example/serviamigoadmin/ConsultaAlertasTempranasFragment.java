package com.example.serviamigoadmin;


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
import com.example.gestion.Gestion_noticia;
import com.example.modelo.Alerta_temprana;
import com.example.serviamigoadmin.Items.adapterItemAlertaTemprana;
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
    private static View view_permanente;
    private RecyclerView recyclerView_alertas_tempranas;
    private ArrayList<Alerta_temprana> alerta_tempranaArrayList;
    private static int num_alertas = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view_permanente == null)
        {
            view_permanente =  inflater.inflate(R.layout.fragment_consulta_alertas_tempranas, container, false);
            consultar_alertas_tempranas();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(;;)
                    {
                        try {
                            Thread.sleep(5000);
                            comparar_num_alertas();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        return view_permanente;
    }

    private void comparar_num_alertas()
    {
        HashMap<String, String> hashMap = new Gestion_alerta_temprana().num_alertas();
        Log.d("Thread", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                int aux = 0;
                try
                {
                    aux = Integer.parseInt(response);
                    if(aux != num_alertas)
                    {
                        consultar_alertas_tempranas();
                    }
                }
                catch(NumberFormatException exc)
                {

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }


    private void consultar_alertas_tempranas()
    {
        final Gestion_noticia gestion_noticia = new Gestion_noticia();
        //tomo los parametros del controlador
        HashMap<String,String> params = new Gestion_alerta_temprana().consultar_alerta_temprana();
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
        alerta_tempranaArrayList = new Gestion_alerta_temprana().generar_json(json);
        num_alertas = alerta_tempranaArrayList.size();
        recyclerView_alertas_tempranas = view_permanente.findViewById(R.id.alertas_tempranas_Recycler_view);
        recyclerView_alertas_tempranas.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapterItemAlertaTemprana adapterItemCliente = new adapterItemAlertaTemprana(alerta_tempranaArrayList);
        recyclerView_alertas_tempranas.setAdapter(adapterItemCliente);
        recyclerView_alertas_tempranas.setHasFixedSize(true);
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
