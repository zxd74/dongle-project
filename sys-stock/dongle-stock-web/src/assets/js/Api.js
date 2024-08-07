import axios from 'axios';
let base = `${process.env.API_ROOT}`;

if (base.endsWith("/")){
    base = base.substring(0,base.length-1)
}

const dataApi = base + "/api/data"
const stockApi = base + "/api/stock-manage"
const StockGroupApi = base + '/api/stock-group'

axios.interceptors.request.use(config => {
    config.headers.token = localStorage.getItem("t")
    config.headers['Content-Type'] = "application/json";
    // config.headers['Access-Control-Allow-Origin'] = "*";
    return config;
}, function (error) {
    return Promise.reject(error);
});
// http response 拦截器
axios.interceptors.response.use(
    response => {
        if (response.status == 403) {
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
    }
);

/***************数据管理接口********** */
export const allStockData = () =>{
    return axios.get(`${dataApi}/all`)
}
export const allStockNewData = () =>{
    return axios.get(`${dataApi}/new`)
}
export const stockAllData = (params) =>{
    return axios.get(`${dataApi}/stock`,{params:params})
}
export const groupStockAllData = (params)=>{
    return axios.get(`${dataApi}/group-all`,{params:params})
}


/************股票管理接口******* */
export const stockInfo = (params) =>{
    return axios.get(`${stockApi}/query`,{params:params})
}
export const allStocks = () =>{
    return axios.get(`${stockApi}/all-stock`)
}

/***************股票分组管理接口***********************/
export const allStockGroup = ()=> axios.get(`${StockGroupApi}/all`)
export const saveStockGroup = (params) => axios.post(`${StockGroupApi}/options?options=1`,params)
export const deleteStockGroup = (params) => axios.post(`${StockGroupApi}/options?options=2`,params)
export const getStockGroup = (params) => axios.get(`${StockGroupApi}/group-all`,{params:params})
export const addGroupStock = (params) => axios.post(`${StockGroupApi}/add-group-stock`,params)
export const deleteGroupStock = (params) => axios.post(`${StockGroupApi}/del-group-stock`,params)