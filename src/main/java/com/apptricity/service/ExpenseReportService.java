package com.apptricity.service;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.repo.ExpenseReportRepo;
import com.apptricity.repo.MerchantRepo;
import com.apptricity.util.ErrorMessage;
import com.apptricity.util.UpdateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
   *
   */
  public Page<ExpenseReport> findAll(final Pageable pageable) {
    return expenseReportRepo.findAll(pageable);
  }

  /**
   *
   */
  public ExpenseReport findOne(final String inId) {

    ExpenseReport expenseReport = expenseReportRepo.findOne(inId);

    return expenseReport;
  }

  /**
   * @param createDto
   * @return
   */
  public ExpenseReportResponseDto createFromDto(final ExpenseReportCreateDto createDto) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder = new ExpenseReportResponseDto.Builder();

    try {
      if (!createDto.hasErrors()) {
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

  /**
   * @param inId
   * @param build
   * @return
   */
  public ExpenseReportResponseDto updateFromDto(final String inId, final ExpenseReportCreateDto createDto) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder = new ExpenseReportResponseDto.Builder();
    final ExpenseReport expenseReport = this.expenseReportRepo.findOne(inId);
    ExpenseReport savedExpenseReport = null;

    if (null != expenseReport) {
      if (ExpenseReportStatus.NEW == expenseReport.getStatus()) {

        final ExpenseReport dtoExpenseRpt = createDto.getExpenseReport();

        expenseReport.setAmount(UpdateHelper.update(
            expenseReport.getAmount(), dtoExpenseRpt.getAmount()));

        expenseReport.setExpenseDateTime(UpdateHelper.update(
            expenseReport.getExpenseDateTime(), dtoExpenseRpt.getExpenseDateTime()));

        final ExpenseReportStatus status = dtoExpenseRpt.getStatus();
        if (null != status && status == ExpenseReportStatus.REIMBURSED) {
          expenseReport.setStatus(status);
        }
        savedExpenseReport = this.expenseReportRepo.save(expenseReport);

        responseDtoBuilder.setExpenseReport(savedExpenseReport);
        responseDtoBuilder.setMerchant(savedExpenseReport.getMerchant());
      } else {
        // ExpenseReport is NOT NEW - can not update.
        responseDtoBuilder.addErrorMessage("Can not update an ExpenseReport that is REDEEMED.");
      }
    } else {
      responseDtoBuilder.addErrorMessage(String.format("No such ExpenseReport with id '%s'", inId));
    }

    return responseDtoBuilder.build();
  }
}
