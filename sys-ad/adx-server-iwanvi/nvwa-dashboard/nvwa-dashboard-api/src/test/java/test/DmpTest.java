//package test;
//
//import com.iwanvi.nvwa.common.utils.Constants;
//import com.iwanvi.nvwa.common.utils.JsonUtils;
//import com.iwanvi.nvwa.common.utils.StringUtils;
//import com.iwanvi.nvwa.dao.model.DmpDatas;
//import com.iwanvi.nvwa.dao.model.DmpDatasource;
//import com.iwanvi.nvwa.web.service.DmpDatasService;
//import com.iwanvi.nvwa.web.service.DmpDatasourceService;
//import com.iwanvi.nvwa.web.util.WebConstants;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class DmpTest extends BaseTest {
//    @Autowired
//    private DmpDatasourceService dmpDatasourceService;
//    @Autowired
//    private DmpDatasService dmpDatasService;
//
////    @Test
//    public void addTest() {
//        DmpDatasource datasource = new DmpDatasource();
//        datasource.setName("测试数据源");
//        datasource.setStatus(Constants.STATE_VALID);
//        datasource.setPaths("http://b.hiphotos.baidu.com/image/pic/item/11385343fbf2b2119202e609c78065380cd78e4c.jpg");
//        dmpDatasourceService.add(datasource);
//    }
//
////    @Test
//    public void previewTest() {
//        List<List<String>> list = dmpDatasService.preview(2,null,"|");
//        System.out.println(JsonUtils.TO_JSON(list));
//    }
//
////    @Test
//    public void transTest() {
//        String s = "asd|asdsad|dewff|ad";
//        String s1 = "asd;asdsad;dewff;ad";
//        String s2 = "asd asdsad dewff ad";
//        String s3 = "asd||asdsad||dewff||ad";
//        String s4 = "asd\rasdsad\rdewff\rad";
//        System.out.println(JsonUtils.TO_JSON(StringUtils.str2List(s,"|")));
//        System.out.println(JsonUtils.TO_JSON(StringUtils.str2List(s1,";")));
//        System.out.println(JsonUtils.TO_JSON(StringUtils.str2List(s2," ")));
//        System.out.println(JsonUtils.TO_JSON(StringUtils.str2List(s3,"||")));
//        System.out.println(JsonUtils.TO_JSON(StringUtils.str2List(s4,"\r")));
//        System.out.println(JsonUtils.TO_JSON(StringUtils.str2List(s4,"\\\r")));
//        System.out.println(s3.contains("||"));
//    }
//}
