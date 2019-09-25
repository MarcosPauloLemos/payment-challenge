package br.com.challenge.model.enums;

public enum PaymentStatus {

    APPROVED(1),
    REFUSED(2),
    WAITING_PROCESS(3),
    WAITING_PAY(4),
    CANCELED(5);

    private Integer statusCod;

    public Integer getStatusCod(){
        return  statusCod;
    }

    PaymentStatus(Integer statusCod){
        this.statusCod = statusCod;
    }

    public static PaymentStatus toEnum(Integer statusCod){
        if(statusCod == null || statusCod < 0)
            throw new IllegalArgumentException("Invalid statusCod!");

        for(PaymentStatus paymentStatus : PaymentStatus.values()){
            if(statusCod.equals(paymentStatus.getStatusCod()))
                return paymentStatus;
        }

        throw new IllegalArgumentException("StatusCod does not exist!");
    }
}
