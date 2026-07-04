package vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("음수로는 생성할 수 없다 (불변식 2)")
    void 음수로_생성하면_예외가_발생한다() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> new Money(-1));
    }

    @Test
    @DisplayName("0원은 생성할 수 있다")
    void 금액이_0이면_생성할_수_있다() {
        // when
        Money zero = new Money(0);

        // then
        assertEquals(Money.ZERO, zero);
    }

    @Test
    @DisplayName("같은 금액이면 값으로 동등하다 (값 객체)")
    void 금액이_같으면_동등하다() {
        // given
        Money a = new Money(1000);
        Money b = new Money(1000);

        // when & then
        assertEquals(a, b);
    }

    @Test
    @DisplayName("plus는 새로운 Money를 반환하고 원본은 바뀌지 않는다")
    void plus는_더한_새_Money를_반환하고_원본은_바뀌지_않는다() {
        // given
        Money base = new Money(1000);

        // when
        Money result = base.plus(new Money(2000));

        // then
        assertEquals(new Money(3000), result);
        assertEquals(new Money(1000), base); // 원본 불변
    }

    @Test
    @DisplayName("minus는 새로운 Money를 반환한다")
    void minus는_뺀_새_Money를_반환한다() {
        // given
        Money base = new Money(3000);

        // when
        Money result = base.minus(new Money(1000));

        // then
        assertEquals(new Money(2000), result);
    }

    @Test
    @DisplayName("결과가 음수가 되는 빼기는 예외 (불변식 1의 토대)")
    void 결과가_음수가_되는_minus는_예외가_발생한다() {
        // given
        Money base = new Money(1000);
        Money tooMuch = new Money(3000);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> base.minus(tooMuch));
    }
}