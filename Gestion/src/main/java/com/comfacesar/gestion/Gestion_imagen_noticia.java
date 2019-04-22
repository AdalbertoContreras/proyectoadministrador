package com.comfacesar.gestion;

import com.comfacesar.modelo.Imagen_noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_imagen_noticia {
    private static String tipo_consulta;
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_IMAGEN_NOTICIA = Propiedades.LLAVE_IMAGEN_NOTICIA;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_IMAGEN_NOTICIA = "id_imagen_noticia";
    private final String URL_IMAGEN_NOTICIA = "url_imagen_noticia";
    private final String URL_IMAGEN_ANTERIOR_NOTICIA = "url_imagen_anterior_noticia";
    private final String FECHA_REGISTRO_IMAGEN_NOTICIA = "fecha_registro_imagen_noticia";
    private final String HORA_REGISTRO_IMAGEN_NOTICIA = "hora_registro_imagen_noticia";
    private final String NOTICIA_IMAGEN_NOTICIA = "noticia_imagen_noticia";
    //############################################################################################\\
    //###############################CONSULTAS####################################################\\
    private final String SUBIR_Y_ELIMINAR_IMAGEN = "subir_y_eliminar_imagen";
    private final String REGISTRAR_IMAGEN_CON_ARCHIVO = "registrar_imagen_con_archivo";

    public HashMap<String, String> subir_y_eliminar_imagen(Imagen_noticia imagen_noticia)
    {
        tipo_consulta = SUBIR_Y_ELIMINAR_IMAGEN;
        return construir_parametros(imagen_noticia);
    }

    public HashMap<String, String> registrar_imagen_con_archivo(Imagen_noticia imagen_noticia)
    {
        tipo_consulta = REGISTRAR_IMAGEN_CON_ARCHIVO;
        return construir_parametros(imagen_noticia);
    }

    public ArrayList<Imagen_noticia> generar_json(String respuesta)
    {
        ArrayList<Imagen_noticia> lista_elementos = new ArrayList<>();
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

    private Imagen_noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Imagen_noticia(){{
            try {
                id_imagen_noticia = jsonObject.get(ID_IMAGEN_NOTICIA).getAsInt();
                url_imagen_noticia = jsonObject.get(URL_IMAGEN_NOTICIA).getAsString();
                fecha_registro_imagen_noticia = jsonObject.get(FECHA_REGISTRO_IMAGEN_NOTICIA).getAsString();
                hora_registro_imagen_noticia = jsonObject.get(HORA_REGISTRO_IMAGEN_NOTICIA).getAsString();
                noticia_imagen_noticia = jsonObject.get(NOTICIA_IMAGEN_NOTICIA).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Imagen_noticia elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(ID_IMAGEN_NOTICIA, elemento.id_imagen_noticia);
            obj.addProperty(URL_IMAGEN_NOTICIA, elemento.url_imagen_noticia);
            obj.addProperty(URL_IMAGEN_ANTERIOR_NOTICIA, elemento.url_imagen_anterior_noticia);
            obj.addProperty(FECHA_REGISTRO_IMAGEN_NOTICIA, elemento.fecha_registro_imagen_noticia);
            obj.addProperty(HORA_REGISTRO_IMAGEN_NOTICIA, elemento.hora_registro_imagen_noticia);
            obj.addProperty(NOTICIA_IMAGEN_NOTICIA, elemento.noticia_imagen_noticia);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS, LLAVE_IMAGEN_NOTICIA);
            if(Gestion_administrador.getAdministrador_actual() != null)
            {
                obj.addProperty(TOKEN,Gestion_administrador.getAdministrador_actual().token);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }
}
