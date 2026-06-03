package fr.adriencaubel.hotel.domain.ports.out.notification;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailNotification {
    void sendBookingConfirmation(SimpleMailMessage email);
}