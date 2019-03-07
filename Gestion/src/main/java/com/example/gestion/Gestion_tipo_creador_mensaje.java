package com.example.gestion;

import com.example.modelo.Tag_noticia;
import com.example.modelo.Tipo_creador_mensaje;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_tipo_creador_mensaje {
    private static Tipo_creador_mensaje aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Tipo_creador_mensaje();
    }
    private ArrayList<Tipo_creador_mensaje> generar_json(String respuesta)
    {
        ArrayList<Tipo_creador_mensaje> lista_elementos = new ArrayList<>();
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

    private Tipo_creador_mensaje agregar_elemento(final JsonObject jsonObject)
    {
        return new Tipo_creador_mensaje(){{
            try {
                id_tipo_creador = jsonObject.get("id_tipo_creador").getAsInt();
                nombre_tipo_creador = jsonObject.get("nombre_tipo_creador").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Tipo_creador_mensaje elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_tipo_creador", elemento.id_tipo_creador);
            obj.addProperty("nombre_tipo_creador", elemento.nombre_tipo_creador);
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
