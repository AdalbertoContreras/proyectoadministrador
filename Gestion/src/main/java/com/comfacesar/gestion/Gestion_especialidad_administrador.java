package com.comfacesar.gestion;

import com.comfacesar.modelo.Especialidad_administrador;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_especialidad_administrador {
    private static Especialidad_administrador aux = new Especialidad_administrador();
    private static String llave_ws = "especialidad_administrador";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;
    private JsonObject obj;

    public HashMap<String, String> registrar_especialidad_administrador(Especialidad_administrador especialidad_administrador){
        tipo_consulta = "insert";
        return construir_parametros(especialidad_administrador);
    }
    public HashMap<String, String> eliminar_especialidad_administrador(Especialidad_administrador especialidad_administrador){
        tipo_consulta = "delete";
        return construir_parametros(especialidad_administrador);
    }

    public ArrayList<Especialidad_administrador> generar_json(String respuesta)
    {

        ArrayList<Especialidad_administrador> lista_elementos = new ArrayList<>();
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

    private Especialidad_administrador agregar_elemento(final JsonObject jsonObject)
    {
        return new Especialidad_administrador(){{
            try {
                id_especialidad_administrador = jsonObject.get("id_especialidad_administrador").getAsInt();
                especialidad_especialidad_admnistrador = jsonObject.get("especialidad_especialidad_admnistrador").getAsInt();
                administrador_especialidad_administrador = jsonObject.get("administrador_especialidad_administrador").getAsInt();
                fecha_especilidad_administrador = jsonObject.get("fecha_especilidad_administrador").getAsString();
                hora_especialidad_administrador = jsonObject.get("hora_especialidad_administrador").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Especialidad_administrador elemento)
    {
        obj = new JsonObject();
        try {
            obj.addProperty("id_especialidad_administrador", elemento.id_especialidad_administrador);
            obj.addProperty("especialidad_especialidad_admnistrador", elemento.especialidad_especialidad_admnistrador);
            obj.addProperty("administrador_especialidad_administrador", elemento.administrador_especialidad_administrador);
            obj.addProperty("fecha_especilidad_administrador", elemento.fecha_especilidad_administrador);
            obj.addProperty("hora_especialidad_administrador", elemento.hora_especialidad_administrador);
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            adjuntarAcceso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }

    private void adjuntarAcceso()
    {
        if(Gestion_administrador.getAdministrador_actual() != null)
        {
            obj.addProperty("NA",Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
            obj.addProperty("CA",Gestion_administrador.getAdministrador_actual().contrasena_administrador);
        }
    }
}
