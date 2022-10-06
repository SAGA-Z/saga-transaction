package org.sagaz.purchase.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonResponse<T>  {
    public Boolean success;
    public T response;
}
