package ua.net.azhytnytskyi.NBUCurrencyGraph.client;

import org.springframework.stereotype.Component;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrency;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrencyDto;

import java.util.Date;
import java.util.List;

public interface ApiClient {

    List<DateCurrency> getCurrencyVolumeInDateRange(Date from, Date due, String currencyCode);
}
