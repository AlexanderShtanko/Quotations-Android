package com.alexandershtanko.quotations.data.mappers;

import com.alexandershtanko.quotations.data.models.QuotationsListEntity;
import com.alexandershtanko.quotations.data.models.QuotationsResponseEntity;
import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 07/09/2017.
 *
 */

public class DataMapper {
    private final ObjectMapper objectMapper;

    @Inject
    public DataMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getSubscribeMessage(List<String> instruments) {
        return "SUBSCRIBE: " + StringUtils.join(instruments, ',');
    }

    public String getUnsubscribeMessage(List<String> instruments) {
        return "UNSUBSCRIBE: " + StringUtils.join(instruments, ',');
    }

    public QuotationsListEntity getQuotations(String message) {
        try {
            return objectMapper.readValue(message, QuotationsListEntity.class);
        } catch (IOException e) {
            try {
                QuotationsResponseEntity response = objectMapper.readValue(message, QuotationsResponseEntity.class);
                return response.getSubscribedList();
            } catch (IOException e1) {
                ErrorUtils.log(e);
                return null;
            }
        }
    }
}
