package net.efrei.s6.databases.aurelducyoni.controllers;

import net.efrei.s6.databases.aurelducyoni.dbms.Datastore;
import net.efrei.s6.databases.aurelducyoni.models.*;

import java.util.*;
import java.util.stream.Collectors;

public class StatsController {

    private final Datastore datastore;

    public StatsController(Datastore datastore) {
        this.datastore = datastore;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, ?>> getGoldCustomerList() {
        List<Customer> customers = datastore.getCustomerList();
        List<Quote> quotes = datastore.getQuoteList();

        Map<Integer, Integer> counts = new HashMap<>();
        Map<Integer, Double> spendings = new HashMap<>();
        for (Quote quote : quotes) {
            int count = counts.getOrDefault(quote.getReservation().getCustomer().getId(), 0);
            counts.put(quote.getReservation().getCustomer().getId(), count + 1);

            double spending = spendings.getOrDefault(quote.getReservation().getCustomer().getId(), 0.0);
            spendings.put(quote.getReservation().getCustomer().getId(), spending + quote.getAmount());
        }
        double maxSpending = spendings.values().stream().max(Double::compare).orElse(0.0);
        return customers.stream().filter(c -> spendings.getOrDefault(c.getId(), 0.0) >= maxSpending * 0.8).map(c -> {
            Map<String, Object> json = (Map<String, Object>) c.toJSON();
            json.put("rentals_count", counts.getOrDefault(c.getId(), 0));
            json.put("spending_sum", spendings.getOrDefault(c.getId(), 0.0));
            return json;
        }).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, ?>> getAgenciesTurnovers() {
        List<Agency> agencies = datastore.getAgencyList();
        List<Quote> quotes = datastore.getQuoteList();

        Map<Integer, Integer> counts = new HashMap<>();
        Map<Integer, Double> spendings = new HashMap<>();
        for (Quote quote : quotes) {
            int count = counts.getOrDefault(quote.getReservation().getAgency().getId(), 0);
            counts.put(quote.getReservation().getAgency().getId(), count + 1);

            double spending = spendings.getOrDefault(quote.getReservation().getAgency().getId(), 0.0);
            spendings.put(quote.getReservation().getAgency().getId(), spending + quote.getAmount());
        }
        return agencies.stream().map(a -> {
            Map<String, Object> json = (Map<String, Object>) a.toJSON();
            json.put("rentals_count", counts.getOrDefault(a.getId(), 0));
            json.put("spending_sum", spendings.getOrDefault(a.getId(), 0.0));
            return json;
        }).collect(Collectors.toList());
    }

}
