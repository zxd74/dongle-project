//package test;
//
//import com.iwanvi.nvwa.common.utils.JsonUtils;
//import com.iwanvi.nvwa.dao.model.SdkReport;
//import com.iwanvi.nvwa.dao.model.SettlementReport;
//import com.iwanvi.nvwa.web.service.SDKReportService;
//import com.iwanvi.nvwa.web.service.SettlementService;
//import com.iwanvi.nvwa.web.util.UploadOssHandler;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.List;
//
//public class UploadTest extends BaseTest {
//
//    @Autowired
//    private SettlementService settlementService;
//    @Autowired
//    private SDKReportService sdkReportService;
//
////    @Test
//    public void uploadTest() {
//        try {
//            UploadOssHandler handler = UploadOssHandler.values()[0];
//            File file = new File("/Users/li/Desktop/228*150.png");
//            MultipartFile mFile = new MockMultipartFile(file.getName(),file.getName(),null, new FileInputStream(file));
//            String path = handler.uploadFile(mFile);
//            System.out.println(path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    @Test
//    public void uploadExcelTest() {
//        String path = "/Users/li/Downloads/广点通.xls";
//        try {
//            File file = new File(path);
//            MultipartFile mFile = new MockMultipartFile(file.getName(),file.getName(),null,
//                    new FileInputStream(file));
//            sdkReportService.importSDKReport(mFile, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    @Test
//    public void uploadSettleTest() {
//        String path = "/Users/li/Desktop/结算报表.xls";
//        try {
//            File file = new File(path);
//            MultipartFile mFile = new MockMultipartFile(file.getName(),file.getName(),null,
//                    new FileInputStream(file));
//            settlementService.importSettlementReport(mFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    @Test
//    public void sdkSumTest() {
//        List<SdkReport> list = sdkReportService.sumSDKReportWithGroup("1",null, null,
//                null ,"app_id", 20190301,20190401);
//        System.out.println(JsonUtils.TO_JSON(list));
//    }
//
////    @Test
//    public void sdkSumDayTest() {
//        List<SdkReport> list = sdkReportService.sumSDKReportWithDate("1",null, null,
//                null ,20190301,20190401);
//        System.out.println(JsonUtils.TO_JSON(list));
//    }
//
////    @Test
//    public void settleSumDayTest() {
//        List<SettlementReport> list = settlementService.sumWithDate("1",null, null,
//                null, null, 20190612, 20190612);
//        System.out.println(JsonUtils.TO_JSON(list));
//        List<SettlementReport> list1 = settlementService.sumWithGroup("1",null, null,
//                null, null, "channel", 20190612, 20190612);
//        System.out.println(JsonUtils.TO_JSON(list1));
//        List<SettlementReport> list2 = settlementService.detailReport("1","1",null, null,
//                null, null, null, 20190612, 20190612);
//        System.out.println(JsonUtils.TO_JSON(list2));
//    }
//}
