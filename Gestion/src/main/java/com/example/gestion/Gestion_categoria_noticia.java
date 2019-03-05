package com.example.gestion;

import com.example.modelo.Boton;
import com.example.modelo.Categoria_noticia_manual;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_categoria_noticia {
    private static Categoria_noticia_manual aux = new Categoria_noticia_manual();
    private static String llave_ws = "categoria_noticia_manual";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Categoria_noticia_manual();
    }

    public HashMap<String, String> consultar_categorias()
    {
        tipo_consulta = "consultar_categorias";
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
                id_categoria_noticia_manual = jsonObject.get("id_categoria_noticia_manual").getAsInt();
                nombre_categoria_noticia = jsonObject.get("nombre_categoria_noticia").getAsString();
                numero_noticias_categoria_noticia = jsonObject.get("numero_noticias_categoria_noticia").getAsInt();
                numero_noticias_no_visitadas_categoria_noticia = jsonObject.get("numero_noticias_no_visitadas_categoria_noticia").getAsInt();
                numero_visitas_categoria_noticia_manual = jsonObject.get("numero_visitas_categoria_noticia_manual").getAsInt();
                numero_visitas_por_primera_infancia_categoria_noticia = jsonObject.get("numero_visitas_por_primera_infancia_categoria_noticia").getAsInt();
                numero_visitas_por_primera_m_infancia_categoria_noticia = jsonObject.get("numero_visitas_por_primera_m_infancia_categoria_noticia").getAsInt();
                numero_visitas_por_primera_f_infancia_categoria_noticia = jsonObject.get("numero_visitas_por_primera_f_infancia_categoria_noticia").getAsInt();
                numero_visitas_por_infancia_categoria_noticia = jsonObject.get("numero_visitas_por_infancia_categoria_noticia").getAsInt();
                numero_visitas_por_infancia_m_categoria_noticia = jsonObject.get("numero_visitas_por_infancia_m_categoria_noticia").getAsInt();
                numero_visitas_por_infancia_f_categoria_noticia = jsonObject.get("numero_visitas_por_infancia_f_categoria_noticia").getAsInt();
                numero_visitas_por_adolecencia_categoria_noticia = jsonObject.get("numero_visitas_por_adolecencia_categoria_noticia").getAsInt();
                numero_visitas_por_adolecencia_m_categoria_noticia = jsonObject.get("numero_visitas_por_adolecencia_m_categoria_noticia").getAsInt();
                numero_visitas_por_adolecencia_f_categoria_noticia = jsonObject.get("numero_visitas_por_adolecencia_f_categoria_noticia").getAsInt();
                numero_visitas_por_juventud_categoria_noticia = jsonObject.get("numero_visitas_por_juventud_categoria_noticia").getAsInt();
                numero_visitas_por_juventud_m_categoria_noticia = jsonObject.get("numero_visitas_por_juventud_m_categoria_noticia").getAsInt();
                numero_visitas_por_juventud_f_categoria_noticia = jsonObject.get("numero_visitas_por_juventud_f_categoria_noticia").getAsInt();
                numero_visitas_por_adultez_categoria_noticia = jsonObject.get("numero_visitas_por_adultez_categoria_noticia").getAsInt();
                numero_visitas_por_adultez_m_categoria_noticia = jsonObject.get("numero_visitas_por_adultez_m_categoria_noticia").getAsInt();
                numero_visitas_por_adultez_f_categoria_noticia = jsonObject.get("numero_visitas_por_adultez_f_categoria_noticia").getAsInt();
                numero_visitas_por_mayor_categoria_noticia = jsonObject.get("numero_visitas_por_mayor_categoria_noticia").getAsInt();
                numero_visitas_por_mayor_m_categoria_noticia = jsonObject.get("numero_visitas_por_mayor_m_categoria_noticia").getAsInt();
                numero_visitas_por_mayor_f_categoria_noticia = jsonObject.get("numero_visitas_por_mayor_f_categoria_noticia").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Categoria_noticia_manual elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_categoria_noticia_manual", elemento.id_categoria_noticia_manual);
            obj.addProperty("nombre_categoria_noticia", elemento.nombre_categoria_noticia);
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
