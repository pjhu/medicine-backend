package com.pjhu.medicine.product.domain.model;

import java.util.List;

public interface ProductParser<T> {

    List<Product> parse(T input);
}
