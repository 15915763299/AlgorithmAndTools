package tools.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class Medicine extends BaseRowModel {

    @ExcelProperty(value = "公司药品名称", index = 0)//A
    private String coName;

    @ExcelProperty(value = "公司药品编码", index = 1)//B
    private String coCode;

    @ExcelProperty(value = "条形码", index = 2)//C
    private String barCode;

    @ExcelProperty(value = "药品单价", index = 3)//D
    private String price;

    @ExcelProperty(value = "医保目录编码", index = 4)//E
    private String miCode;

    @ExcelProperty(value = "医保目录名称", index = 5)//F
    private String miName;

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMiCode() {
        return miCode;
    }

    public void setMiCode(String miCode) {
        this.miCode = miCode;
    }

    public String getMiName() {
        return miName;
    }

    public void setMiName(String miName) {
        this.miName = miName;
    }
}
