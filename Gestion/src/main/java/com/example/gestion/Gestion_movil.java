package com.example.gestion;

import com.example.modelo.Mensaje_chat_asesoria;
import com.example.modelo.Movil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_movil {
    private static Movil aux;
    private static String llave_ws = "movil";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Movil();
    }

    public HashMap<String, String> registrar(Movil movil)
    {
     tipo_consulta = "insert";
     return construir_parametros(movil);
    }

    private ArrayList<Movil> generar_json(String respuesta)
    {
        ArrayList<Movil> lista_elementos = new ArrayList<>();
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

    private Movil agregar_elemento(final JsonObject jsonObject)
    {
        return new Movil(){{
            try {
                imei = jsonObject.get("imei").getAsString();
                modelo_movil = jsonObject.get("modelo_movil").getAsString();
                fecha_registro_movil = jsonObject.get("fecha_registro_movil").getAsString();
                hora_registro_movil = jsonObject.get("hora_registro_movil").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Movil elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("imei", elemento.imei);
            obj.addProperty("modelo_movil", elemento.modelo_movil);
            obj.addProperty("fecha_registro_movil", elemento.fecha_registro_movil);
            obj.addProperty("hora_registro_movil", elemento.hora_registro_movil);
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
