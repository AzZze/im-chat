package com.aze.imchat.service;

import com.aze.imchat.entity.Audit;
import com.aze.imchat.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aze
 * @since 2023-06-22
 */
public interface AuditService extends IService<Audit> {

    R friendApplyList(Long userId);
}
