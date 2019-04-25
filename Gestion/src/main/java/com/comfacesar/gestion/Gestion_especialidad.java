package com.comfacesar.gestion;

import com.comfacesar.modelo.Especialidad;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_especialidad {
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_ESPECIALIDAD= Propiedades.LLAVE_ESPECIALIDAD;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_ESPECIALIDAD = "id_especialidad";
    private final String NOMBRE_ESPECIALIDAD = "nombre_especialidad";
    private final String NUMERO_ASIGNADOS_ESPECILIDAD = "numero_asignados_especilidad";
    private final String NUMERO_CHAT_ASESORIA_ESPECIALIDAD = "numero_chat_asesoria_especialidad";
    private final String NUMERO_CHAT_ASESORIA_PRIMERA_INFANCIA_ESPECIALIDAD = "numero_chat_asesoria_primera_infancia_especialidad";
    private final String NUMERO_CHAT_ASESORIA_INFANCIA_ESPECIALIDAD = "numero_chat_asesoria_infancia_especialidad";
    private final String NUMERO_CHAT_ASESORIA_ADOLECENCIA_ESPECIALIDAD = "numero_chat_asesoria_adolecencia_especialidad";
    private final String NUMERO_CHAT_ASESORIA_JUVENTUD_ESPECIALIDAD = "numero_chat_asesoria_juventud_especialidad";
    private final String NUMERO_CHAT_ASESORIA_ADULTEZ_ESPECIALIDAD = "numero_chat_asesoria_adultez_especialidad";
    private final String NUMERO_CHAT_ASESORIA_MAYOR_ESPECIALIDAD = "numero_chat_asesoria_mayor_especialidad";
    private final String NUMERO_CHAT_ASESORIA_PRIMERA_INFANCIA_M_ESPECIALIDAD = "numero_chat_asesoria_primera_infancia_m_especialidad";
    private final String NUMERO_CHAT_ASESORIA_INFANCIA_M_ESPECIALIDAD = "numero_chat_asesoria_infancia_m_especialidad";
    private final String NUMERO_CHAT_ASESORIA_ADOLECENCIA_M_ESPECIALIDAD = "numero_chat_asesoria_adolecencia_m_especialidad";
    private final String NUMERO_CHAT_ASESORIA_JUVENTUD_M_ESPECIALIDAD = "numero_chat_asesoria_juventud_m_especialidad";
    private final String NUMERO_CHAT_ASESORIA_ADULTEZ_M_ESPECIALIDAD = "numero_chat_asesoria_adultez_m_especialidad";
    private final String NUMERO_CHAT_ASESORIA_MAYOR_M_ESPECIALIDAD = "numero_chat_asesoria_mayor_m_especialidad";
    private final String NUMERO_CHAT_ASESORIA_PRIMERA_INFANCIA_F_ESPECIALIDAD = "numero_chat_asesoria_primera_infancia_f_especialidad";
    private final String NUMERO_CHAT_ASESORIA_INFANCIA_F_ESPECIALIDAD = "numero_chat_asesoria_infancia_f_especialidad";
    private final String NUMERO_CHAT_ASESORIA_ADOLECENCIA_F_ESPECIALIDAD = "numero_chat_asesoria_adolecencia_f_especialidad";
    private final String NUMERO_CHAT_ASESORIA_JUVENTUD_F_ESPECIALIDAD = "numero_chat_asesoria_juventud_f_especialidad";
    private final String NUMERO_CHAT_ASESORIA_ADULTEZ_F_ESPECIALIDAD = "numero_chat_asesoria_adultez_f_especialidad";
    private final String NUMERO_CHAT_ASESORIA_MAYOR_F_ESPECIALIDAD = "numero_chat_asesoria_mayor_f_especialidad";
    //############################################################################################\\
    //###############################CONSULTA#####################################################\\
    private final String CONSULTAR_ESPECIALIDADES = "consultar_especialidades";
    private Especialidad aux = new Especialidad();
    private String tipo_consulta;
    private JsonObject obj;

    public HashMap<String, String> consultar_especialidades()
    {
        tipo_consulta = CONSULTAR_ESPECIALIDADES;
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
                id_especialidad = jsonObject.get(ID_ESPECIALIDAD).getAsInt();
                nombre_especialidad = jsonObject.get(NOMBRE_ESPECIALIDAD).getAsString();
                if(jsonObject.has(NUMERO_ASIGNADOS_ESPECILIDAD))
                {
                    numero_asignados_especilidad = jsonObject.get(NUMERO_ASIGNADOS_ESPECILIDAD).getAsInt();
                    numero_chat_asesoria_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_primera_infancia_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_PRIMERA_INFANCIA_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_infancia_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_INFANCIA_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_adolecencia_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_ADOLECENCIA_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_juventud_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_JUVENTUD_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_adultez_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_ADULTEZ_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_mayor_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_MAYOR_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_primera_infancia_m_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_PRIMERA_INFANCIA_M_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_infancia_m_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_INFANCIA_M_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_adolecencia_m_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_ADOLECENCIA_M_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_juventud_m_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_JUVENTUD_M_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_adultez_m_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_ADULTEZ_M_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_mayor_m_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_MAYOR_M_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_primera_infancia_f_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_PRIMERA_INFANCIA_F_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_infancia_f_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_INFANCIA_F_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_adolecencia_f_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_ADOLECENCIA_F_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_juventud_f_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_JUVENTUD_F_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_adultez_f_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_ADULTEZ_F_ESPECIALIDAD).getAsInt();
                    numero_chat_asesoria_mayor_f_especialidad = jsonObject.get(NUMERO_CHAT_ASESORIA_MAYOR_F_ESPECIALIDAD).getAsInt();
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Especialidad elemento)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(ID_ESPECIALIDAD, elemento.id_especialidad);
            obj.addProperty(NOMBRE_ESPECIALIDAD, elemento.nombre_especialidad);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_ESPECIALIDAD);
            adjuntarAcceso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    private void adjuntarAcceso()
    {
        if(Gestion_administrador.getAdministrador_actual() != null)
        {
            obj.addProperty(TOKEN,Gestion_administrador.getAdministrador_actual().token);
        }
    }
}