package com.comfacesar.serviamigoadmin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.extra.Calculo;
import com.comfacesar.extra.MySocialMediaSingleton;
import com.comfacesar.extra.WebService;
import com.comfacesar.gestion.Gestion_administrador;
import com.comfacesar.gestion.Gestion_chat_asesoria;
import com.comfacesar.gestion.Gestion_especialidad;
import com.comfacesar.gestion.Gestion_usuario;
import com.comfacesar.modelo.Administrador;
import com.comfacesar.modelo.Chat_asesoria;
import com.comfacesar.modelo.Especialidad;
import com.comfacesar.modelo.Usuario;
import com.comfacesar.serviamigoadmin.Activity.ChatAsesoriaActivity;
import com.comfacesar.serviamigoadmin.Fragment.Actualizar_AdministradorFragment;
import com.comfacesar.serviamigoadmin.Fragment.CambiarContrasenaFragment;
import com.comfacesar.serviamigoadmin.Fragment.ChatUsuarioFragment;
import com.comfacesar.serviamigoadmin.Fragment.ConsultaAlertasTempranasFragment;
import com.comfacesar.serviamigoadmin.Fragment.ConsultarAsesoresFragment;
import com.comfacesar.serviamigoadmin.Fragment.EstadisticaUsuariosFragment;
import com.comfacesar.serviamigoadmin.Fragment.ListaNoticiasFragment;
import com.comfacesar.serviamigoadmin.Fragment.MisAsesoriasFragment;
import com.comfacesar.serviamigoadmin.Fragment.Modificar_noticiaFragment;
import com.comfacesar.serviamigoadmin.Fragment.Registrar_AdministradorFragment;
import com.comfacesar.serviamigoadmin.Fragment.Registrar_noticiaFragment;
import com.comfacesar.serviamigoadmin.Fragment.Vista_vacia_fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConsultaAlertasTempranasFragment.OnFragmentInteractionListener, Registrar_noticiaFragment.OnFragmentInteractionListener, MisAsesoriasFragment.OnFragmentInteractionListener, ChatUsuarioFragment.OnFragmentInteractionListener, Registrar_AdministradorFragment.OnFragmentInteractionListener, CambiarContrasenaFragment.OnFragmentInteractionListener, Actualizar_AdministradorFragment.OnFragmentInteractionListener, EstadisticaUsuariosFragment.OnFragmentInteractionListener, ConsultarAsesoresFragment.OnFragmentInteractionListener, ListaNoticiasFragment.OnFragmentInteractionListener, Modificar_noticiaFragment.OnFragmentInteractionListener, Vista_vacia_fragment.OnFragmentInteractionListener, ListaAsesoriaVacia.OnFragmentInteractionListener {
    private ConsultaAlertasTempranasFragment consultaAlertasTempranasFragment;
    private NotificationManagerCompat notificationManagerCompat;
    private static ArrayList<Chat_asesoria> chat_asesorias_local;
    private boolean hilo_notificaciones_activo = false;
    private final String CHANEL_ID = "NOTIFICACION";
    private DrawerLayout drawer;
    public static boolean hilo_notificacion_iniciado;
    private final int CICLO_NOTIFICACIONES = 5000;
    private final int SUMA = 100;
    private boolean aplicacion_terminada;
    private TextView tiulo_tollba;

    public Navigation()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Gestion_administrador.getAdministrador_actual() != null)
        {
            if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 1)
            {
                setContentView(R.layout.navigation_administrador);
            }
            else if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 2)
            {
                setContentView(R.layout.navigation_asesor);
            }
            iniciarActividadesNormales();
        }
        else
        {
            recuperarSesion();
        }

    }

    private void iniciarActividadesNormales()
    {
        tiulo_tollba= findViewById(R.id.titulo_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        consultaAlertasTempranasFragment = new ConsultaAlertasTempranasFragment();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.framengMaster,new Vista_vacia_fragment()).commit();
        Log.d("administrador", "valido");
        hilo_notificaciones_activo = true;
        if(!hilo_notificacion_iniciado)
        {
            hilo_notificacion_iniciado = true;
            iniciar_hilo_notificaciones();
        }
        Gestion_chat_asesoria.setChatAbierto(new Gestion_chat_asesoria.ChatAbierto() {
            @Override
            public void abierto(int id_chat) {
                /***
                 * Cierra la notificacion segun el chat abierto por el usuario
                 */
                notificationManagerCompat = NotificationManagerCompat.from(Navigation.this);
                notificationManagerCompat.cancel(id_chat);
            }
        });
        aplicacion_terminada = false;
    }

    private void recuperarSesion()
    {
        SharedPreferences prefs = getSharedPreferences("SESION", Context.MODE_PRIVATE);
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
        else
        {
            cerrarSesion();
        }
    }

    private void salvarSesion()
    {
        SharedPreferences prefs = getSharedPreferences("SESION", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putInt("ID", Gestion_administrador.getAdministrador_actual().id_administrador);
        myEditor.putString("USER", Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
        myEditor.putString("PASS", Gestion_administrador.getAdministrador_actual().contrasena_administrador);
        myEditor.commit();
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
                        salvarSesion();
                        if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 1)
                        {
                            setContentView(R.layout.navigation_administrador);
                        }
                        else if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 2)
                        {
                            setContentView(R.layout.navigation_asesor);
                        }
                        iniciarActividadesNormales();
                    }
                    else
                    {
                        cerrarSesion();
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                quitarSesionAdministrador();
                cerrarSesion();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void cerrarSesion()
    {
        setContentView(R.layout.navigation_administrador);
        tiulo_tollba= findViewById(R.id.titulo_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Gestion_administrador.setAdministrador_actual(null);
        onBackPressed();
        finish();
    }

    public void quitarSesionAdministrador()
    {
        SharedPreferences prefs = getSharedPreferences("SESION", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putInt("ID", -1);
        myEditor.putString("USER", "-1");
        myEditor.putString("PASS", "-1");
        myEditor.commit();
    }

    private void iniciar_hilo_notificaciones()
    {
        chat_asesorias_local = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = CICLO_NOTIFICACIONES;
                while (hilo_notificaciones_activo)
                {
                    if(time >= CICLO_NOTIFICACIONES)
                    {
                        time = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                actualizar_notificaciones_chat();
                            }
                        });
                    }
                    try {
                        Thread.sleep(SUMA);
                        time += SUMA;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void actualizar_notificaciones_chat()
    {
        if(Gestion_administrador.getAdministrador_actual() != null)
        {
            HashMap<String,String> params = new Gestion_chat_asesoria().consultar_por_administrador(Gestion_administrador.getAdministrador_actual().id_administrador);
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    ArrayList<Chat_asesoria> chat_asesorias_remoto = new Gestion_chat_asesoria().generar_json(response);
                    if(chat_asesorias_remoto != null)
                    {
                        if(Gestion_chat_asesoria.getChat_asesorias() == null)
                        {
                            for(Chat_asesoria item :  chat_asesorias_remoto)
                            {
                                ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(item.usuario);
                                ArrayList<Especialidad> especialidads = new Gestion_especialidad().generar_json(item.especialidad);
                                String titulo = "";
                                if(!usuarios.isEmpty() && !especialidads.isEmpty())
                                {
                                    Usuario usuario = usuarios.get(0);
                                    Especialidad especialidad = especialidads.get(0);
                                    titulo = usuario.nombre_cuenta_usuario + " <<" + especialidad.nombre_especialidad + ">>";
                                }
                                if(!usuarios.isEmpty() && !especialidads.isEmpty())
                                {
                                    aux(item, titulo);
                                }
                            }
                        }
                        else
                        {
                            for(Chat_asesoria item :  chat_asesorias_remoto)
                            {
                                Chat_asesoria chat_asesoria = chat_asesoria_por_id(item.id_chat_asesoria);
                                ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(item.usuario);
                                ArrayList<Especialidad> especialidads = new Gestion_especialidad().generar_json(item.especialidad);
                                String titulo = "";
                                if(!usuarios.isEmpty() && !especialidads.isEmpty())
                                {
                                    Usuario usuario = usuarios.get(0);
                                    Especialidad especialidad = especialidads.get(0);
                                    titulo = usuario.nombre_cuenta_usuario + " <<" + especialidad.nombre_especialidad + ">>";
                                }
                                if(chat_asesoria == null)
                                {
                                    if(!usuarios.isEmpty() && !especialidads.isEmpty())
                                    {
                                        aux(item, titulo);
                                    }
                                }
                                else
                                {
                                    if(!(item.ultima_fecha_usuario_chat_asesoria + item.ultima_hora_usuario_chat_asesoria).equals(chat_asesoria.ultima_fecha_usuario_chat_asesoria + chat_asesoria.ultima_hora_usuario_chat_asesoria))
                                    {
                                        aux(item, titulo);
                                    }
                                }
                            }
                        }
                        Gestion_chat_asesoria.setChat_asesorias(chat_asesorias_remoto);
                    }
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
            MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
        }
    }

    private void aux(Chat_asesoria item, String titulo)
    {
        boolean valido = false;
        if(!item.ultima_fecha_usuario_chat_asesoria.equals("-1"))
        {
            if(!item.ultima_hora_usuario_chat_asesoria.equals("-1"))
            {
                valido = true;
            }
            if(!valido)
            {
                agregar_notificacion(item, titulo);
            }
            else
            {
                String date1 = item.ultima_fecha_usuario_chat_asesoria;
                String time1 = item.ultima_hora_usuario_chat_asesoria;
                String date2 = item.ultima_fecha_vista_administrador_chat_asesoria;
                String time2 = item.ultima_hora_vista_administrador_chat_asesoria;
                Calendar calendar = new Calculo().String_a_Date( date1, time1);
                Calendar calendar2 = new Calculo().String_a_Date(date2, time2);
                if(!(date1 + time1).equals(date2+time2))
                {
                    if(Calculo.compararCalendar(calendar,calendar2) == 1)
                    {
                        agregar_notificacion(item, titulo);
                    }
                }
            }
        }
    }

    private void agregar_notificacion(Chat_asesoria item, String titulo)
    {
        createNotificationChanel();
        createNotification(item.ultimo_mensaje_usuario_chat_asesoria, titulo, item.id_chat_asesoria);
    }

    public static Chat_asesoria chat_asesoria_por_id(final int ID)
    {
        for(Chat_asesoria item : Gestion_chat_asesoria.getChat_asesorias())
        {
            if(item.id_chat_asesoria == ID)
            {
                return item;
            }
        }
        return null;
    }

    private void createNotificationChanel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(String mensaje, String titulo, int id)
    {
        Intent resultIntent = new Intent(Navigation.this, ChatAsesoriaActivity.class);
        resultIntent.putExtra("chat", id);

// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(titulo);
        builder.setContentText(mensaje);
        builder.setColor(Color.RED);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.RED, 1000, 1000);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setContentIntent(resultPendingIntent);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(id, builder.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed()
    {
        if(aplicacion_terminada)
        {
            finish();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            } else
            {
                moveTaskToBack(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        boolean selecionado = false;


        if (id == R.id.consulta_alertas_tempranas) {
            fragment = new ConsultaAlertasTempranasFragment();
            selecionado = true;
            tiulo_tollba.setText("Lista Alertas");

        }
        if (id == R.id.crear_noticia) {
            fragment = new Registrar_noticiaFragment();
            selecionado = true;
            tiulo_tollba.setText("Crear Articulo");
        }
        if (id == R.id.ver_mis_Asesorias) {
            tengoAsesorias();
        }
        if (id == R.id.registrar_asesor) {
            fragment = new Registrar_AdministradorFragment();
            selecionado = true;
            tiulo_tollba.setText("Registro Asesor");
        }
        if (id == R.id.ver_asesores) {
            fragment = new ConsultarAsesoresFragment();
            selecionado = true;
            tiulo_tollba.setText("Lista Asesores");
        }
        if (id == R.id.cambiar_contraseña) {
            fragment = new CambiarContrasenaFragment();
            selecionado = true;
            tiulo_tollba.setText("Cambiar Contraseña");
        }
        if (id == R.id.actualizar_mis_datos) {
            fragment = new Actualizar_AdministradorFragment();
            selecionado = true;
            tiulo_tollba.setText("Actualizar Datos");
        }
        if (id == R.id.estadistica_numero_usuario) {
            fragment = new EstadisticaUsuariosFragment();
            selecionado = true;
            tiulo_tollba.setText("Estadisticas");
        }
        if(id == R.id.cosultar_noticias)
        {
            fragment = new ListaNoticiasFragment();
            selecionado = true;
            tiulo_tollba.setText("Lista Noticias");
        }
        if (id == R.id.cerrar_sesion) {
            cerrar_sesion();
            return false;
        }
        if(selecionado)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.framengMaster,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrar_sesion()
    {
        HashMap<String, String> hashMap = new Gestion_administrador().cerrar_sesion();
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val == 1)
                    {
                        new Gestion_sesion(Navigation.this).quitarSesionAdministrador();
                        Log.d("administrador", "null");
                        Gestion_administrador.setAdministrador_actual(null);
                        hilo_notificaciones_activo = false;
                        hilo_notificacion_iniciado = false;
                        notificationManagerCompat = NotificationManagerCompat.from(Navigation.this);
                        if(chat_asesorias_local != null)
                        {
                            for(Chat_asesoria chat : chat_asesorias_local)
                            {
                                notificationManagerCompat.cancel(chat.id_chat_asesoria);
                            }
                            chat_asesorias_local.clear();
                            chat_asesorias_local = null;
                        }
                        aplicacion_terminada = true;
                        Toast.makeText(getBaseContext(),"Sesion finalizada.", Toast.LENGTH_LONG).show();
                        new Gestion_sesion(Navigation.this).quitarSesionAdministrador();
                        onBackPressed();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(),"Sesion no finalizada.", Toast.LENGTH_LONG).show();
                    }
                }
                catch(NumberFormatException exc)
                {
                    Toast.makeText(getBaseContext(),"Ocurrio un error en la conexcion.", Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Ocurrio un error en la conexcion.", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);

    }

    private void tengoAsesorias()
    {
        Fragment fragment;
        if(!Gestion_chat_asesoria.getChat_asesorias().isEmpty())
        {
            fragment = new MisAsesoriasFragment();
            tiulo_tollba.setText("Mis Asesorias");
        }
        else
        {
            fragment = new ListaAsesoriaVacia();
            tiulo_tollba.setText("Mis Asesorias");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.framengMaster,fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
