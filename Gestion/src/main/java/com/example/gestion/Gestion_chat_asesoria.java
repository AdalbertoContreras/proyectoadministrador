package com.example.gestion;

import com.example.modelo.Chat_asesoria;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_chat_asesoria {
    private static Chat_asesoria aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Chat_asesoria();
    }
    private ArrayList<Chat_asesoria> generar_json(String respuesta)
    {
        ArrayList<Chat_asesoria> lista_elementos = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(respuesta).getAsJsonArray();
            for(JsonElement element : array )
            {
                lista_elementos.add (agregar_elemento(element.getAsJsonObject()));
            }
        }
        catch(JsonSyntaxException | IllegalStateException | NullPointerException e)
        {
            lista_elementos = new ArrayList<>();
        }
        return lista_elementos;
    }

    private Chat_asesoria agregar_elemento(final JsonObject jsonObject)
    {
        return new Chat_asesoria(){{
            try {
                id_chat_asesoria = jsonObject.get("id_chat_asesoria").getAsInt();
                fecha_inicio_asesoria = jsonObject.get("fecha_inicio_asesoria").getAsString();
                hora_inicio_asesoria = jsonObject.get("hora_inicio_asesoria").getAsString();
                fecha_cierre_asesoria = jsonObject.get("fecha_cierre_asesoria").getAsString();
                hora_cierra_chat_asesoria = jsonObject.get("hora_cierra_chat_asesoria").getAsString();
                administrador_chat_asesoria = jsonObject.get("administrador_chat_asesoria").getAsInt();
                usuario_chat_asesoria = jsonObject.get("usuario_chat_asesoria").getAsInt();
                estado_cerrado = jsonObject.get("estado_cerrado").getAsInt();
                tiempo_sesion_chat_asesoria = jsonObject.get("tiempo_sesion_chat_asesoria").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Chat_asesoria elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_chat_asesoria", elemento.id_chat_asesoria);
            obj.addProperty("fecha_inicio_asesoria", elemento.fecha_inicio_asesoria);
            obj.addProperty("hora_inicio_asesoria", elemento.hora_inicio_asesoria);
            obj.addProperty("fecha_cierre_asesoria", elemento.fecha_cierre_asesoria);
            obj.addProperty("hora_cierra_chat_asesoria", elemento.hora_cierra_chat_asesoria);
            obj.addProperty("administrador_chat_asesoria", elemento.administrador_chat_asesoria);
            obj.addProperty("usuario_chat_asesoria", elemento.usuario_chat_asesoria);
            obj.addProperty("estado_cerrado", elemento.estado_cerrado);
            obj.addProperty("tiempo_sesion_chat_asesoria", elemento.tiempo_sesion_chat_asesoria);
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            obj.addProperty("usuario_ol",Gestion_usuario.getUsuario_online().nombre_cuenta_usuario);
            obj.addProperty("contrasena_ol",Gestion_usuario.getUsuario_online().contrasena_usuario);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
