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
 *
 */
@RestController
@RequestMapping(value = "/auth")
public class ExpenseReportAuthController extends BaseController {

  @Autowired
  public ExpenseReportAuthController(final ExpenseReportService inExpenseReportService) {
    super(inExpenseReportService);
  }

  /**
   *
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
   *
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.GET)
  public ResponseEntity findOne(@PathVariable("id") String id) {
    return super.findOne(id);
  }

  /**
   *
   */
  @RequestMapping(value = "/expense", method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity createExpenseReport(@RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {
    return super.createExpenseReport(dtoBuilder);
  }

  /**
   *
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public ResponseEntity updateExpenseReport(
      @PathVariable("id") String id, @RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {
    return super.updateExpenseReport(id, dtoBuilder);
  }

  /**
   *
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteExpenseReport(@PathVariable("id") String id) {
    return super.deleteExpenseReport(id);
  }
}
