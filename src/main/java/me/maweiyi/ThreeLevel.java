package me.maweiyi;

import java.util.List;

/**
 * Created by maweiyi on 7/11/16.
 */
public class ThreeLevel {

    private Integer totalCount;
    private Integer pageSize;
    private List<FourLevel> result;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<FourLevel> getResult() {
        return result;
    }

    public void setResult(List<FourLevel> result) {
        this.result = result;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
