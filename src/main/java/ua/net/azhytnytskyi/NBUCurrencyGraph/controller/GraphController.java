package ua.net.azhytnytskyi.NBUCurrencyGraph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.net.azhytnytskyi.NBUCurrencyGraph.client.NbuApiClient;
import ua.net.azhytnytskyi.NBUCurrencyGraph.dto.DateCurrency;

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
@Scope(value = "request")
@RequestMapping("/app")
public class GraphController {

    @Autowired
    NbuApiClient nbuApiClient;

    private Map<String, Double> exchangeRates;
    private String chartTitle;

    @PostConstruct
    public void init(){
        exchangeRates = new LinkedHashMap<>();
        chartTitle = "";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getChartPage(ModelMap modelMap) {

        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, params = {"dateFrom","dateTo","currency"})
    public String getChartDateWithParams(@RequestParam("dateFrom") Date dateFrom,
                                         @RequestParam("dateTo") Date dateTo,
                                         @RequestParam("currency") String currency,
                                         ModelMap modelMap) {

        chartTitle = currency + " to UAH";
        List<DateCurrency> dateCurrencies = nbuApiClient.getCurrencyVolumeInDateRange(dateFrom,dateTo, currency);

        for (DateCurrency dateCurrency : dateCurrencies){
            exchangeRates.put(dateCurrency.getExchangedate(), dateCurrency.getRate());
        }

        return "index";
    }

    @ModelAttribute("chartTitle")
    public String getChartTitle(){
        return chartTitle;
    }

    @ModelAttribute("exchange")
    public Map<String, Double> getExchangeRates(){
        return exchangeRates;
    }

}
