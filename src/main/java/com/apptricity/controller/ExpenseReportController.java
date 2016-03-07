package com.apptricity.controller;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.service.ExpenseReportService;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * Supported rest calls for the ExpenseManagement project.  No authorization needed.
 */
@RestController
@RequestMapping(value = "/")
public class ExpenseReportController extends BaseController {

  @Autowired
  public ExpenseReportController(final ExpenseReportService inExpenseReportService) {
    super(inExpenseReportService);
  }

  /**
   * Find all Expense reports, allow a 'querydsl' filter based on URL parameters.
   *
   * @param model
   * @param predicate
   * @param pageable
   * @param parameters
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
  @RequestMapping(value = "/expenses", method = RequestMethod.GET)
  public ResponseEntity findAllPageable(
      Model model,
      @QuerydslPredicate(root = ExpenseReport.class) Predicate predicate, Pageable pageable,
      @RequestParam MultiValueMap<String, String> parameters
  ) {
    return super.findAllPageable(model, predicate, pageable, parameters);
  }

  /**
   * @param id Input ExportReport id - used to fetch a single record.
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.GET)
  public ResponseEntity findOne(@PathVariable("id") String id) {
    return super.findOne(id);
  }

  /**
   * @param dtoBuilder A DTO is created on the fly by the Http attached JSON content.
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
  @RequestMapping(value = "/expense", method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity createExpenseReport(@RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {
    return super.createExpenseReport(dtoBuilder);
  }

  /**
   * @param id The id of the ExpenseReport to attempt to update.
   * @param dtoBuilder A DTO is created on the fly by the Http attached JSON content.
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public ResponseEntity updateExpenseReport(
      @PathVariable("id") String id, @RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {
    return super.updateExpenseReport(id, dtoBuilder);
  }

  /**
   * @param id The id of the ExpenseReport to attempt to delete.
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteExpenseReport(@PathVariable("id") String id) {
    return super.deleteExpenseReport(id);
  }
}
