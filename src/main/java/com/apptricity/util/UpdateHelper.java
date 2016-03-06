package com.apptricity.util;

/**
 *
 */
public class UpdateHelper<C> {

  // set : UpdateHelper.update( expenseReport.getAmount(), dtoExpenseRpt.getAmount()));

  private C value;
  private boolean changed = false;

  private UpdateHelper() {
  }

  public boolean isChanged() {
    return changed;
  }

  public C getValue() {
    return value;
  }

  public static final <C> UpdateHelper newInstance(final C currentValue, final C nextValue) {

    final UpdateHelper updateHelper = new UpdateHelper<C>();

    if (null == currentValue || null != nextValue) {
      updateHelper.value = nextValue;
      updateHelper.changed = true;
    } else {
      updateHelper.value = currentValue;
    }

    return updateHelper;
  }
}
