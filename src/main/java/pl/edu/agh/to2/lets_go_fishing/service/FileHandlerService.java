package pl.edu.agh.to2.lets_go_fishing.service;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@Service
public class FileHandlerService {

    public File showFileChooser(String title, FileChooser.ExtensionFilter... filters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(filters);
        return fileChooser.showOpenDialog(new Stage());
    }

    public File showSaveFileChooser(String title, FileChooser.ExtensionFilter... filters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(filters);
        return fileChooser.showSaveDialog(new Stage());
    }

    public String readFile(File file) throws IOException {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".txt")) {
            return readTxtFile(file);
        } else if (fileName.endsWith(".docx")) {
            return readDocxFile(file);
        } else if (fileName.endsWith(".pdf")) {
            return readPdfFile(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileName);
        }
    }

    private String readTxtFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    private String readDocxFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            return paragraphs.stream().map(XWPFParagraph::getText).collect(Collectors.joining("\n"));
        }
    }

    private String readPdfFile(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    public void saveToFile(File file, String text) throws IOException {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".pdf")) {
            saveToPdf(file, text);
        } else if (fileName.endsWith(".png")) {
            saveToPng(file, text);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileName);
        }
    }

    public void saveToPdf(File file, String text) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            File fontFile = new File("src/main/resources/courier_new.ttf");
            PDType0Font font = PDType0Font.load(document, fontFile);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(40, 800);
                for (String line : text.split("\n")) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -20);
                }
                contentStream.endText();
            }
            document.save(file);
        }
    }

    public void saveToPng(File file, String text) throws IOException {
        Font font = new Font("Courier New", Font.PLAIN, 12);
        int width = 600;
        int height = ((text.split("\n").length + 2) * font.getSize()) + 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
        int x = 10;
        int y = 20;
        for (String line : text.split("\n")) {
            graphics.drawString(line, x, y);
            y += graphics.getFontMetrics().getHeight();
        }
        graphics.dispose();
        ImageIO.write(image, "png", file);
    }
}