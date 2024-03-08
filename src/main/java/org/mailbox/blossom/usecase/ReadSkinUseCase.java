package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.SkinListDto;

@UseCase
public interface ReadSkinUseCase {
    SkinListDto readSkin(String userId);
}
