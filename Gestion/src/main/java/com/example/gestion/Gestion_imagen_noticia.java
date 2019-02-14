package com.example.gestion;

import com.example.modelo.Chat_asesoria;
import com.example.modelo.Imagen_noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_imagen_noticia {
    private static Imagen_noticia aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Imagen_noticia();
    }

    public HashMap<String, String> registrar_imagen_con_archivo(Imagen_noticia imagen_noticia)
    {
        tipo_consulta = "registrar_imagen_con_archivo";
        return construir_parametros(imagen_noticia);
    }

    public ArrayList<Imagen_noticia> generar_json(String respuesta)
    {
        ArrayList<Imagen_noticia> lista_elementos = new ArrayList<>();
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

    private Imagen_noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Imagen_noticia(){{
            try {
                id_imagen_noticia = jsonObject.get("id_imagen_noticia").getAsInt();
                url_imagen_noticia = jsonObject.get("url_imagen_noticia").getAsString();
                fecha_registro_imagen_noticia = jsonObject.get("fecha_registro_imagen_noticia").getAsString();
                hora_registro_imagen_noticia = jsonObject.get("hora_registro_imagen_noticia").getAsString();
                noticia_imagen_noticia = jsonObject.get("noticia_imagen_noticia").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Imagen_noticia elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_imagen_noticia", elemento.id_imagen_noticia);
            obj.addProperty("url_imagen_noticia", elemento.url_imagen_noticia);
            obj.addProperty("fecha_registro_imagen_noticia", elemento.fecha_registro_imagen_noticia);
            obj.addProperty("hora_registro_imagen_noticia", elemento.hora_registro_imagen_noticia);
            obj.addProperty("noticia_imagen_noticia", elemento.noticia_imagen_noticia);
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
