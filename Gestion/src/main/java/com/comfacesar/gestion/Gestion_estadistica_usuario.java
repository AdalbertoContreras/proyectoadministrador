package com.comfacesar.gestion;

import com.comfacesar.modelo.Estadistica_usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_estadistica_usuario {
    private static Estadistica_usuario aux = new Estadistica_usuario();
    private static String llave_ws = "estadistica_usuario";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Estadistica_usuario();
    }

    public HashMap<String, String> consultar()
    {
        tipo_consulta = "consultar";
        return construir_parametros();
    }

    public ArrayList<Estadistica_usuario> generar_json(String respuesta)
    {
        ArrayList<Estadistica_usuario> lista_elementos = new ArrayList<>();
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

    private Estadistica_usuario agregar_elemento(final JsonObject jsonObject)
    {
        return new Estadistica_usuario(){{
            try {
                id_estadistica_usuario = jsonObject.get("id_estadistica_usuario").getAsInt();
                numero_usuarios_total = jsonObject.get("numero_usuarios_total").getAsInt();
                numero_usuarios_femeninos = jsonObject.get("numero_usuarios_femeninos").getAsInt();
                numero_usuarios_masculinos = jsonObject.get("numero_usuarios_masculinos").getAsInt();
                numero_usuarios_primera_infancia = jsonObject.get("numero_usuarios_primera_infancia").getAsInt();
                numero_usuarios_primera_infancia_femenino = jsonObject.get("numero_usuarios_primera_infancia_femenino").getAsInt();
                numero_usuarios_primera_infancia_masculino = jsonObject.get("numero_usuarios_primera_infancia_masculino").getAsInt();
                numero_usuarios_infancia = jsonObject.get("numero_usuarios_infancia").getAsInt();
                numero_usuarios_infancia_femenino = jsonObject.get("numero_usuarios_infancia_femenino").getAsInt();
                numero_usuarios_infancia_masculino = jsonObject.get("numero_usuarios_infancia_masculino").getAsInt();
                numero_usuarios_adolecente = jsonObject.get("numero_usuarios_adolecente").getAsInt();
                numero_usuarios_adolecente_femenino = jsonObject.get("numero_usuarios_adolecente_femenino").getAsInt();
                numero_usuarios_adolecente_masculino = jsonObject.get("numero_usuarios_adolecente_masculino").getAsInt();
                numero_usuarios_joven = jsonObject.get("numero_usuarios_joven").getAsInt();
                numero_usuarios_joven_femenino = jsonObject.get("numero_usuarios_joven_femenino").getAsInt();
                numero_usuarios_joven_masculino = jsonObject.get("numero_usuarios_joven_masculino").getAsInt();
                numero_usuarios_adulto = jsonObject.get("numero_usuarios_adulto").getAsInt();
                numero_usuarios_adulto_femenino = jsonObject.get("numero_usuarios_adulto_femenino").getAsInt();
                numero_usuarios_adulto_masculino = jsonObject.get("numero_usuarios_adulto_masculino").getAsInt();
                numero_usuarios_mayor = jsonObject.get("numero_usuarios_mayor").getAsInt();
                numero_usuarios_mayor_femenino = jsonObject.get("numero_usuarios_mayor_femenino").getAsInt();
                numero_usuarios_mayor_masculino = jsonObject.get("numero_usuarios_mayor_masculino").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Estadistica_usuario elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_estadistica_usuario", elemento.id_estadistica_usuario);
            obj.addProperty("numero_usuarios_total", elemento.numero_usuarios_total);
            obj.addProperty("numero_usuarios_femeninos", elemento.numero_usuarios_femeninos);
            obj.addProperty("numero_usuarios_masculinos", elemento.numero_usuarios_masculinos);
            obj.addProperty("numero_usuarios_primera_infancia", elemento.numero_usuarios_primera_infancia);
            obj.addProperty("numero_usuarios_primera_infancia_femenino", elemento.numero_usuarios_primera_infancia_femenino);
            obj.addProperty("numero_usuarios_primera_infancia_masculino", elemento.numero_usuarios_primera_infancia_masculino);
            obj.addProperty("numero_usuarios_infancia", elemento.numero_usuarios_infancia);
            obj.addProperty("numero_usuarios_infancia_femenino", elemento.numero_usuarios_infancia_femenino);
            obj.addProperty("numero_usuarios_infancia_masculino", elemento.numero_usuarios_infancia_masculino);
            obj.addProperty("numero_usuarios_adolecente", elemento.numero_usuarios_adolecente);
            obj.addProperty("numero_usuarios_adolecente_femenino", elemento.numero_usuarios_adolecente_femenino);
            obj.addProperty("numero_usuarios_adolecente_masculino", elemento.numero_usuarios_adolecente_masculino);
            obj.addProperty("numero_usuarios_joven", elemento.numero_usuarios_joven);
            obj.addProperty("numero_usuarios_joven_femenino", elemento.numero_usuarios_joven_femenino);
            obj.addProperty("numero_usuarios_joven_masculino", elemento.numero_usuarios_joven_masculino);
            obj.addProperty("numero_usuarios_adulto", elemento.numero_usuarios_adulto);
            obj.addProperty("numero_usuarios_adulto_femenino", elemento.numero_usuarios_adulto_femenino);
            obj.addProperty("numero_usuarios_adulto_masculino", elemento.numero_usuarios_adulto_masculino);
            obj.addProperty("numero_usuarios_mayor", elemento.numero_usuarios_mayor);
            obj.addProperty("numero_usuarios_mayor_femenino", elemento.numero_usuarios_mayor_femenino);
            obj.addProperty("numero_usuarios_mayor_masculino", elemento.numero_usuarios_mayor_masculino);
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            if(Gestion_administrador.getAdministrador_actual() != null)
            {
                obj.addProperty("nombre_administrador_ol",Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
                obj.addProperty("contrasena_administrador_ol",Gestion_administrador.getAdministrador_actual().contrasena_administrador);
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

    private HashMap<String,String> construir_parametros()
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            if(Gestion_administrador.getAdministrador_actual() != null)
            {
                obj.addProperty("nombre_administrador_ol",Gestion_administrador.getAdministrador_actual().nombre_cuenta_administrador);
                obj.addProperty("contrasena_administrador_ol",Gestion_administrador.getAdministrador_actual().contrasena_administrador);
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
