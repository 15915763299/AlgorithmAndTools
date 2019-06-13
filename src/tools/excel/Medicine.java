package tools.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class Medicine extends BaseRowModel {
// 药品名称（必填）	药品编码
// 条码	            药品价格（必填）
// 剂型	            单位
// 规格	            药品分类(必填)
// 生产厂家	        医保药品名称(必填）
// 医保药品编码(必填)	医保收费大类(必填)	    医保项目等级

    @ExcelProperty(value = "药品名称（必填）", index = 0)//A
    private String hospName;

    @ExcelProperty(value = "药品编码", index = 1)//B
    private String hospCode;

    @ExcelProperty(value = "条码", index = 2)//C
    private String barCode;

    @ExcelProperty(value = "药品价格（必填）", index = 3)//D
    private String price;

    @ExcelProperty(value = "剂型", index = 4)//E
    private String drugForm;

    @ExcelProperty(value = "单位", index = 5)//F
    private String unit;

    @ExcelProperty(value = "规格", index = 6)//G
    private String spec;

    @ExcelProperty(value = "药品分类(必填)", index = 7)//H
    private String classify;

    @ExcelProperty(value = "生产厂家", index = 8)//I
    private String placeOfProd;

    @ExcelProperty(value = "医保药品名称(必填）", index = 9)//J
    private String miName;

    @ExcelProperty(value = "医保药品编码(必填)", index = 10)//K
    private String miCode;

    @ExcelProperty(value = "医保收费大类(必填)", index = 11)//L
    private String miCategory;

    @ExcelProperty(value = "医保项目等级", index = 12)//M
    private String miItemLevel;


    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getHospCode() {
        return hospCode;
    }

    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
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

    public String getDrugForm() {
        return drugForm;
    }

    public void setDrugForm(String drugForm) {
        this.drugForm = drugForm;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getPlaceOfProd() {
        return placeOfProd;
    }

    public void setPlaceOfProd(String placeOfProd) {
        this.placeOfProd = placeOfProd;
    }

    public String getMiName() {
        return miName;
    }

    public void setMiName(String miName) {
        this.miName = miName;
    }

    public String getMiCode() {
        return miCode;
    }

    public void setMiCode(String miCode) {
        this.miCode = miCode;
    }

    public String getMiCategory() {
        return miCategory;
    }

    public void setMiCategory(String miCategory) {
        this.miCategory = miCategory;
    }

    public String getMiItemLevel() {
        return miItemLevel;
    }

    public void setMiItemLevel(String miItemLevel) {
        this.miItemLevel = miItemLevel;
    }
}
