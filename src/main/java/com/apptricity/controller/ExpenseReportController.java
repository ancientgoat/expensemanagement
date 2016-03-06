package com.apptricity.controller;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.service.ExpenseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping(value = "/")
public class ExpenseReportController {

  @Autowired
  private ExpenseReportService expenseReportService;


  /**
   * @param name
   * @return
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.GET)
  public ResponseEntity findOne(@PathVariable("id") String id) {
    HttpStatus httpStatus = HttpStatus.OK;
    ExpenseReport expenseReport = expenseReportService.findOne(id);
    return new ResponseEntity(expenseReport, httpStatus);
  }

  /**
   * @param name
   * @return
   */
  @RequestMapping(value = "/expense", method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity createExpenseReport(@RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {

    HttpStatus httpStatus = HttpStatus.OK;

    ExpenseReportResponseDto responseDto = expenseReportService.createFromDto(dtoBuilder.buildForInsert());

    if (responseDto.hasMessage()) {
      httpStatus = HttpStatus.NOT_ACCEPTABLE;
      return new ResponseEntity(responseDto.getMessages(), httpStatus);
    }
    return new ResponseEntity(responseDto.getExpenseReport(), httpStatus);
  }

  /**
   * @param name
   * @return
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public ResponseEntity updateExpenseReport(
      @RequestParam String inId, @RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {

    HttpStatus httpStatus = HttpStatus.OK;

    ExpenseReportResponseDto responseDto =
        expenseReportService.updateFromDto(inId, dtoBuilder.buildForInsert());

    if (responseDto.hasMessage()) {
      httpStatus = HttpStatus.NOT_ACCEPTABLE;
      return new ResponseEntity(responseDto.getMessages(), httpStatus);
    }
    return new ResponseEntity(responseDto.getExpenseReport(), httpStatus);
  }
}
