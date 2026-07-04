package domain;

import vo.Money;

public class Wallet {

    private Money balance = Money.ZERO;

    public void deposit(Money amount) {
        if (amount.equals(Money.ZERO)) {
            throw new IllegalArgumentException("0원을 입금할 수 없습니다.");
        }
        balance = balance.plus(amount);
    }

    public Money withdraw(Money amount) {
        if (amount.equals(Money.ZERO)) {
            throw new IllegalArgumentException("0원을 출금할 수 없습니다.");
        }
        balance = balance.minus(amount);
        return amount;
    }
}
