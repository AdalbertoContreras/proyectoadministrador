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
import com.example.gestion.Gestion_noticia;
import com.example.modelo.Noticia;
import com.example.serviamigoadmin.Adapter.AdaptaderNoticiaModificar;
import com.example.serviamigoadmin.Adapter.AdapterItemAlertaTemprana;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaNoticiasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaNoticiasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaNoticiasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView noticiasRecyclerView;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListaNoticiasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaNoticiasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaNoticiasFragment newInstance(String param1, String param2) {
        ListaNoticiasFragment fragment = new ListaNoticiasFragment();
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
        view =  inflater.inflate(R.layout.fragment_lista_noticias, container, false);
        noticiasRecyclerView = view.findViewById(R.id.noticiasRecyclerView);
        noticiasRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        consultar_noticias();
        return view;
    }

    private void consultar_noticias()
    {
        HashMap<String, String> hashMap = new Gestion_noticia().consultar_con_imagenes_y_m_primero();
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                ArrayList<Noticia> noticias = new Gestion_noticia().generar_json(response);
                if(!noticias.isEmpty())
                {
                    AdaptaderNoticiaModificar adapterItemCliente = new AdaptaderNoticiaModificar(noticias, ListaNoticiasFragment.this.getFragmentManager());
                    noticiasRecyclerView.setAdapter(adapterItemCliente);
                    noticiasRecyclerView.setHasFixedSize(true);
                }
            }
        };
        Response.ErrorListener errorListener =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Reponse.Error",error.toString() + " Alerta temprana no consultada");
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
