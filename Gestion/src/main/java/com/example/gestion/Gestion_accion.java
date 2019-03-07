package com.example.gestion;

import com.example.modelo.Accion;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_accion {
    private static Accion aux;
    private static String llave_ws = "accion";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Accion();
    }

    private HashMap<String, String> registrar_accion(Accion accion)
    {
        tipo_consulta = "insert";
        return  construir_parametros(accion);
    }
    private ArrayList<Accion> generar_json(String respuesta)
    {
        ArrayList<Accion> lista_elementos = new ArrayList<>();
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

    private Accion agregar_elemento(final JsonObject jsonObject)
    {
        return new Accion(){{
            try {
                id_accion = jsonObject.get("id_accion").getAsInt();
                tipo_accion = jsonObject.get("tipo_accion").getAsInt();
                accion = jsonObject.get("accion").getAsInt();
                fecha_accion = jsonObject.get("fecha_accion").getAsString();
                hora_accion = jsonObject.get("hora_accion").getAsString();
                id_generador_accion = jsonObject.get("id_generador_accion").getAsInt();
                id_entidad_accion = jsonObject.get("id_entidad_accion").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Accion elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_accion", elemento.id_accion);
            obj.addProperty("tipo_accion",elemento.tipo_accion);
            obj.addProperty("accion",elemento.accion);
            obj.addProperty("fecha_accion",elemento.fecha_accion);
            obj.addProperty("hora_accion",elemento.hora_accion);
            obj.addProperty("id_generador_accion",elemento.id_generador_accion);
            obj.addProperty("id_entidad_accion",elemento.id_entidad_accion);
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
