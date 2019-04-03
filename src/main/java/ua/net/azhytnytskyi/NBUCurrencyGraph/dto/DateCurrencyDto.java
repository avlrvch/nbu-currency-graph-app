package ua.net.azhytnytskyi.NBUCurrencyGraph.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DateCurrencyDto {

    private List<DateCurrency> dateCurrencyDateCurrencies;

    public DateCurrencyDto() {
    }

    public List<DateCurrency> getDateCurrencyDateCurrencies() {
        return dateCurrencyDateCurrencies;
    }

    public void setDateCurrencyDateCurrencies(List<DateCurrency> dateCurrencyDateCurrencies) {
        this.dateCurrencyDateCurrencies = dateCurrencyDateCurrencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateCurrencyDto that = (DateCurrencyDto) o;
        return Objects.equals(dateCurrencyDateCurrencies, that.dateCurrencyDateCurrencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateCurrencyDateCurrencies);
    }

}
