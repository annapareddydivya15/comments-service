package com.divya.apps.comments.config;


import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
@ReadingConverter
public class DocumentToOffsetDateTimeConverter implements Converter<Document, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(final Document document) {
        final Date dateTime = document.getDate(OffsetDateTimeToDocumentConverter.DATE_FIELD);
        final ZoneOffset offset = ZoneOffset.of(document.getString(OffsetDateTimeToDocumentConverter.OFFSET_FIELD));
        return OffsetDateTime.ofInstant(dateTime.toInstant(), offset);
    }

}