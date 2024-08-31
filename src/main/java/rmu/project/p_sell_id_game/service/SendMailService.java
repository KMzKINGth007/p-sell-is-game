package rmu.project.p_sell_id_game.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rmu.project.p_sell_id_game.model.OrderResponseModel;
import rmu.project.p_sell_id_game.utils.Constants;
import rmu.project.p_sell_id_game.utils.SendEmailUtils;

import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;

@Service
public class SendMailService {

    @Autowired
    private SendEmailUtils sendEmailUtils;

    public void sendOrderMail(OrderResponseModel order, String customMessage) {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String emailTemplatePath = path + File.separator + Constants.PATH_FOLDER_EMAIL + File.separator + "sendmail.txt";
            
            File file = new File(emailTemplatePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            reader.close();

            String emailContent = stringBuilder.toString();
            emailContent = replacePlaceholders(emailContent, order, customMessage);
            
            sendEmailUtils.sendMail(order.getUserDetail().getEmail(), "Order Confirmation", emailContent);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private String replacePlaceholders(String content, OrderResponseModel order, String customMessage) {
        content = content.replace("{CUSTOM_MESSAGE}", customMessage);
        content = content.replace("{ORDER_ID}", String.valueOf(order.getOrderId()));
        content = content.replace("{TOTAL_AMOUNT}", order.getTotalAmount().toString());
        content = content.replace("{ORDER_DATE}", order.getOrderDate().toString());
        content = content.replace("{USER_NAME}", order.getUserDetail().getFirstName() + " " + order.getUserDetail().getLastName());
        content = content.replace("{ORDER_ITEMS}", formatOrderItems(order));
        return content;
    }

    private String formatOrderItems(OrderResponseModel order) {
        StringBuilder items = new StringBuilder();
        for (OrderResponseModel.OrderItem item : order.getItems()) {
            items.append("Product ID: ").append(item.getProductId())
                 .append(", Quantity: ").append(item.getQuantity())
                 .append(", Price: ").append(item.getPrice())
                 .append("<br>");
        }
        return items.toString();
    }
}