package com.pjhu.medicine.catalog.domain.model;

import java.util.List;

public interface ItemDataParser<T> {

    List<ItemData> parse(T input);
}
