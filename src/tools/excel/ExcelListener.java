package tools.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

    //private Logger logger = Logger.getLogger(ExcelListener.class.getSimpleName());

    private final List<T> rows = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext context) {
        rows.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //logger.info("read {} rows " + rows.size());
    }

    public List<T> getRows() {
        return rows;
    }
}