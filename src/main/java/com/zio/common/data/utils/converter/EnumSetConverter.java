package com.zio.common.data.utils.converter;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;


public class EnumSetConverter<E extends Enum<E>> implements AttributeConverter<EnumSet<E>, Long> {

    private final Class<E> enumClass;

    public EnumSetConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Long convertToDatabaseColumn(EnumSet<E> enumSet) {
        if (enumSet == null || enumSet.isEmpty()) {
            return 0L;
        }
        long bitmask = 0L;
        for (E e : enumSet) {
            bitmask |= (1L << e.ordinal());
        }
        return bitmask;
    }

    @Override
    public EnumSet<E> convertToEntityAttribute(Long bitmask) {
        EnumSet<E> enumSet = EnumSet.noneOf(enumClass);
        if (bitmask == null || bitmask == 0L) {
            return enumSet;
        }
        for (E e : enumClass.getEnumConstants()) {
            if ((bitmask & (1L << e.ordinal())) != 0) {
                enumSet.add(e);
            }
        }
        return enumSet;
    }
}
