package com.example.extra;

import java.util.Calendar;

public class Calculo {
    public static Calendar String_a_Date(String date, String time)
    {
        String[] vectorFecha = date.split ("-");
        String[] vectorHora = time.split (":");
        int año = Integer.parseInt (vectorFecha[0]);
        int mes = Integer.parseInt (vectorFecha[1]);
        int dia = Integer.parseInt (vectorFecha[2]);
        int hora = Integer.parseInt (vectorHora[0]);
        int min = Integer.parseInt (vectorHora[1]);
        int seg = Integer.parseInt (vectorHora[2]);
        Calendar calendar = Calendar.getInstance ();
        calendar.set (año, mes, dia, hora, min, seg);
        return calendar;
    }

    /***
     * Comparar en un string con equals
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static int compararCalendar(Calendar  calendar1, Calendar calendar2)
    {
        if(calendar1.after (calendar2))
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}
