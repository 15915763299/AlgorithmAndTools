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

public class ConvertExcelToSql {

    private static ExcelListener<Medicine> listener = new ExcelListener<>();

    public static void main(String[] args) throws Exception {
        File excelFile = new File(MyUrlDemo.class.getResource("init_2019-06-03.xlsx").getPath());

        InputStream inputStream = new FileInputStream(excelFile);
        //InputStream in, Object customContent, AnalysisEventListener eventListener

        ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
        excelReader.read(new Sheet(1, 1, Medicine.class));

        //INSERT INTO T_MEDICINE VALUES (
        //31150120920','testPharmacy2','厄贝沙坦片(安博维)','31150120920','HPSMTLEHSP','6950641900143',
        //'片剂','盒','0.15g*7T','1','赛诺菲（杭州）制药有限公司',
        //'厄贝沙坦片(安博维)','01V9168089100085','01','1','30.5','2');

        List<Medicine> medicines = listener.getRows();
        for (Medicine m : medicines) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO T_MEDICINE VALUES ('");
            sb.append(generateId());
            sb.append("','");
            sb.append("testPharmacy2");
            sb.append("','");
            sb.append(m.getHospName());
            sb.append("','");
            sb.append(m.getHospCode());
            sb.append("','");
            sb.append(getPinYinHeadChar(m.getHospName()));
            sb.append("','");
            sb.append(m.getBarCode());
            sb.append("','");
            sb.append(m.getDrugForm());
            sb.append("','");
            sb.append(m.getUnit());
            sb.append("','");
            sb.append(m.getSpec());
            sb.append("','");
            sb.append(CLASSIFY_MAP.get(m.getClassify()));
            sb.append("','");
            sb.append(m.getPlaceOfProd());
            sb.append("','");
            sb.append(m.getMiName());
            sb.append("','");
            sb.append(m.getMiCode());
            sb.append("','");
            sb.append(BKE001_MAP.get(m.getMiCategory()));
            sb.append("','");
            sb.append(m.getPrice());
            sb.append("','");
            sb.append(MD_STATUS_SELLING);
            sb.append("');");
            System.out.println(sb.toString());
        }

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

    static {
        CLASSIFY_MAP = new HashMap<>();
        CLASSIFY_MAP.put("药品", "1");
        CLASSIFY_MAP.put("医疗器械", "2");

        BKE001_MAP = new HashMap<>();
        BKE001_MAP.put("西药", "01");
        BKE001_MAP.put("中药、中成药", "02");
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
    }

}
