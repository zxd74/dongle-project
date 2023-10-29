  <template>
    <div>
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="second_1">
              <el-form ref="form" :model="form"   status-icon label-width="100px">
                    <el-form-item label="选择订单:">
                      <el-select v-model="form.options1" placeholder="请选择订单">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="投放名称:">
                      <el-input v-model="form.user_name" placeholder="请输入投放名称" style="width:40%"></el-input>
                    </el-form-item>
                    <el-form-item label="计费方式:">
                      <el-tag>{{name}}</el-tag>
                    </el-form-item>
                    <div >
                        <el-form-item label="私有化交易ID:">
                        <el-input v-model="form.user_name" placeholder="" style="width:40%"></el-input>
                        </el-form-item>
                        <el-form-item label="量级:">
                        <el-input v-model="form.user_name" placeholder="" style="width:40%"></el-input>
                        </el-form-item>
                    </div>
                    <el-form-item label="投放类型:">
                        <el-radio v-model="form.put_type" label="1">网站</el-radio>
                        <el-radio v-model="form.put_type" label="2">IOS应用</el-radio>
                        <el-radio v-model="form.put_type" label="3">安卓应用</el-radio>
                        <el-radio v-model="form.put_type" label="4">深度链接</el-radio>
                      <el-form-item label="推广链接:" v-if="form.put_type == 1 || form.put_type == 2 || form.put_type == 3 || form.put_type == 4 ">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="应用id:" v-if="form.put_type == 2">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="应用包名:" v-if="form.put_type == 3 || form.put_type == 4">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                    </el-form-item>
                    <el-form-item label="选择广告位:">
                        <el-radio v-model="form.select_AD" label="1">广告位</el-radio>
                        <el-radio v-model="form.select_AD" label="2">广告位包</el-radio>
                    </el-form-item>                 
                    <el-form-item label="PC站:" v-if="form.select_AD ==1">
                        <div v-for="(address1,index_) in form.make_address1" :key="index_">
                            <el-radio v-model="form.number_facility" label="1">广告位</el-radio>
                        </div>
                    </el-form-item>
                    <el-form-item label="iOS端:" v-if="form.select_AD ==1">
                         <div v-for="(address2,value_) in form.click_address1" :key="value_">
                            <el-radio v-model="form.number_facility" label="1">广告位</el-radio>
                        </div>
                    </el-form-item>
                    <el-form-item label="" v-if="form.select_AD ==2">
                          <div v-for="(address2,value_) in form.click_address1" :key="value_">
                            <el-radio v-model="form.number_facility" label="1">单选按钮</el-radio>
                            <!-- <el-input v-model="address2.click_address" style="width:80%"></el-input>
                            <el-button type="primary" icon="el-icon-plus" @click="click_address"></el-button>
                            <el-button type="primary" icon="el-icon-close" @click="delete_click(value_)" v-show="value_ !=0"></el-button> -->
                          </div>
                    </el-form-item>
              </el-form> 
            </el-tab-pane>
            <el-tab-pane label="定向设置" name="second_2">
               <el-tag type="success">人群定向</el-tag>
               <el-form ref="form" :model="form"  status-icon label-width="100px">
                  <el-form-item label="性别:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                   </el-form-item>
                  <el-form-item label="年龄:">
                          <el-radio v-model="form.age" label="1">不限</el-radio>
                          <el-radio v-model="form.age" label="2">自定义</el-radio>
                          <el-form-item v-if="form.age == 2">
                               <el-checkbox v-model="form.age1">0-18</el-checkbox>
                               <el-checkbox v-model="form.age2">19-25</el-checkbox>
                               <el-checkbox v-model="form.age3">26-35</el-checkbox>
                               <el-checkbox v-model="form.age4">36-45 </el-checkbox>
                               <el-checkbox v-model="form.age5">46-55</el-checkbox>
                               <el-checkbox v-model="form.age6">56及以上</el-checkbox>
                          </el-form-item>
                  </el-form-item>
                  <el-form-item label="家庭情况:">
                          <el-radio v-model="form.age" label="1">不限</el-radio>
                          <el-radio v-model="form.age" label="2">自定义</el-radio>
                          <el-radio v-model="form.age" label="2">自定义</el-radio>
                  </el-form-item>
                  <el-form-item label="收入:">
                          <el-radio v-model="form.age" label="1">不限</el-radio>
                          <el-radio v-model="form.age" label="2">自定义</el-radio>
                          <el-radio v-model="form.age" label="2">自定义</el-radio>
                  </el-form-item>
                  <el-form-item label="兴趣:">
                          <el-radio v-model="form.age" label="1">不限</el-radio>
                          <el-radio v-model="form.age" label="2">自定义</el-radio>
                          <el-radio v-model="form.age" label="2">自定义</el-radio>
                  </el-form-item>
                 <el-tag type="success">地域定向</el-tag>
                 <el-form-item label="类型:">
                          <el-radio v-model="form.sex" label="1">独立地域</el-radio>
                          <el-radio v-model="form.sex" label="2">地域包</el-radio>
                 </el-form-item>
                 <el-form-item label="判定:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                 <el-form-item label="城市:">
                          <!-- <diqu :selectedData.sync="manyAreaValue"></diqu> -->
                          <template>
                                <el-transfer
                                    filterable
                                    :filter-method="filterMethod"
                                    filter-placeholder="请输入城市拼音"
                                    v-model="value2"
                                    :data="data2">
                                </el-transfer>
                            </template>
                 </el-form-item>
                 <el-tag type="success">设备定向</el-tag>
                 <el-form-item label="操作系统:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                 <el-form-item label="运营商:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                 <el-form-item label="联网方式:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                 <el-tag type="success">购车兴趣定向</el-tag>
                  <el-form-item label="价格区间:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                  <el-form-item label="品牌:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                  <el-form-item label="车系:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                  <el-form-item label="级别:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                 </el-form-item>
                  <!-- <el-form-item label="兴趣:">
                          <el-radio v-model="form.interest" label="1">不限</el-radio>
                          <el-radio v-model="form.interest" label="2">自定义</el-radio>
                            <el-form-item v-if="form.interest == 2">
                               <el-checkbox v-model="form.interest1">游戏</el-checkbox>
                               <el-checkbox v-model="form.interest2">购物</el-checkbox>
                               <el-checkbox v-model="form.interest3">太极熊猫</el-checkbox>
                               <el-checkbox v-model="form.interest4">天下HD专用</el-checkbox>
                               <el-checkbox v-model="form.interest5">TD游戏</el-checkbox>
                               <el-checkbox v-model="form.interest6">TD游戏-休闲时间</el-checkbox>
                               <el-checkbox v-model="form.interest7">TD游戏-跑酷竞速</el-checkbox>
                               <el-checkbox v-model="form.interest8">TD游戏-网络游戏</el-checkbox>
                               <el-checkbox v-model="form.interest9">TD游戏-宝石消除</el-checkbox>
                               <el-checkbox v-model="form.interest10">TD游戏-动作射击</el-checkbox>
                               <el-checkbox v-model="form.interest11">TD游戏-扑克棋牌</el-checkbox>
                               <el-checkbox v-model="form.interest12">TD游戏-儿童益智</el-checkbox>
                               <el-checkbox v-model="form.interest13">TD游戏-塔防守卫</el-checkbox>
                               <el-checkbox v-model="form.interest14">TD游戏-体育格斗</el-checkbox>
                               <el-checkbox v-model="form.interest15">TD游戏-角色扮演</el-checkbox>
                               <el-checkbox v-model="form.interest16">TD游戏-经营策略</el-checkbox>
                               <el-checkbox v-model="form.interest17">TD游戏-养成类</el-checkbox>
                               <el-checkbox v-model="form.interest18">TD游戏-挂机类</el-checkbox>
                               <el-checkbox v-model="form.interest19">TD游戏-文字游戏</el-checkbox>
                               <el-checkbox v-model="form.interest20">TD游戏-博彩类</el-checkbox>
                               <el-checkbox v-model="form.interest21">贴吧游戏</el-checkbox>
                               <el-checkbox v-model="form.interest22">贴吧电商</el-checkbox>
                               <el-checkbox v-model="form.interest23">优酷O2PC定向</el-checkbox>
                            </el-form-item>
                  </el-form-item>
                  <el-form-item label="渠道:">
                          <el-radio v-model="form.channel" label="1">不限</el-radio>
                          <el-radio v-model="form.channel" label="2">自定义</el-radio>
                            <el-form-item v-if="form.channel == 2">
                               <el-checkbox v-model="form.channel1">流量来源：ADX</el-checkbox>
                               <el-checkbox v-model="form.channel2">流量来源：移动联盟</el-checkbox>
                            </el-form-item>
                  </el-form-item>
                  <el-form-item label="贴片展示顺序:">
                          <el-radio v-model="form.order" label="1">不限</el-radio>
                          <el-radio v-model="form.order" label="2">自定义</el-radio>
                            <el-form-item v-if="form.order == 2">
                               <el-checkbox v-model="form.order1">从第一帧</el-checkbox>
                               <el-checkbox v-model="form.order2">从第二帧</el-checkbox>
                               <el-checkbox v-model="form.order3">从第三帧</el-checkbox>
                               <el-checkbox v-model="form.order4">从第四帧</el-checkbox>
                               <el-checkbox v-model="form.order5">从第五帧</el-checkbox>
                               <el-checkbox v-model="form.order6">从第六帧</el-checkbox>
                            </el-form-item>
                  </el-form-item>
                  <el-form-item label="贴片投放位置:">
                          <el-radio v-model="form.position" label="1">不限</el-radio>
                          <el-radio v-model="form.position" label="2">自定义</el-radio>
                          <el-form-item v-if="form.position == 2">
                               <el-checkbox v-model="form.position1">前贴</el-checkbox>
                               <el-checkbox v-model="form.position2">中贴</el-checkbox>
                               <el-checkbox v-model="form.position3">后贴</el-checkbox>
                          </el-form-item>
                  </el-form-item>
                  <el-form-item label="设备品牌过滤:">
                          <el-radio v-model="form.guolv" label="1">不限</el-radio>
                          <el-radio v-model="form.guolv" label="2">自定义</el-radio>
                            <el-form-item v-if="form.guolv == 2">
                               <el-checkbox v-model="form.guolv1">华为</el-checkbox>
                            </el-form-item>
                  </el-form-item>
                  <el-form-item label="终端类型:">
                          <el-radio v-model="form.zdlx" label="1">移动</el-radio>
                          <el-radio v-model="form.zdlx" label="2">PC</el-radio>
                          <el-radio v-model="form.zdlx" label="3">WAP</el-radio>
                  </el-form-item>
              
                  <el-form-item label="操作系统:">
                          <el-radio v-model="form.system" label="1">不限</el-radio>
                          <el-radio v-model="form.system" label="2">IOS</el-radio>
                          <el-radio v-model="form.system" label="3">安卓</el-radio>
                  </el-form-item>
                  <el-form-item label="运营商:">
                          <el-radio v-model="form.yys" label="1">不限</el-radio>
                          <el-radio v-model="form.yys" label="2">移动</el-radio>
                          <el-radio v-model="form.yys" label="3">联通</el-radio>
                          <el-radio v-model="form.yys" label="4">电信</el-radio>
                  </el-form-item>
                  <el-form-item label="网络:">
                          <el-radio v-model="form.network" label="1">不限</el-radio>
                          <el-radio v-model="form.network" label="2">WIFI</el-radio>
                          <el-radio v-model="form.network" label="3">2G</el-radio>
                          <el-radio v-model="form.network" label="4">3G</el-radio>
                          <el-radio v-model="form.network" label="5">4G</el-radio>
                  </el-form-item>
                  <el-form-item label="重定向:">
                          <el-radio v-model="form.direction" label="1">不限</el-radio>
                          <el-radio v-model="form.direction" label="2">自定义</el-radio>
                            <el-form-item v-if="form.direction == 2">
                               <el-checkbox v-model="form.direction1">点击数据</el-checkbox>
                               <el-checkbox v-model="form.direction2">激活数据</el-checkbox>
                            </el-form-item>
                  </el-form-item>
                  <el-form-item label="设备:">
                        <div v-for="(data,index) in form.click_shebei1" :key="index">
                          <el-input v-model="data.click_shebei" style="width:80%"></el-input>
                       </div>
                       <el-button type="primary" icon="el-icon-plus" @click="click_shebei"></el-button>
                  </el-form-item>
                  <el-form-item label="微博DMP规则:">
                          <el-input v-model="form.rule" placeholder="请输入话题"></el-input>
                  </el-form-item> -->
                </el-form> 
            </el-tab-pane>
            <el-tab-pane label="设置排期" name="second_3">
               <el-form ref="form" :model="form"  status-icon label-width="100px">

                  <el-tag type="success">设置排期</el-tag>
                    <div v-for="(item,index_) in form.Schedule" :key="index_">
                        <el-form-item label="日期:">
                            <el-date-picker
                            v-model="form.add_time"
                            type="date"
                            placeholder="选择日期">
                            </el-date-picker>
                                至
                            <el-date-picker
                            v-model="form.name"
                            type="date"
                            placeholder="选择日期">
                            </el-date-picker>
                            <el-button style="margin-top: 12px;" @click="AddSchedule">增加</el-button>
                        </el-form-item>
                        <el-form-item label="时段">
                             <el-radio v-model="form.time_frame" :label="1">全时段</el-radio>
                             <el-radio v-model="form.time_frame" :label="2">指定时段</el-radio>
                             <div v-for="(time,value_i) in form.set_time" :key="value_i" v-if="form.time_frame == 2"> 
                                    <el-select v-model="time.begin" placeholder="请选择">
                                            <el-option
                                            v-for="(item,i_index) in hours"
                                            :key="i_index"
                                            :label="item.label"
                                            :value="item.value">
                                            </el-option>
                                    </el-select> 至
                                    <el-select v-model="time.end" placeholder="请选择">
                                            <el-option
                                            v-for="(item,e_index) in hours"
                                            :key="e_index"
                                            :label="item.label"
                                            :value="item.value">
                                            </el-option>
                                    </el-select>
                                     <!-- 添加和删除时间按钮  -->
                                    <el-button type="primary" icon="search" @click="new_time">+</el-button>
                                    <el-button type="primary" icon="search" @click="delete_time(value_i)" v-show="value_i != 0">X</el-button>
                                </div>
                        </el-form-item>
                        <el-form-item >
                            <picker :time='form' :data="tableData"></picker>
                        </el-form-item>
                    </div>
                    <div class="td_color">
                            <div class="diamonds"></div>空闲
                            <div class="diamonds bg_color"></div>有投放
                    </div>
          
              </el-form> 
            </el-tab-pane>         
            <!-- <el-tab-pane label="广告位" name="second_4">
                  <el-form ref="form" :model="form"  status-icon label-width="100px">
                          <el-form-item label="媒体类型:">
                                <el-radio label="1" v-model="form.media_type">普通媒体</el-radio>
                                <el-radio label="2" v-model="form.media_type">联盟媒体</el-radio>
                          </el-form-item>
                          <el-form-item label="媒体:">

                          </el-form-item>
                          <el-form-item label="广告位:">
                                <el-radio label="1" v-model="form.AD_position">推荐页tag-小卡图文-原生广告</el-radio>
                                <el-radio label="2" v-model="form.AD_position">推荐页tag-大卡图文-原生广告</el-radio>
                                <el-radio label="3" v-model="form.AD_position">推荐页tag-三图-原生广告</el-radio>
                          </el-form-item>
                          <el-form-item label="查找方式:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.find_location1">APP</el-radio>
                                <el-radio label="2" v-model="form.find_location1">广告位</el-radio>
                          </el-form-item>
                          <el-form-item label="广告位:" v-if="form.find_location == 2">
                                
                          </el-form-item>
                          <el-form-item label="app资源:">
                                <el-checkbox v-model="form.checked1">app分类-汽车</el-checkbox>
                                <el-checkbox v-model="form.checked2">行园汽车</el-checkbox>
                                <el-checkbox v-model="form.checked3">app分类-新闻资讯</el-checkbox>
                                <el-checkbox v-model="form.checked4">行园汽车</el-checkbox>
                          </el-form-item>
                          <el-form-item label="是否过滤已选app:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.guolv_APP">否</el-radio>
                                <el-radio label="2" v-model="form.guolv_APP">是</el-radio>
                          </el-form-item>
                          <el-form-item label="广告位:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.find_location2">插屏600*500</el-radio>
                                <el-radio label="2" v-model="form.find_location2">开屏640*960</el-radio>
                                <el-radio label="3" v-model="form.find_location2">贴片15s*500</el-radio>
                                <el-radio label="4" v-model="form.find_location2">原生190*130（20字内描述，素材-50k）-原生广告*960</el-radio>
                                <el-radio label="5" v-model="form.find_location2">原生640*360（20字标题，20字描述）-原生广告</el-radio>
                          </el-form-item>
                          <el-form-item label="是否开启系统优化:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.xtyh">否</el-radio>
                                <el-radio label="2" v-model="form.xtyh">是</el-radio>
                                <span class="span">*只有做了激活上报对接的才有效</span>
                          </el-form-item>
                          <el-form-item label="预设CPA:" v-if="form.media_type == 2">
                                <el-radio label="1" v-model="form.cpa">否</el-radio>
                                <el-radio label="2" v-model="form.cpa">是</el-radio>
                          </el-form-item>
                   </el-form> 
            </el-tab-pane> -->
            <!-- <el-tab-pane label="确认提交" name="second_5">
                   <el-form ref="form" :model="form"  status-icon>
                       <el-form-item label="推广计划:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="推广单元:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="投放方式:">
                                <span>11</span>
                       </el-form-item>
                      <el-form-item label="价格:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="每日限额:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="投放日期:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="投放类型:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="曝光上报地址:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="点击上报地址:">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="扩展参数(ext_creative_id):">
                                <span>11</span>
                       </el-form-item>
                       <el-form-item label="广告位:">
                                <span>11</span>
                       </el-form-item>
                      <el-form-item label="定向设置:">
                                <div class='r_div'>
                                  <el-form-item label="性别:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="操作系统:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="运营商:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="网络:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="年龄:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="学历:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="行为:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="兴趣:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="设备品牌过滤:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="终端:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="地区:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                                  <el-form-item label="规则:">
                                    <span>{{11}}</span>
                                  </el-form-item>
                               </div>
                       </el-form-item>
                      
                   </el-form> 
            </el-tab-pane> -->
        </el-tabs> 
       <span slot="footer" class="dialog-footer">
                <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
                <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_3'">下一步</el-button>
                <!-- <el-button @click="newVisible = false">取 消</el-button> -->
                <el-button type="primary" @click="saveNews" v-if="activeName == 'second_3'">确 定</el-button>
       </span>
    </div>
</template>

<script>
// import picker from "../../../components/common/picker/index.vue";
// import diqu from '../../../components/common/diqu.vue'


export default {
  name: "basetable",
  data() {
     const generateData2 = _ => {
        const data = [];
        const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
        const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu'];
        cities.forEach((city, index) => {
          data.push({
            label: city,
            key: index,
            pinyin: pinyin[index]
          });
        });
        return data;
      };

    return {
        data2: generateData2(),
        value2: [],
        filterMethod(query, item) {
          return item.pinyin.indexOf(query) > -1;
        },
      url: "./static/vuetable.json",
      tableData: [],
      cur_page: 1,
      select_cate: "",
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      newVisible: false,
      newaddAD: false,
      template_dialog: false,
      currentRow: null,
      idx: -1,
      activeName: "second_1",
      radio:'',
      select_AD:'',
      name: "",
      form: {
        set_time:[{begin:'',end:''}],
        Schedule:[{}],
        time_frame:'',
        name: "",
        date: "",
        switch: true,
        add_time: "",
        options1: "",
        type: 1,
        types: null,
        flow_management: null,
        data_report: null,
        make_address1: [{ make_address: "" }],
        click_address1: [{ click_address: "" }],
        click_shebei1: [{ click_shebei: "" }],
        put_PDB: "1",
        charge_mode: '1',
        number_facility: '1',
        number_ip: '1',
        number_id: '2',
        number_guiyin: '1',
        put_type: '1',
        age: "1",
        checked: false,
        sex: "1",
        Education: "1",
        Action: "1",
        Crowd: "1",
        interest: "1",
        channel: "1",
        order: "1",
        position: "1",
        guolv: "1",
        zdlx: "1",
        diqu: "1",
        system: "1",
        yys: "1",
        network: "1",
        direction: "1",
        rule: "",
        media_type:'1',
        AD_position:'1',
        find:'1',
        APP_zy:'',
        find_location1:'1',
        guolv_APP:'1',
        xtyh:'1',
        cpa:'',
        syID:'',
        price:'',
        unitLimit:'',
        options: [
          {
            value: "1",
            label: "收入类订单"
          },
          {
            value: "2",
            label: "战略内退订单"
          },
          {
            value: "3",
            label: "置换订单"
          },
          {
            value: "4",
            label: "内推订单"
          }
        ],
        value: "1",
      },
      options:[],
      options2: "2",
      options3: "3",
      hours: [
          {
            value: "1",
            label: "1"
          },
          {
            value: "2",
            label: "2"
          },
          {
            value: "3",
            label: "3"
          },
          {
            value: "4",
            label: "4"
          },
          {
            value: "5",
            label: "5"
          },
          {
            value: "6",
            label: "6"
          },
          {
            value: "7",
            label: "7"
          },
          {
            value: "8",
            label: "8"
          },
          {
            value: "9",
            label: "9"
          },
          {
            value: "10",
            label: "10"
          },
          {
            value: "1",
            label: "11"
          },
          {
            value: "12",
            label: "12"
          },
          {
            value: "13",
            label: "13"
          },
          {
            value: "14",
            label: "14"
          },
          {
            value: "15",
            label: "15"
          },
          {
            value: "16",
            label: "16"
          },
          {
            value: "17",
            label: "17"
          },
          {
            value: "18",
            label: "18"
          },
          {
            value: "19",
            label: "19"
          },
          {
            value: "20",
            label: "20"
          },
          {
            value: "21",
            label: "21"
          },
          {
            value: "22",
            label: "22"
          },
          {
            value: "23",
            label: "23"
          },
          {
            value: "24",
            label: "24"
          },
        ],
    };
  },
  created() {
    // this.getData();
  },
  components: {
    picker: picker,
    diqu:diqu,
  },

  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    // // 获取 easy-mock 的模拟数据
    // getData() {
    //   // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
    //   if (process.env.NODE_ENV === "development") {
    //     this.url = "/ms/table/list";
    //   }

    //   this.$axios
    //     .post(this.url, {
    //       page: this.cur_page
    //     })
    //     .then(res => {
    //       this.tableData = res.data.list;
    //     });
    // },
    search() {
      this.is_search = true;
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleEdit(index, row) {
      this.idx = index;
      const item = this.tableData[index];
      this.form = {
        name: item.name,
        date: item.date,
        address: item.address
      };
      this.editVisible = true;
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },

    delAll() {
      const length = this.multipleSelection.length;
      let str = "";
      this.del_list = this.del_list.concat(this.multipleSelection);
      for (let i = 0; i < length; i++) {
        str += this.multipleSelection[i].name + " ";
      }
      this.$message.error("删除了" + str);
      this.multipleSelection = [];
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 保存编辑
    saveEdit() {
      this.$set(this.tableData, this.idx, this.form);
      this.editVisible = false;
      this.$message.success(`修改第 ${this.idx + 1} 行成功`);
    },
    // 新建
    create() {
      this.newVisible = true;
      this.form = {
        name: "",
        date: "",
        switch: true,
        add_time: "",
        options1: "",
        type: 1,
        types: null,
        flow_management: null,
        data_report: null,
        make_address1: [{ make_address: "" }],
        click_address1: [{ click_address: "" }],
        click_shebei1: [{ click_shebei: "" }],
        put_PDB: "1",
        charge_mode: '1',
        number_facility: '1',
        number_ip: '1',
        number_id: '2',
        number_guiyin: '1',
        put_type: '1',
        age: "1",
        checked: false,
        sex: "1",
        Education: "1",
        Action: "1",
        Crowd: "1",
        interest: "1",
        channel: "1",
        order: "1",
        position: "1",
        guolv: "1",
        zdlx: "1",
        diqu: "1",
        system: "1",
        yys: "1",
        network: "1",
        direction: "1",
        rule: "",
        media_type:'1',
        AD_position:'1',
        find:'1',
        APP_zy:'',
        find_location1:'1',
        guolv_APP:'1',
        xtyh:'1',
        cpa:'',
        syID:'',
        price:'',
        unitLimit:'',
        options: [
          {
            value: "1",
            label: "流量管理员"
          },
          {
            value: "2",
            label: "广告客户"
          },
          {
            value: "3",
            label: "直客客户"
          },
          {
            value: "4",
            label: "代理商"
          }
        ],
        value: "1",
      };
      this.activeName = "second_1";
    },
    saveNews() {
      // this.tableData.push(this.form);
      this.newVisible = false;
    },
    saveAD() {
      this.newaddAD = false;
    },
    // 确定删除
    deleteRow() {
      this.tableData.splice(this.idx, 1);
      this.$message.success("删除成功");
      this.delVisible = false;
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    template_() {},
    handleClick(tab, event) {
      console.log(tab, event);
    },
    make_address() {
      this.form.make_address1.push({ make_address: "" });
    },
    delete_make(i) {
      this.form.make_address1.splice(i,1)
    },
    delete_click(z) {
      this.form.click_address1.splice(z,1)
    },
    click_address() {
      this.form.click_address1.push({ click_address: "" });
    },
    click_shebei() {
      console.log(this.form);
      this.form.click_shebei1.push({ click_shebei: "" });
    },
    last_(){
        let value = this.activeName.split("_");
        this.activeName = value[0]+"_"+parseInt(parseInt(value[1])-1)
    },
    next_(){
        let value = this.activeName.split("_");
        this.activeName = value[0]+"_"+parseInt(parseInt(value[1])+1)
        console.log(  this.activeName)
    }, 
    AddSchedule(){
        this.form.Schedule.push({});
    },
     new_time(){
          this.form.set_time.push({begin:'',end:''})
    },
    delete_time(i){
        this.form.set_time.splice(i,1)
    },
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.header-select {
  margin-bottom: 20px;
}
.btn_plan {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #249cd3;
  border: 1px silver solid;
  color: aliceblue;
  line-height: 50px;
}
.btn_plan_2 {
  text-align: center;
  margin: 0 auto;
  width: 100px;
  height: 50px;
  background-color: #ffffff;
  border: 1px silver solid;
  color: black;
  line-height: 50px;
  margin-top: 20px;
}
.span{
  color: red
}
.el-checkbox+.el-checkbox{
  margin-left: 0px;
}
.r_div{
    margin-left: 80px
}
.border{
  border: 2px dashed #26a580;
  padding: 10px;
}
.div{
  margin-top: 10px;
}
.td_color{
    margin-left: 47%;
}
.diamonds{
   display: inline-block;
   width: 20px;
   height: 20px;
   border: 1px solid silver;

}
.bg_color{
  background: silver;
}

</style>
