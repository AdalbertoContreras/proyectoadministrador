package com.example.gestion;

import com.example.modelo.Tipo_creacion_noticia;
import com.example.modelo.Tipo_creador_mensaje;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_tipo_creacion_noticia {
    private static Tipo_creacion_noticia aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Tipo_creacion_noticia();
    }
    private ArrayList<Tipo_creacion_noticia> generar_json(String respuesta)
    {
        ArrayList<Tipo_creacion_noticia> lista_elementos = new ArrayList<>();
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

    private Tipo_creacion_noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Tipo_creacion_noticia(){{
            try {
                id_tipo_creacion_noticia = jsonObject.get("id_tipo_creador").getAsInt();
                nombre_tipo_creacio_noticia = jsonObject.get("nombre_tipo_creador").getAsString();
                num_noticias = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_noticias_visitadas_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_noticias_no_visitadas_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_primera_infancia_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_primera_m_infancia_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_primera_f_infancia_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_infancia_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_infancia_m_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_infancia_f_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_adolecencia_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_adolecencia_m_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_adolecencia_f_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_juventud_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_juventud_m_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_juventud_f_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_adultez_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_adultez_m_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_adultez_f_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_mayor_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_mayor_m_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
                numero_visitas_por_mayor_f_tipo_creacion_noticia = jsonObject.get("nombre_tipo_creador").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Tipo_creacion_noticia elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_tipo_creador", elemento.id_tipo_creacion_noticia);
            obj.addProperty("nombre_tipo_creador", elemento.nombre_tipo_creacio_noticia);
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
