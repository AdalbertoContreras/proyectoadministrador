package com.example.gestion;

import com.example.modelo.Tipo_creador_mensaje;
import com.example.modelo.Tipo_registro;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_tipo_registro {
    private static Tipo_registro aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Tipo_registro();
    }
    private ArrayList<Tipo_registro> generar_json(String respuesta)
    {
        ArrayList<Tipo_registro> lista_elementos = new ArrayList<>();
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

    private Tipo_registro agregar_elemento(final JsonObject jsonObject)
    {
        return new Tipo_registro(){{
            try {
                id_tipo_registro = jsonObject.get("id_tipo_registro").getAsInt();
                nombre_tipo_registro = jsonObject.get("nombre_tipo_registro").getAsString();
                fecha_registro_tipo_registro = jsonObject.get("fecha_registro_tipo_registro").getAsString();
                hora_registro_tipo_registro = jsonObject.get("hora_registro_tipo_registro").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Tipo_registro elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_tipo_registro", elemento.id_tipo_registro);
            obj.addProperty("nombre_tipo_registro", elemento.nombre_tipo_registro);
            obj.addProperty("fecha_registro_tipo_registro", elemento.fecha_registro_tipo_registro);
            obj.addProperty("fecha_registro_tipo_registro", elemento.fecha_registro_tipo_registro);
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
