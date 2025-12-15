@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactMail(ContactRequest req) {

        if (req == null) {
            throw new IllegalArgumentException("Request is null");
        }

        SimpleMailMessage mail = new SimpleMailMessage();

        // ⚠ MUST be Brevo-verified sender
        mail.setFrom("noreply@trivyxa.com");
        mail.setTo("trivyxatech@gmail.com");
        mail.setSubject("📩 New Project Inquiry – TRIVYXA");

        String body =
                "========================================\n" +
                "        🚀 NEW PROJECT INQUIRY\n" +
                "========================================\n\n" +

                "👤 CLIENT DETAILS\n" +
                "• Name: " + safe(req.getName()) + "\n" +
                "• Email: " + safe(req.getEmail()) + "\n" +
                "• Phone: " + safe(req.getPhone(), "Not Provided") + "\n\n" +

                "🧩 PROJECT INFORMATION\n" +
                "• Service: " + safe(req.getService(), "Not Selected") + "\n" +
                "• Budget: " + safe(req.getBudget(), "Not Specified") + "\n\n" +

                "📝 MESSAGE\n" +
                safe(req.getMessage(), "No message provided") + "\n\n" +

                "📅 Submitted via TRIVYXA.COM\n";

        mail.setText(body);

        mailSender.send(mail); // 🚀 this will now work
    }

    private String safe(String value) {
        return value != null ? value : "";
    }

    private String safe(String value, String fallback) {
        return (value != null && !value.isEmpty()) ? value : fallback;
    }
}
