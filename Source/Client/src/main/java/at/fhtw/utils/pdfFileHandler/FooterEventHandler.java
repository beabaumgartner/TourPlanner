package at.fhtw.utils.pdfFileHandler;


import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.properties.TextAlignment;

class FooterEventHandler implements IEventHandler {
    Document doc;
    public FooterEventHandler(Document doc) {
        this.doc = doc;
    }
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        int pageNumber = pdfDoc.getPageNumber(page);
        Rectangle pageSize = page.getPageSize();

        PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdfDoc);

        float x = pageSize.getWidth() / 2;
        float y = pageSize.getBottom() + 15;

        new Canvas(pdfCanvas, pageSize).showTextAligned(String.valueOf(pageNumber), x, y, TextAlignment.CENTER);
    }
}
