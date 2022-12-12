package vn.noron.commons.utils;

import com.google.common.base.CaseFormat;
import com.mongodb.client.model.InsertOneModel;
import io.vertx.core.json.JsonObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Updates.set;
import static io.vertx.core.json.JsonObject.mapFrom;
import static java.util.stream.Collectors.toList;
import static vn.noron.data.constant.GenericFieldConstant._ID;

public class ObjTransformUtils {
    private ObjTransformUtils() {
    }

    public static <T> Document objToDocument(T obj) {
        JsonObject json = mapFrom(obj);
        Document doc = new Document();
        json.getMap().forEach((key, value) -> {
            if (value != null)
                doc.append(snakeCase(key), value);
        });
        doc.append(_ID, new ObjectId().toString());
        return doc;
    }

    public static <T> List<Document> objToDocument(List<T> objs) {
        return objs.stream().map(ObjTransformUtils::objToDocument).collect(toList());
    }


    public static <T> List<InsertOneModel<Document>> toDocInsertModel(Collection<T> collection) {
        return collection.stream()
                .map(obj -> new InsertOneModel<>(objToDocument(obj)))
                .collect(toList());
    }

    public static <T> List<Bson> toDocUpdate(T obj, String... ignoreKey) {
        JsonObject json = mapFrom(obj);
        Arrays.stream(ignoreKey).forEach(json::remove);
        List<Bson> updateBsons = new ArrayList<>();
        json.getMap().forEach((s, o) -> {
            if (o != null) updateBsons.add(set(snakeCase(s), o));
        });
        return updateBsons;
    }

    private static String snakeCase(String input) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
    }

    public static <T> Document toDocumentInsert(T obj) {
        JsonObject json = mapFrom(obj);
        Document doc = new Document();
        json.getMap().forEach((key, value) -> {
            if (value != null) doc.append(key, value);
        });
        return doc;
    }
}
