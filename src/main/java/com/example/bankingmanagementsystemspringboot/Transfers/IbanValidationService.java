
package com.example.bankingmanagementsystemspringboot.Transfers;

import org.iban4j.Iban4jException;
import org.iban4j.IbanUtil;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class IbanValidationService {

    public boolean isValidIban(String iban) {

        if (iban == null || iban.isBlank()) {
            return false;
        }

        String normalizedIban = iban.replaceAll("\\s", "").toUpperCase(Locale.ROOT);

        try {
            IbanUtil.validate(normalizedIban);
            return true;
        } catch (Iban4jException exception) {
            return false;
        }
    }
}
