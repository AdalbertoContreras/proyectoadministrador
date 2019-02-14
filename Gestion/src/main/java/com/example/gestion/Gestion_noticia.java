package com.example.gestion;

import com.example.modelo.Movil_registro;
import com.example.modelo.Noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_noticia {
    private static Noticia aux = new Noticia();
    private static String llave_ws = "noticia";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Noticia();
    }

    public HashMap<String, String> registrar_noticia_manual(Noticia noticia)
    {
        tipo_consulta = "registrar_noticia_manual";
        return construir_parametros(noticia);
    }

    public HashMap<String, String> consultar_num_noticia()
    {
        tipo_consulta = "consultar_num_noticias";
        return construir_parametros(aux);
    }

    public HashMap<String,String> consultar_con_imagenes_y_m_primero()
    {
        tipo_consulta = "consultar_con_imagenes_y_m_primero";
        return  construir_parametros(aux);
    }

    public ArrayList<Noticia> generar_json(String respuesta)
    {
        ArrayList<Noticia> lista_elementos = new ArrayList<>();
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

    private Noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Noticia(){{
            try {
                id_notiticia = jsonObject.get("id_movil_registro").getAsInt();
                id_generacion_noticia = jsonObject.get("fecha_movil_registro").getAsInt();
                titulo_noticia = jsonObject.get("hora_movil_registro").getAsString();
                contenido_noticia = jsonObject.get("imei_movil_registro").getAsString();
                num_imagenes_noticia = jsonObject.get("id_movil_registro").getAsInt();
                fecha_registro_noticia = jsonObject.get("tipo_registro_movil_registro").getAsString();
                hora_registro_noticia = jsonObject.get("tipo_registro_movil_registro").getAsString();
                tipo_creacion_noticia = jsonObject.get("tipo_registro_movil_registro").getAsInt();
                numero_visistas_noticia = jsonObject.get("tipo_registro_movil_registro").getAsInt();
                administrador_noticia = jsonObject.get("tipo_registro_movil_registro").getAsInt();
                categoria_noticia_manual_noticia = jsonObject.get("tipo_registro_movil_registro").getAsInt();
                json_imagenes = jsonObject.get("json_imagenes").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Noticia elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_notiticia", elemento.id_notiticia);
            obj.addProperty("id_generacion_noticia", elemento.id_generacion_noticia);
            obj.addProperty("titulo_noticia", elemento.titulo_noticia);
            obj.addProperty("contenido_noticia", elemento.contenido_noticia);
            obj.addProperty("num_imagenes_noticia", elemento.num_imagenes_noticia);
            obj.addProperty("fecha_registro_noticia", elemento.fecha_registro_noticia);
            obj.addProperty("hora_registro_noticia", elemento.hora_registro_noticia);
            obj.addProperty("tipo_creacion_noticia", elemento.tipo_creacion_noticia);
            obj.addProperty("numero_visistas_noticia", elemento.numero_visistas_noticia);
            obj.addProperty("administrador_noticia", elemento.administrador_noticia);
            obj.addProperty("categoria_noticia_manual_noticia", elemento.categoria_noticia_manual_noticia);
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
