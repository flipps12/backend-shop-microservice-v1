package com.ronnie.products_service.models.dtos;

public record BaseResponse(String[] errorMensages) {

    public boolean hasError() {
        return errorMensages != null && errorMensages.length > 0;
    }
}
