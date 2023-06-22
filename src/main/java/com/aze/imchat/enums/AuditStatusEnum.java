package com.aze.imchat.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AuditStatusEnum {

    NO_AUDIT(0),
    SUCCESS(1),
    REJECT(2);

    private   Integer status;

    public Integer getStatus() {
        return status;
    }
}
