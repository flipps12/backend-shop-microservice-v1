package com.ronnie.products_service.entities.dtos;

public record BaseResponse(String[] errorMensages) {

    public boolean hasError() {
        return errorMensages != null && errorMensages.length > 0;
    }
}
