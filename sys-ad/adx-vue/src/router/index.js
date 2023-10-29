import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [{
            path: '/',
            redirect: '/login'
        },
        {
            path: '/page',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: {
                title: '自述文件'
            },
            children: [{
                    path: '/dataoverview',
                    component: resolve => require(['../components/page/home/dataOverview.vue'], resolve),
                    meta: {
                        title: '流量数据'
                    }
                },
                {
                    path: '/datacustomer',
                    component: resolve => require(['../components/page/home/dataCustomer.vue'], resolve),
                    meta: {
                        title: '客户数据'
                    }
                },
                {
                    path: '/datagent',
                    component: resolve => require(['../components/page/home/dataAgent.vue'], resolve),
                    meta: {
                        title: '代理商数据'
                    }
                },
                {
                    path: '/dataplatform',
                    component: resolve => require(['../components/page/home/dataPlatForm.vue'], resolve),
                    meta: {
                        title: '平台数据'
                    }
                },


                {
                    path: '/flowmanagement/flowsource',
                    component: resolve => require(['../components/page/flowmanagement/FlowSource.vue'], resolve),
                    meta: {
                        title: '流量源管理'
                    }
                },
                {
                    path: '/flowmanagement/appsource',
                    component: resolve => require(['../components/page/flowmanagement/appSource.vue'], resolve),
                    meta: {
                        title: '流量源App管理'
                    }
                },
                {
                    path: '/flowmanagement/adtemplate',
                    component: resolve => require(['../components/page/flowmanagement/Adtemplate.vue'], resolve),
                    meta: {
                        title: '广告模板管理'
                    }
                },
                {
                    path: '/flowmanagement/adsense',
                    component: resolve => require(['../components/page/flowmanagement/AdSense.vue'], resolve),
                    meta: {
                        title: '广告位管理'
                    }
                },
                {
                    path: '/flowmanagement/adprice',
                    component: resolve => require(['../components/page/flowmanagement/AdPrice.vue'], resolve),
                    meta: {
                        title: '广告位底价'
                    }
                },
                {
                    path: '/flowmanagement/flowcontrol',
                    component: resolve => require(['../components/page/flowmanagement/FlowControl.vue'], resolve),
                    meta: {
                        title: '流量控制'
                    }
                },
                {
                    path: '/flowmanagement/flowstrategy',
                    component: resolve => require(['../components/page/flowmanagement/FlowStrategy.vue'], resolve),
                    meta: {
                        title: 'SDK广告轮播策略'
                    }
                },
                {
                    path: '/flowmanagement/admap',
                    component: resolve => require(['../components/page/flowmanagement/AdMap.vue'], resolve),
                    meta: {
                        title: '广告位映射'
                    }
                },
                {
                    path: '/flowmanagement/AppVersions',
                    component: resolve => require(['../components/page/flowmanagement/AppVersions.vue'], resolve),
                    meta: {
                        title: '版本渠道管理'
                    }
                },
                {
                    path: '/flowmanagement/earlyWarning',
                    component: resolve => require(['../components/page/flowmanagement/earlyWarning.vue'], resolve),
                    meta: {
                        title: '预警设置'
                    }
                },

                {
                    path: '/sdkput/sdksegment',
                    component: resolve => require(['../components/page/SdkPut/SdkSegment.vue'], resolve),
                    meta: {
                        title: 'SDK包段'
                    }
                },
                {
                    path: '/sdkput/SdkPacket',
                    component: resolve => require(['../components/page/SdkPut/SdkPacket.vue'], resolve),
                    meta: {
                        title: 'SDK包量'
                    }
                },

                {
                    path: '/parameterManagement/keywordManagement',
                    component: resolve => require(['../components/page/parameterManagement/keywordManagement.vue'], resolve),
                    meta: {
                        title: '关键词管理'
                    }
                },
                {
                    path: '/parameterManagement/territoryManagement',
                    component: resolve => require(['../components/page/parameterManagement/territoryManagement.vue'], resolve),
                    meta: {
                        title: '地域管理'
                    }
                },
                {
                    path: '/parameterManagement/blacklistManagement',
                    component: resolve => require(['../components/page/parameterManagement/blacklistManagement.vue'], resolve),
                    meta: {
                        title: '黑名单管理'
                    }
                },
                {
                    path: '/parameterManagement/typeManagement',
                    component: resolve => require(['../components/page/parameterManagement/typeManagement.vue'], resolve),
                    meta: {
                        title: '机型管理'
                    }
                },
                {
                    path: '/parameterManagement/bookManagement',
                    component: resolve => require(['../components/page/parameterManagement/bookManagement.vue'], resolve),
                    meta: {
                        title: '图书分级管理'
                    }
                },




                {
                    path: '/adwords/adwords',
                    component: resolve => require(['../components/page/Adwords/adwords.vue'], resolve),
                    meta: {
                        title: '广告平台管理'
                    }
                },
                {
                    path: '/adwords/advertiser',
                    component: resolve => require(['../components/page/Adwords/advertiser.vue'], resolve),
                    meta: {
                        title: '广告平台广告主'
                    }
                },
                {
                    path: '/adwords/platformidear',
                    component: resolve => require(['../components/page/Adwords/platformIdear.vue'], resolve),
                    meta: {
                        title: '广告平台创意'
                    }
                },


                {
                    path: '/usermanagement/usermanagement',
                    component: resolve => require(['../components/page/userManagement/UserManagement.vue'], resolve),
                    meta: {
                        title: '用户管理'
                    }
                },
                {
                    path: '/usermanagement/usergroup',
                    component: resolve => require(['../components/page/userManagement/userGroup.vue'], resolve),
                    meta: {
                        title: '用户组管理'
                    }
                },
                {
                    path: '/customermanagement/customermanagements',
                    component: resolve => require(['../components/page/CustomerManagement/CustomerManagements.vue'], resolve),
                    meta: {
                        title: '客户管理'
                    }
                },
                {
                    path: '/agenmanagement/agentmanagement',
                    component: resolve => require(['../components/page/agenmanagement/AgentManagement.vue'], resolve),
                    meta: {
                        title: '代理商管理'
                    }
                },
                {
                    path: '/agenmanagement/agentmanageprice',
                    component: resolve => require(['../components/page/agenmanagement/AgentManagePrice.vue'], resolve),
                    meta: {
                        title: '代理商底价'
                    }
                },
                {
                    path: '/agenmanagement/systemprice',
                    component: resolve => require(['../components/page/agenmanagement/SystemPrice.vue'], resolve),
                    meta: {
                        title: '系统底价'
                    }
                },


                {
                    path: '/cashmanagement/orderput/ordermanagement',
                    component: resolve => require(['../components/page/cashManagement/orderPut/OrderManagement.vue'], resolve),
                    meta: {
                        title: '订单列表'
                    }
                },
                {
                    path: '/cashmanagement/orderPut/launchmanagements',
                    component: resolve => require(['../components/page/cashManagement/orderPut/LaunchManagements.vue'], resolve),
                    meta: {
                        title: '投放列表'
                    }
                },
                {
                    path: '/cashmanagement/orderPut/ideamanage',
                    component: resolve => require(['../components/page/cashManagement/orderPut/ideaManage.vue'], resolve),
                    meta: {
                        title: '创意列表'
                    }
                },
                {
                    path: '/cashmanagement/orderPut/ideaStatus',
                    component: resolve => require(['../components/page/cashManagement/orderPut/ideaStatus.vue'], resolve),
                    meta: {
                        title: '创意状态查询'
                    }
                },

                {
                    path: '/cashmanagement/launchPrecisely/planput',
                    component: resolve => require(['../components/page/cashManagement/launchPrecisely/PlanPut.vue'], resolve),
                    meta: {
                        title: '计划'
                    }
                },
                {
                    path: '/cashmanagement/launchPrecisely/elementput',
                    component: resolve => require(['../components/page/cashManagement/launchPrecisely/ElementPut.vue'], resolve),
                    meta: {
                        title: '单元'
                    }
                },
                {
                    path: '/cashmanagement/launchPrecisely/idearput',
                    component: resolve => require(['../components/page/cashManagement/launchPrecisely/IdearPut.vue'], resolve),
                    meta: {
                        title: '创意'
                    }
                },
                {
                    path: '/cashmanagement/chaodimanagement',
                    component: resolve => require(['../components/page/cashManagement/chaodiManagement.vue'], resolve),
                    meta: {
                        title: '抄底投放'
                    }
                },

                {
                    path: '/auditmanagement/costomermaudits',
                    component: resolve => require(['../components/page/AuditManagement/CostomermAudits.vue'], resolve),
                    meta: {
                        title: '客户审核'
                    }
                },
                {
                    path: '/auditmanagement/advertisingaudit',
                    component: resolve => require(['../components/page/AuditManagement/AdvertisingAudit.vue'], resolve),
                    meta: {
                        title: '广告审核'
                    }
                },

                {
                    path: '/moneymanagement/agent',
                    component: resolve => require(['../components/page/MoneyManagement/Agent.vue'], resolve),
                    meta: {
                        title: '代理商'
                    }
                },
                {
                    path: '/moneymanagement/customer',
                    component: resolve => require(['../components/page/MoneyManagement/Customer.vue'], resolve),
                    meta: {
                        title: '客户'
                    }
                },

                {
                    path: '/datareport/timemonitoring/flowSource',
                    component: resolve => require(['../components/page/DataReport/timeMonitoring/FlowSource.vue'], resolve),
                    meta: {
                        title: '流量源'
                    }
                },
                {
                    path: '/datareport/timemonitoring/advertisingplatform',
                    component: resolve => require(['../components/page/DataReport/timeMonitoring/AdvertisingPlatform.vue'], resolve),
                    meta: {
                        title: '广告平台'
                    }
                },
                {
                    path: '/datareport/flowreport/flowSourcereport',
                    component: resolve => require(['../components/page/DataReport/flowReport/FlowSourceReport.vue'], resolve),
                    meta: {
                        title: '流量源报告'
                    }
                },
                {
                    path: '/datareport/flowreport/FlowSourceArea',
                    component: resolve => require(['../components/page/DataReport/flowReport/FlowSourceArea.vue'], resolve),
                    meta: {
                        title: '流量源报告-地区'
                    }
                },
                {
                    path: '/datareport/flowreport/addetailedreport',
                    component: resolve => require(['../components/page/DataReport/flowReport/ADdetailedReport.vue'], resolve),
                    meta: {
                        title: '详细',
                        keepAlive:true,
                    }
                },
                {
                    path: '/datareport/flowreport/advertisingreport',
                    component: resolve => require(['../components/page/DataReport/flowReport/AdvertisingReport.vue'], resolve),
                    meta: {
                        title: '广告位报告'
                    }
                },
                {
                    path: '/datareport/adplatformreport',
                    component: resolve => require(['../components/page/DataReport/ADplatformReport.vue'], resolve),
                    meta: {
                        title: 'SDK报告'
                    }
                },
                {
                    path: '/datareport/ADplatformIn',
                    component: resolve => require(['../components/page/DataReport/ADplatformIn.vue'], resolve),
                    meta: {
                        title: 'SDK报告'
                    }
                },
                {
                    path: '/datareport/flowCosumer',
                    component: resolve => require(['../components/page/DataReport/flowCosumer.vue'], resolve),
                    meta: {
                        title: '广告平台报告'
                    }
                },
                {
                    path: '/datareport/flowCosumer2',
                    component: resolve => require(['../components/page/DataReport/flowCosumer2.vue'], resolve),
                    meta: {
                        title: '详细广告平台报告'
                    }
                },
                {
                    path: '/datareport/orderreport',
                    component: resolve => require(['../components/page/DataReport/OrderReport.vue'], resolve),
                    meta: {
                        title: '订单报告'
                    }
                },
                {
                    path: '/datareport/orderdetail',
                    component: resolve => require(['../components/page/DataReport/OrderDetail.vue'], resolve),
                    meta: {
                        title: '详情'
                    }
                },
                {
                    path: '/datareport/idearone',
                    component: resolve => require(['../components/page/DataReport/idearOne.vue'], resolve),
                    meta: {
                        title: '创意报告'
                    }
                },
                {
                    path: '/datareport/ideareport',
                    component: resolve => require(['../components/page/DataReport/ideaReport.vue'], resolve),
                    meta: {
                        title: '详情'
                    }
                },
                {
                    path: '/datareport/planreport',
                    component: resolve => require(['../components/page/DataReport/PlanReport.vue'], resolve),
                    meta: {
                        title: '计划报告'
                    }
                },
                {
                    path: '/datareport/plandetail',
                    component: resolve => require(['../components/page/DataReport/PlanDetail.vue'], resolve),
                    meta: {
                        title: '详情'
                    }
                },
                {
                    path: '/datareport/ideaplan',
                    component: resolve => require(['../components/page/DataReport/ideaPlan.vue'], resolve),
                    meta: {
                        title: '详情'
                    }
                },
                {
                    path: '/datareport/ideartwo',
                    component: resolve => require(['../components/page/DataReport/idearTwo.vue'], resolve),
                    meta: {
                        title: '创意报告'
                    }
                },

                {
                    path: '/datareport/agentreport',
                    component: resolve => require(['../components/page/DataReport/AgentReport.vue'], resolve),
                    meta: {
                        title: '代理商报告'
                    }
                },
                {
                    path: '/datareport/customerreport',
                    component: resolve => require(['../components/page/DataReport/CustomerReport.vue'], resolve),
                    meta: {
                        title: '客户报告'
                    }
                },
                {
                    path: '/datareport/finalReport',
                    component: resolve => require(['../components/page/DataReport/finalReport.vue'], resolve),
                    meta: {
                        title: '结算报告'
                    }
                },
                {
                    path: '/datareport/finalReportIn',
                    component: resolve => require(['../components/page/DataReport/finalReportIn.vue'], resolve),
                    meta: {
                        title: '结算报告详情'
                    }
                },
                {
                    path: '/datareport/bookReport',
                    component: resolve => require(['../components/page/DataReport/bookReport.vue'], resolve),
                    meta: {
                        title: '图书分类报告'
                    }
                },
                {
                    path: '/datareport/bookReportIn',
                    component: resolve => require(['../components/page/DataReport/bookReportIn.vue'], resolve),
                    meta: {
                        title: '图书分类详情'
                    }
                },


                {
                    path: '/dmp/datasource',
                    component: resolve => require(['../components/page/dmp/DataSource.vue'], resolve),
                    meta: {
                        title: '数据源管理'
                    }
                },
                {
                    path: '/dmp/dataset',
                    component: resolve => require(['../components/page/dmp/DataSet.vue'], resolve),
                    meta: {
                        title: '数据集管理'
                    }
                },
                {
                    path: '/dmp/labelgroup',
                    component: resolve => require(['../components/page/dmp/Labelgroup.vue'], resolve),
                    meta: {
                        title: '标签组'
                    }
                },
                {
                    path: '/dmp/labelgudge',
                    component: resolve => require(['../components/page/dmp/LabelGudge.vue'], resolve),
                    meta: {
                        title: '标签判定'
                    }
                },
                {
                    path: '/dmp/crowdmanagement',
                    component: resolve => require(['../components/page/dmp/CrowdManagement.vue'], resolve),
                    meta: {
                        title: '人群管理'
                    }
                },
                {
                    path: '/dmp/userphoto',
                    component: resolve => require(['../components/page/dmp/UserPhoto.vue'], resolve),
                    meta: {
                        title: '用户画像'
                    }
                },
                {
                    path: '/dmp/pnhto',
                    component: resolve => require(['../components/page/dmp/pnhto.vue'], resolve),
                    meta: {
                        title: '详细'
                    }
                },
                {
                    path: '/AuditManagement/index',
                    name:'index',
                    component: resolve => require(['../components/page/AuditManagement/index.vue'], resolve),
                    meta: {
                        title: '激励视频'
                    }
                },
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '/404',
            component: resolve => require(['../components/page/404.vue'], resolve)
        },
        {
            path: '/403',
            component: resolve => require(['../components/page/403.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        },


    ]
})
