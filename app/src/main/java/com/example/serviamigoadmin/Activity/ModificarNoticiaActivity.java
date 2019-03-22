package com.example.serviamigoadmin.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ModificarNoticiaActivity extends AppCompatActivity {
    private EditText tituloEditText;
    private EditText contenidoEditText;
    private Button cargarImagenButton;
    private ArrayList<Categoria_noticia_manual> categoria_noticiaArrayList;
    private View view;
    private Spinner categoriaSpinner;
    private Button registrarNoticiaButton;
    private Categoria_noticia_manual categoria_noticia_selecionado;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private ImageView foto_gallery;
    private Imagen_noticia imagen_noticia;
    private Bitmap bitmap;
    public static Noticia noticiaModificar;
    private boolean categoriaCambiada = false;
    private boolean tituloCambiado = false;
    private boolean imagenCambiada = false;
    private boolean contenidoCambiado = false;
    public static NoticiaActualizada noticiaActualizada;
    private String urlNoticia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_noticia);
        cargar_categorias();
        tituloEditText = findViewById(R.id.tituloEditTextRegistrarNoticia);
        contenidoEditText = findViewById(R.id.contenidoEditTextRegistrarNoticia);
        categoriaSpinner = findViewById(R.id.categoriaspinnerRegistrar_noticia);
        tituloEditText = findViewById(R.id.tituloEditTextRegistrarNoticia);
        cargarImagenButton = findViewById(R.id.cargarimagenButtonRegistrarNoticia);
        registrarNoticiaButton = findViewById(R.id.registrar_noticiaButtonRegistrarNoticia);
        foto_gallery = findViewById(R.id.imagenImageViewRegistrarImagen);
        iniciareventos();
        cargarDatosNoticia();

    }

    private void cargarDatosNoticia()
    {
        tituloEditText.setText(noticiaModificar.titulo_noticia);
        contenidoEditText.setText(noticiaModificar.contenido_noticia);
        cargarImagenNoticia();
    }

    private void cargarImagenNoticia()
    {
        ArrayList<Imagen_noticia> imagen_noticias = new Gestion_imagen_noticia().generar_json(noticiaModificar.json_imagenes);
        if(imagen_noticias.isEmpty())
        {
            Picasso.with(getBaseContext()).load(R.drawable.perfil2).into(foto_gallery);
        }
        else
        {
            imagen_noticia = imagen_noticias.get(0);
            Picasso.with(getBaseContext()).load(imagen_noticia.url_imagen_noticia).placeholder(R.drawable.perfil2)
                    .error(R.drawable.perfil2).into(foto_gallery);
        }
    }

    private void iniciareventos()
    {
        registrarNoticiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noticiaModificar.titulo_noticia.equals(tituloEditText.getText().toString()))
                {
                    tituloCambiado = false;
                }
                else
                {
                    tituloCambiado = true;
                }
                if(contenidoEditText.getText().toString().equals(noticiaModificar.contenido_noticia))
                {
                    contenidoCambiado = false;
                }
                else
                {
                    contenidoCambiado = true;
                }
                if(bitmap != null)
                {
                    imagenCambiada = true;
                }
                else
                {
                    imagenCambiada = false;
                }

                if(categoriaCambiada || tituloCambiado || imagenCambiada || contenidoCambiado)
                {
                    Noticia noticia = new Noticia();
                    if(tituloEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(getBaseContext(), "Ingrese el titulo de la noticia", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        noticia.titulo_noticia = tituloEditText.getText().toString();
                    }
                    if(contenidoEditText.getText().toString().isEmpty())
                    {
                        Toast.makeText(getBaseContext(), "Ingrese el contenido de la noticia", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        noticia.contenido_noticia = contenidoEditText.getText().toString();
                    }
                    if(categoria_noticia_selecionado == null)
                    {
                        Toast.makeText(getBaseContext(), "Selecione la categoria de la noticia", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        noticia.categoria_noticia_manual_noticia = categoria_noticia_selecionado.id_categoria_noticia_manual;
                    }
                    modificarNoticia(noticia);
                }
                else
                {
                    Toast.makeText(getBaseContext(),"No se registro ningun cambio.", Toast.LENGTH_LONG).show();
                }
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
                    if(categoria_noticia_selecionado.id_categoria_noticia_manual == noticiaModificar.categoria_noticia_manual_noticia)
                    {
                        categoriaCambiada = false;
                    }
                    else
                    {
                        categoriaCambiada = true;
                    }
                }
                else
                {
                    categoria_noticia_selecionado = null;
                    categoriaCambiada = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void resetearComparaciones()
    {
        tituloCambiado = true;
        contenidoCambiado = true;
        imagenCambiada = true;
        categoriaCambiada= true;
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
                imagenCambiada = true;
                bitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), imageUri);
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

    private void modificarNoticia(Noticia noticia)
    {
        noticia.administrador_noticia = Gestion_administrador.getAdministrador_actual().id_administrador;
        noticia.id_notiticia = noticiaModificar.id_notiticia;
        HashMap<String, String> hashMap = new Gestion_noticia().update(noticia);
        Log.d("parametros", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
    {
        @Override
        public void onResponse(String response) {
            //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
            noticiaRegistrada(response);
        }
    };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Ocurrio un error en la conexcion, registro cancelado.", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void noticiaRegistrada(String response)
    {
        int valor = -1;
        try
        {
            valor = Integer.parseInt(response);
            urlNoticia = imagen_noticia.url_imagen_noticia;
        }
        catch (NumberFormatException exc)
        {

        }
        if(valor > 0)
        {
            noticiaModificar.titulo_noticia = tituloEditText.getText().toString();
            noticiaModificar.contenido_noticia = contenidoEditText.getText().toString();
            noticiaModificar.categoria_noticia_manual_noticia = categoria_noticia_selecionado.id_categoria_noticia_manual;
            if(bitmap != null)
            {
                registrar_imagen(valor);
            }
            else
            {
                noticiaActualizada.noticiaCambiada(noticiaModificar);
            }
            Toast.makeText(getBaseContext(), "Noticia y imagen registrada con exito.", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Error al registrar noticia.", Toast.LENGTH_LONG).show();
        }

    }

    private void registrar_imagen(int id)
    {
        if(imagen_noticia != null)
        {
            if(imagen_noticia.url_imagen_noticia.contains("http://31.220.63.102/WScomfacesar/"))
            {
                imagen_noticia.url_imagen_anterior_noticia = imagen_noticia.url_imagen_noticia.replace("http://31.220.63.102/WScomfacesar/","");
            }
            else
            {
                imagen_noticia.url_imagen_noticia = "-1";
            }
        }
        else
        {
            imagen_noticia = new Imagen_noticia();
            imagen_noticia.url_imagen_anterior_noticia = "-1";
        }
        imagen_noticia.url_imagen_noticia = bitmap_conver_to_String(bitmap);
        imagen_noticia.noticia_imagen_noticia = id;
        HashMap<String, String> hashMap = new Gestion_imagen_noticia().subir_y_eliminar_imagen(imagen_noticia);
        Log.d("parametros imagen", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                ArrayList<Imagen_noticia> imagen_noticias = new Gestion_imagen_noticia().generar_json(response);
                if(!imagen_noticias.isEmpty())
                {
                    imagen_noticia = imagen_noticias.get(0);
                    noticiaModificar.json_imagenes = response;
                    noticiaActualizada.noticiaCambiada(noticiaModificar);
                }
                bitmap = null;
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Ha ocurrido un error en el servidor", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_categorias()
    {
        HashMap<String, String> hashMap = new Gestion_categoria_noticia().consultar_categorias();
        Log.d("parametros", hashMap.toString());
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
                Toast.makeText(getBaseContext(),"Error en la conexion.", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
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
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, categoria_array);
        categoriaSpinner.setAdapter(arrayAdapter);
        int cont = 1;
        for(Categoria_noticia_manual item : categoria_noticiaArrayList)
        {
            if(item.id_categoria_noticia_manual == noticiaModificar.categoria_noticia_manual_noticia)
            {
                categoriaSpinner.setSelection(cont);
            }
            cont ++;
        }
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

    public interface NoticiaActualizada
    {
        void noticiaCambiada(Noticia noticia);
    }
}
