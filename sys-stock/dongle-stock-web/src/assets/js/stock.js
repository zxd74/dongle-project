import * as echarts from 'echarts'
export const createEcharts = (element,data)=>{
    var echart = echarts.init(element)
    echart.resize({width: 700,height: 300})
    var options = {
        title:{
            text:'股票数据'
        },
        legend:{},
        tooltip:{},
        xAxis:{
            data:[],
        },
        yAxis:{},
        series:[]
    }
    var dates=[],hights=[],opens=[],prices=[],lows=[],title=''
    data.forEach(key => {
        if(title == '') title = key.code 
        dates.push(key.date)
        hights.push(key.hight)
        opens.push(key.open)
        prices.push(key.price)
        lows.push(key.low)
    });
    options.title.text = title
    options.xAxis.data = dates
    options.yAxis.scale = true
    options.series.push({name:'开盘',type:'line',data:opens})
    options.series.push({name:'最高',type:'line',data:hights})
    options.series.push({name:'最低',type:'line',data:lows})
    options.series.push({name:'收盘',type:'line',data:prices})
    echart.setOption(options)
    return echart
}