import axios from 'axios';
// import Qs from 'qs'
import {
    Upload
} from 'element-ui';

let base = process.env.API_ROOT;


// 请求拦截器，添加访问token
axios.interceptors.request.use(config => {
    // Do something before request is sent
    config.headers.token = localStorage.getItem("t")
    config.headers['Content-Type'] = "application/json";

    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});
// http response 拦截器
axios.interceptors.response.use(
    response => {
        if(response.status == 403) {
            localStorage.removeItem("t");

        }
        return response;
    },
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 403:
                     localStorage.removeItem("t");


            }
        }
        return Promise.reject(error.response.data)   // 返回接口返回的错误信息
    });


export const appchannelsselectTwo= (url,params) => {
    return axios.post(`${base}/app-channels/selectTwo/${url}`,params).then(res => res);
};

    // 获取城市
export const getAreas = params => {
    return axios.get(`./static/areas.json`, {
        params: params
    });
};
// 流量源地区
export const qasumByDay = params => {
    return axios.get(`${base}/qa/sumByDay`, {
        params: params
    });
};
// 详情
export const qasumByDaydetail = params => {
    return axios.get(`${base}/qa/detail`, {
        params: params
    });
};
    
// ----------数据概览 ----------
// 流量数据
export const flowdatagetflowalldata = params => {
    return axios.get(`${base}/flowdata/getflowalldata`, {
        params: params
    });
};
//  客户、代理商数据概览接口
export const companyquotaByDay = params => {
    return axios.get(`${base}/company/quotaByDay`, {
        params: params
    });
};
//  平台数据
export const consumerdataall = params => {
    return axios.get(`${base}/consumerdata/all`, {
        params: params
    });
};


// ----------流量管理 ----------
//  export const flowSuress = params => { return axios.post(`${base}/flowsource/getallflowsource`, params).then(res => res.data); };
// 流量源管理
//列表
export const flowsourcegetall = params => {
    return axios.get(`${base}/flowsource/getall`, {
        params: params
    });
};
//新建
export const flowsourceadd = params => {
    return axios.get(`${base}/flowsource/add`, {
        params: params
    });
};
//编辑
// export const flowsourceupdate = params => {
//     return axios.get(`${base}/flowsource/update`, {
//         params: params
//     });
// };
//开关
export const flowsourceupdate = params => {
    return axios.get(`${base}/flowsource/update`, {
        params: params
    });
};
// 删除
export const flowsourcedel = params => {
    return axios.get(`${base}/flowsource/del`, {
        params: params
    });
};
// 模糊
export const flowsourcegetusers = params => {
    return axios.get(`${base}/flowsource/getusers`, {
        params: params
    });
};
// 用户权限
export const flowsourcegetgrand = params => {
    return axios.get(`${base}/flowsource/getgrand`, {
        params: params
    });
};
// 授权
export const flowsourcegrand = params => {
    return axios.get(`${base}/flowsource/grand`, {
        params: params
    });
};
// 授权删除
export const flowsourcemovegrand = params => {
    return axios.get(`${base}/flowsource/movegrand`, {
        params: params
    });
};

// 流量源APP管理
//列表
export const appgetlist = params => {
    return axios.get(`${base}/app/getlist`, {
        params: params
    });
};
export const flowconsumersdkList= params => {
    return axios.get(`${base}/flowconsumer/get-sdkList`, {
        params: params
    });
};
export const flowconsumerList= params => {
    return axios.get(`${base}/flowconsumer/getlist`, {
        params: params
    });
};
// 流量源名称
export const flowsourcegetlist = params => {
    return axios.get(`${base}/flowsource/getlist`, {
        params: params
    });
};
// 媒体类型
export const flowsourcegetallbytype = params => {
    return axios.get(`${base}/flowsource/getallbytype`, {
        params: params
    });
};
// 分类
export const diclist = params => {
    return axios.get(`${base}/dic/list`, {
        params: params
    });
};
// 新建
export const appadd = params => {
    return axios.post(`${base}/app/add`, params).then(res => res);
};
// 上传
export const upload = `${base}` + "/upload/upload";
// 广告位
export const getlistbyfsorplatForm = params => {
    return axios.get(`${base}/adPosition/getlistbyfsorplatForm`, {
        params: params
    });
};
// 编辑
export const appgetapp = params => {
    return axios.get(`${base}/app/getapp`, {
        params: params
    });
};
// 保存
export const appupdate = params => {
    return axios.post(`${base}/app/update`, params).then(res => res);
};
// 删除
export const appdelete = params => {
    return axios.get(`${base}/app/delete`, {
        params: params
    });
};
// 状态
export const appupdateStaus = params => {
    return axios.get(`${base}/app/updateStaus`, {
        params: params
    });
};

// 广告平台管理
//列表
export const flowconsumergetpage = params => {
    return axios.get(`${base}/flowconsumer/getpage`, {
        params: params
    });
};
export const flowconsumerlistbyapp = params => {
    return axios.get(`${base}/flowconsumer/list-by-app`, {
        params: params
    });
};
export const flowconsumerlistbymapped = params => {
    return axios.get(`${base}/flowconsumer/list-by-mapped`, {
        params: params
    });
};
//编辑
// export const flowconsumerget = params => { return axios.get(`${base}/flowconsumer/get`,{ params: params });};
//保存
export const flowconsumerupdate = params => {
    return axios.get(`${base}/flowconsumer/update`, {
        params: params
    });
};
// 新建
export const flowconsumeradd = params => {
    return axios.get(`${base}/flowconsumer/add`, {
        params: params
    });
};
// 删除
export const flowconsumerdel = params => {
    return axios.get(`${base}/flowconsumer/del`, {
        params: params
    });
};
// 广告位管理
export const flowconsumergetadps = params => {
    return axios.get(`${base}/flowconsumer/getadps`, {
        params: params
    });
};
// APP名称
export const appgetallfc = params => {
    return axios.get(`${base}/app/getall-fc`, {
        params: params
    });
};

export const flowconsumergetflowdx = params => {
    return axios.post(`${base}/flowconsumer/get-flow-dx`,params).then(res => res);
};
// 保存
export const flowconsumersetflowdx = params => {
    return axios.post(`${base}/flowconsumer/set-flow-dx`,params).then(res => res);
};
// 价格
export const consumerpositionpriceget = params => {
    return axios.get(`${base}/consumer-position-price/get`, {
        params: params
    });
};
// 保存价格
export const consumerpositionpriceset = params => {
    return axios.post(`${base}/consumer-position-price/set`,params).then(res => res);
};


// 广告平台广告主
// 列表
export const advertiserDspgetList = params => {
    return axios.get(`${base}/advertiserDsp/getList`, {
        params: params
    });
};
// 新建
export const advertiserDspadd = params => {
    return axios.get(`${base}/advertiserDsp/add`, {
        params: params
    });
};
// 编辑
export const advertiserDspupdate = params => {
    return axios.get(`${base}/advertiserDsp/update`, {
        params: params
    });
};
// 审核
export const advertiserDspget = params => {
    return axios.get(`${base}/advertiserDsp/get`, {
        params: params
    });
};
// 审核确认
export const advertiserDspexamines = params => {
    return axios.get(`${base}/advertiserDsp/examines`, {
        params: params
    });
};
// 广告平台创意
// 列表
export const entityDspgetList = params => {
    return axios.post(`${base}/entityDsp/getList`,params).then(res => res);
};
// 审核
export const entityDspget = params => {
    return axios.get(`${base}/entityDsp/get`, {
        params: params
    });
};
// 更新
export const entityDspupdate = params => {
    return axios.get(`${base}/entityDsp/update`, {
        params: params
    });
};
// 保存
export const entityDspexamines = params => {
    return axios.get(`${base}/entityDsp/examines`, {
        params: params
    });
};

// 广告模板管理
// 列表
export const templateList = params => {
    return axios.get(`${base}/template/getList`, {
        params: params
    });
};
export const listByPosition = params => {
    return axios.get(`${base}/template/listByPosition/`+params.id, {
        // params: params
    });
};
//  创建广告模版
export const templateAdd = params => {
    return axios.post(`${base}/template/add`, params).then(res => res);
}
// 获取广告模版信息
export const templateGet = params => {
    return axios.get(`${base}/template/get`, {
        params: params
    });
};
// 广告模板保存
export const templateupdate = params => {
    return axios.post(`${base}/template/update`, params).then(res => res);
}
// 广告模板状态开关
export const templateupdateStatus = params => {
    return axios.get(`${base}/template/updateStatus`,  {
        params: params
    });
}
// 模板验证
export const templatemodifiable = params => {
    return axios.get(`${base}/template/modifiable/`+params.id,  {
        // params: params
    });
}
export const modifiableByPosition = params => {
    return axios.get(`${base}/template/modifiableByPosition`,  {
        params: params
    });
}

// 广告位管理
// 新建
export const adPositionadd = params => {
    return axios.post(`${base}/adPosition/add`, params).then(res => res);
}
// 编辑
export const adPositionupdate = params => {
    return axios.post(`${base}/adPosition/update`, params).then(res => res);
}
// 广告位管理开关
export const  adPositionupdateStatus = params => {
    return axios.get(`${base}/adPosition/updateStatus`,  {
        params: params
    });
}
// 广告位验证
export const  adPositionmodifiable = params => {
    return axios.get(`${base}/adPosition/modifiable/`+params.id,  {
        // params: params
    });
}

// 广告位底价
// 行业
export const adPositionPriceaddIndustry = params => {
    return axios.get(`${base}/adPositionPrice/addIndustry`, {
        params: params
    });
};
// 行业
export const adPositiondeleteIndustry = params => {
    return axios.get(`${base}/adPositionPrice/deleteIndustry`, {
        params: params
    });
};
// 价格修改
export const adPositionPriceaupdate = params => {
    return axios.post(`${base}/adPositionPrice/update-price`, params).then(res => res);
};


// 地域等级
export const areaGroupgetList = params => {
    return axios.get(`${base}/areaGroup/getList`, {
        params: params
    });
};
// 地域等级新加
export const areaGroupadd = params => {
    return axios.get(`${base}/areaGroup/add`, {
        params: params
    });
};
// 地域等级删除
export const areaGroupdelete = params => {
    return axios.get(`${base}/areaGroup/delete`, {
        params: params
    });
};
// 地域搜索
export const areaGroupgetOptionalArea = params => {
    return axios.get(`${base}/areaGroup/getOptionalArea`, {
        params: params
    });
};
// 地域等级包涵城市
export const areaGroupget = params => {
    return axios.get(`${base}/areaGroup/get`, {
        params: params
    });
};
// 地域保存
export const areaGroupupdate = params => {
    return axios.get(`${base}/areaGroup/update`, {
        params: params
    });
};
// 广告位列表
export const adPositiongetAiKaPosition = params => {
    return axios.get(`${base}/adPosition/getAiKaPosition`, {
        params: params
    });
};
// 广告位新增
export const adPositionPriceadd = params => {
    return axios.get(`${base}/adPositionPrice/add`, {
        params: params
    });
};
// 广告位新增
export const adPositionPricegetList = params => {
    return axios.get(`${base}/adPositionPrice/getList`, {
        params: params
    });
};
// 修改价格
export const adPositionPricegetupdate = params => {
    return axios.get(`${base}/adPositionPrice/update`, {
        params: params
    });
};

// 流量控制
export const flowcontrolpage = params => {
    return axios.post(`${base}/flowcontrol/page`, params).then(res => res);
};
export const flowcontrolbatchinsert = params => {
    return axios.post(`${base}/flowcontrol/batch-insert`, params).then(res => res);
};
export const flowcontrolbatchdel = params => {
    return axios.post(`${base}/flowcontrol/batch-del`, params).then(res => res);
};
export const flowcontroldel = params => {
    return axios.get(`${base}/flowcontrol/del/${params.id}`, {
        params: params
    });
};
export const prohibitedateget = params => {
    return axios.get(`${base}/prohibite-date/get`, {
        params: params
    });
};
// 详情
export const flowcontrol = params => {
    return axios.get(`${base}/flowcontrol/${params.id}`, {
        params: params
    });
};
export const flowcontrolflowswitch = params => {
    return axios.get(`${base}/flowcontrol/flow-switch`, {
        params: params
    });
};
export const flowcontrolupdate = params => {
    return axios.post(`${base}/flowcontrol/update`, params).then(res => res);
};
export const prohibitedateset = params => {
    return axios.post(`${base}/prohibite-date/set`, params).then(res => res);
};
export const AppVersionselect = params => {
    return axios.get(`${base}/app-versions/select`, {
        params: params
    });
};
export const AppChannelselect = params => {
    return axios.get(`${base}/app-channels/select`, {
        params: params
    });
};
// 一级渠道号
export const AppChannelselectOne = params => {
    return axios.get(`${base}/app-channels/selectOne`, {
        params: params
    });
};
// 二级渠道号
export const AppChannelselectTwo = params => {
    return axios.post(`${base}/app-channels/selectTwo/`+name, params).then(res => res);
};
// 批量操作
export const flowcontrolbatchupdate = params => {
    return axios.post(`${base}/flowcontrol/batch-update`, params).then(res => res);
};

// SDK广告轮播策略
export const adcarouselpage = params => {
    return axios.post(`${base}/adcarousel/page`, params).then(res => res);
};
export const appgetall = params => {
    return axios.get(`${base}/app/getall`, {
        params: params
    });
};
export const appgetone = params => {
    return axios.get(`${base}/app/getone`, {
        params: params
    });
};
export const appgetsdkone = params => {
    return axios.get(`${base}/app/get-sdk-one`, {
        params: params
    });
};
export const adcarousel = params => {
    return axios.post(`${base}/adcarousel`, params).then(res => res);
};
export const adcarousel2 = params => {
    return axios.get(`${base}/adcarousel/${params.id}`, {
        params: params
    });
};
export const adcarouselgetdefult = params => {
    return axios.get(`${base}/adcarousel/get-defult`, {
        params: params
    });
};
export const adcarouselsetdefult = params => {
    return axios.post(`${base}/adcarousel/set-defult`, params).then(res => res);
};
export const adcarouselgetadpositionlist = params => {
    return axios.get(`${base}/adcarousel/get-adposition-list`, {
        params: params
    });
};
export const appselectfc = params => {
    return axios.get(`${base}/app/select-fc`, {
        params: params
    });
};
export const appchannelsselectfc = params => {
    return axios.get(`${base}/app-channels/select-fc`, {
        params: params
    });
};
export const appversionsselectfc = params => {
    return axios.get(`${base}/app-versions/select-fc`, {
        params: params
    });
};

// 广告位映射
// export const adPositiongetList = params => {
//     return axios.post(`${base}/adPosition/getList`, params).then(res => res);
// };
// 编辑
export const adPositionget = params => {
    return axios.get(`${base}/adPosition/get`, {
        params: params
    });
};
export const adPositionmappingswitch = params => {
    return axios.post(`${base}/adPosition/mapping-switch`, params).then(res => res);
};
export const adPositionmapping= params => {
    return axios.post(`${base}/adPosition/position-mapping`, params).then(res => res);
};
// 版本渠道管理
// 列表
export const appversionspage= params => {
    return axios.post(`${base}/app-versions/page`, params).then(res => res);
};
// 搜索
export const appversionspagePage = params => {
    return axios.post(`${base}/app-versions/page`, params).then(res => res);
};
// 新建
export const appversions = params => {
    return axios.post(`${base}/app-versions`, params).then(res => res);
};
// 删除
export const appversionsDel = params => {
    return axios.post(`${base}/app-versions/${params.id}`, params).then(res => res);
};
// 渠道列表
export const appchannelspage= params => {
    return axios.post(`${base}/app-channels/page`, params).then(res => res);
};
// 新建
export const appchannels= params => {
    return axios.post(`${base}/app-channels`, params).then(res => res);
};
// 删除
export const appchannelsDel= params => {
    return axios.post(`${base}/app-channels/${params.id}`, params).then(res => res);
};






// ----------参数管理 ----------
// 行业tree
export const industrysselect = params => {
    return axios.get(`${base}/industrys/select`, {
        params: params
    });
};
export const industrys= params => {
    return axios.post(`${base}/industrys`, params).then(res => res);
};
export const industrysDelete= params => {
    return axios.post(`${base}/industrys/${params.id}`, params).then(res => res);
};
// 应用
export const apppkgspage = params => {
    return axios.post(`${base}/app-pkgs/page`, params).then(res => res);
};
export const apppkgs = params => {
    return axios.post(`${base}/app-pkgs`, params).then(res => res);
};
export const apppkgsDelet = params => {
    return axios.post(`${base}/app-pkgs/${params.id}`, params).then(res => res);
};
export const apppkgsget = params => {
    return axios.get(`${base}/app-pkgs/`+params.id, {
        // params: params
    });
};
// 黑名单
export const industrysblacklistspage= params => {
    return axios.post(`${base}/industry-blacklists/page`, params).then(res => res);
};
export const industrysblacklists= params => {
    return axios.post(`${base}/industry-blacklists`, params).then(res => res);
};
export const industryblacklistsInfo = params => {
    return axios.get(`${base}/industry-blacklists`, {
        params: params
    });
};
export const industrysblacklistsDelete = params => {
    return axios.post(`${base}/industry-blacklists/${params.id}`, params).then(res => res);
};
export const industrysblacklistsStateOn = params => {
    return axios.post(`${base}/industry-blacklists/state/on/${params.id}`, params).then(res => res);
};
export const industrysblacklistsStateOff = params => {
    return axios.post(`${base}/industry-blacklists/state/off/${params.id}`, params).then(res => res);
};
// 竞品标签
export const competingproductspage= params => {
    return axios.post(`${base}/competing-products/page`, params).then(res => res);
};
export const competingproducts= params => {
    return axios.post(`${base}/competing-products`, params).then(res => res);
};
export const competingproductsDel = params => {
    return axios.post(`${base}/competing-products/${params.id}`, params).then(res => res);
};
// 应用包黑名单列表
export const apppkgblacklistspage= params => {
    return axios.post(`${base}/app-pkg-blacklists/page`, params).then(res => res);
};
export const apppkgsselect = params => {
    return axios.get(`${base}/app-pkgs/select`, {
        params: params
    });
};
export const appblanklistsDel = params => {
    return axios.post(`${base}/app-pkg-blacklists/${params.id}`, params).then(res => res);
};
export const Newapppkgblacklists = params => {
    return axios.post(`${base}/app-pkg-blacklists`, params).then(res => res);
};
// 机型管理分页列表
export const devicemodelspage= params => {
    return axios.post(`${base}/device-models/page`, params).then(res => res);
};
// 机型管理全部列表
export const devicemodelsselect = params => {
    return axios.get(`${base}/device-models/select`, {
        params: params
    });
};
// 新增/修改机型
export const devicemodels= params => {
    return axios.post(`${base}/device-models`, params).then(res => res);
};
// 删除机型
export const devicemodelsDel = params => {
    return axios.post(`${base}/device-models/${params.id}`, params).then(res => res);
};


// 图书分类管理
export const bookcategorysselect = params => {
    return axios.get(`${base}/book-categorys/select`, {
        params: params
    });
};
// 新增修改
export const bookcategorys= params => {
    return axios.post(`${base}/book-categorys`, params).then(res => res);
};
export const bookcategorysDel = params => {
    return axios.post(`${base}/book-categorys/${params.id}`, params).then(res => res);
};



// ----------审核管理 ----------
// 广告审核列表
export const auditentitypages = params => {
    return axios.post(`${base}/auditEntity/pages`, params).then(res => res);
};
// 获取创意审核详情页
export const auditentityauditinfo = params => {
    return axios.get(`${base}/auditEntity/auditInfo`, {
        params: params
    });
};
// 审核
export const auditentityaudit = params => {
    return axios.get(`${base}/auditEntity/audit`, {
        params: params
    });
};
// 客户审核列表
export const auditAdverpages = params => {
    return axios.get(`${base}/auditAdver/pages`, {
        params: params
    });
};
// 客户审核详情
export const auditAdverauditInfo = params => {
    return axios.get(`${base}/auditAdver/auditInfo`, {
        params: params
    });
};
// 客户审核
export const auditAdveraudit = params => {
    return axios.get(`${base}/auditAdver/audit`, {
        params: params
    });
};



// ----------财务管理 ----------
//  取客户、代理商余额
export const getBalance = params => {
    return axios.get(`${base}/company/getBalance`, {
        params: params
    });
};
// // 客户、代理商列表
// export const CompanygetList = params => { return axios.get(`${base}/company/getList`, { params: params }); };
// 客户充值/退款
export const rechargeAdd = params => {
    return axios.get(`${base}/recharge/add`, {
        params: params
    });
};
// 代理商充值/退款
export const agentRechargeAdd = params => {
    return axios.get(`${base}/agentRecharge/add`, {
        params: params
    });
};

// 客户历史账单
export const rechargesumHistory = params => {
    return axios.get(`${base}/recharge/sumHistory`, {
        params: params
    });
};

// 代理商历史账单
export const sumHistory = params => {
    return axios.get(`${base}/agentRecharge/sumHistory`, {
        params: params
    });
};
// 代理商echers
export const agentRechargesumByDate = params => {
    return axios.get(`${base}/agentRecharge/sumByDate`, {
        params: params
    });
};
// 代理商充值 退款 表格
export const agentrechargegetList = params => {
    return axios.get(`${base}/agentRecharge/getList`, {
        params: params
    });
};

// 客户echers
export const rechargesumByDate = params => {
    return axios.get(`${base}/recharge/sumByDate`, {
        params: params
    });
};
// 代理商,客户表格
export const rechargegetList= params => {
    return axios.get(`${base}/recharge/getList`, {
        params: params
    });
};

// 客户，代理商 花费报表
export const reportForCast = params => {
    return axios.get(`${base}/recharge/reportForCast`, {
        params: params
    });
};
// //报表
// export const agentRecharge = params => {
//     return axios.get(`${base}/agentRecharge/reportForCast`, {
//         params: params
//     });
// };

// ----------代理商管理 ----------
//  代理商列表
export const AgentList = params => {
    return axios.get(`${base}/company/list`, {
        params: params
    });
};
// 代理商详情页
export const AgentGet = params => {
    return axios.get(`${base}/company/get`, {
        params: params
    });
};
// 代理商新增
export const AgentAdd = params => {
    return axios.post(`${base}/company/add`, params).then(res => res);
};
// 代理商修改
export const AgentUpdate = params => {
    return axios.post(`${base}/company/update`, params).then(res => res);
};
// 代理商删除
export const AgentDelete = params => {
    return axios.get(`${base}/company/delete`, {
        params: params
    });
};
// ----------系统底价 ----------
// 系统底价列表
export const PriceList = params => {
    return axios.get(`${base}/price/list`, {
        params: params
    });
};
// 系统底价新增
export const PriceAdd = params => {
    return axios.post(`${base}/price/add`, params).then(res => res);
};
// 系统底价修改
export const PriceUpdate = params => {
    return axios.post(`${base}/price/update`, params).then(res => res);
};
// 系统底价 价格同步
export const PriceSyncPrice = params => {
    return axios.get(`${base}/price/syncPrice`, {
        params: params
    });
};
// 系统底价 利润率同步
export const PriceSyncProfit = params => {
    return axios.get(`${base}/price/syncProfit`, {
        params: params
    });
};
// 系统底价 广告位名称
// export const adPositiongetList = params => {
//     return axios.get(`${base}/adPosition/getList`, {
//         params: params
//     });
// };
export const adPositiongetList = params => {
    return axios.post(`${base}/adPosition/getList`, params).then(res => res);
};
export const adPositiongetListByUser = params => {
    return axios.get(`${base}/adPosition/getListByUser`, {
        params: params
    });
};
export const adPositiongetlistByConsumerAndApp= params => {
    return axios.get(`${base}/adPosition/listByConsumerAndApp`, {
        params: params
    });
};


// ----------客户管理 ----------
// 客户列表
export const CustomerList = params => {
    return axios.get(`${base}/company/list`, {
        params: params
    });
};
export const usergetAgInfo = params => {
    return axios.get(`${base}/user/getAgInfo`, {
        params: params
    });
};
// 媒体提审(表格)
export const CustomerGet = params => {
    return axios.get(`${base}/company/get`, {
        params: params
    });
};
// 客户新增
export const CustomerAdd = params => {
    return axios.post(`${base}/company/add`, params).then(res => res);
};
// 客户修改
export const CustomerUpdate = params => {
    return axios.post(`${base}/company/update`, params).then(res => res);
};
// 客户删除
export const CustomerDelete = params => {
    return axios.get(`${base}/company/delete`, {
        params: params
    });
};
// 客户授权列表
export const CustomergetGrand = params => {
    return axios.get(`${base}/company/getGrand`, {
        params: params
    });
};
// 客户授权
export const CustomerGrand = params => {
    return axios.get(`${base}/company/grand`, {
        params: params
    });
};
// 客户授权删除
export const CustomermoveGrand = params => {
    return axios.get(`${base}/company/moveGrand`, {
        params: params
    });
};
// 行业
export const industryManage = params => {
    return axios.get(`${base}/industrys/primaryIndustry`, {
        params: params
    });
};
// 提交审核
export const relationAdd = params => {
    return axios.get(`${base}/relation/add`, {
        params: params
    });
};

// ----------用户管理 ----------
// 用户列表
export const UserList = params => {
    return axios.get(`${base}/user/list`, {
        params: params
    });
};
// 用户新增
export const UserAdd = data => {
    return axios.post(`${base}/user/add`, data).then(res => res);
}
// 用户修改
export const UserUpdate = params => {
    return axios.post(`${base}/user/update`, params).then(res => res);
}
// 用户删除
export const UserDelete = params => {
    return axios.get(`${base}/user/delete`, {
        params: params
    });
};
// 账户权限
export const authgetAuth = params => {
    return axios.get(`${base}/auth/getAuth`, {
        params: params
    });
};
// 流量源名称
export const GetallFlowsource = params => {
    return axios.get(`${base}/flowsource/getlist`, {
        params: params
    });
};
// 账户类型
export const grouplistForAdd = params => {
    return axios.get(`${base}/group/listForAdd`, {
        params: params
    });
};

export const userget = params => {
    return axios.get(`${base}/user/get`, {
        params: params
    });
};

//   用户组管理
// 用户组列表
export const grouplist = params => {
    return axios.get(`${base}/group/list`, {
        params: params
    });
};
// 用户组列表
export const groupget = params => {
    return axios.get(`${base}/group/get`, {
        params: params
    });
};
// 用户组新增
export const groupadd = params => {
    return axios.post(`${base}/group/add`, params).then(res => res);
};
// 用户组编辑
export const groupupdate = params => {
    return axios.post(`${base}/group/update`, params).then(res => res);
};
// 菜单列表
export const authgetAllAuth = params => {
    return axios.get(`${base}/auth/getAllAuth`, {
        params: params
    });
};

//DMP
//数据源列表
export const datasourcelist = params => {
    return axios.get(`${base}/ds/list`, {
        params: params
    });
};
export const datasourcerejudge = params => {
    return axios.get(`${base}/ds/rejudge`, {
        params: params
    });
};
//数据源编辑
export const datasourceupdate = params => {
    return axios.post(`${base}/ds/update`, params).then(res => res);
};
//数据源新增
export const datasourceadd = params => {
    return axios.post(`${base}/ds/add`, params).then(res => res);
};
//数据源新增
export const dataslist = params => {
    return axios.get(`${base}/datas/list`, {
        params: params
    });
};
//数据源预览
export const dataspreview = params => {
    return axios.get(`${base}/datas/preview`, {
        params: params
    });
};
//数据级新增
export const datasadd = params => {
    return axios.post(`${base}/datas/add`, params).then(res => res);
};
//数据级编辑
export const datasupdate = params => {
    return axios.post(`${base}/datas/update`, params).then(res => res);
};
//用户画像
export const personsgetTagsd = params => {
    return axios.get(`${base}/persons/getTags`, {
        params: params
    });
};
//图标
export const personsgetTagPercent = params => {
    return axios.get(`${base}/persons/getTagPercent`, {
        params: params
    });
};

//标签组列表
export const tagslist = params => {
    return axios.get(`${base}/tags/list`, {
        params: params
    });
};
//新增
export const tagsadd = params => {
    return axios.post(`${base}/tags/add`, params).then(res => res);
};
//编辑
export const tagsupdate = params => {
    return axios.post(`${base}/tags/update`, params).then(res => res);
};
//删除标签
export const tagsupdateTag = params => {
    return axios.post(`${base}/tags/updateTag`, params).then(res => res);
};
//标签组判定列表
export const judgelist = params => {
    return axios.get(`${base}/judge/list`, {
        params: params
    });
};
//标签组判定删除
export const judgedrop = params => {
    return axios.get(`${base}/judge/drop`, {
        params: params
    });
};
//标签组新增
export const judgeadd = params => {
    return axios.post(`${base}/judge/add`, params).then(res => res);
};

//上传文本
export const uploadgetCont = `${base}` + "/upload/getCont";
//上传文本
export const batchImport = `${base}` + "/app-channels/batchImport";


//人群管理列表
export const personslist = params => {
    return axios.get(`${base}/persons/list`, {
        params: params
    });
};
//人群管理新建
export const personsadd = params => {
    return axios.post(`${base}/persons/add`, params).then(res => res);
};
//人群管理编辑
export const personsupdate = params => {
    return axios.post(`${base}/persons/update`, params).then(res => res);
};


// ----------变现管理 ----------
// 订单投放
// 订单投列表
export const orderspage = params => {
    return axios.post(`${base}/orders/page`, params).then(res => res);
};
// 新增订单
export const ordersadd = params => {
    return axios.post(`${base}/orders/add`, params).then(res => res);
};
// 订单详情
export const ordersinfo = params => {
    return axios.get(`${base}/orders/info/`+params.id, {
        // params: params
    });
};
// 修改订单
export const ordersupdate= params => {
    return axios.post(`${base}/orders/update`, params).then(res => res);
};
// 删除订单
export const ordersdelete= params => {
    return axios.post(`${base}/orders/delete/`+params.id).then(res => res);
};



// 投放列表
export const orderPutpages= params => {
    return axios.post(`${base}/orderPut/pages`,params).then(res => res);
};
// 新增投放
export const orderPutadd= params => {
    return axios.post(`${base}/orderPut/add`, params).then(res => res);
};
// 投放详情
export const orderPutinfo = params => {
    return axios.get(`${base}/orderPut/info/`+params.id, {
        // params: params
    });
};
// 库存
export const adPositionstock= params => {
    return axios.post(`${base}/adPosition/stock`, params).then(res => res);
};


// 排期列表
export const positionTimescheduling = params => {
    return axios.get(`${base}/positionTime/scheduling`, {
        params: params
    });
};

// 创意列表
export const entitypage= params => {
    return axios.post(`${base}/entity/page`, params).then(res => res);
};
// 查询状态
export const entitycheckEntity = params => {
    return axios.get(`${base}/entity/checkEntity/`+params.id, {
        // params: params
    });
};




// 精准投放
// 计划列表
export const planpage = params => {
    return axios.get(`${base}/plan/page`, {
        params: params
    });
};
// 计划新增
export const planadd = params => {
    return axios.post(`${base}/plan/add`,  params).then(res => res);
};
// 计划详情
export const planinfo = params => {
    return axios.get(`${base}/plan/info`, {
        params: params
    });
};

// 计划修改
export const planupdate = params => {
    return axios.post(`${base}/plan/update`, params).then(res => res);
};
// 计划删除
export const plandelete = params => {
    return axios.get(`${base}/plan/delete`, {
        params: params
    });
};
// 开关
// export const entityupdate = params => { return axios.get(`${base}/entity/update`, { params: params }); };

// 单元列表
export const putpage = params => {
    return axios.post(`${base}/put/page`, params).then(res => res);
};
// 投放计划
export const planlist = params => {
    return axios.get(`${base}/plan/list`, {
        params: params
    });
};
// 定向标签
export const tagsgetDxTags = params => {
    return axios.get(`${base}/tags/getDxTags`, {
        params: params
    });
};
// 定向频道
export const dicmapList = params => {
    return axios.get(`${base}/dic/mapList`, {
        params: params
    });
};
// 定向城市
export const areaGroupgetAreasByNameAndType = params => {
    return axios.get(`${base}/areaGroup/getAreasByNameAndType`, {
        params: params
    });
};
// 新增投放
export const putadd = params => {
    return axios.post(`${base}/put/add`, params).then(res => res);
};
// 编辑投放
export const putupdate = params => {
    return axios.post(`${base}/put/update`, params).then(res => res);
};
// 投放详情
export const putinfo = params => {
    return axios.get(`${base}/put/info/`+params.id, {
        // params: params
    });
};
// 获取城市
export const diclistByIdStr = params => {
    return axios.get(`${base}/areaGroup/listByIdStr`, {
        params: params
    });
};
// 投放删除
// export const putdelete = params => {
//     return axios.get(`${base}/put/delete/`+params.id, {
//         // params: params
//     });
// };
export const putdelete = params => {
    return axios.post(`${base}/put/delete/${params.id}`,params).then(res => res);
};

// 抄底投放
export const entitygetOneByPid = params => {
    return axios.get(`${base}/entity/getOneByPid/`+params.id, {
        // params: params
    });
};



// 投放名称
export const orderPutlist = params => {
    return axios.post(`${base}/orderPut/list`,params).then(res => res);
};
// 投放名称
export const putlist = params => {
    return axios.post(`${base}/put/list`,params).then(res => res);
};
// 投放名称
export const orderPutdelete = params => {
    return axios.post(`${base}/orderPut/delete/${params.id}`,params).then(res => res);
};
// 创意模板
export const adPositiongetModules = params => {
    return axios.get(`${base}/adPosition/getModules`, {
        params: params
    });
};
// 广告位
export const orderPutpositions= params => {
    return axios.get(`${base}/orderPut/positions/`+params.id, {
        // params: params
    });
};
// 上传模板
export const uploaduploadWithSize = `${base}` + "/upload/uploadWithSize";




//新建创意
export const orderPutupdate = params => {
    return axios.post(`${base}/orderPut/update`, params).then(res => res);
};
export const orderPutupdateRunState = params => {
    return axios.post(`${base}/orderPut/updateRunState`, params).then(res => res);
};
//新建创意
export const entityadd = params => {
    return axios.post(`${base}/entity/add`, params).then(res => res);
};
//创意编辑
export const entityupdate = params => {
    return axios.post(`${base}/entity/update`, params).then(res => res);
};
//创意详情
export const entityinfo = params => {
    return axios.get(`${base}/entity/info/`+params.id, {
        // params: params
    });
};
//创意删除
export const entitydelete = params => {
    return axios.post(`${base}/entity/delete/${params.id}`, params).then(res => res);
};
//创意审核按钮
export const entityauditAdx = params => {
    return axios.post(`${base}/entity/auditAdx `, params).then(res => res);
};
// 创意模板
export const adPositiongetTemplatesById = params => {
    return axios.get(`${base}/adPosition/getTemplatesById/`+params.id, {
        // params: params
    });
};
// 模板组件
export const adPositiongetModulesByTemplateId = params => {
    return axios.get(`${base}/adPosition/getModulesByTemplateId`, {
        params: params
    });
};



// ----------数据报告 ----------
//  查询单日流量源
export const flowdatagetoneflowdatabyhour = params => {
    return axios.get(`${base}/flowdata/getoneflowdatabyhour`, {
        params: params
    });
};
//  查询多流量源
export const flowdatagetflowdatabyhourandflow = params => {
    return axios.get(`${base}/flowdata/getflowdatabyhourandflow`, {
        params: params
    });
};
// 表格
export const flowdatagetflowdataonedaybyfs = params => {
    return axios.get(`${base}/flowdata/getflowdataonedaybyfs`, {
        params: params
    });
};
// 广告位平台列表
export const flowconsumergetlist = params => {
    return axios.post(`${base}/flowconsumer/list-by-type`, params).then(res => res);
};
// 广告位平台图表
export const consumerdatagetonefcbyhour = params => {
    return axios.get(`${base}/consumerdata/getonefcbyhour`, {
        params: params
    });
};
export const positionmappinglist = params => {
    return axios.post(`${base}/position-mapping/list`, params).then(res => res);
    // return axios.get(`${base}/position-mapping/list`, {
    //     params: params
    // });
};
// 广告位平台多日图表
export const consumerdatagetplatformsdatabyhour = params => {
    return axios.get(`${base}/consumerdata/getplatformsdatabyhour`, {
        params: params
    });
};
// 广告位平台多日表格
export const consumerdatagetPlatformsdatatoday = params => {
    return axios.get(`${base}/consumerdata/getPlatformsdatatoday`, {
        params: params
    });
};

// 流量源报告图表
export const quotaFlowsumByDay = params => {
    return axios.get(`${base}/quotaFlow/sumByDay`, {
        params: params
    });
};
// 流量源表格
export const quotaFlowsum = params => {
    return axios.get(`${base}/quotaFlow/sum`, {
        params: params
    });
};
// 流量源-详细
export const quotaFlowdetail = params => {
    return axios.get(`${base}/quotaFlow/detail`, {
        params: params
    });
};
// 流量源-详细表格
export const flowdatagetflowdatapagebyday = params => {
    return axios.get(`${base}/flowdata/getflowdatapagebyday`, {
        params: params
    });
};

// 广告平台报告图表表格
export const consumerdatagetoneplatformdatabyday = params => {
    return axios.get(`${base}/consumerdata/getoneplatformdatabyday`, {
        params: params
    });
};
// 单平台
export const consumerdatasumByDay = params => {
    return axios.get(`${base}/consumerdata/sumByDay`, {
        params: params
    });
};
// 多平台
export const consumerdatasumItemByDay = params => {
    return axios.get(`${base}/consumerdata/sumItemByDay`, {
        params: params
    });
};
// 表格
export const consumerdatasumByDayWithGroup = params => {
    return axios.get(`${base}/consumerdata/sumByDayWithGroup`, {
        params: params
    });
};
export const consumerdatadetailReport = params => {
    return axios.get(`${base}/consumerdata/detailReport`, {
        params: params
    });
};
// 表格二
export const consumerdatagetoneplatformpagedatabyday = params => {
    return axios.get(`${base}/consumerdata/getoneplatformpagedatabyday`, {
        params: params
    });
};

// 广告平台报告
// 广告平台报告图表
export const sdkReportsumByDay = params => {
    return axios.get(`${base}/sdkReport/sumByDay`, {
        params: params
    });
};
// 广告平台报告列表
export const sdkReportsum = params => {
    return axios.get(`${base}/sdkReport/sum`, {
        params: params
    });
};
// 上传
export const sdkReportupload = `${base}` + "/sdkReport/upload";
// 表格二
export const sdkReportdetail = params => {
    return axios.get(`${base}/sdkReport/detail`, {
        params: params
    });
};
export const flowdatagetflowdatabyday = params => {
    return axios.get(`${base}/sdkReport/detail`, {
        params: params
    });
};
// 结算报告
// 图表
export const settlesumByDay = params => {
    return axios.get(`${base}/settle/sumByDay`, {
        params: params
    });
};
// 表格
export const settlesum = params => {
    return axios.get(`${base}/settle/sum`, {
        params: params
    });
};
// 详情
export const settledetail = params => {
    return axios.get(`${base}/settle/detail`, {
        params: params
    });
};
// 上传
export const settleupload = `${base}` + "/settle/upload";

// 图书分类报告
// 图表
export const quotaBooksumByDay = params => {
    return axios.get(`${base}/quotaBook/sumByDay`, {
        params: params
    });
};
// 列表
export const quotaBooksum = params => {
    return axios.get(`${base}/quotaBook/sum`, {
        params: params
    });
};
// 详情
export const quotaBookdetail = params => {
    return axios.get(`${base}/quotaBook/detail`, {
        params: params
    });
};
// 一级
export const bookcategorysselectOne = params => {
    return axios.get(`${base}/book-categorys/selectOne`, {
        params: params
    });
};
export const bookcategorysselectTwo = params => {
    return axios.get(`${base}/book-categorys/selectTwo`, {
        params: params
    });
};
export const bookcategorysselectThree = params => {
    return axios.get(`${base}/book-categorys/selectThree`, {
        params: params
    });
};

// 订单报告
// 订单名称
// export const orderslist = params => {
//     return axios.get(`${base}/orders/list`, {
//         params: params
//     });
// };
export const orderslist = params => {
    return axios.post(`${base}/orders/list`, params).then(res => res);
};
// 订单表格
export const quotaOrderlistByInit = params => {
    return axios.post(`${base}/quotaOrder/listByInit`, params).then(res => res);
};

// 订单详情图表
export const quotaOrderlistByDay = params => {
    return axios.get(`${base}/quotaOrder/listByDay`, {
        params: params
    });
};
// 订单报告-详情table
export const quotaPutlistByOidOrPid = params => {
    return axios.get(`${base}/quotaPut/listByOidOrPid`, {
        params: params
    });
};
//订单投放报告-详情
export const quotaPutlistByDay = params => {
    return axios.get(`${base}/quotaPut/listByDay`, {
        params: params
    });
};
//创意列表
export const quotaEntitylistByPid = params => {
    return axios.get(`${base}/quotaEntity/listByPid`, {
        params: params
    });
};
//创意图标
export const quotaEntityreportByDay = params => {
    return axios.get(`${base}/quotaEntity/reportByDay`, {
        params: params
    });
};

// 计划报告
//计划列表
export const quotaPlanlistByInit = params => {
    return axios.post(`${base}/quotaPlan/listByInit`, params).then(res => res);
};
//计划搜索
export const quotaPlanlistByDay = params => {
    return axios.get(`${base}/quotaPlan/listByDay`, {
        params: params
    });
};
//  客户、代理商报表接口
export const companyquotaByUser = params => {
    return axios.get(`${base}/company/quotaByUser`, {
        params: params
    });
};
// 预警
// 列表
export const warningsettingpage = params => {
    return axios.post(`${base}/warning-setting/page`, params).then(res => res);
};
// 新增
export const warningsetting = params => {
    return axios.post(`${base}/warning-setting`, params).then(res => res);
};
// 详情
export const warningsettingInfo = params => {
    return axios.get(`${base}/warning-setting/`+params.id, {
        // params: params
    });
};
// 初始化
export const warninginitpage = params => {
    return axios.get(`${base}/warning-setting/init-page`, {
        params: params
    });
};
// 开关
export const warningsettingstatus = params => {
    return axios.get(`${base}/warning-setting/status`, {
        params: params
    });
};


// sdk投放
// 列表
export const sdkallotlist = params => {
    return axios.get(`${base}/sdk/allot/list`, {
        params: params
    });
};
// 详情
export const sdkallotget = params => {
    return axios.get(`${base}/sdk/allot/get/`+params.id, {
        // params: params
    });
};
// 开关
export const sdkallotstatus = params => {
    return axios.get(`${base}/sdk/allot/status`, {
        params: params
    });
};
// 新增
export const sdkallotadd = params => {
    return axios.post(`${base}/sdk/allot/add`, params).then(res => res);
};
// cpm cpc新增
export const addFixed = params => {
    return axios.post(`${base}/sdk/allot/addFixed`, params).then(res => res);
};
// 编辑
export const sdkallotupdate = params => {
    return axios.post(`${base}/sdk/allot/update`, params).then(res => res);
};
// 编辑 cpm cpc
export const updateFixed = params => {
    return axios.post(`${base}/sdk/allot/updateFixed`, params).then(res => res);
};
// 广告位状态
export const sdkallotadpstatus = params => {
    return axios.get(`${base}/sdk/allot/adp/status/`+params.allotId, {
        // params: params
    });
};
// App、平台列表
export const sdkallotpre = params => {
    return axios.get(`${base}/sdk/allot/pre`, {
        params: params
    });
};
// sdk广告位
export const sdkallotadp = params => {
    return axios.get(`${base}/sdk/allot/adp/`+params.appId+'/'+params.platformId, {
        // params: params
    });
};
// 包段排期
export const sdkallotsche = params => {
    return axios.get(`${base}/sdk/allot/sche`, {
        params: params
    });
};


//  登录
export const loginlogin = params => {
    return axios.post(`${base}/auth/signin`, params).then(res => res);
};
// 修改密码
export const userresetPwd = params => {
    return axios.get(`${base}/user/resetPwd`, {
        params: params
    });
};
//  退出登录
export const authsignout = params => {
    return axios.get(`${base}/auth/signout`, {
        params: params
    });
};

