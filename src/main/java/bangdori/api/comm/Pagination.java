package bangdori.api.comm;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class Pagination {

    int pageSize = Constants.VALUE_PAGE_DEFAULT_PAGE_SIZE;
    int pageNum =1;
    int startRow = 1;
    int endRow = pageSize * pageNum;
    int totalRow = 0;
    int pageCnt = 0;

    public void setPageSize(String pageSize) {
        if(pageSize !=null && !pageSize.isEmpty()) {
            setPageSize(Integer.parseInt(pageSize));
        }
    }

    public void setPageNum(String pageNum) {
        if(pageNum!=null && !pageNum.isEmpty()) {
            setPageNum(Integer.parseInt(pageNum));
        }
    }

    public void setPageSize(int pageSize) {
        if(pageSize > 0 ) {
            this.pageSize = pageSize;
            calcStartEndRow();
        }
    }

    public void setPageNum(int pageNum) {
        if(pageNum > 0) {
            this.pageNum = pageNum;
            calcStartEndRow();
        }
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
        this.pageCnt = (this.totalRow/this.pageSize)+1;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.KEY_PAGE_TOTAL_ROW, this.totalRow);
        result.put(Constants.KEY_PAGE_PAGE_COUNT, this.pageCnt);
        result.put(Constants.KEY_PAGE_PAGE_SIZE, this.pageSize);
        result.put(Constants.KEY_PAGE_PAGE_NUMBER, this.pageNum);
        result.put(Constants.KEY_PAGE_START_ROW, this.startRow);
        return result;
    }

    private void calcStartEndRow() {
        this.startRow = ((this.pageNum-1)*this.pageSize)+1;
        this.endRow = this.pageNum * this.pageSize;
    }

}


//서비스단 사용예제
//@Override
//@InjectUserDetails
//public Map<String, Object> selectPbinCntrList(Map<String, Object> inParams) throws Exception {
//    Pagination pagination = new Pagination();
//    pagination.setPageNum((String)inParams.get("pageNum"));
//    pagination.setPageSize((String)inParams.get("pageSize"));
//
//    inParams.put("order", "N");
//    Map<String, Object> countMap = ctmnMapper.selectPbinCntrListCount(inParams);
//    pagination.setTotalRow(Integer.parseInt(String.valueOf(countMap.get(Constants.KEY_PAGE_TOTAL_ROW))));
//
//    inParams.put("order", "Y");
//    inParams.put("startRow", pagination.getStartRow());
//    inParams.put("endRow", pagination.getEndRow());
//
//    Map<String, Object> result = new HashMap<String, Object>();
//    result.putAll(pagination.toMap());
//    result.put(Constants.KEY_LIST, ctmnMapper.selectPbinCntrListPage(inParams));
//    return result;
//}