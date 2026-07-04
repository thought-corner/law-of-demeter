package domain;

import vo.Money;

public class Customer {

    private final Wallet wallet = new Wallet();

    public void charge(Money amount) {
        wallet.deposit(amount);      // 지갑에 위임
    }

    public Money pay(Money amount) {
        return wallet.withdraw(amount);
    }
}
