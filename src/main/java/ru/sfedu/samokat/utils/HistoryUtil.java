package ru.sfedu.samokat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.samokat.model.HistoryContent;

import static org.bson.Document.parse;
import static ru.sfedu.samokat.Constant.*;

public class HistoryUtil {
    private static final Logger log = LogManager.getLogger(HistoryUtil.class);

    public static void saveToLog(HistoryContent content) {
        try (MongoClient mongoClient = MongoClients.create(MONGO_CONNECT)) {
            mongoClient.getDatabase(MONGO_DATABASE).getCollection(MONGO_COLLECTION).insertOne(parse(objectToString(content)));
        } catch (Exception e) {
            log.error(e);
        }
    }

    private static String objectToString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
