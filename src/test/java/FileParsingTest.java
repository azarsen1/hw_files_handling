import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileParsingTest {

    private final ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    @DisplayName("Файл file_example_XLSX_10 корректно читается из архива")
    void shouldReadFileXlsxFromZip() throws Exception {
        // GIVEN
        boolean found = false;

        try (InputStream is = cl.getResourceAsStream("Архив файлов.zip");
             ZipInputStream zis = new ZipInputStream(is)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().endsWith("file_example_XLSX_10.xlsx") && !entry.isDirectory()) {
                    //  нашли нужный файл и начинаем читать его
                    XLS xls = new XLS(zis);
                    String value = xls.excel.getSheetAt(0).getRow(7).getCell(2).getStringCellValue();
                    assertThat(value).as("Ячейка не содержить слово 'Hurn'")
                            .isEqualTo("Hurn");

                    found = true;
                    break;
                }
            }
        }

        assertThat(found).as("Файл не найден").isTrue();

    }


    @Test
    @DisplayName("Файл username.csv корректно обрабатывается из архива")
    void shouldReadFileCvsFromZip() throws Exception {
        // GIVEN
        boolean found = false;

        try (InputStream is = cl.getResourceAsStream("Архив файлов.zip");
             ZipInputStream zis = new ZipInputStream(is)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().endsWith("/username.csv") && !entry.isDirectory()) {
                    try (CSVReader reader = new CSVReaderBuilder(
                            new InputStreamReader(zis, StandardCharsets.UTF_8))
                            .withCSVParser(new CSVParserBuilder()
                                    .withSeparator(';')
                                    .build())
                            .build()) {

                        List<String[]> content = reader.readAll();
                        org.assertj.core.api.Assertions.assertThat(content).contains(
                                new String[]{"Username", " Identifier", "First name", "Last name"},
                                new String[]{"booker12", "9012", "Rachel", "Booker"},
                                new String[]{"grey07", "2070", "Laura", "Grey"},
                                new String[]{"johnson81", "4081", "Craig", "Johnson"},
                                new String[]{"jenkins46", "9346", "Mary", "Jenkins"},
                                new String[]{"smith79", "5079", "Jamie", "Smith"}


                        );


                        found = true;
                        break;
                    }

                }
            }

            assertThat(found).as("Файл не найден").isTrue();

        }
    }

    @Test
    @DisplayName("Файл 24-11-2025.pdf корректно обрабатывается из архива")
    void shouldReadFileTxtCvsFromZip() throws Exception {
        // GIVEN
        boolean found = false;

        try (InputStream is = cl.getResourceAsStream("Архив файлов.zip");
             ZipInputStream zis = new ZipInputStream(is)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().endsWith("24-11-2025.pdf") && !entry.isDirectory()) {
                    PDF pdf = new PDF(zis);
                    String text = pdf.text;
                    assertThat(text)
                            .as("PDF должен содержать слово 'Ipak Yuli Mobile' или 'Успешно'")
                            .contains("Ipak Yuli Mobile", "Успешно");
                    found = true;
                    break;
                }
            }
        }

        assertThat(found).as("Файл не найден").isTrue();

    }
}
