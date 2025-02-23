package kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation;

import kz.balaguide.common_module.core.exceptions.buisnesslogic.BusinessLogicException;

public class FinancialOperationException extends BusinessLogicException {
    public FinancialOperationException(String message) {
        super(message);
    }
}
