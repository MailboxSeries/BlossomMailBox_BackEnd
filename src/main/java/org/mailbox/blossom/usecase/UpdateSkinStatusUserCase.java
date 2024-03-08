package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.request.SkinStatusInfoDto;

@UseCase
public interface UpdateSkinStatusUserCase {
    void updateSkinStatus(String userId, SkinStatusInfoDto skinStatusInfoDto);
}
