package pl.szaran.shop_spring.model.enums;

public enum EGuarantee {
    HELP_DESK("Help Desk"), MONEY_BACK("Money back"), SERVICE("Service"), EXCHANGE("Exchange");

    private String fullName;

    EGuarantee(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
