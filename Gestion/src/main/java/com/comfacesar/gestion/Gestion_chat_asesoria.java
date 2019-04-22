package com.comfacesar.gestion;

import com.comfacesar.modelo.Chat_asesoria;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_chat_asesoria {
    public static ArrayChatCambiado arrayChatCambiado;
    public interface ArrayChatCambiado
    {
        void chatCambiado();
    }
    private static ArrayList<Chat_asesoria> chat_asesorias = null;
    private Chat_asesoria aux = new Chat_asesoria();
    private static ChatAbierto chatAbierto;
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_CHAT_ASESORIA= Propiedades.LLAVE_CHAT_ASESORIA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private String tipo_consulta;
    private String ID_CHAT_ASESORIA = "A";
    private String FECHA_INICIO_ASESORIA = "B";
    private String HORA_INICIO_ASESORIA = "C";
    private String FECHA_CIERRE_ASESORIA = "D";
    private String HORA_CIERRA_CHAT_ASESORIA = "E";
    private String ADMINISTRADOR_CHAT_ASESORIA = "F";
    private String USUARIO_CHAT_ASESORIA = "G";
    private String ESTADO_CERRADO = "H";
    private String TIEMPO_SESION_CHAT_ASESORIA = "I";
    private String ESPECIALIZACION_CHAT_ASESORIA = "J";
    private String ULTIMA_FECHA_ADMINISTRADOR_CHAT_ASESORIA = "K";
    private String ULTIMA_HORA_ADMINISTRADOR_CHAT_ASESORIA = "L";
    private String ULTIMO_MENSAJE_ADMINISTRADOR_CHAT_ASESORIA = "M";
    private String ULTIMA_FECHA_USUARIO_CHAT_ASESORIA = "N";
    private String ULTIMA_HORA_USUARIO_CHAT_ASESORIA = "O";
    private String ULTIMO_MENSAJE_USUARIO_CHAT_ASESORIA = "P";
    private String ULTIMA_FECHA_VISTA_ADMINISTRADOR_CHAT_ASESORIA = "Q";
    private String ULTIMA_HORA_VISTA_ADMINISTRADOR_CHAT_ASESORIA = "R";
    private String ULTIMA_FECHA_VISTA_USUARIO_CHAT_ASESORIA = "S";
    private String ULTIMA_HORA_VISTA_USUARIO_CHAT_ASESORIA = "T";
    private String ULTIMO_MENSAJE_CHAT_ASESORIA = "U";
    private String ULTIMA_FECHA_CHAT_ASESORIA = "V";
    private String ULTIMA_HORA_CHAT_ASESORIA = "X";
    private String TIPO_CONSULTA = "TC";
    //############################################################################################\\
    //###############################PROPIEDADES DE RELACION######################################\\
    private final String USUARIO = "usuario";
    private final String ADMINISTRADOR = "administrador";
    private final String ESPECIALIDAD = "especialidad";
    //############################################################################################\\
    //###############################CONSULTA#####################################################\\
    private final String VISTA_POR_ADMINISTRADOR = "vista_por_administrador";
    private final String CHAT_ASESORIA_POR_ID = "chat_asesoria_por_id";
    private final String CONSULTAR_POR_ADMINISTRADOR = "consultar_por_administrador";

    public interface ChatAbierto
    {
        void abierto(int id_chat);
    }

    public static void setChatAbierto(ChatAbierto chatAbierto) {
        Gestion_chat_asesoria.chatAbierto = chatAbierto;
    }

    public static void chat_abiero(int id_chat)
    {
        chatAbierto.abierto(id_chat);
    }

    public HashMap<String, String> vista_por_administrador( int id_chat)
    {
        aux.id_chat_asesoria = id_chat;
        tipo_consulta = VISTA_POR_ADMINISTRADOR;
        return construir_parametros(aux);
    }

    public HashMap<String, String> chat_asesoria_por_id(int id_chat)
    {
        aux.id_chat_asesoria = id_chat;
        tipo_consulta = CHAT_ASESORIA_POR_ID;
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_por_administrador(int administrador)
    {
        aux.administrador_chat_asesoria = administrador;
        tipo_consulta = CONSULTAR_POR_ADMINISTRADOR;
        HashMap hashMap = construir_parametros(aux);
        return hashMap;
    }

    public ArrayList<Chat_asesoria> generar_json(String respuesta)
    {
        ArrayList<Chat_asesoria> lista_elementos = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(respuesta).getAsJsonArray();
            for(JsonElement element : array )
            {
                Chat_asesoria chat_asesoria = agregar_elemento(element.getAsJsonObject());
                lista_elementos.add (chat_asesoria);
            }
        }
        catch(JsonSyntaxException | IllegalStateException | NullPointerException e)
        {
            lista_elementos = new ArrayList<>();
        }
        return lista_elementos;
    }

    private Chat_asesoria agregar_elemento(final JsonObject jsonObject)
    {
        return new Chat_asesoria(){{
            try {
                id_chat_asesoria = jsonObject.get(ID_CHAT_ASESORIA).getAsInt();
                fecha_inicio_asesoria = jsonObject.get(FECHA_INICIO_ASESORIA).getAsString();
                hora_inicio_asesoria = jsonObject.get(HORA_INICIO_ASESORIA).getAsString();
                if(!jsonObject.get(FECHA_CIERRE_ASESORIA).isJsonNull())
                {
                    fecha_cierre_asesoria = jsonObject.get(FECHA_CIERRE_ASESORIA).getAsString();
                }
                else
                {
                    fecha_cierre_asesoria = "";
                }
                if(!jsonObject.get(HORA_CIERRA_CHAT_ASESORIA).isJsonNull())
                {
                    hora_cierra_chat_asesoria = jsonObject.get(HORA_CIERRA_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    hora_cierra_chat_asesoria = "";
                }
                administrador_chat_asesoria = jsonObject.get(ADMINISTRADOR_CHAT_ASESORIA).getAsInt();
                usuario_chat_asesoria = jsonObject.get(USUARIO_CHAT_ASESORIA).getAsInt();
                estado_cerrado = jsonObject.get(ESTADO_CERRADO).getAsInt();
                tiempo_sesion_chat_asesoria = jsonObject.get(TIEMPO_SESION_CHAT_ASESORIA).getAsString();
                especializacion_chat_asesoria = jsonObject.get(ESPECIALIZACION_CHAT_ASESORIA).getAsInt();
                if(!jsonObject.get(ULTIMA_FECHA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_administrador_chat_asesoria = jsonObject.get(ULTIMA_FECHA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_administrador_chat_asesoria = jsonObject.get(ULTIMA_HORA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMO_MENSAJE_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultimo_mensaje_administrador_chat_asesoria = jsonObject.get(ULTIMO_MENSAJE_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_FECHA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_usuario_chat_asesoria = jsonObject.get(ULTIMA_FECHA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_usuario_chat_asesoria = jsonObject.get(ULTIMA_HORA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMO_MENSAJE_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultimo_mensaje_usuario_chat_asesoria = jsonObject.get(ULTIMO_MENSAJE_USUARIO_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_FECHA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_vista_administrador_chat_asesoria = jsonObject.get(ULTIMA_FECHA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_vista_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_vista_administrador_chat_asesoria = jsonObject.get(ULTIMA_HORA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_vista_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_FECHA_VISTA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_vista_usuario_chat_asesoria = jsonObject.get(ULTIMA_FECHA_VISTA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_vista_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_VISTA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_vista_usuario_chat_asesoria = jsonObject.get(ULTIMA_HORA_VISTA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_vista_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(USUARIO).isJsonNull())
                {
                    usuario = jsonObject.get(USUARIO).getAsString();
                }
                if(!jsonObject.get(ADMINISTRADOR).isJsonNull())
                {
                    administrador = jsonObject.get(ADMINISTRADOR).getAsString();
                }
                if(!jsonObject.get(ESPECIALIDAD).isJsonNull())
                {
                    especialidad = jsonObject.get(ESPECIALIDAD).getAsString();
                }
                if(!jsonObject.get(ULTIMO_MENSAJE_CHAT_ASESORIA).isJsonNull())
                {
                    ultimo_mensaje_chat_asesoria = jsonObject.get(ULTIMO_MENSAJE_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_FECHA_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_chat_asesoria = jsonObject.get(ULTIMA_FECHA_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_HORA_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_chat_asesoria = jsonObject.get(ULTIMA_HORA_CHAT_ASESORIA).getAsString();
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Chat_asesoria elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(ID_CHAT_ASESORIA, elemento.id_chat_asesoria);
            obj.addProperty(FECHA_INICIO_ASESORIA, elemento.fecha_inicio_asesoria);
            obj.addProperty(HORA_INICIO_ASESORIA, elemento.hora_inicio_asesoria);
            obj.addProperty(FECHA_CIERRE_ASESORIA, elemento.fecha_cierre_asesoria);
            obj.addProperty(HORA_CIERRA_CHAT_ASESORIA, elemento.hora_cierra_chat_asesoria);
            obj.addProperty(ADMINISTRADOR_CHAT_ASESORIA, elemento.administrador_chat_asesoria);
            obj.addProperty(USUARIO_CHAT_ASESORIA, elemento.usuario_chat_asesoria);
            obj.addProperty(ESTADO_CERRADO, elemento.estado_cerrado);
            obj.addProperty(TIEMPO_SESION_CHAT_ASESORIA, elemento.tiempo_sesion_chat_asesoria);
            obj.addProperty(ESPECIALIZACION_CHAT_ASESORIA, elemento.especializacion_chat_asesoria);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_CHAT_ASESORIA);
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

    public static void setChat_asesorias(ArrayList<Chat_asesoria> chat_asesorias_aux)
    {
        boolean cambio = false;
        if(chat_asesorias_aux != null)
        {
            if(chat_asesorias != null)
            {
                if(chat_asesorias.size() < chat_asesorias_aux.size())
                {
                    cambio = true;
                }
                for(Chat_asesoria item :  chat_asesorias_aux)
                {
                    Chat_asesoria chat_asesoria = buscarChatAsesoria(item.id_chat_asesoria);
                    if(chat_asesoria != null)
                    {
                        try
                        {
                            if(!chat_asesoria.ultima_fecha_chat_asesoria.equals(item.ultima_fecha_chat_asesoria) || !chat_asesoria.ultima_hora_chat_asesoria.equals(item.ultima_hora_chat_asesoria))
                            {
                                cambio = true;
                            }
                        }
                        catch(NullPointerException exc)
                        {

                        }
                    }
                }
            }
            chat_asesorias = new ArrayList<>();
            for(Chat_asesoria item : chat_asesorias_aux)
            {
                addChat_asesoria(item);
            }
            if(cambio)
            {
                if(Gestion_administrador.getAdministrador_actual() != null)
                {
                    if(arrayChatCambiado != null)
                    {
                        arrayChatCambiado.chatCambiado();
                    }
                }
            }
        }
        else
        {
            chat_asesorias = null;
        }
    }

    public static void addChat_asesoria(Chat_asesoria chat_asesoria) {
        if (buscarChatAsesoria(chat_asesoria.id_chat_asesoria) == null) {
            chat_asesorias.add(chat_asesoria);
        }
    }

    private static Chat_asesoria buscarChatAsesoria(int id)
    {
        if(chat_asesorias != null)
        {
            for(Chat_asesoria item : chat_asesorias)
            {
                if(item.id_chat_asesoria == id)
                {
                    return item;
                }
            }
        }
        return  null;
    }

    public static ArrayList<Chat_asesoria> getChat_asesorias() {
        return chat_asesorias;
    }
}
