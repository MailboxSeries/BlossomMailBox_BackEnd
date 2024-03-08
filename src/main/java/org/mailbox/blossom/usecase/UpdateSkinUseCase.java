package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.request.SkinInfoDto;

@UseCase
public interface UpdateSkinUseCase {
    void updateSkin(String userId, SkinInfoDto skinInfoDto);
}
