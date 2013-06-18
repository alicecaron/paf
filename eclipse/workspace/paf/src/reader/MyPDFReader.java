package reader;
import java.io.IOException;
 
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.PDFTextStream;
 
public class MyPDFReader {

	private String filename;
	private StringBuilder text;
	
    public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public StringBuilder getText() {
		return text;
	}

	public void setText(StringBuilder text) {
		this.text = text;
	}

	public MyPDFReader(String pdfFilePath) throws IOException {
        PDFTextStream pdfts = new PDFTextStream(pdfFilePath);
        this.text = new StringBuilder(1024);
        pdfts.pipe(new OutputTarget(text));
        pdfts.close();        
    }
}