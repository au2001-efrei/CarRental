package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoyaltySubscription extends Observable<LoyaltySubscription> {

    private final int id;
    private Customer customer;
    private LoyaltyProgram loyaltyProgram;
    private Date subscriptionDate;

    public LoyaltySubscription(int id, Customer customer, LoyaltyProgram loyaltyProgram, Date subscriptionDate) {
        this.id = id;
        this.customer = customer;
        this.loyaltyProgram = loyaltyProgram;
        this.subscriptionDate = subscriptionDate != null ? new Date(subscriptionDate.getTime()) : null;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        notifyObservers();
    }

    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgram;
    }

    public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        this.loyaltyProgram = loyaltyProgram;
        notifyObservers();
    }

    public Date getSubscriptionDate() {
        return subscriptionDate != null ? new Date(subscriptionDate.getTime()) : null;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate != null ? new Date(subscriptionDate.getTime()) : null;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("customer", getCustomer() != null ? getCustomer().toJSON() : null);
        json.put("loyalty_program", getLoyaltyProgram() != null ? getLoyaltyProgram().toJSON() : null);
        json.put("subscription_date", getSubscriptionDate() != null ? getSubscriptionDate().getTime() : null);
        return json;
    }

}
