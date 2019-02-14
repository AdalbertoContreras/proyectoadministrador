package com.example.gestion;

import com.example.modelo.Asunto;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_asunto {
    private static Asunto aux = new Asunto();
    private static String llave_ws = "asunto";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Asunto();
    }

    public HashMap<String, String> consultar_asuntos()
    {
        tipo_consulta = "consultar_asuntos";
        return construir_parametros(aux);
    }

    public ArrayList<Asunto> generar_json(String respuesta)
    {
        ArrayList<Asunto> lista_elementos = new ArrayList<>();
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

    private Asunto agregar_elemento(final JsonObject jsonObject)
    {
        return new Asunto(){{
            try {
                id_asunto = jsonObject.get("id_asunto").getAsInt();
                nombre_asunto = jsonObject.get("nombre_asunto").getAsString();
                numero_alertas_tempranas_asunto = jsonObject.get("numero_alertas_tempranas_asunto").getAsInt();
                numero_alertas_tempranas_atendidas_asunto = jsonObject.get("numero_alertas_tempranas_atendidas_asunto").getAsInt();
                numero_alertas_tempranas_no_atendidas_asunto = jsonObject.get("numero_alertas_tempranas_no_atendidas_asunto").getAsInt();
                numero_alertas_tempranas_por_primera_infancia_asunto = jsonObject.get("numero_alertas_tempranas_por_primera_infancia_asunto").getAsInt();
                numero_alertas_tempranas_por_primera_m_infancia_asunto = jsonObject.get("numero_alertas_tempranas_por_primera_m_infancia_asunto").getAsInt();
                numero_alertas_tempranas_por_primera_f_infancia_asunto = jsonObject.get("numero_alertas_tempranas_por_primera_f_infancia_asunto").getAsInt();
                numero_alertas_tempranas_por_infancia_asunto = jsonObject.get("numero_alertas_tempranas_por_infancia_asunto").getAsInt();
                numero_alertas_tempranas_por_infancia_m_asunto = jsonObject.get("numero_alertas_tempranas_por_infancia_m_asunto").getAsInt();
                numero_alertas_tempranas_por_infancia_f_asunto = jsonObject.get("numero_alertas_tempranas_por_infancia_f_asunto").getAsInt();
                numero_alertas_tempranas_por_adolecencia_asunto = jsonObject.get("numero_alertas_tempranas_por_adolecencia_asunto").getAsInt();
                numero_alertas_tempranas_por_adolecencia_m_asunto = jsonObject.get("numero_alertas_tempranas_por_adolecencia_m_asunto").getAsInt();
                numero_alertas_tempranas_por_adolecencia_f_asunto = jsonObject.get("numero_alertas_tempranas_por_adolecencia_f_asunto").getAsInt();
                numero_alertas_tempranas_por_juventud_asunto = jsonObject.get("numero_alertas_tempranas_por_juventud_asunto").getAsInt();
                numero_alertas_tempranas_por_juventud_m_asunto = jsonObject.get("numero_alertas_tempranas_por_juventud_m_asunto").getAsInt();
                numero_alertas_tempranas_por_juventud_f_asunto = jsonObject.get("numero_alertas_tempranas_por_juventud_f_asunto").getAsInt();
                numero_alertas_tempranas_por_mayor_asunto = jsonObject.get("numero_alertas_tempranas_por_mayor_asunto").getAsInt();
                numero_alertas_tempranas_por_mayor_m_asunto = jsonObject.get("numero_alertas_tempranas_por_mayor_m_asunto").getAsInt();
                numero_alertas_tempranas_por_mayor_f_asunto = jsonObject.get("numero_alertas_tempranas_por_mayor_f_asunto").getAsInt();
                numero_alertas_tempranas_por_adultez_asunto = jsonObject.get("numero_alertas_tempranas_por_adultez_asunto").getAsInt();
                numero_alertas_tempranas_por_adultez_m_asunto = jsonObject.get("numero_alertas_tempranas_por_adultez_m_asunto").getAsInt();
                numero_alertas_tempranas_por_adultez_f_asunto = jsonObject.get("numero_alertas_tempranas_por_adultez_f_asunto").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Asunto elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_asunto", elemento.id_asunto);
            obj.addProperty("nombre_asunto", elemento.nombre_asunto);
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
