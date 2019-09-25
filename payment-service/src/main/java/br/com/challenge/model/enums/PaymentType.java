package br.com.challenge.model.enums;

public enum PaymentType {
    CREDIT_CARD(1),
    BOLETO(2);

    private Integer type;

    PaymentType(Integer type){
        this.type = type;
    }

    public Integer getTypeCod(){
        return this.type;
    }

    public static PaymentType toEnum(Integer type){
        if(type == null || type < 0)
            throw new IllegalArgumentException("Invalid paymentType!");

        for(PaymentType paymentType : PaymentType.values()){
            if(type.equals(paymentType.getTypeCod()))
                return paymentType;
        }

        throw new IllegalArgumentException("payment type does not exist!");
    }
}
