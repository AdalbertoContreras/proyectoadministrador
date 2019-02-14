package com.example.gestion;

import com.example.modelo.Pagina;
import com.example.modelo.Tag_noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_tag_noticia {
    private static Tag_noticia aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Tag_noticia();
    }
    private ArrayList<Tag_noticia> generar_json(String respuesta)
    {
        ArrayList<Tag_noticia> lista_elementos = new ArrayList<>();
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

    private Tag_noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Tag_noticia(){{
            try {
                id_tag_noticia = jsonObject.get("id_tag_noticia").getAsInt();
                nombre_tag_noticia = jsonObject.get("nombre_tag_noticia").getAsString();
                numero_usos = jsonObject.get("numero_usos").getAsInt();
                noticia_id_notiticia = jsonObject.get("noticia_id_notiticia").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Tag_noticia elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_tag_noticia", elemento.id_tag_noticia);
            obj.addProperty("nombre_tag_noticia", elemento.nombre_tag_noticia);
            obj.addProperty("numero_usos", elemento.numero_usos);
            obj.addProperty("noticia_id_notiticia", elemento.noticia_id_notiticia);
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
