// 服务API
import axios from 'axios'

const serverUrl="http://localhost:8081"

axios.interceptors.request.use(
    config =>{
        //config.headers["token"] = "dongle"
        config.headers['Content-Type'] = "application/json";
        return config
    },function (error) {
        // Do something with request error
        return Promise.reject(error);
    }
)
axios.interceptors.response.use(
    response => {
        return response
    },
    error => {
        console.log(error)
        // return Promise.reject(error.response.data)
        return error
    }
)

export const local= (url) => { return axios.get(url) }

// test accessing server 
export const access = ()=>{ return axios.get(`${serverUrl}/index`)}

