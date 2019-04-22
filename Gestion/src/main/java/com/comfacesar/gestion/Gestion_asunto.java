package com.comfacesar.gestion;

import com.comfacesar.modelo.Administrador;
import com.comfacesar.modelo.Asunto;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_asunto {
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\

    //############################################################################################\\
    //###############################PROPIEDADES DE ASUNTO########################################\\
    private final String ID_ASUNTO = "id_asunto";
    private final String NOMBRE_ASUNTO = "nombre_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_ASUNTO = "numero_alertas_tempranas_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_ATENDIDAS_ASUNTO = "numero_alertas_tempranas_atendidas_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_NO_ATENDIDAS_ASUNTO = "numero_alertas_tempranas_no_atendidas_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_PRIMERA_INFANCIA_ASUNTO = "numero_alertas_tempranas_por_primera_infancia_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_PRIMERA_M_INFANCIA_ASUNTO = "numero_alertas_tempranas_por_primera_m_infancia_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_PRIMERA_F_INFANCIA_ASUNTO = "numero_alertas_tempranas_por_primera_f_infancia_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_INFANCIA_ASUNTO = "numero_alertas_tempranas_por_infancia_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_INFANCIA_M_ASUNTO = "numero_alertas_tempranas_por_infancia_m_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_INFANCIA_F_ASUNTO = "numero_alertas_tempranas_por_infancia_f_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_ADOLECENCIA_ASUNTO = "numero_alertas_tempranas_por_adolecencia_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_ADOLECENCIA_M_ASUNTO = "numero_alertas_tempranas_por_adolecencia_m_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_ADOLECENCIA_F_ASUNTO = "numero_alertas_tempranas_por_adolecencia_f_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_JUVENTUD_ASUNTO = "numero_alertas_tempranas_por_juventud_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_JUVENTUD_M_ASUNTO = "numero_alertas_tempranas_por_juventud_m_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_JUVENTUD_F_ASUNTO = "numero_alertas_tempranas_por_juventud_f_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_MAYOR_ASUNTO = "numero_alertas_tempranas_por_mayor_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_MAYOR_M_ASUNTO = "numero_alertas_tempranas_por_mayor_m_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_MAYOR_F_ASUNTO = "numero_alertas_tempranas_por_mayor_f_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_ADULTEZ_ASUNTO = "numero_alertas_tempranas_por_adultez_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_ADULTEZ_M_ASUNTO = "numero_alertas_tempranas_por_adultez_m_asunto";
    private final String NUMERO_ALERTAS_TEMPRANAS_POR_ADULTEZ_F_ASUNTO = "numero_alertas_tempranas_por_adultez_f_asunto";
    //############################################################################################\\
    //###############################CONSULTA#####################################################\\

    private JsonObject obj;

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
                id_asunto = jsonObject.get(ID_ASUNTO).getAsInt();
                nombre_asunto = jsonObject.get(NOMBRE_ASUNTO).getAsString();
                numero_alertas_tempranas_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_ASUNTO).getAsInt();
                numero_alertas_tempranas_atendidas_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_ATENDIDAS_ASUNTO).getAsInt();
                numero_alertas_tempranas_no_atendidas_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_NO_ATENDIDAS_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_primera_infancia_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_PRIMERA_INFANCIA_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_primera_m_infancia_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_PRIMERA_M_INFANCIA_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_primera_f_infancia_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_PRIMERA_F_INFANCIA_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_infancia_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_INFANCIA_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_infancia_m_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_INFANCIA_M_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_infancia_f_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_INFANCIA_F_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_adolecencia_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_ADOLECENCIA_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_adolecencia_m_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_ADOLECENCIA_M_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_adolecencia_f_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_ADOLECENCIA_F_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_juventud_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_JUVENTUD_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_juventud_m_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_JUVENTUD_M_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_juventud_f_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_JUVENTUD_F_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_mayor_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_MAYOR_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_mayor_m_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_MAYOR_M_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_mayor_f_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_MAYOR_F_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_adultez_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_ADULTEZ_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_adultez_m_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_ADULTEZ_M_ASUNTO).getAsInt();
                numero_alertas_tempranas_por_adultez_f_asunto = jsonObject.get(NUMERO_ALERTAS_TEMPRANAS_POR_ADULTEZ_F_ASUNTO).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }
}
