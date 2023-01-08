package vn.noron.api.service.message;

import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;
import vn.noron.api.service.fcm.IFcmService;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.commons.data.request.SendMessageRequest;
import vn.noron.data.mapper.DialogMapper;
import vn.noron.data.request.message.CreateDialogRequest;
import vn.noron.data.tables.pojos.FcmTokenUser;
import vn.noron.repository.dialog.IDialogRepository;
import vn.noron.repository.fcm.ITokenUserRepository;

import java.util.List;

import static vn.noron.data.Tables.*;
import static vn.noron.utils.CollectionUtils.extractField;

@Service
public class MessageServiceImpl implements IMessageService {

    private final IDialogRepository dialogRepository;
    private final DialogMapper dialogMapper;
    private final ITokenUserRepository tokenUserRepository;
    private final IFcmService fcmService;

    public MessageServiceImpl(IDialogRepository dialogRepository, DialogMapper dialogMapper, ITokenUserRepository tokenUserRepository, IFcmService fcmService) {
        this.dialogRepository = dialogRepository;
        this.dialogMapper = dialogMapper;
        this.tokenUserRepository = tokenUserRepository;
        this.fcmService = fcmService;
    }

    @Override
    public Single<Boolean> createDialog(CreateDialogRequest request) {
        return dialogRepository.insertOnConflictKeyUpdate(dialogMapper.toPOJO(request),
                        List.of(DIALOG.FIRST_USER_ID, DIALOG.SECOND_USER_ID))
                .flatMap(optDialog -> optDialog
                        .map(dialog -> tokenUserRepository.getByUserIds(List.of(dialog.getFirstUserId(),
                                        dialog.getSecondUserId()))
                                .flatMap(fcmTokenUsers -> {
                                    List<String> tokens = extractField(fcmTokenUsers, FcmTokenUser::getToken);
                                    return fcmService.registerTokensToTopic(tokens, dialog.getTopic());
                                }))
                        .orElseGet(() -> Single.error(new ApiException("Dialog not found")))
                );
    }

    @Override
    public Single<String> sendMessage(SendMessageRequest request){
        return null;
    }
}
