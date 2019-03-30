package com.example.serviamigoadmin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_chat_asesoria;
import com.example.gestion.Gestion_especialidad;
import com.example.gestion.Gestion_mensaje_chat_asesoria;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Especialidad;
import com.example.modelo.Mensaje_chat_asesoria;
import com.example.modelo.Usuario;
import com.example.serviamigoadmin.Adapter.Adapter_Mensajes_Chat;
import com.example.serviamigoadmin.Navigation;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatAsesoriaActivity extends AppCompatActivity {
    public static Chat_asesoria chat_asesoria;
    private String ultima_fecha = "";
    private String ultima_hora = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias;
    private RecyclerView recyclerView_chat_asesoria;
    private EditText mensajeEditText;
    private Button enviarButton;
    private Boolean mensaje_enviado;
    private Boolean consultando;
    private Adapter_Mensajes_Chat adapter_mensajes_chat_asesoria;
    private boolean fragment_activo;
    private TextView nombreUsuarioTextView;
    private int id_chat_notificacion;
    public static CambioEstado cambioEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_asesoria);
        mensaje_chat_asesorias = new ArrayList<>();
        fragment_activo = true;
        recyclerView_chat_asesoria = findViewById(R.id.mensajes_chat_asesoria_recyclerview);
        recyclerView_chat_asesoria.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
        nombreUsuarioTextView = findViewById(R.id.nombreUsuarioTextView);

        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        toolbar.setBackgroundResource(R.color.Gris3);
        ShowToolbar(".",true);


        mensajeEditText = findViewById(R.id.mensajeEdittext);
        enviarButton = findViewById(R.id.enviarButton);
        try
        {
            id_chat_notificacion = getIntent().getExtras().getInt("chat");
            Gestion_chat_asesoria.chat_abiero(id_chat_notificacion);
            chat_asesoria = Navigation.chat_asesoria_por_id(id_chat_notificacion);
        }
        catch (NullPointerException exc)
        {

        }

        ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(chat_asesoria.usuario);
        if(!usuarios.isEmpty())
        {
            Usuario usuario = usuarios.get(0);
            ShowToolbar(usuario.nombre_cuenta_usuario,true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                            }
                        };
                        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
                        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
                    }
                    mensajeEditText.setText("");
                }
            }
        });
        Gestion_chat_asesoria.chat_abiero(chat_asesoria.id_chat_asesoria);
    }

    public interface CambioEstado
    {
        void cambio(boolean estado, Activity activity);
    }

    public void ShowToolbar(String Tittle, boolean upButton)
    {
        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void chatVisto()
    {
        HashMap<String,String> params = new Gestion_chat_asesoria().vista_por_administrador(chat_asesoria.id_chat_asesoria);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                consultando = false;
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
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
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_mensajes_de_inicio(String response)
    {
        consultando = true;
        mensaje_chat_asesorias = new Gestion_mensaje_chat_asesoria().generar_json(response);
        //Collections.reverse(mensaje_chat_asesorias);
        adapter_mensajes_chat_asesoria = new Adapter_Mensajes_Chat(mensaje_chat_asesorias, chat_asesoria);
        cambioEstado.cambio(true, this);
        if(!mensaje_chat_asesorias.isEmpty())
        {
            recyclerView_chat_asesoria.smoothScrollToPosition(mensaje_chat_asesorias.size() - 1);
            ultima_fecha = mensaje_chat_asesorias.get(mensaje_chat_asesorias.size() - 1).fecha_envio_mensaje_chat_asesoria;
            ultima_hora = mensaje_chat_asesorias.get(mensaje_chat_asesorias.size() - 1).hora_envio_mensaje_asesoria;
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
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                consultar_mensajes_nuevos();
                            }
                        });
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
            params = new Gestion_mensaje_chat_asesoria().mensaje_chat_asesoria_por_chat_mayor(ultima_fecha, ultima_hora, chat_asesoria.id_chat_asesoria);
        }
        else
        {
            params = new Gestion_mensaje_chat_asesoria().mensajes_asesoria_por_asesoria(chat_asesoria.id_chat_asesoria);
        }
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
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
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
            ultima_fecha = mensaje_chat_asesorias_aux.get(mensaje_chat_asesorias_aux.size() - 1).fecha_envio_mensaje_chat_asesoria;
            ultima_hora = mensaje_chat_asesorias_aux.get(mensaje_chat_asesorias_aux.size() - 1).hora_envio_mensaje_asesoria;
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

    @Override
    protected void onResume() {
        super.onResume();
        fragment_activo = true;
        mensaje_enviado = false;
        iniciar_conexion_chat();
        if(cambioEstado != null)
        {
            cambioEstado.cambio(true, this);
        }
        chatVisto();
    }

    @Override
    public void onPause() {
        super.onPause();
        fragment_activo = false;
        if(cambioEstado != null)
        {
            cambioEstado.cambio(false, this);
        }
    }

    @Override
    public void onBackPressed() {
        if(id_chat_notificacion > 0)
        {
            Intent intent = new Intent(getBaseContext(), Navigation.class);
            startActivity(intent);
        }
        else
        {
            super.onBackPressed();
        }
    }

}
