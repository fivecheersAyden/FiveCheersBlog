export const keyToJSONObject = (keys)=>{
    let result = []
    let level1 = []
    let level2 = []
    let level3 = []
    let allNodeKeys = []
    // 第一层遍历
    for (let i = 0; i < keys.length; i++) {
        const key = keys[i];
        if (isFirstLevel(key)) {
            let tmp = {
                title: key.title,
                key: key.blogKey,
                children: []
            }
            result.push(tmp);
            level1.push(tmp);
            allNodeKeys.push(tmp.key)
            keys.splice(i, 1);
            i--;
        }
    }
    //第一层排序
    result.sort((a, b) => {
        const keyA = parseInt(a.key.split('-')[0], 10);
        const keyB = parseInt(b.key.split('-')[0], 10);
        return keyA - keyB;
    });
    //第二层遍历
    for (const level1Element of level1) {
        for (let i = 0; i < keys.length; i++) {
            if(isChildLevel2(level1Element.key, keys[i].blogKey)){
                let tmp = {
                    title: keys[i].title,
                    key: keys[i].blogKey,
                    children: []
                }
                level1Element.children.push(tmp);
                level2.push(tmp)
                allNodeKeys.push(tmp.key)
                keys.splice(i, 1);
                i--;
            }
        }
        //第二层排序
        level1Element.children.sort((a, b) => {
            const keyA = parseInt(a.key.split('-')[1], 10);
            const keyB = parseInt(b.key.split('-')[1], 10);
            return keyA - keyB;
        });
    }
    //第三层遍历
    for (const level2Element of level2) {
        for (let i = 0; i < keys.length; i++) {
            if(isChildLevel3(level2Element.key, keys[i].blogKey)){
                let tmp = {
                    title: keys[i].title,
                    key: keys[i].blogKey,
                    children: []
                }
                level2Element.children.push(tmp);
                level3.push(tmp)
                allNodeKeys.push(tmp.key)
                keys.splice(i, 1);
                i--;
            }
        }
        //第三层排序
        level2Element.children.sort((a, b) => {
            const keyA = parseInt(a.key.split('-')[2], 10);
            const keyB = parseInt(b.key.split('-')[2], 10);
            return keyA - keyB;
        });
    }
    //第四层遍历
    for (const level3Element of level3) {
        for (let i = 0; i < keys.length; i++) {
            if(isChildLevel4(level3Element.key, keys[i].blogKey)){
                let tmp = {
                    title: keys[i].title,
                    key: keys[i].blogKey,
                    children: []
                }
                level3Element.children.push(tmp);
                keys.splice(i, 1);
                i--;
            }
        }
        //第四层排序
        level3Element.children.sort((a, b) => {
            const keyA = parseInt(a.key.split('-')[3], 10);
            const keyB = parseInt(b.key.split('-')[3], 10);
            return keyA - keyB;
        });
    }
    return {
        JSONObj: result,
        allNodeKeys: allNodeKeys
    }
}

//获取第一层元素
const isFirstLevel = (key)=>{
    let tmp = key.blogKey.split('-')
    return tmp[1] === '0';
}

//判断第二层元素的从属关系
const isChildLevel2 = (parentKey, childKey) => {
    let parentParts = parentKey.split('-')
    let childParts = childKey.split('-')
    return parentParts[0] === childParts[0] && childParts[2] === '0';
}

//判断第三层元素的从属关系
const isChildLevel3 = (parentKey, childKey) => {
    let parentParts = parentKey.split('-')
    let childParts = childKey.split('-')
    return parentParts[0] === childParts[0] && parentParts[1] === childParts[1] && childParts[3]==='0';
}

//判断第四层元素的从属关系
const isChildLevel4 = (parentKey, childKey) => {
    let parentParts = parentKey.split('-')
    let childParts = childKey.split('-')
    return parentParts[0] === childParts[0] && parentParts[1] === childParts[1] && parentParts[2] === childParts[2];
}
