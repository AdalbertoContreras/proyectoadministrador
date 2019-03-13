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
    private static Chat_asesoria aux = new Chat_asesoria();
    private static String llave_ws = "chat_asesoria";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;
    private static String ID_CHAT_ASESORIA = "A";
    private static String FECHA_INICIO_ASESORIA = "B";
    private static String HORA_INICIO_ASESORIA = "C";
    private static String FECHA_CIERRE_ASESORIA = "D";
    private static String HORA_CIERRE_CHAT_ASESORIA = "E";
    private static String ADMINISTRADOR_CHAT_ASESORIA = "F";
    private static String USUARIO_CHAT_ASESORIA = "G";
    private static String ESTADO_CERRADO = "H";
    private static String TIEMPO_SESION_CHAT_ASESORIA = "I";
    private static String ESPECIALIZACION_CHAT_ASESORIA = "J";
    private static String ULTIMA_FECHA_CHAT_ASESORIA = "K";
    private static String ULTIMA_HORA_CHAT_ASESORIA = "L";
    private static String ULTIMO_MENSAJE = "M";
    private static String NOMBRE_ADMINISTRADOR_OL = "NA";
    private static String CONTRASENA_ADMINISTRADOR = "CA";
    private static String TIPO_CONSULTA = "TC";

    public HashMap<String, String> chat_asesoria_por_id(int id_chat)
    {
        aux.id_chat_asesoria = id_chat;
        tipo_consulta = "chat_asesoria_por_id";
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_por_administrador(int administrador)
    {
        aux.administrador_chat_asesoria = administrador;
        tipo_consulta = "consultar_por_administrador";
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_chat_asesoria_por_usuario_administrador_y_especialidad_atributos(int administrador, int usuario, int especialidad)
    {
        aux.administrador_chat_asesoria = administrador;
        aux.usuario_chat_asesoria = usuario;
        aux.especializacion_chat_asesoria = especialidad;
        tipo_consulta = "consultar_chat_asesoria_por_usuario_administrador_y_especialidad_atributos";
        return construir_parametros(aux);
    }

    public ArrayList<Chat_asesoria> generar_json(String respuesta)
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
                id_chat_asesoria = jsonObject.get(ID_CHAT_ASESORIA).getAsInt();
                fecha_inicio_asesoria = jsonObject.get(FECHA_INICIO_ASESORIA).getAsString();
                hora_inicio_asesoria = jsonObject.get(HORA_INICIO_ASESORIA).getAsString();
                if(!jsonObject.get(FECHA_CIERRE_ASESORIA).isJsonNull())
                {
                    fecha_cierre_asesoria = jsonObject.get(FECHA_CIERRE_ASESORIA).getAsString();
                }
                if(!jsonObject.get(HORA_CIERRE_CHAT_ASESORIA).isJsonNull())
                {
                    hora_cierra_chat_asesoria = jsonObject.get(HORA_CIERRE_CHAT_ASESORIA).getAsString();
                }
                administrador_chat_asesoria = jsonObject.get(ADMINISTRADOR_CHAT_ASESORIA).getAsInt();
                usuario_chat_asesoria = jsonObject.get(USUARIO_CHAT_ASESORIA).getAsInt();
                estado_cerrado = jsonObject.get(ESTADO_CERRADO).getAsInt();
                tiempo_sesion_chat_asesoria = jsonObject.get(TIEMPO_SESION_CHAT_ASESORIA).getAsString();
                especializacion_chat_asesoria = jsonObject.get(ESPECIALIZACION_CHAT_ASESORIA).getAsInt();
                if(!jsonObject.get(ULTIMO_MENSAJE).isJsonNull())
                {
                    ultimo_mensaje = jsonObject.get(ULTIMO_MENSAJE).getAsString();
                }
                if(!jsonObject.get(ULTIMA_FECHA_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_chat_asesoria = jsonObject.get(ULTIMA_FECHA_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_HORA_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_chat_asesoria = jsonObject.get("ultima_hora_chat_asesoria").getAsString();
                }
                if(!jsonObject.get("usuario").isJsonNull())
                {
                    usuario = jsonObject.get("usuario").getAsString();
                }
                if(!jsonObject.get("administrador").isJsonNull())
                {
                    administrador = jsonObject.get("administrador").getAsString();
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Chat_asesoria elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(ID_CHAT_ASESORIA, elemento.id_chat_asesoria);
            obj.addProperty(FECHA_INICIO_ASESORIA, elemento.fecha_inicio_asesoria);
            obj.addProperty(HORA_INICIO_ASESORIA, elemento.hora_inicio_asesoria);
            obj.addProperty(FECHA_CIERRE_ASESORIA, elemento.fecha_cierre_asesoria);
            obj.addProperty(HORA_CIERRE_CHAT_ASESORIA, elemento.hora_cierra_chat_asesoria);
            obj.addProperty(ADMINISTRADOR_CHAT_ASESORIA, elemento.administrador_chat_asesoria);
            obj.addProperty(USUARIO_CHAT_ASESORIA, elemento.usuario_chat_asesoria);
            obj.addProperty(ESTADO_CERRADO, elemento.estado_cerrado);
            obj.addProperty(TIEMPO_SESION_CHAT_ASESORIA, elemento.tiempo_sesion_chat_asesoria);
            obj.addProperty(ESPECIALIZACION_CHAT_ASESORIA, elemento.especializacion_chat_asesoria);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            if(Gestion_administrador.getAdministrador_actual() != null)
            {
                obj.addProperty(NOMBRE_ADMINISTRADOR_OL,Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
                obj.addProperty(CONTRASENA_ADMINISTRADOR,Gestion_administrador.getAdministrador_actual().contrasena_administrador);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
