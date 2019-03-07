package com.example.gestion;

import com.example.modelo.Asunto;
import com.example.modelo.Boton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_boton {
    private static Boton aux;
    private static String llave_ws = "administrador";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Boton();
    }
    private ArrayList<Boton> generar_json(String respuesta)
    {
        ArrayList<Boton> lista_elementos = new ArrayList<>();
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

    private Boton agregar_elemento(final JsonObject jsonObject)
    {
        return new Boton(){{
            try {
                id_boton = jsonObject.get("id_boton").getAsInt();
                nombre_boton_boton = jsonObject.get("nombre_boton_boton").getAsString();
                id_boton_en_aplicacion_boton = jsonObject.get("id_boton_en_aplicacion_boton").getAsInt();
                numero_veces_presionados_por_primera_infancia_boton = jsonObject.get("numero_veces_presionados_por_primera_infancia_boton").getAsInt();
                numero_veces_presionados_por_primera_infancia_m_boton = jsonObject.get("numero_veces_presionados_por_primera_infancia_m_boton").getAsInt();
                numero_veces_presionados_por_primera_infancia_f_boton = jsonObject.get("numero_veces_presionados_por_primera_infancia_f_boton").getAsInt();
                numero_veces_presionados_por_infancia_boton = jsonObject.get("numero_veces_presionados_por_infancia_boton").getAsInt();
                numero_veces_presionados_por_infancia_m_boton = jsonObject.get("numero_veces_presionados_por_infancia_m_boton").getAsInt();
                numero_veces_presionados_por_infancia_f_boton = jsonObject.get("numero_veces_presionados_por_infancia_f_boton").getAsInt();
                numero_veces_presionados_por_adolecencia_boton = jsonObject.get("numero_veces_presionados_por_adolecencia_boton").getAsInt();
                numero_veces_presionados_por_adolecencia_m_boton = jsonObject.get("numero_veces_presionados_por_adolecencia_m_boton").getAsInt();
                numero_veces_presionados_por_adolecencia_f_boton = jsonObject.get("numero_veces_presionados_por_adolecencia_f_boton").getAsInt();
                numero_veces_presionado_por_juventud_boton = jsonObject.get("numero_veces_presionado_por_juventud_boton").getAsInt();
                numero_veces_presionado_por_juventud_f_boton = jsonObject.get("numero_veces_presionado_por_juventud_f_boton").getAsInt();
                numero_veces_presionado_por_juventud_f_boton = jsonObject.get("numero_veces_presionado_por_juventud_f_boton").getAsInt();
                numero_veces_presionado_por_adultez_boton = jsonObject.get("numero_veces_presionado_por_adultez_boton").getAsInt();
                numero_veces_presionado_por_adultez_m_boton = jsonObject.get("numero_veces_presionado_por_adultez_m_boton").getAsInt();
                numero_veces_presionado_por_adultez_f_boton = jsonObject.get("numero_veces_presionado_por_adultez_f_boton").getAsInt();
                numero_veces_presionado_por_mayor_boton = jsonObject.get("numero_veces_presionado_por_mayor_boton").getAsInt();
                numero_veces_presionado_por_mayor_m_boton = jsonObject.get("numero_veces_presionado_por_mayor_m_boton").getAsInt();
                numero_veces_presionado_por_mayor_f_boton = jsonObject.get("numero_veces_presionado_por_mayor_f_boton").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Boton elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_boton", elemento.id_boton);
            obj.addProperty("nombre_boton_boton", elemento.nombre_boton_boton);
            obj.addProperty("id_boton_en_aplicacion_boton", elemento.id_boton_en_aplicacion_boton);
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
