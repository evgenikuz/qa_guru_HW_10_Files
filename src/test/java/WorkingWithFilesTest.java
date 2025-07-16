import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.Cat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class WorkingWithFilesTest {
    private ClassLoader cl = WorkingWithFilesTest.class.getClassLoader();

    @DisplayName("Проверка pdf в zip")
    @Test
    void pdfInZipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("archive.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName() == "archive/Presentation.pdf") {
                        PDF pdf = new PDF(zis);
                        assertEquals("Keynote", pdf.creator);
                        assertEquals(7, pdf.numberOfPages);
                        assertEquals("Presentation 5", pdf.title);
                }
            }
        }
    }

    @DisplayName("Проверка xlsx в zip")
    @Test
    void xlsxInZipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("archive.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName() == "archive/продуктовые запасы.xlsx") {
                        XLS xls = new XLS(zis);
                        String actualValue = xls.excel.getSheetAt(0).getRow(7).getCell(5).getStringCellValue();
                        assertTrue(actualValue.contains("Уксусы и соусы"));
                }
            }
        }
    }

    @DisplayName("Проверка csv в zip")
    @Test
    void csvInZipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("archive.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName() == "archive/Книга1.csv") {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvReader.readAll();
                    assertEquals(2, data.size());
                    Assertions.assertArrayEquals(
                            new String[]{"42-44", " 280р"}, //не обрезает всякие пробелы
                            data.get(0)
                    );
                    Assertions.assertArrayEquals(
                            new String[]{"44-46", " 320р"},
                            data.get(1)
                    );
                }
            }
        }
    }

    @DisplayName("Проверка json файла")
    @Test
    void jsonParcingTest() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("cat.json")) {

            ObjectMapper mapper = new ObjectMapper();
            Cat cat = mapper.readValue(is, Cat.class);

            assertEquals("Пушистик", cat.getName());
            assertEquals(5, cat.getAge());
            assertTrue(cat.isHasLongTail());
            assertFalse(cat.isHasLongFur());
            assertEquals(24, cat.getWhiskersAmount());

            assertEquals("зеленые", cat.getColors().getEyes());
            assertEquals("рыжий", cat.getColors().getFur());
            assertEquals("белый", cat.getColors().getBelly());
            assertEquals("розовый", cat.getColors().getPaws());
            assertEquals("розовый", cat.getColors().getNose());

            assertArrayEquals(
                    new String[]{"лежать", "играть", "смотреть в окно", "бегать", "охотиться", "мурлыкать"},
                    cat.getHobbies()
            );
        }

    }
}

