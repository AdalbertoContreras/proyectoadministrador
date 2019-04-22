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
    private String tipo_consulta;
    private JsonObject obj;
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_ESPECIALIDAD_ADMINISTRADOR= Propiedades.LLAVE_ESPECIALIDAD_ADMINISTRADOR;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_ESPECIALIDAD_ADMINISTRADOR = "id_especialidad_administrador";
    private final String ESPECIALIDAD_ESPECIALIDAD_ADMNISTRADOR = "especialidad_especialidad_admnistrador";
    private final String ADMINISTRADOR_ESPECIALIDAD_ADMINISTRADOR = "administrador_especialidad_administrador";
    private final String FECHA_ESPECILIDAD_ADMINISTRADOR = "fecha_especilidad_administrador";
    private final String HORA_ESPECIALIDAD_ADMINISTRADOR = "hora_especialidad_administrador";
    //############################################################################################\\
    //###############################CONSULTAS#######################\\
    private final String INSERT = "insert";
    private final String DELETE = "delete";


    public HashMap<String, String> registrar_especialidad_administrador(Especialidad_administrador especialidad_administrador){
        tipo_consulta = INSERT;
        return construir_parametros(especialidad_administrador);
    }
    public HashMap<String, String> eliminar_especialidad_administrador(Especialidad_administrador especialidad_administrador){
        tipo_consulta = DELETE;
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
                id_especialidad_administrador = jsonObject.get(ID_ESPECIALIDAD_ADMINISTRADOR).getAsInt();
                especialidad_especialidad_admnistrador = jsonObject.get(ESPECIALIDAD_ESPECIALIDAD_ADMNISTRADOR).getAsInt();
                administrador_especialidad_administrador = jsonObject.get(ADMINISTRADOR_ESPECIALIDAD_ADMINISTRADOR).getAsInt();
                fecha_especilidad_administrador = jsonObject.get(FECHA_ESPECILIDAD_ADMINISTRADOR).getAsString();
                hora_especialidad_administrador = jsonObject.get(HORA_ESPECIALIDAD_ADMINISTRADOR).getAsString();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Especialidad_administrador elemento)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(ID_ESPECIALIDAD_ADMINISTRADOR, elemento.id_especialidad_administrador);
            obj.addProperty(ESPECIALIDAD_ESPECIALIDAD_ADMNISTRADOR, elemento.especialidad_especialidad_admnistrador);
            obj.addProperty(ADMINISTRADOR_ESPECIALIDAD_ADMINISTRADOR, elemento.administrador_especialidad_administrador);
            obj.addProperty(FECHA_ESPECILIDAD_ADMINISTRADOR, elemento.fecha_especilidad_administrador);
            obj.addProperty(HORA_ESPECIALIDAD_ADMINISTRADOR, elemento.hora_especialidad_administrador);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_ESPECIALIDAD_ADMINISTRADOR);
            adjuntarAcceso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    private void adjuntarAcceso()
    {
        if(Gestion_administrador.getAdministrador_actual() != null)
        {
            obj.addProperty(TOKEN,Gestion_administrador.getAdministrador_actual().token);
        }
    }
}
