package com.example.gestion;

import com.example.modelo.Chat_asesoria;
import com.example.modelo.Lugar;
import com.example.modelo.Mensaje_chat_asesoria;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_mensaje_chat_asesoria {
    private static final String llave_ws = "mensaje_chat_asesoria";
    private JsonObject obj;


    private void adjuntar_aseso()
    {
        if(Gestion_administrador.getAdministrador_actual() != null)
        {
            obj.addProperty("nombre_administrador_ol",Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
            obj.addProperty("contrasena_administrador_ol",Gestion_administrador.getAdministrador_actual().contrasena_administrador);
        }
        else
        {
            obj.addProperty("nombre_administrador_ol","");
            obj.addProperty("contrasena_administrador_ol","");
        }
        obj.addProperty("llave_ws",llave_ws);
    }

    public HashMap<String, String> registrar_mensaje_chat_asesoria(Mensaje_chat_asesoria mensaje_chat_asesoria)
    {
        obj = new JsonObject();
        try {
            obj.addProperty("contenido_mensaje_chat_asesoria", mensaje_chat_asesoria.contenido_mensaje_chat_asesoria);
            obj.addProperty("chat_mensaje_chat_asesoria", mensaje_chat_asesoria.chat_mensaje_chat_asesoria);
            obj.addProperty("id_creador_mensaje_chat_asesoria", mensaje_chat_asesoria.id_creador_mensaje_chat_asesoria);
            obj.addProperty("tipo_creador_mensaje_chat_asesoria", mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria);
            obj.addProperty("tipo_consulta","registrar_mensaje_chat_asesoria");
            adjuntar_aseso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }

    public HashMap<String, String> mensajes_asesoria_por_asesoria(int asesoria)
    {
        obj = new JsonObject();
        try {
            obj.addProperty("chat_mensaje_chat_asesoria", asesoria);
            obj.addProperty("tipo_consulta","mensajes_asesoria_por_asesoria");
            adjuntar_aseso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }

    public HashMap<String, String> mensaje_chat_asesoria_por_chat_mayor(int id,int id_chat)
    {
        obj = new JsonObject();
        try {
            obj.addProperty("id_mensaje_chat_asesoria", id);
            obj.addProperty("chat_mensaje_chat_asesoria", id_chat);
            obj.addProperty("tipo_consulta","mensaje_chat_asesoria_por_chat_mayor");
            adjuntar_aseso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }

    public ArrayList<Mensaje_chat_asesoria> generar_json(String respuesta)
    {
        ArrayList<Mensaje_chat_asesoria> lista_elementos = new ArrayList<>();
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

    private Mensaje_chat_asesoria agregar_elemento(final JsonObject jsonObject)
    {
        return new Mensaje_chat_asesoria(){{
            try {
                id_mensaje_chat_asesoria = jsonObject.get("id_mensaje_chat_asesoria").getAsInt();
                fecha_envio_mensaje_chat_asesoria = jsonObject.get("fecha_envio_mensaje_chat_asesoria").getAsString();
                hora_envio_mensaje_asesoria = jsonObject.get("hora_envio_mensaje_asesoria").getAsString();
                contenido_mensaje_chat_asesoria = jsonObject.get("contenido_mensaje_chat_asesoria").getAsString();
                chat_mensaje_chat_asesoria = jsonObject.get("chat_mensaje_chat_asesoria").getAsInt();
                id_creador_mensaje_chat_asesoria = jsonObject.get("id_creador_mensaje_chat_asesoria").getAsInt();
                tipo_creador_mensaje_chat_asesoria = jsonObject.get("tipo_creador_mensaje_chat_asesoria").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }
}
