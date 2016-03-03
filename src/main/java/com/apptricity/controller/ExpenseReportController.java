package com.apptricity.controller;


import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.repo.ExpenseReportRepo;
import com.apptricity.repo.MerchantRepo;
import com.apptricity.service.ExpenseReportService;
import com.apptricity.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
  @RequestMapping(value = "/expense", method = RequestMethod.POST, consumes = "application/json")
  public ResponseEntity createExpenseReport(@RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {

    HttpStatus httpStatus = HttpStatus.OK;

    ExpenseReportResponseDto responseDto = expenseReportService.createFromDto(dtoBuilder.build());

    if(responseDto.hasErrors()){
      httpStatus = HttpStatus.NOT_ACCEPTABLE;
      return new ResponseEntity<>(responseDto.getErrorMessage(), httpStatus);
    }
    return new ResponseEntity<>(responseDto.getExpenseReport(), httpStatus);
  }

  /**
   * @param name
   * @return
   */
  @RequestMapping(value = "/expense/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public ResponseEntity updateExpenseReport(@RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {

    HttpStatus httpStatus = HttpStatus.OK;

    ExpenseReportResponseDto responseDto = expenseReportService.createFromDto(dtoBuilder.build());

    if(responseDto.hasErrors()){
      httpStatus = HttpStatus.NOT_ACCEPTABLE;
      return new ResponseEntity<>(responseDto.getErrorMessage(), httpStatus);
    }
    return new ResponseEntity<>(responseDto.getExpenseReport(), httpStatus);
  }
}
