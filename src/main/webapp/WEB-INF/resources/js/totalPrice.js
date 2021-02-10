$(function () {
    $('input[type=checkbox]').change(function () {
        var totalPriceNow = 0;
        //loop through checked checkboxes
        $('input[type=checkbox]:checked').each(function () {
            var productId = 'productId' + this.value;
            var qantityId = 'quantityId' + this.value;
            var price = parseInt($('#' + productId).text().trim());
            var quantity = parseInt($('#' + qantityId).text().trim());
            var price = price * quantity;
            totalPriceNow += price; //get text
        })
        $('#totalCost').text(totalPriceNow + '$');
    });
})