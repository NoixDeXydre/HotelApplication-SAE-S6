package fr.adriencaubel.hotel.serviceTest;

import fr.adriencaubel.hotel.service.EmailSender;
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

class EmailSenderTest {

    @Test
    @DisplayName("sendConfirmation sends an email")
    void sendConfirmationSendsAnEmail() {
        // given
        JavaMailSender mailSender = mock(JavaMailSender.class);
        EmailSender sender = new EmailSender(mailSender);
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // when
        sender.sendConfirmation("user@example.com", "hello");

        // then
        verify(mailSender).send(captor.capture());
        SimpleMailMessage message = captor.getValue();
        assertEquals("noreply@hotel-legacy.com", message.getFrom());
        assertEquals("user@example.com", message.getTo()[0]);
        assertEquals("Hotel Booking Confirmation", message.getSubject());
        assertEquals("hello", message.getText());
    }

    @Test
    @DisplayName("sendConfirmation swallows mail exceptions")
    void sendConfirmationSwallowsMailExceptions() {
        // given
        JavaMailSender mailSender = mock(JavaMailSender.class);
        doThrow(new RuntimeException("boom")).when(mailSender).send(org.mockito.ArgumentMatchers.any(SimpleMailMessage.class));
        EmailSender sender = new EmailSender(mailSender);

        // when
        Runnable action = () -> sender.sendConfirmation("user@example.com", "hello");

        // then
        assertDoesNotThrow(action::run);
    }
}
