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
    private static String tipo_consulta;
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_ESTADISTICA_USUARIO= Propiedades.LLAVE_ESTADISTICA_USUARIO;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_ESTADISTICA_USUARIO = "id_estadistica_usuario";
    private final String NUMERO_USUARIOS_TOTAL = "numero_usuarios_total";
    private final String NUMERO_USUARIOS_FEMENINOS = "numero_usuarios_femeninos";
    private final String NUMERO_USUARIOS_MASCULINOS = "numero_usuarios_masculinos";
    private final String NUMERO_USUARIOS_PRIMERA_INFANCIA = "numero_usuarios_primera_infancia";
    private final String NUMERO_USUARIOS_PRIMERA_INFANCIA_FEMENINO = "numero_usuarios_primera_infancia_femenino";
    private final String NUMERO_USUARIOS_PRIMERA_INFANCIA_MASCULINO = "numero_usuarios_primera_infancia_masculino";
    private final String NUMERO_USUARIOS_INFANCIA = "numero_usuarios_infancia";
    private final String NUMERO_USUARIOS_INFANCIA_FEMENINO = "numero_usuarios_infancia_femenino";
    private final String NUMERO_USUARIOS_INFANCIA_MASCULINO = "numero_usuarios_infancia_masculino";
    private final String NUMERO_USUARIOS_ADOLECENTE = "numero_usuarios_adolecente";
    private final String NUMERO_USUARIOS_ADOLECENTE_FEMENINO = "numero_usuarios_adolecente_femenino";
    private final String NUMERO_USUARIOS_ADOLECENTE_MASCULINO = "numero_usuarios_adolecente_masculino";
    private final String NUMERO_USUARIOS_JOVEN = "numero_usuarios_joven";
    private final String NUMERO_USUARIOS_JOVEN_FEMENINO = "numero_usuarios_joven_femenino";
    private final String NUMERO_USUARIOS_JOVEN_MASCULINO = "numero_usuarios_joven_masculino";
    private final String NUMERO_USUARIOS_ADULTO = "numero_usuarios_adulto";
    private final String NUMERO_USUARIOS_ADULTO_FEMENINO = "numero_usuarios_adulto_femenino";
    private final String NUMERO_USUARIOS_ADULTO_MASCULINO = "numero_usuarios_adulto_masculino";
    private final String NUMERO_USUARIOS_MAYOR = "numero_usuarios_mayor";
    private final String NUMERO_USUARIOS_MAYOR_FEMENINO = "numero_usuarios_mayor_femenino";
    private final String NUMERO_USUARIOS_MAYOR_MASCULINO = "numero_usuarios_mayor_masculino";
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String CONSULTAR = "consultar";
    public HashMap<String, String> consultar()
    {
        tipo_consulta = CONSULTAR;
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
                id_estadistica_usuario = jsonObject.get(ID_ESTADISTICA_USUARIO).getAsInt();
                numero_usuarios_total = jsonObject.get(NUMERO_USUARIOS_TOTAL).getAsInt();
                numero_usuarios_femeninos = jsonObject.get(NUMERO_USUARIOS_FEMENINOS).getAsInt();
                numero_usuarios_masculinos = jsonObject.get(NUMERO_USUARIOS_MASCULINOS).getAsInt();
                numero_usuarios_primera_infancia = jsonObject.get(NUMERO_USUARIOS_PRIMERA_INFANCIA).getAsInt();
                numero_usuarios_primera_infancia_femenino = jsonObject.get(NUMERO_USUARIOS_PRIMERA_INFANCIA_FEMENINO).getAsInt();
                numero_usuarios_primera_infancia_masculino = jsonObject.get(NUMERO_USUARIOS_PRIMERA_INFANCIA_MASCULINO).getAsInt();
                numero_usuarios_infancia = jsonObject.get(NUMERO_USUARIOS_INFANCIA).getAsInt();
                numero_usuarios_infancia_femenino = jsonObject.get(NUMERO_USUARIOS_INFANCIA_FEMENINO).getAsInt();
                numero_usuarios_infancia_masculino = jsonObject.get(NUMERO_USUARIOS_INFANCIA_MASCULINO).getAsInt();
                numero_usuarios_adolecente = jsonObject.get(NUMERO_USUARIOS_ADOLECENTE).getAsInt();
                numero_usuarios_adolecente_femenino = jsonObject.get(NUMERO_USUARIOS_ADOLECENTE_FEMENINO).getAsInt();
                numero_usuarios_adolecente_masculino = jsonObject.get(NUMERO_USUARIOS_ADOLECENTE_MASCULINO).getAsInt();
                numero_usuarios_joven = jsonObject.get(NUMERO_USUARIOS_JOVEN).getAsInt();
                numero_usuarios_joven_femenino = jsonObject.get(NUMERO_USUARIOS_JOVEN_FEMENINO).getAsInt();
                numero_usuarios_joven_masculino = jsonObject.get(NUMERO_USUARIOS_JOVEN_MASCULINO).getAsInt();
                numero_usuarios_adulto = jsonObject.get(NUMERO_USUARIOS_ADULTO).getAsInt();
                numero_usuarios_adulto_femenino = jsonObject.get(NUMERO_USUARIOS_ADULTO_FEMENINO).getAsInt();
                numero_usuarios_adulto_masculino = jsonObject.get(NUMERO_USUARIOS_ADULTO_MASCULINO).getAsInt();
                numero_usuarios_mayor = jsonObject.get(NUMERO_USUARIOS_MAYOR).getAsInt();
                numero_usuarios_mayor_femenino = jsonObject.get(NUMERO_USUARIOS_MAYOR_FEMENINO).getAsInt();
                numero_usuarios_mayor_masculino = jsonObject.get(NUMERO_USUARIOS_MAYOR_MASCULINO).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros()
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_ESTADISTICA_USUARIO);
            if(Gestion_administrador.getAdministrador_actual() != null)
            {
                obj.addProperty(TOKEN,Gestion_administrador.getAdministrador_actual().token);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }
}
