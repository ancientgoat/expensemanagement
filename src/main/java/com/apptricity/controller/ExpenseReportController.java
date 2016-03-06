package com.apptricity.controller;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.service.ExpenseReportService;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
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
   *
   */
  @RequestMapping(value = "/expenses", method = RequestMethod.GET)
  //public ResponseEntity findAllPageable(final Pageable pageable) {
  public ResponseEntity findAllPageable(
      Model model,
      @QuerydslPredicate(root = ExpenseReport.class) Predicate predicate, Pageable pageable,
      @RequestParam MultiValueMap<String, String> parameters
      ) {

    HttpStatus httpStatus = HttpStatus.OK;
    Page<ExpenseReport> expenseReportPages = expenseReportService.findAll(predicate, pageable);
    if (0 == expenseReportPages.getTotalElements()) {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity(expenseReportPages, httpStatus);
  }

  /**
   *
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.GET)
  public ResponseEntity findOne(@PathVariable("id") String id) {
    HttpStatus httpStatus = HttpStatus.OK;
    final ExpenseReport expenseReport = expenseReportService.findOne(id);
    if (null == expenseReport) {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity(expenseReport, httpStatus);
  }

  /**
   *
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
   *
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public ResponseEntity updateExpenseReport(
      @PathVariable("id") String id, @RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {

    HttpStatus httpStatus = HttpStatus.OK;
    ExpenseReportResponseDto responseDto =
        expenseReportService.updateFromDto(id, dtoBuilder.buildForInsert());

    return new ResponseEntity(responseDto, httpStatus);
  }

  /**
   *
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteExpenseReport(@PathVariable("id") String id) {
    HttpStatus httpStatus = HttpStatus.OK;
    final ExpenseReportResponseDto responseDto = expenseReportService.delete(id);
    return new ResponseEntity(responseDto, httpStatus);
  }
}
