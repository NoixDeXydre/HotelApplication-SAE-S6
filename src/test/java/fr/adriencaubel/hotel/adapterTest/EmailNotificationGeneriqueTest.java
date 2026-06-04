package fr.adriencaubel.hotel.adapterTest;

import fr.adriencaubel.hotel.adapter.notification.NotificationGeneriqueEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmailNotificationGeneriqueTest {

    @Test
    @DisplayName("sendConfirmation sends an email")
    void sendBookingConfirmationTest() {
        // given
        JavaMailSender mailSender = mock(JavaMailSender.class);
        NotificationGeneriqueEmail sender = new NotificationGeneriqueEmail(mailSender);
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // when
        sender.sendBookingConfirmation("user@example.com");

        // then
        verify(mailSender).send(captor.capture());
        SimpleMailMessage message = captor.getValue();
        assertEquals("noreply@hotel-legacy.com", message.getFrom());
        assertEquals("user@example.com", message.getTo()[0]);
        assertEquals("Hotel Booking Confirmation", message.getSubject());
        assertEquals("Your booking has been confirmed.", message.getText());
    }
}
