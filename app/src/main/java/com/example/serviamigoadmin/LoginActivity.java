package com.example.serviamigoadmin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.modelo.Administrador;
import com.example.servimaigoadmin.R;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
public class    LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText cuentaEditText;
    private EditText contraseñaEditText;
    private ProgressDialog progressDialog;
    private static SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Navigation.hilo_notificacion_iniciado = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        progressDialog = new ProgressDialog(LoginActivity.this);
        cuentaEditText = findViewById(R.id.nombreCuentaEditTextLogin);
        contraseñaEditText = (EditText) findViewById(R.id.contraseñaEditTextLogin);
        cuentaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    cuentaEditText.setSelection(0, cuentaEditText.getText().toString().length());
                }
            }
        });
        contraseñaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    contraseñaEditText.setSelection(0, contraseñaEditText.getText().toString().length());
                }
            }
        });
        contraseñaEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    abrir_ventana_principal();
                }
                return false;
            }
        });
        Button iniciar_secionButton = findViewById(R.id.inicio_sesion_Button);
        iniciar_secionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Conetandose a SERVIAMIGO");
                abrir_ventana_principal();
            }
        });
    }


    private void abrir_ventana_principal()
    {
        Administrador administrador = new Administrador();
        if(cuentaEditText.getText().toString().isEmpty())
        {
            Toast.makeText(getBaseContext(), "Ingrese el nombre de la cuenta", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        else
        {
            administrador.nombre_cuenta_administrador = cuentaEditText.getText().toString();
        }
        if(contraseñaEditText.getText().toString().isEmpty())
        {
            Toast.makeText(getBaseContext(), "Ingrese la contraseña de la cuenta", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        else
        {
            administrador.contrasena_administrador = contraseñaEditText.getText().toString();
        }
        iniciarSesion(administrador);
    }

    private void iniciarSesion(Administrador administrador)
    {
        HashMap<String,String> params = new Gestion_administrador().validar_administrador(administrador);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val == 0)
                    {
                        Toast.makeText(getBaseContext(), "Los datos ingresados no coindicen", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    else if(val == -1)
                    {
                        Toast.makeText(getBaseContext(), "Esta cuenta no cuenta con permisos para iniciar sesion.", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    else
                    {
                        prepararAdministrador(val, contraseñaEditText.getText().toString());
                    }
                }
                catch(NumberFormatException exc)
                {
                    Toast.makeText(getBaseContext(), "Los datos ingresados no coindicen", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                quitarSesionAdministrador();
                Toast.makeText(getBaseContext(), "Ocurrio un error en el servidor", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void prepararAdministrador(int id_administrador, final String contrasena)
    {
        Administrador administrador = new Administrador();
        administrador.nombre_cuenta_administrador = cuentaEditText.getText().toString();
        administrador.contrasena_administrador = contraseñaEditText.getText().toString();
        asignarAdministrador(administrador, contrasena, id_administrador);
    }

    private void asignarAdministrador(Administrador administrador, final String contrasena, int id_administrador)
    {
        Gestion_administrador.setAdministrador_actual(administrador);
        HashMap<String,String> params = new Gestion_administrador().consultar_administrador_por_id(id_administrador);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                if(!response.equals(""))
                {
                    Log.d("response", response);
                    ArrayList<Administrador> arrayList = new Gestion_administrador().generar_json(response);
                    if(!arrayList.isEmpty())
                    {
                        arrayList.get(0).contrasena_administrador = contrasena;
                        Gestion_administrador.setAdministrador_actual(arrayList.get(0));
                        if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 1)
                        {
                            Toast.makeText(getBaseContext(), "Administrador conectado", Toast.LENGTH_LONG).show();
                        }
                        if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 2)
                        {
                            Toast.makeText(getBaseContext(), "Asesor conectado", Toast.LENGTH_LONG).show();
                        }
                        salvarSesion();
                        Intent intent = new Intent(LoginActivity.this, Navigation.class);
                        progressDialog.dismiss();
                        contraseñaEditText.setText("");
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Los datos ingresados no coindicen", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Los datos ingresados no coindicen", Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                quitarSesionAdministrador();
                Toast.makeText(getBaseContext(), "Ocurrio un error en el servidor", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public static void quitarSesionAdministrador()
    {
        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putInt("ID", -1);
        myEditor.putString("USER", "-1");
        myEditor.putString("PASS", "-1");
        myEditor.commit();
    }

    private void salvarSesion()
    {
        prefs = getSharedPreferences("SESION", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putInt("ID", Gestion_administrador.getAdministrador_actual().id_administrador);
        myEditor.putString("USER", Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
        myEditor.putString("PASS", Gestion_administrador.getAdministrador_actual().contrasena_administrador);
        myEditor.commit();
    }

    private void recuperarSesion()
    {
        prefs = getSharedPreferences("SESION", Context.MODE_PRIVATE);
        int id = prefs.getInt("ID", -1);
        String user = prefs.getString("USER", "-1");
        String pass = prefs.getString("PASS", "-1");
        if(id != -1 && !user.equals("-1") && !pass.equals("-1"))
        {
            Administrador administrador = new Administrador();
            administrador.id_administrador = id;
            administrador.nombre_cuenta_administrador = user;
            administrador.contrasena_administrador = pass;
            asignarAdministrador(administrador, administrador.contrasena_administrador, administrador.id_administrador);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarSesion();
    }
}

