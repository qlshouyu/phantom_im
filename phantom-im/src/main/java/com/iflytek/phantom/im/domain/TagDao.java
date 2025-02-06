package com.iflytek.phantom.im.domain;

import com.qlshouyu.jframework.db.model.DBEntity;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description:
 * @author: 高露 lugao2
 * @create: 2025/2/4
 * @Version 1.0.0
 */
@Data
@Document(collection = "tags")
public class TagDao extends DBEntity<ObjectId> {

    private String code;


}
