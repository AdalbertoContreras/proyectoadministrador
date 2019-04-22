package com.comfacesar.gestion;

import com.comfacesar.modelo.Mensaje_chat_asesoria;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_mensaje_chat_asesoria {
    private JsonObject obj;
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_MENSAJE_CHAT_ASESORIA= Propiedades.LLAVE_MENSAJE_CHAT_ASESORIA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    private static final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    //############################################################################################\\
    //###############################PROPIEDADES MENSAJE CHAT ASESORIA#############################\\
    private static final String ID_MENSAJE_CHAT_ASESORIA = "A";
    private static final String FECHA_ENVIO_MENSAJE_CHAT_ASESORIA = "B";
    private static final String HORA_ENVIO_MENSAJE_ASESORIA = "C";
    private static final String CONTENIDO_MENSAJE_CHAT_ASESORIA = "D";
    private static final String CHAT_MENSAJE_CHAT_ASESORIA = "E";
    private static final String ID_CREADOR_MENSAJE_CHAT_ASESORIA = "F";
    private static final String TIPO_CREADOR_MENSAJE_CHAT_ASESORIA = "G";
    //############################################################################################\\
    //###############################CONSULTA#####################################################\\
    private final String REGISTRAR_MENSAJE_CHAT_ASESORIA = "registrar_mensaje_chat_asesoria";
    private final String MENSAJES_ASESORIA_POR_ASESORIA = "mensajes_asesoria_por_asesoria";
    private final String MENSAJE_CHAT_ASESORIA_POR_CHAT_MAYOR = "mensaje_chat_asesoria_por_chat_mayor";

    private void adjuntar_aseso()
    {
        if(Gestion_administrador.getAdministrador_actual() != null)
        {
            obj.addProperty(TOKEN,Gestion_administrador.getAdministrador_actual().token);
        }
        obj.addProperty(LLAVE_WS, LLAVE_MENSAJE_CHAT_ASESORIA);
    }

    public HashMap<String, String> registrar_mensaje_chat_asesoria(Mensaje_chat_asesoria mensaje_chat_asesoria)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(CONTENIDO_MENSAJE_CHAT_ASESORIA, mensaje_chat_asesoria.contenido_mensaje_chat_asesoria);
            obj.addProperty(CHAT_MENSAJE_CHAT_ASESORIA, mensaje_chat_asesoria.chat_mensaje_chat_asesoria);
            obj.addProperty(ID_CREADOR_MENSAJE_CHAT_ASESORIA, mensaje_chat_asesoria.id_creador_mensaje_chat_asesoria);
            obj.addProperty(TIPO_CREADOR_MENSAJE_CHAT_ASESORIA, mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria);
            obj.addProperty(TIPO_CONSULTA,REGISTRAR_MENSAJE_CHAT_ASESORIA);
            adjuntar_aseso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    public HashMap<String, String> mensajes_asesoria_por_asesoria(int asesoria)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(CHAT_MENSAJE_CHAT_ASESORIA, asesoria);
            obj.addProperty(TIPO_CONSULTA,MENSAJES_ASESORIA_POR_ASESORIA);
            adjuntar_aseso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    public HashMap<String, String> mensaje_chat_asesoria_por_chat_mayor(String fecha, String hora,int id_chat)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(FECHA_ENVIO_MENSAJE_CHAT_ASESORIA, fecha);
            obj.addProperty(HORA_ENVIO_MENSAJE_ASESORIA, hora);
            obj.addProperty(CHAT_MENSAJE_CHAT_ASESORIA, id_chat);
            obj.addProperty(TIPO_CONSULTA,MENSAJE_CHAT_ASESORIA_POR_CHAT_MAYOR);
            adjuntar_aseso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
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
                id_mensaje_chat_asesoria = jsonObject.get(ID_MENSAJE_CHAT_ASESORIA).getAsInt();
                fecha_envio_mensaje_chat_asesoria = jsonObject.get(FECHA_ENVIO_MENSAJE_CHAT_ASESORIA).getAsString();
                hora_envio_mensaje_asesoria = jsonObject.get(HORA_ENVIO_MENSAJE_ASESORIA).getAsString();
                contenido_mensaje_chat_asesoria = jsonObject.get(CONTENIDO_MENSAJE_CHAT_ASESORIA).getAsString();
                chat_mensaje_chat_asesoria = jsonObject.get(CHAT_MENSAJE_CHAT_ASESORIA).getAsInt();
                id_creador_mensaje_chat_asesoria = jsonObject.get(ID_CREADOR_MENSAJE_CHAT_ASESORIA).getAsInt();
                tipo_creador_mensaje_chat_asesoria = jsonObject.get(TIPO_CREADOR_MENSAJE_CHAT_ASESORIA).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }
}