package domain;

import vo.Money;

public class Paperboy {

    private Money total = Money.ZERO;

    public void collectFrom(Customer customer, Money price) {
        Money received = customer.pay(price);   // 고객이 지불
        total = total.plus(received);           // 신문배달부의 수익이 증가
    }

    public Money totalCollected() {
        return total;
    }
}
