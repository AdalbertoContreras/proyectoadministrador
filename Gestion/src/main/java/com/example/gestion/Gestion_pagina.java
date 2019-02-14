package com.example.gestion;

import com.example.modelo.Noticia;
import com.example.modelo.Pagina;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_pagina {
    private static Pagina aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Pagina();
    }
    private ArrayList<Pagina> generar_json(String respuesta)
    {
        ArrayList<Pagina> lista_elementos = new ArrayList<>();
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

    private Pagina agregar_elemento(final JsonObject jsonObject)
    {
        return new Pagina(){{
            try {
                id_pagina = jsonObject.get("id_pagina").getAsInt();
                url_pagina = jsonObject.get("url_pagina").getAsString();
                numero_visitas_pagina = jsonObject.get("numero_visitas_pagina").getAsInt();
                numero_visitas_por_primera_infancia_pagina = jsonObject.get("numero_visitas_por_primera_infancia_pagina").getAsInt();
                numero_visitas_por_primera_m_infancia_pagina = jsonObject.get("numero_visitas_por_primera_m_infancia_pagina").getAsInt();
                numero_visitas_por_primera_f_infancia_pagina = jsonObject.get("numero_visitas_por_primera_f_infancia_pagina").getAsInt();
                numero_visitas_por_infancia_pagina = jsonObject.get("numero_visitas_por_infancia_pagina").getAsInt();
                numero_visitas_por_infancia_m_pagina = jsonObject.get("numero_visitas_por_infancia_m_pagina").getAsInt();
                numero_visitas_por_infancia_f_pagina = jsonObject.get("numero_visitas_por_infancia_f_pagina").getAsInt();
                numero_visitas_por_adolecencia_pagina = jsonObject.get("numero_visitas_por_adolecencia_pagina").getAsInt();
                numero_visitas_por_adolecencia_m_pagina = jsonObject.get("numero_visitas_por_adolecencia_m_pagina").getAsInt();
                numero_visitas_por_adolecencia_f_pagina = jsonObject.get("numero_visitas_por_adolecencia_f_pagina").getAsInt();
                numero_visitas_por_juventud_pagina = jsonObject.get("numero_visitas_por_juventud_pagina").getAsInt();
                numero_visitas_por_juventud_m_pagina = jsonObject.get("numero_visitas_por_juventud_m_pagina").getAsInt();
                numero_visitas_por_juventud_f_pagina = jsonObject.get("numero_visitas_por_juventud_f_pagina").getAsInt();
                numero_visitas_por_adultez_pagina = jsonObject.get("numero_visitas_por_adultez_pagina").getAsInt();
                numero_visitas_por_adultez_m_pagina = jsonObject.get("numero_visitas_por_adultez_m_pagina").getAsInt();
                numero_visitas_por_adultez_f_pagina = jsonObject.get("numero_visitas_por_adultez_f_pagina").getAsInt();
                numero_visitas_por_mayor_pagina = jsonObject.get("numero_visitas_por_mayor_pagina").getAsInt();
                numero_visitas_por_mayor_m_pagina = jsonObject.get("numero_visitas_por_mayor_m_pagina").getAsInt();
                numero_visitas_por_mayor_f_pagina = jsonObject.get("numero_visitas_por_mayor_f_pagina").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Pagina elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_pagina", elemento.id_pagina);
            obj.addProperty("url_pagina", elemento.url_pagina);
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
