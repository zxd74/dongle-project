//package test;
//
//import com.iwanvi.nvwa.common.utils.JsonUtils;
//import com.iwanvi.nvwa.dao.FlowSourceMapper;
//import com.iwanvi.nvwa.dao.model.FlowSource;
//import com.iwanvi.nvwa.dao.model.QuotaBook;
//import com.iwanvi.nvwa.dao.model.QuotaFlow;
//import com.iwanvi.nvwa.dao.model.QuotaFlowArea;
//import com.iwanvi.nvwa.web.service.*;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Map;
//
//public class QuotaTest extends BaseTest {
//
//    @Autowired
//    private QuotaFlowService quotaFlowService;
//    @Autowired
//    private QuotaBookService quotaBookService;
//    @Autowired
//    private FlowDataService flowDataService;
//    @Autowired
//    private FlowSourceMapper flowSourceMapper;
//    @Autowired
//    private ConsumerDataService consumerDataService;
//    @Autowired
//    private SDKReportService sdkReportService;
//    @Autowired
//    private QuotaFlowAreaService quotaFlowAreaService;
//
////    @Test
//    public void flowTest() {
////        List<FlowSource> flowSources = flowSourceMapper.selectByExample(null);
////        List list = flowDataService.getAllData(flowSources,"20190425", "20190425");
////        System.out.println(JsonUtils.TO_JSON(list));
//
//        List list1 = flowDataService.getOneFlowDataByHour(0);
//        System.out.println(JsonUtils.TO_JSON(list1));
//        List list2 = flowDataService.getOneFlowDataByHour(1);
//        System.out.println(JsonUtils.TO_JSON(list2));
//    }
//
////    @Test
//    public void sumFlowTest() {
////        List<QuotaFlow> sumDayList = quotaFlowService.sumWithDate("M7nuyi", "",
////                "", "", "", "",
////                20190523, 20190523);
////        System.out.println(JsonUtils.TO_JSON(sumDayList));
//        List<QuotaFlow> sumList = quotaFlowService.sumWithGroup("M7nuyi", "",
//                "", "", "", "","adpid",
//                20190523, 20190524);
//        System.out.println(JsonUtils.TO_JSON(sumList));
////        List<QuotaFlow> detailList = quotaFlowService.detailReport("maeA3e","M7nuyi", "",
////                "", "", "", "appid",
////                20190401, 20190425);
////        System.out.println(JsonUtils.TO_JSON(detailList));
//    }
//
////    @Test
//    public void sumBookTest() {
////        List<QuotaBook> sumDayList = quotaBookService.sumWithDate(20190520, 20190520, "",
////                "4,5,6,12","17,18,10");
////        System.out.println(JsonUtils.TO_JSON(sumDayList));
//        List<QuotaBook> sumList = quotaBookService.sumWithGroup(20190425, 20190425, "1,2,3,7",
//                "","", "level1");
//        System.out.println(JsonUtils.TO_JSON(sumList));
////        List<QuotaBook> detailList = quotaBookService.detailReport(7,20190425, 20190425, "",
////                "","", "level1");
////        System.out.println(JsonUtils.TO_JSON(detailList));
//    }
//
////    @Test
//    public void consumerDateTest() {
////        List list = consumerDataService.getOneFcByhour("20190506", "20190506", "2", 0);
////        System.out.println(JsonUtils.TO_JSON(list));
////        List list1 = consumerDataService.sumByDay( "toutiao","","",20190620, 20190620);
////        System.out.println(JsonUtils.TO_JSON(list1));
////        List list6 = consumerDataService.sumByDayWithGroup( "toutiao","","","adpid", 20190620, 20190620);
////        System.out.println(JsonUtils.TO_JSON(list6));
//        List list2 = consumerDataService.sumByDayWithGroup( "toutiao,360,ENfmEr,e6jaAn,2MFfYj,NFbqiu,BR7nyi,N7vqAj,IJJJRf,miYnia,qiU3U3,Q7b2ye,2EJZVb,6jE3Mv,aAZ3mq,VVR7j2,neQj22,R7zuam,nmiqIb,imQzem,2UvmIj,aya6Jv,BFNb2y,iMviQv,zeQNNb","","","platform_id", 20190620, 20190620);
//        System.out.println(JsonUtils.TO_JSON(list2));
////        List list4 = consumerDataService.detailReport("qM7Jza", "toutiao,360,ENfmEr,e6jaAn,2MFfYj,NFbqiu,BR7nyi,N7vqAj,IJJJRf,miYnia,qiU3U3,Q7b2ye","","","adpid",20190531, 20190531);
////        System.out.println(JsonUtils.TO_JSON(list4));
////        List list5 = consumerDataService.detailReport("qM7Jza", "toutiao","","","adpid",20190531, 20190531);
////        System.out.println(JsonUtils.TO_JSON(list5));
////        Map list3 = consumerDataService.sumItemByDay( "toutiao,360,ENfmEr,e6jaAn,2MFfYj,NFbqiu,BR7nyi,N7vqAj,IJJJRf,miYnia,qiU3U3,Q7b2ye","","", 20190603, 20190603, "req", "adpid");
////        System.out.println(JsonUtils.TO_JSON(list3));
//
////        Map map = consumerDataService.getPlatformsDataByHour("201905010","20190530","2,5,6,7,9,10,12,13,15,16,17,18","req");
////        System.out.println(JsonUtils.TO_JSON(map));
//    }
//
////    @Test
//    public void sdkTest() {
//        List list = sdkReportService.sumSDKReportWithGroup("1","2,3,4,5","62","123","type",20190501,20190530);
//        System.out.println(JsonUtils.TO_JSON(list));
//    }
//
////    @Test
//    public void syncTest() {
//        for (int i = 0; i < 5; i++) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    List list2 = consumerDataService.sumByDayWithGroup( "toutiao","","","platform_id", 20190528, 20190528);
//                    System.out.println(JsonUtils.TO_JSON(list2));
//                    List list4 = consumerDataService.detailReport("toutiao", "toutiao","","","platform_id",20190528, 20190528);
//                    System.out.println(JsonUtils.TO_JSON(list4));
//                }
//            });
//            thread.run();
//        }
//    }
//
////    @Test
//    public void quotaAreaTest() {
////        List<QuotaFlowArea> list = quotaFlowAreaService.sumByDay(20190614, 20190614, "");
////        System.out.println(JsonUtils.TO_JSON(list));
//        List<QuotaFlowArea> list1 = quotaFlowAreaService.detailReport(20190610, 20190618, "110000");
//        System.out.println(JsonUtils.TO_JSON(list1));
//    }
//
//
//    public static void main(String[] args) {
//        System.out.println(Double.parseDouble(String.format("%.2f", ((0l / 11) * 100.0))));
//    }
//}
