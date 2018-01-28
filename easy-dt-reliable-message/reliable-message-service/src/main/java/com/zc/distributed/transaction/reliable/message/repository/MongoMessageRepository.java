package com.zc.distributed.transaction.reliable.message.repository;

import com.mongodb.WriteResult;
import com.zc.distributed.transaction.reliable.message.dto.TransactionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2018/01/27 下午11:38
 * <p>
 * Company: xxx
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class MongoMessageRepository implements MessageRepository {

    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "transaction_message";

    @Autowired
    public MongoMessageRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public TransactionMessage getByMessageId(String messageId) {
        Query query = new Query(Criteria.where("messageId").is(messageId));
        return mongoTemplate.findOne(query, TransactionMessage.class, COLLECTION_NAME);
    }

    @Override
    public String saveMessage(TransactionMessage message) {
        if (message == null) {
            throw new NullPointerException("TransactionMessage can`t be null");
        }
        mongoTemplate.save(message, COLLECTION_NAME);
        return message.getMessageId();
    }

    @Override
    public int updateByMessageId(TransactionMessage message) {
        if (message == null) {
            throw new NullPointerException("TransactionMessage can`t be null");
        }

        Query query = new Query(Criteria.where("messageId").is(message.getMessageId()));

        Update update = getUpdate(message);
        if (update == null) {
            return 0;
        }
        WriteResult writeResult = mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        return writeResult.getN();
    }

    @Override
    public int deleteByMessageId(String messageId) {
        Query query = new Query(Criteria.where("messageId").is(messageId));
        WriteResult writeResult = mongoTemplate.remove(query, COLLECTION_NAME);
        return writeResult.getN();
    }

    @Override
    public List<TransactionMessage> selectMultiple(TransactionMessage message,int skip, int limit) {
        Query query = getQuery(message);
        query.skip(skip);
        query.limit(limit);
        return mongoTemplate.find(query, TransactionMessage.class, COLLECTION_NAME);
    }

    @Override
    public long count(TransactionMessage message) {
        Query query = getQuery(message);
        return mongoTemplate.count(query, COLLECTION_NAME);
    }


    private Query getQuery(Object obj) {
        Criteria criteria = new Criteria();
        List<Field> fieldList = getNotEmptyFiled(obj);
        for (Field f : fieldList) {
            f.setAccessible(true);
            try {
                Object val = f.get(obj);
                if (!StringUtils.isEmpty(val)) {
                    criteria.and(f.getName()).is(val);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return new Query(criteria);
    }


    private Update getUpdate(Object obj) {
        List<Field> fieldList = getNotEmptyFiled(obj);
        Update update = null;
        if (fieldList.size() > 0) {
            update = new Update();
            for (Field f : fieldList) {
                f.setAccessible(true);
                try {
                    Object val = f.get(obj);
                    if (!StringUtils.isEmpty(val)) {
                        update.set(f.getName(), val);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return update;
    }


    private List<Field> getNotEmptyFiled(Object obj) {
        List<Field> fieldList = new ArrayList<>();
        if (obj != null) {
            Class clazz = obj.getClass();
            while (clazz != null) {
                fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();
            }
        }
        return fieldList;
    }
}
