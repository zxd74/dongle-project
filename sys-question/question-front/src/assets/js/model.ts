export declare type QuestionType = {
    type?:number, // 题类型标识
    name?:string,// 题类型名称
}

export declare type Question={
    idx?:number // 题序
    qn?:number // 题号，一般为题序+1
    desc?:string // 详细问题
    type?:QuestionType // 题类型
    
}
