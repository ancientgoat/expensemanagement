package com.apptricity.service;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.repo.ExpenseReportRepo;
import com.apptricity.repo.MerchantRepo;
import com.apptricity.util.Messages;
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
      if (!createDto.hasMessage()) {
        final Merchant merchant = merchantRepo.save(createDto.getMerchant());
        ExpenseReport expenseReport = createDto.getExpenseReport();
        expenseReport.setMerchant(merchant);
        responseDtoBuilder
            .setMerchant(merchant)
            .setExpenseReport(expenseReportRepo.save(expenseReport))
        ;
      } else {
        // has errors
        responseDtoBuilder.addMessages(createDto.getMessages());
      }
    } catch (Exception e) {
      responseDtoBuilder.addError(e.getMessage());
    }
    return responseDtoBuilder.build();
  }

  /**
   *
   */
  public ExpenseReportResponseDto updateFromDto(final String inId, final ExpenseReportCreateDto createDto) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder = new ExpenseReportResponseDto.Builder();
    final ExpenseReport expenseReport = this.expenseReportRepo.findOne(inId);

    if (null == expenseReport) {
      responseDtoBuilder.addError(String.format("No such ExpenseReport with id '%s'", inId));
    } else {
      if (ExpenseReportStatus.NEW == expenseReport.getStatus()) {
        // ExpenseReport is NOT NEW - update not allowed.
        responseDtoBuilder.addWarn("Can not update a REDEEMED ExpenseReport.");
      } else {
        final ExpenseReport dtoExpenseRpt = createDto.getExpenseReport();

        boolean changed =
            expenseReport.updateAmount(dtoExpenseRpt.getAmount())
                || expenseReport.updateExpenseDateTime(dtoExpenseRpt.getExpenseDateTime());

        final ExpenseReportStatus status = dtoExpenseRpt.getStatus();
        if (null != status && status == ExpenseReportStatus.REIMBURSED) {
          changed = expenseReport.updateStatus(dtoExpenseRpt.getStatus()) || changed;
        }

        // Save some time - if nothing changed.
        ExpenseReport savedExpenseReport = null;
        if (changed) {
          savedExpenseReport = this.expenseReportRepo.save(expenseReport);
        } else {
          responseDtoBuilder.addInfo(
              String.format("No changes for ExpenseReport id '%s', nothing saved.", inId));
          savedExpenseReport = expenseReport;
        }
        responseDtoBuilder.setExpenseReport(savedExpenseReport);
      }
    }

    return responseDtoBuilder.build();
  }
}
