package vn.noron.utils.message;

import vn.noron.data.constant.Constant;
import vn.noron.data.request.message.CreateDialogRequest;
import vn.noron.data.tables.pojos.Dialog;

public class MessageUtils {

    public static String createTopicForDialog(CreateDialogRequest dialog){
        return "message_"+dialog.getFirstUserId()+"_"+dialog.getSecondUserId();
    }

}
