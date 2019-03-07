package com.example.gestion;

import com.example.modelo.Movil;
import com.example.modelo.Movil_registro;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_movil_registro {
    private static Movil_registro aux;
    private static String llave_ws = "movil_registro";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Movil_registro();
    }

    public HashMap<String, String> registrar_movil_registro(Movil_registro movil_registro)
    {
        tipo_consulta = "insert";
        return  construir_parametros(movil_registro);
    }

    private ArrayList<Movil_registro> generar_json(String respuesta)
    {
        ArrayList<Movil_registro> lista_elementos = new ArrayList<>();
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

    private Movil_registro agregar_elemento(final JsonObject jsonObject)
    {
        return new Movil_registro(){{
            try {
                id_movil_registro = jsonObject.get("id_movil_registro").getAsInt();
                fecha_movil_registro = jsonObject.get("fecha_movil_registro").getAsString();
                hora_movil_registro = jsonObject.get("hora_movil_registro").getAsString();
                imei_movil_registro = jsonObject.get("imei_movil_registro").getAsString();
                id_movil_registro = jsonObject.get("id_movil_registro").getAsInt();
                tipo_registro_movil_registro = jsonObject.get("tipo_registro_movil_registro").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Movil_registro elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_movil_registro", elemento.id_movil_registro);
            obj.addProperty("fecha_movil_registro", elemento.fecha_movil_registro);
            obj.addProperty("hora_movil_registro", elemento.hora_movil_registro);
            obj.addProperty("imei_movil_registro", elemento.imei_movil_registro);
            obj.addProperty("id_registrado_movil_registro", elemento.id_registrado_movil_registro);
            obj.addProperty("tipo_registro_movil_registro", elemento.tipo_registro_movil_registro);
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
