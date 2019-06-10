package com.leo.springboot.domain;

import com.leo.springboot.validation.constraints.ValidCardNumber;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class User {
    @Max(10000)
    private long id;
    @NotNull
    private String name;
    //卡号 --LEO-123456
    @NotNull
    @ValidCardNumber
    private String cardNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
