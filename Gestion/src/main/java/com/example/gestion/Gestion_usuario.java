package com.example.gestion;

import com.example.modelo.Usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_usuario {
    private static Usuario aux = new Usuario();
    private static String llave_ws = "usuario";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;
    private static Usuario usuario_online = null;

    private static void iniciar_axu()
    {
        aux = new Usuario();
    }

    public HashMap<String, String> registrar_usuario(Usuario usuario)
    {
        tipo_consulta = "insert";
        return construir_parametros(usuario);
    }

    public HashMap<String, String> validar_usuario(Usuario usuario)
    {
        tipo_consulta = "validar_cuenta";
        return construir_parametros(usuario);
    }

    public HashMap<String, String> consultar_usuario_por_id(int id)
    {
        tipo_consulta = "consultar_usuario_por_id";
        aux.id_usuario = id;
        return construir_parametros(aux);
    }

    public ArrayList<Usuario> generar_json(String respuesta)
    {
        ArrayList<Usuario> lista_elementos = new ArrayList<>();
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

    private Usuario agregar_elemento(final JsonObject jsonObject)
    {
        return new Usuario(){{
            try {
                id_usuario = jsonObject.get("id_usuario").getAsInt();
                numero_identificacion_usuario = jsonObject.get("numero_identificacion_usuario").getAsString();
                nombres_usuario = jsonObject.get("nombres_usuario").getAsString();
                apellidos_usuario = jsonObject.get("apellidos_usuario").getAsString();
                direccion_usuario = jsonObject.get("direccion_usuario").getAsString();
                telefono_usuario = jsonObject.get("telefono_usuario").getAsString();
                sexo_usuario = jsonObject.get("sexo_usuario").getAsInt();
                fecha_nacimiento = jsonObject.get("fecha_nacimiento").getAsString();
                correo_usuario = jsonObject.get("correo_usuario").getAsString();
                nombre_cuenta_usuario = jsonObject.get("nombre_cuenta_usuario").getAsString();
                contrasena_usuario = jsonObject.get("contraseña_usuario").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Usuario elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_usuario", elemento.id_usuario);
            obj.addProperty("numero_identificacion_usuario", elemento.numero_identificacion_usuario);
            obj.addProperty("nombres_usuario", elemento.nombres_usuario);
            obj.addProperty("apellidos_usuario", elemento.apellidos_usuario);
            obj.addProperty("direccion_usuario", elemento.direccion_usuario);
            obj.addProperty("telefono_usuario", elemento.telefono_usuario);
            obj.addProperty("sexo_usuario", elemento.sexo_usuario);
            obj.addProperty("fecha_nacimiento", elemento.fecha_nacimiento);
            obj.addProperty("correo_usuario", elemento.correo_usuario);
            obj.addProperty("nombre_cuenta_usuario", elemento.nombre_cuenta_usuario);
            obj.addProperty("contrasena_usuario", elemento.contrasena_usuario);
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

    public static Usuario getUsuario_online() {
        return usuario_online;
    }

    public static void setUsuario_online(Usuario usuario_online) {
        Gestion_usuario.usuario_online = usuario_online;
    }
}
