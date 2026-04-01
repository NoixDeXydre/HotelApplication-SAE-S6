package fr.adriencaubel.hotel.serviceTest;

import fr.adriencaubel.hotel.service.PaymentClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentClientTest {

    @Test
    @DisplayName("authorize rejects null or short credit card")
    void authorizeRejectsInvalidCard() {
        // given
        PaymentClient client = new PaymentClient();

        // when
        boolean nullCard = client.authorize(null, new BigDecimal("10.00"));
        boolean shortCard = client.authorize("123", new BigDecimal("10.00"));

        // then
        assertEquals(false, nullCard);
        assertEquals(false, shortCard);
    }

    @Test
    @DisplayName("authorize rejects non-positive amount")
    void authorizeRejectsNonPositiveAmount() {
        // given
        PaymentClient client = new PaymentClient();

        // when
        boolean zeroAmount = client.authorize("12345678", BigDecimal.ZERO);
        boolean negativeAmount = client.authorize("12345678", new BigDecimal("-1.00"));

        // then
        assertEquals(false, zeroAmount);
        assertEquals(false, negativeAmount);
    }

    @Test
    @DisplayName("authorize follows hash parity for valid inputs")
    void authorizeFollowsHashParityForValidInputs() {
        // given
        PaymentClient client = new PaymentClient();
        String card = "12345678";
        BigDecimal amount = new BigDecimal("10.00");
        boolean expected = (card.hashCode() & 1) == 0;

        // when
        boolean result = client.authorize(card, amount);

        // then
        assertEquals(expected, result);
    }
}
