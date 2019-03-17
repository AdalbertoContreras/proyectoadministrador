package com.example.gestion;

import com.example.modelo.Alerta_temprana;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_alerta_temprana {
    private static Alerta_temprana aux = new Alerta_temprana();
    private static String llave_ws = "alerta_temprana";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    public HashMap<String, String> consultar_alerta_temprana()
    {
        tipo_consulta = "consultar_alerta_temprana";
        return construir_parametros();
    }

    public HashMap<String, String> num_alertas()
    {
        tipo_consulta = "num_alertas";
        return construir_parametros();
    }

    public HashMap<String, String> atendido(int atendido_por, int id_alerta)
    {
        tipo_consulta = "atendido";
        aux.atendido_por = atendido_por;
        aux.id_alerta_temprana = id_alerta;
        return construir_parametros(aux);
    }

    public HashMap<String, String> no_atendido(int id_alerta)
    {
        tipo_consulta = "no_atendido";
        aux.id_alerta_temprana = id_alerta;
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_alertas_tempranas_por_usuario(int usuario)
    {
        tipo_consulta = "consultar_alertas_tempranas_por_usuario";
        aux.usuario_alerta_temprana = usuario;
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_mayor(int id_alerta_temprana)
    {
        tipo_consulta = "consultar_mayor";
        aux.id_alerta_temprana = id_alerta_temprana;
        return construir_parametros(aux);
    }

    private static void iniciar_axu()
    {
        aux = new Alerta_temprana();
    }
    public HashMap<String, String> registrar_alerta_temprana(Alerta_temprana alerta_temprana)
    {
        tipo_consulta = "insert";
        return construir_parametros(alerta_temprana);
    }

    public ArrayList<Alerta_temprana> generar_json(String respuesta)
    {
        ArrayList<Alerta_temprana> lista_elementos = new ArrayList<>();
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

    private Alerta_temprana agregar_elemento(final JsonObject jsonObject)
    {
        return new Alerta_temprana(){{
            try {
                id_alerta_temprana = jsonObject.get("id_alerta_temprana").getAsInt();
                asunto_alerta_temprana = jsonObject.get("asunto_alerta_temprana").getAsInt();
                descripcion_alerta_temprana = jsonObject.get("descripcion_alerta_temprana").getAsString();
                fecha_alerta_temprana = jsonObject.get("fecha_alerta_temprana").getAsString();
                hora_alerta_temprana = jsonObject.get("hora_alerta_temprana").getAsString();
                usuario_alerta_temprana = jsonObject.get("usuario_alerta_temprana").getAsInt();
                estado_atendido = jsonObject.get("estado_atendido").getAsInt();
                numero_visitas = jsonObject.get("numero_visitas").getAsInt();
                estado_atendido = jsonObject.get("estado_atendido").getAsInt();
                if(!jsonObject.get("atendido_por").isJsonNull())
                {
                    atendido_por = jsonObject.get("atendido_por").getAsInt();
                }
                else
                {
                    atendido_por = -1;
                }
                if(!jsonObject.get("asunto").isJsonNull())
                {
                    asunto = jsonObject.get("asunto").getAsString();
                }
                else
                {
                    asunto = "";
                }
                if(!jsonObject.get("usuario").isJsonNull())
                {
                    usuario = jsonObject.get("usuario").getAsString();
                }
                else
                {
                    usuario = "";
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Alerta_temprana elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_alerta_temprana", elemento.id_alerta_temprana);
            obj.addProperty("asunto_alerta_temprana", elemento.asunto_alerta_temprana);
            obj.addProperty("descripcion_alerta_temprana", elemento.descripcion_alerta_temprana);
            obj.addProperty("fecha_alerta_temprana", elemento.fecha_alerta_temprana);
            obj.addProperty("hora_alerta_temprana", elemento.hora_alerta_temprana);
            obj.addProperty("usuario_alerta_temprana", elemento.usuario_alerta_temprana);
            obj.addProperty("estado_visto", elemento.estado_visto);
            obj.addProperty("numero_visitas", elemento.numero_visitas);
            obj.addProperty("estado_atendido", elemento.estado_atendido);
            obj.addProperty("atendido_por", elemento.atendido_por);
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
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
                obj.addProperty("nombre_administrador_ol","");
                obj.addProperty("contrasena_administrador_ol","");
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
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
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
                obj.addProperty("nombre_administrador_ol","");
                obj.addProperty("contrasena_administrador_ol","");
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
