package ua.net.azhytnytskyi.NBUCurrencyGraph.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrency;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrencyDto;
import ua.net.azhytnytskyi.NBUCurrencyGraph.utils.DateUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

        List<LocalDate> datesInRange = dateUtils.getLocalDatesListInRange(from, due);

        return datesInRange.stream()
                .map(date -> dateUtils.formatDateByPattern(date, dateFormat))
                .map(this::getCurrencyVolumeByDay)
                .map(dateCurrencyDto -> getCurrencyByCode(currencyCode, dateCurrencyDto))
                .collect(Collectors.toList());
    }

    private DateCurrencyDto getCurrencyVolumeByDay(String dateFormatted) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format(apiPath, dateFormatted);

        DateCurrencyDto dateCurrencyDto = new DateCurrencyDto();

        dateCurrencyDto.setDateCurrencyDateCurrencies(
                Arrays.asList(Objects.requireNonNull(restTemplate
                        .getForEntity(url, DateCurrency[].class)
                        .getBody())));

        return dateCurrencyDto;
    }

    private DateCurrency getCurrencyByCode(String currencyCode, DateCurrencyDto dateCurrencyDto) {

        return dateCurrencyDto.getDateCurrencyDateCurrencies().stream()
                .filter(dateCurrency -> dateCurrency.getCc().equals(currencyCode))
                .findFirst()
                .orElse(null);
    }
}
