package com.mymoid.batch.configuration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;


public class OrderWriter implements ItemStreamWriter<User> {



    private XSSFWorkbook wb;
    private WritableResource resource;
    private int row;
    private XSSFSheet sheet;


    private static final String FILE_NAME = "/tmp/MyFirstExcel4.xls";

    private String fileDirectory;

    public OrderWriter(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        resource = new FileSystemResource(FILE_NAME);
        wb= new XSSFWorkbook();
        sheet = wb.createSheet("Datatypes in Java");
        row=0;

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

        if (wb == null) {
            return;
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(resource.getOutputStream())) {
            wb.write(bos);
            bos.flush();
            wb.close();
            wb=null;
        } catch (IOException ex) {
            throw new ItemStreamException("Error writing to output file", ex);
        }
        row = 0;

    }

    @Override
    public void write(List<? extends User> list) throws Exception {

        System.out.println("Creating excel");

        for (User user : list) {
             row=row+1;
            final XSSFRow row1 = sheet.createRow(row);
            int colNum = 0;
            Cell cell = row1.createCell(colNum++);
            cell.setCellValue(user.getName());

        }

    }
}
