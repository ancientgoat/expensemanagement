package com.apptricity.service;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.repo.ExpenseReportRepo;
import com.apptricity.repo.MerchantRepo;
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
   *
   */
  public ExpenseReportResponseDto createFromDto(final ExpenseReportCreateDto createDto) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder = new ExpenseReportResponseDto.Builder();

    try {
      if (!createDto.hasMessage()) {
        // final Merchant merchant = merchantRepo.save(createDto.getMerchant());
        ExpenseReport expenseReport = createDto.getExpenseReport();
        // expenseReport.setMerchant(merchant);
        responseDtoBuilder.setExpenseReport(expenseReportRepo.save(expenseReport));
      } else {
        // Has errors
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

    try {
      final ExpenseReport expenseReport = this.expenseReportRepo.findOne(inId);

      if (null == expenseReport) {
        responseDtoBuilder.addError(String.format("No such ExpenseReport with id '%s'", inId));
      } else {
        if (ExpenseReportStatus.REIMBURSED == expenseReport.getStatus()) {
          // ExpenseReport is NOT NEW - update not allowed.
          responseDtoBuilder.addWarn("Can not update a REIMBURSED ExpenseReport.");
        } else {
          final ExpenseReport dtoExpenseRpt = createDto.getExpenseReport();

          // Update ExpenseReport
          boolean changed =
              expenseReport.updateAmount(dtoExpenseRpt.getAmount())
                  || expenseReport.updateExpenseDateTime(dtoExpenseRpt.getExpenseDateTime());

          final ExpenseReportStatus status = createDto.getExpenseReport().getStatus();
          if (null != status && status == ExpenseReportStatus.REIMBURSED) {
            changed = expenseReport.updateStatus(status) || changed;
          }

          // Update Merchant
          if (createDto.haveMerchantName()) {
            Merchant merchant = expenseReport.getMerchant();
            if (null == merchant) {
              merchant = new Merchant();
            }
            changed = merchant.updateName(createDto.getMerchantName()) || changed;
            expenseReport.setMerchant(merchant);
          }

          // Save some time - if nothing changed.
          ExpenseReport savedExpenseReport = null;
          if (changed) {
            savedExpenseReport = this.expenseReportRepo.save(expenseReport);
          } else {
            responseDtoBuilder.addInfo(
                String.format("No changes were input for ExpenseReport id '%s', so nothing saved.", inId));
            savedExpenseReport = expenseReport;
          }
          responseDtoBuilder.setExpenseReport(savedExpenseReport);
        }
      }
    } catch (Exception e) {
      responseDtoBuilder.addWarn(String.format("Error with ExpenseReport : '%s' : %s", inId, e.toString()));
    }
    return responseDtoBuilder.build();
  }

  /**
   *
   */
  public ExpenseReportResponseDto delete(final String inId) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder = new ExpenseReportResponseDto.Builder();

    try {
      final ExpenseReport expenseReport = this.expenseReportRepo.findOne(inId);

      if (null == expenseReport) {
        responseDtoBuilder.addError(String.format("No such ExpenseReport with id '%s'", inId));
      } else {
        if (ExpenseReportStatus.REIMBURSED == expenseReport.getStatus()) {
          // ExpenseReport is NOT NEW - update not allowed.
          responseDtoBuilder.addWarn("Can not delete a REIMBURSED ExpenseReport.");
        } else {
          this.expenseReportRepo.delete(inId);
        }
      }
    } catch (Exception e) {
      responseDtoBuilder.addWarn(String.format("Error with ExpenseReport : '%s' : %s", inId, e.toString()));
    }
    return responseDtoBuilder.build();
  }
}
