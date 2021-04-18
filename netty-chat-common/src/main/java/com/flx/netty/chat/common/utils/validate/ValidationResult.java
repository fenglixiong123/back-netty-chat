package com.flx.netty.chat.common.utils.validate;

import com.flx.netty.chat.common.utils.result.ResultResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author fenglixiong
 * @date 2021-01-01 12:12
 */
@Data
public class ValidationResult {

    private final boolean success;

    private final Map<String, String> messageMap;

    public ValidationResult(boolean success, Map<String, String> messageMap) {
        this.success = success;
        this.messageMap = messageMap;
    }

    public static ValidationResult success() {
        return new ValidationResult(true, Collections.emptyMap());
    }


    public static ValidationResult failure() {
        return new ValidationResult(false, new HashMap<>());
    }

    public ValidationResult addMessage(String propertyPath, String message) {
        messageMap.put(propertyPath, message);
        return this;
    }

    /**
     * 用于将ValidationResult转换为ResultResponse
     * @return 返回对象
     */
    public ResultResponse toResponse() {
        if (success) {
            return ResultResponse.success();
        }
        List<String> errorMessageList = new ArrayList<>();
        for (Map.Entry<String, String> entry : messageMap.entrySet()) {
            String message = entry.getKey() + entry.getValue();
            errorMessageList.add(message);
        }
        return ResultResponse.error("Valid.Entity.Error", StringUtils.join(errorMessageList, "|"));
    }

}
