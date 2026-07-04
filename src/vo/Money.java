package vo;

public record Money(int amount) {

    public static final Money ZERO = new Money(0);

    public Money {
        if (amount < 0) {
            throw new IllegalArgumentException("금액은 0보다 작을 수 없습니다: " + amount);
        }
    }

    public Money plus(Money other) {
        return new Money(amount + other.amount);
    }

    public Money minus(Money other) {
        int result = amount - other.amount;
        if (result < 0) {
            throw new IllegalArgumentException("결과가 음수일 수 없습니다.");
        }
        return new Money(result);
    }
}
