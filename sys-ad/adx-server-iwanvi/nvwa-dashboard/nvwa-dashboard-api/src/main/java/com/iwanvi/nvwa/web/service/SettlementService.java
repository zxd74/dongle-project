package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.SettlementReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SettlementService {

    void importSettlementReport(MultipartFile file);

    List<SettlementReport> sumWithDate(String flowCons, String apps, String pids, String flowPosIds,
                                       String channels, Integer sDate, Integer eDate);

    List<SettlementReport> sumWithGroup(String flowCons, String apps, String pids, String flowPosIds,
                                        String channels, String group, Integer sDate, Integer eDate);

    List<SettlementReport> detailReport(String id, String flowCons, String apps, String pids, String flowPosIds,
                                        String channels, String group, Integer sDate, Integer eDate);
}
