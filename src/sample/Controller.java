package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAllLines;

public class Controller {

    public AnchorPane anchorPane;
    public ImageView imageView;

    public void emal(ActionEvent actionEvent) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.yandex.ru");
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", 465);
        Session s = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("andreeva.cr1stin@yandex.ru", "Andr!1999");
                    }
                });


        try {
            Message message = new MimeMessage(s);
            message.setFrom(new InternetAddress("andreeva.cr1stin@yandex.ru"));//
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kandreeva952@gmail.com"));
            message.setSubject("тестовое письмо");//тема письма
            message.setText("Проверка отправки письма");//текст письма

            //Multipart multipart = new MimeMultipart();
            // MimeBodyPart attachmentBodyPart= new MimeBodyPart();
            //DataSource source = new FileDataSource("C:\\images\\test.txt"); // ex : "C:\\test.pdf"
            // attachmentBodyPart.setDataHandler(new DataHandler(source));
            // attachmentBodyPart.setFileName("test.txt"); // ex : "test.pdf"
            // multipart.addBodyPart(textBodyPart);  // add the text part
            //  multipart.addBodyPart(attachmentBodyPart); // add the attachement par

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            FileChooser fileChooser = new FileChooser();//класс работы с диалоговым окном
            fileChooser.setTitle("Выберите файл...");//заголовок диалога
            //задает фильтр для указанного расшиерения
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Файл", "*.txt"),
                    new FileChooser.ExtensionFilter("Файл", "*.docx"));
            File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
            String str = file.getPath();//получаем строку с путем к файлу
            System.out.println("" + str);
            //  String file = "C:\\images\\test.txt";
            String fileName = "test.txt";//строка с названием файла
            DataSource source = new FileDataSource(str);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(str);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Письмо отправлено");
        } catch (Exception ex) {
            System.out.println("Ошибка отправки сообщения" + ex);
        }
    }

    public void code(ActionEvent actionEvent) {

        // anchorPane=new AnchorPane();
        //anchorPane.setMinHeight (599);
        //anchorPane.setMaxHeight (500);
        Code128Bean bean = new Code128Bean();
        final int dpi = 50;
        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar
        bean.doQuietZone(false);
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY,
                        false, 0);
        bean.generateBarcode(canvas, "1234567889");
        BufferedImage bufferedImage = canvas.getBufferedImage();
        imageView = new ImageView();
        imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        anchorPane.getChildren().add(imageView);
        // stage.setScene (new Scene(anchorPane));
        //stage.show ();

    }
// перезапись файла и замена строк
    public void files(ActionEvent actionEvent) throws IOException {
    String fileReadName="C:\\images\\test.txt";
    String fileWriteName="C:\\images\\news.txt";
    Path pathRead=Paths.get(fileReadName);
    Path pathWrite=Paths.get(fileWriteName);
        //для чтения
        Scanner scanner = new Scanner(pathRead);
         PrintWriter writer = new PrintWriter(fileWriteName); //для записи
        String search = "Андреева";//что меняем
        String replace = "Тамбов";//на что мняем
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            line = line.replaceAll(search, replace);
            writer.write(line+"\r\n");
        }

        scanner.close();
        writer.flush();

    }




    }


