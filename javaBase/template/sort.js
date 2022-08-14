var fzqk = [
    { station01_1: "1", },
    { station02_1: "0" },
    { station03_1: "1" },
    { station04_1: "1" },
    { station05_1: "1" },
    { station06_1: "0" },
    { station07_1: "0" },
    { station08_1: "0" },
    { station09_1: "0" },
    { station10_1: "0" },
    { station11_1: "0" },
    { station12_1: "0" },

    { station01_2: "1", },
    { station02_2: "1" },
    { station03_2: "1" },
    { station04_2: "1" },
    { station05_2: "0" },
    { station06_2: "0" },
    { station07_2: "0" },
    { station08_2: "0" },
    { station09_2: "0" },
    { station10_2: "0" },
    { station11_2: "0" },
    { station12_2: "0" },

    { station01_3: "0" },
    { station02_3: "0" },
    { station03_3: "2" },
    { station04_3: "0" },
    { station05_3: "0" },
    { station06_3: "0" },
    { station07_3: "0" },
    { station08_3: "0" },
    { station09_3: "0" },
    { station10_3: "0" },
    { station11_3: "0" },

    { station01_4: "1", }
]

var num = [1,2,3,4];

var result = [];

function sort(){
    // 根据尾数分组
    num.map(function (n){
        let temp = []
        fzqk.map( function (item){
            for (let itemKey in item) {
                if (itemKey.contain("_")){
                    let s = itemKey.substring(9,1);
                    temp.push(item)
                }
            }
        })
        result.push(temp);
    })

    console.log(result)
}

sort()

