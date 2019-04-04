package ua.net.azhytnytskyi.NBUCurrencyGraph.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrency;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrencyDto;
import ua.net.azhytnytskyi.NBUCurrencyGraph.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class NbuApiClient implements ApiClient {

    @Value("${nbu.api.url}")
    private String apiPath;
    @Value("${npu.api.date.format}")
    private String dateFormat;

    private DateUtils dateUtils;

    @Autowired
    public NbuApiClient(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    @Override
    public List<DateCurrency> getCurrencyVolumeInDateRange(Date from, Date due, String currencyCode) {
        List<DateCurrency> dateCurrencies = new ArrayList<>();

        DateCurrencyDto dateCurrencyDto;
        List<LocalDate> datesInRange = dateUtils.getLocalDatesListInRange(from,due);

        for (LocalDate date : datesInRange){
            dateCurrencyDto = getCurrencyVolumeByDay(dateUtils.formatDateByPattern(date,dateFormat));
            dateCurrencies.add(getCurrencyByCode(currencyCode,dateCurrencyDto));
        }

        return dateCurrencies;
    }

    private DateCurrencyDto getCurrencyVolumeByDay(String dateFormatted) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format(apiPath, dateFormatted);

        DateCurrencyDto dateCurrencyDto = new DateCurrencyDto();

        dateCurrencyDto.setDateCurrencyDateCurrencies(
                Arrays.asList(restTemplate
                        .getForEntity(url, DateCurrency[].class)
                        .getBody()));

        return dateCurrencyDto;
    }

    private DateCurrency getCurrencyByCode(String currencyCode, DateCurrencyDto dateCurrencyDto) {

        for (DateCurrency dateCurrency : dateCurrencyDto.getDateCurrencyDateCurrencies()) {
            if (dateCurrency.getCc().equals(currencyCode)) {
                return dateCurrency;
            }
        }

        return null;
    }
}
