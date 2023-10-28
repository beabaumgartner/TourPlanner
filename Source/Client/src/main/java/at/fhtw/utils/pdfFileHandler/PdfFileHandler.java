package at.fhtw.utils.pdfFileHandler;

import at.fhtw.TourPlannerClient;
import at.fhtw.exceptions.FailedToCreatePdfFileException;
import at.fhtw.models.Tour;
import at.fhtw.models.TourLog;
import at.fhtw.utils.DateHandler;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PdfFileHandler {
    private static final Logger logger = LogManager.getLogger(PdfFileHandler.class);
    private final String FONT = "./src/main/resources/ARIALUNI.TTF";
    private final String IMAGE_PATH = "data/images/";
    private final Integer FONTSIZE_HEADING = 20;
    private final Integer FONTSIZE_SUBHEADING = 16;
    private final Integer FONTSIZE_SUBSUBHEADING = 14;
    private final Integer FONTSIZE_TEXT = 12;
    private final Text NEWLINE = new Text("\n").setFontSize(3);
    public void createSummarizeReport(java.util.List<Tour> tours, String filename) {
        if(!filename.endsWith(".pdf")) {
            filename += ".pdf";
        }

        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(filename));
            Document document = new Document(pdfDocument);
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler(document));

            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
            document.setFont(font);

            pdfDocument.getDocumentInfo().setTitle("Summarize-Report");
            pdfDocument.getDocumentInfo().setCreator("TourPlanner-Client");

            addSummarizeReportTitle(document);

            addTours(document, tours);

            document.close();

            logger.info("PdfFileHandler.createSummarizeReport() - summarize report created successfully: " + filename);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new FailedToCreatePdfFileException("createSummarizeReport - " + e.getMessage());
        }
    }
    public void createTourReport(Tour tour, String filename) {
        java.util.List<Tour> tours = new ArrayList<>();
        tours.add(tour);
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(filename));
            Document document = new Document(pdfDocument);
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler(document));

            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
            document.setFont(font);

            pdfDocument.getDocumentInfo().setTitle("Tour-Report");
            pdfDocument.getDocumentInfo().setSubject(tour.getName());
            pdfDocument.getDocumentInfo().setCreator("TourPlanner-Client");

            addTourReportTitle(document);

            addTours(document, tours);

            document.close();
            logger.info("PdfFileHandler.createTourReport() - tour report created successfully: " + filename);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new FailedToCreatePdfFileException("createTourReport - " + e.getMessage());
        }
    }
    private void addSummarizeReportTitle(Document document) {
        Paragraph paragraph = new Paragraph();
        paragraph.setFontSize(FONTSIZE_TEXT);

        Text heading = new Text("Summarize-Report").setFontSize(FONTSIZE_HEADING).setBold();
        Text dateOfReport = new Text("Date of report: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));

        paragraph.add(heading);
        paragraph.add(NEWLINE);

        paragraph.add(dateOfReport);

        document.add(paragraph);
    }
    private void addTourReportTitle(Document document) {
        Paragraph paragraph = new Paragraph();
        paragraph.setFontSize(FONTSIZE_TEXT);

        Text heading = new Text("Tour-Report").setFontSize(FONTSIZE_HEADING).setBold();
        Text dateOfReport = new Text("Date of report: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));

        paragraph.add(heading);
        paragraph.add(NEWLINE);

        paragraph.add(dateOfReport);

        document.add(paragraph);
    }
    private void addTours(Document document, java.util.List<Tour> tours) throws MalformedURLException {
        Integer i = 1;
        for (Tour tour : tours) {
            Paragraph paragraph = new Paragraph();
            paragraph.setFontSize(FONTSIZE_TEXT);

            Text subHeading = new Text(i + ") " + tour.getName()).setFontSize(FONTSIZE_SUBHEADING).setBold();
            paragraph.add(subHeading);
            paragraph.add(NEWLINE);
            paragraph.add(NEWLINE);

            if(!tour.getTourLogs().isEmpty()) {
                Integer averageTime = 0;
                Integer averageDifficulty = 0;
                Integer averageRating = 0;

                for(TourLog tourLog : tour.getTourLogs()) {
                    averageTime += tourLog.getTotalTime();
                    averageDifficulty += tourLog.getDifficulty();
                    averageRating += tourLog.getRating();
                }

                averageTime = averageTime / tour.getTourLogs().size();
                averageDifficulty = averageDifficulty / tour.getTourLogs().size();
                averageRating = averageRating / tour.getTourLogs().size();

                paragraph.add("Average Total Time: " + DateHandler.formatSecondsToHHMMSS(averageTime));
                paragraph.add(NEWLINE);

                String averageRatingString = getStringWithStars(averageRating, "Average Rating:");
                paragraph.add(averageRatingString);
                paragraph.add(NEWLINE);

                String averageDifficultyString = getStringWithStars(averageDifficulty, "Average Difficulty:");
                paragraph.add(averageDifficultyString);
                paragraph.add(NEWLINE);
            }

            Text subSubHeading = new Text(i + ".1) Tour Information").setFontSize(FONTSIZE_SUBSUBHEADING).setBold();

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();

            List list = new List();
            list.setListSymbol("• ");

            String popularityString = getStringWithStars(tour.getPopularity(), "Popularity:");
            String childfriendlinessString = getStringWithStars(tour.getChildFriendliness(), "Child Friendliness:");

            list.add("Tour Name: " + tour.getName());
            list.add("Start: " + tour.getStart());
            list.add("Destination: " + tour.getDestination());
            list.add("Transport Type: " + tour.getTransportType());
            list.add("Tour Distance: " + tour.getTourDistance() + "km");
            list.add("Estimated Time: " + DateHandler.formatSecondsToDDHHMM(tour.getEstimatedTime()));
            list.add(popularityString);
            list.add(childfriendlinessString);
            list.add("Tour Description: " + tour.getTourDescription());

            Image image = new Image(ImageDataFactory.create(IMAGE_PATH + tour.getTourId() + ".jpg"));
            image.setWidth(220);
            image.setHorizontalAlignment(HorizontalAlignment.RIGHT);

            Cell infoCell = new Cell().add(new Paragraph(subSubHeading).add(NEWLINE).add(NEWLINE)).add(list).setBorder(Border.NO_BORDER);
            Cell imageCell = new Cell().add(image).setBorder(Border.NO_BORDER);

            table.addCell(infoCell);
            table.addCell(imageCell);

            paragraph.add(table);

            if(!tour.getTourLogs().isEmpty()) {
                subSubHeading = new Text(i + ".2) Tour Logs").setFontSize(FONTSIZE_SUBSUBHEADING).setBold();
                paragraph.add(subSubHeading);
                paragraph.add(NEWLINE);
                paragraph.add(NEWLINE);

                Table tourLogTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();

                Integer k = 1;
                for (TourLog tourLog : tour.getTourLogs()) {
                    Cell tourLogCell = new Cell().setBorder(Border.NO_BORDER);

                    Paragraph tourLogParagraph = new Paragraph();
                    tourLogParagraph.setFontSize(FONTSIZE_TEXT);

                    Text tourLogHeading = new Text("Tour Log #" + k).setBold();
                    tourLogParagraph.add(tourLogHeading);
                    tourLogParagraph.add(NEWLINE);

                    tourLogParagraph.add("Date: " + tourLog.getDate());
                    tourLogParagraph.add(NEWLINE);

                    tourLogParagraph.add("Total Time: " + DateHandler.formatSecondsToHHMMSS(tourLog.getTotalTime()));
                    tourLogParagraph.add(NEWLINE);

                    String ratingString = getStringWithStars(tourLog.getRating(), "Rating:");
                    tourLogParagraph.add(ratingString);
                    tourLogParagraph.add(NEWLINE);

                    String difficultyString = getStringWithStars(tourLog.getDifficulty(), "Difficulty:");
                    tourLogParagraph.add(difficultyString);
                    tourLogParagraph.add(NEWLINE);

                    tourLogParagraph.add(tourLog.getComment());
                    tourLogParagraph.add(NEWLINE);
                    tourLogParagraph.add(NEWLINE);

                    tourLogCell.add(tourLogParagraph);
                    tourLogTable.addCell(tourLogCell);

                    if(k % 2 == 0) {
                        paragraph.add(tourLogTable);
                        tourLogTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();
                    }

                    k = k + 1;
                }

                paragraph.add(tourLogTable);
            }

            i = i + 1;

            document.add(paragraph);

            if(i - 1 != tours.size()) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }
        }
    }
    private String getStringWithStars(Integer integer, String string) {
        for (int j = 0; j < integer; j++) {
            string += " ★";
        }
        for (int j = 5; j > integer; j--) {
            string += " ☆";
        }
        return string;
    }
}
