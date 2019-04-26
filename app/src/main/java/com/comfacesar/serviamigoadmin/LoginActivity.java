package com.comfacesar.serviamigoadmin;


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
import com.comfacesar.extra.MySocialMediaSingleton;
import com.comfacesar.extra.WebService;
import com.comfacesar.gestion.Gestion_administrador;
import com.comfacesar.modelo.Administrador;

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

    private void iniciarSesion(final Administrador administrador)
    {
        HashMap<String,String> params = new Gestion_administrador().validar_administrador(administrador);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try
                {
                    Integer.parseInt(response);
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Datos no validos", Toast.LENGTH_LONG).show();
                }
                catch(NumberFormatException exc)
                {
                    ArrayList<Administrador> arrayList = new Gestion_administrador().generar_json(response);
                    if(!arrayList.isEmpty())
                    {
                        arrayList.get(0).contrasena_administrador = administrador.contrasena_administrador;
                        Gestion_administrador.setAdministrador_actual(arrayList.get(0));
                        if (Gestion_administrador.getAdministrador_actual().tipo_administrador == 1) {
                            Toast.makeText(getBaseContext(), "Administrador conectado", Toast.LENGTH_LONG).show();
                        }
                        if (Gestion_administrador.getAdministrador_actual().tipo_administrador == 2) {
                            Toast.makeText(getBaseContext(), "Asesor conectado", Toast.LENGTH_LONG).show();
                        }
                        salvarSesion();
                        Intent intent = new Intent(LoginActivity.this, Navigation.class);
                        progressDialog.dismiss();
                        contraseñaEditText.setText("");
                        startActivity(intent);
                    }
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

    public static void quitarSesionAdministrador()
    {

        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putString("TOKEN", "-1");
        myEditor.commit();
    }

    private void salvarSesion()
    {
        prefs = getSharedPreferences("SESION", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putString("TOKEN", Gestion_administrador.getAdministrador_actual().token);
        myEditor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gestion_sesion gestion_sesion = new Gestion_sesion(LoginActivity.this,new Gestion_sesion.EscuchadoAsignarAdministrador() {
            @Override
            public void responseOk(String response, String contrasena, Gestion_sesion gestion_sesion1) {
                if(!response.equals(""))
                {
                    Log.d("response", response);
                    ArrayList<Administrador> arrayList = new Gestion_administrador().generar_json(response);
                    if(!arrayList.isEmpty())
                    {
                        arrayList.get(0).contrasena_administrador = contrasena;
                        Gestion_administrador.setAdministrador_actual(arrayList.get(0));
                        gestion_sesion1.salvarSesion();
                        Intent intent = new Intent(LoginActivity.this, Navigation.class);
                        progressDialog.dismiss();
                        contraseñaEditText.setText("");
                        startActivity(intent);
                    }
                }
                else
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void responseError(Gestion_sesion gestion_sesion1) {
                gestion_sesion1.quitarSesionAdministrador();
            }
        });
        gestion_sesion.recuperarSesion();
        gestion_sesion.asignarAdministrador();
    }
}

