package net.jest.api.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.jest.api.util.JsonUtil;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleResponse implements Response {

    int statusCode;
    Object response;

    @Override
    public String getOutput() {
        return JsonUtil.to(this);
    }
}
