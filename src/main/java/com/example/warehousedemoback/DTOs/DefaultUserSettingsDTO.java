package com.example.warehousedemoback.DTOs;

public class DefaultUserSettingsDTO {

    int defaultFreeItemLimit;
    Double defaultItemCost;

    public int getDefaultFreeItemLimit() {
        return defaultFreeItemLimit;
    }

    public void setDefaultFreeItemLimit(int defaultFreeItemLimit) {
        this.defaultFreeItemLimit = defaultFreeItemLimit;
    }

    public Double getDefaultItemCost() {
        return defaultItemCost;
    }

    public void setDefaultItemCost(Double defaultItemCost) {
        this.defaultItemCost = defaultItemCost;
    }
}
