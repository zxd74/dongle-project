package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Entity;

import java.io.UnsupportedEncodingException;

/**
 * class
 *
 * @author hao
 * @date 2019/3/26.
 */
public interface AuditService {

    void auditEntity(Entity entity) throws UnsupportedEncodingException;

    void checkVideoStatus() throws UnsupportedEncodingException;
}
