package pl.szaran.shop_spring.model.enums;

public enum EPayment {
    CASH("Cash"), CARD("Credit Card"), MONEY_TRANSFER("Money transfer");

    private String fullName;

    EPayment(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }


}
