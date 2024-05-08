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

// 股票插值数据图
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