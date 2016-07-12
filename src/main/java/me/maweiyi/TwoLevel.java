package me.maweiyi;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by maweiyi on 7/11/16.
 */
public class TwoLevel {

    @JSONField(name = "pageSize")
    private Integer pageSize;

    private ThreeLevel positionResult;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public ThreeLevel getPositionResult() {
        return positionResult;
    }

    public void setPositionResult(ThreeLevel positionResult) {
        this.positionResult = positionResult;
    }
}
