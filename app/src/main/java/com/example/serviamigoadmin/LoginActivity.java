package com.example.serviamigoadmin;


import android.app.ProgressDialog;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Button iniciar_secionButton = (Button) findViewById(R.id.inicio_sesion_Button);
        iniciar_secionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                progressDialog.setCancelable(false);
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
        HashMap<String,String> params = new Gestion_administrador().validar_administrador(administrador);
        Log.d("parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                Log.d("response", response);
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val == 0)
                    {
                        Toast.makeText(getBaseContext(), "Los datos ingresados no coindicen", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    else
                    {
                        asignar_aministrador(val);
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
                Toast.makeText(getBaseContext(), "Ocurrio un error en el servidor", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void asignar_aministrador(int id_administrador)
    {
        HashMap<String,String> params = new Gestion_administrador().consultar_administrador_por_id(id_administrador);
        Log.d("parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                Log.d("response", response);
                if(!response.equals(""))
                {
                    ArrayList<Administrador> arrayList = new Gestion_administrador().generar_json(response);
                    if(!arrayList.isEmpty())
                    {
                        Toast.makeText(getBaseContext(), "Administrador conectado", Toast.LENGTH_LONG).show();
                        Administrador administrador = arrayList.get(0);
                        administrador.contrasena_administrador = contraseñaEditText.getText().toString();
                        Gestion_administrador.setAdministrador_actual(administrador);
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
                Toast.makeText(getBaseContext(), "Ocurrio un error en el servidor", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }
}

