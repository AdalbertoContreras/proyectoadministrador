package com.example.serviamigoadmin.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_categoria_noticia;
import com.example.gestion.Gestion_imagen_noticia;
import com.example.gestion.Gestion_noticia;
import com.example.modelo.Categoria_noticia_manual;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;
import com.example.servimaigoadmin.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Modificar_noticiaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Modificar_noticiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Modificar_noticiaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Bitmap bitmap;
    public Modificar_noticiaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registrar_noticiaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Modificar_noticiaFragment newInstance(String param1, String param2) {
        Modificar_noticiaFragment fragment = new Modificar_noticiaFragment();
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
    private EditText tituloEditText;
    private EditText contenidoEditText;
    private Button cargarImagenButton;
    private ArrayList<Categoria_noticia_manual> categoria_noticiaArrayList;
    private View view;
    private Spinner categoriaSpinner;
    private Button registrarNoticiaButton;
    private Categoria_noticia_manual categoria_noticia_selecionado;
    private int id_noticia;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private ImageView foto_gallery;
    private Imagen_noticia imagen_noticia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_registrar_noticia, container, false);
        cargar_categorias();
        tituloEditText = view.findViewById(R.id.tituloEditTextRegistrarNoticia);
        contenidoEditText = view.findViewById(R.id.contenidoEditTextRegistrarNoticia);
        categoriaSpinner = view.findViewById(R.id.categoriaspinnerRegistrar_noticia);
        tituloEditText = view.findViewById(R.id.tituloEditTextRegistrarNoticia);
        cargarImagenButton = view.findViewById(R.id.cargarimagenButtonRegistrarNoticia);
        registrarNoticiaButton = view.findViewById(R.id.registrar_noticiaButtonRegistrarNoticia);
        foto_gallery = view.findViewById(R.id.imagenImageViewRegistrarImagen);
        iniciareventos();

        return view;
    }

    private void iniciareventos()
    {
        registrarNoticiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Noticia noticia = new Noticia();
            imagen_noticia = new Imagen_noticia();
            if(tituloEditText.getText().toString().isEmpty())
            {
                Toast.makeText(view.getContext(), "Ingrese el titulo de la noticia", Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                noticia.titulo_noticia = tituloEditText.getText().toString();
            }
            if(contenidoEditText.getText().toString().isEmpty())
            {
                Toast.makeText(view.getContext(), "Ingrese el contenido de la noticia", Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                noticia.contenido_noticia = contenidoEditText.getText().toString();
            }
            if(categoria_noticia_selecionado == null)
            {
                Toast.makeText(view.getContext(), "Selecione la categoria de la noticia", Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                noticia.categoria_noticia_manual_noticia = categoria_noticia_selecionado.id_categoria_noticia_manual;
            }
            registrar_noticia(noticia);
            }
        });
        cargarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                {
                    categoria_noticia_selecionado = categoria_noticiaArrayList.get(position - 1);
                }
                else
                {
                    categoria_noticia_selecionado = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(view.getContext().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String bitmap_conver_to_String(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
        byte[] bytes = stream.toByteArray();
        String s = Base64.encodeToString(bytes, Base64.DEFAULT);
        return s;
    }

    private void limpiar_campos()
    {
        categoriaSpinner.setSelection(0);
        tituloEditText.setText("");
        contenidoEditText.setText("");
        imageUri = null;
        bitmap = null;
        foto_gallery.setImageURI(imageUri);
    }

    private void registrar_noticia(Noticia noticia)
    {
        id_noticia = -1;
        noticia.administrador_noticia = Gestion_administrador.getAdministrador_actual().id_administrador;
        HashMap<String, String> hashMap = new Gestion_noticia().registrar_noticia_manual(noticia);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                registrar_noticia(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),"Ocurrio un error en la conexcion, registro cancelado.", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void registrar_noticia(String response)
    {
        int valor = -1;
        try
        {
            valor = Integer.parseInt(response);
        }
        catch (NumberFormatException exc)
        {

        }
        if(valor > 0)
        {

            if(bitmap != null)
            {
                id_noticia = valor;
                registrar_imagen(valor);
                Toast.makeText(view.getContext(), "Noticia y imagen registrada con exito", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(view.getContext(), "Noticia registrada con exito", Toast.LENGTH_LONG).show();
            }
            limpiar_campos();
        }
        else
        {
            Toast.makeText(view.getContext(), "Error al registrar noticia", Toast.LENGTH_LONG).show();
        }

    }

    private void registrar_imagen(int id)
    {
        Imagen_noticia imagen_noticia = new Imagen_noticia();
        imagen_noticia.url_imagen_noticia = bitmap_conver_to_String(bitmap);
        imagen_noticia.noticia_imagen_noticia = id;
        HashMap<String, String> hashMap = new Gestion_imagen_noticia().registrar_imagen_con_archivo(imagen_noticia);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso

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

    private void cargar_categorias()
    {
        HashMap<String, String> hashMap = new Gestion_categoria_noticia().consultar_categorias();
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                llenar_categorias(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),"Error en la conexion.", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void llenar_categorias(String json)
    {

        categoria_noticiaArrayList = new Gestion_categoria_noticia().generar_json(json);
        String[] categoria_array = null;
        if(categoria_noticiaArrayList.isEmpty())
        {
            categoria_array = llenar_categorias_vacio();
        }
        else
        {
            categoria_array = pasar_categorias_a_string(categoria_noticiaArrayList);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item, categoria_array);
        categoriaSpinner.setAdapter(arrayAdapter);
    }

    private String[] pasar_categorias_a_string(ArrayList<Categoria_noticia_manual> categoria_noticias)
    {
        String[] categorias = new String[categoria_noticias.size() + 1];
        categorias[0] = "Selecione una categoria";
        int cont = 1;
        for(Categoria_noticia_manual item :  categoria_noticias)
        {
            categorias[cont] = item.nombre_categoria_noticia;
            cont ++;
        }
        return categorias;
    }

    private String[] llenar_categorias_vacio()
    {
        return new String[]{"No hay categoria"};
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
