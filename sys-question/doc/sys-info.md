# 描述

问卷系统类似考试试系统，仿照考试系统来开发。分为题型管理，试题管理，考试管理，用户管理等等。



# 管理说明

## 题型管理

### 单选题

* 只能选择选项中的一个选项
* 可设置是否需要其他选项
  * 若有其他选项，也可指明是否需要补充其他说明

### 多选题

* 可以选择多个选项
* 可设置是否需要其他选项
  * 若有其他选项，也可指明是否需要补充其他说明

### 填空题

* 以一段文字描述，中间穿插需要补充的空白区
* 通过特殊的分割符(以下划线显示)定义空白区
  * 需要指定空白区长度，可以在标准输入中前后增加2-5个字符

### 判断题

* 只有对与错的选项
* 可以是输入型(√/×)，或者单选题类型

### 问答题

* 根据问题，做问题解答

### 连线题

* 根据选择项内容做内容匹配

### 画图题

* 根据问题，绘画

### 作文题

* 类似问答题，根据问题描述，自行总结内容，并书写指定长度的自我理解



## 试题管理

分为题库管理和试卷管理

### 题库管理

1. 设计明确的题内容，指定问题描述、题型、选项，作答等等。

2. 可以对单个试题做添加，删除、修改等操作
   1. 不可变更试题类型
   2. 不可在试题有试卷绑定时删除
   3. 不可真删除
3. 部分试题需要明确试题答案

### 试卷管理

1. 将多个试题绑定为一套试卷
2. 指定试题顺序，题号，分数
3. 指定是否需要答题号，一般分为大题号和小题号
4. 统计或校验试卷总分或其他限制条件
5. 除试题及顺序外，其他都为可选项



## 考试管理

1. 定义考试时间、时长、试卷、主/副监考人
2. 除试卷外，其他内容都是可选项



## 用户管理

* 限定考试用户范围：学校限定年级班级，社会无限制(可见即可答)
* 获取用户信息：根据推广方式获取所需信息



# 数据库设计

* 题型分类：名称，标识，描述，
* 试题库：问题，题型，描述，选项，答案
* 试卷信息：描述，分数，标题，
* 试卷详情：题号，试题，分数
* 作答信息：试卷，题号，解答

## 题库表`tb_questions`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>id</td>
        <td>int(11)</td>
        <td>11</td>
        <td>题标识ID，生成规则：YYYYMMDDHHmmss</td>
    </tr>
    <tr>
        <td>title</td>
        <td>varchar(200)</td>
        <td>200</td>
        <td>问题</td>
    </tr>
    <tr>
        <td>type</td>
        <td>int(1)</td>
        <td>1</td>
        <td>题型分类，见题型表</td>
    </tr>
    <tr>
        <td>desc</td>
        <td>varchar(200)</td>
        <td>200</td>
        <td>问题描述</td>
    </tr>
    <tr>
        <td>cate</td>
        <td>varchar(100)</td>
        <td>100</td>
        <td>问题类别，见题类别表，逗号分割,待优化列</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td>-</td>
        <td>唯一索引</td>
        <td>`id`</td>
        <td>题标识ID，生成规则：YYYYMMDDHHmmss</td>
    </tr>
</table>

## 试题详情`tb_question_info`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>qid</td>
        <td>int(11)</td>
        <td>11</td>
        <td>题标识ID，见题库表</td>
    </tr>
    <tr>
        <td>选项</td>
        <td>blob</td>
        <td>-</td>
        <td>选项或描述，根据题型补充</td>
    </tr>
    <tr>
        <td>答案</td>
        <td>blob</td>
        <td>-</td>
        <td>选项或问答内容</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td>-</td>
        <td>唯一索引</td>
        <td>`qid`</td>
        <td>题标识ID</td>
    </tr>
</table>

## 分类表`tb_cate`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>id</td>
        <td>int(11)</td>
        <td>11</td>
        <td>标签ID</td>
    </tr>
    <tr>
        <td>name</td>
        <td>varchar(50)</td>
        <td>50</td>
        <td>分类名称</td>
    </tr>
    <tr>
        <td>pid</td>
        <td>int(11)</td>
        <td>11</td>
        <td>父类分类ID</td>
    </tr>
    <tr>
        <td>order</td>
        <td>int(11)</td>
        <td>11</td>
        <td>顺序</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td>-</td>
        <td>唯一索引</td>
        <td>`id`</td>
        <td>分类ID</td>
    </tr>
</table>

## 标签表`tb_label`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>id</td>
        <td>int(11)</td>
        <td>11</td>
        <td>标签ID</td>
    </tr>
    <tr>
        <td>name</td>
        <td>varchar(50)</td>
        <td>50</td>
        <td>标签名</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td>-</td>
        <td>唯一索引</td>
        <td>`id`</td>
        <td>标签ID</td>
    </tr>
</table>

## 试卷表`tb_papers`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>id</td>
        <td>int(11)</td>
        <td>11</td>
        <td>试卷标识ID，生成规则：YYYYMMDDHHmmss</td>
    </tr>
    <tr>
        <td>name</td>
        <td>varchar(50)</td>
        <td>50</td>
        <td>试卷名称</td>
    </tr>
    <tr>
        <td>total_score</td>
        <td>int(11)</td>
        <td>11</td>
        <td>试卷总分数，默认100</td>
    </tr>
    <tr>
        <td>type</td>
        <td>int(1)</td>
        <td>1</td>
        <td>试卷类型</td>
    </tr>
    <tr>
        <td>cate</td>
        <td>int(1)</td>
        <td>1</td>
        <td>试卷分类</td>
    </tr>
    <tr>
        <td>status</td>
        <td>int(1)</td>
        <td>1</td>
        <td>试卷状态</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td>-</td>
        <td>唯一索引</td>
        <td>`id`</td>
        <td>试卷标识ID，生成规则：YYYYMMDDHHmmss</td>
    </tr>
</table>

## 试卷大题表`tb_paper_struct`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>pid</td>
        <td>int(11)</td>
        <td>11</td>
        <td>试卷标识ID，见试卷表</td>
    </tr>
    <tr>
        <td>bqn</td>
        <td>int</td>
        <td>11</td>
        <td>试题大题号，见试卷大题表</td>
    </tr>
    <tr>
        <td>info</td>
        <td>varchar(20)</td>
        <td>20</td>
        <td>大题简介</td>
    </tr>
    <tr>
        <td>desc</td>
        <td>varchar(200)</td>
        <td>200</td>
        <td>大题详细描述</td>
    </tr>
    <tr>
        <td>idx</td>
        <td>int</td>
        <td>11</td>
        <td>大题顺序</td>
    </tr>
    <tr>
        <td>total_score</td>
        <td>int(11)</td>
        <td>11</td>
        <td>试卷大题总分数，默认0</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td></td>
        <td>唯一索引</td>
        <td>`pid`,`bqn`</td>
        <td>试卷大题唯一</td>
    </tr>
</table>

## 试卷试题表`tb_paper_questions`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>pid</td>
        <td>int(11)</td>
        <td>11</td>
        <td>试卷标识ID，见试卷表</td>
    </tr>
    <tr>
        <td>bqn</td>
        <td>int</td>
        <td>11</td>
        <td>试题大题号，见试卷大题表</td>
    </tr>
    <tr>
        <td>qid</td>
        <td>int(11)</td>
        <td>11</td>
        <td>试题ID，见试题表</td>
    </tr>
    <tr>
        <td>order</td>
        <td>int</td>
        <td>11</td>
        <td>试题顺序</td>
    </tr>
    <tr>
        <td>score</td>
        <td>int(11)</td>
        <td>11</td>
        <td>试卷试题分数，默认0</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td>`pq`</td>
        <td>唯一索引</td>
        <td>`pid`,`qid`</td>
        <td>试卷小题唯一</td>
    </tr>
</table>

## 试卷详情表`tb_paper_info`

暂略。

## 用户表`tb_users`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>id</td>
        <td>int(11)</td>
        <td>11</td>
        <td>用户ID，系统生成</td>
    </tr>
    <tr>
        <td>username</td>
        <td>varchar(20)</td>
        <td>20</td>
        <td>用户名</td>
    </tr>
    <tr>
        <td>password</td>
        <td>char(64)</td>
        <td>64</td>
        <td>用户密码，加密</td>
    </tr>
    <tr>
        <td>sex</td>
        <td>int(1)</td>
        <td>1</td>
        <td>性别</td>
    </tr>
    <tr>
        <td>age</td>
        <td>int(3)</td>
        <td>3</td>
        <td>年龄</td>
    </tr>
    <tr>
        <td>phone</td>
        <td>varchar(20)</td>
        <td>20</td>
        <td>手机号</td>
    </tr>
    <tr>
        <td>email</td>
        <td>varchar(50)</td>
        <td>50</td>
        <td>邮箱地址</td>
    </tr>
    <tr>
        <td>logo</td>
        <td>varchar(100)</td>
        <td>100</td>
        <td>用户头像</td>
    </tr>
    <tr>
        <td>source</td>
        <td>varchar(20)</td>
        <td>20</td>
        <td>来源，如微信，注册，其它</td>
    </tr>
    <tr>
        <td>rdt</td>
        <td>datetime</td>
        <td>-</td>
        <td>注册时间</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td></td>
        <td>唯一索引</td>
        <td>`id`</td>
        <td>用户ID</td>
    </tr>
</table>

## 用户试卷表`tb_user_paper`

暂略。

## 用户试卷答题表`tb_user_paper_info`

暂略。

## 系统操作记录表`tb_sys_opt`

暂略。

## 用户访问记录表`tb_user_logs`

暂略。

## 字典表`tb_dict`

<table>
	<tr>
        <th>字段</th>
        <th>类型</th>
        <th>长度</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>id</td>
        <td>int(11)</td>
        <td>11</td>
        <td>字典ID，自增</td>
    </tr>
    <tr>
        <td>name</td>
        <td>varchar(20)</td>
        <td>20</td>
        <td>字典名称</td>
    </tr>
    <tr>
        <td>pid</td>
        <td>int(11)</td>
        <td>11</td>
        <td>父级字典</td>
    </tr>
    <tr>
        <td>order</td>
        <td>int</td>
        <td>11</td>
        <td>字典顺序</td>
    </tr>
</table>

<table>
	<tr>
        <th>索引分类</th>
        <th>索引名称</th>
        <th>索引类型</th>
        <th>索引列</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>主键</td>
        <td></td>
        <td>唯一索引</td>
        <td>`id`</td>
        <td>字典ID</td>
    </tr>
    <tr>
        <td>索引</td>
        <td>`idx_p`</td>
        <td>普通索引</td>
        <td>`pid`</td>
        <td>父级字典索引</td>
    </tr>
</table>

# 业务设计

* 题型分类一般为静态内容不变
* 试题允许添加，删除，修改
* 试卷绑定试题，顺序，分数，描述
* 试卷作答负责记录用户对试卷的解答



# UI设计



