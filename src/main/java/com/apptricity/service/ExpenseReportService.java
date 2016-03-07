package com.apptricity.service;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.repo.ExpenseReportRepo;
import com.apptricity.repo.MerchantRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service/Business layer between the Controller and the Repositories.
 */
@Service
public class ExpenseReportService {

  @JsonIgnore
  public static final Logger log = LoggerFactory.getLogger(ExpenseReportService.class);

  private MerchantRepo merchantRepo;
  private ExpenseReportRepo expenseReportRepo;

  @Autowired
  public ExpenseReportService(final MerchantRepo inMerchantRepo, final ExpenseReportRepo inExpenseReportRepo) {
    this.merchantRepo = inMerchantRepo;
    this.expenseReportRepo = inExpenseReportRepo;
  }

  /**
   * @param pageable
   * @return
   */
  public Page<ExpenseReport> findAll(final Pageable pageable) {
    return expenseReportRepo.findAll(pageable);
  }

  /**
   * @param predicate
   * @param pageable
   * @return
   */
  public Page<ExpenseReport> findAll(final Predicate predicate, final Pageable pageable) {
    return expenseReportRepo.findAll(predicate, pageable);
  }

  /**
   * @param inId
   * @return
   */
  public ExpenseReport findOne(final String inId) {
    //expenseReportRepo.findBy
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
   * @param inId
   * @param createDto
   * @return
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

          String firstComment = createDto.getFirstComment();
          if (null != firstComment && 0 < firstComment.length()) {
            changed = true;
            expenseReport.addComment(firstComment);
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
   * @param inId
   * @return
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
          final Merchant merchant = expenseReport.getMerchant();
          if (null != merchant) {
            final String msg = String.format("Deleted Merchant '%s'", merchant.getId());
            this.merchantRepo.delete(merchant.getId());
            if (log.isInfoEnabled()) {
              log.info(msg);
            }
            responseDtoBuilder.addInfo(msg);
          }

          final String msg = String.format("Deleted ExpenseReport '%s'", inId);
          this.expenseReportRepo.delete(inId);
          if (log.isInfoEnabled()) {
            log.info(msg);
          }
          responseDtoBuilder.addInfo(msg);
        }
      }
    } catch (Exception e) {
      responseDtoBuilder.addWarn(String.format("Error with ExpenseReport : '%s' : %s", inId, e.toString()));
    }
    return responseDtoBuilder.build();
  }
}
