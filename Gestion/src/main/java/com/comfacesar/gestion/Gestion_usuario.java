package com.comfacesar.gestion;

import com.comfacesar.modelo.Usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class Gestion_usuario
{
    //############################################################################################\\
    //###############################PROPIEDADES USUARIO##########################################\\
    private final String ID_USUARIO = "id_usuario";
    private final String NOMBRES_USUARIO = "nombres_usuario";
    private final String APELLIDOS_USUARIO = "apellidos_usuario";
    private final String DIRECCION_USUARIO = "direccion_usuario";
    private final String TELEFONO_USUARIO = "telefono_usuario";
    private final String SEXO_USUARIO = "sexo_usuario";
    private final String FECHA_NACIMIENTO = "fecha_nacimiento";
    private final String CORREO_USUARIO = "correo_usuario";
    private final String NOMBRE_CUENTA_USUARIO = "nombre_cuenta_usuario";
    private final String FOTO_PERFIL_USUARIO = "foto_perfil_usuario";

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
                id_usuario = jsonObject.get(ID_USUARIO).getAsInt();
                nombres_usuario = jsonObject.get(NOMBRES_USUARIO).getAsString();
                apellidos_usuario = jsonObject.get(APELLIDOS_USUARIO).getAsString();
                direccion_usuario = jsonObject.get(DIRECCION_USUARIO).getAsString();
                telefono_usuario = jsonObject.get(TELEFONO_USUARIO).getAsString();
                sexo_usuario = jsonObject.get(SEXO_USUARIO).getAsInt();
                fecha_nacimiento = jsonObject.get(FECHA_NACIMIENTO).getAsString();
                correo_usuario = jsonObject.get(CORREO_USUARIO).getAsString();
                nombre_cuenta_usuario = jsonObject.get(NOMBRE_CUENTA_USUARIO).getAsString();
                foto_perfil_usuario = jsonObject.get(FOTO_PERFIL_USUARIO).getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException  | JsonIOException e) {
                e.printStackTrace();
            }
        }};
        return usuario;
    }
}
