package com.example.gestion;

import com.example.modelo.Tag_noticia;
import com.example.modelo.Tipo_accion;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_tipo_accion {
    private static Tipo_accion aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Tipo_accion();
    }
    private ArrayList<Tipo_accion> generar_json(String respuesta)
    {
        ArrayList<Tipo_accion> lista_elementos = new ArrayList<>();
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

    private Tipo_accion agregar_elemento(final JsonObject jsonObject)
    {
        return new Tipo_accion(){{
            try {
                id_tipo_accion = jsonObject.get("id_tipo_accion").getAsInt();
                nombre_tipo_accion = jsonObject.get("nombre_tipo_accion").getAsString();
                numero_acciones_por_primera_infancia_tipo_accion = jsonObject.get("numero_acciones_por_primera_infancia_tipo_accion").getAsInt();
                numero_acciones_por_primera_m_infancia_tipo_accion = jsonObject.get("numero_acciones_por_primera_m_infancia_tipo_accion").getAsInt();
                numero_acciones_por_primera_f_infancia_tipo_accion = jsonObject.get("numero_acciones_por_primera_f_infancia_tipo_accion").getAsInt();
                numero_acciones_por_infancia_tipo_accion = jsonObject.get("numero_acciones_por_infancia_tipo_accion").getAsInt();
                numero_acciones_por_infancia_m_tipo_accion = jsonObject.get("numero_acciones_por_infancia_m_tipo_accion").getAsInt();
                numero_acciones_por_infancia_f_tipo_accion = jsonObject.get("numero_acciones_por_infancia_f_tipo_accion").getAsInt();
                numero_acciones_por_adolecencia_tipo_accion = jsonObject.get("numero_acciones_por_adolecencia_tipo_accion").getAsInt();
                numero_acciones_por_adolecencia_m_tipo_accion = jsonObject.get("numero_acciones_por_adolecencia_m_tipo_accion").getAsInt();
                numero_acciones_por_adolecencia_f_tipo_accion = jsonObject.get("numero_acciones_por_adolecencia_f_tipo_accion").getAsInt();
                numero_acciones_por_juventud_tipo_accion = jsonObject.get("numero_acciones_por_juventud_tipo_accion").getAsInt();
                numero_acciones_por_juventud_m_tipo_accion = jsonObject.get("numero_acciones_por_juventud_m_tipo_accion").getAsInt();
                numero_acciones_por_juventud_f_tipo_accion = jsonObject.get("numero_acciones_por_juventud_f_tipo_accion").getAsInt();
                numero_acciones_por_adultez_tipo_accion = jsonObject.get("numero_acciones_por_adultez_tipo_accion").getAsInt();
                numero_acciones_por_adultez_m_tipo_accion = jsonObject.get("numero_acciones_por_adultez_m_tipo_accion").getAsInt();
                numero_acciones_por_adultez_f_tipo_accion = jsonObject.get("numero_acciones_por_adultez_f_tipo_accion").getAsInt();
                numero_acciones_por_mayor_m_tipo_accion = jsonObject.get("numero_acciones_por_mayor_m_tipo_accion").getAsInt();
                numero_acciones_por_mayor_m_tipo_accion = jsonObject.get("numero_acciones_por_mayor_m_tipo_accion").getAsInt();
                numero_acciones_por_mayor_f_tipo_accion = jsonObject.get("numero_acciones_por_mayor_f_tipo_accion").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Tipo_accion elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_tipo_accion", elemento.id_tipo_accion);
            obj.addProperty("nombre_tipo_accion", elemento.nombre_tipo_accion);
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
