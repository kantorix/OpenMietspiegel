package de.htw.berlin.opendata.openmietspiegel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFTextParser {

	public String convertPDFToString(String fileName)
	{
		return convertPDFToString(fileName, 2, 2);
	}
	
	public String convertPDFToString(String fileName, int startPage, int endPage) {
		PDFParser parser;
		String parsedText = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		File file = new File(fileName);
		if (!file.isFile()) {
			System.err.println("File " + fileName + " does not exist.");
			return null;
		}
		try {
			parser = new PDFParser(new FileInputStream(file));
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e.getMessage());
			return null;
		}
		try {
			parser.parse();
			cosDoc = parser.getDocument();
			PDFTextStripper pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(startPage);
			pdfStripper.setEndPage(endPage);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.err.println("An exception occured in parsing the PDF Document." + e.getMessage());
		} finally {
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parsedText;
	}
}