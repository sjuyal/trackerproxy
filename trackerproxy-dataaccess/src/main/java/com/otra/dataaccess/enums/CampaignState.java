package com.otra.dataaccess.enums;

public enum CampaignState {
    DRAFT("DRAFT"),
    SCHEDULED("SCHEDULED"),
    READY("READY"),
    LIVE("LIVE"),
    PAUSED("paused"),
    COMPLETED("COMPLETED"),
    CLOSED("CLOSED");
    private String status;

    CampaignState(String status) {
        this.status = status;
    }

    public static CampaignState getCampaignState(String status) {
        for (CampaignState value : CampaignState.values()) {
            if (value.toString().equals(status)) {
                return value;
            }
        }
        return null;
    }
}
