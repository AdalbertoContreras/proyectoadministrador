package com.comfacesar.gestion;

import com.comfacesar.modelo.Usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class Gestion_usuario {

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
        Usuario usuario = new Usuario(){{
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
                foto_perfil_usuario = jsonObject.get("foto_perfil_usuario").getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException  | JsonIOException e) {
                e.printStackTrace();
            }
        }};
        return usuario;
    }
}
