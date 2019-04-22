package com.comfacesar.extra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private boolean validarFechaSegundaCapa(Fecha fecha)
    {
        int[] diasMes= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ( fecha.año < 1900 ) {
            throw new IllegalArgumentException(
                    "Solo se comprueban fechas del año 1900 o posterior");
        }
        if ( fecha.mes<1 || fecha.mes>12 )
            return false;
        // Para febrero y bisiesto el limite es 29
        if ( fecha.mes==2 && fecha.año%4==0 )
            return fecha.dia>=1 && fecha.dia<=29;
        return fecha.dia>=0 && fecha.dia<=diasMes[fecha.mes-1];
    }

    private class Fecha
    {
        public Fecha(int año, int mes, int dia) {
            this.año = año;
            this.mes = mes;
            this.dia = dia;
        }

        private int año;
        private int mes; // 1 a 12
        private int dia; // 1 a 31
    }

    public boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(true);
            formatoFecha.parse(fecha);
            String[] array = fecha.split("-");
            try
            {
                Fecha fecha1 = new Fecha(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
                return validarFechaSegundaCapa(fecha1);
            }
            catch (NumberFormatException exc)
            {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }
}
