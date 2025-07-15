import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class WorkingWithFilesTest {
    private ClassLoader cl = WorkingWithFilesTest.class.getClassLoader();

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("archive.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                switch (entry.getName()) {
                    case "archive/Presentation.pdf" -> {
                        PDF pdf = new PDF(zis);
                        Assertions.assertEquals("Keynote", pdf.creator);
                        Assertions.assertEquals(7, pdf.numberOfPages);
                        Assertions.assertEquals("Presentation 5", pdf.title);
                    }
                    case "archive/продуктовые запасы.xlsx" -> {
                        XLS xls = new XLS(zis);
                        String actualValue = xls.excel.getSheetAt(0).getRow(7).getCell(5).getStringCellValue();
                        Assertions.assertTrue(actualValue.contains("Уксусы и соусы"));
                    }
                    case "archive/Книга1.csv" -> {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> data = csvReader.readAll();
                        Assertions.assertEquals(2, data.size());
                        Assertions.assertArrayEquals(
                                new String[]{"42-44", " 280р"}, //не обрезает всякие пробелы
                                data.get(0)
                        );
                        Assertions.assertArrayEquals(
                                new String[]{"44-46", " 320р"},
                                data.get(1)
                        );
                    }
                    default -> {}
                }
            }
        }
    }
}

