package com.comfacesar.extra;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calculo {
    public final int SEGUNDO = 1;
    public final int MINUTO = 2;
    public final int HORA = 3;
    public final int DIA = 4;
    public Calendar String_a_Date(String date, String time)
    {
        String[] vectorFecha = date.split ("-");
        String[] vectorHora = time.split (":");
        int año = Integer.parseInt (vectorFecha[0]);
        int mes = Integer.parseInt (vectorFecha[1]);
        int dia = Integer.parseInt (vectorFecha[2]);
        int hora = Integer.parseInt (vectorHora[0]);
        int min = Integer.parseInt (vectorHora[1]);
        int seg = Integer.parseInt (vectorHora[2]);
        Calendar calendar = new GregorianCalendar();
        calendar.set (año, mes, dia, hora, min, seg);
        return calendar;
    }

    public String fechaFormatoHace(Calendar fechaInicio, Calendar fechaFin, Calendar c)
    {
        c.setTimeInMillis(fechaInicio.getTime().getTime() - fechaFin.getTime().getTime());
        if(fechaInicio.get (Calendar.YEAR) == fechaFin.get (Calendar.YEAR) )
        {
            if(fechaInicio.get (Calendar.MONTH) == fechaFin.get (Calendar.MONTH) )
            {
                if(fechaInicio.get (Calendar.DAY_OF_MONTH) == (fechaFin.get (Calendar.DAY_OF_MONTH)) )
                {
                    if(fechaInicio.get (Calendar.HOUR_OF_DAY) == (fechaFin.get (Calendar.HOUR_OF_DAY)) )
                    {
                        if(fechaInicio.get (Calendar.MINUTE) == fechaFin.get (Calendar.MINUTE) )
                        {
                            return "hace unos segundos";
                        }
                        else
                        {
                            int minuto = fechaInicio.get (Calendar.MINUTE) - fechaFin.get (Calendar.MINUTE);
                            if(minuto == 1)
                            {
                                if(fechaFin.get (Calendar.SECOND) >= fechaInicio.get (Calendar.SECOND))
                                {
                                    return "hace unos segundos";
                                }
                                else
                                {
                                    return "hace 1 minuto";
                                }
                            }
                            else
                            {
                                return "hace " + minuto + " minutos";
                            }
                        }
                    }
                    else
                    {
                        int horas = fechaInicio.get (Calendar.HOUR_OF_DAY)  - fechaFin.get (Calendar.HOUR_OF_DAY);
                        if(horas == 1)
                        {
                            if(fechaFin.get (Calendar.MINUTE) >= fechaInicio.get (Calendar.MINUTE))
                            {
                                return "hace " + ((fechaInicio.get(Calendar.MINUTE) - fechaFin.get(Calendar.MINUTE)) + 60) + " minutos";
                            }
                            else
                            {
                                return "hace 1 hora";
                            }
                        }
                        else
                        {
                            return "hace " + horas + " hora";
                        }
                    }
                }
                else
                {
                    int dias = fechaInicio.get (Calendar.DAY_OF_MONTH)  - fechaFin.get (Calendar.DAY_OF_MONTH);
                    if(dias == 1)
                    {
                        if(fechaFin.get (Calendar.MINUTE) >= fechaInicio.get (Calendar.MINUTE))
                        {
                            return "hace " + ((fechaInicio.get(Calendar.HOUR_OF_DAY) - fechaFin.get(Calendar.HOUR_OF_DAY)) + 24) + " horas";
                        }
                        else
                        {
                            return "hace 1 dia";
                        }
                    }
                    else
                    {
                        return "hace " + dias + " dias";
                    }
                }
            }
            else
            {
                int meses = fechaInicio.get (Calendar.DAY_OF_MONTH)  - fechaFin.get (Calendar.DAY_OF_MONTH);
                if(meses == 1)
                {
                    if(fechaFin.get (Calendar.DAY_OF_MONTH) >= fechaInicio.get (Calendar.DAY_OF_MONTH))
                    {
                        return "hace " + ((fechaInicio.get(Calendar.DAY_OF_MONTH) - fechaFin.get(Calendar.DAY_OF_MONTH)) + 31) + " dias";
                    }
                    else
                    {
                        return "hace 1 mes";
                    }
                }
                else
                {
                    return "hace " + meses + " meses";
                }
            }
        }
        return "Hace meses";
    }

    public int por(Calendar fechaInicio, Calendar fechaFin, Calendar c)
    {
        c.setTimeInMillis(fechaInicio.getTime().getTime() - fechaFin.getTime().getTime());
        if(fechaInicio.get (Calendar.YEAR) == fechaFin.get (Calendar.YEAR) ) {
            if (fechaInicio.get(Calendar.MONTH) == fechaFin.get(Calendar.MONTH)) {
                if (fechaInicio.get(Calendar.DAY_OF_MONTH) == (fechaFin.get(Calendar.DAY_OF_MONTH))) {
                    if (fechaInicio.get(Calendar.HOUR_OF_DAY) == (fechaFin.get(Calendar.HOUR_OF_DAY))) {
                        if (fechaInicio.get(Calendar.MINUTE) == fechaFin.get(Calendar.MINUTE)) {
                            return SEGUNDO;
                        }
                        else
                        {
                            int minuto = fechaInicio.get(Calendar.MINUTE) - fechaFin.get(Calendar.MINUTE);
                            if (minuto == 1) {
                                if (fechaFin.get(Calendar.SECOND) >= fechaInicio.get(Calendar.SECOND)) {
                                    return SEGUNDO;
                                } else {
                                    return MINUTO;
                                }
                            } else {
                                return MINUTO;
                            }
                        }
                    } else if (fechaInicio.get(Calendar.HOUR_OF_DAY) != fechaFin.get(Calendar.HOUR_OF_DAY)) {
                        int horas = fechaInicio.get(Calendar.HOUR_OF_DAY) - fechaFin.get(Calendar.HOUR_OF_DAY) + 24;
                        if (horas == 1) {
                            if (fechaFin.get(Calendar.MINUTE) >= fechaInicio.get(Calendar.MINUTE)) {
                                return MINUTO;
                            } else {
                                return HORA;
                            }
                        } else {
                            return HORA;
                        }
                    }
                } else if (fechaInicio.get(Calendar.DAY_OF_MONTH) != fechaFin.get(Calendar.DAY_OF_MONTH)) {
                    int dias = fechaInicio.get(Calendar.DAY_OF_MONTH) - fechaFin.get(Calendar.DAY_OF_MONTH);
                    if (dias == 1) {
                        if (fechaFin.get(Calendar.HOUR_OF_DAY) >= fechaInicio.get(Calendar.HOUR_OF_DAY)) {
                            return HORA;
                        } else {
                            return DIA;
                        }
                    } else {
                        return DIA;
                    }
                }
            }
        }
        return DIA;
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
