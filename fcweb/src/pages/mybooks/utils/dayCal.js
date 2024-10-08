export const dateCalHelperGetTotal = (list) => {
    let resultArray = {
        total: [],
        in: [],
        out: [],
        eat: [],
        fun: [],
        house: [],
        travel: [],
        life: [],
        big: [],
        other: []
    }
    const groupedData = groupData(list)
    for (let index = 1; index <= 31; index++) {
        //收入
        let income = 0
        for (const element of groupedData[index]) {
            if(element.countType === '入账'){
                income += element.countNum
            }
        }
        resultArray.in.push(income)
        //支出
        let expend = 0
        for (const element of groupedData[index]) {
            if(element.countNum < 0){
                expend += -1 * element.countNum
            }
        }
        resultArray.out.push(expend)
        //总收支
        resultArray.total.push(income - expend)
        //吃喝
        let eat = 0;
        for (const element of groupedData[index]) {
            if(element.countType === '吃喝'){
                eat += -1 * element.countNum
            }
        }
        resultArray.eat.push(eat)
        //玩乐
        let fun = 0;
        for (const element of groupedData[index]) {
            if(element.countType === '玩乐'){
                fun += -1 * element.countNum
            }
        }
        resultArray.fun.push(fun)
        //住宿
        let house = 0;
        for (const element of groupedData[index]) {
            if(element.countType === '住宿'){
                house += -1 * element.countNum
            }
        }
        resultArray.house.push(house)
        //旅行
        let travel = 0;
        for (const element of groupedData[index]) {
            if(element.countType === '旅行'){
                travel += -1 * element.countNum
            }
        }
        resultArray.travel.push(travel)
        //生活花销
        let life = 0;
        for (const element of groupedData[index]) {
            if(element.countType === '生活花销'){
                life += -1 * element.countNum
            }
        }
        resultArray.life.push(life)
        //大宗商品
        let big = 0;
        for (const element of groupedData[index]) {
            if(element.countType === '大宗商品'){
                big += -1 * element.countNum
            }
        }
        resultArray.big.push(big)
        //其他
        let other = 0;
        for (const element of groupedData[index]) {
            if(element.countType === '其他'){
                other += -1 * element.countNum
            }
        }
        resultArray.other.push(other)
    }
    
    return resultArray
}

const groupData = (list)=>{
    const groupedData = list.reduce((acc,  item) => {
        const date = item.countTime.split('T')[0]; // 提取日期部分
        const day = parseInt(date.split('-')[2], 10); // 获取日期的日部分
        if (!acc[day]) {
            acc[day] = [];
        }
        acc[day].push(item);
        return acc;
    }, {})
    console.log(groupedData)
    for (let day = 1; day <= 31; day++) {
        if (!groupedData[day]) {
            groupedData[day] = [];
        }
    }
    return groupedData
}