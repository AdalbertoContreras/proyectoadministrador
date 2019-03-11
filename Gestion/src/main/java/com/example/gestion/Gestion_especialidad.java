package com.example.gestion;

import com.example.modelo.Especialidad;
import com.example.modelo.Imagen_noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_especialidad {
    private static Especialidad aux = new Especialidad();
    private static String llave_ws = "especialidad";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    public HashMap<String, String> consultar_especialidades()
    {
        tipo_consulta = "consultar_especialidades";
        return construir_parametros(aux);
    }

    public ArrayList<Especialidad> generar_json(String respuesta)
    {

        ArrayList<Especialidad> lista_elementos = new ArrayList<>();
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

    private Especialidad agregar_elemento(final JsonObject jsonObject)
    {
        return new Especialidad(){{
            try {
                id_especialidad = jsonObject.get("id_especialidad").getAsInt();
                nombre_especialidad = jsonObject.get("nombre_especialidad").getAsString();
                if(jsonObject.has("numero_asignados_especilidad"))
                {
                    numero_asignados_especilidad = jsonObject.get("numero_asignados_especilidad").getAsInt();
                    numero_chat_asesoria_especialidad = jsonObject.get("numero_chat_asesoria_especialidad").getAsInt();
                    numero_chat_asesoria_primera_infancia_especialidad = jsonObject.get("numero_chat_asesoria_primera_infancia_especialidad").getAsInt();
                    numero_chat_asesoria_infancia_especialidad = jsonObject.get("numero_chat_asesoria_infancia_especialidad").getAsInt();
                    numero_chat_asesoria_adolecencia_especialidad = jsonObject.get("numero_chat_asesoria_adolecencia_especialidad").getAsInt();
                    numero_chat_asesoria_juventud_especialidad = jsonObject.get("numero_chat_asesoria_juventud_especialidad").getAsInt();
                    numero_chat_asesoria_adultez_especialidad = jsonObject.get("numero_chat_asesoria_adultez_especialidad").getAsInt();
                    numero_chat_asesoria_mayor_especialidad = jsonObject.get("numero_chat_asesoria_mayor_especialidad").getAsInt();
                    numero_chat_asesoria_primera_infancia_m_especialidad = jsonObject.get("numero_chat_asesoria_primera_infancia_m_especialidad").getAsInt();
                    numero_chat_asesoria_infancia_m_especialidad = jsonObject.get("numero_chat_asesoria_infancia_m_especialidad").getAsInt();
                    numero_chat_asesoria_adolecencia_m_especialidad = jsonObject.get("numero_chat_asesoria_adolecencia_m_especialidad").getAsInt();
                    numero_chat_asesoria_juventud_m_especialidad = jsonObject.get("numero_chat_asesoria_juventud_m_especialidad").getAsInt();
                    numero_chat_asesoria_adultez_m_especialidad = jsonObject.get("numero_chat_asesoria_adultez_m_especialidad").getAsInt();
                    numero_chat_asesoria_mayor_m_especialidad = jsonObject.get("numero_chat_asesoria_mayor_m_especialidad").getAsInt();
                    numero_chat_asesoria_primera_infancia_f_especialidad = jsonObject.get("numero_chat_asesoria_primera_infancia_f_especialidad").getAsInt();
                    numero_chat_asesoria_infancia_f_especialidad = jsonObject.get("numero_chat_asesoria_infancia_f_especialidad").getAsInt();
                    numero_chat_asesoria_adolecencia_f_especialidad = jsonObject.get("numero_chat_asesoria_adolecencia_f_especialidad").getAsInt();
                    numero_chat_asesoria_juventud_f_especialidad = jsonObject.get("numero_chat_asesoria_juventud_f_especialidad").getAsInt();
                    numero_chat_asesoria_adultez_f_especialidad = jsonObject.get("numero_chat_asesoria_adultez_f_especialidad").getAsInt();
                    numero_chat_asesoria_mayor_f_especialidad = jsonObject.get("numero_chat_asesoria_mayor_f_especialidad").getAsInt();
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Especialidad elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_especialidad", elemento.id_especialidad);
            obj.addProperty("nombre_especialidad", elemento.nombre_especialidad);
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            if(Gestion_administrador.getAdministrador_actual() != null)
            {
                obj.addProperty("nombre_admnistrador_ol",Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
                obj.addProperty("contraseña_administrador_ol",Gestion_administrador.getAdministrador_actual().contrasena_administrador);
            }
            else
            {
                obj.addProperty("nombre_admnistrador_ol","");
                obj.addProperty("contraseña_administrador_ol","");
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
