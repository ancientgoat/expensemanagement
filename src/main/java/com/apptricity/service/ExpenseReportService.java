package com.apptricity.service;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.repo.ExpenseReportRepo;
import com.apptricity.repo.MerchantRepo;
import com.apptricity.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ExpenseReportService {

  @Autowired
  private MerchantRepo merchantRepo;

  @Autowired
  private ExpenseReportRepo expenseReportRepo;

  /**
   * @param createDto
   * @return
   */
  public ExpenseReportResponseDto createFromDto(final ExpenseReportCreateDto createDto) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder = new ExpenseReportResponseDto.Builder();

    try {
      if(!createDto.hasErrors()) {
        final Merchant merchant = merchantRepo.save(createDto.getMerchant());
        ExpenseReport expenseReport = createDto.getExpenseReport();
        expenseReport.setMerchant(merchant);
        responseDtoBuilder
            .setMerchant(merchant)
            .setExpenseReport(expenseReportRepo.save(expenseReport))
        ;
      } else {
        // has errors
        responseDtoBuilder.setErrorMessage(createDto.getErrorMessage());
      }
    } catch (Exception e) {
      responseDtoBuilder.setErrorMessage(new ErrorMessage.Builder().addError(e.getMessage()).build());
    }

    return responseDtoBuilder.build();
  }
}
