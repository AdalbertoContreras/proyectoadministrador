package com.comfacesar.gestion;

import com.comfacesar.modelo.Administrador;
import com.comfacesar.modelo.Categoria_noticia_manual;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_categoria_noticia {
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_CATEGORIA_NOTICIA = Propiedades.LLAVE_CATEGORIA_NOTICIA_MANUAL;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_CATEGORIA_NOTICIA_MANUAL = "id_categoria_noticia_manual";
    private final String NOMBRE_CATEGORIA_NOTICIA = "nombre_categoria_noticia";
    private final String NUMERO_NOTICIAS_CATEGORIA_NOTICIA = "numero_noticias_categoria_noticia";
    private final String NUMERO_NOTICIAS_NO_VISITADAS_CATEGORIA_NOTICIA = "numero_noticias_no_visitadas_categoria_noticia";
    private final String NUMERO_VISITAS_CATEGORIA_NOTICIA_MANUAL = "numero_visitas_categoria_noticia_manual";
    private final String NUMERO_VISITAS_POR_PRIMERA_INFANCIA_CATEGORIA_NOTICIA = "numero_visitas_por_primera_infancia_categoria_noticia";
    private final String NUMERO_VISITAS_POR_PRIMERA_M_INFANCIA_CATEGORIA_NOTICIA = "numero_visitas_por_primera_m_infancia_categoria_noticia";
    private final String NUMERO_VISITAS_POR_PRIMERA_F_INFANCIA_CATEGORIA_NOTICIA = "numero_visitas_por_primera_f_infancia_categoria_noticia";
    private final String NUMERO_VISITAS_POR_INFANCIA_CATEGORIA_NOTICIA = "numero_visitas_por_infancia_categoria_noticia";
    private final String NUMERO_VISITAS_POR_INFANCIA_M_CATEGORIA_NOTICIA = "numero_visitas_por_infancia_m_categoria_noticia";
    private final String NUMERO_VISITAS_POR_INFANCIA_F_CATEGORIA_NOTICIA = "numero_visitas_por_infancia_f_categoria_noticia";
    private final String NUMERO_VISITAS_POR_ADOLECENCIA_CATEGORIA_NOTICIA = "numero_visitas_por_adolecencia_categoria_noticia";
    private final String NUMERO_VISITAS_POR_ADOLECENCIA_M_CATEGORIA_NOTICIA = "numero_visitas_por_adolecencia_m_categoria_noticia";
    private final String NUMERO_VISITAS_POR_ADOLECENCIA_F_CATEGORIA_NOTICIA = "numero_visitas_por_adolecencia_f_categoria_noticia";
    private final String NUMERO_VISITAS_POR_JUVENTUD_CATEGORIA_NOTICIA = "numero_visitas_por_juventud_categoria_noticia";
    private final String NUMERO_VISITAS_POR_JUVENTUD_M_CATEGORIA_NOTICIA = "numero_visitas_por_juventud_m_categoria_noticia";
    private final String NUMERO_VISITAS_POR_JUVENTUD_F_CATEGORIA_NOTICIA = "numero_visitas_por_juventud_f_categoria_noticia";
    private final String NUMERO_VISITAS_POR_ADULTEZ_CATEGORIA_NOTICIA = "numero_visitas_por_adultez_categoria_noticia";
    private final String NUMERO_VISITAS_POR_ADULTEZ_M_CATEGORIA_NOTICIA = "numero_visitas_por_adultez_m_categoria_noticia";
    private final String NUMERO_VISITAS_POR_ADULTEZ_F_CATEGORIA_NOTICIA = "numero_visitas_por_adultez_f_categoria_noticia";
    private final String NUMERO_VISITAS_POR_MAYOR_CATEGORIA_NOTICIA = "numero_visitas_por_mayor_categoria_noticia";
    private final String NUMERO_VISITAS_POR_MAYOR_M_CATEGORIA_NOTICIA = "numero_visitas_por_mayor_m_categoria_noticia";
    private final String NUMERO_VISITAS_POR_MAYOR_F_CATEGORIA_NOTICIA = "numero_visitas_por_mayor_f_categoria_noticia";
    //############################################################################################\\
    //###############################CONSULTA#####################################################\\
    private final String CONSULTAR_CATEGORIAS = "consultar_categorias";
    private Categoria_noticia_manual aux = new Categoria_noticia_manual();
    private String tipo_consulta;
    private JsonObject obj;

    public HashMap<String, String> consultar_categorias()
    {
        tipo_consulta = CONSULTAR_CATEGORIAS;
        return construir_parametros(aux);
    }

    public ArrayList<Categoria_noticia_manual> generar_json(String respuesta)
    {
        ArrayList<Categoria_noticia_manual> lista_elementos = new ArrayList<>();
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

    private Categoria_noticia_manual agregar_elemento(final JsonObject jsonObject)
    {
        return new Categoria_noticia_manual(){{
            try {
                id_categoria_noticia_manual = jsonObject.get(ID_CATEGORIA_NOTICIA_MANUAL).getAsInt();
                nombre_categoria_noticia = jsonObject.get(NOMBRE_CATEGORIA_NOTICIA).getAsString();
                numero_noticias_categoria_noticia = jsonObject.get(NUMERO_NOTICIAS_CATEGORIA_NOTICIA).getAsInt();
                numero_noticias_no_visitadas_categoria_noticia = jsonObject.get(NUMERO_NOTICIAS_NO_VISITADAS_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_categoria_noticia_manual = jsonObject.get(NUMERO_VISITAS_CATEGORIA_NOTICIA_MANUAL).getAsInt();
                numero_visitas_por_primera_infancia_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_PRIMERA_INFANCIA_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_primera_m_infancia_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_PRIMERA_M_INFANCIA_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_primera_f_infancia_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_PRIMERA_F_INFANCIA_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_infancia_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_INFANCIA_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_infancia_m_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_INFANCIA_M_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_infancia_f_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_INFANCIA_F_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_adolecencia_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_ADOLECENCIA_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_adolecencia_m_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_ADOLECENCIA_M_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_adolecencia_f_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_ADOLECENCIA_F_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_juventud_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_JUVENTUD_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_juventud_m_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_JUVENTUD_M_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_juventud_f_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_JUVENTUD_F_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_adultez_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_ADULTEZ_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_adultez_m_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_ADULTEZ_M_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_adultez_f_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_ADULTEZ_F_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_mayor_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_MAYOR_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_mayor_m_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_MAYOR_M_CATEGORIA_NOTICIA).getAsInt();
                numero_visitas_por_mayor_f_categoria_noticia = jsonObject.get(NUMERO_VISITAS_POR_MAYOR_F_CATEGORIA_NOTICIA).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Categoria_noticia_manual elemento)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(ID_CATEGORIA_NOTICIA_MANUAL, elemento.id_categoria_noticia_manual);
            obj.addProperty(NOMBRE_CATEGORIA_NOTICIA, elemento.nombre_categoria_noticia);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_CATEGORIA_NOTICIA);
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
