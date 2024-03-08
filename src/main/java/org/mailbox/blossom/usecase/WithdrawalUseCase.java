package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;

@UseCase
public interface WithdrawalUseCase {
    void withdrawal(String userId);
}
