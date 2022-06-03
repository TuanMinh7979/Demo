package com.example.trymyself.converter;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListLongToStringConverter implements AttributeConverter<List<Long>, String> {
    @Override
    public String convertToDatabaseColumn(List<Long> longAttributes) {
        StringBuilder sb = new StringBuilder();
        for (Long atbi : longAttributes) {
            sb.append(atbi);
        }
        return sb.toString();
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.equals("")) return Collections.emptyList();


        dbData = dbData.substring(0, dbData.length() - 1);
        return List.of(dbData.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
    }
}