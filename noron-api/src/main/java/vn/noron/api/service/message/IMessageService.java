package vn.noron.api.service.message;

import io.reactivex.rxjava3.core.Single;
import vn.noron.commons.data.request.SendMessageRequest;
import vn.noron.data.request.message.CreateDialogRequest;
import vn.noron.data.tables.pojos.Dialog;

import java.util.Optional;

public interface IMessageService {

    Single<Boolean> createDialog(CreateDialogRequest request);

    Single<String> sendMessage(SendMessageRequest request);
}
