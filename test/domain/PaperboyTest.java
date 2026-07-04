package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vo.Money;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 요구사항 '완료 기준' 5개 시나리오를 검증한다.
 * 잔액 getter가 없으므로(금지 1), 잔액은 '행동'으로 검증한다.
 */
class PaperboyTest {

    @Test
    @DisplayName("정상 결제: 잔액 10,000원에 3,000원 청구 → 성공, 잔액 7,000원, 총액 3,000원")
    void 수금하면_청구금액만큼_총액이_늘어난다() {
        // given
        Customer customer = new Customer();
        customer.charge(new Money(10000));
        Paperboy paperboy = new Paperboy();
        Money remaining = new Money(7000);
        Money over = new Money(1);

        // when
        paperboy.collectFrom(customer, new Money(3000));

        // then
        assertEquals(new Money(3000), paperboy.totalCollected());
        // 잔액 7,000원 검증: 7,000원까지는 더 낼 수 있고, 그 이상은 실패해야 한다
        assertDoesNotThrow(() -> paperboy.collectFrom(customer, remaining));
        assertThrows(IllegalArgumentException.class, () -> paperboy.collectFrom(customer, over));
        assertEquals(new Money(10000), paperboy.totalCollected());
    }

    @Test
    @DisplayName("잔액 부족: 잔액 1,000원에 3,000원 청구 → 예외, 잔액 유지, 총액 변화 없음")
    void 잔액보다_큰_금액은_수금에_실패하고_총액은_그대로다() {
        // given
        Customer customer = new Customer();
        customer.charge(new Money(1000));
        Paperboy paperboy = new Paperboy();
        Money over = new Money(3000);
        Money affordable = new Money(1000);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> paperboy.collectFrom(customer, over));

        // then — 총액은 안 늘어나고, 잔액 1,000원은 그대로라 1,000원 결제는 성공해야 한다
        assertEquals(Money.ZERO, paperboy.totalCollected());
        assertDoesNotThrow(() -> paperboy.collectFrom(customer, affordable));
        assertEquals(new Money(1000), paperboy.totalCollected());
    }

    @Test
    @DisplayName("다중 수금: A에게 3,000원, B에게 2,000원 → 총액 5,000원")
    void 여러_고객에게_수금하면_총액이_합산된다() {
        // given
        Customer a = new Customer();
        a.charge(new Money(3000));
        Customer b = new Customer();
        b.charge(new Money(2000));
        Paperboy paperboy = new Paperboy();

        // when
        paperboy.collectFrom(a, new Money(3000));
        paperboy.collectFrom(b, new Money(2000));

        // then
        assertEquals(new Money(5000), paperboy.totalCollected());
    }

    @Test
    @DisplayName("경계값: 잔액과 같은 금액 청구 → 성공, 잔액 0")
    void 잔액과_같은_금액은_수금되고_잔액이_0이_된다() {
        // given
        Customer customer = new Customer();
        customer.charge(new Money(3000));
        Paperboy paperboy = new Paperboy();
        Money over = new Money(1);

        // when
        paperboy.collectFrom(customer, new Money(3000));

        // then
        assertEquals(new Money(3000), paperboy.totalCollected());
        // 잔액 0: 이제 어떤 금액도 결제되지 않아야 한다
        assertThrows(IllegalArgumentException.class, () -> paperboy.collectFrom(customer, over));
    }

    @Test
    @DisplayName("입금: 지갑에 5,000원 충전 후 결제 가능해진다")
    void 충전한_뒤에는_그만큼_수금할_수_있다() {
        // given
        Customer customer = new Customer();
        Paperboy paperboy = new Paperboy();
        Money price = new Money(5000);
        // 충전 전에는 결제 불가
        assertThrows(IllegalArgumentException.class, () -> paperboy.collectFrom(customer, price));

        // when
        customer.charge(new Money(5000));

        // then
        assertDoesNotThrow(() -> paperboy.collectFrom(customer, price));
        assertEquals(new Money(5000), paperboy.totalCollected());
    }
}