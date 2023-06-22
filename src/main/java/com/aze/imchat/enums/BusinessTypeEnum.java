package com.aze.imchat.enums;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public enum BusinessTypeEnum {

    CONTACT(0),
    QUN(1);


    private Integer  type;

    public Integer getType() {
        return type;
    }

}
