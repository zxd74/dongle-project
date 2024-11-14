import * as echarts from 'echarts'
export const createEcharts = (element,data,text)=>{
    var echart = echarts.init(element)
    echart.resize({width: 700,height: 300})
    var options = {
        title:{},
        legend:{},
        tooltip:{},
        xAxis:{
            data:[],
        },
        yAxis:{},
        series:[]
    }
    var dates=[],highs=[],opens=[],prices=[],lows=[],title= text ||'',gaps=[]
    data.forEach(key => {
        if(title == '') title = key.code 
        dates.push(key.date)
        highs.push(key.high)
        opens.push(key.open)
        prices.push(key.price)
        lows.push(key.low)
        gaps.push((key.high-key.low).toFixed(2))
    });
    options.title.text = title
    options.xAxis.data = dates
    options.yAxis.scale = true
    options.series.push({name:'开盘',type:'line',data:opens})
    options.series.push({name:'最高',type:'line',data:highs})
    options.series.push({name:'最低',type:'line',data:lows})
    options.series.push({name:'收盘',type:'line',data:prices})
    options.series.push({name:'最差',type:'line',data:gaps})
    echart.setOption(options)
    return echart
}

// 股票差值数据图
export const createEchartsForStockGap=(element,data,text)=>{
    var echart = echarts.init(element)
    echart.resize({width: 700,height: 300})
    var options = {
        title:{},
        legend:{},
        tooltip:{},
        xAxis:{
            data:[],
        },
        yAxis:{},
        series:[]
    }
    var dates=[],highs=[],opens=[],prices=[],lows=[],title= text ||'',gaps=[]

    data.forEach(key => {
        if(title == '') title = key.code 
        dates.push(key.date)
        highs.push(key.high_gap)
        opens.push(key.open_gap)
        prices.push(key.price_gap)
        lows.push(key.low_gap)
        gaps.push((key.high-key.low).toFixed(2))
    });
    options.title.text = title
    options.xAxis.data = dates
    options.yAxis.scale = true
    options.series.push({name:'开盘差',type:'line',data:opens,itemStyle : { normal: {label : {show: true}}}})
    options.series.push({name:'最高差',type:'line',data:highs})
    options.series.push({name:'最低差',type:'line',data:lows})
    options.series.push({name:'收盘差',type:'line',data:prices})
    options.series.push({name:'最差',type:'line',data:gaps})
    echart.setOption(options)
    return echart
}

export const formatTitle=(stock=>stock.name + '(' + stock.code + ")")

/***
 * EchartData:
 *      title :need 标题
 *      xdata :need x轴数据
 *      ydata :need y轴数据
 *      sdata :need 图例数据
 *      options == echart options
 * 
 * ydata.length = sdata.length
 */
export const createLineEchart=(element,data)=>{
    if(!validateEchartData(data)) throw new Error('EchartData is invalid')
    var echart = echarts.init(element)
    var options = data.options || {title:{},legend:{},tooltip:{},xAxis:{},yAxis:{},series:[]}
    echart.resize({width: options.width||element.parentNode.width,height: options.height||300})

    options.title.text = data.title
    options.xAxis.data = data.xdata
    options.yAxis.scale = true

    for (var i=0;i<data.sdata.length;i++){
        options.series.push({name:data.sdata[i],type:'line',data:data.ydata[i]})
    }

    echart.setOption(options)
}

export const createLineEchartWithAvg=(element,data)=>{
    if(!validateEchartData(data)) throw new Error('EchartData is invalid')
    var echart = echarts.init(element)
    var options = data.options || {title:{},legend:{},tooltip:{},xAxis:{},yAxis:{},series:[]}
    echart.resize({width: options.width||element.parentNode.width,height: options.height||300})

    options.title.text = data.title
    options.xAxis.data = data.xdata
    options.yAxis.scale = true

    for (var i=0;i<data.sdata.length;i++){
        options.series.push({name:data.sdata[i],type:'line',data:data.ydata[i],
            markLine:{data:[{type:'average',name:'平均值'}],label : {show: true,position:'middle',formatter:"平均：{c}"}}
        })
    }

    echart.setOption(options)
}

const validateEchartData=(data)=>{
    // 值是否存在
    if(!data || !data.title || !data.xdata || !data.ydata || !data.sdata ) return false
    // 类型校验
    if(!data.xdata instanceof Array || !data.ydata instanceof Array || !data.sdata instanceof Array) return false
    // 额外校验
    if(data.ydata.length != data.sdata.length) return false
    return true
}

export const createEchartsForStock=(element,data,text)=>{
    var echartData = {}
    echartData.title = text
    echartData.xdata = []
    echartData.ydata = []
    echartData.sdata = ["开盘","最高","最低","收盘","最差"]
    echartData.sdata.forEach(()=>echartData.ydata.push([]))
    data.forEach(item=>{
        echartData.xdata.push(item.date)
        var idx = 0
        echartData.ydata[idx++].push(item.open)
        echartData.ydata[idx++].push(item.high)
        echartData.ydata[idx++].push(item.low)
        echartData.ydata[idx++].push(item.price)
        echartData.ydata[idx].push((item.high-item.low).toFixed(2))
    })

    createLineEchart(element,echartData)
}
export const createEchartsForStockByGrap=(element,data,text)=>{
    var echartData = {}
    echartData.title = text
    echartData.xdata = []
    echartData.ydata = []
    echartData.sdata = ["开盘差","最高差","最低差","收盘差","前日最差"]
    echartData.sdata.forEach(()=>echartData.ydata.push([]))
    data.forEach(item=>{
        echartData.xdata.push(item.date)
        var idx = 0
        echartData.ydata[idx++].push(item.open_gap)
        echartData.ydata[idx++].push(item.high_gap)
        echartData.ydata[idx++].push(item.low_gap)
        echartData.ydata[idx++].push(item.price_gap)
        echartData.ydata[idx].push(item.pre_gap)
    })
    createLineEchartWithAvg(element,echartData)
}

