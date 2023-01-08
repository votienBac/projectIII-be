package vn.noron.data.mapper;

import org.mapstruct.*;
import vn.noron.data.request.message.CreateDialogRequest;
import vn.noron.data.tables.pojos.Dialog;
import vn.noron.utils.message.MessageUtils;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DialogMapper {


    @Named("createToPojo")
    public abstract Dialog toPOJO(CreateDialogRequest request);

    @IterableMapping(qualifiedByName = "createToPojo")
    public abstract List<Dialog> toPOJOs(List<CreateDialogRequest> requests);


    @AfterMapping
    public void afterToPOJO(@MappingTarget Dialog target, CreateDialogRequest source) {
        target.setTopic(MessageUtils.createTopicForDialog(source));
    }
}
