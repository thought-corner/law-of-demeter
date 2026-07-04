package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vo.Money;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    @DisplayName("입금한 만큼 출금할 수 있고, 출금하면 꺼낸 금액을 돌려준다")
    void 입금한_만큼_출금하면_꺼낸_금액을_돌려준다() {
        // given
        Wallet wallet = new Wallet();
        wallet.deposit(new Money(5000));

        // when
        Money withdrawn = wallet.withdraw(new Money(5000));

        // then
        assertEquals(new Money(5000), withdrawn);
    }

    @Test
    @DisplayName("잔액보다 큰 금액은 출금할 수 없다 (불변식 1)")
    void 잔액보다_큰_금액은_출금에_실패한다() {
        // given
        Wallet wallet = new Wallet();
        wallet.deposit(new Money(1000));
        Money overBalance = new Money(3000);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> wallet.withdraw(overBalance));
    }

    @Test
    @DisplayName("잔액과 정확히 같은 금액을 출금하면 잔액이 0이 된다 (경계값)")
    void 잔액과_같은_금액을_출금하면_잔액이_0이_된다() {
        // given
        Wallet wallet = new Wallet();
        wallet.deposit(new Money(3000));
        Money one = new Money(1);

        // when
        wallet.withdraw(new Money(3000));

        // then — getter가 없으니 '더 이상 못 꺼낸다'로 잔액 0을 검증
        assertThrows(IllegalArgumentException.class, () -> wallet.withdraw(one));
    }

    @Test
    @DisplayName("0원은 입금할 수 없다")
    void 금액이_0이면_입금할_수_없다() {
        // given
        Wallet wallet = new Wallet();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> wallet.deposit(Money.ZERO));
    }

    @Test
    @DisplayName("0원은 출금할 수 없다")
    void 금액이_0이면_출금할_수_없다() {
        // given
        Wallet wallet = new Wallet();
        wallet.deposit(new Money(1000));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> wallet.withdraw(Money.ZERO));
    }
}