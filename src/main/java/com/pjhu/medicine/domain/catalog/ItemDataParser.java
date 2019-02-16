package com.pjhu.medicine.domain.catalog;

import java.util.List;

public interface ItemDataParser<T> {

    List<ItemData> parse(T input);
}
