<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input v-model="select_word" placeholder="请输入投放名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="search" @click="create">新建投放</el-button>
            </div>
            <div class="header-select">
                 <template>
                        <el-select v-model="options1" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
                        <el-select v-model="options2" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
                        <el-select v-model="options3" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                        </el-select>
              </template>
            </div>
            <el-table :data="data" border style="width: 100%" ref="multipleTable">
                <el-table-column prop="date" label="序号" align="center">
                </el-table-column>
                <el-table-column prop="date" label="投放名称"  width="200" align="center">
                </el-table-column>
                <el-table-column prop="date" label="每日限额" align="center">
                </el-table-column>
                <el-table-column prop="date" label="计费/底价" align="center">
                </el-table-column>
                <el-table-column prop="date" label="投放方式" align="center">
                </el-table-column>
                <el-table-column  label="状态"  width="" align="center">
                  <template slot-scope="scope">
                     <el-switch v-model="scope.row.switch"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="操作"  width="150" align="center">
                    <template slot-scope="scope">
                        <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
               <el-pagination @current-change="handleCurrentChange" layout="total,prev, pager, next,jumper" :total="total">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

          <!-- 新建弹出框 -->
        <el-dialog title="新建订单" :visible.sync="newVisible" width="65%">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="second_1">
              <el-form ref="form" :model="form"   status-icon label-width="100px">
                    <el-form-item label="投放类型:">
                      精准投放
                    </el-form-item>
                    <el-form-item label="投放名称:">
                        <el-input v-model="form.user_name" style="width:40%"></el-input>
                    </el-form-item>
                    <div class="add_task" v-for="(end_div,index_) in append_div" :key="index_">
                      <dir class="btn_right">
                        <el-button type="primary" icon="search" @click="add_div">+</el-button>
                        <el-button type="primary" icon="search" @click="delete_div(index_)" v-show="index_ != 0">X</el-button>
                      </dir>
                        <el-form-item label="投放日期:">
                            <el-date-picker
                                v-model="end_div.time"
                                type="daterange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="投放时段:">
                             <el-radio v-model="end_div.time_frame" label="1">全时段</el-radio>
                             <el-radio v-model="end_div.time_frame" label="2">指定时段</el-radio>
                              <el-form-item v-if="end_div.time_frame == 2">
                                 <div v-for="(time,value_i) in form.set_time" :key="value_i"> 
                                    <el-select v-model="time.begin" placeholder="请选择">
                                            <el-option
                                            v-for="(item,i_index) in hours"
                                            :key="i_index"
                                            :label="item.label"
                                            :value="item.value">
                                            </el-option>
                                    </el-select>至
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
                        </el-form-item>
                        <el-form-item label="每日限额:">
                            <el-input-number v-model="end_div.unitLimit" style="width:40%"></el-input-number>
                        </el-form-item>
                    </div>
                    <el-form-item label="投放方式:">
                         <el-radio v-model="form.deliveryMode" label="1">标准投放</el-radio>
                         <el-radio v-model="form.deliveryMode" label="2">匀速投放</el-radio>
                    </el-form-item>
                    <el-form-item label="价格:">
                       <el-input-number v-model="form.price" style="width:40%"></el-input-number>元
                    </el-form-item>
                    <el-form-item label="是否设备频次控制:">
                        <el-radio v-model="form.number_facility" label="1">否</el-radio>
                        <el-radio v-model="form.number_facility" label="2">是</el-radio>
                    </el-form-item>
                    <el-form-item label="是否ip频次控制:">
                        <el-radio v-model="form.number_ip" label="1">否</el-radio>
                        <el-radio v-model="form.number_ip" label="2">是</el-radio>
                    </el-form-item>
                    <el-form-item label="是否过滤非法设备ID:">
                        <el-radio v-model="form.number_id" label="1">否</el-radio>
                        <el-radio v-model="form.number_id" label="2">是</el-radio>
                    </el-form-item>
                    <el-form-item label="是否归因:">
                        <el-radio v-model="form.number_guiyin" label="1">否</el-radio>
                        <el-radio v-model="form.number_guiyin" label="2">是</el-radio>
                    </el-form-item>
                    <el-form-item label="投放类型:">
                        <el-radio v-model="form.put_type" label="1">网站</el-radio>
                        <el-radio v-model="form.put_type" label="2">IOS应用</el-radio>
                        <el-radio v-model="form.put_type" label="3">安卓应用</el-radio>
                        <el-radio v-model="form.put_type" label="4">深度链接</el-radio>
                      <el-form-item label="推广链接:" v-if="form.put_type == 1">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="推广链接:" v-if="form.put_type == 2">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="应用id:" v-if="form.put_type == 2">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="推广链接:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="应用包名:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="版本号:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="版本名称:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="包大小(字节)::" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="包签名:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="包MD5值:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="系统最低版本要求:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="系统最低版本要求:" v-if="form.put_type == 3">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="推广链接:" v-if="form.put_type == 4">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                      <el-form-item label="应用包名:" v-if="form.put_type == 4">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                      </el-form-item>
                    </el-form-item>
                    <div class="add_task">
                        <el-form-item label="曝光上报地址:">
                            <div v-for="(address1,index_) in form.make_address1" :key="index_">
                              <el-input v-model="address1.make_address" style="width:80%"></el-input>
                              <el-button type="primary" icon="el-icon-plus" @click="make_address"></el-button>
                              <el-button type="primary" icon="el-icon-close" @click="delete_task(index_)" v-show="index_!= 0"></el-button>
                         </div>
                        </el-form-item>
                    </div>
                    <div class="add_task">
                        <el-form-item label="点击上报地址:">
                          <div v-for="(address2,index_address) in form.click_address1" :key="index_address">
                            <el-input v-model="address2.click_address" style="width:80%"></el-input>
                            <el-button type="primary" icon="el-icon-plus" @click="click_address"></el-button>
                            <el-button type="primary" icon="el-icon-close" @click="delete_click(index_address)" v-show="index_address != 0"></el-button>
                          </div>
                        </el-form-item>
                    </div>
                    <el-form-item label="扩展参数(ext_creative_id):" v-if="form.put_type == 4">
                          <el-input v-model="form.name" style="width:80%"></el-input>
                    </el-form-item>
                    <el-form-item label="是否PDB投放:">
                          <el-radio v-model="form.put_PDB" label="1">否</el-radio>
                          <el-radio v-model="form.put_PDB" label="2">是</el-radio>
                    </el-form-item>
                    <el-form-item label="私有化交易ID:" v-if="form.put_type == 4">
                          <el-input v-model="form.syID" style="width:80%"></el-input>
                    </el-form-item>
              </el-form> 
            </el-tab-pane>
            <el-tab-pane label="定向设置" name="second_2">
              <el-form ref="form" :model="form"  status-icon label-width="100px">
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
                  <el-form-item label="性别:">
                          <el-radio v-model="form.sex" label="1">不限</el-radio>
                          <el-radio v-model="form.sex" label="2">男</el-radio>
                          <el-radio v-model="form.sex" label="2">女</el-radio>
                  </el-form-item>
                  <el-form-item label="学历:">
                          <el-radio v-model="form.Education" label="1">不限</el-radio>
                          <el-radio v-model="form.Education" label="2">自定义</el-radio>
                          <el-form-item v-if="form.Education == 2">
                               <el-checkbox v-model="form.Education1">博士</el-checkbox>
                               <el-checkbox v-model="form.Education2">硕士</el-checkbox>
                               <el-checkbox v-model="form.Education3">本科</el-checkbox>
                               <el-checkbox v-model="form.Education4">高中 </el-checkbox>
                               <el-checkbox v-model="form.Education5">初中</el-checkbox>
                               <el-checkbox v-model="form.Education6">小学</el-checkbox>
                               <el-checkbox v-model="form.Education7">其他</el-checkbox>
                          </el-form-item>
                  </el-form-item>
                  <el-form-item label="行为:">
                          <el-radio v-model="form.Action" label="1">不限</el-radio>
                          <el-radio v-model="form.Action" label="2">自定义</el-radio>
                          <el-form-item v-if="form.Action == 2">
                               <el-checkbox v-model="form.Action1">母婴</el-checkbox>
                               <el-checkbox v-model="form.Action2">金融</el-checkbox>
                               <el-checkbox v-model="form.Action3">电商</el-checkbox>
                               <el-checkbox v-model="form.Action4">游戏 </el-checkbox>
                               <el-checkbox v-model="form.Action5">其他</el-checkbox>
                               <el-checkbox v-model="form.Action6">土豆站内</el-checkbox>
                               <el-checkbox v-model="form.Action7">奇艺影视</el-checkbox>
                               <el-checkbox v-model="form.Action8">贴吧-当代人物</el-checkbox>
                               <el-checkbox v-model="form.Action9">贴吧-电脑数码</el-checkbox>
                               <el-checkbox v-model="form.Action10">贴吧-电视节目</el-checkbox>
                               <el-checkbox v-model="form.Action11">贴吧-电视剧 </el-checkbox>
                               <el-checkbox v-model="form.Action12">贴吧-电影</el-checkbox>
                               <el-checkbox v-model="form.Action13">贴吧-动漫</el-checkbox>
                               <el-checkbox v-model="form.Action14">贴吧-高等院校</el-checkbox>
                               <el-checkbox v-model="form.Action15">贴吧-工农业产品</el-checkbox>
                               <el-checkbox v-model="form.Action16">贴吧-教育培训</el-checkbox>
                               <el-checkbox v-model="form.Action17">贴吧-金融</el-checkbox>
                               <el-checkbox v-model="form.Action18">贴吧-科学技术 </el-checkbox>
                               <el-checkbox v-model="form.Action19">贴吧-历史人物</el-checkbox>
                               <el-checkbox v-model="form.Action20">贴吧-企业</el-checkbox>
                               <el-checkbox v-model="form.Action21">贴吧-情感</el-checkbox>
                               <el-checkbox v-model="form.Action22">贴吧-人文自然</el-checkbox>
                               <el-checkbox v-model="form.Action23">贴吧-商业服务</el-checkbox>
                               <el-checkbox v-model="form.Action24">贴吧-社会</el-checkbox>
                               <el-checkbox v-model="form.Action25">贴吧-生活 </el-checkbox>
                               <el-checkbox v-model="form.Action26">贴吧-生活用品</el-checkbox>
                               <el-checkbox v-model="form.Action27">贴吧-体育</el-checkbox>
                               <el-checkbox v-model="form.Action28">贴吧-网友俱乐部</el-checkbox>
                               <el-checkbox v-model="form.Action29">母婴</el-checkbox>
                               <el-checkbox v-model="form.Action30">贴吧-文学</el-checkbox>
                               <el-checkbox v-model="form.Action31">贴吧-音乐</el-checkbox>
                               <el-checkbox v-model="form.Action32">贴吧-游戏 </el-checkbox>
                               <el-checkbox v-model="form.Action33">贴吧-娱乐明星</el-checkbox>
                               <el-checkbox v-model="form.Action34">贴吧-中小学</el-checkbox>
                               <el-checkbox v-model="form.Action35">贴吧-其它</el-checkbox>
                               <el-checkbox v-model="form.Action36">搞笑</el-checkbox>
                               <el-checkbox v-model="form.Action37">体育</el-checkbox>
                               <el-checkbox v-model="form.Action38">电影</el-checkbox>
                               <el-checkbox v-model="form.Action39">优酷站内 </el-checkbox>
                               <el-checkbox v-model="form.Action40">优酷影视</el-checkbox>
                               <el-checkbox v-model="form.Action41">优酷其他</el-checkbox>
                               <el-checkbox v-model="form.Action42">土豆影视</el-checkbox>
                               <el-checkbox v-model="form.Action43">土豆其他</el-checkbox>
                               <el-checkbox v-model="form.Action44">搜狐-新闻</el-checkbox>
                               <el-checkbox v-model="form.Action45">搜狐-影视综</el-checkbox>
                               <el-checkbox v-model="form.Action46">搜狐-长视频 </el-checkbox>
                               <el-checkbox v-model="form.Action47">搜狐-短视频</el-checkbox>
                               <el-checkbox v-model="form.Action48">搜狐-男性类</el-checkbox>
                               <el-checkbox v-model="form.Action49">优酷-男性类</el-checkbox>
                               <el-checkbox v-model="form.Action50">奇艺-男性类</el-checkbox>
                               <el-checkbox v-model="form.Action51">乐视-全部</el-checkbox>
                               <el-checkbox v-model="form.Action52">乐视-影视</el-checkbox>
                               <el-checkbox v-model="form.Action53">乐视-其他 </el-checkbox>
                               <el-checkbox v-model="form.Action54">优土动漫包</el-checkbox>
                               <el-checkbox v-model="form.Action55">奇艺动漫包</el-checkbox>
                            </el-form-item>
                  </el-form-item>
                  <el-form-item label="人群:">
                          <el-radio v-model="form.Crowd" label="1">不限</el-radio>
                          <el-radio v-model="form.Crowd" label="2">自定义</el-radio>
                            <el-form-item v-if="form.Crowd == 2">
                               <el-checkbox v-model="form.Crowd1">TD-游戏</el-checkbox>
                            </el-form-item>
                  </el-form-item>
                  <el-form-item label="兴趣:">
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
                  <el-form-item label="地区:">
                          <el-radio v-model="form.diqu" label="1">不限</el-radio>
                          <el-radio v-model="form.diqu" label="2">自定义</el-radio>
                            <el-form-item v-if="form.diqu == 2">
                              <diqu :selectedData.sync="manyAreaValue"></diqu>
                            </el-form-item>
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
                  </el-form-item>
                </el-form> 
            </el-tab-pane>
            <el-tab-pane label="广告位" name="second_3">
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
            </el-tab-pane>
            <el-tab-pane label="确认提交" name="second_4">
                   <el-form ref="form" :model="form"  status-icon>
                       <el-form-item label="推广计划:">
                                <span>{{v_planName}}</span>
                       </el-form-item>
                      <el-form-item label="推广单元:">
                                <span>{{v_unitName}}</span>
                      </el-form-item>
                       <el-form-item label="投放方式:">
                                 <span>{{v_deliveryMode}}</span>
                       </el-form-item>
                      <el-form-item label="价格:">
                                <span>{{v_price}}</span>
                       </el-form-item>
                       <el-form-item label="每日限额:">
                               <span>{{v_unitLimit}}</span>
                       </el-form-item>
                       <el-form-item label="投放日期:">
                                <span>{{v_star_end}}</span>
                       </el-form-item>
                       <el-form-item label="投放类型:">
                                <span>{{v_extensionType}}</span>
                       </el-form-item>
                       <el-form-item label="曝光上报地址:">
                                <span>{{v_impMonitorUrl}}</span>
                       </el-form-item>
                       <el-form-item label="点击上报地址:">
                                 <span>{{v_clkMonitorUrl}}</span>
                       </el-form-item>
                       <el-form-item label="扩展参数(ext_creative_id):">
                                 <span>{{v_extCreativeId}}</span>
                       </el-form-item>
                       <el-form-item label="广告位:">
                                <span>{{v_adCollection}}</span>
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
            </el-tab-pane>
          </el-tabs>
            
             <span slot="footer" class="dialog-footer">
                <el-button style="margin-top: 12px;" @click="last_" v-if="activeName != 'second_1'">上一步</el-button>
                <el-button style="margin-top: 12px;" @click="next_" v-if="activeName != 'second_4'">下一步</el-button>
                <!-- <el-button @click="newVisible = false">取 消</el-button> -->
                <el-button type="primary" @click="saveNews" v-if="activeName == 'second_4'">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import picker from "../../../../components/common/picker/index.vue";
import diqu from '../../../../components/common/diqu.vue'


export default {
  name: "basetable",
  data() {
    return {
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
      time:'',
      unitLimit:'',
      deliveryMode:'1',
      price:'',
         v_planName:'',
        v_unitName:'',
        v_deliveryMode:'',
        v_price:'',
        v_unitLimit:'',
        v_star_end:'',
        v_extensionType:'',
        v_impMonitorUrl:'',
        v_clkMonitorUrl:'',
        v_extCreativeId:'',
        v_adCollection:'',

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
     
      form: {
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
        time:'',
        time_frame:'1',
        syID:'',
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
       
        set_time:[{begin:'',end:''}],
      },
      options1: "1",
      options2: "2",
      options3: "3",
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
        append_div:[],
     
    };
  },
  created() {
    this.getData();
  },
  components: {
    picker: picker,
    diqu:diqu,
  },
  computed: {
    data() {
      return this.tableData.filter(d => {
        let is_del = false;
        for (let i = 0; i < this.del_list.length; i++) {
          if (d.name === this.del_list[i].name) {
            is_del = true;
            break;
          }
        }
        if (!is_del) {
          // if (
          //   d.address.indexOf(this.select_cate) > -1 &&
          //   (d.name.indexOf(this.select_word) > -1 ||
          //     d.address.indexOf(this.select_word) > -1)
          // ) {
          return d;
          // }
        }
      });
    }
  },
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.cur_page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
      if (process.env.NODE_ENV === "development") {
        this.url = "/ms/table/list";
      }

      this.$axios
        .post(this.url, {
          page: this.cur_page
        })
        .then(res => {
          this.tableData = res.data.list;
        });
    },
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
        time:'',
        time_frame:'1',
         unitLimit:'',
         deliveryMode:'1',
         price:'',
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

        set_time:[{begin:'',end:''}],
      };
      this.activeName = "second_1";
      this.append_div = [{time_frame: "1"}];
    },
    saveNews() {
      this.tableData.push(this.form);
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
    last_(){
        let value = this.activeName.split("_");
        this.activeName = value[0]+"_"+parseInt(parseInt(value[1])-1)
    },
    next_(){
        let value = this.activeName.split("_");
        this.activeName = value[0]+"_"+parseInt(parseInt(value[1])+1)
        console.log(  this.activeName)
    }, 
    make_address() {
      this.form.make_address1.push({ make_address: "" });
    },
    click_address() {
      this.form.click_address1.push({ click_address: "" });
    },
    click_shebei() {
      console.log(this.form);
      this.form.click_shebei1.push({ click_shebei: "" });
    },
    delete_task(j){
        this.form.make_address1.splice(j,1)
    },
    delete_click(z){
        this.form.click_address1.splice(z,1)
    },
    add_div(){
        this.append_div.push({time_frame: "1"})
    },
    delete_div(g){
         this.append_div.splice(g,1)
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
.add_task{
    
    border:2px dashed green;
    padding: 10px;
    margin: 10px;
}
.btn_right{
     margin-left: 88%;
}
.r_div{
    margin-left: 80px
}
</style>




