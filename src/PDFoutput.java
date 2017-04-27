
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;





public class PDFoutput {
	
	


    
    
    private static BaseColor streamedGameColour = new BaseColor(152,251,152);  //WebColors.getRGBColor("#98FB98");
    
    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL);
    private static Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    
    private static float pageWidth;
    private static float pageHeight;
    private static float numRounds;
    private static float roundDuration;
    private static int totalColumns;
    private static int totalRows;
    private static int numStations;
    private static float spaceAboveTable;
    private static float tableHeight;
    private static float tableWidth;
    private static float headerHeight;
    private static float cellHeight;
    private static float cellWidth;
    private static float columnWidth;
    private static float lefterWidth;
    private static Float[] widths;
    private static float marginSides;
    
    
	
	public PDFoutput(Tournament t, File destination){
		
		File f = new File(destination, "StreamLiner Pools");
		
		try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(f));
            document.open();
            document.setPageSize(PageSize.A4.rotate());
            initialise(document, t);
            
//            addMetaData(document);
//            addTitlePage(document);
            for (Tournament.Pool p: t.getPools()){
            	
            	addContent(document, t, p);
            	document.newPage();
            	
            }
            
            document.close();
		 } catch (Exception e) {
            e.printStackTrace();
		 }
		
	}
	

  private static void initialise(Document document, Tournament t){
	  
	  Tournament.Pool p = t.getPools().get(0);
	  
	  Rectangle box = document.getPageSize();
//		System.err.println("fgsd");
		pageWidth = box.getWidth();
		pageHeight = box.getHeight();
		System.out.println("pageHeight = " + pageHeight);
		
		numRounds = p.getNumberOfRounds();       		
		totalColumns = (int) numRounds+1;
		roundDuration = 60/numRounds;
		
		numStations = (int) (p.getNumberofMatches() / numRounds);
		System.out.println("There are " + numStations + " Stations");
		totalRows = (int) numStations + 1;
		
		spaceAboveTable = 150;//This can be adusted
		marginSides = 35;
		
		tableHeight = pageHeight - spaceAboveTable;
		tableWidth = pageWidth - 2*marginSides;
		
		headerHeight = 100;
		
		cellHeight = (tableHeight - headerHeight) / numStations;
//		cellHeight = 80;//TODO remove
		System.out.println("Cell Height is: " + cellHeight);
		
		lefterWidth = 30;
		columnWidth = (tableWidth -lefterWidth) / numRounds;
//		widths = new float[]{lefterWidth, 100,100,100,100,100}
		ArrayList<Float> tempList = new ArrayList<Float>();
		tempList.add(lefterWidth);
		for (int i =0; i<numRounds; i++){
			tempList.add(columnWidth);
		}
		widths = new Float[totalColumns];
		widths = tempList.toArray(widths);
		System.out.println();
		
		
		
  }
           
               
     

        private static void addContent(Document document, Tournament t, Tournament.Pool p) throws DocumentException {
        	
        	
	        Anchor anchor = new Anchor("Pool " + p.getPoolIdentifier(), catFont);                
	        anchor.setName("X1 Pool Sheets");
	
	        // Second parameter is the number of the chapter
	        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
	        catPart.setNumberDepth(0);

	        
	        Paragraph instructions = new Paragraph();
	        instructions.add("Play all games in order left to right! \n");
	        instructions.add("Games highlighted in green should be played on the livestream!");
	        addEmptyLine(instructions, 1);     
	        instructions.setAlignment(Element.ALIGN_CENTER);
	        catPart.add(instructions);
	
	        // add a table
	        createTable(catPart, t, p);
	
	        // now add all this to the document
	        document.add(catPart);
	


        }
        
        private static void createTable(Section subCatPart, Tournament t, Tournament.Pool p)  throws DocumentException {
        	
            PdfPTable table = new PdfPTable(totalColumns);
//            table.setWidthPercentage(100);
            
            table.setTotalWidth(ArrayUtils.toPrimitive(widths));//apache commons to turn Float to float
            table.setLockedWidth(true);
            
            table.setSpacingBefore(0f);
            table.setSpacingAfter(0f);
            
            
            addHeaders(table, t, p);            
            addMatches(table, t, p);                    

            subCatPart.add(table);

    }

        
        private static void addHeaders(PdfPTable table, Tournament t, Tournament.Pool p) throws DocumentException {
        	
        	PdfPCell c1;
            
            c1 = new PdfPCell();
            
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);//blank top left cell
//            c1
            table.addCell(c1);	
            
            for (int i = 0; i <totalColumns -1 ; i++){
            	c1 = new PdfPCell(new Paragraph("MATCH " + (i+1),headerFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);	
            }
            c1= new PdfPCell();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);//blank top left cell
            table.addCell(c1);	
            for (int i = 1; i <totalColumns; i++){
            	c1 = new PdfPCell(new Paragraph(t.getRoundStartTime(i, (int) roundDuration), headerFont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);	
            }
                          
            table.setHeaderRows(2);
        	
        }
        
        private static void addMatch(PdfPTable table, Tournament t, Tournament.Pool p, int matchNumber){
        	
        	if (matchNumber >10){
//        		return;
        	}
        	
      		Match m = p.getMatch(matchNumber);
          	
      		String matchText = m.toString();
        	
        	PdfPCell cell = new PdfPCell();
            if (m.isStreamed()){
        		cell.setBackgroundColor(streamedGameColour);
            }
            cell.setPaddingBottom(20f);
            cell.setPaddingTop(20f);
            Paragraph para = new Paragraph(matchText, subFont);
            para.setLeading(0,1);
            para.setAlignment(Paragraph.ALIGN_CENTER);
            cell.addElement(para);
//                cell.setMinimumHeight(50);
            cell.setFixedHeight(cellHeight);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
            table.addCell(cell);
        }
        
        private static void addMatches(PdfPTable table, Tournament t, Tournament.Pool p) throws DocumentException{
        	
//          System.out.println("p.getnumofmatches() = " + p.getNumberofMatches());
        	
        	String station = new String("TV \n");
        	
        	
        	int matchNumber = 0;
        	for (int i = 0; i<numStations; i++){
        		Paragraph para = new Paragraph(station + (i+1));
        		para.setAlignment(Paragraph.ALIGN_CENTER);
        		para.setAlignment(Paragraph.ALIGN_MIDDLE);
        		PdfPCell cell = new PdfPCell(para);//add the "stations" cell"
        		cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
        		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        		table.addCell(cell);
        		
        		for (int j=0; j<numRounds; j++){
        			addMatch(table, t, p, matchNumber);
        			matchNumber++;
        		}
        		
        		
        	}
        	
//          for (int i=0; i<p.getNumberofMatches() + numStations; i++){
//          	
//        	  if (i%totalColumns ==0 ){
//        		  PdfPCell cell = new PdfPCell(new Paragraph("g"));
//        	  }else{
        		  
        	  
          	
//          	System.out.println(i);
//          		Match m = p.getMatch(i);
//          	
//          		String name = m.toString();
          	
          	
//	              PdfPCell cell = new PdfPCell();
//	              if (m.isStreamed()){
//	          		cell.setBackgroundColor(streamedGameColour);
//	              }
//	              cell.setPaddingBottom(20f);
//	              cell.setPaddingTop(20f);
//	              Paragraph para = new Paragraph(name, subFont);
//	              para.setLeading(0,1);
//	              para.setAlignment(Paragraph.ALIGN_CENTER);
//	              cell.addElement(para);
//	//                cell.setMinimumHeight(50);
//	              cell.setFixedHeight(cellHeight);
//	              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//              
//	              table.addCell(cell);
//        	  }
          
//          }
        	
        }

       
 
        private static void addEmptyLine(Paragraph paragraph, int number) {
                for (int i = 0; i < number; i++) {
                        paragraph.add(new Paragraph(" "));
                }
        }

	
	
}
