package com.example.gestion;

import com.example.modelo.Imagen_noticia;
import com.example.modelo.Lugar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_lugar {
    private static Lugar aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Lugar();
    }
    private ArrayList<Lugar> generar_json(String respuesta)
    {
        ArrayList<Lugar> lista_elementos = new ArrayList<>();
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

    private Lugar agregar_elemento(final JsonObject jsonObject)
    {
        return new Lugar(){{
            try {
                id_lugar = jsonObject.get("id_lugar").getAsInt();
                nombre_lugar = jsonObject.get("nombre_lugar").getAsString();
                longitud_lugar = jsonObject.get("longitud_lugar").getAsFloat();
                latitud_lugar = jsonObject.get("latitud_lugar").getAsFloat();
                direccion_lugar = jsonObject.get("direccion_lugar").getAsString();
                numero_visitas_lugar = jsonObject.get("numero_visitas_lugar").getAsInt();
                numero_visitas_por_primera_infancia_lugar = jsonObject.get("numero_visitas_por_primera_infancia_lugar").getAsInt();
                numero_visitas_por_primera_m_infancia_lugar = jsonObject.get("numero_visitas_por_primera_m_infancia_lugar").getAsInt();
                numero_visitas_por_primera_f_infancia_lugar = jsonObject.get("numero_visitas_por_primera_f_infancia_lugar").getAsInt();
                numero_visitas_por_infancia_lugar = jsonObject.get("numero_visitas_por_infancia_lugar").getAsInt();
                numero_visitas_por_infancia_m_lugar = jsonObject.get("numero_visitas_por_infancia_m_lugar").getAsInt();
                numero_visitas_por_infancia_f_lugar = jsonObject.get("numero_visitas_por_infancia_f_lugar").getAsInt();
                numero_visitas_por_adolecencia_lugar = jsonObject.get("numero_visitas_por_adolecencia_lugar").getAsInt();
                numero_visitas_por_adolecencia_m_lugar = jsonObject.get("numero_visitas_por_adolecencia_m_lugar").getAsInt();
                numero_visitas_por_adolecencia_f_lugar = jsonObject.get("numero_visitas_por_adolecencia_f_lugar").getAsInt();
                numero_visitas_por_juventud_lugar = jsonObject.get("numero_visitas_por_juventud_lugar").getAsInt();
                numero_visitas_por_juventud_m_lugar = jsonObject.get("numero_visitas_por_juventud_m_lugar").getAsInt();
                numero_visitas_por_juventud_f_lugar = jsonObject.get("numero_visitas_por_juventud_f_lugar").getAsInt();
                numero_visitas_por_adultez_lugar = jsonObject.get("numero_visitas_por_adultez_lugar").getAsInt();
                numero_visitas_por_adultez_m_lugar = jsonObject.get("numero_visitas_por_adultez_m_lugar").getAsInt();
                numero_visitas_por_adultez_f_lugar = jsonObject.get("numero_visitas_por_adultez_f_lugar").getAsInt();
                numero_visitas_por_mayor_lugar = jsonObject.get("numero_visitas_por_mayor_lugar").getAsInt();
                numero_visitas_por_mayor_m_lugar = jsonObject.get("numero_visitas_por_mayor_m_lugar").getAsInt();
                numero_visitas_por_mayor_f_lugar = jsonObject.get("numero_visitas_por_mayor_f_lugar").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Lugar elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_lugar", elemento.id_lugar);
            obj.addProperty("nombre_lugar", elemento.nombre_lugar);
            obj.addProperty("longitud_lugar", elemento.longitud_lugar);
            obj.addProperty("latitud_lugar", elemento.latitud_lugar);
            obj.addProperty("direccion_lugar", elemento.direccion_lugar);
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
