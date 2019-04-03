package ua.net.azhytnytskyi.NBUCurrencyGraph.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DateUtils {

    private LocalDate dateToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date localDateToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String formatDateByPattern(LocalDate date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        return dateFormat.format(localDateToDate(date));
    }

    public List<LocalDate> getLocalDatesListInRange(Date from, Date due){
        List<LocalDate> dates = new ArrayList<>();

        for (LocalDate date = dateToLocalDate(from); !date.isAfter(dateToLocalDate(due)); date = date.plusDays(1)) {
            dates.add(date);
        }

        return dates;
    }
}
