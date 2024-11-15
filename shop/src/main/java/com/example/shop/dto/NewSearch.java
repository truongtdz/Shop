package com.example.shop.dto;

import com.example.shop.enums.CategoryEnum;
import com.example.shop.enums.SortEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewSearch {
    Long minPrice;
    Long maxPrice;
    List<CategoryEnum> categories;
    SortEnum typeSort;
}
