package com.example.serviamigoadmin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.Calculo;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_chat_asesoria;
import com.example.gestion.Gestion_especialidad;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Especialidad;
import com.example.modelo.Usuario;
import com.example.serviamigoadmin.Activity.ChatAsesoriaActivity;
import com.example.serviamigoadmin.Fragment.Actualizar_AdministradorFragment;
import com.example.serviamigoadmin.Fragment.CambiarContrasenaFragment;
import com.example.serviamigoadmin.Fragment.ChatUsuarioFragment;
import com.example.serviamigoadmin.Fragment.ConsultaAlertasTempranasFragment;
import com.example.serviamigoadmin.Fragment.ConsultarAsesoresFragment;
import com.example.serviamigoadmin.Fragment.EstadisticaUsuariosFragment;
import com.example.serviamigoadmin.Fragment.MisAsesoriasFragment;
import com.example.serviamigoadmin.Fragment.Registrar_AdministradorFragment;
import com.example.serviamigoadmin.Fragment.Registrar_noticiaFragment;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConsultaAlertasTempranasFragment.OnFragmentInteractionListener, Registrar_noticiaFragment.OnFragmentInteractionListener, MisAsesoriasFragment.OnFragmentInteractionListener, ChatUsuarioFragment.OnFragmentInteractionListener, Registrar_AdministradorFragment.OnFragmentInteractionListener, CambiarContrasenaFragment.OnFragmentInteractionListener, Actualizar_AdministradorFragment.OnFragmentInteractionListener, EstadisticaUsuariosFragment.OnFragmentInteractionListener, ConsultarAsesoresFragment.OnFragmentInteractionListener {
    private ConsultaAlertasTempranasFragment consultaAlertasTempranasFragment;
    private NotificationManagerCompat notificationManagerCompat;
    private static ArrayList<Chat_asesoria> chat_asesorias_local;
    private ArrayList<Chat_asesoria> chat_asesorias_remoto;
    private boolean hilo_notificaciones_activo = false;
    private final String CHANEL_ID = "NOTIFICACION";
    private DrawerLayout drawer;
    private boolean seguir;

    public Navigation()
    {
        if(Gestion_administrador.cambioAdministrador == null)
        {
            Gestion_administrador.cambioAdministrador = new Gestion_administrador.CambioAdministrador() {
                @Override
                public void administradorCambiado(Administrador administrador) {
                    if(administrador != null)
                    {
                        Log.d("administrador", "valido");
                        hilo_notificaciones_activo = true;
                        iniciar_hilo_notificaciones();
                    }
                    else
                    {
                        Log.d("administrador", "null");
                        hilo_notificaciones_activo = false;
                        if(chat_asesorias_local != null)
                        {
                            for(Chat_asesoria item : chat_asesorias_local)
                            {
                                notificationManagerCompat.cancel(item.id_chat_asesoria);
                            }
                            chat_asesorias_local.clear();
                            chat_asesorias_local = null;
                        }
                    }
                }
            };
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 1)
        {
            setContentView(R.layout.navigation_administrador);
        }
        else
        {
            setContentView(R.layout.navigation_asesor);
        }
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
        if(Gestion_administrador.getAdministrador_actual().tipo_administrador == 1)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.framengMaster,new ConsultaAlertasTempranasFragment()).commit();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().add(R.id.framengMaster,new MisAsesoriasFragment()).commit();
        }
    }

    public static void iniciarEscuchador()
    {

    }
    private void iniciar_hilo_notificaciones()
    {
        chat_asesorias_local = null;
        actualizar_notificaciones_chat();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("hilo", "iniciado");
                int time = 3000;
                while (hilo_notificaciones_activo)
                {
                    Log.d("hilo", "en ciclo");
                    if(time >= 3000)
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
                        Thread.sleep(100);
                        time += 100;
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
            Log.d("parametros", params.toString());
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    chat_asesorias_remoto = new Gestion_chat_asesoria().generar_json(response);
                    if(chat_asesorias_remoto != null)
                    {
                        if(chat_asesorias_local == null)
                        {
                            chat_asesorias_local = new ArrayList<>();
                            chat_asesorias_local.addAll(chat_asesorias_remoto);
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
                                chat_asesorias_local.add(item);
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
                                    chat_asesorias_local.add(item);
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
                Calendar calendar = Calculo.String_a_Date( date1, time1);
                Calendar calendar2 = Calculo.String_a_Date(date2, time2);
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
        reemplazar_chat_local(item);
        createNotificationChanel();
        createNotification(item.ultimo_mensaje_usuario_chat_asesoria, titulo, item.id_chat_asesoria);
    }

    public static Chat_asesoria chat_asesoria_por_id(int id)
    {
        for(Chat_asesoria item : chat_asesorias_local)
        {
            if(item.id_chat_asesoria == id)
            {
                return item;
            }
        }
        return null;
    }

    public void reemplazar_chat_local(Chat_asesoria chat_asesoria)
    {
        int tam = chat_asesorias_local.size();
        for(int i = 0; i < tam; i ++)
        {
            if(chat_asesorias_local.get(i).id_chat_asesoria == chat_asesoria.id_chat_asesoria)
            {
                chat_asesorias_local.add(i, chat_asesoria);
            }
        }
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
        Intent resultIntent = new Intent(getBaseContext(), ChatAsesoriaActivity.class);
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
        seguir = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void iniciar_hilo_validacion()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int cont = 5000;
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
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
            fragment = consultaAlertasTempranasFragment;
            selecionado = true;
        }
        if (id == R.id.crear_noticia) {
            fragment = new Registrar_noticiaFragment();
            selecionado = true;
        }
        if (id == R.id.ver_mis_Asesorias) {
            fragment = new MisAsesoriasFragment();
            selecionado = true;
        }
        if (id == R.id.registrar_asesor) {
            fragment = new Registrar_AdministradorFragment();
            selecionado = true;
        }
        if (id == R.id.ver_asesores) {
            fragment = new ConsultarAsesoresFragment();
            selecionado = true;
        }
        if (id == R.id.cambiar_contraseña) {
            fragment = new CambiarContrasenaFragment();
            selecionado = true;
        }
        if (id == R.id.actualizar_mis_datos) {
            fragment = new Actualizar_AdministradorFragment();
            selecionado = true;
        }
        if (id == R.id.estadistica_numero_usuario) {
            fragment = new EstadisticaUsuariosFragment();
            selecionado = true;
        }
        if (id == R.id.cerrar_sesion) {
            finish();
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



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
