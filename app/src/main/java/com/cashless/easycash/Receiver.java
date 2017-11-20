package com.cashless.easycash;


import java.util.UUID;

public class Receiver {
    private String id;
    private String name, vpa;
    public Receiver(String id, String name, String vpa){
        this.id=id;
        this.name=name;
        this.vpa=vpa;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVpa() {
        return vpa;
    }
}
