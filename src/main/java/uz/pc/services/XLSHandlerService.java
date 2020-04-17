package uz.pc.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pc.db.dto.salary.SalariesDTO;
import uz.pc.db.dto.salary.SalaryDetailDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class XLSHandlerService {

    private List<SalariesDTO> collections;
    private LocalDateTime start;
    private LocalDateTime end;

    public XLSHandlerService(List<SalariesDTO> collections, LocalDateTime start, LocalDateTime end) {
        this.collections = collections;
        this.start = start;
        this.end = end;
    }

    public void createXls(boolean type) {
        Workbook workbook = new XSSFWorkbook();

        CellStyle heads = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setBold(true);
        heads.setFont(font);
        heads.setWrapText(true);

        if (type) {
            writeDataToExcel(workbook, heads);
        } else {
            saveOverallReport(workbook, heads);
        }
    }

    private void writeDataToExcel(Workbook workbook, CellStyle heads) {
        for (SalariesDTO item : collections) {
            if (item.getDetails().isEmpty()) {
                continue;
            }

            Sheet sheet = workbook.createSheet(
                    item.getEmployee().getFirstName()
            );
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 6000);
            sheet.setColumnWidth(2, 6000);
            sheet.setColumnWidth(3, 6000);
            sheet.setColumnWidth(4, 6000);
            sheet.setColumnWidth(5, 6000);

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

            headerCell.setCellValue(formatDate(start) + " - " + formatDate(end) + " kunlar ichida bajarilgan ishlar");
            headerEmpKey.setCellValue("Tanlangan ishchi:");
            headerEmpVal.setCellValue(item.getEmployee().getFirstName() + " " + item.getEmployee().getSecondName());

            headerDatesKey.setCellValue("Tanlangan kunlar:");
            headerDatesVal.setCellValue(formatDate(start) + " - " + formatDate(end));

            headerSalaryKey.setCellValue("Jami:");
            headerSalaryVal.setCellValue(item.getOverallSalary());

            Row reportHeader = sheet.createRow(5);
            String[] headers = {"Sana", "Ishlab chiqarilgan mahsulot", "Ishlagan vaqti", "Kunlik ish haqi"};
            int dateCellIterator = 1;
            Cell reportHeaderCell;
            for (int i = 0; i < headers.length; i++) {
                reportHeaderCell = reportHeader.createCell(i);
                reportHeaderCell.setCellValue(headers[i]);
                reportHeaderCell.setCellStyle(heads);
            }

            int rowReportIterator = 6;
            Row reportDataRow;
            Cell reportDataCell;
            for (SalaryDetailDTO detail : item.getDetails()) {
                reportDataRow = sheet.createRow(rowReportIterator);

                reportDataCell = reportDataRow.createCell(0);
                reportDataCell.setCellValue(formatDate(detail.getProductionDate()));

                reportDataCell = reportDataRow.createCell(1);
                reportDataCell.setCellValue(detail.getProduct());

                reportDataCell = reportDataRow.createCell(2);
                reportDataCell.setCellValue(detail.getWorkedHours());

                reportDataCell = reportDataRow.createCell(3);
                reportDataCell.setCellValue(detail.getSalary());

                rowReportIterator++;
            }

            String userHome = System.getProperty("user.home");

            File currDir = new File(userHome + "/Desktop");
            String path = currDir.getAbsolutePath();

            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream("segmented.xlsx");
                workbook.write(outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void saveOverallReport(Workbook workbook, CellStyle heads) {

        Sheet sheet = workbook.createSheet();
        Row header = sheet.createRow(0);
        String[] headerString = {"Sana", "Ro'yhatga olish raqami", "Ishlab chiqarilgan mahsulot", "Ishlagan vaqti", "Kunlik ish haqi"};
        Cell reportHeaderCell;
        for (int i = 0; i < headerString.length; i++) {
            reportHeaderCell = header.createCell(i);
            reportHeaderCell.setCellValue(headerString[i]);
            reportHeaderCell.setCellStyle(heads);
        }

        Row reportDataRow;
        Cell controlData;
        int rowIterator = 1;
        int groupStart = 0;
        int groupEnd = 0;

        for (SalariesDTO item : collections) {
            if (item.getDetails().isEmpty()) {
                continue;
            }

            reportDataRow = sheet.createRow(rowIterator);
            controlData = reportDataRow.createCell(0);
            controlData.setCellValue(item.getEmployee().getFirstName());

            controlData = reportDataRow.createCell(1);
            controlData.setCellValue(item.getEmployee().getSecondName());

            controlData = reportDataRow.createCell(4);
            controlData.setCellValue(item.getOverallSalary());

            ++rowIterator;
            groupStart = rowIterator;

            for (SalaryDetailDTO detail : item.getDetails()) {

                reportDataRow = sheet.createRow(rowIterator);

                controlData = reportDataRow.createCell(0);
                controlData.setCellValue(formatDate(detail.getProductionDate()));

                controlData = reportDataRow.createCell(1);
                controlData.setCellValue(detail.getProductionReference());

                controlData = reportDataRow.createCell(2);
                controlData.setCellValue(detail.getProduct());

                controlData = reportDataRow.createCell(3);
                controlData.setCellValue(detail.getWorkedHours());

                controlData = reportDataRow.createCell(4);
                controlData.setCellValue(detail.getSalary());

                ++rowIterator;

            }
            groupEnd = rowIterator;
            sheet.groupRow(groupStart, groupEnd);

            sheet.setRowGroupCollapsed(groupStart, true);
            rowIterator += 2;

        }

        String userHome = System.getProperty("user.home");

        File currDir = new File(userHome + "/Desktop");
        String path = currDir.getAbsolutePath();

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("overall.xlsx");
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy");

        return time.format(formatter);
    }
}
