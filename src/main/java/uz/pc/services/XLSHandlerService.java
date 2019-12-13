package uz.pc.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pc.collections.SalaryCollection;
import uz.pc.collections.SalaryDetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XLSHandlerService {

    private List<SalaryCollection> collections;
    private Date start;
    private Date end;

    public XLSHandlerService(List<SalaryCollection> collections, Date start, Date end) {
        this.collections = collections;
        this.start = start;
        this.end = end;
    }

    public void writeDataToExcel() {
        Workbook workbook = new XSSFWorkbook();

        for (SalaryCollection item : collections) {
            Sheet sheet = workbook.createSheet(
                    item.getEmployee().getFirstName()
                            + " " +
                            item.getEmployee().getSecondName()
            );

            Row headerTitle = sheet.createRow(0);
            Row headerEmp = sheet.createRow(1);
            Row headerDates = sheet.createRow(2);
            Row headerSalary = sheet.createRow(3);

            Cell headerCell = headerTitle.createCell(1);

            Cell headerEmpKey = headerEmp.createCell(0);
            Cell headerEmpVal = headerEmp.createCell(1);

            Cell headerDatesKey = headerDates.createCell(0);
            Cell headerDatesVal = headerDates.createCell(1);

            Cell headerSalaryKey = headerSalary.createCell(0);
            Cell headerSalaryVal = headerSalary.createCell(1);

            headerCell.setCellValue(start + " - " + end + " kunlar ichida bajarilgan ishlar");
            headerEmpKey.setCellValue("Tanlangan ishchi:");
            headerEmpVal.setCellValue(item.getEmployee().getFirstName() + " " + item.getEmployee().getSecondName());

            headerDatesKey.setCellValue("Tanlangan kunlar:");
            headerDatesVal.setCellValue(start + " - " + end);

            headerSalaryKey.setCellValue("Jami:");
            headerSalaryVal.setCellValue(item.getOverallSalary());

            Row reportDates = sheet.createRow(5);
            int dateCellIterator = 1;
            Cell reportDatesCell;
            for (LocalDateTime date : getDatesBetween(start, end)) {
                reportDatesCell = reportDates.createCell(dateCellIterator);
                reportDatesCell.setCellValue(date.toString());
                ++dateCellIterator;
            }

            int rowReportIterator = 6;
            int CellReportIterator = 1;
            Row reportDataRow;
            Cell reportDataCell;
            for (SalaryDetail detail : item.getDetails()) {
                reportDataRow = sheet.createRow(rowReportIterator);
                reportDataCell = reportDataRow.createCell(0);
                reportDataCell.setCellValue(detail.getProductionReference());



            }

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(fileLocation);
                workbook.write(outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private List<LocalDateTime> getDatesBetween(Date start, Date end) {
        LocalDateTime startLocal = start.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime endLocal = end.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        long numOfDaysBetween = ChronoUnit.DAYS.between(startLocal, endLocal);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startLocal.plusDays(i))
                .collect(Collectors.toList());
    }
}
