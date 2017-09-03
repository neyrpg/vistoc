package br.com.giltech.vistoc.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class UtilDate {


    public static boolean verificaFiltro(Date date, Integer filtro){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -filtro);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        return cal2.compareTo(cal) >= 0;
    }
}
