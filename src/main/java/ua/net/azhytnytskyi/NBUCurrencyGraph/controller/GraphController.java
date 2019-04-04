package ua.net.azhytnytskyi.NBUCurrencyGraph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.net.azhytnytskyi.NBUCurrencyGraph.client.ApiClient;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrency;

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
@Scope(value = "request")
@RequestMapping("/app")
public class GraphController {

    @Autowired
    ApiClient nbuApiClient;

    private Map<String, Double> exchangeRates;

    @PostConstruct
    public void init(){
        exchangeRates = new LinkedHashMap<>();

    }

    @RequestMapping(method = RequestMethod.GET)
    public String getChartPage(ModelMap modelMap) {

        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getChartDateWithParams(@RequestParam("dateFrom") Date dateFrom,
                                         @RequestParam("dateTo") Date dateTo,
                                         @RequestParam("currency") String currency,
                                         ModelMap modelMap) {

        List<DateCurrency> dateCurrencies = nbuApiClient.getCurrencyVolumeInDateRange(dateFrom,dateTo, currency);

        for (DateCurrency dateCurrency : dateCurrencies){
            exchangeRates.put(dateCurrency.getExchangedate(), dateCurrency.getRate());
        }

        return "index";
    }

    @ModelAttribute("exchange")
    public Map<String, Double> getExchangeRates(){
        return exchangeRates;
    }

}
