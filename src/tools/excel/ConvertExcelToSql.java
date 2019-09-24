package tools.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 将 Excle 转换为 SQL 语句
 */
public class ConvertExcelToSql {

    private static ExcelListener<Medicine> listener = new ExcelListener<>();

    public static void main(String[] args) throws Exception {
        File excelFile = new File(MyUrlDemo.class.getResource("init_00009999_20190923.xlsx").getPath());

        InputStream inputStream = new FileInputStream(excelFile);
        //InputStream in, Object customContent, AnalysisEventListener eventListener

        ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
        excelReader.read(new Sheet(1, 1, Medicine.class));

        // INSERT INTO R_Company_Medicine (COMPANYID, MEDICINEID, COMEDICINEID, COMEDICINENAME, MICODE, MINAME, PRICE, MINPRICE, BARCODE, STATUS)
        // VALUES ('392bdc8c36874717bbc3b089c4820278',(select MEDICINEID from T_Medicine where MINAME='参一胶囊（限制条件详见人社部36种谈判药品目录）' and MICODE='02V999346300014'),
        // '132229','参一胶囊','02V999346300014','参一胶囊（限制条件详见人社部36种谈判药品目录）','0.0','0','6927301600033','1');

        int count = 0;
        List<Medicine> medicines = listener.getRows();
        for (Medicine m : medicines) {
            count++;
            String sql = "INSERT INTO R_Company_Medicine (COMPANYID, MEDICINEID, COMEDICINEID, COMEDICINENAME, MICODE, MINAME, PRICE, MINPRICE, BARCODE, STATUS) VALUES ('" +
                    "testCompany'," +
                    "(select MEDICINEID from T_Medicine where MINAME='" + m.getMiName() + "' and MICODE='" + m.getMiCode() + "'),'" +
                    m.getCoCode() + "','" +
                    m.getMiName() + "','" +
                    m.getMiCode() + "','" +
                    m.getMiName() + "','" +
                    m.getPrice() + "','" +
                    "0','" +
                    m.getBarCode() + "','" +
                    "1');";
            System.out.println(sql);
        }
        System.out.println(count);
    }


    /**
     * 获取ID（32位）
     */
    private static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }


    /**
     * 提取每个汉字的首字母
     * https://blog.csdn.net/sky_limitless/article/details/79443540
     */
    private static String getPinYinHeadChar(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            //提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                sb.append(pinyinArray[0].charAt(0));
            }/* else {
                sb.append(word);
            }*/
        }
        return sb.toString().toUpperCase();
    }


    public static final int MD_STATUS_DELETE = 0;//删除
    public static final int MD_STATUS_NO_SELL = 1;//下架
    public static final int MD_STATUS_SELLING = 2;//上架

    //药品分类
    private static HashMap<String, String> CLASSIFY_MAP;
    //收费大类编码
    private static HashMap<String, String> BKE001_MAP;
    //收费项目等级
    private static HashMap<String, String> AKA065_MAP;

    static {
        CLASSIFY_MAP = new HashMap<>();
        CLASSIFY_MAP.put("药品", "1");
        CLASSIFY_MAP.put("医疗器械", "2");

        BKE001_MAP = new HashMap<>();
        BKE001_MAP.put("西药", "01");
        BKE001_MAP.put("中药、中成药", "02");
        BKE001_MAP.put("中药", "02");
        BKE001_MAP.put("中成药", "02");

        BKE001_MAP.put("器官购置", "15");
        BKE001_MAP.put("抢救用药", "17");
        BKE001_MAP.put("材料费", "31");
        BKE001_MAP.put("一般医疗服务", "51");
        BKE001_MAP.put("一般检查治疗", "52");
        BKE001_MAP.put("社区卫生服务及预防保健", "53");
        BKE001_MAP.put("其他医疗服务项目", "54");
        BKE001_MAP.put("床位费", "55");
        BKE001_MAP.put("医学影像", "61");
        BKE001_MAP.put("超声检查", "62");
        BKE001_MAP.put("核医学", "63");
        BKE001_MAP.put("临床各系统诊疗", "71");
        BKE001_MAP.put("经血管介入诊疗", "72");
        BKE001_MAP.put("手术治疗", "73");
        BKE001_MAP.put("物理治疗与康复", "74");
        BKE001_MAP.put("中医外治", "81");
        BKE001_MAP.put("中医骨伤", "82");
        BKE001_MAP.put("针刺", "83");
        BKE001_MAP.put("灸法", "84");
        BKE001_MAP.put("推拿疗法", "85");
        BKE001_MAP.put("中医肛肠", "86");
        BKE001_MAP.put("中医特殊疗法", "87");
        BKE001_MAP.put("中医综合", "88");
        BKE001_MAP.put("新增试用项目", "89");

        AKA065_MAP = new HashMap<>();
        AKA065_MAP.put("甲类", "1");
        AKA065_MAP.put("乙类", "2");
        AKA065_MAP.put("丙类", "3");
        AKA065_MAP.put("特检特治", "4");
    }

}
