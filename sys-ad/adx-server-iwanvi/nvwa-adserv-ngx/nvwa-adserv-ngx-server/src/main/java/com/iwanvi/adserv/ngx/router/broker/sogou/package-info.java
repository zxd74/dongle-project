/**
 * @author: 郑晓东
 * @date: 2019-07-05 09:05
 * @version: v1.0
 * @Description:  搜狗DSP对接
 *      Http Get请求
 *      requestId ：与奇点沟通获取前缀，后拼接时间戳
 *      token：请求时需补充token，线下申请
 *      auth：若keyword不为空，则MD5(requestId+keyword+token)，否则MD5(requestId+token)，32位大写
 *      deviceId：安卓设备请使⽤用imei或imei的md5值，MD5值全大写， iOS设备请使⽤用idfa明文(大写)
 */
package com.iwanvi.adserv.ngx.router.broker.sogou;