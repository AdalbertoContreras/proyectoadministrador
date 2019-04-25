package com.comfacesar.gestion;

import com.comfacesar.modelo.Alerta_temprana;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_alerta_temprana {
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private Alerta_temprana aux = new Alerta_temprana();
    private final String LLAVE_ALERTA_TEMPRANA = Propiedades.LLAVE_ALERTA_TEMPRANA;
    private final String TOKEN = Propiedades.TOKEN;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    //############################################################################################\\
    //###############################PROPIEDADES DE ALERTA TEMPRANA###############################\\
    private final String ID_ALERTA_TEMPRANA = "id_alerta_temprana";
    private final String ASUNTO_ALERTA_TEMPRANA = "asunto_alerta_temprana";
    private final String DESCRIPCION_ALERTA_TEMPRANA = "descripcion_alerta_temprana";
    private final String FECHA_ALERTA_TEMPRANA = "fecha_alerta_temprana";
    private final String ATENDIDO_POR = "atendido_por";
    private final String HORA_ALERTA_TEMPRANA = "hora_alerta_temprana";
    private final String USUARIO_ALERTA_TEMPRANA = "usuario_alerta_temprana";
    private final String ESTADO_ATENDIDO = "estado_atendido";
    private final String ASUNTO = "asunto";
    private final String USUARIO = "usuario";
    private final String ESTADO_VISTO = "estado_visto";
    private final String NUMERO_VISITAS = "numero_visitas";
    private final String DIRECCION_ALERTA_TEMPRANA = "direccion_alerta_temprana";
    private final String NUMERO_TELEFONO_ALERTA_TEMPRANA = "numero_telefono_alerta_temprana";
    //############################################################################################\\
    //###############################CONSULTAS####################################################\\
    private final String CONSULTAR_ALERTA_TEMPRANA = "consultar_alerta_temprana";
    private final String ATENDIDO = "atendido";
    private final String NO_ATENDIDO = "no_atendido";
    private final String CONSULTAR_MAYOR = "consultar_mayor";
    private String tipo_consulta;
    private JsonObject obj;

    public HashMap<String, String> consultar_alerta_temprana()
    {
        tipo_consulta = CONSULTAR_ALERTA_TEMPRANA;
        return construir_parametros();
    }

    public HashMap<String, String> atendido(int atendido_por, int id_alerta)
    {
        tipo_consulta = ATENDIDO;
        aux.atendido_por = atendido_por;
        aux.id_alerta_temprana = id_alerta;
        return construir_parametros(aux);
    }

    public HashMap<String, String> no_atendido(int id_alerta)
    {
        tipo_consulta = NO_ATENDIDO;
        aux.id_alerta_temprana = id_alerta;
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_mayor(int id_alerta_temprana)
    {
        tipo_consulta = CONSULTAR_MAYOR;
        aux.id_alerta_temprana = id_alerta_temprana;
        return construir_parametros(aux);
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
                id_alerta_temprana = jsonObject.get(ID_ALERTA_TEMPRANA).getAsInt();
                asunto_alerta_temprana = jsonObject.get(ASUNTO_ALERTA_TEMPRANA).getAsInt();
                descripcion_alerta_temprana = jsonObject.get(DESCRIPCION_ALERTA_TEMPRANA).getAsString();
                fecha_alerta_temprana = jsonObject.get(FECHA_ALERTA_TEMPRANA).getAsString();
                hora_alerta_temprana = jsonObject.get(HORA_ALERTA_TEMPRANA).getAsString();
                usuario_alerta_temprana = jsonObject.get(USUARIO_ALERTA_TEMPRANA).getAsInt();
                numero_visitas = jsonObject.get(NUMERO_VISITAS).getAsInt();
                estado_atendido = jsonObject.get(ESTADO_ATENDIDO).getAsInt();
                direccion_alerta_temprana = jsonObject.get(DIRECCION_ALERTA_TEMPRANA).getAsString();
                numero_telefono_alerta_temprana = jsonObject.get(NUMERO_TELEFONO_ALERTA_TEMPRANA).getAsString();
                if(!jsonObject.get(ATENDIDO_POR).isJsonNull())
                {
                    atendido_por = jsonObject.get(ATENDIDO_POR).getAsInt();
                }
                else
                {
                    atendido_por = -1;
                }
                if(!jsonObject.get(ASUNTO).isJsonNull())
                {
                    asunto = jsonObject.get(ASUNTO).getAsString();
                }
                else
                {
                    asunto = "";
                }
                if(!jsonObject.get(USUARIO).isJsonNull())
                {
                    usuario = jsonObject.get(USUARIO).getAsString();
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
        obj = new JsonObject();
        try {
            obj.addProperty(ID_ALERTA_TEMPRANA, elemento.id_alerta_temprana);
            obj.addProperty(ASUNTO_ALERTA_TEMPRANA, elemento.asunto_alerta_temprana);
            obj.addProperty(DESCRIPCION_ALERTA_TEMPRANA, elemento.descripcion_alerta_temprana);
            obj.addProperty(FECHA_ALERTA_TEMPRANA, elemento.fecha_alerta_temprana);
            obj.addProperty(HORA_ALERTA_TEMPRANA, elemento.hora_alerta_temprana);
            obj.addProperty(USUARIO_ALERTA_TEMPRANA, elemento.usuario_alerta_temprana);
            obj.addProperty(ESTADO_VISTO, elemento.estado_visto);
            obj.addProperty(NUMERO_VISITAS, elemento.numero_visitas);
            obj.addProperty(ESTADO_ATENDIDO, elemento.estado_atendido);
            obj.addProperty(ATENDIDO_POR, elemento.atendido_por);
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS, LLAVE_ALERTA_TEMPRANA);
            adjuntarAcceso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    private HashMap<String,String> construir_parametros()
    {
        obj = new JsonObject();
        try {
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS, LLAVE_ALERTA_TEMPRANA);
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
