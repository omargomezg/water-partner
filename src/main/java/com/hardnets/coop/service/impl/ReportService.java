package com.hardnets.coop.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.hardnets.coop.exception.PeriodException;
import com.hardnets.coop.model.dto.municipal.BeneficiaryDto;
import com.hardnets.coop.model.dto.municipal.DecreeDto;
import com.hardnets.coop.model.dto.municipal.MunicipalReportDto;
import com.hardnets.coop.model.dto.municipal.ReportDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.PeriodRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final PeriodRepository periodRepository;
    private final ConsumptionRepository consumptionRepository;

    public MunicipalReportDto getMunicipalReportByPeriod(Long periodId, int pageIndex, int pageSize) throws PeriodException {
        PeriodEntity period = periodRepository.findById(periodId)
                .orElseThrow(PeriodException::new);

        // Optional<PeriodEntity> lastPeriod = periodRepository.findFirstByIdNot(periodId);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ResumeConsumptionDetailDto> page = consumptionRepository.findAllByPeriod(period.getId(), pageable);
        MunicipalReportDto report = new MunicipalReportDto();
        List<ReportDto> detail = page.getContent().stream()
                .map(consumption -> ReportDto.builder().consumption(consumption.getActualRecord().intValue())
                        .codeCommune(14101)
                        .beneficiary(BeneficiaryDto.builder()
                                .dni(consumption.getDni())
                                .names(consumption.getNames())
                                .middleName(consumption.getMiddleName())
                                .lastName(consumption.getLastName())
                                .address("Curiñanco")
                                .build())
                        .nameCommune("VALDIVIA")
                        .decree(new DecreeDto(132423L,new Date()))
                        .build())
                .collect(Collectors.toList());
        report.setDetail(detail);
        report.setTotalHits(page.getTotalElements());
        return report;
    }

    public void getMunicipalReport() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("Persons");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            headerStyle.setFont(font);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Name");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Age");
            headerCell.setCellStyle(headerStyle);

            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            Row row = sheet.createRow(2);
            Cell cell = row.createCell(0);
            cell.setCellValue("John Smith");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(20);
            cell.setCellStyle(style);

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
        } finally {
            if (workbook != null) {

                workbook.close();

            }
        }
    }

}
