package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.SdkReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SDKReportService {

    void importSDKReport(MultipartFile file, Integer type);

    List<SdkReport> sumSDKReportWithDate(String flowCons, String apps, String pids, String flowPosIds,
                                          Integer sDate, Integer eDate);

    List<SdkReport> sumSDKReportWithGroup(String flowCons, String apps, String pids, String flowPosIds, String group,
                                          Integer sDate, Integer eDate);

    List<SdkReport> detailSDKReport(String id, String flowCons, String apps, String pids, String flowPosIds,
                                    String group, Integer sDate, Integer eDate);
}
