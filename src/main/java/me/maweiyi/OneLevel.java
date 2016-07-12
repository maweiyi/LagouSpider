package me.maweiyi;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by maweiyi on 7/11/16.
 */
public class OneLevel {

    @JSONField(name = "content")
    private TwoLevel content;

    public TwoLevel getContent() {
        return content;
    }

    public void setContent(TwoLevel content) {
        this.content = content;
    }
}
