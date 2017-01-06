package com.mooneyserver.springamqpretry;

import lombok.Data;


/*
    Sample:
    {
        "id": 1,
        "name": "Bob",
        "anyoneCare": false,
        "amount": 45.456
    }

 */
@Data
public class SampleMessage {

    private Integer id;
    private String name;
    private Boolean anyoneCare;
    private Double amount;
}
