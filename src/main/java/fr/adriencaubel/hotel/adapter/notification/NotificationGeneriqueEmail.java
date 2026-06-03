package fr.adriencaubel.hotel.adapter.notification;

import fr.adriencaubel.hotel.domain.ports.out.notification.IEmailNotification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificationGeneriqueEmail implements IEmailNotification {

    private final JavaMailSender mailSender;

    public NotificationGeneriqueEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendBookingConfirmation(String emailTo) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@hotel-legacy.com");
        message.setTo(emailTo);
        message.setSubject("Hotel Booking Confirmation");

        mailSender.send(message);
    }
}
