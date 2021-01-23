$(document).ready(function (){
    caltQuantity();
});

function caltQuantity(){
    let quantitySpan = $('#quantity');
    let totalQuantity = 0;
    let array = $('#bucketCount');
    let result = array.map(({ quantityInBucket }) => quantityInBucket);
    let splitArray = array.data('bucket').split("quantityInBucket=")
    for(var i=1;i<splitArray.length;i++){
        if(splitArray[i][1] == ','){
            totalQuantity += Number(splitArray[i][0]);
        }else {
            let num = splitArray[i][0] + splitArray[i][1];
            totalQuantity += Number(num);
        }
    }
    quantitySpan.text(totalQuantity);
}