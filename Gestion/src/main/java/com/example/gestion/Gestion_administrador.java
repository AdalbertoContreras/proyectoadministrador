package com.example.gestion;

import com.example.modelo.Accion;
import com.example.modelo.Administrador;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_administrador{
    private Administrador aux = new Administrador();
    private static Administrador administrador_actual = null;
    private String llave_ws = "administrador";
    private String fecha1;
    private String fecha2;
    private int especialidad;
    private String tipo_consulta;

    private void iniciar_axu()
    {
        aux = new Administrador();
    }

    public HashMap<String, String> consultar_administradores_por_especialidad(int _especialidad)
    {
        tipo_consulta = "consultar_por_especialidad";
        especialidad = _especialidad;
        return construir_parametros();
    }

    public HashMap<String, String> consultar_administrador_por_nombre(String nombre)
    {
        tipo_consulta = "consultar_por_nombre_cuenta";
        aux.nombre_cuenta_administrador = nombre;
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_administrador_por_id(int id_administrador)
    {
        tipo_consulta = "administrador_por_id";
        aux.id_administrador = id_administrador;
        return construir_parametros(aux);
    }

    public HashMap<String, String> validar_administrador(Administrador administrador)
    {
        tipo_consulta = "validar_administrador";
        return construir_parametros(administrador);
    }
    public HashMap<String, String> activar_administrador(Administrador administrador)
    {
        tipo_consulta = "activar_administrador";
        return construir_parametros();
    }

    public HashMap<String, String> bloquear_administrador(int id_administrador)
    {
        iniciar_axu();
        tipo_consulta = "bloquar_administrador";
        aux.id_administrador = id_administrador;
        return construir_parametros(aux);
    }


    public HashMap<String, String> consultar_activos()
    {
        tipo_consulta = "consultar_activos";
        return construir_parametros();
    }

    public HashMap<String, String> consultar_bloqueados()
    {
        tipo_consulta = "consultar_bloqueados";
        return construir_parametros();
    }

    public HashMap<String, String> consultar_por_nombre_cuenta(String nombre)
    {
        iniciar_axu();
        tipo_consulta = "consultar_por_nombre_cuenta";
        aux.nombre_cuenta_administrador = nombre;
        return construir_parametros(aux);
    }

    public HashMap<String, String> registrar_administrador(Administrador administrador)
    {
        tipo_consulta = "insert";
        return construir_parametros();
    }

    public ArrayList<Administrador> generar_json(String respuesta)
    {
        ArrayList<Administrador> lista_elementos = new ArrayList<>();
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

    private Administrador agregar_elemento(final JsonObject jsonObject)
    {
        return new Administrador(){{
            try {
                id_administrador = jsonObject.get("id_administrador").getAsInt();
                tipo_administrador = jsonObject.get("tipo_administrador").getAsInt();
                nombre_cuenta_administrador = jsonObject.get("nombre_cuenta_administrador").getAsString();
                nombres_administrador = jsonObject.get("nombres_administrador").getAsString();
                apellidos_administrador = jsonObject.get("apellidos_administrador").getAsString();
                fecha_nacimiento_administrador = jsonObject.get("fecha_nacimiento_administrador").getAsString();
                sexo_administrador = jsonObject.get("sexo_administrador").getAsInt();
                estado_administrador = jsonObject.get("estado_administrador").getAsInt();
                fecha_registro_administrador = jsonObject.get("fecha_registro_administrador").getAsString();
                hora_registro_administrador= jsonObject.get("hora_registro_administrador").getAsString();
                numero_asesorias_dadas_administrador = jsonObject.get("numero_asesorias_dadas_administrador").getAsInt();
                numero_asesorias_dadas_primera_infancia_administrador = jsonObject.get("numero_asesorias_dadas_primera_infancia_administrador").getAsInt();
                numero_asesorias_dadas_infancia_administrador = jsonObject.get("numero_asesorias_dadas_infancia_administrador").getAsInt();
                numero_asesorias_dadas_adolecencia_administrador = jsonObject.get("numero_asesorias_dadas_adolecencia_administrador").getAsInt();
                numero_asesorias_dadas_juventud_administrador = jsonObject.get("numero_asesorias_dadas_juventud_administrador").getAsInt();
                numero_asesorias_dadas_adultez_administrador = jsonObject.get("numero_asesorias_dadas_adultez_administrador").getAsInt();
                numero_asesorias_dadas_mayor_administrador = jsonObject.get("numero_asesorias_dadas_mayor_administrador").getAsInt();
                numero_asesorias_dadas_primera_m_infancia_administrador = jsonObject.get("numero_asesorias_dadas_primera_m_infancia_administrador").getAsInt();
                numero_asesorias_dadas_primera_f_infancia_administrador = jsonObject.get("numero_asesorias_dadas_primera_f_infancia_administrador").getAsInt();
                numero_asesorias_dadas_infancia_m_administrador = jsonObject.get("numero_asesorias_dadas_infancia_m_administrador").getAsInt();
                numero_asesorias_dadas_infancia_f_administrador = jsonObject.get("numero_asesorias_dadas_infancia_f_administrador").getAsInt();
                numero_asesorias_dadas_adolecencia_m_administrador = jsonObject.get("numero_asesorias_dadas_adolecencia_m_administrador").getAsInt();
                numero_asesorias_dadas_adolecencia_f_administrador = jsonObject.get("numero_asesorias_dadas_adolecencia_f_administrador").getAsInt();
                numero_asesorias_dadas_juventud_m_administrador = jsonObject.get("numero_asesorias_dadas_juventud_m_administrador").getAsInt();
                numero_asesorias_dadas_juventud_f_administrador = jsonObject.get("numero_asesorias_dadas_juventud_f_administrador").getAsInt();
                numero_asesorias_dadas_adultez_m_administrador = jsonObject.get("numero_asesorias_dadas_adultez_m_administrador").getAsInt();
                numero_asesorias_dadas_adultez_f_administrador = jsonObject.get("numero_asesorias_dadas_adultez_f_administrador").getAsInt();
                numero_asesorias_dadas_mayor_m_administrador = jsonObject.get("numero_asesorias_dadas_mayor_m_administrador").getAsInt();
                numero_asesorias_dadas_mayor_f_administrador = jsonObject.get("numero_asesorias_dadas_mayor_f_administrador").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Administrador elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_administrador", elemento.id_administrador);
            obj.addProperty("nombre_cuenta_administrador",elemento.nombre_cuenta_administrador);
            obj.addProperty("contrasena_administrador",elemento.contrasena_administrador);
            obj.addProperty("nombres_administrador",elemento.nombres_administrador);
            obj.addProperty("apellidos_administrador",elemento.apellidos_administrador);
            obj.addProperty("fecha_nacimiento_administrador",elemento.fecha_nacimiento_administrador);
            obj.addProperty("sexo_administrador",elemento.sexo_administrador);
            obj.addProperty("estado_administrador",elemento.estado_administrador);
            obj.addProperty("fecha_registro_administrador",elemento.fecha_registro_administrador);
            obj.addProperty("hora_registro_administrador",elemento.hora_registro_administrador);
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
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
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            obj.addProperty("especialidad",especialidad);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }

    public static Administrador getAdministrador_actual() {
        return administrador_actual;
    }

    public static void setAdministrador_actual(Administrador administrador_actual) {
        Gestion_administrador.administrador_actual = administrador_actual;
    }
}
