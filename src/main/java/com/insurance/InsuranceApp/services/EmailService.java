package com.insurance.InsuranceApp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.insurance.InsuranceApp.dto.ClaimUpdateDto;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            javaMailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (MailException e) {
            logger.error("Error sending email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
    
    public void sendClaimUpdateEmail(String to, String customerName, Long claimId, ClaimUpdateDto updatedDetails) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Your Insurance Claim #" + claimId + " Has Been Updated");
            message.setText(buildClaimUpdateEmailContent(customerName, claimId, updatedDetails));
            javaMailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (MailException e) {
            System.err.println("Error sending email: " + e.getMessage());
            // Log the exception properly using a logging framework (e.g., SLF4J, Logback)
        }
    }

    private String buildClaimUpdateEmailContent(String customerName, Long claimId, ClaimUpdateDto updatedDetails) {
        // You can make this more sophisticated with HTML templates, but for simplicity:
        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(customerName).append(",\n\n");
        content.append("This is to inform you that your insurance claim with ID #").append(claimId).append(" has been updated.\n\n");
        content.append("Here are the updated details:\n");
        // Add relevant updated details from ClaimUpdateDto
        if (updatedDetails.getClaimStatus() != null) {
            content.append("- New Status: ").append(updatedDetails.getClaimStatus()).append("\n");
        }
        if (updatedDetails.getClaimAmount() != null) { // Assuming you have such a field
            content.append("- Updated Amount: ").append(updatedDetails.getClaimAmount()).append("\n");
        }
        // Add other relevant fields from ClaimUpdateDto

        content.append("\nIf you have any questions, please do not hesitate to contact us.\n\n");
        content.append("Sincerely,\n");
        content.append("Buddies Insurance");
        return content.toString();
    }
}
